package practice.list;

import java.util.Comparator;
import java.util.PriorityQueue;

/*

https://leetcode.com/problems/merge-k-sorted-lists/

Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

Example:

Input:
[
  1->4->5,
  1->3->4,
  2->6
]
Output: 1->1->2->3->4->4->5->6
 */
public class MergeKSortedLists {
    public ListNode mergeKLists(ListNode[] lists) {

        // O(NlogK), N is the number of nodes in out array, K is the number of input lists

        if (lists == null || lists.length == 0) {
            return null;
        }

        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(lists.length, Comparator.comparingInt(node -> node.val));

        // Enqueue the head node of each list
        for (ListNode list : lists) {
            if (list != null) {
                minHeap.add(list);
            }
        }

        // Dequeue min head node and enqueue output the node
        // enqueue remainders to Min Heap until remainders are empty

        ListNode dummy = new ListNode(0);
        ListNode node = dummy;

        while (minHeap.size() > 0) {// O(N Log k)
            ListNode min = minHeap.poll();// O(Log K)
            if (min.next != null) {
                minHeap.add(min.next);// O(Log K)
            }
            node.next = min;
            node = node.next;
        }

        return dummy.next;
    }
}
