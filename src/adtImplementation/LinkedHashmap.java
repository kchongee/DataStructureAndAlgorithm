package adtImplementation;

import adtInterfaces.MapInterface;

public class LinkedHashmap <K, V> implements MapInterface<K,V>
{
    private Entry<K,V>[] entryBuckets;
    private static final int DEFAULT_BUCKET_QTY = 16;
    private static final int DEFAULT_POWER = 4;
    private int power; // 2 ^ 4 = 16 buckets
    private static final double LOAD_FACTOR = 0.75;
    private int totalEntries;
    private LinkedList<Entry> entrySequence;

    public LinkedHashmap()
    {
        this.entryBuckets = new Entry[DEFAULT_BUCKET_QTY];
        this.power = DEFAULT_POWER;
        this.totalEntries = 0;
        this.entrySequence = new LinkedList<Entry>();
    }

    private LinkedHashmap(int newSize)
    {
        this.entryBuckets = new Entry[newSize];
        this.power = DEFAULT_POWER;
        this.totalEntries = 0;
        this.entrySequence = new LinkedList<Entry>();
    }

    public V put(K key, V value)
    {
        int hashCode = key.hashCode();
        int bucketIndex = convertHashCodeToArrayIndex(hashCode);
        Entry<K,V> entry = entryBuckets[bucketIndex];

        if (collisionHappen(bucketIndex))
        {

            // dead bug
            // System.out.println("Collision happen");


            Entry existingEntry = entry.searchEntry(key);

            if (existingEntry == null) {
                entry.appendCollidedEntry(new Entry<K,V>(key, value));
            }
            else {
                existingEntry.value = value;

                // dead bug
                // System.out.println("reassigned from " + existingEntry.value + " to " + value);
            }
        }
        else
        {
            Entry<K,V> newEntry = new Entry<K,V>(key, value);
            entryBuckets[bucketIndex] = newEntry;
            entrySequence.add(newEntry);
        }

        this.totalEntries++;


        if (bucketsHaveTooMuchEntries()) {
            rehash();
        }
        return value;
    }

    @Override
    public V get(K k) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }


    private int convertHashCodeToArrayIndex(int hashcode) {
        return hashcode % entryBuckets.length;
    }


    private boolean collisionHappen(int bucketIndex){
        return  entryBuckets[bucketIndex] != null;
    }


    private boolean bucketsHaveTooMuchEntries() {

        // active bug
        System.out.println(totalEntries);
        System.out.println(entryQtyLimit());

        return totalEntries > entryQtyLimit();
    }


    private int entryQtyLimit(){
        return (int) (entryBuckets.length * LOAD_FACTOR);
    }


    private static class Entry<K,V>
    {
        K key;
        V value;
        Entry<K,V> collidedEntry;
        Entry<K,V> lastEntry;
        int totalEntry;
        Entry sequenceTracker;

        public Entry(K key, V value)
        {
            this.key = key;
            this.value = value;
            this.lastEntry = this;
            this.collidedEntry = null;
            this.totalEntry = 1;
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
            Entry<K,V> searchEntry = this;
            for (int i = 0 ; i < searchEntry.totalEntry ; i++)
            {
                if (searchEntry.key == key){
                    return searchEntry;
                }
                searchEntry = searchEntry.collidedEntry;
            }
            return null;
        }

        public void appendCollidedEntry(Entry<K,V> entry)
        {
            this.lastEntry.collidedEntry =entry;
            this.lastEntry = entry;
            this.totalEntry += 1;
        }

        public boolean isNewEntry(K key){
            return searchEntry(key) == null;
        }

    }


    private int getNewTableSize(){
        return (int) Math.pow(2, this.power+1);
    }



    private void rehash()
    {
        // active bug
        System.out.println("triggered rehash");

        LinkedHashmap<K,V> newMap = new LinkedHashmap<>(getNewTableSize());

        for (int i = 0 ; i < entryBuckets.length ; i++)
        {
            Entry<K,V> tempEntry = entryBuckets[i];
            if (tempEntry != null)
            {
                Entry<K,V>[] entrySet = tempEntry.toArray();
                for (Entry<K,V> entry : entrySet) {
                    newMap.put(entry.key, entry.value);
                }
            }
        }
        this.power +=1;
        this.entryBuckets = newMap.entryBuckets;
    }

    public String toString()
    {
        String str = "{\n";
        for (int i = 0 ; i < totalEntries ; i++)
        {
            Entry entry = entrySequence.get(i);
            if (entry != null){
                String map = "\t" + entry.key.toString() + " : " + entry.value.toString() + "\n";
                str = str+map;
            }
        }
        str=str+"}";
        return str;
    }

    public static void main(String[] args)
    {
        LinkedHashmap<String,Integer> test = new LinkedHashmap<String, Integer>();
        test.put("a",1);
        test.put("b",2);
        test.put("c",2);
        test.put("d",2);
        test.put("d",7);
        test.put("d",8);
        test.put("f",2);
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
        test.put("s",2);
        test.put("s",2);
        test.put("s",10);

        System.out.println(test.toString());

    }

}
