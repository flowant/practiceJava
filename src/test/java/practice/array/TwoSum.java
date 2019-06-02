package practice.array;

import java.util.HashMap;

/*
https://leetcode.com/problems/two-sum/

Given an array of integers, return indices of the two numbers such that they add up to a specific target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

Example:

Given nums = [2, 7, 11, 15], target = 9,

Because nums[0] + nums[1] = 2 + 7 = 9,
return [0, 1].

 */
public class TwoSum {
    /*

    https://leetcode.com/problems/two-sum/

    1. Initialize HashMap.
    2. if Map contains the current value as a key
       then return the current index and its value of the map entry
    3. Else Calculate the target value's complement value of the current element
       and put a new map entry which has the complement as a key, current loop index as a value.
    4. go to step 2 until the last element array.

    TimeCompelexity: O(N) where n is the length of the input array
    SpaceComplexity: O(N)

    Runtime: 2 ms, faster than 99.25% of Java online submissions for Two Sum.
    Memory Usage: 36.9 MB, less than 99.56% of Java online submissions for Two Sum.

    */
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return null;
        }

        HashMap<Integer, Integer> complementMap = new HashMap<>();
        int[] answer = new int[2];
        for (int i = 0; i < nums.length; i++) {

            if (complementMap.containsKey(nums[i])) {
                answer[0] = complementMap.get(nums[i]);
                answer[1] = i;
                return answer;
            }

            int complement = target - nums[i];
            complementMap.put(complement, i);
        }

        return null;
    }

}
