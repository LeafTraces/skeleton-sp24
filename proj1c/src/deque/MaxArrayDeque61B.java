package deque;

import java.util.Comparator;


public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private Comparator<T> myComparator;

    public MaxArrayDeque61B(Comparator<T> c){
        super();
        this.myComparator = c;
    }

    public T max(Comparator<T> c){
        if (isEmpty()){
            return null;
        }

        T maxItem = this.get(0);

        for (T item : this){
            if (c.compare(item, maxItem) > 0){
                maxItem = item;
            }
        }
        return maxItem;
    }

    public T max(){
        return max(this.myComparator);
    }

}