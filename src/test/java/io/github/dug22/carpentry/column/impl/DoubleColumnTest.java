package io.github.dug22.carpentry.column.impl;

import io.github.dug22.carpentry.column.parser.DoubleParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleColumnTest {

    private DoubleColumn doubleColumn;

    @BeforeEach
    public void setup(){
        doubleColumn = DoubleColumn.create("Doubles", new Double[]{20.2, 30.3, 20.2, 25.7});
    }

    @Test
    public void appendTest(){
        doubleColumn.append(2d);
        doubleColumn.appendNull();
        doubleColumn.appendAll(new Double[]{20D, 20.4});
        assertAll(
                () -> assertEquals(8, doubleColumn.size()),
                () -> assertArrayEquals(new Double[]{20.2, 30.3, 20.2, 25.7, 2D, Double.NaN, 20D, 20.4}, doubleColumn.getValues())
        );
    }

    @Test
    public void copyTest(){
        DoubleColumn copy = doubleColumn.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void emptyCopyTest(){
        DoubleColumn emptyCopy = doubleColumn.emptyCopy();
        assertEquals(0, emptyCopy.size());
    }

    @Test
    public void uniqueTest(){
        DoubleColumn unique = doubleColumn.unique();
        assertAll(
                () -> assertEquals(3, unique.size()),
                () -> assertArrayEquals(new Double[]{20.2, 30.3, 25.7}, unique.getValues())
        );
    }

    @Test
    public void applyTest(){
        DoubleColumn apply = doubleColumn.apply(i -> i * 2);
        assertAll(
                () -> assertEquals(4, apply.size()),
                () -> assertArrayEquals(new Double[]{40.4, 60.6, 40.4, 51.4}, apply.getValues())
        );
    }

    @Test
    public void filterTest() {
        DoubleColumn filter = doubleColumn.filter(i -> i > 25);
        assertAll(
                () -> assertEquals(2, filter.size()),
                () -> assertArrayEquals(new Double[]{30.3, 25.7}, filter.getValues())
        );
    }

    @Test
    public void sortTest() {
        assertAll(
                () -> {
                    doubleColumn.sortAscending();
                    assertArrayEquals(new Double[]{20.2, 20.2, 25.7, 30.3}, doubleColumn.getValues());
                },

                () -> {
                    doubleColumn.sortDescending();
                    assertArrayEquals(new Double[]{30.3, 25.7, 20.2, 20.2}, doubleColumn.getValues());
                });
    }

    @Test
    public void replaceTest(){
        doubleColumn.replace(new Double[]{20.2},  21.2);
        assertArrayEquals(new Double[]{21.2, 30.3, 21.2, 25.7}, doubleColumn.getValues());
    }

    @Test
    public void parserTest(){
        DoubleParser parser = doubleColumn.getColumnParser();
        String doubleValueOne = "2,002.50";
        assertEquals(2002.50, parser.parse(doubleValueOne));
        String doubleValueTwo = "20,002.50";
        assertEquals(20002.50, parser.parse(doubleValueTwo));
        String doubleValueThree = "1,200,002.50";
        assertEquals(1_200_002.50, parser.parse(doubleValueThree));
    }
}
