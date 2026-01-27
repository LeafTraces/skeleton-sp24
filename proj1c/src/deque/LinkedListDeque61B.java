package deque;

import java.util.Iterator;
import java.util.LinkedList;
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

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T>{
        private Node p;

        public LinkedListIterator(){
            p = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return p != sentinel;
        }

        @Override
        public T next() {
            T x = p.item;
            p = p.next;
            return x;
        }
    }

    @Override
    public boolean equals(Object other){
        if (this == other){
            return true;
        }

        if (other instanceof Deque61B<?> otherDeque){
            if (this.size() != otherDeque.size()){
                return false;
            }

            for (int i = 0; i < this.size; i++){
                Object item1 = this.get(i);
                Object item2 = otherDeque.get(i);

                if (item1 == null && item2 != null){
                    return false;
                }
                else if (!item1.equals(item2)){
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        if (isEmpty()){
            return "[]";
        }

        StringBuilder returnString = new StringBuilder();
        returnString.append("[");
        for (T x : this){
            returnString.append(x);
            returnString.append(", ");
        }
        int len = returnString.length();
        returnString.delete(len - 2, len);

        returnString.append("]");

        return returnString.toString();
    }
}

