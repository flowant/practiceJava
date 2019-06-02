package practice.dp;

import org.junit.Test;

import java.util.Random;

public class LargestSubarray {

    Random rand = new Random();

    static class Result {

        int start;
        int end;
        int value;

        public Result(int start, int end, int value) {
            this.start = start;
            this.end = end;
            this.value = value;
        }
        public String toString() {
            return "start index: " + start + ", end index: " + end
                    + ", largest sum: " + value;
        }
    }

    @Test
    public void test() {
        int cntSample = 10;
        int array[] = new int[cntSample];

        System.out.println("input array:");
        for (int i = 0; i < cntSample; i++) {
            array[i] = rand.nextInt(100) - 50;
            System.out.print(array[i] + " ");
        }

        System.out.println(maxSumSubarray(array));
    }

    public static Result maxSumSubarray(int[] input) {
        if (input == null || input.length == 0) {
            return null;
        }

        int max_so_far = Integer.MIN_VALUE;
        int max_ending_here = 0;
        int start = 0, end = 0, s = 0;

        for (int i = 0; i < input.length; i++) {
            max_ending_here += input[i];

            if (max_ending_here > max_so_far) {
                max_so_far = max_ending_here;
                start = s;
                end = i;
            }
            if (max_ending_here < 0) {
                max_ending_here = 0;
                s = i + 1;
            }
        }

        return new Result(start, end, max_so_far);

    }
}
