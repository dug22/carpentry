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

package io.github.dug22.carpentry.filter;

import io.github.dug22.carpentry.DataFrameInterface;
import io.github.dug22.carpentry.column.impl.DateColumn;
import io.github.dug22.carpentry.column.impl.DateTimeColumn;
import io.github.dug22.carpentry.filter.predicates.*;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.Temporal;
import java.util.Collection;

public class FilterPredicateCondition {
    private final DataFrameInterface dataFrame;
    private final String columnName;

    public FilterPredicateCondition(DataFrameInterface dataFrame, String columnName) {
        this.dataFrame = dataFrame;
        this.columnName = columnName;
    }

    public <T extends Comparable<T>> FilterPredicate gt(T value) {
        return new ComparePredicate(columnName).greaterThan(value);
    }

    public <T extends Comparable<T>> FilterPredicate gte(T value) {
        return new ComparePredicate(columnName).greaterThanOrEqual(value);
    }

    public <T extends Comparable<T>> FilterPredicate lt(T value) {
        return new ComparePredicate(columnName).lessThan(value);
    }

    public <T extends Comparable<T>> FilterPredicate lte(T value) {
        return new ComparePredicate(columnName).lessThanOrEqual(value);
    }

    public <T extends Comparable<T>> FilterPredicate eq(T value) {
        return new ComparePredicate(columnName).equal(value);
    }

    public <T extends Comparable<T>> FilterPredicate neq(T value) {
        return new ComparePredicate(columnName).notEqual(value);
    }

    public <T extends Comparable<T>> FilterPredicate between(T min, T max) {
        return new ComparePredicate(columnName).between(min, max);
    }

    public FilterPredicate objEq(Object value) {
        return new EqualizerPredicate(columnName).eq(value);
    }

    public FilterPredicate objNotEq(Object value) {
        return new EqualizerPredicate(columnName).neq(value);
    }

    public FilterPredicate in(Collection<?> values) {
        return new CollectionPredicate(columnName).in(values);
    }

    public FilterPredicate notIn(Collection<?> values) {
        return new CollectionPredicate(columnName).notIn(values);
    }

    public static FilterPredicate and(FilterPredicate... predicates) {
        return LogicalPredicate.and(predicates);
    }

    public static FilterPredicate or(FilterPredicate... predicates) {
        return LogicalPredicate.or(predicates);
    }

    public static FilterPredicate not(FilterPredicate predicate) {
        return LogicalPredicate.not(predicate);
    }

    public static FilterPredicate either(FilterPredicate... predicates) {
        return LogicalPredicate.or(predicates);
    }

    public static FilterPredicate both(FilterPredicate... predicates) {
        return LogicalPredicate.and(predicates);
    }

    public FilterPredicate isInMonth(Month month) {
        if (dataFrame.getColumn(columnName) instanceof DateColumn dateColumn) {
            return new DatePredicate(columnName, dateColumn.getDateColumnFormatter()).isInMonth(month);
        }else if(dataFrame.getColumn(columnName) instanceof DateTimeColumn dateTimeColumn){
            return new DateTimePredicate(columnName, dateTimeColumn.getDateTimeColumnFormatter()).isInMonth(month);
        }else{
            throw new IllegalArgumentException("Column instance must be a DateColumn, or a DateTimeColumn!");
        }
    }

    public FilterPredicate isInJanuary() {
        return isInMonth(Month.JANUARY);
    }

    public FilterPredicate isInFebruary() {
        return isInMonth(Month.FEBRUARY);
    }

    public FilterPredicate isInMarch() {
        return isInMonth(Month.MARCH);
    }

    public FilterPredicate isInApril() {
        return isInMonth(Month.APRIL);
    }

    public FilterPredicate isInJune() {
        return isInMonth(Month.JUNE);
    }

    public FilterPredicate isInJuly() {
        return isInMonth(Month.JULY);
    }

    public FilterPredicate isInAugust() {
        return isInMonth(Month.AUGUST);
    }

    public FilterPredicate isInSeptember() {
        return isInMonth(Month.SEPTEMBER);
    }

    public FilterPredicate isInOctober() {
        return isInMonth(Month.OCTOBER);
    }

    public FilterPredicate isInNovember() {
        return isInMonth(Month.NOVEMBER);
    }

    public FilterPredicate isInDecember() {
        return isInMonth(Month.DECEMBER);
    }

    public FilterPredicate isOnDay(DayOfWeek dayOfWeek) {
        if (dataFrame.getColumn(columnName) instanceof DateColumn dateColumn) {
            return new DatePredicate(columnName,  dateColumn.getDateColumnFormatter()).isOnDay(dayOfWeek);
        }else if (dataFrame.getColumn(columnName) instanceof DateTimeColumn dateTimeColumn) {
            return new DateTimePredicate(columnName,  dateTimeColumn.getDateTimeColumnFormatter()).isOnDay(dayOfWeek);
        }else{
            throw new IllegalArgumentException("Column instance must be a DateColumn, or a DateTimeColumn!");
        }
    }

