package practice.array;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class Rotate {


    // 0  1  2  3      12 8  4  0
    // 4  5  6  7      13 9  5  1
    // 8  9  10 11     14 10 6  2
    // 12 13 14 15     15 11 7  3

    // 0  1  2  3  4  5
    // 4  5  6  7  0  0
    // 8  9  10 11 0  0
    // 12 13 14 15 0  0
    // 8  9  10 11 0  0
    // 12 13 14 15 0  0

    public static boolean rotate(int matrix[][]) {
        if (matrix == null || matrix.length != matrix[0].length) {
            return false;
        }

        for (int i = 0; i < matrix.length /2; i++) {
            for (int ri = i; ri < matrix.length - 1 - i; ri++) {
                rotateFourElements(matrix, ri, i);
            }
        }

        return true;
    }

    static void rotateFourElements(int matrix[][], int rowIndex, int colIndex) {
        int temp = matrix[rowIndex][colIndex];

        for (int i = 0; i < 3; i++) {
            int fromRowIndex = 3 - colIndex;
            int fromColIndex = rowIndex;
            matrix[rowIndex][colIndex] = matrix[fromRowIndex][fromColIndex];
            rowIndex = fromRowIndex;
            colIndex = fromColIndex;
        }
        matrix[rowIndex][colIndex] = temp;
    }

    @Test
    public void testRotate() {
        int matrix[][] = {{0, 1, 2, 3}, {4, 5, 6, 7},{8, 9, 10, 11},{12, 13, 14, 15}};
        int expect[][] = {{12, 8, 4, 0}, {13, 9, 5, 1},{14, 10, 6, 2},{15, 11, 7, 3}};

        assertTrue(rotate(matrix));
        System.out.println(Arrays.deepToString(matrix));

        assertTrue(Arrays.deepEquals(expect, matrix));
        // 0  1  2  3      12 8  4  0
        // 4  5  6  7      13 9  5  1
        // 8  9  10 11     14 10 6  2
        // 12 13 14 15     15 11 7  3
    }

}
