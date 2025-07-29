package io.github.dug22.carpentry.column;

import io.github.dug22.carpentry.column.impl.DoubleColumn;
import io.github.dug22.carpentry.column.impl.IntegerColumn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ColumnFunctionTest {

    @Test
    public void columnFunctionTest() {
        ColumnFunction<Double, Double> doubleColumnFunction = i -> i * 2;
        DoubleColumn doubleColumn = DoubleColumn.create("Doubles", new Double[]{10D, 10D, 15D}).apply(doubleColumnFunction);
        Double[] expectedDoubleValues= new Double[]{20D, 20D, 30D};
        assertArrayEquals(expectedDoubleValues, doubleColumn.getValues());
        ColumnFunction<Integer, Integer> integerColumnFunction = i -> i * 2;
        IntegerColumn integerColumn = IntegerColumn.create("Integers", new Integer[]{10, 10, 15}).apply(integerColumnFunction);
        Integer[] expectedIntegerValues = new Integer[]{20, 20, 30};
        assertArrayEquals(expectedIntegerValues, integerColumn.getValues());
    }
}
