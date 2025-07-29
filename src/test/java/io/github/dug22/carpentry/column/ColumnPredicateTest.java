package io.github.dug22.carpentry.column;

import io.github.dug22.carpentry.column.impl.DoubleColumn;
import io.github.dug22.carpentry.column.impl.IntegerColumn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ColumnPredicateTest {

    @Test
    public void columnPredicateTest(){
        ColumnPredicate<Double> doubleColumnPredicate = i -> i > 5;
        DoubleColumn doubleColumn = DoubleColumn.create("Doubles", new Double[]{1D, 2D, 3D, 4D, 5D, 6D, 7D}).filter(doubleColumnPredicate);
        Double[] expectedDoubleValues= new Double[]{6D, 7D};
        assertArrayEquals(expectedDoubleValues, doubleColumn.getValues());

        ColumnPredicate<Integer> integerColumnPredicate = i -> i > 5;
        IntegerColumn integerColumn = IntegerColumn.create("Integers", new Integer[]{1, 2, 3, 4, 5, 6, 7}).filter(integerColumnPredicate);
        Integer[] expectedIntegerValues = new Integer[]{6, 7};
        assertArrayEquals(expectedIntegerValues, integerColumn.getValues());
    }
}