    public FilterPredicate isOnMonday() {
        return isOnDay(DayOfWeek.MONDAY);
    }

    public FilterPredicate isOnTuesday() {
        return isOnDay(DayOfWeek.TUESDAY);
    }

    public FilterPredicate isOnWednesday() {
        return isOnDay(DayOfWeek.WEDNESDAY);
    }

    public FilterPredicate isOnThursday() {
        return isOnDay(DayOfWeek.THURSDAY);
    }

    public FilterPredicate isOnFriday() {
        return isOnDay(DayOfWeek.FRIDAY);
    }

    public FilterPredicate isOnSaturday() {
        return isOnDay(DayOfWeek.SATURDAY);
    }

    public FilterPredicate isOnSunday() {
        return isOnDay(DayOfWeek.SUNDAY);
    }

    public FilterPredicate isOnWeekday() {
        if (dataFrame.getColumn(columnName) instanceof DateColumn dateColumn) {
            return new DatePredicate(columnName,  dateColumn.getDateColumnFormatter()).isOnWeekday();
        } else if (dataFrame.getColumn(columnName) instanceof DateTimeColumn dateTimeColumn) {
            return new DateTimePredicate(columnName, dateTimeColumn.getDateTimeColumnFormatter()).isOnWeekday();
        } else {
            throw new IllegalArgumentException("Column instance must be a DateColumn or a DateTimeColumn!");
        }
    }

    public FilterPredicate isOnWeekend() {
        if (dataFrame.getColumn(columnName) instanceof DateColumn dateColumn) {
            return new DatePredicate(columnName,  dateColumn.getDateColumnFormatter()).isOnWeekend();
        } else if (dataFrame.getColumn(columnName) instanceof DateTimeColumn dateTimeColumn) {
            return new DateTimePredicate(columnName, dateTimeColumn.getDateTimeColumnFormatter()).isOnWeekend();
        } else {
            throw new IllegalArgumentException("Column instance must be a DateColumn or a DateTimeColumn!");
        }
    }

    public FilterPredicate isInYear(int year) {
        if (dataFrame.getColumn(columnName) instanceof DateColumn dateColumn) {
            return new DatePredicate(columnName, dateColumn.getDateColumnFormatter()).isInYear(year);
        } else if (dataFrame.getColumn(columnName) instanceof DateTimeColumn dateTimeColumn) {
            return new DateTimePredicate(columnName, dateTimeColumn.getDateTimeColumnFormatter()).isInYear(year);
        } else {
            throw new IllegalArgumentException("Column instance must be a DateColumn or a DateTimeColumn!");
        }
    }

    public FilterPredicate isBeforeDate(Temporal date) {
        if (dataFrame.getColumn(columnName) instanceof DateColumn dateColumn) {
            return new DatePredicate(columnName, dateColumn.getDateColumnFormatter()).isBefore(date);
        } else if (dataFrame.getColumn(columnName) instanceof DateTimeColumn dateTimeColumn) {
            return new DateTimePredicate(columnName, dateTimeColumn.getDateTimeColumnFormatter()).isBefore(date);
        } else {
            throw new IllegalArgumentException("Column instance must be a DateColumn or a DateTimeColumn!");
        }
    }

    public FilterPredicate isAfterDate(Temporal date) {
        if (dataFrame.getColumn(columnName) instanceof DateColumn dateColumn) {
            return new DatePredicate(columnName, dateColumn.getDateColumnFormatter()).isAfter(date);
        } else if (dataFrame.getColumn(columnName) instanceof DateTimeColumn dateTimeColumn) {
            return new DateTimePredicate(columnName, dateTimeColumn.getDateTimeColumnFormatter()).isAfter(date);
        } else {
            throw new IllegalArgumentException("Column instance must be a DateColumn or a DateTimeColumn!");
        }
    }

    public FilterPredicate isBetweenDates(Temporal startDate, Temporal endDate) {
        if (dataFrame.getColumn(columnName) instanceof DateColumn dateColumn) {
            return new DatePredicate(columnName, dateColumn.getDateColumnFormatter()).isBetween(startDate, endDate);
        } else if (dataFrame.getColumn(columnName) instanceof DateTimeColumn dateTimeColumn) {
            return new DateTimePredicate(columnName, dateTimeColumn.getDateTimeColumnFormatter()).isBetween(startDate, endDate);
        } else {
            throw new IllegalArgumentException("Column instance must be a DateColumn or a DateTimeColumn!");
        }
    }

    public FilterPredicate startsWith(String prefix) {
        return new StringPredicate(columnName, prefix).startsWith();
    }

    public FilterPredicate endsWith(String suffix) {
        return new StringPredicate(columnName, suffix).endsWith();
    }

    public FilterPredicate contains(String suffix) {
        return new StringPredicate(columnName, suffix).contains();
    }

    public FilterPredicate matches(String regex) {
        return new StringPredicate(columnName, regex).matches();
    }
}