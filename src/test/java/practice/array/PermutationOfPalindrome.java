package practice.array;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.BitSet;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class PermutationOfPalindrome {
    static class MutableInt {
        int value;

        public MutableInt(int value) {
            this.value = value;
        }

        int incrementAndGet() {
            return ++value;
        }
    }

    public static boolean isPermutaionOfPalindromeMI(String s) {
        if (s == null || s.length() < 1) {
            return false;
        }

        int cntOdd = 0;
        HashMap<Integer, MutableInt> mapCodePointCounter = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            int codePoint = s.codePointAt(i);
            mapCodePointCounter.putIfAbsent(codePoint, new MutableInt(0));
            if (mapCodePointCounter.get(codePoint).incrementAndGet() % 2 == 1) {
                cntOdd++;
            } else {
                cntOdd--;
            }
        }

        return cntOdd <= 1;
    }

    public static boolean isPermutaionOfPalindromeBS(String s) {
        if (s == null || s.length() < 1) {
            return false;
        }

//		BitSet codePointToggle = new BitSet(Character.MAX_CODE_POINT);
        BitSet codePointToggle = new BitSet();

        for (int i = 0; i < s.length(); i++) {
            int codePoint = s.codePointAt(i);
            codePointToggle.flip(codePoint);
        }

        return codePointToggle.cardinality() <= 1;
    }

    public static boolean isP(String phrase) {
        if (phrase == null || phrase.length() < 1) {
            return false;
        }

        int bitVector = createBitVector(phrase);
        return bitVector == 0 || checkExactlyOneBitSet(bitVector);
    }

    static int createBitVector(String phrase) {
        int bitVector = 0;
        for (char c : phrase.toCharArray()) {
            int x = Character.getNumericValue(c);
            bitVector = toggle(bitVector, x);
        }
        return bitVector;
    }

    static int toggle(int bitVector, int index) {
        if (index < 0)
            return bitVector;

        int mask = 1 << index;
        if ((bitVector & mask) == 0) {
            bitVector |= mask;
        } else {
            bitVector &= ~mask;
        }
        return bitVector;
    }

    static boolean checkExactlyOneBitSet(int bitVector) {
        return (bitVector & (bitVector - 1)) == 0;
    }

//    @Benchmark
    public void benchmakrIsPermutationOfPalindromeP() {
        testIsPermutaionOfPalindrome(PermutationOfPalindrome::isP);
    }

    @Benchmark
    public void benchmakrIsPermutationOfPalindromeBS() {
        testIsPermutaionOfPalindrome(PermutationOfPalindrome::isPermutaionOfPalindromeBS);
    }

    @Benchmark
    public void benchmakrIsPermutationOfPalindromeMI() {
        testIsPermutaionOfPalindrome(PermutationOfPalindrome::isPermutaionOfPalindromeMI);
    }

    @Test
    public void testIsPermutationOfPalindromes() {
        testIsPermutaionOfPalindrome(PermutationOfPalindrome::isPermutaionOfPalindromeBS);
        testIsPermutaionOfPalindrome(PermutationOfPalindrome::isPermutaionOfPalindromeMI);
        testIsPermutaionOfPalindrome(PermutationOfPalindrome::isP);
    }

    public void testIsPermutaionOfPalindrome(Function<String, Boolean> f) {
        assertFalse(f.apply(null));
        assertFalse(f.apply(""));
        assertTrue(f.apply("a"));
        assertTrue(f.apply("abab"));
        assertTrue(f.apply("abwab"));
        assertTrue(f.apply("absfwabsf"));
        assertFalse(f.apply("absfwdabsf"));
        assertTrue(f.apply("absfabsf한한글글"));
        assertTrue(f.apply("absfabs f한 한글글"));
        assertTrue(f.apply("absfabs f한 한글 글"));
        assertTrue(f.apply("abs'fa`bs f한 한글'글"));

        StringBuffer sbFirst = new StringBuffer();
        for (int i = 0; i < 10000000; i++)
            sbFirst.append("한한글글");
        for (int i = 0; i < 10000000; i++)
            sbFirst.append("abab");
        assertTrue(f.apply(sbFirst.toString()));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(".*" + PermutationOfPalindrome.class.getSimpleName() + ".*")
//                .verbosity(VerboseMode.EXTRA) //VERBOSE OUTPUT
//                .addProfiler(HotspotMemoryProfiler.class)
//                .addProfiler(StackProfiler.class)
                .warmupIterations(5).measurementIterations(5).forks(1).build();

        new Runner(opt).run();
    }

}
