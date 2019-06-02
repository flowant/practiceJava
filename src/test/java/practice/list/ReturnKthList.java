package practice.list;

import org.junit.Assert;
import org.junit.Test;

public class ReturnKthList<E> extends DoublyLinkedList<E> {
    public Node<E> findKthToLastNode(int k) {
        Node<E> kTh = first;
        Node<E> travelNode = kTh;

        for (int i = 0; i < k; i++ ) {
            if (travelNode.next == null) {
                return null;
            }
            travelNode = travelNode.next;
        }

        while (travelNode.next != null) {
            travelNode = travelNode.next;
            kTh = kTh.next;
        }
        return kTh;
    }

    public boolean deleteNode(Node<E> n) {
        if (n == null || n.next == null) {
            return false;
        }

        n.item = n.next.item;
        n.next = n.next.next;

        return true;
    }

    @Test
    public void testFindKthToLastNode() {
        ReturnKthList<Integer> list = new ReturnKthList<>();

        for (int i = 0; i < 10; i++) {
            list.linkFirst(i);// 9 8 7 .. 0
        }

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(i, list.findKthToLastNode(i).item.intValue());
        }
    }

    @Test
    public void testDeleteNode() {
        ReturnKthList<Integer> list = new ReturnKthList<>();
        for (int i = 0; i < 10; i++) {
            list.linkLast(i);// 9 8 7 .. 0
        }
        Node<Integer> node = list.getNode(1);
        list.deleteNode(node);
        Assert.assertEquals(2, list.get(1).intValue());
    }
}
