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

import io.github.dug22.carpentry.column.impl.DateTimeColumn;
import io.github.dug22.carpentry.column.impl.LongColumn;
import io.github.dug22.carpentry.utils.Nulls;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public interface DateTimeTransformation extends Transformation {

    /**
     * Returns the array of LocalDateTime values for the column.
     * @return an array of LocalDateTime
     */
    LocalDateTime[] getValues();

    /**
     * Adds seconds to each datetime value.
     * @param value the number of seconds to add
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn plusSeconds(long value) {
        return plus(value, ChronoUnit.SECONDS);
    }

    /**
     * Adds minutes to each datetime value.
     * @param value the number of minutes to add
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn plusMinutes(long value) {
        return plus(value, ChronoUnit.MINUTES);
    }

    /**
     * Adds hours to each datetime value.
     * @param value the number of hours to add
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn plusHours(long value) {
        return plus(value, ChronoUnit.HOURS);
    }

    /**
     * Adds days to each datetime value.
     * @param value the number of days to add
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn plusDays(long value) {
        return plus(value, ChronoUnit.DAYS);
    }

    /**
     * Adds weeks to each datetime value.
     * @param value the number of weeks to add
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn plusWeeks(long value) {
        return plus(value, ChronoUnit.WEEKS);
    }

    /**
     * Adds months to each datetime value.
     * @param value the number of months to add
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn plusMonths(long value) {
        return plus(value, ChronoUnit.MONTHS);
    }

    /**
     * Adds years to each datetime value.
     * @param value the number of years to add
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn plusYears(long value) {
        return plus(value, ChronoUnit.YEARS);
    }

    /**
     * Subtracts seconds from each datetime value.
     * @param value the number of seconds to subtract
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn minusSeconds(long value) {
        return minus(value, ChronoUnit.SECONDS);
    }

    /**
     * Subtracts minutes from each datetime value.
     * @param value the number of minutes to subtract
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn minusMinutes(long value) {
        return minus(value, ChronoUnit.MINUTES);
    }

    /**
     * Subtracts hours from each datetime value.
     * @param value the number of hours to subtract
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn minusHours(long value) {
        return minus(value, ChronoUnit.HOURS);
    }

    /**
     * Subtracts days from each datetime value.
     * @param value the number of days to subtract
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn minusDays(long value) {
        return minus(value, ChronoUnit.DAYS);
    }

    /**
     * Subtracts weeks from each datetime value.
     * @param value the number of weeks to subtract
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn minusWeeks(long value) {
        return minus(value, ChronoUnit.WEEKS);
    }

    /**
     * Subtracts months from each datetime value.
     * @param value the number of months to subtract
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn minusMonths(long value) {
        return minus(value, ChronoUnit.MONTHS);
    }

    /**
     * Subtracts years from each datetime value.
     * @param value the number of years to subtract
     * @return a new DateTimeColumn with updated values
     */
    default DateTimeColumn minusYears(long value) {
        return minus(value, ChronoUnit.YEARS);
    }

    /**
     * Calculates the number of seconds between this column and another DateTimeColumn.
     * @param dateTimeColumn the column to compare against
     * @return a LongColumn with the number of seconds between corresponding values
     */
    default LongColumn secondsBetween(DateTimeColumn dateTimeColumn) {
        return between(dateTimeColumn, ChronoUnit.SECONDS, "_seconds_between");
    }

    /**
     * Calculates the number of minutes between this column and another DateTimeColumn.
     * @param dateTimeColumn the column to compare against
     * @return a LongColumn with the number of minutes between corresponding values
     */
    default LongColumn minutesBetween(DateTimeColumn dateTimeColumn) {
        return between(dateTimeColumn, ChronoUnit.MINUTES, "_minutes_between");
    }

    /**
     * Calculates the number of hours between this column and another DateTimeColumn.
     * @param dateTimeColumn the column to compare against
     * @return a LongColumn with the number of hours between corresponding values
     */
    default LongColumn hoursBetween(DateTimeColumn dateTimeColumn) {
        return between(dateTimeColumn, ChronoUnit.HOURS, "_hours_between");
    }

    /**
     * Calculates the number of days between this column and another DateTimeColumn.
     * @param dateTimeColumn the column to compare against
     * @return a LongColumn with the number of days between corresponding values
     */
    default LongColumn daysBetween(DateTimeColumn dateTimeColumn) {
        return between(dateTimeColumn, ChronoUnit.DAYS, "_days_between");
    }

    /**
     * Calculates the number of weeks between this column and another DateTimeColumn.
     * @param dateTimeColumn the column to compare against
     * @return a LongColumn with the number of weeks between corresponding values
     */
    default LongColumn weeksBetween(DateTimeColumn dateTimeColumn) {
        return between(dateTimeColumn, ChronoUnit.WEEKS, "_weeks_between");
    }

    /**
     * Calculates the number of months between this column and another DateTimeColumn.
     * @param dateTimeColumn the column to compare against
     * @return a LongColumn with the number of months between corresponding values
     */
    default LongColumn monthsBetween(DateTimeColumn dateTimeColumn) {
        return between(dateTimeColumn, ChronoUnit.MONTHS, "_months_between");
    }

    /**
     * Calculates the number of years between this column and another DateTimeColumn.
     * @param dateTimeColumn the column to compare against
     * @return a LongColumn with the number of years between corresponding values
     */
    default LongColumn yearsBetween(DateTimeColumn dateTimeColumn) {
        return between(dateTimeColumn, ChronoUnit.YEARS, "_years_between");
    }

    /**
     * Internal utility to apply date/time addition.
     */
    private DateTimeColumn plus(long value, ChronoUnit unit) {
        return applyDateTimeAdditionSubtractionOperation(value, unit, true);
    }

    /**
     * Internal utility to apply date/time subtraction.
     */
    private DateTimeColumn minus(long value, ChronoUnit unit) {
        return applyDateTimeAdditionSubtractionOperation(value, unit, false);
    }

    /**
     * Applies a plus or minus operation to each datetime value based on the given unit and direction.
     */
    private DateTimeColumn applyDateTimeAdditionSubtractionOperation(long value, ChronoUnit unit, boolean isAddition) {
        LocalDateTime[] values = getValues();
        LocalDateTime[] results = new LocalDateTime[size()];
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                results[index] = Nulls.getDefaultNullValue(LocalDateTime.class);
            } else {
                try {
                    LocalDateTime currentDateTimeValue = values[index];
                    results[index] = isAddition
                            ? currentDateTimeValue.plus(value, unit)
                            : currentDateTimeValue.minus(value, unit);
                } catch (Exception e) {
                    results[index] = Nulls.getDefaultNullValue(LocalDateTime.class);
                }
            }
        }

        return DateTimeColumn.create(name() + (isAddition ? "_plus_" : "_minus_") + value + "_" + unit.toString().toLowerCase(), results);
    }

    /**
     * Calculates the time difference between two DateTimeColumns using the specified unit.
     */
    private LongColumn between(DateTimeColumn dateTimeColumn, ChronoUnit unit, String label) {
        if (dateTimeColumn.size() != size())
            throw new IllegalArgumentException("Both columns must have the same length");

        LocalDateTime[] values = getValues();
        Long[] results = new Long[size()];
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                results[index] = Nulls.getDefaultNullValue(Long.class);
            } else {
                try {
                    LocalDateTime fromDateTimeValue = values[index];
                    LocalDateTime toDateTimeValue = dateTimeColumn.get(index);
                    results[index] = unit.between(fromDateTimeValue, toDateTimeValue);
                } catch (Exception e) {
                    results[index] = Nulls.getDefaultNullValue(Long.class);
                }
            }
        }

        return LongColumn.create(name() + label, results);
    }
}
