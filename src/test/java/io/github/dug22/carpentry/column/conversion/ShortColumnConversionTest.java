package io.github.dug22.carpentry.column.conversion;

import io.github.dug22.carpentry.column.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ShortColumnConversionTest {

    private ShortColumn shortColumn;

    @BeforeEach
    public void setup() {
        shortColumn = ShortColumn.create("Shorts", new Short[]{1, 2, 3, 4});
    }


    @Test
    public void asByteColumnTest() {
        ByteColumn byteColumn = shortColumn.asByteColumn();
        assertAll(
                () -> assertEquals(4, byteColumn.size()),
                () -> assertArrayEquals(new Byte[]{1, 2, 3, 4}, byteColumn.getValues())
        );
    }


    @Test
    public void asDoubleColumnTest() {
        DoubleColumn doubleColumn = shortColumn.asDoubleColumn();
        assertAll(
                () -> assertEquals(4, doubleColumn.size()),
                () -> assertArrayEquals(new Double[]{1D, 2D, 3D, 4D}, doubleColumn.getValues())
        );
    }

    @Test
    public void asFloatColumnTest() {
        FloatColumn floatColumn = shortColumn.asFloatColumn();
        assertAll(
                () -> assertEquals(4, floatColumn.size()),
                () -> assertArrayEquals(new Float[]{1F, 2F, 3F, 4F}, floatColumn.getValues())
        );
    }

    @Test
    public void asIntegerColumnTest() {
        IntegerColumn integerColumn = shortColumn.asIntegerColumn();
        assertAll(
                () -> assertEquals(4, integerColumn.size()),
                () -> assertArrayEquals(new Integer[]{1, 2, 3, 4}, integerColumn.getValues())
        );
    }

    @Test
    public void asLongColumnTest() {
        LongColumn longColumn = shortColumn.asLongColumn();
        assertAll(
                () -> assertEquals(4, longColumn.size()),
                () -> assertArrayEquals(new Long[]{1L, 2L, 3L, 4L}, longColumn.getValues())
        );
    }

    @Test
    public void asStringColumn() {
        StringColumn stringColumn = shortColumn.asStringColumn();
        assertAll(
                () -> assertEquals(4, stringColumn.size()),
                () -> assertArrayEquals(new String[]{"1", "2", "3", "4"}, stringColumn.getValues())
        );
    }
}
