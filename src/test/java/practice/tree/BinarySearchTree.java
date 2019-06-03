package practice.tree;

public class BinarySearchTree<E extends Comparable<E>> {

    static class Node<E extends Comparable<E>> {
        E key;
        Node<E> parent = null;
        Node<E> left = null;
        Node<E> right = null;
        public Node(E key) {
            this.key = key;
        }
    }

    Node<E> root;

    public Node<E> minimum(Node<E> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public Node<E> successor(Node<E> node) {
        if (node.right != null) {
            return minimum(node);
        }
        Node<E> parent = node.parent;
        while (parent != null && node == parent.right) {
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }

    public Node<E> delete(Node<E> node) {
        Node<E> willBeDeleted = null;
        if (node.left == null || node.right == null) {
            willBeDeleted = node;
        } else {
            willBeDeleted = successor(node);
        }
        Node<E> child = null;
        if (willBeDeleted.left != null) {
            child = willBeDeleted.left;
        } else {
            child = willBeDeleted.right;
        }
        if (child != null) {
            child.parent = willBeDeleted.parent;
        }
        if (willBeDeleted.parent == null) {
            root = child;
        } else if (willBeDeleted == willBeDeleted.parent.left) {
            willBeDeleted.parent.left = child;
        } else {
            willBeDeleted.parent.right = child;
        }
        if (willBeDeleted != node) {
            node.key = willBeDeleted.key;
        }
        return willBeDeleted;
    }

    public void insert(E key) {
        Node<E> newNode = new Node<>(key);
        Node<E> parent = null;
        Node<E> node = root;

        while (node != null) {
            parent = node;
            if (key.compareTo(node.key) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        newNode.parent = parent;

        if (parent == null) {
            root = newNode;
        } else if (newNode.key.compareTo(parent.key) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    public Node<E> search(Node<E> node, E key) {
        while (node != null && key.compareTo(node.key) != 0) {
            if (key.compareTo(node.key) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    public void walkInOrder(Node<E> node) {
        if (node != null) {
            walkInOrder(node.left);
            System.out.println(node.key);
            walkInOrder(node.right);
        }
    }

    public void walkPreOrder(Node<E> node) {
        if (node != null) {
            System.out.println(node.key);
            walkPreOrder(node.left);
            walkPreOrder(node.right);
        }
    }

    public void walkPostOrder(Node<E> node) {
        if (node != null) {
            walkPostOrder(node.left);
            walkPostOrder(node.right);
            System.out.println(node.key);
        }
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        tree.insert(5);
        tree.insert(3);
        tree.insert(7);
        tree.insert(1);
        tree.insert(2);
        tree.insert(6);
        tree.insert(8);

        tree.delete(tree.search(tree.root, 2));
        tree.delete(tree.search(tree.root, 8));
        tree.delete(tree.search(tree.root, 1));

        tree.walkInOrder(tree.root);
//        tree.walkPreOrder(tree.root);
//        tree.walkPostOrder(tree.root);

    }
}
