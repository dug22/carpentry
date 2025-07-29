package io.github.dug22.carpentry.column.transformation;

import io.github.dug22.carpentry.column.impl.BooleanColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanTransformationsTest {

    private BooleanColumn booleanColumn;

    @BeforeEach
    public void setup() {
        booleanColumn = BooleanColumn.create("Booleans", new Boolean[]{true, false, true, false});
    }

    @Test
    public void andTest() {
        BooleanColumn otherBooleanColumn = BooleanColumn.create("Booleans", new Boolean[]{false, false, true, false});
        BooleanColumn and = booleanColumn.and(otherBooleanColumn);
        assertAll(
                () -> assertEquals(4, and.size()),
                () -> assertArrayEquals(new Boolean[]{false, false, true, false}, and.getValues())
        );
    }

    @Test
    public void andNotTest(){
        BooleanColumn otherBooleanColumn = BooleanColumn.create("Booleans", new Boolean[]{false, false, true, false});
        BooleanColumn andNot = booleanColumn.andNot(otherBooleanColumn);
        assertAll(
                () -> assertEquals(4, andNot.size()),
                () -> assertArrayEquals(new Boolean[]{true, false, false, false}, andNot.getValues())
        );
    }

    @Test
    public void orTest(){
        BooleanColumn otherBooleanColumn = BooleanColumn.create("Booleans", new Boolean[]{false, false, true, false});
        BooleanColumn or = booleanColumn.or(otherBooleanColumn);
        assertAll(
                () -> assertEquals(4, or.size()),
                () -> assertArrayEquals(new Boolean[]{true, false, true, false}, or.getValues())
        );
    }

    @Test
    public void xorTest(){
        BooleanColumn otherBooleanColumn = BooleanColumn.create("Booleans", new Boolean[]{false, false, true, false});
        BooleanColumn xor = booleanColumn.xor(otherBooleanColumn);
        assertAll(
                () -> assertEquals(4, xor.size()),
                () -> assertArrayEquals(new Boolean[]{true, false, false, false}, xor.getValues())
        );
    }

    @Test
    public void flipTest(){
        BooleanColumn flip = booleanColumn.flip();
        assertAll(
                () -> assertEquals(4, flip.size()),
                () -> assertArrayEquals(new Boolean[]{false, true, false, true}, flip.getValues())
        );
    }
}
