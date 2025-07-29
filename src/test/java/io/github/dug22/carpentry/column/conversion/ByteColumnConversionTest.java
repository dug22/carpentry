package io.github.dug22.carpentry.column.conversion;

import io.github.dug22.carpentry.column.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ByteColumnConversionTest {

    private ByteColumn byteColumn;

    @BeforeEach
    public void setup() {
        byteColumn = ByteColumn.create("Bytes", new Byte[]{1, 2, 3, 4});
    }

    @Test
    public void asDoubleColumnTest() {
        DoubleColumn doubleColumn = byteColumn.asDoubleColumn();
        assertAll(
                () -> assertEquals(4, doubleColumn.size()),
                () -> assertArrayEquals(new Double[]{1D, 2D, 3D, 4D}, doubleColumn.getValues())
        );
    }

    @Test
    public void asFloatColumnTest() {
        FloatColumn floatColumn = byteColumn.asFloatColumn();
        assertAll(
                () -> assertEquals(4, floatColumn.size()),
                () -> assertArrayEquals(new Float[]{1F, 2F, 3F, 4F}, floatColumn.getValues())
        );
    }

    @Test
    public void asIntegerColumnTest() {
        IntegerColumn integerColumn = byteColumn.asIntegerColumn();
        assertAll(
                () -> assertEquals(4, integerColumn.size()),
                () -> assertArrayEquals(new Integer[]{1, 2, 3, 4}, integerColumn.getValues())
        );
    }

    @Test
    public void asLongColumnTest() {
        LongColumn longColumn = byteColumn.asLongColumn();
        assertAll(
                () -> assertEquals(4, longColumn.size()),
                () -> assertArrayEquals(new Long[]{1L, 2L, 3L, 4L}, longColumn.getValues())
        );
    }

    @Test
    public void asShortColumnTest() {
        ShortColumn shortColumn = byteColumn.asShortColumn();
        assertAll(
                () -> assertEquals(4, shortColumn.size()),
                () -> assertArrayEquals(new Short[]{1, 2, 3, 4}, shortColumn.getValues())
        );
    }

    @Test
    public void asStringColumn() {
        StringColumn stringColumn = byteColumn.asStringColumn();
        assertAll(
                () -> assertEquals(4, stringColumn.size()),
                () -> assertArrayEquals(new String[]{"1", "2", "3", "4"}, stringColumn.getValues())
        );
    }
}
