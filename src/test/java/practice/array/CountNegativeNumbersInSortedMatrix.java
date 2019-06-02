package practice.array;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CountNegativeNumbersInSortedMatrix {

    // Count Negative Numbers in a Column-Wise and Row-Wise Sorted Matrix

    // given row-wise and column-wise sorted array,
    // count the number of negative integers.

    // test case 6
    // -5 -3 -2 1
    // -4 -2  1 2
    // -2  1  2 3
    //  0  2  3 4

    // 1. From the bottom left, if current index greater than or equals to length of the row or
    // the current element is not negative go up
    // and accumulate the current column index to the sum.
    // 2. else go right
    // 3. go to step 1 until the top row

    // time Complexity O(row + column -1)
    // space complexity O(1)

    public static int count(int[][] in) {
        if (in == null || in.length == 0 || in[0].length == 0) {
            return 0;
        }

        int j = 0;
        int i = in.length - 1;
        int sum = 0;

        while (i >= 0) {
            if (j >= in[0].length || in[i][j] >= 0) {
                sum += j;
                i--;
            } else {
                j++;
            }
        }

        return sum;
    }


    // -5 -3 -2 1
    // -4 -2  1 2
    // -2  1  2 3
    //  0  2  3 4
    public static int count2(int[][] in) {
        if (in == null || in.length == 0 || in[0].length == 0) {
            return 0;
        }

        int i = 0;
        int j = in[0].length - 1;
        int sum = 0;

        while(i < in.length && j >= 0) {
            if (in[i][j] < 0) {
                sum += j + 1;
                i++;
            } else {
                j--;
            }
        }

        return sum;
    }

    @Test
    public void testPartition() {
        int case1[][] = new int[][] {
            {-5, -3, -2, 1},
            {-4, -2,  1, 2},
            {-2,  1,  2, 3},
            { 0,  2,  3, 4}
        };

        int case2[][] = new int[][] {
            {-5, -3, -3, -2},
            {-4, -3, -2, -1},
            {-2,  1,  2, 3}
        };

        int case3[][] = new int[][] {
            {-5, -3},
            {-4, -3},
        };

        int case4[][] = new int[][] {
            {5, 3},
            {4, 3},
        };

        assertEquals(0, CountNegativeNumbersInSortedMatrix.count(null));
        assertEquals(0, CountNegativeNumbersInSortedMatrix.count(new int[][] {{}}));
        assertEquals(6, CountNegativeNumbersInSortedMatrix.count(case1));
        assertEquals(9, CountNegativeNumbersInSortedMatrix.count(case2));
        assertEquals(4, CountNegativeNumbersInSortedMatrix.count(case3));
        assertEquals(0, CountNegativeNumbersInSortedMatrix.count(case4));

        assertEquals(0, CountNegativeNumbersInSortedMatrix.count2(null));
        assertEquals(0, CountNegativeNumbersInSortedMatrix.count2(new int[][] {{}}));
        assertEquals(6, CountNegativeNumbersInSortedMatrix.count2(case1));
        assertEquals(9, CountNegativeNumbersInSortedMatrix.count2(case2));
        assertEquals(4, CountNegativeNumbersInSortedMatrix.count2(case3));
        assertEquals(0, CountNegativeNumbersInSortedMatrix.count2(case4));
    }

}
