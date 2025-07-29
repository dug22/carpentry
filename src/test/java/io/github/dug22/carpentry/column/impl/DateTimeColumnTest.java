package io.github.dug22.carpentry.column.impl;

import io.github.dug22.carpentry.column.format.temporal.DateTimeColumnFormatter;
import io.github.dug22.carpentry.column.parser.DateTimeParser;
import io.github.dug22.carpentry.utils.Nulls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DateTimeColumnTest {

    private DateTimeColumn dateTimeColumn;

    @BeforeEach
    public void setUp() {
        dateTimeColumn = DateTimeColumn.create(
                "Date Times",
                new String[]{"05/05/2022 11:30:05", "05/05/2022 11:30:05", "05/05/2023 05:30:32", "05/05/2024 12:30:05"});
    }

    @Test
    public void appendTest() {
        dateTimeColumn.append(LocalDateTime.of(2023, 12, 15, 1, 20, 30));
        dateTimeColumn.append(LocalDateTime.of(2024, 12, 30, 2, 30, 50));
        dateTimeColumn.appendNull();
        LocalDateTime[] expectedValues = new LocalDateTime[]{
                LocalDateTime.of(2022, 5, 5, 11, 30, 5),
                LocalDateTime.of(2022, 5, 5, 11, 30, 5),
                LocalDateTime.of(2023, 5, 5, 5, 30, 32),
                LocalDateTime.of(2024, 5, 5, 12, 30, 5),
                LocalDateTime.of(2023, 12, 15, 1, 20, 30),
                LocalDateTime.of(2024, 12, 30, 2, 30, 50),
                Nulls.getDefaultNullValue(LocalDateTime.class) //DEFAULT NULL
        };
        assertAll(
                () -> assertEquals(7, dateTimeColumn.size()),
                () -> assertArrayEquals(expectedValues, dateTimeColumn.getValues())
        );
    }

    @Test
    public void copyTest() {
        DateTimeColumn copy = dateTimeColumn.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void emptyCopyTest() {
        DateTimeColumn emptyCopy = dateTimeColumn.emptyCopy();
        assertEquals(0, emptyCopy.size());
    }

    @Test
    public void uniqueTest() {
        DateTimeColumn unique = dateTimeColumn.unique();
        LocalDateTime[] expectedValues = new LocalDateTime[]{
                LocalDateTime.of(2022, 5, 5, 11, 30, 5),
                LocalDateTime.of(2023, 5, 5, 5, 30, 32),
                LocalDateTime.of(2024, 5, 5, 12, 30, 5),
        };
        assertAll(
                () -> assertEquals(3, unique.size()),
                () -> assertArrayEquals(expectedValues, unique.getValues())
        );
    }

    @Test
    public void applyTest() {
        DateTimeColumn apply = dateTimeColumn.apply(date -> date.plusDays(1));
        LocalDateTime[] expectedValues = new LocalDateTime[]{
                LocalDateTime.of(2022, 5, 6, 11, 30, 5),
                LocalDateTime.of(2022, 5, 6, 11, 30, 5),
                LocalDateTime.of(2023, 5, 6, 5, 30, 32),
                LocalDateTime.of(2024, 5, 6, 12, 30, 5),
        };
        assertAll(
                () -> assertEquals(4, apply.size()),
                () -> assertArrayEquals(expectedValues, apply.getValues())
        );
    }

    @Test
    public void filterTest() {
        DateTimeColumn filter = dateTimeColumn.filter(date -> date.getYear() > 2022);
        LocalDateTime[] expectedValues = new LocalDateTime[]{
                LocalDateTime.of(2023, 5, 5, 5, 30, 32),
                LocalDateTime.of(2024, 5, 5, 12, 30, 5),
        };
        assertAll(
                () -> assertEquals(2, filter.size()),
                () -> assertArrayEquals(expectedValues, filter.getValues())
        );
    }

    @Test
    public void sortTest() {
        assertAll(
                () -> {
                    dateTimeColumn.sortAscending();
                    LocalDateTime[] expectedValues = new LocalDateTime[]{
                            LocalDateTime.of(2022, 5, 5, 11, 30, 5),
                            LocalDateTime.of(2022, 5, 5, 11, 30, 5),
                            LocalDateTime.of(2023, 5, 5, 5, 30, 32),
                            LocalDateTime.of(2024, 5, 5, 12, 30, 5),
                    };
                    assertArrayEquals(expectedValues, dateTimeColumn.getValues());
                },
                () -> {
                    dateTimeColumn.sortDescending();
                    LocalDateTime[] expectedValues = new LocalDateTime[]{
                            LocalDateTime.of(2024, 5, 5, 12, 30, 5),
                            LocalDateTime.of(2023, 5, 5, 5, 30, 32),
                            LocalDateTime.of(2022, 5, 5, 11, 30, 5),
                            LocalDateTime.of(2022, 5, 5, 11, 30, 5),
                    };
                    assertArrayEquals(expectedValues, dateTimeColumn.getValues());
                }
        );
    }

    @Test
    public void replaceTest() {
        dateTimeColumn.replace(new LocalDateTime[]{
                        LocalDateTime.of(2022, 5, 5, 11, 30, 5),
                },
                LocalDateTime.of(2025, 5, 5, 11, 30, 6));
        LocalDateTime[] expectedValues = new LocalDateTime[]{
                LocalDateTime.of(2025, 5, 5, 11, 30, 6),
                LocalDateTime.of(2025, 5, 5, 11, 30, 6),
                LocalDateTime.of(2023, 5, 5, 5, 30, 32),
                LocalDateTime.of(2024, 5, 5, 12, 30, 5),
        };
        assertArrayEquals(expectedValues, dateTimeColumn.getValues());
    }

    @Test
    public void parserTest(){
        dateTimeColumn.setOutputFormatter(DateTimeColumnFormatter.getDefault().withPattern("MM/dd/yyyy HH:mm:ss"));
        DateTimeParser dateTimeParser =  dateTimeColumn.getColumnParser();
        DateTimeColumnFormatter formatter = dateTimeColumn.getDateTimeColumnFormatter();
        LocalDateTime parsedDateTime = dateTimeParser.parse("05/05/2025 05:30:12");
        String formattedDateTime = formatter.format(parsedDateTime);
        assertEquals("05/05/2025 05:30:12", formattedDateTime);

    }
}
