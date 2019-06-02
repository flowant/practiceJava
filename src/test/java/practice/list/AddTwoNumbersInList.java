package practice.list;

/*

https://leetcode.com/problems/add-two-numbers/

You are given two non-empty linked lists representing two non-negative integers.
The digits are stored in reverse order and each of their nodes contain a single digit.
Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Example:

Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8
Explanation: 342 + 465 = 807.
 */
public class AddTwoNumbersInList {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        /*
         * 1. Make dummy node to point the head of the list
         * 2. set the carry varialbe as 0
         * 3. Make new ListNode and set its reference to the next of the previous Node
         * 4. add carry value to the value of the current node
         * 5. if l1 node is not null add its value to the current node, and move to its next
         *    do the same thing to l2
         * 6. set carry the value as the current value divided by 10
         * 7. set the current value as the current value modulo 10
         * 8. go to step 4 until there are more nodes in l1 or l1, carry value is greater than 0.
         * 9. return dummy next
         *
         * Time Complexity: O(l1 + l2) where l1, l2 are the length of the input lists respectively.
         * Space Complexity: O(l1 + l2)
         *
         * Runtime: 2 ms, faster than 94.56% of Java online submissions for Add Two Numbers.
         * Memory Usage: 43.6 MB, less than 90.75% of Java online submissions for Add Two Numbers.
         */

        int carry = 0;
        ListNode dummy = new ListNode(0);
        ListNode node = dummy;
        while(l1 != null || l2 != null || carry == 1) {

            node.next = new ListNode(0);
            node = node.next;

            node.val += carry;

            if (l1 != null) {
                node.val += l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                node.val += l2.val;
                l2 = l2.next;
            }

            carry = node.val / 10;
            node.val %= 10;
        }

        return dummy.next;
    }
}
