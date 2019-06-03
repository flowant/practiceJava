package practice.tree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import static org.junit.Assert.assertArrayEquals;

public class BinaryTree {
    static class Node <T> {
        T value;
        Node<T> left = null;
        Node<T> right = null;
        public Node(T value) {
            this.value = value;
        }
    }

    int cnt = 10;
    int bound = 10;

    @Test
    public void testMakeTree() {
        Random rand = new Random();
        for (int i = 0; i < cnt; i++) {
            Integer[] array = new Integer[i + 1];
            for (int j = 0; j <= i; j++) {
                array[j] = rand.nextInt(bound);
            }

            Node<Integer> root = makeTree(array, 0, array.length - 1);
//            Node<Integer> root = makeTree(array, 0, array.length);

            ArrayList<Integer> list = new ArrayList<>();
            inOrder(list, root);
            Integer[] travel = list.toArray(Integer[]::new);

            assertArrayEquals(array, travel);

            System.out.println("\nArray");
            for (Integer j : array) {
                System.out.print(j + " ");
            }
            list = new ArrayList<>();
            preOrderStack(list, root);
            System.out.println("\npreOrder");
            for (Integer j : list) {
                System.out.print(j + " ");
            }
        }
    }

    public static <T> Node<T> makeTree(T[] array, int leftIndex, int rightIndex) {
        if (array == null || leftIndex < 0 || rightIndex >= array.length || rightIndex < leftIndex) {
            return null;
        }

        // In case of one element in subtree
        // l:3 r:3 1 / 2 = m:3
        // In case of two elements in subtree, we will make a root and a left child first.
        // l:3 r:4 1 / 2 = m:4
        // In case of 3 elements in subtree.
        // l:3 r:5 1 / 2 = m:4

        int middleIndex = (leftIndex + rightIndex + 1) / 2;
        Node<T> node = new Node<>(array[middleIndex]);

        node.left = makeTree(array, leftIndex, middleIndex - 1);
        node.right = makeTree(array, middleIndex + 1, rightIndex);

        return node;
    }

//    public static <T> Node makeTree(T[] array, int start, int len) {
//        if (array == null || start < 0 || start >= array.length
//                || len <= 0 || start + len > array.length) {
//            return null;
//        }
//
//        int half = len / 2;
//        Node<T> node = new Node<>(array[start + half]);
//
//        // idx  01234567
//        // len  12345678
//        // half 01122334
//
//        if (len > 1) { // has a left child
//            node.left = makeTree(array, start, half);
//        }
//
//        if (len > 2) { // has a right child
//            node.right = makeTree(array, start + half + 1, len - half - 1);
//        }
//
//        return node;
//    }

    public static <T> void inOrder(List<T> list, Node<T> node) {
        if (node == null || list == null) {
            return;
        }

        if (node.left != null) {
            inOrder(list, node.left);
        }

        list.add(node.value);

        if (node.right != null) {
            inOrder(list, node.right);
        }
    }

    public static <T> void preOrderStack(List<T> list, Node<T> node) {
        if (node == null || list == null) {
            return;
        }

        Stack<Node<T>> stack = new Stack<>();
        stack.push(node);

        while (stack.empty() == false) {
            Node<T> poped = stack.pop();
            list.add(poped.value);

            if (poped.right != null) {
                stack.push(poped.right);
            }

            if (poped.left != null) {
                stack.push(poped.left);
            }
        }

    }

}
