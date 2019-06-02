package practice.graph;

import org.junit.Test;

public class FloydWarshallAllToAll {
    @Test
    public void testFindPathAlltoAll() {
        int[][] graph = {{0, 1, INF, 2, INF},
                        {INF, 0, INF, INF, 4},
                        {10, INF, 0, 5, INF},
                        {3, 9, INF, 0, 2},
                        {INF, 6, 7, INF, 0}};

        int[][] path = findPathAllToAll(graph);

        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[0].length; j++) {
                System.out.print(path[i][j] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < path.length; i++) {
            for(int j = 0; j < path.length; j++) {
                System.out.println("from " + i + " to " + j);
                printPath(i, j, path);
            }
        }
    }

    static void printPath(int start, int goal, int[][] path) {
        if (path[start][goal] == -1) {
            return;
        }
        printPath(start, path[start][goal], path);
        System.out.print(path[start][goal] + " ");
        printPath(path[start][goal], goal, path);
    }

    static final int INF = Integer.MAX_VALUE/3;

    public static int[][] findPathAllToAll(int[][] graph) {
        if (graph == null || graph.length < 1 || graph[0].length != graph.length) {
            return null;
        }

        int n = graph.length;
        int[][] opt = new int[n][n];
        int[][] path = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                opt[i][j] = graph[i][j];
                path[i][j] = -1;
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int min = opt[i][k] + opt[k][j];
                    if (opt[i][j] > min) {
                        opt[i][j] = min;
                        path[i][j] = k;
                    }
                }
            }
        }

        for (int i = 0; i < opt.length; i++) {
            for (int j = 0; j < opt[0].length; j++) {
                System.out.print(opt[i][j] + " ");
            }
            System.out.println();
        }

        return path;
    }
}
