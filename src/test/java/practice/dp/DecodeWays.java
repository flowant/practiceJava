package practice.dp;

/*
A message containing letters from A-Z is being encoded to numbers using the following mapping:

'A' -> 1
'B' -> 2
...
'Z' -> 26
Given a non-empty string containing only digits, determine the total number of ways to decode it.

Example 1:

Input: "12"
Output: 2
Explanation: It could be decoded as "AB" (1 2) or "L" (12).
Example 2:

Input: "226"
Output: 3
Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
 */
public class DecodeWays {
    public static int numDecodings(String s, int n) {
        if (s.startsWith("0")) {
            return 0;
        }

        // int[] cache = new int[n + 1];

        // cache[0] = 1;
        // cache[1] = 1;
        int cacheLeft2 = 1;
        int cacheLeft1 = 1;
        int cache = 1;

        // 1 => a
        // 1121
        for (int i = 2; i <= n; i++) { // O(n)

            cache = 0;

            char left_1 = s.charAt(i - 1);
            char left_2 = s.charAt(i - 2);

            if (left_1 > '0') {
                // cache[i] = cache[i - 1];
                cache = cacheLeft1;
            }

            if (left_2 == '1' || (left_2 == '2' && left_1 < '7')) {
                // cache[i] += cache[i - 2];
                cache += cacheLeft2;
            }

            cacheLeft2 = cacheLeft1;
            cacheLeft1 = cache;
        }

        return cache;
        // return cache[n];
    }

    public int numDecodings(String s) {

        // abcde fghij klmno pqrst uvwxy z
        // 1120123
        // ()
        // 3
        // | \
        // 2 12
        // | |
        // 1 20
        // | | \
        // 20 1 11
        // |\ |
        // 1 11 1
        // |
        // 1

        // aatabc, aatak ..
        // There is overlapping subproblem

        // recursive fomula
        // base case
        // C(n) = 1, if(n == 0 || n == 1)

        // Optimal substructure
        // general case
        // C(n) = C(n - 1) if (s.charAt(n - 1) > '0')
        // C(n) += C(n - 2) if (s.charAt(n - 2) == '1'
        // || (s.charAt(n - 2) == '2' && s.charAt(n - 1) < '7'))

        return numDecodings(s, s.length());

    }
}
