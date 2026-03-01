package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!

    private static final int default_Initial_Capacity = 16;
    private static final double default_LoadFactor = 0.75;

    private int size;
    private double loadFactor;

    /** Constructors */
    public MyHashMap() {
        this(default_Initial_Capacity, default_LoadFactor);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, default_LoadFactor);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        if (initialCapacity < 0){
            throw new IllegalArgumentException();
        }

        this.loadFactor = loadFactor;
        this.size = 0;
        buckets = (Collection<Node>[]) new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++){
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new LinkedList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    private void resize(int newCapacity){
        Collection<Node>[] oldBuckets = buckets;
        buckets = (Collection<Node>[]) new Collection[newCapacity];

        for (int i = 0; i < newCapacity; i++){
            buckets[i] = createBucket();
        }

        size = 0;
        for (Collection<Node> bucket : oldBuckets){
            for (Node node : bucket){
                put(node.key, node.value);
            }
        }
    }

    @Override
    public void put(K key, V value) {
        if (key == null){
            throw new IllegalArgumentException("Null key not allowed.");
        }

        int index = Math.floorMod(key.hashCode(), buckets.length);
        Collection<Node> bucket = buckets[index];

        for (Node node : bucket){
            if (node.key.equals(key)){
                node.value = value;
                return;
            }
        }

        bucket.add(new Node(key, value));
        size++;

        if ((double)size / buckets.length > loadFactor){
            resize(buckets.length * 2);
        }
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    private Node getNode(K key){
        if (key == null){
            throw new IllegalArgumentException("Null key not allowed.");
        }

        int index = Math.floorMod(key.hashCode(), buckets.length);
        Collection<Node> bucket = buckets[index];

        for (Node node : bucket){
            if (node.key.equals(key)){
                return node;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        int originalCapacity = buckets.length;
        buckets = (Collection<Node>[]) new Collection[originalCapacity];
        for (int i = 0; i < originalCapacity; i++){
            buckets[i] = createBucket();
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Collection<Node> bucket : buckets){
            for (Node node : bucket){
               set.add(node.key);
            }
        }
        return set;
    }

    @Override
    public V remove(K key) {
        if (key == null){
            throw new IllegalArgumentException("Null key not allowed.");
        }

        int index = Math.floorMod(key.hashCode(), buckets.length);
        Collection<Node> bucket = buckets[index];

        Node node = getNode(key);
        V value = node == null ? null : node.value;
        if (node == null){
            return null;
        }
        bucket.remove(node);

        size--;

        return value;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

}
