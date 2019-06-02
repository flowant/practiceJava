package practice.array;

import org.junit.Test;

import java.util.Random;
import java.util.function.IntSupplier;

public class LeaveMinimumValueInRows {

    final static int UPPER_BOUND = 10;

    @Test
    public void test() {
        int[][] sample = makeSample(4, 4, () -> 0);
        printMatrix(sample);
        onlyLeaveMinMatrix(sample);
        printMatrix(sample);

        sample = makeSample(4, 4, () -> new Random().nextInt(UPPER_BOUND));
        printMatrix(sample);
        onlyLeaveMinMatrix(sample);
        printMatrix(sample);
    }

    public int[][] makeSample(int numRow, int numCol, IntSupplier s) {
        int[][] sample = new int[numRow][numCol];

        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                sample[i][j] = s.getAsInt();
            }
        }
        return sample;
    }

    public void printMatrix(int[][] matrix) {
        System.out.println("print matrix");
        for (int[] row : matrix) {
            for (int col : row) {
                System.out.print(col);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private void onlyLeaveMin(int[] cols) {
        int minIndex = cols.length;
        for (int i = 0; i < cols.length; i++) {
            if (cols[i] != 0) {
                minIndex = i;
                break;
            }
        }
        for (int i = minIndex + 1; i < cols.length; i++) {
            if (cols[i] != 0 && cols[minIndex] > cols[i]) {
                cols[minIndex] = 0;
                minIndex = i;
            } else {
                cols[i] = 0;
            }
        }
    }

    public void onlyLeaveMinMatrix(int[][] matrix) {
        if (matrix == null) {
            return;
        }

        for(int[] cols : matrix) {
            onlyLeaveMin(cols);
        }
    }
}
