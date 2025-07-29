package io.github.dug22.carpentry.column.impl;

import io.github.dug22.carpentry.column.parser.ByteParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ByteColumnTest {

    private ByteColumn byteColumn;

    @BeforeEach
    public void setup() {
        byteColumn = ByteColumn.create("Bytes", new Byte[]{4, 10, 8, 10});
    }

    @Test
    public void appendTest() {
        byteColumn.append((byte) 3);
        byteColumn.appendNull();
        byteColumn.appendAll(new Byte[]{10, 20});
        assertAll(
                () -> assertEquals(8, byteColumn.size()),
                () -> assertArrayEquals(new Byte[]{4, 10, 8, 10, 3, -128, 10, 20}, byteColumn.getValues())
        );
    }

    @Test
    public void copyTest() {
        ByteColumn copy = byteColumn.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void emptyCopyTest() {
        ByteColumn emptyCopy = byteColumn.emptyCopy();
        assertEquals(0, emptyCopy.size());
    }

    @Test
    public void uniqueTest() {
        ByteColumn unique = byteColumn.unique();
        assertAll(
                () -> assertEquals(3, unique.size()),
                () -> assertArrayEquals(new Byte[]{4, 10, 8}, unique.getValues())
        );
    }

    @Test
    public void applyTest() {
        ByteColumn apply = byteColumn.apply(i -> (byte) (i * 2));
        assertAll(
                () -> assertEquals(4, apply.size()),
                () -> assertArrayEquals(new Byte[]{8, 20, 16, 20}, apply.getValues())
        );
    }

    @Test
    public void filterTest() {
        ByteColumn filter = byteColumn.filter(i -> i > 8);
        assertAll(
                () -> assertEquals(2, filter.size()),
                () -> assertArrayEquals(new Byte[]{10, 10}, filter.getValues())
        );
    }

    @Test
    public void sortTest() {
        assertAll(
                () -> {
                    byteColumn.sortAscending();
                    assertArrayEquals(new Byte[]{4, 8, 10, 10}, byteColumn.getValues());
                },

                () -> {
                    byteColumn.sortDescending();
                    assertArrayEquals(new Byte[]{10, 10, 8, 4}, byteColumn.getValues());
                });
    }

    @Test
    public void replaceTest(){
        byteColumn.replace(new Byte[]{10}, (byte) 11);
        assertArrayEquals(new Byte[]{4, 11, 8, 11}, byteColumn.getValues());
    }

    @Test
    public void parserTest(){
        ByteParser parser = byteColumn.getColumnParser();
        String byteValueOne = "0b10110";
        assertEquals((byte) 22, parser.parse(byteValueOne));
        String byteValueTwo = "0x16";
        assertEquals((byte) 22, parser.parse(byteValueTwo));
        String byteValueThree = "22";
        assertEquals((byte) 22, parser.parse(byteValueThree));
    }
}
