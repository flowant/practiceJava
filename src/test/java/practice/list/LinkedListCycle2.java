package practice.list;

/*
https://leetcode.com/problems/linked-list-cycle-ii/

Given a linked list, return the node where the cycle begins. If there is no cycle, return null.

To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed) in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.

Note: Do not modify the linked list.



Example 1:

Input: head = [3,2,0,-4], pos = 1
Output: tail connects to node index 1
Explanation: There is a cycle in the linked list, where tail connects to the second node.


Example 2:

Input: head = [1,2], pos = 0
Output: tail connects to node index 0
Explanation: There is a cycle in the linked list, where tail connects to the first node.


Example 3:

Input: head = [1], pos = -1
Output: no cycle
Explanation: There is no cycle in the linked list.




Follow up:
Can you solve it without using extra space?

 */
public class LinkedListCycle2 {

    public ListNode detectCycle(ListNode head) {
        // 1 - 2 - 3 - 4 - 5 - 6 - (3 cycle)
        // s 1 2 3 4 5 6 3 4 5 6 3
        // f 1 3 5 3 5
        // 1 2 3
        // A 3 - 1 = 2
        // B 5 - 3 = 2
        // N = 4
        // 4 = 2 + 2

        /*
         * - Slow pointer goes 1 step a time, fast pointer goes 2 steps a time.
         * - If slow and fast pointers are in the cycle then they are getting closer every iteration.
         * - Let's say A as the distance between head and the first node of cycle,
         *   B as the distance between the first node of cycle and the meeting node
         * - Let's say N as the distance of the cycle
         * - A + B = 2A + 2B - N
         * - N = A + B
         *
         * - test case
         * 1 - 2 - 3 - null => null
         * 1 - 1 => 1
         * 1 - 2 - 3 - 2 => 2
         * 1 - 2 - 2 => 2
         *
         */

        if (head == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                // here is meeting point, cycle detected.
                // fromHead and slow go for the distance A
                // The start point of cycle is where they are met.
                ListNode fromHead = head;
                while (slow != fromHead) {
                    slow = slow.next;
                    fromHead = fromHead.next;
                }
                return fromHead;
            }
        }

        return null;
    }

}
