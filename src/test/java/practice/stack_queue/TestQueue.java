package practice.stack_queue;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TestQueue {
    static class Edge {
        int index;
        int weight;

        public Edge(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }

        public int getIndex() {
            return index;
        }
        public String toString() {
            return "i:" + index + ", weight:" + weight;
        }
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(7);
        pq.add(1);
        pq.add(3);
        pq.add(3);

        System.out.println(pq.peek());
        System.out.println(pq.peek());

        System.out.println(pq.size());


        PriorityQueue<Edge> d = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        Edge e = new Edge(1,10);
        d.add(e);

        Edge t = new Edge(1,7);
        d.add(t);

        System.out.println(d.size());
        System.out.println(d.peek());

        e.weight = 1;

        System.out.println(d.size());
        System.out.println(d.peek());

        d.add(e);
        System.out.println(d.size());
        System.out.println(d.peek());

        System.out.println("last");
        System.out.println(d.poll());
        System.out.println(d.size());
        System.out.println(d.poll());
        System.out.println(d.size());
        System.out.println(d.poll());
        System.out.println(d.size());
    }
}
