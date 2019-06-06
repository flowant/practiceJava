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
