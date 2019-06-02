package practice.array;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/*
Given a collection of numbers that might contain duplicates, return all possible unique permutations.

Example:

Input: [1,1,2]
Output:
[
  [1,1,2],
  [1,2,1],
  [2,1,1]
]
 */
public class PermutationsDuplicates {

    public List<List<Integer>> permuteUnique(int[] nums) {
        /*

        TODO: It's easy but slow.

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
        LinkedList<List<Integer>> result = new LinkedList<>();

        if (nums == null || nums.length == 0) {
            return result;
        }

        HashSet<List<Integer>> outer = new HashSet<>();
        outer.add(new LinkedList<Integer>()); // outer: [ [3, 2, 1], [2, 3, 1], [2, 1, 3]
                                              // [3, 1, 2], [1, 3, 2], [1, 2, 3]]
        for (int num : nums) { // 3
            HashSet<List<Integer>> next = new HashSet<>(); // outerSize: 2
            for (List<Integer> inner : outer) {
                for (int i = 0; i <= inner.size(); i++) { // i:0
                    List<Integer> permutation = new LinkedList<>(inner); // [1, 2, 3]
                    permutation.add(i, num);
                    if (next.contains(permutation) == false) {
                        next.add(permutation);
                    }
                }
            }
            outer = next;
        }

        result.addAll(outer);
        return result;
    }
}
