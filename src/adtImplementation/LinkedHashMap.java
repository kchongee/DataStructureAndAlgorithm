package adtImplementation;

import adtInterfaces.MapInterface;

import java.util.Iterator;
import static java.lang.Math.abs;


public class LinkedHashMap<K, V> implements MapInterface<K,V>, Iterable<MapInterface.Entry<K, V>>
{
    private Entry<K,V>[] entryBuckets;
    private static final int DEFAULT_BUCKET_QTY = 16;
    private static final int DEFAULT_POWER = 4;
    private static final double LOAD_FACTOR = 0.75;
    private int power; // 2 ^ 4 = 16 buckets
    private int totalEntries;
    private Entry<K, V> firstEntry;
    private Entry<K, V> lastEntry;


    public LinkedHashMap() {
        initializeBuckets();
        this.power = DEFAULT_POWER;
        this.totalEntries = 0;
        this.firstEntry = null;
        this.lastEntry = null;
    }


    private LinkedHashMap(int newSize) {
        initializeBuckets(newSize);
        this.power = DEFAULT_POWER;
        this.totalEntries = 0;
        this.firstEntry = null;
        this.lastEntry = null;
    }

    public boolean containsValue(V value) {
        return switch (totalEntries) {
            case 0 -> false;
            case 1 -> compareValue(firstEntry, value);
            default -> searchValueAlongChain(value);
        };
    }


    public V putOverwrite(MapInterface.Entry<K, V> entry) {
        Entry<K, V> newEntry = (LinkedHashMap.Entry<K, V>) entry;

        if (collisionHappen(newEntry.getKey())) {
            handleCollisionOverwrite(newEntry, bucket(entry.getKey()));
        } else {
            addForwardSequence(newEntry);
            this.totalEntries++;
        }

        rehashIfTooMuchEntries();
        return newEntry.getValue();
    }

    public V put(MapInterface.Entry<K, V> entry) {
        Entry<K, V> newEntry = (LinkedHashMap.Entry<K, V>) entry;

        if (collisionHappen(entry.getKey())) {
            handleCollisionPreserveOriginal(newEntry, bucket(entry.getKey()));
        } else {
            fillNullBucketWithEntry(entry.getKey(), newEntry);
        }

        rehashIfTooMuchEntries();
        return newEntry.getValue();
    }

    public V putOverwrite(K key, V value) {
        if (collisionHappen(key)) {
            handleCollisionOverwrite(new Entry<>(key, value), bucket(key));
        } else {
            fillNullBucketWithEntry(key, new Entry<>(key, value));
        }

        rehashIfTooMuchEntries();
        return value;
    }

    public V put(K key, V value) {
        if (collisionHappen(key)) {
            handleCollisionPreserveOriginal(new Entry<>(key, value), bucket(key));
        } else {
            fillNullBucketWithEntry(key, new Entry<>(key, value));
        }
        if (bucketsHaveTooMuchEntries()) {
            rehash();
        }
        return value;
    }

    public void handleCollisionOverwrite(Entry<K, V> newEntry, Entry<K, V> bucket) {
        Entry<K, V> existingEntry = bucket.searchEntryInBucket(newEntry.getKey());
        if (existingEntry == null) {   // new entry should added
            bucket.appendCollidedEntry(newEntry);
            addForwardSequence(newEntry);
            this.totalEntries++;
        } else { // overwrite old entry
            existingEntry.value = newEntry.getValue();
        }
    }

    public void handleCollisionPreserveOriginal(Entry<K, V> newEntry, Entry<K, V> bucket) {
        Entry<K, V> existingEntry = bucket.searchEntryInBucket(newEntry.getKey());
        if (existingEntry == null) // new entry should added
        {
            bucket.appendCollidedEntry(newEntry);
            addForwardSequence(newEntry);
            this.totalEntries++;
        }
    }

    // reason why use try catch is fasten access, no need check null condition
    public V get(K k) {
        try {
            if (bucket(k).getKey() == k)
                return bucket(k).getValue();
            else
                return bucket(k).searchEntryInBucket(k).getValue();
        } catch (NullPointerException e) {
            return null;
        }
    }


    public int size() {
        return totalEntries;
    }

    public boolean containsKey(K k) {
        if (bucket(k) != null) {
            Entry<K, V> result = bucket(k).searchEntryInBucket(k);
            return result != null;
        }
        return false;
    }

