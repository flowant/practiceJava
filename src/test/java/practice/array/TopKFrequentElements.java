package practice.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/*
https://leetcode.com/problems/top-k-frequent-elements/

347. Top K Frequent Elements

Medium

Given a non-empty array of integers, return the k most frequent elements.

Example 1:

Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]
Example 2:

Input: nums = [1], k = 1
Output: [1]
Note:

You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
Your algorithm's time complexity must be better than O(n log n), where n is the array's size.

 */
public class TopKFrequentElements {
    /*
     * 1. Initialize HashMap
     * 2. Set a key as an element from array, set a value as 1
     * at the first time or increase the value in HashMap
     * 3. Iterate step 2 through input array
     * 4. Initialize minHeap with size k
     * 5. add k elements in entries from HashMap
     * 6. peek a minimum element from minHeap, the value is smaller than
     *    the current entry then poll and add the current entry to minHeap
     * 7. Iterate step 6 until there is no entry in the iterator from HashMap.
     * 8. Make List from minHeap and return.
     *
     * Runtime: 42 ms, faster than 42.38% of Java online submissions for Top K Frequent Elements.
     * Memory Usage: 40.8 MB, less than 41.79% of Java online submissions for Top K Frequent Elements.
     *
     */

    public List<Integer> topKFrequentMinHeap(int[] nums, int k) {

        /*
        Input: nums: [1, 1, 1, 2, 2 , 3], k = 2
        Output: [1, 2]

        - use HashMap to count the frequencies of the elements in the input array
        - use MinHeap having k size to store top k Map.Entries
        - use HashMap to save the first index of the element. and use it when the count values are the same

        Testcase:
        - null, k = 2
        - [], k = 2
        - [1], k = 2
        - [1], k = 0

        - [1, 1, 1, 2, 2 , 3], k = 2
        - [1, 1, 1, 2, 2 , 3, 3, 3, 3], k = 2
        - [1], k = 1
        - [1, 2, 3], k = 2

        Time Complexity: O(NlogK) where N is the number of elements in the Input array
        Space Complexity: O(2N + 2K) where N is for HashMaps, K is for MinHeap, K is for result
        */

        List<Integer> result = new ArrayList<>();

        if (nums == null || nums.length < k || k <= 0) {
            return result;
        }

        HashMap<Integer, Integer> numCount = new HashMap<>();
        HashMap<Integer, Integer> numIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            numCount.put(nums[i], numCount.getOrDefault(nums[i], 0) + 1);
            if (numIndex.containsKey(nums[i]) == false) {
                numIndex.put(nums[i], i);
            }
        }

        PriorityQueue<Map.Entry<Integer, Integer>> minHeap =
                new PriorityQueue<>(k, (e1, e2) -> e1.getValue().compareTo(e2.getValue()));

        for (Map.Entry<Integer, Integer> entry : numCount.entrySet()) {
            if (k-- > 0) {
                minHeap.add(entry);
            } else if (minHeap.peek().getValue() < entry.getValue()) {
                minHeap.poll();
                minHeap.add(entry);
            } else if (minHeap.peek().getValue() == entry.getValue()
                    && numIndex.get(entry.getKey()) < numIndex.get(minHeap.peek().getKey())) {
                minHeap.poll();
                minHeap.add(entry);
            }
        }

        for (Map.Entry<Integer, Integer> entry : minHeap) {
            result.add(entry.getKey());
        }

        return result;
    }

    /*
     * 1. Initialize HashMap
     * 2. Set a key as an element from array, set a value as 1
     * at the first time or increase the value in HashMap
     * 3. Iterate step 2 through
     * input array
     * 4. Initialize List Array with nums.length + 1
     * 5. use map entry value as index, add map entry key to element of list
     * 6. go step 5 through map entries
     * 7. Make List from the last element from the array, until size of List will be k
     *
     * Runtime: 9 ms, faster than 99.65% of Java online submissions for Top K
     * Frequent Elements. Memory Usage: 40.3 MB, less than 54.71% of Java online
     * submissions for Top K Frequent Elements.
     *
     */

    public List<Integer> topKFrequent(int[] nums, int k) {
        /*
        Input: nums: [1, 1, 1, 2, 2, 3], k = 2
        Output:      [1, 2]

        - use HashMap to count the frequencies of the elements in the input array
        - use a array of List, the indices of array will be the counter value and add keys from HashMap to the list as value
        - add the last element from the array to the result list.

        Testcase:
        - null, k = 2  => []
        - [], k = 2
        - [1], k = 2
        - [1], k = 0

        - [1, 1, 1, 2, 2 , 3], k = 2        => [1,2]

        - [1], k = 1
        // TODO
        - [1, 2, 3], k = 2 => [1]

        Time Complexity: O(NlogN) where N is the number of elements in the Input array
        Space Complexity: O(4N) where N is for HashMap, N is for the bucket, N is for result
        */

        List<Integer> result = new ArrayList<>();

        if (nums == null || nums.length < k || k <= 0) {
            return result;
        }

        HashMap<Integer, Integer> numIndex = new HashMap<>();
        HashMap<Integer, Integer> numCount = new HashMap<>();
        // [1, 1, 1, 2, 2 , 3], k = 2 => [1,2]
        int maxCount = 0; // 3
        for (int i = 0; i < nums.length; i++) {
            int count = numCount.getOrDefault(nums[i], 0) + 1; // 1
            if (count > maxCount) {
                maxCount = count;
            }
            numCount.put(nums[i], count);
            if (numIndex.containsKey(nums[i]) == false) {
                numIndex.put(nums[i], i);
            }
        }

        @SuppressWarnings("unchecked")
        List<Integer>[] bucket = new List[maxCount + 1];

        for (Map.Entry<Integer, Integer> e : numCount.entrySet()) {
            int count = e.getValue();
            if (bucket[count] == null) {
                bucket[count] = new LinkedList<Integer>();
            }
            bucket[count].add(e.getKey());
        }

        // entries: 1:3 2:2 3:1
        // [null,[3],[],[]]
        int i = bucket.length; // 2
        while (i > 0 && result.size() < k) { // 2 < 2
            if (i == bucket.length || bucket[i] == null || bucket[i].size() == 0) {
                i--;
                // [1, 2, 3, 4,4,4,4], k = 3 => [4, 1, 2]
                // 4: 4 k 3
                // 1: 1 2 3, k 3
                // result [4]
                if (bucket[i] != null && bucket[i].size() > k - result.size()) {
                    Collections.sort(bucket[i], (a, b) -> numIndex.get(a).compareTo(numIndex.get(b)));
                }
                continue;
            }
            result.add(bucket[i].remove(0)); // because LinkedList.remove(0) takes O(1)
        }

        return result;
    }

}
