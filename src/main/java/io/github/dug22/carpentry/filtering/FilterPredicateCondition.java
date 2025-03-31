/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.github.dug22.carpentry.filtering;

import io.github.dug22.carpentry.filtering.predicates.*;

import java.time.Month;
import java.time.temporal.Temporal;
import java.util.Collection;

public class FilterPredicateCondition {

    private final String columnName;

    public FilterPredicateCondition(String columnName) {
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

    public final FilterPredicate and(FilterPredicate... predicates) {
        return LogicalPredicate.and(predicates);
    }

    public final FilterPredicate or(FilterPredicate... predicates) {
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
        return new DatePredicate(columnName).isInMonth(month);
    }

    public FilterPredicate isBefore(Temporal dateTime) {
        return new DatePredicate(columnName).isBefore(dateTime);
    }

    public FilterPredicate isAfter(Temporal dateTime) {
        return new DatePredicate(columnName).isAfter(dateTime);
    }

    public FilterPredicate isBetween(Temporal startDateTime, Temporal endDateTime) {
        return new DatePredicate(columnName).isBetween(startDateTime, endDateTime);
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
}
