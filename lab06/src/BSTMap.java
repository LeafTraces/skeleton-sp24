import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements  Map61B<K, V> {

    private class Node{
        K key;      //键
        V value;    //值
        Node left;  //左孩子
        Node right; //右孩子

        public Node(K k, V v){
            this.key = k;
            this.value = v;
        }
    }

    private Node root;
    private int size = 0;

    @Override
    public void put(K key, V value) {
        root = putHelper(root, key, value);
    }

    private Node putHelper(Node node, K key, V value){
        if (node == null){
            size++;
            return new Node(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0){
            node.left = putHelper(node.left, key, value);
        }
        else if(cmp > 0){
            node.right = putHelper(node.right, key, value);
        }
        else{
            node.value = value;
        }
        return node;
    }

    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    private V getHelper(Node node, K key){
        if (node == null){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0){
            return getHelper(node.left, key);
        }
        else if(cmp > 0){
            return getHelper(node.right, key);
        }
        else{
            return node.value;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containHelper(root, key);
    }

    private boolean containHelper(Node node, K key){
        if (node == null){
            return false;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0){
            return containHelper(node.left, key);
        }
        else if (cmp > 0){
            return containHelper(node.right, key);
        }
        else{
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new java.util.LinkedHashSet<>();
        keySetHelper(root, keys);
        return keys;
    }

    private void keySetHelper(Node node, Set<K> keys){
        if (node == null){
            return;
        }
        keySetHelper(node.left, keys);
        keys.add(node.key);
        keySetHelper(node.right, keys);
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)){
            return null;
        }

        V toReturn = get(key);

        //删除
        root = removeHelper(root, key);
        size--;
        return toReturn;
    }

    private Node removeHelper(Node node, K key){
        if (node == null){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0){
            node.left = removeHelper(node.left, key);
        }
        else if (cmp > 0){
            node.right = removeHelper(node.right, key);
        }
        else{
            //Case1:
            if (node.left == null){
                return node.right;
            }
            //Case2:
            if (node.right == null){
                return node.left;
            }

            //Case3:
            Node successor = minNode(node.right);
            successor.right = removeMin(node.right); //右树的最小叶子
            successor.left = node.left;

            return successor;
        }
        return node;
    }

    //获取最小子树
    private Node minNode(Node node){
        if (node.left == null){
            return node;
        }
        return minNode(node.left);
    }

    //删除最孩子后的子树
    private Node removeMin(Node node){
        if (node.left == null){
            return node.right;
        }
        node.left = removeMin(node.left);
        return node;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public void printInOrder(){
        printHelper(root);
    }

    private void printHelper(Node node){
        if (node == null){
            return;
        }
        printHelper(node.left);
        System.out.println(node.key.toString() + "->" + node.value.toString());
        printHelper(node.right);
    }
}
