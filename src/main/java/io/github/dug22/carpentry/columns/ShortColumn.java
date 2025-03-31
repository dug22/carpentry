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
import io.github.dug22.carpentry.column.numbers.ShortColumnConversionFunctions;

import java.util.*;

public class ShortColumn extends NumericColumn<Short> implements ShortColumnConversionFunctions {

    public ShortColumn(String columnName) {
        super(columnName, Short.class);
    }

    public ShortColumn(String columnName, Short[] values) {
        super(columnName, Short.class, values);
    }

    public static ShortColumn create(String columnName) {
        return new ShortColumn(columnName);
    }

    public static ShortColumn create(String columnName, Short[] values) {
        return new ShortColumn(columnName, values);
    }

    @Override
    public ShortColumn copyEmpty() {
        return create(getColumnName());
    }

    @Override
    public ShortColumn copy() {
        Short[] copyData = Arrays.copyOf(getValues(), size());
        return create(getColumnName(), copyData);
    }

    @Override
    public ShortColumn unique() {
        Set<Short> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Short[] uniqueArray = uniqueValues.toArray(new Short[0]);
        return create(getColumnName(), uniqueArray);
    }

    @Override
    public ShortColumn filter(ColumnPredicate<Short> condition) {
        List<Short> filteredValues = new ArrayList<>();
        for (Short value : getValues()) {
            if (condition.test(value)) {
                filteredValues.add(value);
            }
        }

        Short[] filteredArray = filteredValues.toArray(new Short[0]);
        return create(getColumnName(), filteredArray);
    }

    @Override
    public ShortColumn apply(ColumnFunction<Short, Short> function) {
        Short[] transformedValues = new Short[size()];
        for (int i = 0; i < size(); i++) {
            transformedValues[i] = function.apply(get(i));
        }

        return create(getColumnName(), transformedValues);
    }
}
