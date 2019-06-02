package practice.list;

public class Node<T> {

    T item;
    Node<T> prev = null;
    Node<T> next = null;

    public Node(T value) {
        this.item = value;
    }

}
