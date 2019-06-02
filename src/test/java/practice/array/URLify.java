package practice.array;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class URLify {

    public static int urlify(char[] url, int orgLength) {
        if (url == null || orgLength == 0) {
            return 0;
        }

        int cntSpace = 0;
        for (int i = 0; i < orgLength; i++) {
            if (url[i] == ' ')
                cntSpace++;
        }
        int destLength = cntSpace * 2 + orgLength;

        for (int oIdx = orgLength - 1, dIdx = destLength; oIdx >= 0; oIdx--) {
            if (url[oIdx] == ' ') {
                url[--dIdx] = '0';
                url[--dIdx] = '2';
                url[--dIdx] = '%';
            } else {
                url[--dIdx] = url[oIdx];
            }
        }

        return destLength;
    }

    final static int TEST_BUFFER_SIZE = 128;

    public static void testUrlify(String url, String expUrlified) {
        char[] buffer = new char[TEST_BUFFER_SIZE];
        System.arraycopy(url.toCharArray(), 0, buffer, 0, url.length());
        urlify(buffer, url.length());
        int expLength = expUrlified.length();
        Assert.assertTrue(Arrays.equals(expUrlified.toCharArray(), 0, expLength, buffer, 0, expLength));
    }

    @Test
    public void test() {
        testUrlify("", "");
        testUrlify(" ", "%20");
        testUrlify(" a", "%20a");
        testUrlify(" a ", "%20a%20");
        testUrlify(" abc ", "%20abc%20");
        testUrlify(" abc de", "%20abc%20de");
        testUrlify(" abc de  ", "%20abc%20de%20%20");
        testUrlify(" abc de  f", "%20abc%20de%20%20f");
        testUrlify("0 abc de  f", "0%20abc%20de%20%20f");

    }

}
