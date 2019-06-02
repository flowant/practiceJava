package practice.misc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GreatestCommonDivisor {

    public static int gcd(int a, int b) {
        while(b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        // a 4  10 4 2 2
        // b 10 4  2 2 0
        return a;
    }

    public static int gcd(int[] input) {
        if (input == null || input.length < 2) {
            return 1;
        }

        int gcd = input[0];

        for (int i = 1; i < input.length; i++) {
            gcd = gcd(gcd, input[i]);
        }

        return gcd;
    }

    @Test
    public void testGcd() {
        assertEquals(2, gcd(2,4));
        assertEquals(1, gcd(3,4));
        assertEquals(2, gcd(4,10));

        int arr[] = { 2, 4, 6, 8, 16 };
        assertEquals(2, gcd(arr));
    }

}
