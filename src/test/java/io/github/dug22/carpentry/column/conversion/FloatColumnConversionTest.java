package io.github.dug22.carpentry.column.conversion;

import io.github.dug22.carpentry.column.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class FloatColumnConversionTest {

    private FloatColumn floatColumn;

    @BeforeEach
    public void setup() {
        floatColumn = FloatColumn.create("Floats", new Float[]{1F, 2F, 3F, 4F});
    }

    @Test
    public void asByteColumnTest() {
        ByteColumn byteColumn = floatColumn.asByteColumn();
        assertAll(
                () -> assertEquals(4, byteColumn.size()),
                () -> assertArrayEquals(new Byte[]{1, 2, 3, 4}, byteColumn.getValues())
        );
    }

    @Test
    public void asDoubleColumnTest() {
        DoubleColumn doubleColumn = floatColumn.asDoubleColumn();
        assertAll(
                () -> assertEquals(4, doubleColumn.size()),
                () -> assertArrayEquals(new Double[]{1D, 2D, 3D, 4D}, doubleColumn.getValues())
        );
    }

    @Test
    public void asIntegerColumnTest(){
        IntegerColumn integerColumn = floatColumn.asIntegerColumn();
        assertAll(
                () -> assertEquals(4, integerColumn.size()),
                () -> assertArrayEquals(new Integer[]{1, 2, 3, 4}, integerColumn.getValues())
        );
    }

    @Test
    public void asLongColumnTest(){
        LongColumn longColumn = floatColumn.asLongColumn();
        assertAll(
                () -> assertEquals(4, longColumn.size()),
                () -> assertArrayEquals(new Long[]{1L, 2L, 3L, 4L}, longColumn.getValues())
        );
    }

    @Test
    public void asShortColumnTest(){
        ShortColumn shortColumn = floatColumn.asShortColumn();
        assertAll(
                () -> assertEquals(4, shortColumn.size()),
                () -> assertArrayEquals(new Short[]{1, 2, 3, 4}, shortColumn.getValues())
        );
    }

    @Test
    public void asStringColumn(){
        StringColumn stringColumn = floatColumn.asStringColumn();
        assertAll(
                () -> assertEquals(4, stringColumn.size()),
                () -> assertArrayEquals(new String[]{"1.0", "2.0", "3.0", "4.0"}, stringColumn.getValues())
        );
    }
}
