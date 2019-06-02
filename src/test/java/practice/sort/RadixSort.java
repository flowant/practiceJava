package practice.sort;


import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RadixSort {

    public static <T> boolean sort(T[] in, ToIntFunction<T> getKey, int radix, boolean ascending) {

        if (in == null || in.length < 2 || getKey == null || radix < 2) {
            return false;
        }

        T[] before = in;
        @SuppressWarnings("unchecked")
        T[] after = (T[]) new Object[in.length];
        int[] numbers = new int[in.length];

        for (int base = 1, max = 1; max / base > 0; base *= radix) {
            int[] count = new int[radix];

            for (int i = 0; i < before.length; i++) {
                int key = getKey.applyAsInt(before[i]);
                if (base == 1 && key > max) max = key;
                numbers[i] =  key / base % radix;
                count[numbers[i]]++;
            }

            if (ascending) {
                for (int i = 1; i < count.length; i++) {
                    count[i] += count[i - 1];
                }
            } else {
                for (int i = count.length - 2; i >= 0; i--) {
                    count[i] += count[i + 1];
                }
            }

            for (int i = before.length - 1; i >= 0; i--) {
                after[--count[numbers[i]]] = before[i];
            }

            T[]  temp = after;
            after = before;
            before = temp;
        }

        if (before != in) {
            System.arraycopy(before, 0, in, 0, before.length);
        }

        return true;
    }

    public static boolean sort(int in[], int radix) {
        if (in == null || in.length < 2 || radix < 2) {
            return false;
        }

        int[] before = in;
        int[] after = new int[in.length];

        for (int base = 1, max = 1; max / base > 0; base *= radix) {
            int[] count = new int[radix];

            for (int i = 0; i < before.length; i++) {
                int key = before[i];
                if (base == 1 && key > max) max = key;
                int number = key / base % radix;
                count[number]++;
            }

            for (int i = 1; i < count.length; i++) {
                count[i] += count[i - 1];
            }

            for (int i = before.length - 1; i >= 0; i--) {
                after[--count[before[i] / base % radix]] = before[i];
            }

            int[] temp = after;
            after = before;
            before = temp;
        }

        if (in != before) {
            System.arraycopy(before, 0, in, 0, before.length);
        }

        return true;
    }

    public static int[] makeSample(int cntSample, int maxValue) {
        Random random = new Random();
        return IntStream
                .generate(() -> random.nextInt(maxValue))
                .limit(cntSample)
                .toArray();
    }

    public static class Person {
        int id;

        public Person(int id) {
            this.id = id;

        }

        public int getId() {
            return id;
        }
    }

    public static Person[] makeObjectSample(int cntSample, int maxValue) {
        Random random = new Random();
        return Stream.generate(() -> new Person(random.nextInt(maxValue)))
                .limit(cntSample)
                .toArray(Person[]::new);
    }

    public static int cntSample = 1000000;
    public static int maxValue = Integer.MAX_VALUE ;
    // the denominator at the second parameter of Math.pow means the outer iteration count in radix sort.
    public static int radix = ((int) Math.pow(Integer.MAX_VALUE, 1.0/3.0)) + 1;

    @Test
    public void printTestCondition() {
        System.out.println("radix:" + radix);
        System.out.println("cntSample:" + cntSample);
        System.out.println("maxValue:" + maxValue);
    }

    @Test
    public void testSort() {
        IntStream.range(0, 16).forEach(this::testSort);
        IntStream.range(100000, 100011).forEach(this::testSort);
    }

    public void testSort(int cnt) {
        int[] test = makeSample(cnt, maxValue);
        int[] confirm = Arrays.copyOf(test, test.length);

        Arrays.sort(confirm);
        sort(test, radix);

        Assert.assertArrayEquals(confirm, test);
    }

    @Test
    public void testGenericSort() {
        IntStream.range(0, 16).forEach(this::testGenericSort);
        IntStream.range(100000, 100011).forEach(this::testGenericSort);
    }

    public void testGenericSort(int cnt) {
        Person[] test = makeObjectSample(cnt, maxValue);
        Person[] confirm = Arrays.copyOf(test, test.length);

        Arrays.sort(confirm, Comparator.comparingInt(Person::getId));
        sort(test, Person::getId, radix, true);
        Assert.assertArrayEquals(confirm, test);

        Arrays.sort(confirm, Comparator.comparingInt(Person::getId).reversed());
        sort(test, Person::getId, radix, false);
        Assert.assertArrayEquals(confirm, test);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkIntRadixSort() {
        int[] array = makeSample(cntSample, maxValue);
        sort(array, radix);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkDefaultSort() {
        int[] array = makeSample(cntSample, maxValue);
        Arrays.sort(array);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkGenericRadixSort() {
        Person[] array = makeObjectSample(cntSample, maxValue);
        sort(array, Person::getId, radix, true);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkGenericDefaultSort() {
        Person[] array = makeObjectSample(cntSample, maxValue);
        Arrays.sort(array, Comparator.comparingInt(Person::getId));
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkStreamParallelGenericDefaultSort() {
        Person[] array = makeObjectSample(cntSample, maxValue);
        Stream.of(array)
                .parallel()
                .sorted(Comparator.comparingInt(Person::getId))
                .toArray();
    }

    /*
        Intel(R) Core(TM) i7-4790K CPU @ 4.00GHz
        16GB Ram

        JMH version: 1.21
        VM version: JDK 11.0.2, OpenJDK 64-Bit Server VM, 11.0.2+9-Ubuntu-3ubuntu118.04.3
        VM invoker: /usr/lib/jvm/java-11-openjdk-amd64/bin/java

        Benchmark Test Condition:
        radix:1291 = ((int) Math.pow(Integer.MAX_VALUE, 1.0/3.0)) + 1;
        cntSample:10,000,000;
        maxValue:2,147,483,647 = Integer.MAX_VALUE ;

        Benchmark Test Result:
        Benchmark                                            Mode  Cnt  Score   Error  Units
        RadixSort.benchmarkDefaultSort                       avgt    3  0.780 ± 0.005   s/op
        RadixSort.benchmarkGenericDefaultSort                avgt    3  5.034 ± 0.724   s/op
        RadixSort.benchmarkGenericRadixSort                  avgt    3  3.094 ± 0.637   s/op
        RadixSort.benchmarkIntRadixSort                      avgt    3  0.352 ± 0.028   s/op
        RadixSort.benchmarkStreamParallelGenericDefaultSort  avgt    3  1.686 ± 0.312   s/op

        Benchmark Test Condition:
        radix:216 = ((int) Math.pow(Integer.MAX_VALUE, 1.0/4.0)) + 1;
        cntSample:1,000,000
        maxValue:2147483647

        Benchmark Test Result:
        Benchmark                                            Mode  Cnt  Score    Error  Units
        RadixSort.benchmarkDefaultSort                       avgt    3  0.068 ±  0.003   s/op
        RadixSort.benchmarkGenericDefaultSort                avgt    3  0.316 ±  0.071   s/op
        RadixSort.benchmarkGenericRadixSort                  avgt    3  0.294 ±  0.042   s/op
        RadixSort.benchmarkIntRadixSort                      avgt    3  0.033 ±  0.001   s/op
        RadixSort.benchmarkStreamParallelGenericDefaultSort  avgt    3  0.116 ±  0.014   s/op

        Benchmark Test Condition:
        radix:46341, ((int) Math.pow(Integer.MAX_VALUE, 1.0/2.0)) + 1;
        cntSample:1,000,000
        maxValue:2,147,483,647

        Benchmark Test Result:
        Benchmark                                            Mode  Cnt  Score   Error  Units
        RadixSort.benchmarkDefaultSort                       avgt    3  0.069 ± 0.007   s/op
        RadixSort.benchmarkGenericDefaultSort                avgt    3  0.316 ± 0.050   s/op
        RadixSort.benchmarkGenericRadixSort                  avgt    3  0.149 ± 0.052   s/op
        RadixSort.benchmarkIntRadixSort                      avgt    3  0.037 ± 0.003   s/op
        RadixSort.benchmarkStreamParallelGenericDefaultSort  avgt    3  0.118 ± 0.096   s/op

     */

    //@Test
    public void benchmarkTest() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + RadixSort.class.getSimpleName() + ".*")
                .warmupIterations(1)
                .measurementIterations(3)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}