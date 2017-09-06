package org.x2a.example.cacheable;

import org.x2a.cutter.annotation.Cut;
import org.x2a.example.cacheable.cache.CacheParams;
import org.x2a.example.cacheable.cache.Cacheable;

public class Library {


    @Cut(Cacheable.class) @CacheParams(key = "title")
    public Book findBook(String title) {
        System.out.println("Getting " + title + " from the library");
        //expensive search here
        return new Book();
    }
}
