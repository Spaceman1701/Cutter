package org.x2a.example.cacheable.cache;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) //needs to be searchable with reflection
public @interface CacheParams {
    String key();
}
