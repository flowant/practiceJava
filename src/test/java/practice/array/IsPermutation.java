
package practice.array;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class IsPermutation {

    public static boolean isPermutation(String s1, String s2) {
        /*
         * 1. Initialize HashMap.
         * 2. Save character as a key and it's count as a value through the first string
         * 3. If map doesn't contains the character in the second string return false
         *    Else if map contains the character as a key decrease counter value in the map.
         *    If the value is negative then return false.
         */
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }

        Map<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            count.put(c, count.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < s2.length(); i++) {
            char c = s2.charAt(i);
            // default value 0 means there is no entry using c as a key
            int value = count.getOrDefault(c, 0) - 1;
            if (value < 0) {
                return false;
            }
            count.put(c, value);
        }

        return true;
    }

    static class IntCounter {

        int value;

        public IntCounter(int value) {
            this.value = value;
        }

        public int incrementAndGet() {
            return ++value;
        }

        public int decrementAndGet() {
            return --value;
        }
    }

    public static boolean isPermutationBoxing(String first, String second) {

        if (first.length() != second.length()) {
            return false;
        }

        Map<Integer, IntCounter> charCountMap = new HashMap<>();
        for (int i = 0; i < first.length(); i++) {
            int charCode = first.codePointAt(i);
            charCountMap.putIfAbsent(charCode, new IsPermutation.IntCounter(0));
            charCountMap.get(charCode).incrementAndGet();
        }

        for (int i = 0; i < second.length(); i++) {
            int charCode = second.codePointAt(i);
            IntCounter counter = charCountMap.get(charCode);
            if (counter == null || counter.decrementAndGet() < 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPermutationIArray(String first, String second) {

        if (first.length() != second.length()) {
            return false;
        }

        Map<Integer, int[]> charCountMap = new HashMap<>();
        for (int i = 0; i < first.length(); i++) {
            int charCode = first.codePointAt(i);
            charCountMap.putIfAbsent(charCode, new int[1]);
            charCountMap.get(charCode)[0]++;
        }

        for (int i = 0; i < second.length(); i++) {
            int charCode = second.codePointAt(i);
            int[] counter = charCountMap.get(charCode);
            if (counter == null || --counter[0] < 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPermutationAI(String first, String second) {

        if (first.length() != second.length()) {
            return false;
        }

        Map<Integer, AtomicInteger> charCountMap = new HashMap<>();
        for (int i = 0; i < first.length(); i++) {
            int charCode = first.codePointAt(i);
            charCountMap.putIfAbsent(charCode, new AtomicInteger(0));
            charCountMap.get(charCode).incrementAndGet();
        }

        for (int i = 0; i < second.length(); i++) {
            AtomicInteger value = charCountMap.get(second.codePointAt(i));
            if (value == null || value.decrementAndGet() < 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPermutationIA(String first, String second) {
        return isPermutationIA(first, second, Character.MAX_CODE_POINT);
    }

    public static boolean isPermutationIA(String first, String second, int codeAddrSpace) {

        if (first.length() != second.length()) {
            return false;
        }

        int[] charCountMap = new int[codeAddrSpace];
        for (int i = 0; i < first.length(); i++) {
            charCountMap[first.codePointAt(i)]++;
        }

        for (int i = 0; i < second.length(); i++) {
            if (--charCountMap[second.codePointAt(i)] < 0) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void testIsPermutations() {
        testIsPermutation(IsPermutation::isPermutation);
        testIsPermutation(IsPermutation::isPermutationIA);
        testIsPermutation(IsPermutation::isPermutationAI);
        testIsPermutation(IsPermutation::isPermutationBoxing);
        testIsPermutation(IsPermutation::isPermutationIArray);
    }

    @Benchmark
    public void benchmarkIsPermutation() {
        testIsPermutation(IsPermutation::isPermutation);
    }

    @Benchmark
    public void benchmarkIsPermutationIA() {
        testIsPermutation(IsPermutation::isPermutationIA);
    }

    @Benchmark
    public void benchmarkIsPermutationAI() {
        testIsPermutation(IsPermutation::isPermutationAI);
    }

    @Benchmark
    public void benchmarkIsPermutationBoxing() {
        testIsPermutation(IsPermutation::isPermutationBoxing);
    }

    @Benchmark
    public void benchmarkIsPermutationIArray() {
        testIsPermutation(IsPermutation::isPermutationIArray);
    }

    @Param({ "10000", "10000000" })
    int loop;

    public void testIsPermutation(BiFunction<String, String, Boolean> isPermutation) {
        Assert.assertTrue(isPermutation.apply("", ""));
        Assert.assertTrue(isPermutation.apply("한글", "글한"));
        Assert.assertFalse(isPermutation.apply("한글", "글한한"));
        Assert.assertFalse(isPermutation.apply("abc", "ba"));
        Assert.assertFalse(isPermutation.apply("abc", "bacc"));
        Assert.assertFalse(isPermutation.apply("abcs", "bacc"));

        StringBuffer sbFirst = new StringBuffer();
        for (int i = 0; i < loop; i++)
            sbFirst.append("cabc");
        StringBuffer sbSecond = new StringBuffer();
        for (int i = 0; i < loop; i++)
            sbSecond.append("acbc");

        Assert.assertTrue(isPermutation.apply(sbFirst.toString(), sbSecond.toString()));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(".*" + IsPermutation.class.getSimpleName() + ".*")
//                .verbosity(VerboseMode.EXTRA) //VERBOSE OUTPUT
//                .addProfiler(HotspotMemoryProfiler.class)
//                .addProfiler(StackProfiler.class)
                .warmupIterations(1).measurementIterations(3).forks(1).build();

        new Runner(opt).run();
    }
}
