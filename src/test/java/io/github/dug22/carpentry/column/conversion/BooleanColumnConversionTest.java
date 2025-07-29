package io.github.dug22.carpentry.column.conversion;

import io.github.dug22.carpentry.column.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class BooleanColumnConversionTest {

    private BooleanColumn booleanColumn;

    @BeforeEach
    public void setup() {
        booleanColumn = BooleanColumn.create("Booleans", new Boolean[]{true, false, true, false});
    }

    @Test
    public void asCharacterTest(){
        CharacterColumn characterColumn = booleanColumn.asCharacterColumn().uppercase();
        assertAll(
                () -> assertEquals(4, characterColumn.size()),
                () -> assertArrayEquals(new Character[]{'T', 'F', 'T', 'F'}, characterColumn.getValues())
        );
    }

    @Test
    public void asDoubleColumnTest(){
        DoubleColumn doubleColumn = booleanColumn.asDoubleColumn();
        assertAll(
                () -> assertEquals(4, doubleColumn.size()),
                () -> assertArrayEquals(new Double[]{1D, 0D, 1D, 0D}, doubleColumn.getValues())
        );
    }

    @Test
    public void asIntegerColumnTest(){
        IntegerColumn integerColumn = booleanColumn.asIntegerColumn();
        assertAll(
                () -> assertEquals(4, integerColumn.size()),
                () -> assertArrayEquals(new Integer[]{1, 0, 1, 0}, integerColumn.getValues())
        );
    }

    @Test
    public void asStringColumn(){
        StringColumn stringColumn = booleanColumn.asStringColumn().capitalize();
        assertAll(
                () -> assertEquals(4, stringColumn.size()),
                () -> assertArrayEquals(new String[]{"True", "False", "True", "False"}, stringColumn.getValues())
        );
    }

}
