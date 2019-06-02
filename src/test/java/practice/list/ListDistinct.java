package practice.list;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

public class ListDistinct<E> extends DoublyLinkedList<E> {
    public void distinct() {
        Node<E> node = first;
        HashSet<E> distinct = new HashSet<>();
        while (node != null) {
            if (distinct.contains(node.item)) {
                node.prev.next = node.next;
                size--;
            } else {
                distinct.add(node.item);
            }
            node = node.next;
        }
    }

    public void distinctWithoutBuffer() {
        Node<E> targetNode = first;
        while (targetNode != null) {
            Node<E> remainingNode = targetNode.next;
            while (remainingNode != null)	 {
                if (targetNode.item.equals(remainingNode.item)) {
                    remainingNode.prev.next = remainingNode.next;
                    size--;
                }
                remainingNode = remainingNode.next;
            }
            targetNode = targetNode.next;
        }
    }


    @Test
    public void testDistinct() {
        ListDistinct<Integer> list = new ListDistinct<>();
        list.linkFirst(0);
        list.linkLast(1);
        list.linkLast(1);
        Assert.assertEquals(list.size(), 3);
        list.distinct();
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(list.get(0).intValue(), 0);
        Assert.assertEquals(list.get(1).intValue(), 1);
    }

    @Test
    public void testDistinctWithoutBuffer() {
        ListDistinct<Integer> list = new ListDistinct<>();
        list.linkFirst(0);
        list.linkLast(1);
        list.linkLast(1);
        Assert.assertEquals(list.size(), 3);
        list.distinctWithoutBuffer();
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(list.get(0).intValue(), 0);
        Assert.assertEquals(list.get(1).intValue(), 1);
    }

}
