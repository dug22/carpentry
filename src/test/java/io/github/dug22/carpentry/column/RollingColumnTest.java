package io.github.dug22.carpentry.column;

import io.github.dug22.carpentry.aggregation.AggregationType;
import io.github.dug22.carpentry.column.impl.BooleanColumn;
import io.github.dug22.carpentry.column.impl.IntegerColumn;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RollingColumnTest {


    @Test
    public void rollingTest() {
        BooleanColumn booleanColumn = BooleanColumn.create("Booleans", new Boolean[] {true, false, true, false, true});
        IntegerColumn result = (IntegerColumn) booleanColumn.rolling(2).calc(AggregationType.TRUE_COUNT);
        result.show();
        assertEquals(Arrays.asList(-2147483648, 1, 1, 1, 1), Arrays.stream(result.getValues()).toList());
    }
}