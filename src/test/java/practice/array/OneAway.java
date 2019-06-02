package practice.array;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OneAway {
    public static boolean isOneAway(String strShort, String strLong) {
        if (strShort == null || strLong == null) {
            return false;
        }

        int difference = strLong.length() - strShort.length();

        if (difference < -1 || difference > 1) {
            return false;
        }

        if (difference < 0) {
            difference = -difference;
            String temp = strLong;
            strLong = strShort;
            strShort = temp;
        }

        boolean isDiffFounded = false;

        for (int iS = 0, iL = 0; iS < strShort.length(); iS++, iL++) {
            if (strShort.charAt(iS) != strLong.charAt(iL)) {
                if (isDiffFounded) {
                    return false;
                }
                isDiffFounded = true;
                if (difference == 1) {
                    iS--;
                }
            }
        }

        return difference != 0 /* insert or replace case*/ || isDiffFounded;
    }

    // abc   abc   abc
    // abcd  sabc  asbc

    // abc abc
    // sbc asc

    @Test
    public void testIsOneAway() {
        assertFalse(isOneAway(null, null));
        assertFalse(isOneAway(null, ""));
        assertFalse(isOneAway("", ""));
        assertFalse(isOneAway("abc", "abc"));

        // cases of insert or remove
        assertTrue(isOneAway("sabc", "abc"));
        assertTrue(isOneAway("abc", "sabc"));
        assertTrue(isOneAway("asbc", "abc"));
        assertTrue(isOneAway("abc", "absc"));
        assertTrue(isOneAway("abcs", "abc"));
        assertTrue(isOneAway("abc", "abcs"));
        assertTrue(isOneAway("가나다", "가나다라"));
        assertFalse(isOneAway("가나다", "가나다라마"));
        assertFalse(isOneAway("abc", "abcsd"));
        assertFalse(isOneAway("asbdc", "abc"));

        // cases of replace
        assertTrue(isOneAway("sbc", "abc"));
        assertTrue(isOneAway("asc", "abc"));
        assertTrue(isOneAway("abs", "abc"));
        assertTrue(isOneAway("abc", "sbc"));
        assertTrue(isOneAway("abc", "asc"));
        assertTrue(isOneAway("abc", "abs"));

    }

}
