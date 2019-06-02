package practice.geometry;

/*
https://leetcode.com/problems/rectangle-area/

Find the total area covered by two rectilinear rectangles in a 2D plane.

Each rectangle is defined by its bottom left corner and top right corner as shown in the figure.

Rectangle Area

Example:

Input: A = -3, B = 0, C = 3, D = 4, E = 0, F = -1, G = 9, H = 2
Output: 45
Note:

Assume that the total area is never beyond the maximum possible value of int.

 */
public class RectangleArea {
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {

        /*
         * the answer will be the sum of two rectangle ares minus overlaped area
         *
         * 1. make function to calculate area from a,b,c,d parameters 2. calculate and
         * sum two rectangle area.
         *
         * 2. min(rightsides) - max(leftsides) is width of overlapped area, if the value
         * is greater than 0 then the axis is overlapped. we are going to make function
         * named distance to calculate this.
         *
         * 3. use distance function again to calculate overlapped height.
         *
         * 5. if overlapped width and height are greater than zero then multiply them to
         * calculate area. subtract overlapped area from sum.
         */

        if (A > C || B > D || E > G || F > H) {
            return 0;
        }

        int sum = areaRectangle(A, B, C, D) + areaRectangle(E, F, G, H);
        // 24 + 27 = 51

        int overlappedHeight = distance(B, F, D, H);
        int overlappedWidth = distance(A, E, C, G);
        if (overlappedHeight > 0 && overlappedWidth > 0) {
            sum -= overlappedHeight * overlappedWidth;
            // 51 - (3 * 2) = 45
        }

        return sum;
    }

    public int areaRectangle(int A, int B, int C, int D) {
        return (C - A) * (D - B);
    }
    // 6 * 4 = 24, 9 * 3 = 27
    // -1500000001 0 -1500000000 1, 1500000000 0 1500000001 1
    // 1 * 1 = 1, 1

    public int distance(int smallerSideA, int smallerSideB, int greaterSideA, int greaterSideB) {
        int greaterSide = Math.min(greaterSideA, greaterSideB);
        int smallerSide = Math.max(smallerSideA, smallerSideB);

        if (greaterSide < smallerSide) {
            return Integer.MIN_VALUE;
        }

        if (greaterSide > 0 && smallerSide < 0 && greaterSide > Integer.MAX_VALUE + smallerSide) {
            throw new RuntimeException("overflow");
        }

        return greaterSide - smallerSide;
    }
    // x axis, min(3, 9) - max(0, -3) = 3
    // y axis, min(4, 2) - max(0, -1) = 2
    // move the first rectangle to left by 10, x axis min(-7, 9) - max(-13, 0) = -7
    // - 0 = -7, not overlapped
    // move the first rectangle to left by 3, x axis min(0, 9) - max(-6, 0) = 0 - 0
    // = 0

    // -1500000001 0 -1500000000 1, 1500000000 0 1500000001 1
    // min(-1500000000, 1500000001) - max(1500000000, -1500000001) -1500000000 -
    // 1500000000

}
