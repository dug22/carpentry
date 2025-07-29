package io.github.dug22.carpentry.column.impl;

import io.github.dug22.carpentry.column.parser.LongParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LongColumnTest {

    private LongColumn longColumn;

    @BeforeEach
    public void setup() {
        longColumn = LongColumn.create("Longs", new Long[]{4L, 10L, 8L, 10L});
    }

    @Test
    public void appendTest() {
        longColumn.append(3L);
        longColumn.appendNull();
        longColumn.appendAll(new Long[]{10L, 20L});
        assertAll(
                () -> assertEquals(8, longColumn.size()),
                () -> assertArrayEquals(new Long[]{4L, 10L, 8L, 10L, 3L, -9223372036854775808L, 10L, 20L}, longColumn.getValues())
        );
    }

    @Test
    public void copyTest() {
        LongColumn copy = longColumn.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void emptyCopyTest() {
        LongColumn emptyCopy = longColumn.emptyCopy();
        assertEquals(0, emptyCopy.size());
    }

    @Test
    public void uniqueTest() {
        LongColumn unique = longColumn.unique();
        assertAll(
                () -> assertEquals(3, unique.size()),
                () -> assertArrayEquals(new Long[]{4L, 10L, 8L}, unique.getValues())
        );
    }

    @Test
    public void applyTest() {
        LongColumn apply = longColumn.apply(i -> i * 2);
        assertAll(
                () -> assertEquals(4, apply.size()),
                () -> assertArrayEquals(new Long[]{8L, 20L, 16L, 20L}, apply.getValues())
        );
    }

    @Test
    public void filterTest() {
        LongColumn filter = longColumn.filter(i -> i > 8);
        assertAll(
                () -> assertEquals(2, filter.size()),
                () -> assertArrayEquals(new Long[]{10L, 10L}, filter.getValues())
        );
    }

    @Test
    public void sortTest() {
        assertAll(
                () -> {
                    longColumn.sortAscending();
                    assertArrayEquals(new Long[]{4L, 8L, 10L, 10L}, longColumn.getValues());
                },
                () -> {
                    longColumn.sortDescending();
                    assertArrayEquals(new Long[]{10L, 10L, 8L, 4L}, longColumn.getValues());
                }
        );
    }

    @Test
    public void replaceTest() {
        longColumn.replace(new Long[]{10L}, 11L);
        assertArrayEquals(new Long[]{4L, 11L, 8L, 11L}, longColumn.getValues());
    }

    @Test
    public void parserTest() {
        LongParser parser = longColumn.getColumnParser();
        String longValueOne = "2,000";
        assertEquals(2000L, parser.parse(longValueOne));
        String longValueTwo = "20,000";
        assertEquals(20000L, parser.parse(longValueTwo));
        String longValueThree = "1,200,000";
        assertEquals(1_200_000L, parser.parse(longValueThree));
    }
}