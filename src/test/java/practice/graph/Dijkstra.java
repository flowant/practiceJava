package practice.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Test;

class Vertex {

    int id;
    int distance;
    Vertex previous;
    List<Edge> edges = new LinkedList<>();

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

class Edge {
    private int weight;
    private Vertex adjacency;

    public Edge(Vertex adjacency, int weight) {
        this.weight = weight;
        this.adjacency = adjacency;
    }

    public int getWeight() {
        return weight;
    }

    public Vertex getAdjacency() {
        return adjacency;
    }
}

public class Dijkstra {

    @Test
    public void test() {
        String map =  "0100"
                    + "0001"
                    + "0100"
                    + "0001";

        // or make map randomly.
        int width = 10;
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width * width; i++) {
            sb.append(String.valueOf(rand.nextInt(10)));
        }
        map = sb.toString();

        int start = 1;
        int goal = 14;

        List<Vertex> vertices = makeGraph(map, (int) Math.sqrt(map.length()), 1, '1');
        Assert.assertNotNull(vertices);
        Assert.assertTrue(dijkstra(vertices, start));

        print(vertices, start, goal);
        print(vertices, start, vertices.size() - 1);

    }

    public static List<Vertex> makeGraph(String map, int width, int distance, char hole) {
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

        return new ArrayList<Vertex>(Arrays.asList(vertices));
    }

    public static boolean dijkstra(List<Vertex> vertices, int start) {
        /*
         * 1. Initialize minHeap.
         * 2. Initialize the distance of start vertex as 0 and set distances of other vertices as infinity,
         * 3. add all vertices to minHeap with comparator using distance
         * 4. poll vertex has minimum distance from the minHeap
         * 5. if the sum of the distance of the vertex and the weight of the edge is less than
         *    the distance of adjacent vertex via the edge
         *    then update the distance of adjacent vertex and set its previous vertex as current vertex
         *    and update adjacent vertex in the minHeap.
         * 6. goto step 4 until the minHeap has elements.
         *
         * TimeComplexity: O(VlogV + ELogV) where V is the number of Vertices, and E is the number of Edges.
         * If we can use Fibonacci Heap then O(VlogV + E) because its update takes O(1),
         * SpaceComplexity: O(V)
         */
        if (vertices == null || start < 0 || start >= vertices.size()) {
            return false;
        }

        UpdatablePriorityQ<Vertex> priQ = new UpdatablePriorityQ<>(Comparator.comparingInt(Vertex::getDistance));
        //PriorityQueue<Vertex> priQ = new PriorityQueue<>(vertices.size(), Comparator.comparingInt(Vertex::getDistance));

        for (Vertex v : vertices) {
            v.distance = v.id == start ? 0 : Integer.MAX_VALUE;
            priQ.offer(v);
        }

        while (priQ.isEmpty() == false) {
            Vertex nearest = priQ.poll();

            for (Edge edgeOfNearest : nearest.edges) {
                int newDistance = nearest.distance + edgeOfNearest.getWeight();

                Vertex nextToNearest = edgeOfNearest.getAdjacency();
                // negative newDistance means overflow
                if (nextToNearest.distance > newDistance && newDistance >= 0) {
                    nextToNearest.distance = newDistance;
                    priQ.update(nextToNearest); // O(logn)
                    //priQ.remove(nextToNearest); // O(N)
                    //priQ.offer(nextToNearest);  // O(logN)

                    nextToNearest.previous = nearest;
                }
            }
        }

        return true;
    }

    public static boolean print(List<Vertex> vertices, int start, int goal) {
        if (vertices == null || vertices.size() == 0 || start < 0 || goal < 0
                || start >= vertices.size() || goal >= vertices.size()) {
            return false;
        }

        if (start == goal) {
            System.out.println("start and goal are the same: " + start);
            return true;
        }

        Stack<Integer> stack = new Stack<>();
        for (Vertex v = vertices.get(goal); v.id != start; v = v.previous) {
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
