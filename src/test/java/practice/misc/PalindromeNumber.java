package practice.misc;

/*
Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.

Example 1:

Input: 121
Output: true
Example 2:

Input: -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
Example 3:

Input: 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
Follow up:

Coud you solve it without converting the integer to a string?
 */
public class PalindromeNumber {
    public boolean isPalindrome(int x) {
        /*
           1. If the input number is negative
              or the number is greater than 10 and the last digit is 0 return false.
           2. set the reverse variable as 0
           3. set reverse value as reverse * 10 +  x modulo 10
           4. set x value as x divided by 10
           5. go to step 3 until x is greater than reverse
           6. if x is the same as reverse or x is the same as reverse / 10 then return true

           Time complexity: O(log10 X / 2)
           Space complexity: O(1)

         */
        if (x < 0 || (x % 10 == 0 && x / 10 > 0)) {
            return false;
        }

        int reverse = 0;

        while(x > reverse) {
            reverse = reverse * 10 + x % 10;
            x = x / 10;
        }

        return x == reverse || x == reverse / 10;
    }
}
