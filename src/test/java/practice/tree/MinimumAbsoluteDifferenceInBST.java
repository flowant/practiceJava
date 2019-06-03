package practice.tree;

import java.util.Stack;

/**
 * https://leetcode.com/problems/minimum-absolute-difference-in-bst/
 *
 * Given a binary search tree with non-negative values, find the minimum
 * absolute difference between values of any two nodes.
 *
 * Example:
 *
 * Input:
 *
 * 1 \ 3 / 2
 *
 * Output: 1
 *
 * Explanation: The minimum absolute difference is 1, which is the difference
 * between 2 and 1 (or between 2 and 3).
 *
 *
 * Note: There are at least two nodes in this BST.
 *
 */
public class MinimumAbsoluteDifferenceInBST {
    // inorder traversal without recursion
    // 1. set cur as root
    // 2. push cur to stack and set cur as cur.left until cur is not null
    // 3. pop node from stack and set cur as poped node. and use
    // 4. set cur as cur.right
    // 5. go to step 2 until cur is not null or stack is not empty
    public int getMinimumDifference(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        TreeNode prev = null;
        TreeNode cur = root;
        Stack<TreeNode> stack = new Stack<>();

        while (cur != null || stack.empty() == false) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.pop();

            if (prev != null) {
                int curMin = cur.val - prev.val;
                min = min > curMin ? curMin : min;
            }

            prev = cur;
            cur = cur.right;
        }
        return min;
    }
}
