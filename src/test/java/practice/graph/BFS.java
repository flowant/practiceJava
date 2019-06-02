package practice.graph;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;


public class BFS {
    static class Vertex {
        int id;
        Vertex from = null;
        LinkedList<Vertex> adjacency = new LinkedList<>();

        public Vertex(int id) {
            this.id = id;
        }

        public void addAdjacency(Vertex v) {
            adjacency.add(v);
        }
    }

    @Test
    public void test() {
        String map =  "0100"
                    + "0001"
                    + "0100"
                    + "0001";

        int start = 0;
        int goal = 14;

        Vertex[] vertices = makeGraph(map, (int) Math.sqrt(map.length()), 1, '1');
        Assert.assertNotNull(vertices);
        System.out.println("path length:" + bfs(vertices, start, goal));
        print(vertices, start, goal);
        print(vertices, start, vertices.length - 1);
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
                v.addAdjacency(vertices[i - width]);
            }
            // down edge
            if (i < len - width && map.charAt(i + width) != hole) {
                v.addAdjacency(vertices[i + width]);
            }
            // left edge
            if (i % width != 0 && map.charAt(i - 1) != hole) {
                v.addAdjacency(vertices[i - 1]);
            }
            // right edge
            if (i % width != width - 1 && map.charAt(i + 1) != hole) {
                v.addAdjacency(vertices[i + 1]);
            }
        }

        return vertices;
    }

    public static boolean bfs(Vertex[] vertices, int start, int goal) {
        if (vertices == null || start < 0 || start >= vertices.length
                || goal < 0 || goal >= vertices.length) {
            return false;
        }

        Queue<Vertex> q = new LinkedList<>();

        q.offer(vertices[start]);

        while (q.isEmpty() == false) {
            Vertex v = q.poll();
            if (v.id == goal) {
                return true;
            }
            for (Vertex adjacency : v.adjacency) {
                if (adjacency.from == null) {
                    adjacency.from = v;
                    q.offer(adjacency);
                }
            }
        }

        return false;
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
        for (Vertex v = vertices[goal]; v.id != start; v = v.from) {
            stack.push(v.id);
            if (v.from == null) {
                System.out.println("there is no way from " + start + " to " + goal);
                return false;
            }
        }
        stack.push(start);

        System.out.println("length of path from " + start + " to " + goal + " : " + stack.size() + ", path:");
        while (stack.empty() == false) {
            System.out.print(stack.pop());
            System.out.print(" ");
        }
        System.out.println();

        return true;
    }

}
