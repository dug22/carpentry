package io.github.dug22.carpentry.column.impl;

import io.github.dug22.carpentry.column.format.temporal.DateColumnFormatter;
import io.github.dug22.carpentry.column.parser.DateParser;
import io.github.dug22.carpentry.utils.Nulls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DateColumnTest {

    private DateColumn dateColumn;

    @BeforeEach
    public void setup() {
        dateColumn = DateColumn.create(
                "Dates",
                new String[]{"05/05/2022", "05/05/2022", "05/05/2023", "05/05/2024"} );
    }

    @Test
    public void appendTest() {
        dateColumn.append(LocalDate.of(2022, 12, 20));
        dateColumn.append(LocalDate.of(2023, 12, 20));
        dateColumn.appendNull();
        LocalDate[] expectedValues = new LocalDate[]{
                LocalDate.of(2022, 5, 5),
                LocalDate.of(2022, 5, 5),
                LocalDate.of(2023, 5, 5),
                LocalDate.of(2024, 5, 5),
                LocalDate.of(2022, 12, 20),
                LocalDate.of(2023, 12, 20),
                Nulls.getDefaultNullValue(LocalDate.class) //DEFAULT NULL
        };
        assertAll(
                () -> assertEquals(7, dateColumn.size()),
                () -> assertArrayEquals(expectedValues, dateColumn.getValues())
        );
    }

    @Test
    public void copyTest() {
        DateColumn copy = dateColumn.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void emptyCopyTest() {
        DateColumn emptyCopy = dateColumn.emptyCopy();
        assertEquals(0, emptyCopy.size());
    }

    @Test
    public void uniqueTest() {
        DateColumn unique = dateColumn.unique();
        LocalDate[] expectedValues = new LocalDate[]{
                LocalDate.of(2022, 5, 5),
                LocalDate.of(2023, 5, 5),
                LocalDate.of(2024, 5, 5),
        };
        assertAll(
                () -> assertEquals(3, unique.size()),
                () -> assertArrayEquals(expectedValues, unique.getValues())
        );
    }

    @Test
    public void applyTest() {
        DateColumn apply = dateColumn.apply(date -> date.plusDays(1));
        LocalDate[] expectedValues = new LocalDate[]{
                LocalDate.of(2022, 5, 6),
                LocalDate.of(2022, 5, 6),
                LocalDate.of(2023, 5, 6),
                LocalDate.of(2024, 5, 6),
        };
        assertAll(
                () -> assertEquals(4, apply.size()),
                () -> assertArrayEquals(expectedValues, apply.getValues())
        );
    }

    @Test
    public void filterTest() {
        DateColumn filter = dateColumn.filter(date -> date.getYear() > 2022);
        LocalDate[] expectedValues = new LocalDate[]{
                LocalDate.of(2023, 5, 5),
                LocalDate.of(2024, 5, 5),
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
                    dateColumn.sortAscending();
                    LocalDate[] expectedValues = new LocalDate[]{
                            LocalDate.of(2022, 5, 5),
                            LocalDate.of(2022, 5, 5),
                            LocalDate.of(2023, 5, 5),
                            LocalDate.of(2024, 5, 5)
                    };
                    assertArrayEquals(expectedValues, dateColumn.getValues());
                },
                () -> {
                    dateColumn.sortDescending();
                    LocalDate[] expectedValues = new LocalDate[]{
                            LocalDate.of(2024, 5, 5),
                            LocalDate.of(2023, 5, 5),
                            LocalDate.of(2022, 5, 5),
                            LocalDate.of(2022, 5, 5)
                    };
                    assertArrayEquals(expectedValues, dateColumn.getValues());
                }
        );
    }

    @Test
    public void replaceTest(){
        dateColumn.replace(new LocalDate[]{
                LocalDate.of(2022, 5, 5)
        },
                LocalDate.of(2025, 5, 5));
        LocalDate[] expectedValues = new LocalDate[]{
                LocalDate.of(2025, 5, 5),
                LocalDate.of(2025, 5, 5),
                LocalDate.of(2023, 5, 5),
                LocalDate.of(2024, 5, 5)
        };
        assertArrayEquals(expectedValues, dateColumn.getValues());
    }

    @Test
    public void parserTest() {
        dateColumn.setOutputFormatter(DateColumnFormatter.getDefault().withPattern("MM/dd/yyyy"));
        DateParser dateParser = dateColumn.getColumnParser();
        DateColumnFormatter formatter = dateColumn.getDateColumnFormatter();
        LocalDate parsedDate = dateParser.parse("05/05/2025");
        String formattedDate = formatter.format(parsedDate);
        assertEquals("05/05/2025", formattedDate);
    }
}
