package adtImplementation;

import adtInterfaces.MapInterface;
import adtInterfaces.Set;

import java.util.Iterator;


public class LinkedHashMap<K, V> implements MapInterface<K,V>, Iterable<MapInterface.Entry<K, V>>
{
    private Entry<K,V>[] entryBuckets;
    private static final int DEFAULT_BUCKET_QTY = 16;
    private static final int DEFAULT_POWER = 4;
    private int power; // 2 ^ 4 = 16 buckets
    private static final double LOAD_FACTOR = 0.75;
    private int totalEntries;
    private Entry<K,V> firstEntry;
    private Entry<K,V> lastEntry;


    public LinkedHashMap()
    {
        this.entryBuckets = new Entry[DEFAULT_BUCKET_QTY];
        this.power = DEFAULT_POWER;
        this.totalEntries = 0;
        this.firstEntry = null;
        this.lastEntry = null;
    }


    private LinkedHashMap(int newSize)
    {
        this.entryBuckets = new Entry[newSize];
        this.power = DEFAULT_POWER;
        this.totalEntries = 0;
        this.firstEntry = null;
        this.lastEntry = null;
    }


    public V put(K key, V value)
    {
        int bucketIndex = convertKeyToBucketIndex(key);
        Entry entry = entryBuckets[bucketIndex];
        Entry<K,V> newEntry = new Entry<K,V>(key, value);

        if (collisionHappen(bucketIndex))
        {
            Entry existingEntry = entry.searchEntry(key);
            if (existingEntry == null) // new entry
            {
                entry.appendCollidedEntry(newEntry);
                updateSequence(newEntry);
                this.totalEntries++;
            }
            else { // old entry = > update value
                existingEntry.value = value;
            }
        }
        else // np collision
        {
            entryBuckets[bucketIndex] = newEntry;
            updateSequence(newEntry);
            this.totalEntries++;
        }

        if (bucketsHaveTooMuchEntries()) {
            rehash();
        }
        return value;
    }

    public V put(Entry<K,V> newEntry)
    {
        int bucketIndex = convertKeyToBucketIndex(newEntry.key);
        Entry<K,V>bucket = entryBuckets[bucketIndex];
        if (collisionHappen(bucketIndex)) {
            handleCollision(newEntry, bucket);
        }
        else // np collision
        {
            entryBuckets[bucketIndex] = newEntry;
            updateSequence(newEntry);
            this.totalEntries++;
        }
        if (bucketsHaveTooMuchEntries()) {
            rehash();
        }
        return newEntry.getValue();
    }


    public void handleCollision(Entry<K, V> newEntry, Entry<K, V> bucket)
    {
        Entry<K, V> existingEntry = bucket.searchEntry(newEntry.getKey());
        if (existingEntry == null) // new entry should added
        {
            bucket.appendCollidedEntry(newEntry);
            updateSequence(newEntry);
            this.totalEntries++;
        }
        else { // overwrite old entry
            existingEntry.value = newEntry.getValue();
        }
    }


    public V get(K k)
    {
        Entry<K,V> bucket = getBucketFromKey(k);
        if (bucket != null)
        {
            if (bucket.getKey() == k)
                return bucket.getValue();
            else
                return bucket.searchEntry(k).value;
        }
        return null;
    }


    public int size() {
        return totalEntries;
    }


    public boolean containsKey(K k)
    {
        Entry<K,V> entry = getBucketFromKey(k);
        if (entry != null)
        {
            Entry<K, V> result = entry.searchEntry(k);

            // active
            System.out.println(result.getKey());

            return result != null;
        }
        return false;
    }


    public void clear()
    {
        this.totalEntries =0;
        this.entryBuckets = new Entry[DEFAULT_BUCKET_QTY];
        this.firstEntry = null;
        this.power = 4;
    }


    public boolean containsValue(V Value)
    {
        Entry<K,V> next = null;
        for (int i = 0 ; i < totalEntries && (next.forwardSequnce != null || i == 0) ; i++)
        {
            if (i == 0)
                next = firstEntry;
            else
                next = next.forwardSequnce;
        }
        return false;
    }


    public boolean isEmpty() {
        return totalEntries == 0;
    }


    public Set keySet() {
        return null;
    }

    @Override
    public void putAll() {

    }

    public void putAll(MapInterface<K,V> map) {

    }


    public V remove(K key)
    {
        Entry<K,V> bucket = getBucketFromKey(key);
        if (bucket != null)
        {
            Entry<K,V> target = bucket.searchEntry(key);
            if (target != null)
            {
                cutAndJoin(target);
                removeFromBucket(bucket, target, convertKeyToBucketIndex(key));
                totalEntries--;
                return target.value;
            }
        }
        return null;
    }

    @Override
    public V[] values() {
        return null;
    }


    private void cutAndJoin(Entry<K, V> entry)
    {
        Entry before = entry.backwardSequence;
        Entry after = entry.forwardSequnce;
        if (before == null) {    // remove first entry
            firstEntry = entry.forwardSequnce;
        }
        else if (after == null) { // remove last entry
            before.addforwardLink(null);
            lastEntry = before;
        }
        else {
            before.addforwardLink(after);
        }
    }


    private void removeFromBucket(Entry<K, V> bucket, Entry<K, V> toRemove, int buketIndex)
    {
        if (toRemove == bucket){ // first
            entryBuckets[buketIndex] = bucket.collidedEntry;
        } else if (toRemove.forwardSequnce == null) { // last
            entryBuckets[buketIndex] = null;
        } else{ // middle
            removeMiddlePartOfCollision(bucket, toRemove);
        }
    }


