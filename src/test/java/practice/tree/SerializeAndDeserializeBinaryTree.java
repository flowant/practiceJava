package practice.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
 *
 * Serialization is the process of converting a data structure or object into a
 * sequence of bits so that it can be stored in a file or memory buffer, or
 * transmitted across a network connection link to be reconstructed later in the
 * same or another computer environment.
 *
 * Design an algorithm to serialize and deserialize a binary tree. There is no
 * restriction on how your serialization/deserialization algorithm should work.
 * You just need to ensure that a binary tree can be serialized to a string and
 * this string can be deserialized to the original tree structure.
 *
 * Example:
 *
 * You may serialize the following tree:
 *
 * 1 / \ 2 3 / \ 4 5
 *
 * as "[1,2,3,null,null,4,5]" Clarification: The above format is the same as how
 * LeetCode serializes a binary tree. You do not necessarily need to follow this
 * format, so please be creative and come up with different approaches yourself.
 *
 * Note: Do not use class member/global/static variables to store states. Your
 * serialize and deserialize algorithms should be stateless.
 *
 */
public class SerializeAndDeserializeBinaryTree {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        /*
         * 1. Initialize Queue, add root node 2. poll node from Queue 3. if node is null
         * then put 'n' character to StringBuilder 4. if node is not null then put
         * node.val to StringBuilder and put children to Queue 5. go to the step 2 until
         * Queue is not empty 6. to delete trailing n characters and delimiters, Check
         * the last element is the same as n or deletemiter character. if condition is
         * true then decrease index of StringBuilder and check again until the condition
         * is false. set new length using setLength method with index + 1 value.
         *
         */
        if (root == null) {
            return "";
        }

        StringBuilder tokens = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);

        while (queue.size() > 0) {
            TreeNode node = queue.poll();
            if (node == null) {
                tokens.append("n ");
            } else {
                tokens.append(node.val);
                tokens.append(' ');
                queue.add(node.left);
                queue.add(node.right);
            }
        }

        int i = tokens.length() - 1;
        while (i >= 0 && (tokens.charAt(i) == 'n' || tokens.charAt(i) == ' ')) {
            i--;
        }

        tokens.setLength(i + 1);

        return tokens.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        /*
         * 1. split string to make arrays named tokens 2. Initialize Queue. make new
         * TreeNode with tokens[0] value and enqueue to Queue 3. Initialize tokens array
         * index as 1 4. poll node from Queue 5. if tokens[i] value is not null then
         * make a TreeNode with tokens[i] and link to node.left and increase index. 6.
         * if tokens[i] value is not null then to the same things as step 5 except for
         * linking to node.right 7. go to step 4 until queue is not empty
         *
         */
        if (data == null || data.length() == 0) {
            return null;
        }

        String[] tokens = data.split(" ");
        TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);

        int i = 1;
        while (queue.size() > 0) {
            TreeNode node = queue.poll();

            if (i < tokens.length && tokens[i].equals("n") == false) {
                TreeNode leftNode = new TreeNode(Integer.parseInt(tokens[i]));
                node.left = leftNode;
                queue.add(leftNode);
            }

            i++;

            if (i < tokens.length && tokens[i].equals("n") == false) {
                TreeNode rightNode = new TreeNode(Integer.parseInt(tokens[i]));
                node.right = rightNode;
                queue.add(rightNode);
            }

            i++;

        }

        return root;
    }
}
