package practice.array;

import java.util.HashMap;

/**
 *
 *

https://leetcode.com/problems/permutation-in-string/

567. Permutation in String

Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. In other words, one of the first string's permutations is the substring of the second string.



Example 1:

Input: s1 = "ab" s2 = "eidbaooo"
Output: True
Explanation: s2 contains one permutation of s1 ("ba").
Example 2:

Input:s1= "ab" s2 = "eidboaoo"
Output: False


Note:

The input strings only contain lower case letters.
The length of both given strings is in range [1, 10,000].

 */
public class PermutationInString {
    public boolean checkInclusion(String s1, String s2) {
        /*
            0. Initialize HashMap, set cntSame as zero.
            1. We are going to use HashMap to store the frequency of characters in s1.
            2. From the first to the last element in s2, if HashMap contains the current element then decrease its frequency.
               If the frequency is zero then increase cntSame.
            3. If current index is the same as the length of s1, we remove the i - s1.length to slide window through s2.
               If HashMap contains the left out side element of window and its frequency is 0 then decrease cntSame
               because it will be removed from window.
               And Increase its frequency.
            4. If cntSame equals to size of HashMap then return true.
            5. go to step 2 until the last element.
            6. return false.

            Time Complexity: O(l1 + l2) where l1 and l2 are the lengths of s1 and s2 respectively
            Space Complexity: O(l1)

            Runtime: 16 ms, faster than 44.98% of Java online submissions for Permutation in String.
            Memory Usage: 36.4 MB, less than 99.54% of Java online submissions for Permutation in String.
        */

        if (s1 == null || s2 == null || s1.length() == 0 || s1.length() > s2.length()) {
            return false;
        }

        HashMap<Character, Integer> cntMap = new HashMap<>();

        int cntSame = 0;

        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            cntMap.put(c, cntMap.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < s2.length(); i++) {
            char added = s2.charAt(i);
            if (cntMap.containsKey(added)) {
                int decreasedFreq = cntMap.get(added) - 1;
                cntMap.put(added, decreasedFreq);
                if (decreasedFreq == 0) {
                    cntSame++;
                }
            }

            if (i >= s1.length()) {
                char removed = s2.charAt(i - s1.length());
                if (cntMap.containsKey(removed)) {
                    int freq = cntMap.get(removed);
                    if (freq == 0) {
                        cntSame--;
                    }
                    cntMap.put(removed, freq + 1);
                }
            }

            if (cntSame == cntMap.size()) {
                return true;
            }
        }
        return false;
    }

    /*
    Runtime: 3 ms, faster than 99.74% of Java online submissions for Permutation in String.
    Memory Usage: 36.3 MB, less than 99.62% of Java online submissions for Permutation in String.

    Time Complexity: O(l1 + l2) where l1 and l2 are the lengths of s1 and s2 respectively
    Space Complexity: O(l1 + l2)
     */
    public boolean checkInclusionOptimized(String s1, String s2) {

        if (s1 == null || s2 == null || s1.length() == 0 || s1.length() > s2.length()) {
            return false;
        }

        int len1 = s1.length();
        int len2 = s2.length();

        char[] cntMap = new char[26];
        char[] cntWindowMap = new char[26];

        char[] charIndex1 = s1.toCharArray();
        char[] charIndex2 = s2.toCharArray();

        int cntSame = 0;
        int sizeCntMap = 0;

        for (int i = 0; i < len1; i++) {
            charIndex1[i] -= 'a';
            cntMap[charIndex1[i]]++;
        }

        for (int i = 0; i < 26; i++) {
            if (cntMap[i] > 0) {
                sizeCntMap++;
            }
        }

        for (int i = 0; i < len2; i++) {
            charIndex2[i] -= 'a';

            int addedIndex = charIndex2[i];
            if (cntMap[addedIndex] > 0) {
                cntWindowMap[addedIndex]++;
                if(cntWindowMap[addedIndex] == cntMap[addedIndex]) {
                    cntSame++;
                }
            }

            if (i >= len1) {
                int removedIndex = charIndex2[i - len1];
                if (cntMap[removedIndex] > 0) {
                    if (cntWindowMap[removedIndex] == cntMap[removedIndex]) {
                        cntSame--;
                    }
                    cntWindowMap[removedIndex]--;
                }
            }

            if (cntSame == sizeCntMap) {
                return true;
            }
        }
        return false;
    }
}
