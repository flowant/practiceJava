package practice.list;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class DoublyLinkedList<T> {

    Node<T> first = null;
    Node<T> last = null;
    int size = 0;

    public boolean linkFirst(T value) {
        if (value == null) {
            return false;
        }
        Node<T> node = new Node<>(value);
        if (first == null) {
            first = last = node;
        } else {
            node.next = first;
            first.prev = node;
            first = node;
        }
        size++;
        return true;
    }

    public boolean linkLast(T value) {
        if (value == null) {
            return false;
        }
        Node<T> node = new Node<>(value);
        if (last == null) {
            first = last = node;
        } else {
            node.prev = last;
            last.next = node;
            last = node;
        }
        size++;
        return true;
    }

    public T removeFirst() {
        if (first == null) {
            return null;
        }

        size--;

        Node<T> first = this.first;

        if (this.first == last) {
            this.first = last = null;
        } else {
            this.first = this.first.next;
            this.first.prev = null;
        }

        return first.item;
    }

    public T removeLast() {
        if (last == null) {
            return null;
        }

        size--;

        Node<T> last = this.last;

        if (first == this.last) {
            first = this.last = null;
        } else {
            this.last = this.last.prev;
            this.last.next = null;
        }

        return last.item;
    }

    public int size() {
        return size;
    }

    public Node<T> getNode(int i) {
        if (i >= size || i < 0) {
            return null;
        }

        Node<T> node = first;
        while (i-- != 0) {
            node = node.next;
        }
        return node;
    }

    public T get(int i) {
        if (i >= size || i < 0) {
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

        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
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
            assertEquals(-i, list.removeLast().intValue());
            assertEquals(i * 2, list.size());
        }
    }

    @Test
    public void testStackQueue() {
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
