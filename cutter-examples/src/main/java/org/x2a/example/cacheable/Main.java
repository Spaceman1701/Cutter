package org.x2a.example.cacheable;

public class Main {
    /*
    This example shows how Cutter can be used to implement transparent caching without any complex dependencies

    While the example here is simple, all the business logic is pure java which allows for arbitrarily complex solutions

    Expected Output:
    ----------------
        Getting Breakfast of Champions from the library
        Got object with key: Breakfast of Champions from the cache
    ----------------
     */


    public static void main(String[] args) {
        Library library = new Library();

        Book searchedForBook = library.findBook("Breakfast of Champions");
        Book cachedBook = library.findBook("Breakfast of Champions");
    }
}
