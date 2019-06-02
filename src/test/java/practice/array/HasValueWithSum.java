package practice.array;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

public class HasValueWithSum {
    // [ 1, 3, 4, 4] = sum 8
    //   7, 5  4
    //  {7, 5, 4, }
    public static boolean hasValueWithSum(int[] array, int sum) {
        HashSet<Integer> complementSet = new HashSet<>();

        for (int i = 0; i < array.length; i++) {
            int value = array[i];
            if (complementSet.contains(value)) {
                return true;
            }
            complementSet.add(sum - value);
        }

        return false;
    }

    @Test
    public void testHasValueWithSum () {

        int[] array = new int[10];
        for (int i = 0; i < 10; i++) {
            array[i] = i;
        }

        Assert.assertTrue(hasValueWithSum(array, 3));
        Assert.assertFalse(hasValueWithSum(array, -1));
    }

}
