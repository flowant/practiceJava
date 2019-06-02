package practice.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/*
https://leetcode.com/problems/top-k-frequent-elements/

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
     * 1. Initialize HashMap 2. Set a key as an element from array, set a value as 1
     * at the first time or increase the value in HashMap 3. Iterate step 2 through
     * input array 4. Initialize minHeap with size k 5. add k elements in entries
     * from HashMap 6. peek min elements from minHeap, the value is smaller than the
     * next entry then poll and add the next entry to minHeap 7. Iterate step 6
     * until there is no entry in interator from HashMap. 8. Make Lisk from minHeap
     * and return.
     *
     * Runtime: 43 ms, faster than 35.63% of Java online submissions for Top K
     * Frequent Elements. Memory Usage: 40.9 MB, less than 35.29% of Java online
     * submissions for Top K Frequent Elements.
     *
     */

    public List<Integer> topKFrequent(int[] nums, int k) {

        List<Integer> topFrequentNums = new ArrayList<>();

        if (nums == null) {
            return topFrequentNums;
        }

        HashMap<Integer, Integer> numCount = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            numCount.put(nums[i], numCount.getOrDefault(nums[i], 0) + 1);
        } // 1 2 1 2 3 // 1:1 2:1 2

        // PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue( //
        // k, Comparator.comparingInt(Map.Entry<Integer, Integer>::getValue));

        // PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(
        // k, (a, b) -> a.getValue() - b.getValue());

        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(k,
                (a, b) -> a.getValue().compareTo(b.getValue()));

        for (Map.Entry<Integer, Integer> entry : numCount.entrySet()) {
            if (minHeap.size() < k) {
                minHeap.add(entry);
            } else if (minHeap.peek().getValue() < entry.getValue()) {
                minHeap.poll();
                minHeap.add(entry);
            }
        }

        for (Map.Entry<Integer, Integer> entry : minHeap) {
            topFrequentNums.add(entry.getKey());
        }

        return topFrequentNums;
    }

    /*
     * 1. Initialize HashMap 2. Set a key as an element from array, set a value as 1
     * at the first time or increase the value in HashMap 3. Iterate step 2 through
     * input array 4. Initialize List Array with nums.length + 1 5. use map entry
     * value as index, add map entry key to element of list 6. go step 5 throught
     * map entries 7. Make List from the last element from the array, until size of
     * List will be k
     *
     * Runtime: 9 ms, faster than 99.65% of Java online submissions for Top K
     * Frequent Elements. Memory Usage: 40.3 MB, less than 54.71% of Java online
     * submissions for Top K Frequent Elements.
     *
     */
/*
    public List<Integer> topKFrequent(int[] nums, int k) {

        List<Integer> topFrequentNums = new ArrayList<>();

        if (nums == null) {
            return topFrequentNums;
        }

        HashMap<Integer, Integer> numCount = new HashMap<>();

        int maxCount = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int count = numCount.getOrDefault(nums[i], 0) + 1;
            if (count > maxCount) {
                maxCount = count;
            }
            numCount.put(nums[i], count);
        }

        List<Integer>[] bucket = new List[maxCount + 1];
        for (Map.Entry<Integer, Integer> entry : numCount.entrySet()) {
            if (bucket[entry.getValue()] == null) {
                bucket[entry.getValue()] = new ArrayList<Integer>();
            }
            bucket[entry.getValue()].add(entry.getKey());
        }

        int i = bucket.length - 1;
        while (i >= 0 && topFrequentNums.size() != k) {
            if (bucket[i] == null || bucket[i].size() == 0) {
                i--;
                continue;
            }
            topFrequentNums.add(bucket[i].remove(0));
        }

        return topFrequentNums;
    }
*/
}
