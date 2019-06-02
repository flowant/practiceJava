package practice.array;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class DutchNationalFlag {

    @Test
    public void test() {
        int numArray = 100000;
        int[] test = new int[numArray];
        Random rand = new Random();
        for (int i = 0; i < test.length; i++) {
            test[i] = rand.nextInt(3);
        }
        int[] confirm = Arrays.copyOf(test, test.length);

        sortDNF(test);
        Arrays.sort(confirm);

        Assert.assertArrayEquals(confirm, test);
    }

    // Dutch National Flag (DNF)
    public static void sortDNF(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        for (int low = 0, mid = 0, high = array.length - 1; mid <= high; ) {
            switch (array[mid]) {
                case 0:
                    swap(array, low, mid);
                    low++;
                    mid++;
                    break;
                case 1:
                    mid++;
                    break;
                case 2:
                    swap(array, mid, high);
                    high--;
                    break;
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        if (array[i] != array[j]) {
            int temp = array[j];
            array[j] = array[i];
            array[i] = temp;
        }
    }
}
