package practice.array;

/*
There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

You may assume nums1 and nums2 cannot be both empty.

Example 1:

nums1 = [1, 3]
nums2 = [2]

The median is 2.0
Example 2:

nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5
 */
public class MedianOfTwoSortedArrays {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

    /*
     Even
     1 3 5 7     2 4 6 8

       maxLeftX   minRightX
     1       3 |    5       7
       maxLeftY   minRightY
     2       4 |    6       8

     Odd
     1 3 5 7     2 4 6

       maxLeftX   minRightX
     1       3 |    5       7
       maxLeftY   minRightY
     2       4 |    6

     - Let's say the array has a smaller length as X, the other array is Y
     - We can partition each array into the left and right side respectively
     - If the length of the sum of each side is the same or left side has one more element
       then maxLeftX is less than or equals to minRightY and maxLeftY is less than equals to minRightX
     - The median value is the average of max(maxLeftX, maxLeftY) + min(minRightX, minRightY)
       when the number of whole elements are even.
     - Or max(maxLeftX, maxLeftY) when the number of whole elements are odd.
     - We are going to look for the partition index that satisfy the precondition.
     - partitionY = (X.length + Y.length + 1) / 2 - partitionX

     1. Do binary search to look for partitionX, partitionY can be calculated by the above equation.
     2. If the above precondition is satisfied then return median
     3. If maxLeftX is greater than minRightY then move partitionX leftward.
        Else move partitionY rightward.
     4. go to step 1 until the binary search is done.

     */
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        if (nums1.length == 0) {
            // 0 1 2 3, 2
            // 0 1 2, 1
            int mid = nums2.length / 2;
            if (nums2.length % 2 == 0) {
                return (nums2[mid] + nums2[mid - 1]) / 2.0;
            } else {
                return nums2[mid];
            }
        }

        // find partitionX, Y index that separate array into left side < right side
        // 1 | 3 5 7 {+inf}
        // 2 | 4 6  8

        int leftIndexX = 0;
        int rightIndexX = nums1.length;

        while(leftIndexX <= rightIndexX) {

            //int partitionX = leftIndexX + (rightIndexX - leftIndexX) / 2;
            int partitionX = (leftIndexX + rightIndexX) / 2;

            int partitionY = (nums1.length + nums2.length + 1) / 2 - partitionX;
            int leftX = (partitionX == 0 ? Integer.MIN_VALUE : nums1[partitionX - 1]);
            int rightX = (partitionX == nums1.length ? Integer.MAX_VALUE : nums1[partitionX]);
            int leftY = (partitionY == 0 ? Integer.MIN_VALUE : nums2[partitionY - 1]);
            int rightY = (partitionY == nums2.length ? Integer.MAX_VALUE : nums2[partitionY]);

            if (leftX <= rightY && leftY <= rightX) {
                if ((nums1.length + nums2.length) % 2 == 0) {
                    return (Math.max(leftX, leftY) + Math.min(rightX, rightY)) / 2.0;
                } else {
                    return (double) Math.max(leftX, leftY);
                }
            }

            if (leftX > rightY) {
                rightIndexX = partitionX - 1;
            } else { // leftY > rightX
                leftIndexX = partitionX + 1;
            }

        }

        throw new IllegalArgumentException();
    }
}
