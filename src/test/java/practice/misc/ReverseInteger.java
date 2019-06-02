package practice.misc;

/*

https://leetcode.com/problems/reverse-integer/

Given a 32-bit signed integer, reverse digits of an integer.

Example 1:

Input: 123
Output: 321
Example 2:

Input: -123
Output: -321
Example 3:

Input: 120
Output: 21
Note:
Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.


 */
public class ReverseInteger {

    public int reverse(int x) {

        int reverse = 0;
        int digit  = 0;

        // Interate to extract one digit from input.
        // and check overflow.
        // accumulate the extracted digit to reverse variable.
        // TimeComplexity: O(log10 Integer.MAX_VALUE)
        // SpaceComplexity: O(1)

        while (x != 0) {
            // save the rightmost digit
            digit = x % 10;
            // save x value as x devide by 10
            x = x / 10;

            // overflow condition: digit < 0 && Integer.MAX_VALUE < reverse * 10 + digit
            if (digit >=0 && (Integer.MAX_VALUE - digit) / 10 < reverse) {
                return 0;
            }

            // overflow condition: digit < 0 && Integer.MIN_VALUE > reverse * 10 + digit
            if (digit < 0 && (Integer.MIN_VALUE - digit) / 10 > reverse) {
                return 0;
            }

            // reverse multiply by ten and accumulate the digit
            reverse = reverse * 10 + digit;
        }

        return reverse;
    }
}
