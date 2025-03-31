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

package io.github.dug22.carpentry.columns;

import io.github.dug22.carpentry.column.ColumnFunction;
import io.github.dug22.carpentry.column.ColumnPredicate;
import io.github.dug22.carpentry.column.numbers.LongColumnConversionFunctions;

import java.util.*;

public class LongColumn extends NumericColumn<Long> implements LongColumnConversionFunctions {

    public LongColumn(String columnName) {
        super(columnName, Long.class);
    }

    public LongColumn(String columnName, Long[] values) {
        super(columnName, Long.class, values);
    }

    public static LongColumn create(String columnName) {
        return new LongColumn(columnName);
    }

    public static LongColumn create(String columnName, Long[] values) {
        return new LongColumn(columnName, values);
    }

    @Override
    public LongColumn copyEmpty() {
        return create(getColumnName());
    }

    @Override
    public LongColumn copy() {
        Long[] copyData = Arrays.copyOf(getValues(), size());
        return create(getColumnName(), copyData);
    }

    @Override
    public LongColumn unique() {
        Set<Long> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Long[] uniqueArray = uniqueValues.toArray(new Long[0]);
        return create(getColumnName(), uniqueArray);
    }

    @Override
    public LongColumn filter(ColumnPredicate<Long> condition) {
        List<Long> filteredValues = new ArrayList<>();
        for (Long value : getValues()) {
            if (condition.test(value)) {
                filteredValues.add(value);
            }
        }

        Long[] filteredArray = filteredValues.toArray(new Long[0]);
        return create(getColumnName(), filteredArray);
    }

    @Override
    public LongColumn apply(ColumnFunction<Long, Long> function) {
        Long[] transformedValues = new Long[size()];
        for (int i = 0; i < size(); i++) {
            transformedValues[i] = function.apply(get(i));
        }

        return create(getColumnName(), transformedValues);
    }
}
