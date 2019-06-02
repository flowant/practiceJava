package practice.list;

/*

https://leetcode.com/problems/reverse-nodes-in-k-group/

Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.

k is a positive integer and is less than or equal to the length of the linked list.
If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.

Example:

Given this linked list: 1->2->3->4->5

For k = 2, you should return: 2->1->4->3->5

For k = 3, you should return: 3->2->1->4->5

Note:

Only constant extra memory is allowed.
You may not alter the values in the list's nodes, only nodes itself may be changed.
 */
public class ReverseNodesInKGroup {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k < 2) {
            return head;
        }

        ListNode dummy = new ListNode(0);

        // input is [1, 2, 3, 4, 5] and k is 2
        // k = 2, 2 -> 1 -> 3 -> 4P -> CH, CT 5

        ListNode prevSubListTail = dummy;
        ListNode nextSubListHead = null;
        ListNode currHead = head;
        ListNode currTail = head;
        int cnt = 1;
        while (currTail != null) {
            if (currTail.next == null && cnt < k) {
                prevSubListTail.next = currHead;
            } else if (cnt == k) {
                cnt = 0;
                nextSubListHead = currTail.next;
                prevSubListTail.next = currTail;
                prevSubListTail = currHead;
                currTail = currHead;
                currHead = reverse(currHead, k);
                currTail.next = nextSubListHead;
                currHead = nextSubListHead;
            }
            currTail = currTail.next;
            cnt++;
        }

        return dummy.next;
    }

    public static ListNode reverse(ListNode head, int k) {
        ListNode dummy = new ListNode(0);

        for (int i = 0; i < k; i++) {
                ListNode next = head.next;
                head.next = dummy.next;
                dummy.next = head;
                head = next;
        }

        return dummy.next;
    }
}
