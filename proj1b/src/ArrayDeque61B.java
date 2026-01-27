import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {

    T[] items;
    int size;
    int nextFirst;
    int nextLast;

    public ArrayDeque61B(){
         items = (T[]) new Object[8];
         size = 0;
         nextFirst = 0;
         nextLast = 1;
    }

    private void resize(int capacity){
        T[] a = (T[]) new Object[capacity];

        for (int i = 0; i < size; i++){
            int realIdx = Math.floorMod(nextFirst + i + 1, items.length);
            a[i] = items[realIdx];
        }

        items = a;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length){
            resize(size * 2);
        }
        else if ((double)(items.length / size) <= 0.25){
            resize(Math.floorDiv(items.length, 2));
        }

        items[nextFirst] = x;
        size++;

        nextFirst = Math.floorMod(nextFirst - 1, items.length);
    }

    @Override
    public void addLast(T x) {
        if (size == items.length){
            resize(size * 2);
        }
        else if ((double)(items.length / size) <= 0.25){
            resize(Math.floorDiv(items.length, 2));
        }

        items[nextLast] = x;
        size++;

        nextLast = Math.floorMod(nextLast + 1, items.length);
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int curIdx = Math.floorMod(nextFirst + 1, items.length);

        for (int i = 0; i < size; i++){
            returnList.add(items[curIdx]);
            curIdx = Math.floorMod(curIdx + 1, items.length);
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
        if (isEmpty()){
            return null;
        }

        int removeIdx = Math.floorMod(nextFirst + 1, items.length);
        T x = items[removeIdx];
        items[removeIdx] = null;
        nextFirst = removeIdx;
        size--;

        return x;
    }

    @Override
    public T removeLast() {
        if (isEmpty()){
            return null;
        }

        int removeIdx = Math.floorMod(nextLast - 1, items.length);
        T x = items[removeIdx];
        items[removeIdx] = null;
        nextLast = removeIdx;
        size--;

        return x;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size){
            return null;
        }
        else{
            int actualIdx = Math.floorMod(nextFirst + 1, items.length);
            return items[actualIdx];
        }
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
