package practice.dp;

/*
https://leetcode.com/problems/maximal-square/

Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

Example:

Input:

1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0

Output: 4

 */

public class MaximalSquare {
    public int maximalSquare(char[][] matrix) {
        /*
         * 1. make a empty acc matrix 2. if the element is in the top line or the
         * leftmost line fill the value from matrix to acc without calculation. 3. if
         * the element value from matrix is zero then fill the value from matrix to acc
         * without calculation. 4. else calculate minimum value among the element
         * adjacent to top and left direction 5. add the adjacent minimum value to the
         * value from matrix and save it to acc 6. if acc value is greater than max then
         * change max value 7. go to step 2 until the bottom right element is caculated.
         */
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int max = '0';

        char[][] acc = new char[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i == 0 || j == 0 || matrix[i][j] == '0') {
                    acc[i][j] = matrix[i][j];
                } else {
                    acc[i][j] = (char) (Math.min(acc[i][j - 1], Math.min(acc[i - 1][j - 1], acc[i - 1][j]))
                            + matrix[i][j] - '0');
                }

                if (max < acc[i][j]) {
                    max = acc[i][j];
                }
            }
        }

        max -= '0';

        return max * max;
    }
}
