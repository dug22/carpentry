package io.github.dug22.carpentry.column.transformation;

import io.github.dug22.carpentry.column.impl.DateColumn;
import io.github.dug22.carpentry.column.impl.LongColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class DateTransformationsTest {

    private DateColumn primaryDateColumn;
    private DateColumn secondaryDateColumn;

    @BeforeEach
    public void setup() {
        primaryDateColumn = DateColumn.create(
                "Primary Dates", new String[]{
                        "05/05/2022", "05/06/2022", "05/07/2022"});
        secondaryDateColumn = DateColumn.create("Secondary Dates", new String[]{
                "06/20/2023", "06/21/2023", "06/25/2023"
        });
    }

    @Test
    public void plusAndMinusTest(){
        DateColumn resultingDateColumn = primaryDateColumn.plusDays(10);
        LocalDate[] expectedValuesOne = new LocalDate[]{
                LocalDate.of(2022, 5, 15),
                LocalDate.of(2022, 5, 16),
                LocalDate.of(2022, 5, 17)
        };
        assertArrayEquals(expectedValuesOne, resultingDateColumn.getValues());

        resultingDateColumn = resultingDateColumn.minusDays(10);
        LocalDate[] expectedValuesTwo = new LocalDate[]{
                LocalDate.of(2022, 5, 5),
                LocalDate.of(2022, 5, 6),
                LocalDate.of(2022, 5, 7)
        };
        assertArrayEquals(expectedValuesTwo, resultingDateColumn.getValues());
    }

    @Test
    public void daysBetweenTest() {
        LongColumn daysBetween = primaryDateColumn.daysBetween(secondaryDateColumn);
        assertArrayEquals(new Long[]{411L, 411L, 414L}, daysBetween.getValues());
    }

    @Test
    public void weeksBetweenTest() {
        LongColumn weeksBetween = primaryDateColumn.weeksBetween(secondaryDateColumn);
        assertArrayEquals(new Long[]{58L, 58L, 59L}, weeksBetween.getValues());
    }

    @Test
    public void monthsBetweenTest() {
        LongColumn monthsBetween = primaryDateColumn.monthsBetween(secondaryDateColumn);
        assertArrayEquals(new Long[]{13L, 13L, 13L}, monthsBetween.getValues());
    }

    @Test
    public void yearBetweenTest() {
        LongColumn yearsBetween = primaryDateColumn.yearsBetween(secondaryDateColumn);
        assertArrayEquals(new Long[]{1L, 1L, 1L}, yearsBetween.getValues());
    }
}