    private void removeMiddlePartOfCollision(Entry<K, V> bucket, Entry<K, V> toRemove)
    {
        Entry[] state = new Entry[]{null, bucket}; // [previous, current]
        for (int i = 0 ; i < bucket.totalEntry ; i++)
        {
            if (state[1] == toRemove) {
                state[0].collidedEntry = state[1].collidedEntry;
                return;
            }
            state[0] = state[1];
            state[1] = state[1].collidedEntry;
        }
    }


    private Entry getBucketFromKey(K key)
    {
        int bucketIndex = convertKeyToBucketIndex(key);
        Entry entry = entryBuckets[bucketIndex];
        return entry;
    }


    private int convertKeyToBucketIndex(K key){
        return convertHashCodeToBucketIndex(key.hashCode());
    }


    private int convertHashCodeToBucketIndex(int hashcode) {
        return hashcode % entryBuckets.length;
    }


    private boolean collisionHappen(int bucketIndex){
        return  entryBuckets[bucketIndex] != null;
    }


    private boolean bucketsHaveTooMuchEntries() {

        // bug
        // System.out.println(totalEntries);
        // System.out.println(entryQtyLimit());

        return totalEntries > entryQtyLimit();
    }


    private int entryQtyLimit(){
        return (int) (entryBuckets.length * LOAD_FACTOR);
    }


    private void updateSequence(Entry<K, V> entry){
        if (totalEntries == 0)
        {
            this.firstEntry = entry;
            this.lastEntry = entry;
        }
        else
        {

            //bug
            //System.out.println("Linked " + lastEntry.getKey() + " to " + entry.getKey());


            entry.addbackwardLink(lastEntry);
            lastEntry.addforwardLink(entry);
            this.lastEntry = entry;
        }
    }


    public Iterator<MapInterface.Entry<K, V>> iterator() {
        return new LinkedHashMapIterator<K,V>(this);
    }


    private static class Entry<K, V> implements MapInterface.Entry<K,V>
    {
        private K key;
        private V value;
        private Entry<K,V> collidedEntry;
        private Entry<K,V> lastEntry;
        private int totalEntry;
        Entry forwardSequnce;
        Entry backwardSequence;


        public Entry(K key, V value)
        {
            this.key = key;
            this.value = value;
            this.lastEntry = this;
            this.collidedEntry = null;
            this.totalEntry = 1;
            this.forwardSequnce = null;
            this.backwardSequence = null;
        }


        public Entry<K,V>[] toArray()
        {
            Entry<K,V>[] entryChain = new Entry[totalEntry];
            Entry<K,V> nextEntry = this;

            for(int i = 0 ; i < totalEntry ; i++){
                entryChain[i] = nextEntry;
                nextEntry = nextEntry.collidedEntry;
            }
            return entryChain;
        }


        public Entry<K,V> searchEntry(K key)
        {
            Entry result = null;
            Entry[] entry = new Entry[]{this, this.collidedEntry};
            for (int i = 0 ; i < totalEntry ; i++){
                if (entry[0].getKey() == key){
                    return entry[0];
                }
                entry[0] = entry[1];
                entry[1] = entry[1] == null? null: entry[1].collidedEntry;
            }
            return result;
        }


        public void appendCollidedEntry(Entry<K,V> entry)
        {
            this.lastEntry.collidedEntry =entry;
            this.lastEntry = entry;
            this.totalEntry += 1;
        }


        public K getKey() {
            return key;
        }


        public V getValue() {
            return value;
        }


        public void addforwardLink(Entry<K,V> entry) {
            this.forwardSequnce = entry;
        }


        public void addbackwardLink(Entry<K,V> entry){
            this.backwardSequence = entry;
        }


        public Entry getForwardSequnce() {
            return forwardSequnce;
        }

        public void setForwardSequnce(Entry forwardSequnce) {
            this.forwardSequnce = forwardSequnce;
        }
    }


    // region : utility method
    private void rehash()
    {
        LinkedHashMap<K,V> newMap = new LinkedHashMap<K,V>((int)Math.pow(2, ++this.power));
        for (MapInterface.Entry<K, V> entry : this) {
            newMap.put((Entry<K, V>) entry);
        }
        this.entryBuckets = newMap.entryBuckets;
    }
    // endregion : utility method


    public String toString()
    {
        String map = "{";
        for (MapInterface.Entry<K,V> entry : this){
            map = map + entry.getKey() + ":" + entry.getValue() + ", ";
        }
        return map+"}";
    }

    public static void main(String[] args)
    {
        LinkedHashMap<String,Integer> test = new LinkedHashMap<String, Integer>();
        test.put("a",1);
        test.put("b",2);
        test.put("c",2);
        test.put("d",2);
        test.put("e",7);
        test.put("f",8);

        test.put("g",2);
        test.put("h",2);
        test.put("i",2);
        test.put("j",2);
        test.put("k",2);
        test.put("l",2);
        test.put("m",2);
        test.put("n",2);
        test.put("o",2);
        test.put("p",2);
        test.put("q",2);
        test.put("r",2);
        test.put("s",2);
        test.put("s",4);

        test.put("t",2);
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

    public Entry<K, V> getFirstEntry() {
        return firstEntry;
    }


    public static class LinkedHashMapIterator<K,V> implements Iterator<MapInterface.Entry<K,V>>
    {
        LinkedHashMap.Entry<K, V> current;

        public LinkedHashMapIterator(LinkedHashMap<K, V> linkedHashMap) {
            current = linkedHashMap.getFirstEntry();
        }


        public boolean hasNext() {
            return current != null;
        }

        public final MapInterface.Entry next()
        {
            Entry data = current;
            current = current.getForwardSequnce();
            return data;
        }
    }

}