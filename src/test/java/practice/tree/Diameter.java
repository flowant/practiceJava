package practice.tree;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class Diameter {
    static class Node {
        int data;
        Node left, right;
        public Node() {
        }
        public Node(int data) {
            this.data = data;
        }
    }

    /*

I'm going to use a recursive function named "Diameter".
The function's input parameters are a node and height.
height type is AtomicInteger which keep it's storage between a function call and return.
The function returns 0 if a node parameter has no child.
store the height variable with one plus the maximum height among children's height.
The function return diameter that is the maximum value between return values of recursive call and a sum of children's heights + 1.

     */
    public int diameter(Node node, AtomicInteger height) {
        if (node == null) {
            return 0;
        }

        AtomicInteger lHeight = new AtomicInteger(height.get());
        AtomicInteger rHeight = new AtomicInteger(height.get());

        int lDiameter = diameter(node.left, lHeight);
        int rDiameter = diameter(node.right, rHeight);
        int diameter = lHeight.get() + rHeight.get() + 1;

        height.set(Math.max(lHeight.get(), rHeight.get()) + 1);

        return Math.max(diameter, Math.max(lDiameter, rDiameter));
    }

    public int diameter(Node node) {
        return diameter(node, new AtomicInteger());
    }

    @Test
    public void test() {
        /*
                  1
                /   \
               2      3
              / \
            4     5
         */
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);

        System.out.println(diameter(root));
    }

}