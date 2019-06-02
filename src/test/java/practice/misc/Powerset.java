package practice.misc;/*
 Q. print powerset of given character array

   - iterate i from 0 to 2 ^ array size - 1
   - print '{'
   - check bitset of i, if nth bit is set then print nth element of array
   - print '}'

   - levelup: using BigInteger
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Powerset {

    /*
        https://leetcode.com/problems/subsets/

        Given a set of distinct integers, nums, return all possible subsets (the power set).
        Note: The solution set must not contain duplicate subsets.

        Example:

        Input: nums = [1,2,3]
        Output:
        [
          [3],
          [1],
          [2],
          [1,2,3],
          [1,3],
          [2,3],
          [1,2],
          []
        ]
     */

    /*
     * 1. Initialize Outer list with empty inner list.
     * 2. Copy All inner lists from outer list
     *    and add the element from the input array to each inner list
     *    and add all lists to outer list.
     * 3. Go step 2 until there are elements in the input array.
     *
     * Time Complexity and Space complexity are O(2^n)
     *
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        if (nums == null || nums.length == 0) {
            return result;
        }

        result.add(new ArrayList<>());

        for (int num : nums) {
            int size = result.size();
            for (int i = 0; i < size; i++) {
                List<Integer> inner = new ArrayList<>(result.get(i));
                inner.add(num);
                result.add(inner);
            }
        }

        return result;
    }

    public List<List<Integer>> subsetsBitManipulation(int[] nums) {
        if (nums.length > 31) {
            throw new IllegalArgumentException("the length of nums should be less than 32");
        }

        List<List<Integer>> result = new ArrayList<>();

        if (nums == null || nums.length == 0) {
            return result;
        }

        int power = 1 << nums.length;
        for (int bitset = 0; bitset < power; bitset++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                if ((bitset & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            result.add(subset);
        }

        return result;
    }

    public List<List<Integer>> subsetsBitManipulationBigInteger(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        if (nums == null || nums.length == 0) {
            return result;
        }

        BigInteger power = BigInteger.valueOf(2).pow(nums.length);
        BigInteger bigInt = BigInteger.valueOf(0);

        while(bigInt.compareTo(power) < 0) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                if (bigInt.testBit(i)) {
                    subset.add(nums[i]);
                }
            }
            result.add(subset);

            bigInt = bigInt.add(BigInteger.ONE);
        }
        return result;
    }

}