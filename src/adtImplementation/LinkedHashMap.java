


package adtImplementation;


import UtilityClasses.CMD;
import adtInterfaces.MapInterface;

import java.util.Iterator;


public class LinkedHashMap<K, V> implements MapInterface<K, V>, Iterable<MapInterface.Entry<K, V>> {
    private Entry<K, V>[] entryBuckets;
    private static final int DEFAULT_BUCKET_QTY = 16;
    private static final int DEFAULT_POWER = 4;
    private static final double LOAD_FACTOR = 0.75;
    private int power; // 2 ^ 4 = 16 buckets
    private int totalEntries;
    private Entry<K, V> firstEntry;
    private Entry<K, V> lastEntry; // omit the requirement of searching when what to extend chain


    public LinkedHashMap() {
        initializeBuckets();
        this.power = DEFAULT_POWER;
        this.totalEntries = 0;
        this.firstEntry = null;
        this.lastEntry = null;
    }

    private LinkedHashMap(int newSize) {
        initializeBuckets(newSize);
        // power field will update in rehash method
        this.totalEntries = 0;
        this.firstEntry = null;
        this.lastEntry = null;
    }

    public V put(K key, V value) {
        if (collisionHappen(key)) {
            value = handleCollision(key, value, nonOverwrite);
        } else {
            addNewEntry(key, value, null);
        }
        rehashIfTooMuchEntries();
        return value;
    }

    public V put(MapInterface.Entry<K, V> entry) {
        V value = entry.getValue();
        if (collisionHappen(entry.getKey())) {
            value = handleCollision(entry.getKey(), entry.getValue(), nonOverwrite);
        } else {
            addNewEntry(entry.getKey(), value, null);
        }
        rehashIfTooMuchEntries();
        return value;
    }

    public V putOverwrite(MapInterface.Entry<K, V> entry) {
        V value = entry.getValue();
        if (collisionHappen(entry.getKey())) {
            value = handleCollision(entry.getKey(), entry.getValue(), overwrite);
        } else {
            addForwardSequence((Entry<K, V>) entry);
            this.totalEntries++;
        }
        rehashIfTooMuchEntries();
        return value;
    }

    public V putOverwrite(K key, V value) {
        if (collisionHappen(key)) {
            value = handleCollision(key, value, overwrite);
        } else {
            addNewEntry(key, value, null);
        }
        rehashIfTooMuchEntries();
        return value;
    }

    // reason why use try catch is fasten access, no need check null condition
    public V get(K k) {
        try {
            if (keyMatch(bucket(k), k))
                return bucket(k).getValue(); // O(1) complexity achieve :)
            else
                return bucket(k).searchCollision(k).getValue(); // no O(1) due to collision :(
        } catch (NullPointerException e) {
            return null;
        }
    }

    public int size() {
        return totalEntries;
    }

