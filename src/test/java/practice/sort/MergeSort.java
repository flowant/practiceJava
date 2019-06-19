package practice.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class MergeSort {

    public static <T> void sort(T[] in, Comparator<T> c) {
        sort(in, c, 0, in.length - 1);
    }

    /**
     * Merge Sort
     * Find the middle point to divide the array into two halves.
     * Call sort function recursively for the first half.
     * Likewise, call sort function recursively for the last half.
     * And merge two arrays sorted in previous recursive calls.
     * The base condition is that the array contains at least two items.
     *
     * @param <T>
     * @param in
     * @param c
     * @param leftIndex
     * @param rightIndex
     */
    public static <T> void sort(T[] in, Comparator<T> c, int leftIndex, int rightIndex) {
        //Input validation
        if (in == null || c == null || leftIndex < 0 || rightIndex < 0 || leftIndex >= rightIndex) {
            return;
        }

        int middleIndex = (leftIndex + rightIndex) / 2;
        sort(in, c, leftIndex, middleIndex);
        sort(in, c, middleIndex + 1, rightIndex);
        merge(in, c, leftIndex, middleIndex, rightIndex);
    }

    public static <T> void merge(T[] in, Comparator<T> c, int leftIndex, int middleIndex, int rightIndex) {

        // Before sorting, we need to copy original two arrays to buffers
        T[] leftArray = Arrays.copyOfRange(in, leftIndex, middleIndex + 1);
        T[] rightArray = Arrays.copyOfRange(in, middleIndex + 1, rightIndex + 1);

        int iIn = leftIndex;
        int iLeft = 0;
        int iRight = 0;

        // Compare elements of each array from index 0 to length of those arrays.
        // If one of these indexes equals to their length then exit loop.
        while(iLeft < leftArray.length && iRight < rightArray.length) {
            // We assume the order is ascending.
            // Compare the value, copy a smaller value to the In array.
            // Increase the index pointing smaller value and the index of the In array.
            // when the values are the same we pick the left element first.
            // It can make our sort to be stable. that means we keep original sequence of elements that have the same value.
            if (c.compare(leftArray[iLeft], rightArray[iRight]) <= 0) {
                in[iIn++] = leftArray[iLeft++];
            } else {
                in[iIn++] = rightArray[iRight++];
            }
        }

        // The left array still has values that should be copied to the In array.
        // So, if the loop index doesn't equal to it's length,
        // copy rest elements to current position of the In array.
        while (iLeft < leftArray.length) {
            in[iIn++] = leftArray[iLeft++];
        }

        // The remainders of the right array are the same as the input.
        // Because they are copied from the input array.

    }

    static class Person {
        int id;

        public Person(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

    }

    public void testSort(int cntSample) {
        Random rand = new Random();

//        Person[] people = new Person[cntSample];
//        for (int i = 0; i < cntSample; i++) {
//            people[i] = new Person(rand.nextInt());
//        }

        Person[] people = Stream
               .generate(() -> new Person(rand.nextInt()))
               .limit(cntSample)
               .toArray(Person[]::new);

        Person[] confirm = Arrays.copyOf(people, people.length);

        sort(people, Comparator.comparingInt(Person::getId));
        Arrays.sort(confirm, Comparator.comparingInt(Person::getId));
        Assert.assertArrayEquals(confirm, people);

        sort(people, Comparator.comparingInt(Person::getId).reversed());
        Arrays.sort(confirm, Comparator.comparingInt(Person::getId).reversed());
        Assert.assertArrayEquals(confirm, people);
    }

    @Test
    public void testSort() {
        IntStream.range(0, 16).forEach(this::testSort);
        IntStream.range(1000, 1100).forEach(this::testSort);
    }

}
