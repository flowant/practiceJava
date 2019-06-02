package practice.graph;

import java.util.HashSet;
import java.util.List;

public class WordLadder {
/*
    Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:

    Only one letter can be changed at a time.
    Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
    Note:

    Return 0 if there is no such transformation sequence.
    All words have the same length.
    All words contain only lowercase alphabetic characters.
    You may assume no duplicates in the word list.
    You may assume beginWord and endWord are non-empty and are not the same.
    Example 1:

    Input:
    beginWord = "hit",
    endWord = "cog",
    wordList = ["hot","dot","dog","lot","log","cog"]

    Output: 5

    Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
    return its length 5.
    Example 2:

    Input:
    beginWord = "hit"
    endWord = "cog"
    wordList = ["hot","dot","dog","lot","log"]

    Output: 0

    Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.

    */

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        /*
           If we know beginNode and endNode then we can use bidirectional searches.
           we use one more set to traversal from endWord.

           In Bidirectional BFS, if there are adjacents words between two traversal
           Set then we found the path from begin to end
        */

        HashSet<String> wordSet = new HashSet<>(wordList);

        if (wordSet.contains(endWord) == false) {
            return 0;
        }

        int level = 1;

        HashSet<String> beginSet = new HashSet<>();
        HashSet<String> endSet = new HashSet<>();
        beginSet.add(beginWord);
        endSet.add(endWord);
        wordSet.remove(endWord);

        while (beginSet.size() > 0 && endSet.size() > 0) {
            if (beginSet.size() > endSet.size()) {
                HashSet<String> temp = beginSet;
                beginSet = endSet;
                endSet = temp;
            }

            HashSet<String> nextLevel = new HashSet<>();
            for (String wordFromBegin : beginSet) {
                for (String wordFromEnd : endSet) {
                    if (isAdjacent(wordFromBegin, wordFromEnd)) {
                        return level + 1;
                    }
                }

                HashSet<String> adjacentSet = new HashSet<>();
                for (String wordUnvisited : wordSet) {
                    if (isAdjacent(wordFromBegin, wordUnvisited)) {
                        adjacentSet.add(wordUnvisited);
                    }
                }
                nextLevel.addAll(adjacentSet);
                wordSet.removeAll(adjacentSet);
            }

            level++;
            beginSet = nextLevel;
        }

        return 0;
    }

    public int ladderLengthBfs(String beginWord, String endWord, List<String> wordList) {
        /*
            - We are going to make isAdjacent method to determine two node are adjacent.
              compare each characters at the same indices and count different characters.
              if counter value is 1 then return true else return false

            - BFS will be used for traversal.
            - Instead of Queue I'm going to use set to store next level nodes per level.

            1. Initialize integer variable named level as 1 and initialize set named curLevel and add beginWord to the set.
            2. Initialize empty set named nextLevel.
            3. compare word from curLevel set and word from wordSet using isAdjacent method
               if they are the same and endword equals to word from set then return level + 1;
               else add word from wordSet to nextLevel set
            3. go step 3 through all words from wordSet.
            4. remove nextLevel set from wordSet because of to mark visited.
            5. go step 3 througt all words from curLevel.
            6. increase level and change nextLevel set to curLevel set and go step 2 until curLevel set is not empty.
            7. return level
            -
        */
        HashSet<String> wordSet = new HashSet<>(wordList);

        if (wordSet.contains(endWord) == false) {
            return 0;
        }

        int level = 1;

        HashSet<String> curLevel = new HashSet<>();
        curLevel.add(beginWord);

        while (curLevel.size() > 0) {
            HashSet<String> nextLevel = new HashSet<>();
            for (String node : curLevel) {
                HashSet<String> adjacents = new HashSet<>();
                for (String adjacent : wordSet) {
                    if (isAdjacent(node, adjacent)) {
                        if (adjacent.equals(endWord)) {
                            return level + 1;
                        }
                        adjacents.add(adjacent);
                    }
                }
                nextLevel.addAll(adjacents);
                wordSet.removeAll(nextLevel);
            }
            level++;
            curLevel = nextLevel;
        }

        return 0;
    }

    public static boolean isAdjacent(String first, String second) {
        if (first == null || second == null || first.length() != second.length()) {
            return false;
        }

        int cntDiffer = 0;

        for (int i = 0; i < first.length() && cntDiffer <= 1; i++) {
            if (first.charAt(i) != second.charAt(i)) {
                cntDiffer++;
            }
        }

        return cntDiffer == 1;
    }
}
