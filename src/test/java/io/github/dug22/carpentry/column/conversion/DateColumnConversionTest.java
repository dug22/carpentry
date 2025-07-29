package io.github.dug22.carpentry.column.conversion;

import io.github.dug22.carpentry.column.format.temporal.DateColumnFormatter;
import io.github.dug22.carpentry.column.impl.DateColumn;
import io.github.dug22.carpentry.column.impl.StringColumn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DateColumnConversionTest {

    @Test
    public void asStringColumnTest() {
        DateColumnFormatter formatter = DateColumnFormatter.getDefault().withPattern("MM/dd/yyyy");
        DateColumn dateColumn = DateColumn
                .create("Dates", new String[]{"05/05/2025", "05/06/2025", "05/07/2025"}, formatter);

        StringColumn stringColumn = dateColumn.asStringColumn();

        assertAll(
                () -> assertEquals(3, stringColumn.size()),
                () -> assertArrayEquals(new String[]{"05/05/2025", "05/06/2025", "05/07/2025"}, stringColumn.getValues())
        );
    }
}