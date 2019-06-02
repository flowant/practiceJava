package practice.dp;

public class LCS {
    /* Returns length of LCS for X[0..m-1], Y[0..n-1] */
    public int lcs(char[] x, char[] y)
    {
        int[][] lcs = new int[x.length + 1][y.length + 1];

        for (int i = 0; i <= x.length; i++) {
            for (int j = 0; j <= y.length; j++) {
                if (i == 0 || j == 0) {
                    lcs[i][j] = 0;
                } else if (x[i - 1] == y[j - 1]) {
                    lcs[i][j] = lcs[i - 1][j - 1] + 1;
                } else {
                    lcs[i][j] = max(lcs[i - 1][j], lcs[i][j - 1]);
                }
            }
        }

        return lcs[lcs.length - 1][lcs[0].length - 1];

        // case x or y = 0 , lcs is 0
        // case x == y, lcs[x - 1][y - 1] + 1
        // case x != y, max(lcs[x - 1][y], lcs[x][y - 1])

    }

    int max(int a, int b)
    {
        return a > b ? a : b;
    }

    public static void main(String[] args)
    {
        LCS lcs = new LCS();
        String s1 = "AGGTAB";
        String s2 = "GXTXAYB";

        char[] X=s1.toCharArray();
        char[] Y=s2.toCharArray();

        System.out.println("Length of LCS is" + " " + lcs.lcs(X, Y));
    }
}
