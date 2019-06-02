package practice.graph;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Random;

public class DijkstraTester {
    static String map =  "0100"
            + "0001"
            + "0100"
            + "0001";

    static int width = 900;
    static int start = 0;
    static int goal = width * width - 1;

    static {
        makeMap();
    }

    public static void makeMap() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < width * width; i++) {
            sb.append(String.valueOf(rand.nextInt(10)));
        }
        map = sb.toString();
    }

    @Test
    public void testMyHeap() {
        List<Vertex> vertices = Dijkstra.makeGraph(map, (int) Math.sqrt(map.length()), 1, '1');
        Assert.assertNotNull(vertices);
        Assert.assertTrue(Dijkstra.dijkstra(vertices, start));
        Dijkstra.print(vertices, start, goal);
    }

    @Test
    public void testFibonacciHeap() {
        DijkstraFibonacchi.Vertex[] vertices = DijkstraFibonacchi.makeGraph(map, (int) Math.sqrt(map.length()), 1, '1');
        Assert.assertNotNull(vertices);
        Assert.assertTrue(DijkstraFibonacchi.dijkstra(vertices, start));
        DijkstraFibonacchi.print(vertices, start, goal);
    }

    @Test
    public void testBFS() {
        BFS.Vertex[] vertices = BFS.makeGraph(map, (int) Math.sqrt(map.length()), 1, '1');
        Assert.assertNotNull(vertices);
        System.out.println("path length:" + BFS.bfs(vertices, start, goal));
        BFS.print(vertices, start, goal);
    }
}