    public boolean containsKey(K k) {
        if (bucket(k) != null) {
            Entry<K, V> result = bucket(k).searchCollision(k);
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
        LinkedHashMap<K, Object> castedMap = (LinkedHashMap<K, Object>) this;
        return new LinkedHashSet<>(castedMap);
    }

    public int putAll(MapInterface<K, V> map) {
        int addedQty = 0;
        for (MapInterface.Entry<K, V> entry : map) {
            V value = this.put(entry.getKey(), entry.getValue());
            addedQty = (value == null) ? addedQty : addedQty + 1;
        }
        return addedQty;
    }

    public V remove(K key) {
        if (bucket(key) != null) {       // have bucket associated with key
            removeFromLinkedChainIfExist(key); // using hash, no iteration required
            Entry<K, V> removed = removeFromBucket(key);
            if (removed != null) {      // successfully removed
                --totalEntries;
                return removed.getValue();
            }
        }
        return null;
    }

    public V[] values() {
        if (totalEntries > 0) {
            @SuppressWarnings("unchecked")
            V[] valuesArr = (V[]) new Object[totalEntries];
            int counter = 0;
            for (MapInterface.Entry<K, V> entry : this) {
                valuesArr[counter++] = entry.getValue();
            }
            return valuesArr;
        }
        return null;
    }

    public boolean containsValue(V value) {
        Entry<K, V> current = firstEntry;

        if (current == null)
            return false;

        while (current != null && !valueMatch(current, value))
            current = current.next;

        return current != null && valueMatch(current, value);
    }

    public Iterator<MapInterface.Entry<K, V>> iterator() {
        return new LinkedHashMapIterator<>(this);
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

    public Entry<K, V> getFirstEntry() {
        return firstEntry;
    }


    /**
     * Static classes included
     * 1. iterator class
     * 2. Entry class
     */

    private static class Entry<K, V> implements MapInterface.Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> collidedEntry;
        Entry<K, V> next;
        Entry<K, V> before;

        public Entry(K key, V value, Entry<K, V> collidedEntry) {
            this.key = key;
            this.value = value;
            this.collidedEntry = collidedEntry;
            this.next = null;
            this.before = null;
        }

        public Entry<K, V> searchCollision(K key) {
            Entry<K, V> current = this;

            while (current != null && !keyMatch(current, key))
                current = current.collidedEntry;

            return current;
        }

        public boolean keyMatch(Entry<K, V> entry, K key) {
            return
                    entry.getKey().hashCode() == key.hashCode() &&
                            (key.equals(entry.getKey()) || entry.getKey().hashCode() == key.hashCode());
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setNext(Entry<K, V> entry) {
            this.next = entry;
        }

        public void setBefore(Entry<K, V> entry) {
            this.before = entry;
        }

        public Entry<K, V> getNext() {
            return next;
        }

        public void clearCollisionLink() {
            this.collidedEntry = null;
        }
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


    /**
     * Below are the region for utility methods
     */
    private boolean keyMatch(Entry<K, V> entry, K key) {
        return entry.getKey().hashCode() == key.hashCode() &&
                (key.equals(entry.getKey()) || entry.getKey().hashCode() == key.hashCode());
    }

    private V handleCollision(K key, V value, HandlingMode<K, V> handle) {
        Entry<K, V> existingEntry = bucket(key).searchCollision(key);
        if (existingEntry == null) {
            return addNewEntry(key, value, bucket(key));
        } else {
            return handle.settleCollision(existingEntry, value);
        }
    }

    private V addNewEntry(K key, V value, Entry<K, V> collision) {
        entryBuckets[bucketIndex(key)] = new Entry<>(key, value, collision);
        addForwardSequence(entryBuckets[bucketIndex(key)]);
        this.totalEntries++;
        return value;
    }

    private void rehash() {
        LinkedHashMap<K, V> newMap = new LinkedHashMap<>((int) Math.pow(2, ++this.power));
        for (MapInterface.Entry<K, V> entry : this) {
            LinkedHashMap.Entry<K, V> tempEntry = (LinkedHashMap.Entry<K, V>) entry;
            tempEntry.clearCollisionLink();
            newMap.put(entry);
        }
        this.entryBuckets = newMap.entryBuckets;
    }

    private boolean valueMatch(Entry<K, V> entry, V value) {
        return entry.getValue() == value || entry.getValue().equals(value);
    }

    private void rehashIfTooMuchEntries() {
        if (bucketsHaveTooMuchEntries()) {
            rehash();
        }
    }

    private int bucketIndex(K key) {
        return (key.hashCode() & 0x7fffffff % this.entryBuckets.length);
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
            entry.setBefore(lastEntry);
            lastEntry.setNext(entry);
            this.lastEntry = entry;
        }
    }


    private Entry<K, V> bucket(K key) {
        return entryBuckets[bucketIndex(key)];
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

    private void removeFromLinkedChainIfExist(K key) {
        if (keyMatch(firstEntry, key))
            removeFirstEntry();
        else if (keyMatch(lastEntry, key))
            removeLastEntry();
        else // confirm have at least 3 element
            removeFromMiddlePartOfChainIfKeyExist(key);
    }

    private void removeFromMiddlePartOfChainIfKeyExist(K key) {
        Entry<K, V> entry = bucket(key).searchCollision(key);
        if (entry != null) {
            entry.before.setNext(entry.next);
            entry.next.setBefore(entry.before);
        }
    }

    private void removeFirstEntry() {
        switch (totalEntries) {
            case 1 -> {
                firstEntry = null;
                lastEntry = null;
            }
            case 2 -> {
                firstEntry = lastEntry;
                firstEntry.setBefore(null);
            }
            default -> {
                firstEntry = firstEntry.next;
                firstEntry.setBefore(null);
            }
        }
    }

    private void removeLastEntry() {
        switch (totalEntries) {
            case 1 -> {
                firstEntry = null;
                lastEntry = null;
            }
            case 2 -> {
                firstEntry.setNext(null);
                lastEntry = firstEntry;
            }
            default -> {
                lastEntry = lastEntry.before;
                lastEntry.setNext(null);
            }
        }
    }

    private Entry<K, V> removeFromBucket(K key) {
        if (keyMatch(bucket(key), key))
            return alterBucket(key);
        else
            return removeFromCollisionChain(key);
    }

    private Entry<K, V> alterBucket(K key) {
        Entry<K, V> removed = entryBuckets[bucketIndex(key)];
        entryBuckets[bucketIndex(key)] = bucket(key).collidedEntry;
        return removed;
    }

    private Entry<K, V> removeFromCollisionChain(K key) {
        Entry<K, V> current = entryBuckets[bucketIndex(key)], previous = null;
        while (current != null && !keyMatch(current, key)) {
            previous = current;
            current = current.next;
        }
        if (previous != null) {
            previous.collidedEntry = (current == null) ? null : current.next;
            return current;
        }
        return null;
    }

    /**
     * Below is functional interface(aka lambdas)
     */
    @FunctionalInterface
    private interface HandlingMode<K, V> {
        V settleCollision(LinkedHashMap.Entry<K, V> entry, V value);
    }

    private final HandlingMode<K, V> overwrite = (entry, value) -> {
        entry.value = value;
        return value;
    };
    private final HandlingMode<K, V> nonOverwrite = (entry, value) -> {
        return null;
    };
    // endregion : utility method


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
        test.clear();
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
        test.put("h", 2);
        test.put("i", 2);
        test.put("j", 2);
        test.put("g", 2);
        test.put("h", 2);
        test.put("i", 2);
        test.put("j", 2);
        test.put("n", 2);
        test.put("o", 2);
        test.put("p", 2);
        test.put("q", 2);
        test.put("r", 2);
        test.put("s", 2);
        test.put("s", 4);
        test.put("g", 2);
        test.put("h", 2);
        test.put("i", 2);
        test.put("j", 2);
        test.put("k", 2);
        test.put("l", 2);
        test.put("m", 2);
        test.put("null", null);
        test.put("o", 2);
        test.put("p", 2);
        test.put("q", 2);

        // hash--
        test.clear();
        test.put("r", 1);
        test.put("s", 2);
        test.put("s", 4);
        test.put("g", 2);
        test.put("h", 2);
        test.put("i", 6);
        test.put("j", 2);
        System.out.println(test.get("r"));
        System.out.println(test.get("s"));
        System.out.println(test.get("g"));
        System.out.println(test.get("i"));
        System.out.println(test);

        test.put("k", 2);
        test.put("l", 2);
        test.put("m", 2);
        test.put("n", 2);
        test.remove("g");
        test.remove("h");
        test.remove("i");
        test.remove("j");
        test.remove("k");
        test.remove("l");
        test.remove("m");
        test.remove("n");
        test.remove("o");
        test.remove("p");
        test.remove("q");
        test.remove("r");
        test.remove("s");
        test.remove("s");
        test.remove("g");
        test.remove("h");
        test.remove("i");
        test.remove("j");
        test.remove("k");
        test.remove("l");
        test.remove("m");
        test.remove("n");
        test.remove("o");
        test.remove("p");
        test.remove("q");
        test.remove("r");
        test.remove("s");
        test.remove("s");
        test.remove("g");
        test.remove("h");
        test.remove("i");
        test.remove("j");
        test.remove("k");
        test.remove("l");
        test.remove("m");
        test.remove("n");
        test.remove("o");
        test.clear();
        test.put("o", 2);
        test.put("p", 2);
        test.put("q", 2);
        System.out.println(test);
        System.out.println(test.containsValue(2));
        System.out.println(test.containsValue(4));
        test.containsValue(759);
        test.containsValue(366);
        test.containsValue(054);


        test.containsValue(80);
        test.containsValue(584);
        test.containsValue(376);
        test.containsValue(465);
        test.containsValue(872);
        test.containsValue(865);
        test.containsValue(568);
        test.containsValue(253);
        test.containsValue(119);
        test.containsValue(124);
        test.containsValue(453);
        test.containsValue(513);
        test.containsValue(536);
        test.containsValue(853);
        test.containsValue(439);
        test.containsValue(873);
        test.containsValue(245);
        test.containsValue(463);
        test.containsValue(111);
        test.containsValue(835);
        test.containsValue(964);
        test.containsValue(446);
        test.containsValue(869);
        test.containsValue(770);
        test.containsValue(942);
        test.containsValue(429);
        test.containsValue(060);
        test.containsValue(854);
        test.containsValue(290);
        test.containsValue(317);
        test.containsValue(598);
        test.containsValue(208);
        test.containsValue(890);
        test.containsValue(115);
        test.containsValue(351);
        test.containsValue(549);
        test.containsValue(010);
        test.containsValue(394);
        test.containsValue(426);
        test.containsValue(265);
        test.containsValue(723);
        test.containsValue(890);
        test.containsValue(180);
        test.containsValue(681);
        test.containsValue(666);
        test.containsValue(812);
        test.containsValue(601);
        test.containsValue(392);
        test.containsValue(847);
        test.containsValue(796);
        test.containsValue(79);
        test.containsValue(070);
        test.containsValue(212);
        test.containsValue(439);
        test.containsValue(032);
        test.containsValue(250);
        test.containsValue(28);
        test.containsValue(705);
        test.containsValue(440);
        test.containsValue(888);
        test.containsValue(332);
        test.containsValue(98);
        test.containsValue(645);
        test.containsValue(384);
        test.containsValue(258);
        test.containsValue(575);
        test.containsValue(263);
        test.containsValue(746);
        test.containsValue(8);
        test.containsValue(723);
        test.containsValue(031);
        test.containsValue(401);
        test.containsValue(563);
        test.containsValue(301);
        test.containsValue(401);
        test.containsValue(599);
        test.containsValue(805);
        test.containsValue(041);
        test.containsValue(761);
        test.containsValue(613);
        test.containsValue(735);
        test.containsValue(875);
        test.containsValue(172);
        test.containsValue(956);
        test.containsValue(951);
        test.containsValue(424);
        test.containsValue(109);
        test.containsValue(779);
        test.containsValue(493);
        test.containsValue(984);
        test.containsValue(208);
        test.containsValue(973);
        test.containsValue(804);
        test.containsValue(476);
        test.containsValue(389);
        System.out.println(test.put("r", 2));
        System.out.println(test.put("s", 2));
        System.out.println(test.putOverwrite("s", 4));
        System.out.println(test.put("g", 2));
        System.out.println(test.put("h", 2));
        System.out.println(test.put("i", 2));
        System.out.println(test.put("j", 2));
        System.out.println(test.put("k", 2));
        System.out.println(test.put("l", 2));
        System.out.println(test.put("m", 2));
        System.out.println(test.put("n", 2));
        System.out.println(test.put("o", 2));
        System.out.println(test.put("p", 2));
        System.out.println(test.put("q", 2));
        System.out.println(test.put("r", 2));
        System.out.println(test.put("s", 2));
        System.out.println(test.put("s", 4));
        System.out.println(test.put("t", 2));
        System.out.println(test.put("m", 2));
        System.out.println(test.put("n", 2));
        System.out.println(test.put("o", 2));
        System.out.println(test.put("p", 2));
        System.out.println(test.put("q", 2));
        System.out.println(test.put("r", 2));
        System.out.println(test.put("s", 2));
        System.out.println(test.put("s", 4));
        System.out.println(test.put("g", 2));
        System.out.println(test.put("h", 2));
        System.out.println(test.put("i", 2));
        System.out.println(test.put("j", 2));
        System.out.println(test.put("k", 2));
        System.out.println(test.put("l", 2));
        System.out.println(test.put("m", 2));
        test.put("es6ky9jTpwwcS86iPVmp", 897);
        test.put("scfxbjJByZ26X4sHfgAP", 630);
        test.put("z_88uJAfJlpjQF7oNbsf", 9);
        test.put("NEZHC5asYIuLCsLrVisT", 88);
        test.put("qqyHXM3W6jYRmxiq4PCa", 61);
        test.put("HSh_qdBpGTvqv5tr39U6", 7);
        test.put("jBh9WepUbCSNHJzixS7V", 69);
        test.put("o9hsoa0n_gKpAfKHsIz9", 885);
        test.put("oIntRwpJpDhy08DFfaWw", 91);
        test.put("QngdDV8ZnkCUU6qN1NZ1", 6);
        test.put("UJaIUrnkOB8LAUPSMGXh", 54);
        test.put("7ruTbQbkpLtE6WW0RjAA", 371);
        test.put("pzpEhMoCg1GkVDm3Zfqj", 4);
        test.put("XXkdKaZIAHYl5q7Mt6Tf", 27);
        test.put("oEcH8vymqIzo_RrhjsNh", 89);
        test.put("OTHUCMGFUEv3LJFCRaFP", 3);
        test.put("Rhv3TFJjE4ULrhjtoZgE", 99);
        test.put("dBYI9DGWkG_EbYExs_6l", 51);
        test.put("mAzmuaVW37LTlXyGK6BQ", 43);
        test.put("NOu6MSUAXb7ztBiELQp0", 4);
        test.put("3KCx4hzXQ5cOWt5aaWd1", 34);
        test.put("Hh2RR_8HQ1jtPFdNSAxa", 0);
        test.put("fxW0JfwvSAjvkghcagNL", 656);
        test.put("nO0Vq6gx_LtxBLrXNQTg", 69);
        test.put("ozPegOLtEgpPzOtWcPci", 0);
        test.put("J1Ah7SIRKUYosMH3EL38", 26);
        test.put("nclazFMd_QPfp_zPFBUr", 74);
        test.put("AmFs3Nqcf3vBMocIVi_M", 143);
        test.put("YKR1auQhnIAtEy4iDkvr", 687);
        test.put("SXYCjFXPAMXxMdQl_YY4", 242);
        test.put("t1jSIX0axpCD2t5s2LgL", 95);
        test.put("9mlFlcacDaqe7BSpPAtb", 3);
        test.put("BKeXj2XJ1iCB9uVoHnb6", 39);
        test.put("v37akI40XJBOv9Yx0M0O", 0);
        test.put("zxEwQhhIstbBslx7VlmN", 34);
        test.put("ksqEXX7raPXM8Q5AzcJG", 558);
        test.put("s50TrAo4W0wEQxeCCuk6", 8);
        test.put("UG2g8vGl_ArpENJ0AL9N", 589);
        test.put("dAOnHMbYEoMShlE4NTIh", 31);
        test.put("uh5VwtpRBA4EjJpAisnF", 65);
        test.put("GtX0cIbKt3mXNxs4obOc", 80);
        test.put("tLEbiC2tjf5PZhTp003k", 0);
        test.put("d_tdqPaKvqa0z3Nj7FlT", 9);
        test.put("RovJnxiQBxrRyTSmRzLv", 00);
        test.put("KahamedyFzdeFf584PuN", 698);
        test.put("8f5JXV19zNpO4SQBmN98", 67);
        test.put("qGlsOHoxKXZlGmUw8oZA", 6);
        test.put("c9HrEx9apw7K6ugE74hf", 71);
        test.put("Zww6se4VZr0tuKA5xSu_", 686);
        test.put("NWtmYbnEnpzMo8OQzP6G", 451);
        test.put("24ceLrxlPJZPivmphbSi", 5);
        test.put("AiVlXooEOtZ2jU9AX_GX", 8);
        test.put("ElSx9Wh36FKtJgW1PIxX", 88);
        test.put("MYTVxOKe0nyV2HkCrC2r", 35);
        test.put("9hGgeD54siCVn_DUqh3q", 786);
        test.put("y0gTkS_E4cTCqyl9fuJc", 4);
        test.put("L4Z2m_HlNJ2eUqr8g72m", 31);
        test.put("rr0bI_fzjsaGW1V2CH3m", 421);
        test.put("307jWKzCMmUXyJJIPX58", 3);
        test.put("_OVl1eQCtpcXoaFDX55n", 785);
        test.put("aXi2oOAhF8g6IMgZtT0F", 063);
        test.put("6N0jbN8WSTKIsRg7p702", 18);
        test.put("OyCHJTtiZGWVGYCsAVpF", 7);
        test.put("M3COww3K8WxsNRGAU2NV", 7);
        test.put("WJCNTv6gMvK1RyiJZsr9", 7);
        test.put("r69tlYMqc0vmyJUdQHyO", 98);
        test.put("Dz7fkT83ccb9qHBhEFUP", 6);
        test.put("Uscpl4TltGpQobqoj66V", 07);
        test.put("Y_qFx2U72n4sJaGrYy8n", 1);
        test.put("lRXViI2SaB2m9XR8v0kW", 8);
        test.put("mGyG6OFYdEC7oh2b9SOc", 373);
        test.put("NmJEYkzumMD_IOtcMkw_", 11);
        test.put("ijRao8GUTy8xZT3khTKK", 4);
        test.put("xlwM2ivTUxjmfXk4WdTS", 76);
        test.put("FcHBsE433k0RMbCnckoV", 02);
        test.put("7A3cNzsOE5vdAKrpRSlp", 8);
        test.put("UqaANkiqkPAFCPQMeQJV", 27);
        test.put("kcr4DUqQ5l_sthVnzJWW", 439);
        test.put("82vrsodVLOdNRFARklWH", 22);
        test.put("72eIQcHAyp0wh8E5vGmF", 114);
        test.put("aLkj7JlqgyQNSMGAOz7W", 64);
        test.put("MjPx9anYZDgxYO2W2Tg5", 5);
        test.put("UmIv4JjJaLme3Wgg5va9", 85);
        test.put("6f7hhpHBY0ubSCISADq0", 3);
        test.put("Seta_mlFjMvK3MMnSXav", 297);
        test.put("w3__wqRnazACQJ0rXJoJ", 28);
        test.put("Ktiee4zB0UeoFLldpPOg", 39);
        test.put("3HEP4B6tCySpvGhHu2gm", 11);
        test.get("9rH");
        test.get("0j3");
        test.get("yew");
        test.get("1Q0");
        test.get("hQb");
        test.get("X5A");
        test.get("fpa");
        test.get("wE3");
        test.get("v45");
        test.get("ZL6");
        test.get("jhz");
        test.get("6Rn");
        CMD.pauseWithCustomScript("stop>>");
        System.out.println(test.get("MLJ"));
        System.out.println(test.get("EkK"));
        System.out.println(test.get("l5v"));
        System.out.println(test.get("w2H"));
        CMD.pauseWithCustomScript("stop>>");

        test.get("EkK");
        test.get("l5v");
        test.get("Wf8");
        test.get("w2H");
        test.get("eSX");
        test.get("naf");
        test.get("roF");
        test.get("lUy");
        test.get("Ouz");
        test.get("EdH");
        test.get("qde");
        test.get("OJn");
        test.get("_wY");
        test.get("h8t");
        test.get("eH1");
        test.get("xmF");
        test.get("ui2");
        test.get("d9e");
        test.get("sfT");
        test.get("yaV");
        test.get("nE3");
        test.get("v4O");
        test.get("o6X");
        test.get("9v0");
        test.get("uM1");
        test.get("RRg");
        test.get("tJa");
        test.get("ZY_");
        test.get("g5F");
        test.get("0wU");
        test.get("Enq");
        test.get("9xs");
        test.get("7Uy");
        test.get("dv1");
        test.get("wye");
        test.get("wIB");
        test.get("JSu");
        test.get("4WH");
        test.get("lLg");
        test.get("5iQ");
        test.get("FYL");
        test.get("3Cu");
        test.get(null);
        test.get("yZ2");
        test.get("9lV");
        test.get("1YZ");
        test.get("o0q");
        test.get("WpS");
        test.get("r59");
        test.get("HrI");
        test.get("Ovx");
        test.get("OyQ");
        test.get("7XZ");
        test.get("9Xj");
        test.get("t3d");
        test.get("XWj");
        test.get("ab8");
        test.get("sQe");
        test.get("pLV");
        test.get("84T");
        test.get("x9o");
        test.get("y7K");
        test.get("AfV");
        test.get("G8j");
        test.get("Vch");
        test.get("HN6");
        test.get("Om8");
        test.get("mqB");
        test.get("tvv");
        test.get("0aS");
        test.get("65U");
        test.get("NYU");
        test.get("I27");
        test.get("7Qd");
        test.get("gdi");
        test.get("DeR");
        test.get("x89");
        test.get("hdw");
        test.get("LTJ");
        test.get("4V2");
        test.get("1GK");
        test.get("5rP");
        test.get("9df");
        test.get("Daj");
        test.get("Kf4");
        test.get("agR");
        test.get("UWz");
        test.put("wCPxQPdUIu9tg2qo6FWk", 424);
        test.put("oPminY7rdBC4YIWBnF1L", 7);
        test.put("89oYHykaPDEz_WHaFQQy", 741);
        test.put("PGqzkmz4GAlHIBt9xqBK", 0);
        test.put("yvKWlxGhFcTXJVuPn4V5", 021);
        test.put("hPuMtQPrvNb5_pydsK7Z", 25);
        test.put("hrnGxCEvQiv42uPrR2ef", 33);
        test.put("fEJIlz48UBS0mtx3t0V1", 43);
        test.put("j7PnZTS4JovLn6zB6uT6", 6);
        test.put("_KwIZX8hXjPoo3UF5WCq", 9);
        test.put("cTsQzYYCCz1RfZlZwmwA", 003);
        test.put("9E0p0ZQhxZx5_WI09WGo", 293);
        test.put("n", 2);
        test.put("o", 2);
        test.put("p", 2);

        test.put("q", 2);
        test.put("r", 2);
        test.put("s", 2);
        test.put("s1", 4);
        test.put("t2", 2);
        test.put("m3", 2);

        test.put("n4", 2);
        test.put("o5", 2);
        test.put("p6", 2);
        test.put("q7", 2);
        test.put("r8", 2);
        test.put("s9", 2);
        test.put("s1001", 4);
        test.put("g1", 2);
        test.put("has", 2);

        test.put("i23", 2);
        test.put("asdj", 2);
        test.put("k3", 2);
        test.put("lafd", 2);
        test.put("dfhm", 2);
        test.put("nbzx", 2);
        test.put("sbngo", 2);
        test.put("sngp", 2);
        test.put("qscvn", 2);
        test.put("rvsn", 2);
        test.put("sqnv", 2);
        test.put("sqvnvv", 4);
        test.put("es6ky9jTpwwcS86iPVmp", 897);
        test.put("scfxbjJByZ26X4sHfgAP", 630);
        test.put("z_88uJAfJlpjQF7oNbsf", 9);
        test.put("NEZHC5asYIuLCsLrVisT", 88);
        test.put("qqyHXM3W6jYRmxiq4PCa", 61);
        test.put("HSh_qdBpGTvqv5tr39U6", 7);
        test.put("jBh9WepUbCSNHJzixS7V", 69);
        test.put("o9hsoa0n_gKpAfKHsIz9", 885);
        test.put("oIntRwpJpDhy08DFfaWw", 91);
        test.put("QngdDV8ZnkCUU6qN1NZ1", 6);
        test.put("UJaIUrnkOB8LAUPSMGXh", 54);
        test.put("7ruTbQbkpLtE6WW0RjAA", 371);
        test.put("pzpEhMoCg1GkVDm3Zfqj", 4);
        test.put("XXkdKaZIAHYl5q7Mt6Tf", 27);
        test.put("oEcH8vymqIzo_RrhjsNh", 89);
        test.put("OTHUCMGFUEv3LJFCRaFP", 3);
        test.put("Rhv3TFJjE4ULrhjtoZgE", 99);
        test.put("dBYI9DGWkG_EbYExs_6l", 51);
        test.put("mAzmuaVW37LTlXyGK6BQ", 43);
        test.put("NOu6MSUAXb7ztBiELQp0", 4);
        test.put("3KCx4hzXQ5cOWt5aaWd1", 34);
        test.put("Hh2RR_8HQ1jtPFdNSAxa", 0);
        test.put("fxW0JfwvSAjvkghcagNL", 656);
        test.put("nO0Vq6gx_LtxBLrXNQTg", 69);
        test.put("ozPegOLtEgpPzOtWcPci", 0);
        test.put("J1Ah7SIRKUYosMH3EL38", 26);
        test.put("nclazFMd_QPfp_zPFBUr", 74);
        test.put("AmFs3Nqcf3vBMocIVi_M", 143);
        test.put("YKR1auQhnIAtEy4iDkvr", 687);
        test.put("SXYCjFXPAMXxMdQl_YY4", 242);
        test.put("t1jSIX0axpCD2t5s2LgL", 95);
        test.put("9mlFlcacDaqe7BSpPAtb", 3);
        test.put("BKeXj2XJ1iCB9uVoHnb6", 39);
        test.put("v37akI40XJBOv9Yx0M0O", 0);
        test.put("zxEwQhhIstbBslx7VlmN", 34);
        test.put("ksqEXX7raPXM8Q5AzcJG", 558);
        test.put("s50TrAo4W0wEQxeCCuk6", 8);
        test.put("UG2g8vGl_ArpENJ0AL9N", 589);
        test.put("dAOnHMbYEoMShlE4NTIh", 31);
        test.put("uh5VwtpRBA4EjJpAisnF", 65);
        test.put("GtX0cIbKt3mXNxs4obOc", 80);
        test.put("tLEbiC2tjf5PZhTp003k", 0);
        test.put("d_tdqPaKvqa0z3Nj7FlT", 9);
        test.put("RovJnxiQBxrRyTSmRzLv", 00);
        test.put("KahamedyFzdeFf584PuN", 698);
        test.put("8f5JXV19zNpO4SQBmN98", 67);
        test.put("qGlsOHoxKXZlGmUw8oZA", 6);
        test.put("c9HrEx9apw7K6ugE74hf", 71);
        test.put("Zww6se4VZr0tuKA5xSu_", 686);
        test.put("NWtmYbnEnpzMo8OQzP6G", 451);
        test.put("24ceLrxlPJZPivmphbSi", 5);
        test.put("AiVlXooEOtZ2jU9AX_GX", 8);
        test.put("ElSx9Wh36FKtJgW1PIxX", 88);
        test.put("MYTVxOKe0nyV2HkCrC2r", 35);
        test.values();
        test.put("9hGgeD54siCVn_DUqh3q", 786);
        test.put("y0gTkS_E4cTCqyl9fuJc", 4);
        test.put("L4Z2m_HlNJ2eUqr8g72m", 31);
        test.put("rr0bI_fzjsaGW1V2CH3m", 421);
        test.put("307jWKzCMmUXyJJIPX58", 3);
        test.put("_OVl1eQCtpcXoaFDX55n", 785);
        test.put("aXi2oOAhF8g6IMgZtT0F", 063);
        test.put("6N0jbN8WSTKIsRg7p702", 18);
        test.put("OyCHJTtiZGWVGYCsAVpF", 7);
        test.put("M3COww3K8WxsNRGAU2NV", 7);
        test.put("WJCNTv6gMvK1RyiJZsr9", 7);
        test.put("r69tlYMqc0vmyJUdQHyO", 98);
        test.put("Dz7fkT83ccb9qHBhEFUP", 6);
        test.put("Uscpl4TltGpQobqoj66V", 07);
        test.put("Y_qFx2U72n4sJaGrYy8n", 1);
        test.put("lRXViI2SaB2m9XR8v0kW", 8);
        test.put("mGyG6OFYdEC7oh2b9SOc", 373);
        test.put("NmJEYkzumMD_IOtcMkw_", 11);
        test.put("ijRao8GUTy8xZT3khTKK", 4);
        test.put("xlwM2ivTUxjmfXk4WdTS", 76);
        test.put("FcHBsE433k0RMbCnckoV", 02);
        test.put("7A3cNzsOE5vdAKrpRSlp", 8);
        test.put("UqaANkiqkPAFCPQMeQJV", 27);
        test.put("kcr4DUqQ5l_sthVnzJWW", 439);
        test.put("82vrsodVLOdNRFARklWH", 22);
        test.put("72eIQcHAyp0wh8E5vGmF", 114);
        test.put("aLkj7JlqgyQNSMGAOz7W", 64);
        test.put("MjPx9anYZDgxYO2W2Tg5", 5);
        test.put("UmIv4JjJaLme3Wgg5va9", 85);
        test.put("6f7hhpHBY0ubSCISADq0", 3);
        test.put("Seta_mlFjMvK3MMnSXav", 297);
        test.put("w3__wqRnazACQJ0rXJoJ", 28);
        test.put("Ktiee4zB0UeoFLldpPOg", 39);
        test.put("3HEP4B6tCySpvGhHu2gm", 11);
        test.put("wCPxQPdUIu9tg2qo6FWk", 424);
        test.put("oPminY7rdBC4YIWBnF1L", 7);
        test.put("89oYHykaPDEz_WHaFQQy", 741);
        test.put("PGqzkmz4GAlHIBt9xqBK", 0);
        test.put("yvKWlxGhFcTXJVuPn4V5", 021);
        test.put("hPuMtQPrvNb5_pydsK7Z", 25);
        test.put("hrnGxCEvQiv42uPrR2ef", 33);
        test.put("fEJIlz48UBS0mtx3t0V1", 43);
        test.put("j7PnZTS4JovLn6zB6uT6", 6);
        test.put("_KwIZX8hXjPoo3UF5WCq", 9);
        test.put("cTsQzYYCCz1RfZlZwmwA", 003);
        test.put("es6ky9jTpwwcS86iPVmp", 897);
        test.put("scfxbjJByZ26X4sHfgAP", 630);
        test.put("z_88uJAfJlpjQF7oNbsf", 9);
        test.put("NEZHC5asYIuLCsLrVisT", 88);
        test.put("qqyHXM3W6jYRmxiq4PCa", 61);
        test.put("HSh_qdBpGTvqv5tr39U6", 7);
        test.put("jBh9WepUbCSNHJzixS7V", 69);
        test.put("o9hsoa0n_gKpAfKHsIz9", 885);
        test.put("oIntRwpJpDhy08DFfaWw", 91);
        test.put("QngdDV8ZnkCUU6qN1NZ1", 6);
        test.put("UJaIUrnkOB8LAUPSMGXh", 54);
        test.put("7ruTbQbkpLtE6WW0RjAA", 371);
        test.put("pzpEhMoCg1GkVDm3Zfqj", 4);
        test.put("XXkdKaZIAHYl5q7Mt6Tf", 27);
        test.put("oEcH8vymqIzo_RrhjsNh", 89);
        test.put("OTHUCMGFUEv3LJFCRaFP", 3);
        test.put("Rhv3TFJjE4ULrhjtoZgE", 99);
        test.put("dBYI9DGWkG_EbYExs_6l", 51);
        test.put("mAzmuaVW37LTlXyGK6BQ", 43);
        test.put("NOu6MSUAXb7ztBiELQp0", 4);
        test.put("3KCx4hzXQ5cOWt5aaWd1", 34);
        test.put("Hh2RR_8HQ1jtPFdNSAxa", 0);
        test.values();
        test.put("fxW0JfwvSAjvkghcagNL", 656);
        test.put("nO0Vq6gx_LtxBLrXNQTg", 69);
        test.put("ozPegOLtEgpPzOtWcPci", 0);
        test.put("J1Ah7SIRKUYosMH3EL38", 26);
        test.put("nclazFMd_QPfp_zPFBUr", 74);
        test.put("AmFs3Nqcf3vBMocIVi_M", 143);
        test.put("YKR1auQhnIAtEy4iDkvr", 687);
        test.put("SXYCjFXPAMXxMdQl_YY4", 242);
        test.put("t1jSIX0axpCD2t5s2LgL", 95);
        test.put("9mlFlcacDaqe7BSpPAtb", 3);
        test.values();
        test.put("BKeXj2XJ1iCB9uVoHnb6", 39);
        test.put("v37akI40XJBOv9Yx0M0O", 0);
        test.put("zxEwQhhIstbBslx7VlmN", 34);
        test.put("ksqEXX7raPXM8Q5AzcJG", 558);
        test.put("s50TrAo4W0wEQxeCCuk6", 8);
        test.put("UG2g8vGl_ArpENJ0AL9N", 589);
        test.put("dAOnHMbYEoMShlE4NTIh", 31);
        test.put("uh5VwtpRBA4EjJpAisnF", 65);
        test.remove("5DsUoMIrqB0v8fSLc8mm");
        test.remove("LsZDUVDY297kaCmUXwGL");
        test.remove("3pJcIwke522MwYu98vYe");
        test.remove("qWSw2oYhTZU6oXIM0CpV");
        test.values();
        test.remove("Y5p2PcUeV7DkeI91ZTHc");
        test.remove("rUuMaZyLi7yrAY2q1f2s");
        test.remove("V_ysV0SAo4jXOp6ONXzt");
        test.remove("lvmFskcbHrHHyuoLG8oc");
        test.remove("4pw5whu5eU6hQi11mqOl");
        test.remove("Bt5zJyzIjKKFc2FrRrvm");
        test.remove("gdBJcIWy0MfBhN8mQg9s");
        test.remove("wAn5ipBFA2F0znRJ4r9j");
        test.values();
        test.remove("EdUKCLdoJmuYIdKZ6Vyq");
        test.remove("tTdktBUTlmVTBGg50E1A");
        test.remove("B0tzt991aagQMYZJ6SOf");
        test.remove("B1TtXIulvb138DsQj0Tu");
        test.remove("NbRpRo8Lqep8ExeGXIyZ");
        test.values();
        test.remove("dUiPTue7MR9di_gPvA_v");
        test.remove("PmP8uDL2dkQatR8Cke0K");
        test.remove("PsEYtsjekjeCs1dIZYbM");
        test.remove("oubAIecCVOyGfUoBqUGe");
        test.values();
        test.remove("Hsls0yOM2XNxT2ImMgZ0");
        test.remove("gBq7KHFzO9Lm3eZus5wZ");
        test.remove("E28aA_FgGN4z1MsVrFIT");
        test.remove("Kyw6iQWjvQ5JvoYod4IX");
        test.remove("3UPQAynK6cpRKM1yDiVt");
        test.remove("OG1DhCnBsjvBMZKgIXHw");
        test.remove("tzE1hIN3Eox4E5dxhIyq");
        test.remove("sDdCPKV4edLJ5G1Oq5Je");
        test.values();
        test.remove("1a88VMuYdj_8_NajNi4x");
        test.remove("uymzwCxpiSpI5NIObsXo");
        test.remove("9prseYRr5FsHoeFAH_l1");
        test.values();
        test.remove("nNc0lNZvTIgDLLTZVvrO");
        test.remove("40GwG__BWXFXBHqpnlTg");
        test.remove("EAFp9fk5hhPHwz1Fwq1x");
        test.remove("eyOAudPHf5vqdRGoAdgT");
        test.remove("_6_NxRfbv5i98u59CPZT");
        test.remove("SrDJadotMsrk4O9NFM7t");
        test.remove("hfz6BXjhxkjK7K8KRi7j");
        test.remove("4qLwPYiMu42Sf7_MIJ5n");
        test.values();
        test.remove("2sCkIhZndysiyr5utypX");
        test.remove("0i4_D6jeXlV3lp7gakAh");
        test.remove("t4JXhI2l2_sTZh4aLiGO");
        test.remove("GfH252_vsKTIs3XHIRtW");
        test.remove("XBONrRwMoiuMBpNFm3Mb");
        test.remove("ix3vkolbYWyGWeu5yi_T");
        test.remove("WjOl2D0yGZVfLqzDZvsi");
        test.remove("tJYLljbBQoyREDDfTenH");
        test.remove("X2TX5gIWYUwDDy9evvyu");
        test.remove("QWIehBN6Wxt0tT1SM4RN");
        test.remove("DFksJHm63I2pR8MrMXYQ");
        test.remove("Jc1qwOnXg9kV4Sg_GnzP");
        test.remove("8NovdDFhhXS02_qtzs6N");
        test.remove("Voaov2d_a9Y5wwjuhPC8");
        test.remove("TWYm91Y9JeVgjKmodC6L");
        test.remove("0VO1hPoYqFPz0q7XeN5r");
        test.remove("Qk5LEtCQcfSV6886URie");
        test.remove("7YCZSUM2LeEmm5GzTfkc");
        test.remove("prg_7W1e0BiYiEyTVFQr");
        test.remove("7LmQVCjGPMmASFFyrEsW");
        test.remove("D7VoZuxDliVgLHDt5b5p");
        test.remove("S8JXjVT5TWO80Z5Hsq9B");
        test.remove("ubHW6RTnXfSyPRpcECIX");
        test.remove("nwHYJADKTGUyPr_wZ8yt");
        test.remove("DDpXLTs48_ud0UAKB0iX");
        test.remove("g_bBrtNAqVEOyJs0s9Fd");
        test.remove("HrSAbbG0SAfZBGZ8s7ct");
        test.remove("KSWv3aZ9PU3pukJhNiVm");
        test.remove("P_rG1irCH0eSOh0_NatQ");
        test.remove("4e0bKF3OnLLmnU1lKCNz");
        test.remove("VqL_AXdwDoQK4rUB61_I");
        test.remove("0_AJmn8BNHcBfRawk3qg");
        test.remove("dVUBt_4t35u2dWl7HtgR");
        test.remove("BRuMJzpOI4hZqfiU7aW8");
        test.remove("vB3DrIsVv48p7jl2oDvN");
        test.remove("5kWQjZYe8U0F6C0k7qzC");
        test.remove("VQHb8pHVnT7qhOMPYL90");
        test.remove("FmHWbfXGcAMdOQjsG9eT");
        test.remove("aVV_6AsYCHg6cwFXphJ0");
        test.remove("uYQmtCPYJOJLHN0HEZKV");
        test.remove("DveqDcMBIr1YrBw5T_Xl");
        test.remove("442Gg8pnih2CdTLp8ACe");
        test.remove("f69oER_3F1PQVEyNc_eF");
        test.remove("U6HLwRjXdhU9U0N7ZCeD");
        test.remove("0za01r9t_vgeiy9gKmM_");
        test.remove("xk0wY0MyOPs0Cn99NNDl");
        test.remove("g2EGwgzCn2MDdLeB5fDF");
        test.remove("erBYDO6C3N_MMBfw50Dr");
        test.remove("YpR8iDGY77V4myKSXZX2");
        test.remove("en3DlLFgJV6Ik8MgBcWf");
        test.remove("nvBPw9_HcB83H8g_GZ7H");
        test.remove("1VaL8BpErpz_6yqKVY1z");
        test.remove("2jpiPVnmz2BeO75pk9At");
        test.remove("c8FfUQppk4HP0rMk58zT");
        test.remove("_kP0gvYX4OEYMTy0WZXb");
        test.remove("4hZBpn2_d1EF75CE50oz");
        test.remove("YhDQK6O3FkdeyliFf11r");
        test.remove("oDc8DZnz1HitCzsuXkZh");
        test.remove("UOmstWdN3Ne6o5mAVYJF");
        test.remove("8ypu7ncXsxAMhs9McOho");
        test.put("GtX0cIbKt3mXNxs4obOc", 80);
        test.put("tLEbiC2tjf5PZhTp003k", 0);
        test.put("d_tdqPaKvqa0z3Nj7FlT", 9);
        test.values();
        test.put("RovJnxiQBxrRyTSmRzLv", 00);
        test.put("KahamedyFzdeFf584PuN", 698);
        test.put("8f5JXV19zNpO4SQBmN98", 67);
        test.put("qGlsOHoxKXZlGmUw8oZA", 6);
        test.put("c9HrEx9apw7K6ugE74hf", 71);
        test.put("Zww6se4VZr0tuKA5xSu_", 686);
        test.put("NWtmYbnEnpzMo8OQzP6G", 451);
        test.values();
        test.put("24ceLrxlPJZPivmphbSi", 5);
        test.put("AiVlXooEOtZ2jU9AX_GX", 8);
        test.values();
        test.put("ElSx9Wh36FKtJgW1PIxX", 88);
        test.put("MYTVxOKe0nyV2HkCrC2r", 35);
        test.values();
        test.put("9hGgeD54siCVn_DUqh3q", 786);
        test.put("y0gTkS_E4cTCqyl9fuJc", 4);
        test.put("L4Z2m_HlNJ2eUqr8g72m", 31);
        test.put("rr0bI_fzjsaGW1V2CH3m", 421);

        test.put("307jWKzCMmUXyJJIPX58", 3);
        test.put("_OVl1eQCtpcXoaFDX55n", 785);
        test.put("aXi2oOAhF8g6IMgZtT0F", 063);
        test.put("6N0jbN8WSTKIsRg7p702", 18);
        test.put("OyCHJTtiZGWVGYCsAVpF", 7);
        test.put("M3COww3K8WxsNRGAU2NV", 7);
        test.put("WJCNTv6gMvK1RyiJZsr9", 7);
        test.put("r69tlYMqc0vmyJUdQHyO", 98);
        test.put("Dz7fkT83ccb9qHBhEFUP", 6);
        test.put("Uscpl4TltGpQobqoj66V", 07);
        test.put("Y_qFx2U72n4sJaGrYy8n", 1);
        test.put("lRXViI2SaB2m9XR8v0kW", 8);
        test.put("mGyG6OFYdEC7oh2b9SOc", 373);
        test.put("NmJEYkzumMD_IOtcMkw_", 11);
        test.put("ijRao8GUTy8xZT3khTKK", 4);
        test.put("xlwM2ivTUxjmfXk4WdTS", 76);
        test.put("FcHBsE433k0RMbCnckoV", 02);
        test.put("7A3cNzsOE5vdAKrpRSlp", 8);
        test.put("UqaANkiqkPAFCPQMeQJV", 27);
        test.put("kcr4DUqQ5l_sthVnzJWW", 439);
        test.put("82vrsodVLOdNRFARklWH", 22);
        test.put("72eIQcHAyp0wh8E5vGmF", 114);
        test.put("aLkj7JlqgyQNSMGAOz7W", 64);
        test.put("MjPx9anYZDgxYO2W2Tg5", 5);
        test.put("UmIv4JjJaLme3Wgg5va9", 85);
        test.put("6f7hhpHBY0ubSCISADq0", 3);
        test.put("Seta_mlFjMvK3MMnSXav", 297);
        test.values();
        test.put("w3__wqRnazACQJ0rXJoJ", 28);
        test.put("Ktiee4zB0UeoFLldpPOg", 39);
        test.put("3HEP4B6tCySpvGhHu2gm", 11);
        test.put("wCPxQPdUIu9tg2qo6FWk", 424);
        test.put("oPminY7rdBC4YIWBnF1L", 7);
        test.put("89oYHykaPDEz_WHaFQQy", 741);
        test.put("PGqzkmz4GAlHIBt9xqBK", 0);
        test.put("yvKWlxGhFcTXJVuPn4V5", 021);
        test.put("hPuMtQPrvNb5_pydsK7Z", 25);
        test.put("hrnGxCEvQiv42uPrR2ef", 33);
        test.put("fEJIlz48UBS0mtx3t0V1", 43);
        test.put("j7PnZTS4JovLn6zB6uT6", 6);
        test.containsKey("fc0");
        test.containsKey("hUi");
        test.containsKey("xh0");
        test.containsKey("sU5");
        test.containsKey("5qw");
        test.containsKey("ntl");
        test.containsKey("EIw");
        test.containsKey("6Zu");
        test.containsKey("5Ay");
        test.containsKey("BIZ");
        test.containsKey("ubI");
        test.containsKey("6TC");
        test.containsKey("Xf8");
        test.containsKey("prG");
        test.containsKey("OsH");
        test.containsKey("sdi");
        test.containsKey("Vr7");
        test.containsKey("GTZ");
        test.containsKey("Doh");
        test.containsKey("ytV");
        test.containsKey("VFC");
        test.containsKey("pdA");
        test.containsKey("qH2");
        test.containsKey("k_G");
        test.containsKey("QYH");
        test.containsKey("umK");
        test.containsKey("QAP");
        test.containsKey("tTl");
        test.containsKey("9SR");
        test.containsKey("atF");
        test.containsKey("pej");
        test.containsKey("KES");
        test.containsKey("FeE");
        test.containsKey("dZX");
        test.containsKey("nY3");
        test.containsKey("TSL");
        test.containsKey("scN");
        test.containsKey("ot3");
        test.containsKey("wVY");
        test.containsKey("9KC");
        test.containsKey("zjP");
        test.containsKey("_63");
        test.containsKey("mHU");
        test.containsKey("jD0");
        test.containsKey("8b2");
        test.containsKey("M6M");
        test.containsKey("BDX");
        test.containsKey("99c");
        test.containsKey("_tx");
        test.containsKey("Xsm");
        test.containsKey("SZl");
        test.containsKey("QSn");
        test.containsKey("r2S");
        test.containsKey("9y6");
        test.containsKey("xJ1");
        test.containsKey("31r");
        test.containsKey("hyL");
        test.containsKey("0CP");
        test.containsKey("Nwh");
        test.containsKey("swh");
        test.containsKey("Fyn");
        test.containsKey("Pbe");
        test.containsKey("Fmr");
        test.containsKey("sIJ");
        test.containsKey("YZJ");
        test.containsKey("4IP");
        test.containsKey("BMO");
        test.containsKey("tCF");
        test.containsKey("wqP");
        test.values();
        test.containsKey("cw6");
        test.containsKey("hLm");
        test.containsKey("eps");
        test.containsKey("6jc");
        test.containsKey("l6g");
        test.containsKey("rzS");
        test.containsKey("D2y");
        test.containsKey("rom");
        test.containsKey("Ou3");
        test.containsKey("YDj");
        test.values();
        test.containsKey("vHI");
        test.containsKey("xMz");
        test.containsKey("brS");
        test.containsKey("0ch");
        test.containsKey("Kxq");
        test.containsKey("P2t");
        test.values();
        test.containsKey("0KP");
        test.containsKey("4dq");
        test.containsKey("t6A");
        test.containsKey("SWT");
        test.containsKey("hn7");
        test.containsKey("jkq");
        test.containsKey("nHJ");
        test.containsKey("wpS");
        test.containsKey("ZBK");
        test.containsKey("LkS");
        test.containsKey("vgc");
        test.containsKey("b2w");
        test.containsKey("guJ");
        test.containsKey("4wP");
        test.containsKey("ost");

        test.put("_KwIZX8hXjPoo3UF5WCq", 9);
        test.put("cTsQzYYCCz1RfZlZwmwA", 003);
        test.put("9E0p0ZQhxZx5_WI09WGo", 293);
        test.put("9E0p0ZQhxZx5_WI09WGo", 293);
        test.values();
        test.put("qerg", 2);
        test.remove("a");
        test.remove("s");

        System.out.println(test);

        System.out.println(test.containsKey("a"));
        System.out.println(test.containsKey("o"));


        System.out.println(test.get("a"));
        System.out.println(test.get("b"));

        test.clear();
        System.out.println(test);
        System.out.println(test.isEmpty());


        test.clear();
        test.put("s", 2);
        System.out.println(test.containsKey("m"));

        test.clear();
        test.put("s", 2);
        test.put("4", 8);
        test.remove("9");
        System.out.println(test.size());


        test.clear();
        System.out.println(test.keySet().toString());

        LinkedHashMap<String, Integer> merger = new LinkedHashMap<>();

        merger.put("a", 1);
        merger.put("b", 2);
        merger.put("b", 3);

        System.out.println(merger);

        test.putAll(merger);


        System.out.println(test);


        test.clear();
        System.out.println(test.size());
        merger.clear();
        merger.put("a", 7);
        merger.put("b", 2);
        test.values();
        merger.put("b", 3);
        merger.put("e", 1);
        test.values();
        merger.put("d", 2);
        merger.putOverwrite("b", 4);
        merger.put("d", 9);
        merger.put("z", 2);
        System.out.println(merger);


        merger.remove("a");
        test.values();
        merger.remove("z");

        System.out.println(merger);


        merger.remove("d");
        merger.remove("z");
        test.values();
        merger.remove("a");
        System.out.println(merger);
        merger.remove("e");

        System.out.println(merger);
        System.out.println("size=" + merger.size());


        System.out.println(merger);
        System.out.println("size=" + merger.size());

        System.out.println(merger);
        System.out.println("szie = " + merger.size());
        merger.clear();


        System.out.println("Before rem=" + merger);
        System.out.println("Before rem=" + merger.containsKey("a"));

        merger.remove("z");
        merger.remove("a");
        System.out.println("Before rem=" + merger);
        System.out.println("Before rem=" + merger.containsKey("a"));
        System.out.println("szie = " + merger.size());

        merger.clear();
    }
}

