package practice.array;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
https://leetcode.com/problems/permutations/

46. Permutations

Given a collection of distinct integers, return all possible permutations.

Example:

Input: [1,2,3]
Output:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]

 */
public class Permutations {
    public List<List<Integer>> permute(int[] nums) {
        /*
            nums: 1, 2, 3


            Initialize with empty array
            []
                                           (the index of the current element to be put)
            Iterate size of outer list      1
            Iterate size + 1 of inner list  1
            [1]      0

            Iterate size of outer list       1
            Iterate size + 1 of inner list   2
            [2, 1]   0
            [1, 2]   1

            Iterate size of outer list       2
            Iterate size + 1 of inner list   3 // 3 * 2
            [3, 2, 1]  0
            [2, 3, 1]  1
            [2, 1, 3]  2

            Iterate size + 1 of inner list   3
            [3, 1, 2] 0
            [1, 3, 2] 1
            [1, 2, 3] 2

            Iterate size of outer list       6 = 3 * 2
            Iterate size + 1 of inner list   4
            [4, 3, 2, 1]  0
            [3, 4, 2, 1]  1
            [3, 2, 4, 1]  2
            [3, 2, 1, 4]  3
            ...

            Iterate size of outer list       24 = 4 * 3 * 2
            Iterate size + 1 of inner list   5

            TimeComplexity : O(L!L) where L is the length of the input array.
            SpaceComplexity: O(L!)
            The iterating takes factorial. list.add(index, value) takes L.

            Runtime: 1 ms, faster than 99.71% of Java online submissions for Permutations.
            Memory Usage: 36.3 MB, less than 97.57% of Java online submissions for Permutations.

        */
        LinkedList<List<Integer>> outer = new LinkedList<>();

        if (nums == null || nums.length == 0) {
            return outer;
        }

        outer.add(new LinkedList<Integer>());       // outer: [ [3, 2, 1], [2, 3, 1], [2, 1, 3]
                                                    //  [3, 1, 2], [1, 3, 2], [1, 2, 3]]
        for (int num : nums) {                      // 3
            int outerSize = outer.size();           // outerSize: 2
            for (; outerSize > 0; outerSize--) {
                List<Integer> inner = outer.pollFirst();  // [1, 2]
                for (int i = 0; i <= inner.size(); i++) { // i:0
                    List<Integer> permutation = new LinkedList<>(inner); // [1, 2, 3]
                    permutation.add(i, num);
                    outer.add(permutation);
                }
            }
        }

        return outer;
    }

    public List<List<Integer>> permuteSwap(int[] nums) {
    /*
                  [1,2,3], outer list, size 1

                          swap using following indices based on the element of outer list
                          the outer list: []
                          the polled element: [1,2,3]
                          indices
                  [1,2,3], 0 0
                  [2,1,3], 0 1
                  [3,2,1]  0 2
                          add above elements to the outer list
                          size of outer list 3

                          the outer list: [[2,1,3], [3,2,1]]
                          the polled element: [1,2,3]
                          indices
                  [1,2,3], 1 1
                  [1,3,2]  1 2
                          add outer list: [[2,1,3], [3,2,1], [1,2,3], [1,3,2]]

                  the outer list: [[3,2,1], [1,2,3], [1,3,2]]
                          the polled element: [2,1,3]
                          indices
                  [2,1,3], 1 1
                  [2,3,1]  1 2
                          add outer list: [[3,2,1], [1,2,3], [1,3,2], [2,1,3], [2,3,1]]

                  the outer list: [[1,2,3], [1,3,2], [2,1,3], [2,3,1]]
                          the polled element: [3,2,1]
                          indices
                  [3,2,1], 1 1
                  [3,1,2]  1 2
                          add outer list: [[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,2,1], [3,1,2]]

                  if (indices 2 == nums.length - 1) exit iteration

                  TimeComplexity : O(L!L) where L is the length of the input array.
                  SpaceComplexity: O(L!)
                  The iterating takes factorial. list.add(index, value) takes L.

                  Runtime: 1 ms, faster than 99.71% of Java online submissions for Permutations.
                  Memory Usage: 37.9 MB, less than 85.26% of Java online submissions for Permutations.
     */
        LinkedList<List<Integer>> outer = new LinkedList<>();

        if (nums == null || nums.length == 0) {
            return outer;
        }

        List<Integer> inner = new ArrayList<>();
        for (int num: nums) {
            inner.add(num);
        }
        outer.add(inner);

        for (int i = 0; i < nums.length; i++) {
            int outerSize = outer.size();
            for (; outerSize > 0; outerSize--) {
                inner = outer.remove(0); // LinkedList.remove(0) is O(1)
                for (int j = i; j < nums.length; j++) {
                    List<Integer> permutation = swap(inner, i, j);
                    if (permutation != null) {
                        outer.add(permutation);
                    }
                }
            }
        }

        return outer;
    }

    /**
     * @param list
     * @param indexA
     * @param indexB
     * @return return a new list containing swapped items.
     *  Or return the input list without modification when indices are the same
     */
    public static List<Integer> swap(List<Integer> list, int indexA, int indexB) {
        if (indexA != indexB) {
            // Making a new list takes O(L) where L is size of list
            // ArrayList.get and set are O(1)
            list = new ArrayList<>(list);
            Integer temp = list.get(indexA);
            list.set(indexA, list.get(indexB));
            list.set(indexB, temp);
        }
        return list;
    }
}
