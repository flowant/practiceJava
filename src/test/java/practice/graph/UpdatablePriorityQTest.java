package practice.graph;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Comparator;

public class UpdatablePriorityQTest {

    static class Data {
        int value;
        public Data(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    @Test
    public void tester() {
        int cnt = 30;
        UpdatablePriorityQ<Data> mq = new UpdatablePriorityQ<>(Comparator.comparingInt(Data::getValue));
        for (int i = cnt; i > 0; i--) {
            assertTrue(mq.offer(new Data(i)));
        }
        assertEquals(cnt, mq.size);

        Data data = new Data(15);
        assertTrue(mq.offer(data));

        data.value = 0;
        assertTrue(mq.update(data));

        assertEquals(mq.poll().value, 0);

        data = new Data(15);
        assertTrue(mq.offer(data));

        data.value = 100;
        assertTrue(mq.update(data));

        for (int i = 1; i <= cnt; i++) {
            assertEquals(mq.poll().getValue(), i);
        }
        assertEquals(mq.poll().getValue(), 100);
    }

}