    public void clear() {
        initializeBuckets();
        this.totalEntries = 0;
        this.firstEntry = null;
        this.power = 4;
    }


    public boolean isEmpty() {
        return totalEntries == 0;
    }


    public LinkedHashSet<K> keySet() {
        @SuppressWarnings("unchecked")
        LinkedHashMap<K, Object> casted = (LinkedHashMap<K, Object>) this;
        return new LinkedHashSet<>(casted);
    }

    @Override
    public void putAll(MapInterface<K, V> map) {
        for (MapInterface.Entry<K, V> entry : this) {
            this.put(entry.getKey(), entry.getValue());
        }
    }


    public V remove(K key) {
        if(bucket(key) == null) { // have bucket associated with key
            Entry<K, V> removeTarget = bucket(key).searchEntryInBucket(key);
            removeFromLinkedChain(removeTarget);
            removeFromBucketCollisionChain(removeTarget, key);
            totalEntries--;
            assert removeTarget != null;
            return removeTarget.value;
        }
        return null;
    }

    @Override
    public V[] values() {
        return null;
    }


    private static class Entry<K, V> implements MapInterface.Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> collidedEntry;
        private Entry<K, V> lastEntry;
        private int totalEntry;
        Entry<K, V> next;
        Entry<K, V> before;


        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.lastEntry = this;
            this.collidedEntry = null;
            this.totalEntry = 1;
            this.next = null;
            this.before = null;
        }

        public Entry<K, V> searchEntryInBucket(K key) {
            @SuppressWarnings("unchecked") Entry<K, V>[] entry = new Entry[]{this, this.collidedEntry}; //[current, future]
            for (int i = 0; i < totalEntry; i++) {
                assert entry[0] != null;
                if (entry[0].getKey() == key || entry[0].getKey().equals(key)) {   // key matched
                    return entry[0];
                }
                entry[0] = entry[1];
                entry[1] = entry[1] == null ? null : entry[1].collidedEntry; // null = no method can use
            }
            return null;
        }



        /**
         * Remove element in collision chain
         */
        private boolean removeCollision(Entry<K, V> toRemove) {
            if (toRemove != this) {
                @SuppressWarnings("unchecked")
                Entry<K, V>[] state = new Entry[]{null, this};  // [previous, current]
                for (int i = 0; i < this.totalEntry; i++) {
                    if (state[1] == toRemove) {
                        state[0].collidedEntry = state[1].collidedEntry;
                        return true;
                    } // progress search until last
                    state[0] = state[1];
                    state[1] = state[1].collidedEntry;
                }
            }
            return false;
        }


        public void appendCollidedEntry(Entry<K, V> entry) {
            this.lastEntry.collidedEntry = entry;
            this.lastEntry = entry;
            this.totalEntry += 1;
        }


        public K getKey() {
            return key;
        }


        public V getValue() {
            return value;
        }


        public void addForwardLink(Entry<K, V> entry) {
            this.next = entry;
        }


        public void addBackwardLink(Entry<K, V> entry) {
            this.before = entry;
        }


        public Entry<K, V> getNext() {
            return next;
        }
    }


    // region : utility method
    private void rehash() {
        LinkedHashMap<K, V> newMap = new LinkedHashMap<>((int) Math.pow(2, ++this.power));
        for (MapInterface.Entry<K, V> entry : this) {
            newMap.put(entry);
        }
        this.entryBuckets = newMap.entryBuckets;
    }

    private boolean compareValue(Entry<K, V> entry, V value) {
        return entry.getValue() == value || entry.getValue().equals(value);
    }

    private void rehashIfTooMuchEntries() {
        if (bucketsHaveTooMuchEntries()) {
            rehash();
        }
    }

    public int bucketIndex(K key) {
        return abs(key.hashCode() % this.entryBuckets.length);
    }


    private boolean collisionHappen(K key) {
        return entryBuckets[bucketIndex(key)] != null;
    }


    private boolean bucketsHaveTooMuchEntries() {
        return totalEntries > entryQtyLimit();
    }


    private int entryQtyLimit() {
        return (int) (entryBuckets.length * LOAD_FACTOR);
    }


    private void addForwardSequence(Entry<K, V> entry) {
        if (totalEntries == 0) {
            this.firstEntry = entry;
            this.lastEntry = entry;
        } else {
            entry.addBackwardLink(lastEntry);
            lastEntry.addForwardLink(entry);
            this.lastEntry = entry;
        }
    }

    private void fillNullBucketWithEntry(K key, Entry<K, V> entry) {
        entryBuckets[bucketIndex(key)] = entry;
        addForwardSequence(entry);
        this.totalEntries++;
    }

    private Entry<K, V> bucket(K key) {
        return entryBuckets[bucketIndex(key)];
    }


    private void removeFromLinkedChain(Entry<K, V> entry) {
        if (entry == firstEntry) {    // remove first entry
            firstEntry = entry.next;
        } else if (entry == lastEntry) { // remove last entry
            entry.before.addForwardLink(null);
            lastEntry = entry.before;
        } else {
            entry.before.addForwardLink(entry.next);
        }
    }


    /**
     * Applicable for chain length > 2
     */
    private boolean searchValueAlongChain(V value) {
        @SuppressWarnings("unchecked") Entry<K, V>[] state = new Entry[]{firstEntry, firstEntry.next};
        while (state[0] != null){
            if (compareValue(state[0], value)) {
                System.out.println("compare " + state[0].getValue() + " with " + value);
                return true;
            } else {
                state[0] = state[1];
                state[1] = state[1] == null ? null : state[1].next;
            }
        }
        return false;
    }

    private void initializeBuckets() {
        @SuppressWarnings("unchecked")
        Entry<K, V>[] buckets = new Entry[DEFAULT_BUCKET_QTY];
        this.entryBuckets = buckets;
    }

    private void initializeBuckets(int size) {
        @SuppressWarnings("unchecked")
        Entry<K, V>[] buckets = new Entry[size];
        this.entryBuckets = buckets;
    }
    // endregion : utility method


    public Entry<K, V> getFirstEntry() {
        return firstEntry;
    }


    private static class LinkedHashMapIterator<K, V> implements Iterator<MapInterface.Entry<K, V>> {
        LinkedHashMap.Entry<K, V> current;

        public LinkedHashMapIterator(LinkedHashMap<K, V> linkedHashMap) {
            current = linkedHashMap.getFirstEntry();
        }

        public boolean hasNext() {
            return current != null;
        }

        public final MapInterface.Entry<K, V> next() {
            Entry<K, V> data = current;
            current = current.getNext();
            return data;
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder("{");
        for (MapInterface.Entry<K, V> entry : this) {
            str.append(entry.getKey()).append(":").append(entry.getValue());
            if (entry != lastEntry) {
                str.append(" ,");
            }
        }
        return str + "}";
    }

    private void removeFromBucketCollisionChain(Entry<K, V> toRemove, K key) {
        if (toRemove == bucket(key)) {       // need remove the bucket
            entryBuckets[bucketIndex(key)] = bucket(key).collidedEntry;
        } else {   // change reference
            bucket(key).removeCollision(toRemove);
        }
    }

    public static void main(String[] args) {
        LinkedHashMap<String, Integer> test = new LinkedHashMap<>();
        System.out.println("test null= " + test.get("1"));
        System.out.println("Keyset= " + test.keySet().toString());

        test.put("a", 1);
        test.put("b", 2);
        test.put("c", 3);
        System.out.println("contains3=" + test.containsValue(3));
        System.out.println("contains2=" + test.containsValue(2));

        test.put("d", 2);
        test.put("e", 7);
        test.put("f", 8);

        System.out.println("Keyset= " + test.keySet().toString());

        test.put("g", 2);
        test.put("h", 2);
        test.put("i", 2);
        test.put("j", 2);
        test.put("k", 2);
        test.put("l", 2);
        test.put("m", 2);
        test.put("n", 2);
        test.put("o", 2);
        test.put("p", 2);
        test.put("q", 2);
        test.put("r", 2);
        test.put("s", 2);
        test.put("s", 4);

        test.put("t", 2);
        test.remove("a");
        test.remove("s");

        System.out.println(test.toString());

        System.out.println(test.containsKey("a"));
        System.out.println(test.containsKey("o"));


        System.out.println(test.get("a"));
        System.out.println(test.get("b"));

        test.clear();
        System.out.println(test.toString());
        System.out.println(test.isEmpty());
    }

    public Iterator<MapInterface.Entry<K, V>> iterator() {
        return new LinkedHashMapIterator<>(this);
    }
}