package io.github.dug22.carpentry.column.impl;

import io.github.dug22.carpentry.column.parser.ShortParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShortColumnTest {

    private ShortColumn shortColumn;

    @BeforeEach
    public void setup() {
        shortColumn = ShortColumn.create("Shorts", new Short[]{4, 10, 8, 10});
    }

    @Test
    public void appendTest() {
        shortColumn.append((short) 3);
        shortColumn.appendNull();
        shortColumn.appendAll(new Short[]{10, 20});
        assertAll(
                () -> assertEquals(8, shortColumn.size()),
                () -> assertArrayEquals(new Short[]{4, 10, 8, 10, 3, -32768, 10, 20}, shortColumn.getValues())
        );
    }

    @Test
    public void copyTest() {
        ShortColumn copy = shortColumn.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void emptyCopyTest() {
        ShortColumn emptyCopy = shortColumn.emptyCopy();
        assertEquals(0, emptyCopy.size());
    }

    @Test
    public void uniqueTest() {
        ShortColumn unique = shortColumn.unique();
        assertAll(
                () -> assertEquals(3, unique.size()),
                () -> assertArrayEquals(new Short[]{4, 10, 8}, unique.getValues())
        );
    }

    @Test
    public void applyTest() {
        ShortColumn apply = shortColumn.apply(i -> (short) (i * 2));
        assertAll(
                () -> assertEquals(4, apply.size()),
                () -> assertArrayEquals(new Short[]{8, 20, 16, 20}, apply.getValues())
        );
    }

    @Test
    public void filterTest() {
        ShortColumn filter = shortColumn.filter(i -> i > 8);
        assertAll(
                () -> assertEquals(2, filter.size()),
                () -> assertArrayEquals(new Short[]{10, 10}, filter.getValues())
        );
    }

    @Test
    public void sortTest() {
        assertAll(
                () -> {
                    shortColumn.sortAscending();
                    assertArrayEquals(new Short[]{4, 8, 10, 10}, shortColumn.getValues());
                },
                () -> {
                    shortColumn.sortDescending();
                    assertArrayEquals(new Short[]{10, 10, 8, 4}, shortColumn.getValues());
                }
        );
    }

    @Test
    public void replaceTest() {
        shortColumn.replace(new Short[]{10}, (short) 11);
        assertArrayEquals(new Short[]{4, 11, 8, 11}, shortColumn.getValues());
    }

    @Test
    public void parserTest() {
        ShortParser parser = shortColumn.getColumnParser();
        String shortValueOne = "2,000";
        assertEquals((short) 2000, parser.parse(shortValueOne));
        String shortValueTwo = "20,000";
        assertEquals((short) 20000, parser.parse(shortValueTwo));
    }
}