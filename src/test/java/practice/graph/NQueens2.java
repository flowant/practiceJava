package practice.graph;

/*
https://leetcode.com/problems/n-queens-ii/

The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.

Given an integer n, return the number of distinct solutions to the n-queens puzzle.

Example:

Input: 4
Output: 2
Explanation: There are two distinct solutions to the 4-queens puzzle as shown below.
[
 [".Q..",  // Solution 1
  "...Q",
  "Q...",
  "..Q."],

 ["..Q.",  // Solution 2
  "Q...",
  "...Q",
  ".Q.."]
]

 */
public class NQueens2 {
    public boolean isSafe(int[] matrix, int row, int col) {
        if (matrix == null) {
            return false;
        }

        for (int i = col - 1; i >= 0; i--) {
            if (matrix[i] == row || col - i == Math.abs(row - matrix[i])) {
                return false;
            }
        }

        return true;
        // testcase: row is 0, n -1, n/2
    }

    // n = 4
    // 0 1 2 3
    // 0 . Q
    // 1 . Q dfs
    // 2 Q
    // 3 Q

    public int dfs(int[] matrix, int col) {
        if (matrix == null) {
            return 0;
        }

        if (col >= matrix.length) {
            return 1;
        }

        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (isSafe(matrix, i, col)) {
                matrix[col] = i;
                sum += dfs(matrix, col + 1);
            }
            // matrix[col] = col;
        }

        return sum;
    }

    public int totalNQueens(int n) {
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
         */

        if (n == 0) {
            return 0;
        }

        // index of matrix is col, value at [col] is row
        int[] matrix = new int[n];

        return dfs(matrix, 0);
    }
}
