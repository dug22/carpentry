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
import io.github.dug22.carpentry.column.numbers.FloatColumnConversionFunctions;

import java.util.*;

public class FloatColumn extends NumericColumn<Float> implements FloatColumnConversionFunctions {

    public FloatColumn(String columnName) {
        super(columnName, Float.class);
    }

    public FloatColumn(String columnName, Float[] values) {
        super(columnName, Float.class, values);
    }

    public static FloatColumn create(String columnName) {
        return new FloatColumn(columnName);
    }

    public static FloatColumn create(String columnName, Float[] values) {
        return new FloatColumn(columnName, values);
    }

    @Override
    public FloatColumn copyEmpty() {
        return create(getColumnName());
    }

    @Override
    public FloatColumn copy() {
        Float[] copyData = Arrays.copyOf(getValues(), size());
        return create(getColumnName(), copyData);
    }

    @Override
    public FloatColumn unique() {
        Set<Float> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Float[] uniqueArray = uniqueValues.toArray(new Float[0]);
        return create(getColumnName(), uniqueArray);
    }

    @Override
    public FloatColumn filter(ColumnPredicate<Float> condition) {
        List<Float> filteredValues = new ArrayList<>();
        for (Float value : getValues()) {
            if (condition.test(value)) {
                filteredValues.add(value);
            }
        }

        Float[] filteredArray = filteredValues.toArray(new Float[0]);
        return create(getColumnName(), filteredArray);
    }

    @Override
    public FloatColumn apply(ColumnFunction<Float, Float> function) {
        Float[] transformedValues = new Float[size()];
        for (int i = 0; i < size(); i++) {
            transformedValues[i] = function.apply(get(i));
        }

        return create(getColumnName(), transformedValues);
    }
}