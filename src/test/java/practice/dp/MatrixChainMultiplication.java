package practice.dp;

public class MatrixChainMultiplication {

    static int MatrixChain(int p[], int n)
    {
        int m[][] = new int[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            m[i][i] = 0;
        }

        for (int d = 1; d < n; d++) {
            for (int i = 1; i <= n - d; i++) {
                int j = i + d;
                m[i][j] = Integer.MAX_VALUE;
                for(int k = i; k <= j - 1; k++) {
                    int opt = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (m[i][j] > opt) {
                        m[i][j] = opt;
                    }
                }
            }
        }

        return m[1][n];
    }

    // Driver program to test above function
    public static void main(String args[])
    {
        int arr[] = new int[] {1, 2, 3, 4, 5};

        System.out.println("Minimum number of multiplications is " +
                MatrixChain(arr, arr.length - 1));
    }
}
