package practice.bits;

/*

https://leetcode.com/problems/missing-number/

268. Missing Number
Easy

Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.

Example 1:

Input: [3,0,1]
Output: 2
Example 2:

Input: [9,6,4,2,3,5,7,0,1]
Output: 8
Note:
Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?
 */

public class MissingNumber {

    /*
     * Xor (^) operator return set bit when inputs are different.
     * In terms of Integer, A xor 0 returns A. A xor A returns 0.
     * we can apply xor operation to all expected number and actual input number
     * then we got the missing number.
     */

    public int missingNumber(int[] nums) {
        int xor = 0;
        int i = 0;

        for (; i < nums.length; i++) {
            xor ^= nums[i];
            xor ^= i;
        }

        xor ^= i;

        return xor;
    }
}
