package io.github.dug22.carpentry.column.conversion;

import io.github.dug22.carpentry.column.impl.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColumnConversionTest {

    private static final int EXPECTED_SIZE = 4;

    @Test
    public void convertTest() {
        BooleanColumn booleanColumn = BooleanColumn.create("Booleans", new Boolean[]{true, true, true, true});
        assertEquals(EXPECTED_SIZE, booleanColumn.size(), "Boolean column size should be 4");
        DoubleColumn doubleColumn = booleanColumn.asDoubleColumn();
        doubleColumn.setName("Booleans as Doubles");
        assertAll(
                () -> assertEquals(EXPECTED_SIZE, doubleColumn.size()),
                () -> assertArrayEquals(new Double[]{1.0, 1.0, 1.0, 1.0}, doubleColumn.getValues())
        );

        StringColumn stringColumn = StringColumn.create("Sex", new String[]{"Male", "Female", "Male", "Female"});
        assertEquals(EXPECTED_SIZE, stringColumn.size());
        CharacterColumn characterColumn = stringColumn.asCharacterColumn();
        assertAll(
                () -> assertEquals(EXPECTED_SIZE, characterColumn.size()),
                () -> assertArrayEquals(new Character[]{'M', 'F', 'M', 'F'}, characterColumn.getValues())
        );


        IntegerColumn integerColumn = IntegerColumn.create("Integers", new Integer[]{1, 2, 3, 4});
        assertEquals(EXPECTED_SIZE, integerColumn.size(), "Integer column size should be 4");
        FloatColumn floatColumn = integerColumn.asFloatColumn();
        floatColumn.setName("Integers as Floats");
        assertAll(
                () -> assertEquals(EXPECTED_SIZE, floatColumn.size()),
                () -> assertArrayEquals(new Float[]{1.0f, 2.0f, 3.0f, 4.0f}, floatColumn.getValues())
        );

    }
}
