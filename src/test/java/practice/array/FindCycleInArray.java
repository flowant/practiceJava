package practice.array;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.stream.IntStream;

public class FindCycleInArray {
    //  0  1  2  3  4  5  6
    // [9, 2, 3, 4, 1, 5, 6] cycle does exist

    public static boolean hasCycle(int[] array) {
        HashSet<Integer> hs = new HashSet<>();

        for (int i = 0; i < array.length; i++) { // O(n)
            if (hs.contains(array[i])) { // O(1)
                return true; // cycle does exist
            }
            hs.add(array[i]);// O(1)
        }

        return false;
    }

    @Test
    public void testHasCycle() {

        int[] array = IntStream.range(0, 10).toArray();

        /*
        int[] array = new int[10];
        for (int i = 0; i < 10; i++) {
            array[i] = i;
        }
        */

        Assert.assertFalse(hasCycle(array));
        array[5] = 2;
        Assert.assertTrue(hasCycle(array));

    }

}
