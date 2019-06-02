package practice.graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class UpdatablePriorityQ<E> {

    Comparator<? super E> comparator;
    HashMap<E, Integer> map = new HashMap<>();
    E[] queue;
    int size;

    @SuppressWarnings("unchecked")
    public UpdatablePriorityQ(Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.queue = (E[]) new Object[64];
    }

    public boolean offer(E e) {
        if (e == null)
            throw new NullPointerException();

        if (queue.length == size) {
            grow();
        }
        int i = size;
        queue[i] = e;
        map.put(queue[i], i);
        size++;

        siftUp(i);

        return true;
    }

    void grow() {
        queue = Arrays.copyOf(queue, queue.length << 1);
    }

    void swap(int i, int j) {
        E temp = queue[i];
        queue[i] = queue[j];
        queue[j] = temp;

        map.put(queue[i], i);
        map.put(queue[j], j);
    }

    protected int siftUp(int i) {
        while (i != 0) {
            int parent = (i - 1) >>> 1;
            if (comparator.compare(queue[parent], queue[i]) > 0) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
        return i;
    }

    protected int siftDown(int i) {
        while (i < size) {
            int left = (i << 1) + 1;
            int right = left + 1;
            int child;

            if (left >= size) { // right > size, right is always bigger than left
                return i;
            } else if (right >= size) {
                child = left;
            } else if (comparator.compare(queue[left], queue[right]) < 0) {
                child = left;
            } else {
                child = right;
            }

            if (comparator.compare(queue[child], queue[i]) < 0) {
                swap(child, i);
                i = child;
            } else {
                break;
            }
        }
        return i;
    }

    public boolean update(E e) {
        if (e == null)
            throw new NullPointerException();

        Integer integer = map.get(e);

        if (integer == null) {
            return false;
        }

        if (integer.equals(siftUp(integer))) {
            siftDown(integer);
        }
        return true;
    }

    public E poll () {
        if (size == 0) {
            return null;
        }

        E root = queue[0];

        queue[0] = queue[size - 1];
        size--;
        map.remove(root);
        map.put(queue[0], 0);

        siftDown(0);
        return root;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
