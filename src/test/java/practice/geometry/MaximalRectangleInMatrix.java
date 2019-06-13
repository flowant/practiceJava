package practice.geometry;

import java.util.Stack;

/*

https://leetcode.com/problems/maximal-rectangle/

85. Maximal Rectangle
Hard

Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

Example:

Input:
[
  ["1","0","1","0","0"],
  ["1","0","1","1","1"],
  ["1","1","1","1","1"],
  ["1","0","0","1","0"]
]
Output: 6
 */
public class MaximalRectangleInMatrix {

    public int maximalRectangle(char[][] matrix) {
        /*
            1. Initialize an array whose length is the column length of the matrix
            2. If the current column of the current row is 1 then accumulate to the array.
            2-1. Else set the array value as 0
            3. Go to step 2 until the last column of the current row.
            4. Calculate maximumHistogramArea of the array.
            5. Go to step 2 until the last row.
        */

        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int max = 0;
        int[] hist = new int[matrix[0].length];

        for (int row = 0; row < matrix.length; row++) {

            for (int col = 0; col < matrix[0].length; col++) {
                hist[col] = (matrix[row][col] == '0' ? 0 : hist[col] + 1);
            }

            int curMax = largestRectangleArea(hist);
            if (curMax > max) {
                max = curMax;
            }
        }

        return max;
    }

    public int largestRectangleArea(int[] heights) {
        // null or empty histogram's maximum area value is 0
        if (heights == null || heights.length == 0) {
            return 0;
        }

        /*
         * 1. Initialize the max variable as 0
         * 2. Initialize Stack named leftIndices
         * 3. Initialize the rightIndex as 0
         * 4. If the leftIndices is empty or the height at rightIndex is greater than
              the height of the latest index in the stack
              then push current rightIndex to the stack and increase right index.
         * 5. Else pop leftIndex from the stack and calculate the are by the height at leftIndex times width.
         *    width is rightIndex - leftIndices.peek() - 1 or the rightIndex when stack is empty()
         * 6. The height times width is the area and the area is greater than max then replace max as the area.
         * 7. go to step 4 until rightIndex is less than the length of input array.
         * 8. do step 5 and 6 iteratively until the stack is not empty.
         */

        int max = 0;
        Stack<Integer> leftIndices = new Stack<>();
        int rightIndex = 0;

        while (rightIndex < heights.length) {
            if (leftIndices.empty() || heights[leftIndices.peek()] <= heights[rightIndex]) {
                leftIndices.push(rightIndex);
                rightIndex++;
            } else {
                int leftIndex = leftIndices.pop();

                int rectAngle = heights[leftIndex]
                        * (leftIndices.empty() ? rightIndex : rightIndex - leftIndices.peek() - 1);

                if (rectAngle > max) {
                    max = rectAngle;
                }
            }
        }

        while (leftIndices.empty() == false) {
            int leftIndex = leftIndices.pop();

            int rectAngle = heights[leftIndex]
                    * (leftIndices.empty() ? rightIndex : rightIndex - leftIndices.peek() - 1);

            if (rectAngle > max) {
                max = rectAngle;
            }
        }

        return max;
    }

}
