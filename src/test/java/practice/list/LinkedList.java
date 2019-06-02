package practice.list;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class LinkedList<T> {

    Node<T> first = null;
    Node<T> last = null;
    int size = 0;

    public boolean linkFirst(T item) {
        if (item == null) {
            return false;
        }

        Node<T> node = new Node<>(item);
        size++;

        if (first == null) {
            first = last = node;
        } else {
            node.next = first;
            first = node;
        }

        return true;
    }

    public boolean linkLast(T item) {
        if (item == null) {
            return false;
        }

        Node<T> node = new Node<>(item);
        size++;

        if (last == null) {
            first = last = node;
        } else {
            last.next = node;
            last = node;
        }

        return true;
    }

    public T removeFirst() {
        if (first == null) {
            return null;
        }

        T item = first.item;
        size--;

        if (first == last) {
            first = last = null;
        } else {
            first = first.next;
        }

        return item;
    }

    public int size() {
        return size;
    }

    public Node<T> getNode(int i) {
        if (i < 0 || i >= size) {
            return null;
        }
        Node<T> node = first;
        while (i-- != 0) {
            node = node.next;
        }
        return node;
    }

    public T get(int i) {
        if (i < 0 || i >= size) {
            return null;
        }
        return getNode(i).item;
    }

    public boolean push(T item) {
        return linkFirst(item);
    }

    public T pop() {
        return removeFirst();
    }

    public boolean offer(T item) {
        return linkLast(item);
    }

    public T poll() {
        return removeFirst();
    }

    @Test
    public void test() {
        int cnt = 100;

        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < cnt; i++) {
            list.linkFirst(i);
            list.linkLast(-i);
            assertEquals(list.size(), (i + 1) * 2);
        }

        for (int i = 0; i < cnt; i++) {
            assertEquals(cnt - 1 - i, list.get(i).intValue());
            assertEquals(-(cnt - 1 - i), list.get(list.size() - 1 - i).intValue());
        }

        for (int i = cnt - 1; i >= 0; i--) {
            assertEquals(i, list.removeFirst().intValue());
            assertEquals(i + cnt, list.size());
        }

        for (int i = 0; i < cnt; i++) {
            assertEquals(-i, list.removeFirst().intValue());
            assertEquals(cnt - i - 1, list.size());
        }

    }

    @Test
    public void stackQueue() {
        int cnt = 10;
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        for (int i = 0; i < cnt; i++) {
            list.push(i);
        }
        for (int i = cnt - 1; i >= 0; i--) {
            assertEquals(i, list.pop().intValue());
        }
        assertEquals(0, list.size());
        for (int i = 0; i < cnt; i++) {
            list.offer(i);
        }
        for (int i = 0; i < cnt; i++) {
            assertEquals(i, list.poll().intValue());
        }
    }

}
