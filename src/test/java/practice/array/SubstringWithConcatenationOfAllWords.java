package practice.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
https://leetcode.com/problems/substring-with-concatenation-of-all-words/

You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.

Example 1:

Input:
  s = "barfoothefoobarman",
  words = ["foo","bar"]
Output: [0,9]
Explanation: Substrings starting at index 0 and 9 are "barfoor" and "foobar" respectively.
The output order does not matter, returning [9,0] is fine too.
Example 2:

Input:
  s = "wordgoodgoodgoodbestword",
  words = ["word","good","best","word"]
Output: []

 */
public class SubstringWithConcatenationOfAllWords {

    public List<Integer> findSubstring(String s, String[] words) {
        if (s == null || words == null || words.length == 0 || words[0].length() == 0) {
            return new ArrayList<Integer>();
        }

        ArrayList<Integer> indices = new ArrayList<>();

        int lenWord = words[0].length();
        int cntWord = words.length;
        int lenSubString = lenWord * cntWord;

        HashMap<String, Integer> wordCount = new HashMap<>();

        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        for (int i = 0; i + lenSubString <= s.length(); i++) {// O((s.length-lenSubString) * cntWord)
            HashMap<String, Integer> actualWordCount = new HashMap<>();
            for (int j = 0; j < cntWord; j++) {
                int offset = i + (j * lenWord);
                // i 0, p: 0, 3 p: 3, 6
                // i 9, p: 9, 12 p: 12, 15
                String word = s.substring(offset, offset + lenWord);

                if (wordCount.containsKey(word) == false
                        || wordCount.get(word) == actualWordCount.getOrDefault(word, 0)) {
                    break;
                }

                actualWordCount.put(word, actualWordCount.getOrDefault(word, 0) + 1);

                if (j + 1 == cntWord && wordCount.equals(actualWordCount)) {
                    indices.add(i);
                }
            }

        }

        return indices;
    }

}
