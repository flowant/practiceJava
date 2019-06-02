package practice.array;

import java.util.HashMap;

public class MinimumWindowSubstring {
    /*
     * Given a string S and a string T, find the minimum window in S which will
     * contain all the characters in T in complexity O(n).
     *
     * Example:
     *
     * Input: S = "ADOBECODEBANC", T = "ABC" Output: "BANC" Note:
     *
     * If there is no such window in S that covers all characters in T, return the
     * empty string "". If there is such window, you are guaranteed that there will
     * always be only one unique minimum window in S.
     */

    public String minWindow(String s, String t) {

        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }

        HashMap<Character, Integer> charCount = new HashMap<>();

        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        int countKey = 0;
        int minLength = Integer.MAX_VALUE;
        int leftIndex = 0;
        int minLeftIndex = -1;

        for (int rightIndex = 0; rightIndex < s.length(); rightIndex++) {

            char rightChar = s.charAt(rightIndex);

            if (charCount.containsKey(rightChar)) {
                int count = charCount.get(rightChar) - 1;
                charCount.put(rightChar, count);
                if (count >= 0) {
                    countKey++;
                }

                while (countKey == t.length()) {

                    char leftChar = s.charAt(leftIndex);

                    if (charCount.containsKey(leftChar)) {
                        if (rightIndex - leftIndex + 1 < minLength) {
                            minLength = rightIndex - leftIndex + 1;
                            minLeftIndex = leftIndex;
                        }
                        count = charCount.get(leftChar) + 1;
                        charCount.put(leftChar, count);
                        if (count > 0) {
                            countKey--;
                        }
                    }
                    leftIndex++;
                }
            }
        }

        return minLeftIndex == -1 ? "" : s.substring(minLeftIndex, minLeftIndex + minLength);
    }

}
