package io.github.dug22.carpentry.column.impl;

import io.github.dug22.carpentry.column.parser.IntegerParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntegerColumnTest {

    private IntegerColumn integerColumn;

    @BeforeEach
    public void setup() {
        integerColumn = IntegerColumn.create("Integers", new Integer[]{4, 10, 8, 10});
    }

    @Test
    public void appendTest() {
        integerColumn.append(3);
        integerColumn.appendNull();
        integerColumn.appendAll(new Integer[]{10, 20});
        assertAll(
                () -> assertEquals(8, integerColumn.size()),
                () -> assertArrayEquals(new Integer[]{4, 10, 8, 10, 3, -2147483648, 10, 20}, integerColumn.getValues())
        );
    }

    @Test
    public void copyTest() {
        IntegerColumn copy = integerColumn.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void emptyCopyTest() {
        IntegerColumn emptyCopy = integerColumn.emptyCopy();
        assertEquals(0, emptyCopy.size());
    }

    @Test
    public void uniqueTest() {
        IntegerColumn unique = integerColumn.unique();
        assertAll(
                () -> assertEquals(3, unique.size()),
                () -> assertArrayEquals(new Integer[]{4, 10, 8}, unique.getValues())
        );
    }

    @Test
    public void applyTest() {
        IntegerColumn apply = integerColumn.apply(i -> i * 2);
        assertAll(
                () -> assertEquals(4, apply.size()),
                () -> assertArrayEquals(new Integer[]{8, 20, 16, 20}, apply.getValues())
        );
    }

    @Test
    public void filterTest() {
        IntegerColumn filter = integerColumn.filter(i -> i > 8);
        assertAll(
                () -> assertEquals(2, filter.size()),
                () -> assertArrayEquals(new Integer[]{10, 10}, filter.getValues())
        );
    }

    @Test
    public void sortTest() {
        assertAll(
                () -> {
                    integerColumn.sortAscending();
                    assertArrayEquals(new Integer[]{4, 8, 10, 10}, integerColumn.getValues());
                },
                () -> {
                    integerColumn.sortDescending();
                    assertArrayEquals(new Integer[]{10, 10, 8, 4}, integerColumn.getValues());
                }
        );
    }

    @Test
    public void replaceTest() {
        integerColumn.replace(new Integer[]{10}, 11);
        assertArrayEquals(new Integer[]{4, 11, 8, 11}, integerColumn.getValues());
    }

    @Test
    public void parserTest() {
        IntegerParser parser = integerColumn.getColumnParser();
        String intValueOne = "2,000";
        assertEquals(2000, parser.parse(intValueOne));
        String intValueTwo = "20,000";
        assertEquals(20000, parser.parse(intValueTwo));
        String intValueThree = "1,200,000";
        assertEquals(1_200_000, parser.parse(intValueThree));
    }
}