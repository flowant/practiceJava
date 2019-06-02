package practice.stack_queue;

import org.junit.Assert;
import org.junit.Test;

public class MyQueue<T> {
    private static class Node<T> {
        T item;
        Node<T> next;
        public Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }
    }

    Node<T> first = null;
    Node<T> last = null;

    public boolean isEmpty() {
        return first == null;
    }

    public void add(T item) {
        Node<T> n = new Node<>(item, null);
        if (isEmpty()) {
            first = n;
            last = n;
        } else {
            last.next = n;
            last = n;
        }
    }

    public T remove() {
        if (isEmpty()) {
            return null;
        }

        Node<T> n = first;
        first = n.next;

        return n.item;
    }

    @Test
    public void test() {
        MyQueue<Integer> q = new MyQueue<>();
        Assert.assertTrue(q.isEmpty());

        for (int i = 0; i < 10; i++) {
            q.add(i);
        }

        Assert.assertFalse(q.isEmpty());

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(i, q.remove().intValue());
        }

        Assert.assertTrue(q.isEmpty());

        for (int i = 0; i < 10; i++) {
            q.add(i);
        }

        Assert.assertFalse(q.isEmpty());

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(i, q.remove().intValue());
        }
    }

}
