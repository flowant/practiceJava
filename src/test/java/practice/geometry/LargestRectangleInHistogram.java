package practice.geometry;

import java.util.Stack;

/*
Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.




Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].




The largest rectangle is shown in the shaded area, which has area = 10 unit.



Example:

Input: [2,1,5,6,2,3]
Output: 10
 */
public class LargestRectangleInHistogram {

    public int largestRectangleArea(int[] heights) {
        // null or empty histogram's maximum area value is 0
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int max = 0;

        Stack<Integer> leftIndexes = new Stack<>();

        int rightIndex = 0;

        while (rightIndex < heights.length || leftIndexes.empty() == false) {

            if (rightIndex != heights.length
                    && (leftIndexes.empty() || heights[leftIndexes.peek()] <= heights[rightIndex])) {

                leftIndexes.push(rightIndex);
                rightIndex++;
            } else {
                int leftIndex = leftIndexes.pop();

                int rectAngle = heights[leftIndex]
                        * (leftIndexes.empty() ? rightIndex : rightIndex - leftIndexes.peek() - 1);

                // indexes: left:height:right = rectAngle, 2:3:4 = 6, 1:2:4 = 10
                if (rectAngle > max) {
                    max = rectAngle;
                }
            }

        }

        /*
         * while(leftIndexes.empty() == false) { int leftIndex = leftIndexes.pop();
         *
         * int rectAngle = heights[leftIndex] (leftIndexes.empty() ? rightIndex :
         * rightIndex - leftIndexes.peek() - 1);
         *
         * //indexes: left:height:right = rectAngle, 4:5:6 = 3, 1:4:6 = 8, empty:1:6 = 6
         * if (rectAngle > max) { max = rectAngle; } }
         */

        return max;
    }
}
