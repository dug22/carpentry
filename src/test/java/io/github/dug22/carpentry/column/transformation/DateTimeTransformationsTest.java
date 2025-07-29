package io.github.dug22.carpentry.column.transformation;

import io.github.dug22.carpentry.column.impl.DateTimeColumn;
import io.github.dug22.carpentry.column.impl.LongColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class DateTimeTransformationsTest {

    private DateTimeColumn primaryDateTimeColumn;
    private DateTimeColumn secondaryDateTimeColumn;

    @BeforeEach
    public void setup(){
        primaryDateTimeColumn = DateTimeColumn.create(
                "Primary Date Times", new String[]{
                        "05/05/2022 12:30:33",
                        "05/05/2022 12:31:33",
                        "05/05/2022 12:32:33"
                }
        );

        secondaryDateTimeColumn = DateTimeColumn.create(
                "Secondary Date Times", new String[]{
                        "05/06/2023 12:30:33",
                        "05/06/2023 12:31:33",
                        "05/06/2023 12:32:33"
                }
        );
    }

    @Test
    public void plusAndMinusTest(){
        DateTimeColumn resultingDateTimeColumn = primaryDateTimeColumn.plusHours(1);
        LocalDateTime[] expectedValuesOne = new LocalDateTime[]{
                LocalDateTime.of(2022, 5, 5, 13, 30, 33),
                LocalDateTime.of(2022,5,5,13, 31, 33),
                LocalDateTime.of(2022, 5, 5, 13, 32, 33)
        };
        assertArrayEquals(expectedValuesOne, resultingDateTimeColumn.getValues());

        resultingDateTimeColumn = resultingDateTimeColumn.minusHours(1);
        LocalDateTime[] expectedValuesTwo = new LocalDateTime[]{
                LocalDateTime.of(2022, 5, 5, 12, 30, 33),
                LocalDateTime.of(2022,5,5,12, 31, 33),
                LocalDateTime.of(2022, 5, 5, 12, 32, 33)
        };
        assertArrayEquals(expectedValuesTwo, resultingDateTimeColumn.getValues());
    }

    @Test
    public void secondsBetweenTest(){
        LongColumn secondsBetween = primaryDateTimeColumn.secondsBetween(secondaryDateTimeColumn);
        assertArrayEquals(new Long[]{31622400L, 31622400L, 31622400L}, secondsBetween.getValues());
    }

    @Test
    public void minuteBetweenTest() {
        LongColumn minutesBetween = primaryDateTimeColumn.minutesBetween(secondaryDateTimeColumn);
        assertArrayEquals(new Long[]{527040L, 527040L, 527040L}, minutesBetween.getValues());
    }


    @Test
    public void hoursBetweenTest() {
        LongColumn hoursBetween = primaryDateTimeColumn.hoursBetween(secondaryDateTimeColumn);
        assertArrayEquals(new Long[]{8784L, 8784L, 8784L}, hoursBetween.getValues());
    }

    @Test
    public void daysBetweenTest() {
        LongColumn daysBetween = primaryDateTimeColumn.daysBetween(secondaryDateTimeColumn);
        assertArrayEquals(new Long[]{366L, 366L, 366L}, daysBetween.getValues());
    }

    @Test
    public void weeksBetweenTest() {
        LongColumn weeksBetween = primaryDateTimeColumn.weeksBetween(secondaryDateTimeColumn);
        assertArrayEquals(new Long[]{52L, 52L, 52L}, weeksBetween.getValues());
    }

    @Test
    public void monthsBetweenTest() {
        LongColumn monthsBetween = primaryDateTimeColumn.monthsBetween(secondaryDateTimeColumn);
        assertArrayEquals(new Long[]{12L, 12L, 12L}, monthsBetween.getValues());
    }

    @Test
    public void yearBetweenTest() {
        LongColumn yearsBetween = primaryDateTimeColumn.yearsBetween(secondaryDateTimeColumn);
        assertArrayEquals(new Long[]{1L, 1L, 1L}, yearsBetween.getValues());
    }
}
