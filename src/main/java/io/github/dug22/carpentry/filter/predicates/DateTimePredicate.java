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

package io.github.dug22.carpentry.filter.predicates;

import io.github.dug22.carpentry.column.ColumnTypes;
import io.github.dug22.carpentry.column.format.temporal.DateTimeColumnFormatter;
import io.github.dug22.carpentry.column.parser.DateTimeParser;
import io.github.dug22.carpentry.filter.FilterPredicate;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.Temporal;

public class DateTimePredicate {

    private final String columnName;
    private final static DateTimeParser dateTimeParser = ColumnTypes.DATE_TIME_COLUMN_TYPE.getParser();
    private final DateTimeColumnFormatter formatter;

    public DateTimePredicate(String columnName, DateTimeColumnFormatter formatter) {
        this.columnName = columnName;
        this.formatter = formatter != null ? formatter : DateTimeColumnFormatter.getDefault();
    }

    public FilterPredicate isInMonth(Month month) {
        return row -> {
            Object value = row.get(columnName);
            if (value == null) {
                return false;
            }
            LocalDateTime dateTimeValue = getLocalDate(value);
            return dateTimeValue != null && dateTimeValue.getMonth() == month;
        };
    }

    public FilterPredicate isBefore(Temporal dateTime) {
        return row -> {
            Object value = row.get(columnName);
            if (value == null || !(dateTime instanceof LocalDateTime)) {
                return false;
            }
            LocalDateTime dateTimeValue = getLocalDate(value);
            return dateTimeValue != null && dateTimeValue.isBefore((LocalDateTime) dateTime);
        };
    }

    public FilterPredicate isAfter(Temporal dateTime) {
        return row -> {
            Object value = row.get(columnName);
            if (value == null || !(dateTime instanceof LocalDateTime)) {
                return false;
            }
            LocalDateTime dateTimeValue = getLocalDate(value);
            return dateTimeValue != null && dateTimeValue.isAfter((LocalDateTime) dateTime);
        };
    }

    public FilterPredicate isBetween(Temporal startDateTime, Temporal endDateTime) {
        return row -> {
            Object value = row.get(columnName);
            if (value == null || !(startDateTime instanceof LocalDateTime) || !(endDateTime instanceof LocalDateTime)) {
                return false;
            }
            LocalDateTime dateTimeValue = getLocalDate(value);
            return dateTimeValue != null && !dateTimeValue.isBefore((LocalDateTime) startDateTime) && !dateTimeValue.isAfter((LocalDateTime) endDateTime);
        };
    }

    public FilterPredicate isOnDay(DayOfWeek dayOfWeek) {
        return row -> {
            Object value = row.get(columnName);
            if (value == null) {
                return false;
            }

            LocalDateTime dateTimeValue = getLocalDate(value);
            return dateTimeValue != null && dateTimeValue.getDayOfWeek() == dayOfWeek;
        };
    }

    public FilterPredicate isOnWeekday() {
        return row -> {
            Object value = row.get(columnName);
            if (value == null) {
                return false;
            }

            LocalDateTime dateTimeValue = getLocalDate(value);
            assert dateTimeValue != null;
            DayOfWeek dayOfWeek = dateTimeValue.getDayOfWeek();
            return dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.TUESDAY || dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.THURSDAY || dayOfWeek == DayOfWeek.FRIDAY;
        };
    }

    public FilterPredicate isOnWeekend() {
        return row -> {
            Object value = row.get(columnName);
            if (value == null) {
                return false;
            }

            LocalDateTime dateTimeValue = getLocalDate(value);
            assert dateTimeValue != null;
            return dateTimeValue.getDayOfWeek() == DayOfWeek.SATURDAY || dateTimeValue.getDayOfWeek() == DayOfWeek.SUNDAY;
        };
    }

    public FilterPredicate isInYear(int year) {
        return row -> {
            Object value = row.get(columnName);
            if (value == null) {
                return false;
            }

            LocalDateTime dateTimeValue = getLocalDate(value);
            return dateTimeValue != null && dateTimeValue.getYear() == year;
        };
    }

    private LocalDateTime getLocalDate(Object value) {
        if (value instanceof LocalDateTime localDateTime) {
            return localDateTime;
        } else if (value instanceof String stringValue) {
            return dateTimeParser.parse(stringValue);
        }
        return null;
    }

    public DateTimeColumnFormatter getFormatter() {
        return formatter;
    }

    public DateTimeParser getParser() {
        return dateTimeParser;
    }
}
