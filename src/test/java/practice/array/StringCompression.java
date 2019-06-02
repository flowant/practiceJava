package practice.array;

/*

https://leetcode.com/problems/string-compression/

Given an array of characters, compress it in-place.

The length after compression must always be smaller than or equal to the original array.

Every element of the array should be a character (not int) of length 1.

After you are done modifying the input array in-place, return the new length of the array.


Follow up:
Could you solve it using only O(1) extra space?


Example 1:

Input:
["a","a","b","b","c","c","c"]

Output:
Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]

Explanation:
"aa" is replaced by "a2". "bb" is replaced by "b2". "ccc" is replaced by "c3".


Example 2:

Input:
["a"]

Output:
Return 1, and the first 1 characters of the input array should be: ["a"]

Explanation:
Nothing is replaced.


Example 3:

Input:
["a","b","b","b","b","b","b","b","b","b","b","b","b"]

Output:
Return 4, and the first 4 characters of the input array should be: ["a","b","1","2"].

Explanation:
Since the character "a" does not repeat, it is not compressed. "bbbbbbbbbbbb" is replaced by "b12".
Notice each digit has it's own entry in the array.


Note:

All characters have an ASCII value in [35, 126].
1 <= len(chars) <= 1000.
*/

public class StringCompression {

    /*
        Runtime: 1 ms, faster than 98.71% of Java online submissions for String Compression.
        Memory Usage: 35.2 MB, less than 99.82% of Java online submissions for String Compression.

        Time Complexity: O(N)
        Space Complexity: O(log10N)
    */
    public int compress(char[] chars) {
        if (chars == null || chars.length == 0) {
            return 0;
        }

        // Initialize count as 1, writeIndex as 0
        int count = 1;
        int writeIndex = 0;

        for (int i = 0; i < chars.length; i++) {
            // If i + 1 is less than length and the current and the next elements are the same
            if (i + 1 < chars.length && chars[i] == chars[i + 1]) {
                // If count is 1 then copy the element at writeIndex to current index
                if (count == 1) {
                    chars[writeIndex++] = chars[i];
                }
                count++;
            } else if (count > 1) {
                // Else if Count is greater than 1 transform count to char array.
                // Copy to writeIndex and increate writeIndex as the length of the char array.
                char[] charCount = String.valueOf(count).toCharArray();
                System.arraycopy(charCount, 0, chars, writeIndex, charCount.length);
                writeIndex += charCount.length;
                // Set count as 1
                count = 1;
            } else {
                // Else copy the element at writeIndex to current index
                chars[writeIndex++] = chars[i];
            }
        }

        return writeIndex;
    }
}
