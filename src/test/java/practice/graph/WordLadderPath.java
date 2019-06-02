package practice.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*

https://leetcode.com/problems/word-ladder-ii/

Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation sequence(s) from beginWord to endWord, such that:

Only one letter can be changed at a time
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
Note:

Return an empty list if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.
You may assume no duplicates in the word list.
You may assume beginWord and endWord are non-empty and are not the same.
Example 1:

Input:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

Output:
[
  ["hit","hot","dot","dog","cog"],
  ["hit","hot","lot","log","cog"]
]
Example 2:

Input:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log"]

Output: []

Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.

*/
public class WordLadderPath {

    Map<String, List<String>> backwardGraph;
    List<List<String>> results;

    /**
     * This method uses the bidirectional bfs method to make a backward
     * graph representing the shortest paths.
     * the dfs method traversal the graph to make Output lists.
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        // initialize list for used by answer
        results = new ArrayList<>();

        if (beginWord == null || endWord == null || wordList == null || wordList.size() == 0) {
            return results;
        }

        backwardGraph = new HashMap<>();
        Set<String> unvisitedNode = new HashSet<>(wordList);

        if (unvisitedNode.contains(endWord) == false) {
            return results;
        }

        bfsBidirectionalOptimized(beginWord, endWord, unvisitedNode);
        //bfsBidirectional(beginWord, endWord, unvisitedNode);
        //bfs(beginWord, endWord, unvisitedNode);
        dfs(endWord, beginWord, new ArrayList<String>());

        return results;
    }

    /**
     * This method uses a set per level that contains adjacent nodes from the previous level.
     * The first set is the fromBegin set that contains beginWord.
     * Likewise the second set is the fromEnd set that contains endWord.
     * If there are adjacent nodes between fromBegin and fromEnd set
     * that means the shortest paths are found.
     * Else if there are adjacent nodes between fromBegin and unvisitedNode
     * then adjacent nodes are added to the nextLevel set
     * and replace fromBegin set as nextLevel set at the end of the iteration block.
     *
     * It's going to swap fromBegin and fromEnd set when fromBegin size is greater.
     * Because the smaller set tents to make graphs smaller.
     * The direction will be upside down after swapping, the direction should be traced by
     * flipping the isBeginToEnd boolean variable.
     *
     * The backward edges will be added to the backward graph when there are adjacent nodes between
     * fromBegin and either fromEnd or unvisitedNode set.
     *
     * The nextLevel set will be removed from unvisitedNode set at the end of the iteration.
     * but not at the moment adding to the nextLevel set.
     * It makes the adjacent node can have several backward edges in the graph.
     *
     * Time Complexity: O(V^2 * L) where V is the number of words, L the length of the word.
     * Space Complexity: O(2V + E) where the first V is the number of words. the nodes in the unvisitedNode set move to
     * wordFromBegin or wordFromEnd set during iterations.
     * The second V means the vertices for graph, E edges representing backward.
     *
     * Runtime: 107 ms, faster than 51.83% of Java online submissions for Word Ladder II.
     * Memory Usage: 43 MB, less than 86.74% of Java online submissions for Word Ladder II.

     * @param beginWord
     * @param endWord
     * @param unvisitedNode
     */
    public void bfsBidirectional(String beginWord, String endWord, Set<String> unvisitedNode) {

        Set<String> fromBegin = new HashSet<>();
        fromBegin.add(beginWord);

        Set<String> fromEnd = new HashSet<>();
        fromEnd.add(endWord);
        unvisitedNode.remove(endWord);

        boolean isBeginToEnd = true;

        while (fromBegin.size() > 0 && fromEnd.size() > 0) {
            if (fromBegin.size() > fromEnd.size()) {
                Set<String> temp = fromEnd;
                fromEnd = fromBegin;
                fromBegin = temp;
                isBeginToEnd = !isBeginToEnd;
            }

            boolean isShortest = false;

            Set<String> nextLevel = new HashSet<>();
            for (String wordFromBegin: fromBegin) {

                for (String wordFromEnd: fromEnd) {
                    if (isAdjacent(wordFromBegin, wordFromEnd)) {
                        isShortest = true;
                        addEdge(wordFromBegin, wordFromEnd, isBeginToEnd);
                    }
                }

                for (String adjacentWord: unvisitedNode) {
                    if (isAdjacent(wordFromBegin, adjacentWord)) {
                        nextLevel.add(adjacentWord);
                        addEdge(wordFromBegin, adjacentWord, isBeginToEnd);
                    }
                }
            }

            if (isShortest) {
                break;
            }

            unvisitedNode.removeAll(nextLevel);
            fromBegin = nextLevel;
        }
    }

