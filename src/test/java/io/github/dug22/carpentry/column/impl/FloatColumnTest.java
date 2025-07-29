package io.github.dug22.carpentry.column.impl;

import io.github.dug22.carpentry.column.parser.FloatParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FloatColumnTest {

    private FloatColumn floatColumn;

    @BeforeEach
    public void setup() {
        floatColumn = FloatColumn.create("Floats", new Float[]{20.2f, 30.3f, 20.2f, 25.7f});
    }

    @Test
    public void appendTest() {
        floatColumn.append(2f);
        floatColumn.appendNull();
        floatColumn.appendAll(new Float[]{20f, 20.4f});
        assertAll(
                () -> assertEquals(8, floatColumn.size()),
                () -> assertArrayEquals(new Float[]{20.2f, 30.3f, 20.2f, 25.7f, 2f, Float.NaN, 20f, 20.4f}, floatColumn.getValues())
        );
    }

    @Test
    public void copyTest() {
        FloatColumn copy = floatColumn.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void emptyCopyTest() {
        FloatColumn emptyCopy = floatColumn.emptyCopy();
        assertEquals(0, emptyCopy.size());
    }

    @Test
    public void uniqueTest() {
        FloatColumn unique = floatColumn.unique();
        assertAll(
                () -> assertEquals(3, unique.size()),
                () -> assertArrayEquals(new Float[]{20.2f, 30.3f, 25.7f}, unique.getValues())
        );
    }

    @Test
    public void applyTest() {
        FloatColumn apply = floatColumn.apply(i -> i * 2f);
        assertAll(
                () -> assertEquals(4, apply.size()),
                () -> assertArrayEquals(new Float[]{40.4f, 60.6f, 40.4f, 51.4f}, apply.getValues())
        );
    }

    @Test
    public void filterTest() {
        FloatColumn filter = floatColumn.filter(i -> i > 25);
        assertAll(
                () -> assertEquals(2, filter.size()),
                () -> assertArrayEquals(new Float[]{30.3f, 25.7f}, filter.getValues())
        );
    }

    @Test
    public void sortTest() {
        assertAll(
                () -> {
                    floatColumn.sortAscending();
                    assertArrayEquals(new Float[]{20.2f, 20.2f, 25.7f, 30.3f}, floatColumn.getValues());
                },
                () -> {
                    floatColumn.sortDescending();
                    assertArrayEquals(new Float[]{30.3f, 25.7f, 20.2f, 20.2f}, floatColumn.getValues());
                }
        );
    }

    @Test
    public void replaceTest() {
        floatColumn.replace(new Float[]{20.2f}, 21.2f);
        assertArrayEquals(new Float[]{21.2f, 30.3f, 21.2f, 25.7f}, floatColumn.getValues());
    }

    @Test
    public void parserTest() {
        FloatParser parser = floatColumn.getColumnParser();
        String floatValueOne = "2,002.50";
        assertEquals(2002.50f, parser.parse(floatValueOne));
        String floatValueTwo = "20,002.50";
        assertEquals(20002.50f, parser.parse(floatValueTwo));
    }
}