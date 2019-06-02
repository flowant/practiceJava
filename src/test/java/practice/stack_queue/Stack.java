package practice.stack_queue;

import org.junit.Assert;
import org.junit.Test;

public class Stack <T> {
    private static class Node <T> {
        T item;
        Node<T> next;
        public Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }
    }

    Node<T> top = null;

    public boolean isEmpty() {
        return top == null;
    }

    public void push(T item) {
        top = new Node<T>(item, top);
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }

        Node<T> n = top;
        top = top.next;

        return n.item;
    }

    public T peek() {
        return top.item;
    }

    @Test
    public void test() {
        Stack<Integer> stack = new Stack<>();

        Assert.assertTrue(stack.isEmpty());

        for (int i = 0; i <= 10; i++) {
            stack.push(i);
        }

        Assert.assertFalse(stack.isEmpty());

        for (int i = 10; i >= 0; i--) {
            Assert.assertEquals(i, stack.pop().intValue());
        }

        Assert.assertTrue(stack.isEmpty());
    }

}
