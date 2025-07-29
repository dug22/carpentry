package io.github.dug22.carpentry.column;

import io.github.dug22.carpentry.column.format.temporal.DateColumnFormatter;
import io.github.dug22.carpentry.column.format.temporal.DateTimeColumnFormatter;
import io.github.dug22.carpentry.column.impl.DateColumn;
import io.github.dug22.carpentry.column.impl.DateTimeColumn;
import io.github.dug22.carpentry.column.parser.DateParser;
import io.github.dug22.carpentry.column.parser.DateTimeParser;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColumnUtilsTest {

        @Test
        public void formatTest() {
            DateParser dateParser = new DateParser().withFormat(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            DateColumn dateColumn = DateColumn.create("Date", new String[]{"05/06/2025", "05/07/2025"}, dateParser);
            DateColumnFormatter outputDateFormatter = DateColumnFormatter.getDefault();
            dateColumn.setOutputFormatter(outputDateFormatter);
            assertEquals("2025-05-06", ColumnUtils.format(dateColumn.get(0), dateColumn));
            assertEquals("2025-05-07", ColumnUtils.format(dateColumn.get(1), dateColumn));

            DateTimeParser dateTimeParser = new DateTimeParser().withFormat(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
            DateTimeColumn dateTimeColumn = DateTimeColumn.create("DateTimes", new String[]{"05/06/2025 14:30", "05/07/2025 09:15"}, dateTimeParser);
            DateTimeColumnFormatter outputDateTimeFormatter = new DateTimeColumnFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"), Locale.getDefault(), true);
            dateTimeColumn.setOutputFormatter(outputDateTimeFormatter);
            assertEquals("2025-05-06 14:30", ColumnUtils.format(dateTimeColumn.get(0), dateTimeColumn));
            assertEquals("2025-05-07 09:15", ColumnUtils.format(dateTimeColumn.get(1), dateTimeColumn));
        }
    }
