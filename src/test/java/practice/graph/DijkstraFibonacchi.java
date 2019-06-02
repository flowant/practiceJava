package practice.graph;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class DijkstraFibonacchi {
    static class Vertex {
        int id;
        int distance = Integer.MAX_VALUE;
        Vertex previous;
        LinkedList<Edge> edges = new LinkedList<>();
        FibonacciHeap.Entry<Vertex> entry = null;

        public Vertex(int id, Edge... edges) {
            this.id = id;
            for (Edge e : edges) {
                this.edges.add(e);
            }
        }

        public void addEdge(Edge e) {
            edges.add(e);
        }
        public int getDistance() {
            return distance;
        }
    }

    static class Edge {
        private int value;
        private Vertex vertex;

        public Edge(Vertex vertex, int value) {
            this.value = value;
            this.vertex = vertex;
        }

        public int getValue() {
            return value;
        }

        public Vertex getVertex() {
            return vertex;
        }
    }
    @Test
    public void test() {
        String map =  "0100"
                    + "0001"
                    + "0100"
                    + "0001";

        int width = 10;

        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width * width; i++) {
            sb.append(String.valueOf(rand.nextInt(10)));
        }
        //map = sb.toString();

        int start = 0;
        int goal = 14;

        Vertex[] vertices = makeGraph(map, (int) Math.sqrt(map.length()), 1, '1');
        Assert.assertNotNull(vertices);
        Assert.assertTrue(dijkstra(vertices, start));
        print(vertices, start, goal);

        print(vertices, start, vertices.length - 1);
//        for (int i = 0; i < prevVertex.length; i++) {
//            print(prevVertex, start, i);
//        }
    }

    public static Vertex[] makeGraph(String map, int width, int distance, char hole) {
        if (map == null || map.length() == 0 || width < 1 || distance < 1) {
            return null;
        }

        int len = map.length();
        Vertex[] vertices = new Vertex[len];

        for (int i = 0; i < len; i++) {
            vertices[i] = new Vertex(i);
        }

        for (int i = 0; i < len; i++) {
            if (map.charAt(i) == hole) {
                continue;
            }

            Vertex v = vertices[i];

            // up edge
            if (i >= width && map.charAt(i - width) != hole) {
                v.addEdge(new Edge(vertices[i - width], distance));
            }
            // down edge
            if (i < len - width && map.charAt(i + width) != hole) {
                v.addEdge(new Edge(vertices[i + width], distance));
            }
            // left edge
            if (i % width != 0 && map.charAt(i - 1) != hole) {
                v.addEdge(new Edge(vertices[i - 1], distance));
            }
            // right edge
            if (i % width != width - 1 && map.charAt(i + 1) != hole) {
                v.addEdge(new Edge(vertices[i + 1], distance));
            }
        }

        return vertices;
    }

    public static boolean dijkstra(Vertex[] vertices, int start) {
        if (vertices == null || start < 0 || start >= vertices.length) {
            return false;
        }

        FibonacciHeap<Vertex> fHeap = new FibonacciHeap<>();

        vertices[start].distance = 0;

        for (int i = 0; i < vertices.length; i++) {
            vertices[i].entry = fHeap.enqueue(vertices[i], vertices[i].distance);
        }

        while (fHeap.isEmpty() == false) {
            Vertex nearest = fHeap.dequeueMin().getValue();

            for (Edge edgeOfNearest : nearest.edges) {
                int newDistance = nearest.distance + edgeOfNearest.getValue();

                Vertex nextToNearest = edgeOfNearest.getVertex();
                if (nextToNearest.distance > newDistance && newDistance >= 0) {
                    nextToNearest.distance = newDistance;
                    fHeap.decreaseKey(nextToNearest.entry, newDistance);

                    nextToNearest.previous = nearest;
                }
            }
        }

        return true;
    }

    public static boolean print(Vertex[] vertices, int start, int goal) {
        if (vertices == null || vertices.length == 0 || start < 0 || goal < 0
                || start >= vertices.length || goal >= vertices.length) {
            return false;
        }

        if (start == goal) {
            System.out.println("start and goal are the same: " + start);
            return true;
        }

        Stack<Integer> stack = new Stack<>();
        for (Vertex v = vertices[goal]; v.id != start; v = v.previous) {
            stack.push(v.id);
            if (v.previous == null) {
                System.out.println("there is no way from " + start + " to " + goal);
                return false;
            }
        }
        stack.push(start);

        System.out.println("path from " + start + " to " + goal + " : ");
        while (stack.empty() == false) {
            System.out.print(stack.pop());
            System.out.print(" ");
        }
        System.out.println();

        return true;
    }

}
