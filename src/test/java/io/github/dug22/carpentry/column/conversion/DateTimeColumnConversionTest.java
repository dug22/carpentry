package io.github.dug22.carpentry.column.conversion;

import io.github.dug22.carpentry.column.format.temporal.DateTimeColumnFormatter;
import io.github.dug22.carpentry.column.impl.DateTimeColumn;
import io.github.dug22.carpentry.column.impl.StringColumn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DateTimeColumnConversionTest {

    @Test
    public void asStringColumn() {
        DateTimeColumnFormatter formatter = DateTimeColumnFormatter.getDefault().withPattern("MMM dd, yyyy hh:mm:ss a").withOrdinal(true);
        DateTimeColumn dateTimeColumn = DateTimeColumn.create(
                "Date Times",
                new String[]{"Jan 10, 2010 12:00:01 AM", "Jan 10, 2012 02:00:02 PM"},
                formatter);
        StringColumn stringColumn = dateTimeColumn.asStringColumn();
        String[] expectedValues = new String[]{
                "Jan 10th, 2010 12:00:01 AM",
                "Jan 10th, 2012 02:00:02 PM"
        };
        assertAll(
                () -> assertEquals(2, stringColumn.size()),
                () -> assertArrayEquals(expectedValues, stringColumn.getValues())
        );
        stringColumn.head();
    }
}
