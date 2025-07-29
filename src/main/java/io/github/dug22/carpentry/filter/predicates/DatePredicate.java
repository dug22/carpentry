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
import io.github.dug22.carpentry.column.format.temporal.DateColumnFormatter;
import io.github.dug22.carpentry.column.parser.DateParser;
import io.github.dug22.carpentry.filter.FilterPredicate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.Temporal;

public class DatePredicate {

    private final String columnName;
    private final static DateParser dateParser = ColumnTypes.DATE_COLUMN_TYPE.getParser();
    private final DateColumnFormatter formatter;

    public DatePredicate(String columnName, DateColumnFormatter formatter) {
        this.columnName = columnName;
        this.formatter = formatter != null ? formatter : DateColumnFormatter.getDefault();
    }

    public FilterPredicate isInMonth(Month month) {
        return row -> {
            Object value = row.get(columnName);
            if (value == null) {
                return false;
            }
            LocalDate date = getLocalDate(value);
            return date != null && date.getMonth() == month;
        };
    }

    public FilterPredicate isBefore(Temporal dateTime) {
        return row -> {
            Object value = row.get(columnName);
            if (value == null || !(dateTime instanceof LocalDate)) {
                return false;
            }
            LocalDate date = getLocalDate(value);
            return date != null && date.isBefore((LocalDate) dateTime);
        };
    }

    public FilterPredicate isAfter(Temporal dateTime) {
        return row -> {
            Object value = row.get(columnName);
            if (value == null || !(dateTime instanceof LocalDate)) {
                return false;
            }
            LocalDate date = getLocalDate(value);
            return date != null && date.isAfter((LocalDate) dateTime);
        };
    }

    public FilterPredicate isBetween(Temporal startDateTime, Temporal endDateTime) {
        return row -> {
            Object value = row.get(columnName);
            if (value == null || !(startDateTime instanceof LocalDate) || !(endDateTime instanceof LocalDate)) {
                return false;
            }
            LocalDate date = getLocalDate(value);
            return date != null && !date.isBefore((LocalDate) startDateTime) && !date.isAfter((LocalDate) endDateTime);
        };
    }

    public FilterPredicate isOnDay(DayOfWeek dayOfWeek) {
        return row -> {
            Object value = row.get(columnName);
            if (value == null) {
                return false;
            }

            LocalDate date = getLocalDate(value);
            return date != null && date.getDayOfWeek() == dayOfWeek;
        };
    }

    public FilterPredicate isOnWeekday() {
        return row -> {
            Object value = row.get(columnName);
            if (value == null) {
                return false;
            }

            LocalDate date = getLocalDate(value);
            assert date != null;
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            return dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.TUESDAY || dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.THURSDAY || dayOfWeek == DayOfWeek.FRIDAY;
        };
    }

    public FilterPredicate isOnWeekend() {
        return row -> {
            Object value = row.get(columnName);
            if (value == null) {
                return false;
            }

            LocalDate date = getLocalDate(value);
            assert date != null;
            return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        };
    }

    public FilterPredicate isInYear(int year) {
        return row -> {
            Object value = row.get(columnName);
            if (value == null) {
                return false;
            }

            LocalDate date = getLocalDate(value);
            return date != null && date.getYear() == year;
        };
    }

    private LocalDate getLocalDate(Object value) {
        if (value instanceof LocalDate localDate) {
            return localDate;
        } else if (value instanceof String stringValue) {
            return dateParser.parse(stringValue);
        }
        return null;
    }

    public DateColumnFormatter getFormatter() {
        return formatter;
    }

    public DateParser getParser() {
        return dateParser;
    }
}