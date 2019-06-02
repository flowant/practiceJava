package practice.misc;

import org.junit.Assert;
import org.junit.Test;

public class IncreaseIntegerArray {

    public static int[] increase(int []input) {
        // if input is null or input array length is zero
        // then I suppose the input is the array containing 0 value.
        // so this function returns an array containing 1.
        if (input == null || input.length == 0) {
            return new int[]{1};
        }

        // initial value of carry is 1 that means increase by 1.
        int carry = 1;
        int[] output = new int[input.length];

        for (int i = input.length - 1; i >= 0; i--) {
            // output value is increased by one that is from initial value of carry.
            output[i] = carry + input[i];
            // reset carry.
            carry = 0;

            // if current element of index of output array value is ten
            // set carry value as 1 and output value as 0
            if (output[i] == 10) {
                carry = 1;
                output[i] = 0;
            }

            // if index is 0 and carry value is 1, make a new larger array than output array.
            // then set element value as 0 at index 0.
            // copy an element at index 0 of output array to an element at index 1 of new array.
            // and so on
            if (i == 0 && carry == 1) {
                int[] temp = output;
                output = new int[input.length + 1];
                System.arraycopy(temp, 0, output, 1, input.length);
                output[0] = 1;
            }
        }

        return output;
    }

    @Test
    public void mainTest() {
        int[] case1 = {};
        int[] case2 = {1};
        int[] case3 = {1, 9};
        int[] case4 = {9, 9, 9};

        Assert.assertArrayEquals(new int[] {1}, increase(case1));
        Assert.assertArrayEquals(new int[] {2}, increase(case2));
        Assert.assertArrayEquals(new int[] {2, 0}, increase(case3));
        Assert.assertArrayEquals(new int[] {1, 0, 0, 0}, increase(case4));
    }

}
