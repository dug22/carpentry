/*
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *
 */

package io.github.dug22.carpentry.column.transformations;

import io.github.dug22.carpentry.column.impl.DateColumn;
import io.github.dug22.carpentry.column.impl.LongColumn;
import io.github.dug22.carpentry.utils.Nulls;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public interface DateTransformations extends Transformation {

    /**
     * Returns the array of LocalDate values for the column.
     * @return an array of LocalDate
     */
    LocalDate[] getValues();

    /**
     * Adds days to each date value in the column.
     * @param value the number of days to add
     * @return a new DateColumn with updated values
     */
    default DateColumn plusDays(long value) {
        return plus(value, ChronoUnit.DAYS);
    }

    /**
     * Adds weeks to each date value in the column.
     * @param value the number of weeks to add
     * @return a new DateColumn with updated values
     */
    default DateColumn plusWeeks(long value) {
        return plus(value, ChronoUnit.WEEKS);
    }

    /**
     * Adds months to each date value in the column.
     * @param value the number of months to add
     * @return a new DateColumn with updated values
     */
    default DateColumn plusMonths(long value) {
        return plus(value, ChronoUnit.MONTHS);
    }

    /**
     * Adds years to each date value in the column.
     * @param value the number of years to add
     * @return a new DateColumn with updated values
     */
    default DateColumn plusYears(long value) {
        return plus(value, ChronoUnit.YEARS);
    }

    /**
     * Subtracts days from each date value in the column.
     * @param value the number of days to subtract
     * @return a new DateColumn with updated values
     */
    default DateColumn minusDays(long value) {
        return minus(value, ChronoUnit.DAYS);
    }

    /**
     * Subtracts weeks from each date value in the column.
     * @param value the number of weeks to subtract
     * @return a new DateColumn with updated values
     */
    default DateColumn minusWeeks(long value) {
        return minus(value, ChronoUnit.WEEKS);
    }

    /**
     * Subtracts months from each date value in the column.
     * @param value the number of months to subtract
     * @return a new DateColumn with updated values
     */
    default DateColumn minusMonths(long value) {
        return minus(value, ChronoUnit.MONTHS);
    }

    /**
     * Subtracts years from each date value in the column.
     * @param value the number of years to subtract
     * @return a new DateColumn with updated values
     */
    default DateColumn minusYears(long value) {
        return minus(value, ChronoUnit.YEARS);
    }

    /**
     * Calculates the number of days between this column and another DateColumn.
     * @param dateColumn the column to compare against
     * @return a LongColumn with the number of days between corresponding values
     */
    default LongColumn daysBetween(DateColumn dateColumn) {
        return between(dateColumn, ChronoUnit.DAYS, "_days_between");
    }

    /**
     * Calculates the number of weeks between this column and another DateColumn.
     * @param dateColumn the column to compare against
     * @return a LongColumn with the number of weeks between corresponding values
     */
    default LongColumn weeksBetween(DateColumn dateColumn) {
        return between(dateColumn, ChronoUnit.WEEKS, "_weeks_between");
    }

    /**
     * Calculates the number of months between this column and another DateColumn.
     * @param dateColumn the column to compare against
     * @return a LongColumn with the number of months between corresponding values
     */
    default LongColumn monthsBetween(DateColumn dateColumn) {
        return between(dateColumn, ChronoUnit.MONTHS, "_months_between");
    }

    /**
     * Calculates the number of years between this column and another DateColumn.
     * @param dateColumn the column to compare against
     * @return a LongColumn with the number of years between corresponding values
     */
    default LongColumn yearsBetween(DateColumn dateColumn) {
        return between(dateColumn, ChronoUnit.YEARS, "_years_between");
    }

    /**
     * Internal utility to apply date addition.
     * @param value the amount to add
     * @param unit the unit of time
     * @return a new DateColumn with updated values
     */
    private DateColumn plus(long value, ChronoUnit unit) {
        return applyDateAdditionSubtractionOperation(value, unit, true);
    }

    /**
     * Internal utility to apply date subtraction.
     * @param value the amount to subtract
     * @param unit the unit of time
     * @return a new DateColumn with updated values
     */
    private DateColumn minus(long value, ChronoUnit unit) {
        return applyDateAdditionSubtractionOperation(value, unit, false);
    }

    /**
     * Applies a plus or minus operation to each date value based on the given unit and direction.
     * @param value the amount to add or subtract
     * @param unit the ChronoUnit to apply
     * @param isAddition true if addition, false if subtraction
     * @return a new DateColumn with the result
     */
    private DateColumn applyDateAdditionSubtractionOperation(long value, ChronoUnit unit, boolean isAddition) {
        LocalDate[] values = getValues();
        LocalDate[] results = new LocalDate[size()];
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                results[index] = Nulls.getDefaultNullValue(LocalDate.class);
            } else {
                try {
                    LocalDate currentDateValue = values[index];
                    results[index] = isAddition
                            ? currentDateValue.plus(value, unit)
                            : currentDateValue.minus(value, unit);
                } catch (Exception e) {
                    results[index] = Nulls.getDefaultNullValue(LocalDate.class);
                }
            }
        }

        return DateColumn.create(name() + (isAddition ? "_plus_" : "_minus_") + value + "_" + unit.toString().toLowerCase(), results);
    }

    /**
     * Calculates the time difference between two DateColumns using the specified unit.
     * @param dateColumn the column to compare against
     * @param unit the time unit to use for the difference
     * @param label label used for naming the resulting column
     * @return a LongColumn with the time differences
     */
    private LongColumn between(DateColumn dateColumn, ChronoUnit unit, String label) {
        if (dateColumn.size() != size())
            throw new IllegalArgumentException("Both columns must have the same length");

        LocalDate[] values = getValues();
        Long[] differences = new Long[size()];
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                differences[index] = Nulls.getDefaultNullValue(Long.class);
            } else {
                try {
                    LocalDate fromDateValue = values[index];
                    LocalDate toDateValue = dateColumn.get(index);
                    differences[index] = unit.between(fromDateValue, toDateValue);
                } catch (Exception e) {
                    differences[index] = Nulls.getDefaultNullValue(Long.class);
                }
            }
        }

        return LongColumn.create(name() + label, differences);
    }
}
