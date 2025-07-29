package io.github.dug22.carpentry.column.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringColumnTest {

    private StringColumn stringColumn;

    @BeforeEach
    public void setup(){
        stringColumn = StringColumn.create("Strings", new String[]{"Cat", "Dog", "Fish", "Cat"});
    }

    @Test
    public void appendTest(){
        stringColumn.append("Lizard");
        stringColumn.append("Dog");
        stringColumn.appendNull();
        stringColumn.appendAll(new String[]{"Rabbit", "Lizard"});
        assertAll(
                () -> assertEquals(9, stringColumn.size()),
                () -> assertArrayEquals(new String[]{"Cat", "Dog", "Fish", "Cat", "Lizard", "Dog", "NA", "Rabbit", "Lizard"}, stringColumn.getValues())
        );
    }

    @Test
    public void copyTest(){
        StringColumn copy = stringColumn.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void emptyCopyTest(){
        StringColumn emptyCopy = stringColumn.emptyCopy();
        assertEquals(0, emptyCopy.size());
    }

    @Test
    public void uniqueTest(){
        StringColumn unique = stringColumn.unique();
        assertAll(
                () -> assertEquals(3, unique.size()),
                () -> assertArrayEquals(new String[]{"Cat", "Dog", "Fish"}, unique.getValues())
        );
    }

    @Test
    public void applyTest(){
        StringColumn apply = stringColumn.apply(String::toUpperCase);
        assertAll(
                () -> assertEquals(4, apply.size()),
                () -> assertArrayEquals(new String[]{"CAT", "DOG", "FISH", "CAT"}, apply.getValues())
        );
    }

    @Test
    public void filterTest(){
        StringColumn filter = stringColumn.filter(word -> word.startsWith("D") || word.startsWith("F"));
        assertAll(
                () -> assertEquals(2, filter.size()),
                () -> assertArrayEquals(new String[]{"Dog", "Fish"}, filter.getValues())
        );
    }

    @Test
    public void sortTest(){
        assertAll(
                () -> {
                    stringColumn.sortAscending();
                    assertArrayEquals(new String[]{"Cat", "Cat", "Dog", "Fish"}, stringColumn.getValues());
                },
                () -> {
                    stringColumn.sortDescending();
                    assertArrayEquals(new String[]{"Fish", "Dog", "Cat", "Cat"}, stringColumn.getValues());
                }
        );
    }

    @Test
    public void replaceTest(){
        stringColumn.replace(new String[]{"Cat"}, "Dog");
        assertArrayEquals(new String[]{"Dog", "Dog", "Fish", "Dog"}, stringColumn.getValues());
    }
}
