package practice.hash;

import org.junit.Assert;
import org.junit.Test;

public class ExpireHashMapTest {
    @Test
    public void testExpire() {
        ExpireHashMap<Integer, Integer> expireHashmap = new ExpireHashMap<>();

        int maxCounter = 10;
        // check put, get
        for (int i = 0; i < maxCounter; i++) {
            expireHashmap.put(i, i, 1000);
            Assert.assertTrue(expireHashmap.get(i) == i);
        }

        // check update
        expireHashmap.put(0, 7, 1000);
        Assert.assertTrue(expireHashmap.get(0) == 7);
        expireHashmap.put(0, 0, 0);
        Assert.assertTrue(expireHashmap.get(0) == null);

        // check empty
        Assert.assertTrue(expireHashmap.get(Integer.MAX_VALUE) == null);

        expireHashmap.put(11, 11, Long.MAX_VALUE);
        Assert.assertTrue(expireHashmap.get(11) == 11);

        // check expire
        expireHashmap.put(100, 100, 1);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertTrue(expireHashmap.get(100) == null);
    }
}
