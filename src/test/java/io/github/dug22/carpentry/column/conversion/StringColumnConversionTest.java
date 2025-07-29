package io.github.dug22.carpentry.column.conversion;

import io.github.dug22.carpentry.column.format.temporal.DateColumnFormatter;
import io.github.dug22.carpentry.column.format.temporal.DateTimeColumnFormatter;
import io.github.dug22.carpentry.column.impl.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class StringColumnConversionTest {

    @Test
    public void asBooleanColumnTest(){
        StringColumn stringColumn = StringColumn.create("Passed", new String[]{"yes", "no", "yes"});
        BooleanColumn booleanColumn = stringColumn.asBooleanColumn();
        assertAll(
                () -> assertEquals(3, booleanColumn.size()),
                () -> assertArrayEquals(new Boolean[]{true, false, true}, booleanColumn.getValues())
        );
    }

    @Test
    public void asDoubleColumn(){
        StringColumn stringColumn = StringColumn.create("Scores", new String[]{"90.4", "90.3", "89.2"});
        DoubleColumn doubleColumn = stringColumn.asDoubleColumn();
        assertAll(
                () -> assertEquals(3, doubleColumn.size()),
                () -> assertArrayEquals(new Double[]{90.4, 90.3, 89.2}, doubleColumn.getValues())
        );
    }

    @Test
    public void asIntegerColumn(){
        StringColumn stringColumn = StringColumn.create("Weight", new String[]{"143", "132", "141"});
        IntegerColumn integerColumn = stringColumn.asIntegerColumn();
        assertAll(
                () -> assertEquals(3, integerColumn.size()),
                () -> assertArrayEquals(new Integer[]{143, 132, 141}, integerColumn.getValues())
        );
    }

    @Test
    public void asFloatColumn(){
        StringColumn stringColumn = StringColumn.create("Scores", new String[]{"90.4", "90.3", "89.2"});
        FloatColumn floatColumn = stringColumn.asFloatColumn();
        assertAll(
                () -> assertEquals(3, floatColumn.size()),
                () -> assertArrayEquals(new Float[]{90.4f, 90.3f, 89.2f}, floatColumn.getValues())
        );
    }

    @Test
    public void asLongColumn(){
        StringColumn stringColumn = StringColumn.create("Weight", new String[]{"143", "132", "141"});
        LongColumn longColumn = stringColumn.asLongColumn();
        assertAll(
                () -> assertEquals(3, longColumn.size()),
                () -> assertArrayEquals(new Long[]{143L, 132L, 141L}, longColumn.getValues())
        );
    }

    @Test
    public void asCharacterColumn(){
        StringColumn stringColumn = StringColumn.create("Letters", new String[]{"a", "b", "c"});
        CharacterColumn characterColumn = stringColumn.asCharacterColumn();
        assertAll(
                () -> assertEquals(3, characterColumn.size()),
                () -> assertArrayEquals(new Character[]{'a', 'b', 'c'}, characterColumn.getValues())
        );
    }

    @Test
    public void asDateColumn(){
        StringColumn stringColumn = StringColumn.create("Dates", new String[]{"05/05/2025", "05/06/2025"});
        DateColumn dateColumn = stringColumn.asDateColumn().setOutputFormatter(DateColumnFormatter.getDefault().withPattern("MM-dd-yyyy"));
        assertAll(
                () -> assertEquals(2, dateColumn.size()),
                () -> {
                    LocalDate[] expectedValues = new LocalDate[]{
                            LocalDate.of(2025, 5, 5),
                            LocalDate.of(2025, 5, 6)
                    };
                    assertArrayEquals(expectedValues, dateColumn.getValues());
                }
        );
    }

    @Test
    public void asDateTimeColumn(){
        StringColumn stringColumn = StringColumn.create("Date Times", new String[]{"05/05/2025 12:05:40", "05/06/2025 12:05:30"});
        DateTimeColumn dateTimeColumn = stringColumn.asDateTimeColumn().setOutputFormatter(DateTimeColumnFormatter.getDefault().withPattern("MM-dd-yyyy HH:mm:ss"));
        assertAll(
                () -> assertEquals(2, dateTimeColumn.size()),
                () -> {
                    LocalDateTime[] expectedValues = new LocalDateTime[]{
                            LocalDateTime.of(2025, 5, 5, 12, 5,40),
                            LocalDateTime.of(2025, 5, 6, 12, 5,30),
                    };
                    assertArrayEquals(expectedValues, dateTimeColumn.getValues());
                }
        );
    }
}
