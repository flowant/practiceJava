package practice.graph;

import org.junit.Test;

public class DijkstraMatrix {

    final static int N = -1; // No way or No Edge, Value should be negative
    final static int NO_PREV_VERTEX = -3; // Value should be negative

    @Test
    public void testDijkstra() {

        int[][] graph = {{N, 1, N, 2, N, N},
                         {N, N, N, N, 4, N},
                         {10, N, N, 5, N, N},
                         {3, 9, N, N, 2, N},
                         {N, 6, 7, N, N, N},
                         {N, N, N, N, 1, N}};

        int start = 2;
        int[] prevVertex = null;

        prevVertex = dijkstra(graph, start);

        for (int i = 0; i < prevVertex.length; i++) {
            printPath(prevVertex, start, i);
        }
    }

    public static int[] dijkstra(int[][] graph, int start) {
        if (start >= graph.length) {
            return null;
        }

        int[] distance = new int[graph.length];
        int[] prevVertex = new int[graph.length];
        boolean[] visited = new boolean[graph.length];

        for (int i = 0; i < graph.length; i++) {
            distance[i] = Integer.MAX_VALUE;
            prevVertex[i] = NO_PREV_VERTEX;
            visited[i] = false;
        }

        distance[start] = 0;

        for (int i = 0; i < graph.length - 1; i++) {
            int nearestVertex = minIndex(distance, visited);
            if (nearestVertex == NO_PREV_VERTEX) {
                return prevVertex;
            }
            visited[nearestVertex] = true;


            for (int j = 0; j < graph.length; j++) {
                int distanceOfJ = graph[nearestVertex][j];
                if (distanceOfJ != N) {
                    int newDistance = distance[nearestVertex] + distanceOfJ;
                    if (distance[j] > newDistance){
                        distance[j] = newDistance;
                        prevVertex[j] = nearestVertex;
                    }
                }
            }
        }

        return prevVertex;
    }

    public static int minIndex(int[] distance, boolean[] visited) {
        int minIndex = NO_PREV_VERTEX;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < distance.length; i++) {
            if (visited[i] == false && distance[i] < min) {
                min = distance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public static boolean printPath(int[] prevVertex, int start, int goal) {
        if (start == goal) {
            System.out.println("start and goal are the same as " + start);
            return true;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(goal).append(' ');
        for (int i = goal; prevVertex[i] != start; i = prevVertex[i]) {
            if (prevVertex[i] == NO_PREV_VERTEX) {
                System.out.println("there is no path from " + start + " to " + goal);
                return false;
            }
            sb.append(prevVertex[i]).append(' ');
        }
        sb.append(start).append(' ');
        sb.reverse();

        System.out.println("from " + start + " to " + goal + ": " + sb.toString());
        return true;
    }
}
