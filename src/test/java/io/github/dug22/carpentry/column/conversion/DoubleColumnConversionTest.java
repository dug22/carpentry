package io.github.dug22.carpentry.column.conversion;

import io.github.dug22.carpentry.column.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleColumnConversionTest {


    private DoubleColumn doubleColumn;

    @BeforeEach
    public void setup() {
        doubleColumn = DoubleColumn.create("Doubles", new Double[]{1D, 2D, 3D, 4D});
    }

    @Test
    public void asByteColumnTest() {
        ByteColumn byteColumn = doubleColumn.asByteColumn();
        assertAll(
                () -> assertEquals(4, byteColumn.size()),
                () -> assertArrayEquals(new Byte[]{1, 2, 3, 4}, byteColumn.getValues())
        );
    }

    @Test
    public void asFloatColumnTest() {
        FloatColumn floatColumn = doubleColumn.asFloatColumn();
        assertAll(
                () -> assertEquals(4, floatColumn.size()),
                () -> assertArrayEquals(new Float[]{1F, 2F, 3F, 4F}, floatColumn.getValues())
        );
    }

    @Test
    public void asIntegerColumnTest(){
        IntegerColumn integerColumn = doubleColumn.asIntegerColumn();
        assertAll(
                () -> assertEquals(4, integerColumn.size()),
                () -> assertArrayEquals(new Integer[]{1, 2, 3, 4}, integerColumn.getValues())
        );
    }

    @Test
    public void asLongColumnTest(){
        LongColumn longColumn = doubleColumn.asLongColumn();
        assertAll(
                () -> assertEquals(4, longColumn.size()),
                () -> assertArrayEquals(new Long[]{1L, 2L, 3L, 4L}, longColumn.getValues())
        );
    }

    @Test
    public void asShortColumnTest(){
        ShortColumn shortColumn = doubleColumn.asShortColumn();
        assertAll(
                () -> assertEquals(4, shortColumn.size()),
                () -> assertArrayEquals(new Short[]{1, 2, 3, 4}, shortColumn.getValues())
        );
    }

    @Test
    public void asStringColumn(){
        StringColumn stringColumn = doubleColumn.asStringColumn();
        assertAll(
                () -> assertEquals(4, stringColumn.size()),
                () -> assertArrayEquals(new String[]{"1.0", "2.0", "3.0", "4.0"}, stringColumn.getValues())
        );
    }
}
