import java.util.List;
import java.util.ArrayList; // import the ArrayList class

public class LinkedListDeque61B<T> implements Deque61B<T>{

    private class Node{
        public T item;
        public Node next;
        public Node pre;

        public Node(T x, Node p, Node n){
            item = x;
            pre = p;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque61B(){
        sentinel = new Node(null, null, null);
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public void addFirst(T x) {
        Node oldFirst = sentinel.next;
        Node newNode = new Node(x, sentinel, oldFirst);

        sentinel.next = newNode;
        oldFirst.pre = newNode;

        size++;
    }

    @Override
    public void addLast(T x) {
        Node oldLast = sentinel.pre;
        Node newNode = new Node(x, oldLast, sentinel);

        oldLast.next = newNode;
        sentinel.pre = newNode;

        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node cur = sentinel.next;

        while (cur != sentinel){
            returnList.add(cur.item);
            cur = cur.next;
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size < 1){
            return null;
        }

        Node p = sentinel.next;
        T x = p.item;

        p.next.pre = sentinel;
        sentinel.next = p.next;

        size--;

        return x;
    }

    @Override
    public T removeLast() {
        if (size < 1){
            return null;
        }

        Node p = sentinel.pre;
        T x = p.item;

        p.pre.next = sentinel;
        sentinel.pre = p.pre;

        size--;

        return x;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        Node p = sentinel.next;
        int i = 0;
        while (i < index){
            p = p.next;
            i++;
        }

        return p.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size){
            return null;
        }
        return getRecursiveHelp(sentinel.next, index);
    }

    private  T getRecursiveHelp(Node p, int index){
        if (index == 0){
            return p.item;
        }
        return getRecursiveHelp(p.next, index - 1);
    }
}