    /**
     * The logic used for this functions is the same as the bfsBidirectional method
     * except for looking for adjacent nodes.
     * It makes all possible adjacentWords by changing one character from alphabet low letters.
     * If the nextLevel set from the other side contains the possible adjacentWord that means we found the shortest path.
     * Else if the unvisitedNode set contains the possible adjacentWord then we add the adjacentWord to the nextLevel set.
     *
     * Time Complexity: O(V * 25 * L) where V is the number of words, L the length of the word.
     * Space Complexity: O(2V + E) where the first V is the number of words. the nodes in the unvisitedNode set move to
     * wordFromBegin or wordFromEnd set during iterations.
     * The second V means the vertices for graph, E edges representing backward.
     *
     * It's more efficient when V is greater than 25.
     *
     * Runtime: 17 ms, faster than 94.56% of Java online submissions for Word Ladder II.
     * Memory Usage: 37.7 MB, less than 99.95% of Java online submissions for Word Ladder II.
     *
     * @param beginWord
     * @param endWord
     * @param unvisitedNode
     */
    public void bfsBidirectionalOptimized(String beginWord, String endWord, Set<String> unvisitedNode) {

        Set<String> fromBegin = new HashSet<>();
        fromBegin.add(beginWord);

        Set<String> fromEnd = new HashSet<>();
        fromEnd.add(endWord);
        unvisitedNode.remove(endWord);

        boolean isBeginToEnd = true;

        while (fromBegin.size() > 0 && fromEnd.size() > 0) {
            if (fromBegin.size() > fromEnd.size()) {
                Set<String> temp = fromEnd;
                fromEnd = fromBegin;
                fromBegin = temp;
                isBeginToEnd = !isBeginToEnd;
            }

            boolean isShortest = false;

            Set<String> nextLevel = new HashSet<>();
            for (String wordFromBegin: fromBegin) {
                char[] charsFromBegin = wordFromBegin.toCharArray();

                for (int i = 0; i < wordFromBegin.length(); i++) {
                    char old = charsFromBegin[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == old) {
                            continue;
                        }

                        charsFromBegin[i] = c;
                        String adjacentWord = new String(charsFromBegin);

                        if (fromEnd.contains(adjacentWord)) {
                            isShortest = true;
                            addEdge(wordFromBegin, adjacentWord, isBeginToEnd);
                        } else if (unvisitedNode.contains(adjacentWord)) {
                            nextLevel.add(adjacentWord);
                            addEdge(wordFromBegin, adjacentWord, isBeginToEnd);
                        }

                    }
                    charsFromBegin[i] = old;
                }
            }

            if (isShortest) {
                break;
            }

            unvisitedNode.removeAll(nextLevel);
            fromBegin = nextLevel;
        }
    }

    public void addEdge(String node, String adjacent, boolean isBackward) {
        String key = isBackward ? adjacent : node;
        String value = isBackward ? node : adjacent;

        if (backwardGraph.containsKey(key) == false) {
            backwardGraph.put(key, new ArrayList<String>());
        }
        backwardGraph.get(key).add(value);
    }

    /**
     * See the bidirectional bfs.
     *
     * Runtime: 520 ms, faster than 9.92% of Java online submissions for Word Ladder II.
     * Memory Usage: 47.7 MB, less than 72.95% of Java online submissions for Word Ladder II.
     * @param beginWord
     * @param endWord
     * @param unvisitedNode
     */
    public void bfs(String beginWord, String endWord, Set<String> unvisitedNode) {

        Set<String> curLevel = new HashSet<>();
        curLevel.add(beginWord);

        while (curLevel.size() > 0) {

            boolean isShortest = false;

            Set<String> nextLevel = new HashSet<>();
            for (String curWord: curLevel) {
                for (String adjacentWord: unvisitedNode) {
                    if (isAdjacent(curWord, adjacentWord)) {
                        if (adjacentWord.equals(endWord)) {
                            isShortest = true;
                        }
                        nextLevel.add(adjacentWord);
                        addEdge(curWord, adjacentWord, true);
                    }
                }
            }

            if (isShortest) {
                break;
            }

            unvisitedNode.removeAll(nextLevel);
            curLevel = nextLevel;
        }
    }

    public void dfs(String word, String start, List<String> path) {
        path.add(0, word);

        if (word.equals(start)) {
            results.add(new ArrayList<String>(path));
        } else if (backwardGraph.containsKey(word)) {
            for (String backwardWord : backwardGraph.get(word)) {
                dfs(backwardWord, start, path);
            }
        }

        path.remove(0);
    }

    /**
     * returns true when there is only one different character between two words.
     * else returns false.
     *
     * @param first
     * @param second
     * @return
     */
    public static boolean isAdjacent(String first, String second) {
        if (first == null || second == null || first.length() != second.length()) {
            return false;
        }

        int cntDiffer = 0;

        for (int i = 0; i < first.length(); i++) {
            if (first.charAt(i) != second.charAt(i)) {
                cntDiffer++;
            }
            if (cntDiffer > 1) {
                return false;
            }
        }

        return cntDiffer == 1;
    }

}
