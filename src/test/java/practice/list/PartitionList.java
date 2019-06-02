package practice.list;

import org.junit.Assert;
import org.junit.Test;

public class PartitionList<E extends Number> extends DoublyLinkedList<E> {
    public Node<E> partition(Node<E> n, int value) {
        if (n == null || n.next == null) {
            return n;
        }

        Node<E> first = n, last = n;
        n = n.next;

        while (n != null) {
            Node<E> next = n.next;
            if (n.item.intValue() < value) {
                n.next = first;
                first = n;
            } else {
                last.next = n;
                n.next = null;
                last = n;
            }
            n = next;
        }

        return first;
    }

    @Test
    public void testPartition() {
        PartitionList<Integer> list = new PartitionList<>();
        int lenNode = 5;
        int value = 2;

        for (int i = 0; i < lenNode; i++) {
            list.linkFirst(i);
        }
        Node<Integer> node = list.partition(list.getNode(0), value);

        for (int i = 0; i < value; i++) {
            Assert.assertTrue(Integer.compare(node.item, value) < 0);
            node = node.next;
        }

        for (int i = value; i < lenNode; i++) {
            Assert.assertTrue(node.item >= value);
            node = node.next;
        }
    }

}
