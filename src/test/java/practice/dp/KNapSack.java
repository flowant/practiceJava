package practice.dp;

import org.junit.Assert;
import org.junit.Test;

public class KNapSack {

    @Test
    public void test() {
        int[][] valueWeight = {{1, 1}, {6, 2}, {18, 5}, {22, 6}, {28, 7}};
        Assert.assertEquals(50, knapsack(valueWeight, 13));
    }

    public static int max(int a, int b) {
        return a > b ? a : b;
    }

    public static int knapsack(int[][/*value, weight*/] vw, int limit) {
        if (limit == 0) {
            return 0;
        }

        int[][] opt = new int[vw.length + 1][limit + 1];

        for (int subset = 0; subset < opt.length; subset++) {
            for (int w = 0; w < opt[0].length; w++) {
                if (subset == 0 || w == 0) {
                    opt[subset][w] = 0;
                } else if (vw[subset - 1][1] > w) { // item weight > limit, use opt value from s - 1
                    opt[subset][w] = opt[subset - 1][w];
                } else { // max between don't take item and take item
                    opt[subset][w] = max(opt[subset - 1][w], vw[subset - 1][0] + opt[subset - 1][w - vw[subset - 1][1]]);
                }
            }
        }
        // s 2 (vw index = 1)

        // debug
        for (int s = 0; s < opt.length; s++) {
            for (int w = 0; w < opt[0].length; w++) {
                System.out.print(opt[s][w] + " ");
            }
            System.out.println();
        }

        return opt[opt.length -1][limit];
    }

}
