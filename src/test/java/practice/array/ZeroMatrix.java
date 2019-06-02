package practice.array;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class ZeroMatrix {
    public void setZeroRowsColsIfContainAnyZero(int [][]matrix) {
        if (matrix == null) {
            return;
        }

        boolean firstRowHasZero = false;
        boolean firstColHasZero = false;

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                firstColHasZero = true;
            }
        }

        for (int i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i] == 0) {
                firstRowHasZero = true;
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < matrix[i].length; j++) {
                    matrix[i][j] = 0;
                }
            }
        }

        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                for (int i = 1; i < matrix.length; i++) {
                    matrix[i][j] = 0;
                }
            }
        }

        if (firstRowHasZero) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[0][j] = 0;
            }
        }

        if (firstColHasZero) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    @Test
    public void test() {
        int [][] matrix = {{0,2,3,4,5,3,2},
                           {1,2,0,4,5,0,2},
                           {1,2,3,4,5,3,2},
                           {1,2,3,4,0,3,2},
                           {0,2,3,4,5,3,2}};
        int [][] expected = {{0,0,0,0,0,0,0},
                             {0,0,0,0,0,0,0},
                             {0,2,0,4,0,0,2},
                             {0,0,0,0,0,0,0},
                             {0,0,0,0,0,0,0}};

        setZeroRowsColsIfContainAnyZero(matrix);

        assertTrue(Arrays.deepEquals(expected, matrix));
    }


}
