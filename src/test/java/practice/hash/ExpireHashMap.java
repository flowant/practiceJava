package practice.hash;

import java.util.HashMap;


public class ExpireHashMap<K, V> {

    HashMap<K, V> data = new HashMap<>();
    HashMap<K, Long> expiration = new HashMap<>();

    public V put(K key, V value, long expiredMs) {
        if (key == null || expiredMs <= 0) {
            data.remove(key);
            expiration.remove(key);
            return null;
        }

        long curTimeMs = System.currentTimeMillis();
        long expiredTimeMs = curTimeMs + expiredMs;

        // curTimeMs + expireMs shoud be less than or equals to Long.MAX_VALUE
        // expiredTimeMs < 0 means overflow
        if (expiredTimeMs < 0) {
            expiredTimeMs = Long.MAX_VALUE;
        }

        data.put(key, value);
        expiration.put(key, expiredTimeMs);

        return value;
    }

    public V get(K key) {
        if (data.containsKey(key) == false) {
            return null;
        }

        V value = (V) data.get(key);
        long expiredTime = expiration.get(key);

        if (System.currentTimeMillis() > expiredTime) {
            data.remove(key);
            expiration.remove(key);
            return null;
        } else {
            return value;
        }
    }
}
