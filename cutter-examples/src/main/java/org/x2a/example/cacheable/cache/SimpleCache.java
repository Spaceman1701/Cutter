package org.x2a.example.cacheable.cache;

import java.util.concurrent.ConcurrentHashMap;

public class SimpleCache {

    private static final SimpleCache INSTANCE = new SimpleCache();

    public static SimpleCache getInstance() {
        return INSTANCE;
    }

    private final ConcurrentHashMap<String, Object> cache;

    private SimpleCache() {
        cache = new ConcurrentHashMap<>();
    }


    public void put(String key, Object o) {
        cache.put(key, o);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        System.out.println("Got object with key: " + key + " from the cache");
        return (T) cache.get(key);
    }

    public boolean contains(String key) {
        return cache.containsKey(key);
    }

}
