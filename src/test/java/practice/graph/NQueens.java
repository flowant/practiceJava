package practice.graph;

import java.util.ArrayList;
import java.util.List;

/*
The n-queens puzzle is the problem of placing n queens on an n×n chessboard
such that no two queens attack each other.

Given an integer n, return all distinct solutions to the n-queens puzzle.

Each solution contains a distinct board configuration of the n-queens' placement,
where 'Q' and '.' both indicate a queen and an empty space respectively.

Example:

Input: 4
Output: [
 [".Q..",  // Solution 1
  "...Q",
  "Q...",
  "..Q."],

 ["..Q.",  // Solution 2
  "Q...",
  "...Q",
  ".Q.."]
]
Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above.
 */
public class NQueens {
    public static char QUEEN = 'Q';
    public static char EMPTY = '.';

    public boolean isSafe(char[][] matrix, int row, int col) {
        if (matrix == null) {
            return false;
        }

        int iToTop = row;
        int iToBottom = row;

        for (int i = col - 1; i >= 0; i--) {
            if (matrix[row][i] == QUEEN) { // [0][0]
                return false;
            }

            if (--iToTop >= 0 && matrix[iToTop][i] == QUEEN) {
                return false;
            }

            if (++iToBottom < matrix.length && matrix[iToBottom][i] == QUEEN) {
                return false;
            }
        }

        return true;
        // testcase: row is 0, n -1, n/2
    }

    // n = 4
    // 0 1 2 3
    // 0 . . Q
    // 1 Q .
    // 2 . Q
    // 3 Q

    public List<String> matrixToList(char[][] matrix) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            result.add(new String(matrix[i]));
        }
        return result;
    }

    public void dfs(char[][] matrix, List<List<String>> result, int col) {
        if (matrix == null) {
            return;
        }

        if (col >= matrix[0].length) {
            result.add(matrixToList(matrix));
            return;
        }

        for (int i = 0; i < matrix.length; i++) {
            if (isSafe(matrix, i, col)) {
                matrix[i][col] = QUEEN;
                dfs(matrix, result, col + 1);
            }
            matrix[i][col] = EMPTY;
        }
    }

    public List<List<String>> solveNQueens(int n) {
        /*
         * - a queen cannot take places in the other queen's row, column, diagonal
         * directions. - when n is zero return empty list.
         *
         * - isSafe(row, col): if there is a queen in the leftside of the same row then
         * return false. - if there are queens in the top-left or bottom-left diagonals
         * then return false. - O(N)
         *
         * 1. Make matrix to memorize queen's places. 2. DFS search from the leftmost
         * column. If a row can be occupied then place 'Q' and call DFS again on right
         * column. we're going to make and use isSafe method to check the place is safe.
         * base case: there is no more column on right side return true and save the
         * answer. 3. else put '.' go to step 2 until there is no row to be checked 4.
         * remove queen exit dfs
         *
         * Runtime: 2 ms, faster than 95.98% of Java online submissions for N-Queens.
         * Memory Usage: 36.3 MB, less than 100.00% of Java online submissions for N-Queens.
         *
         */

        List<List<String>> result = new ArrayList<>();

        if (n == 0) {
            return result;
        }

        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = EMPTY;
            }
        }

        dfs(matrix, result, 0);
        return result;
    }
}
