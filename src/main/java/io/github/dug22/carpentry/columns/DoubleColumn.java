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
import io.github.dug22.carpentry.column.numbers.DoubleColumnConversionFunctions;

import java.util.*;

public class DoubleColumn extends NumericColumn<Double> implements DoubleColumnConversionFunctions {

    public DoubleColumn(String columnName) {
        super(columnName, Double.class);
    }

    public DoubleColumn(String columnName, Double[] values) {
        super(columnName, Double.class, values);
    }

    public static DoubleColumn create(String columnName) {
        return new DoubleColumn(columnName);
    }

    public static DoubleColumn create(String columnName, Double[] values) {
        return new DoubleColumn(columnName, values);
    }

    @Override
    public DoubleColumn copyEmpty() {
        return create(getColumnName());
    }

    @Override
    public DoubleColumn copy() {
        Double[] copyData = Arrays.copyOf(getValues(), size());
        return create(getColumnName(), copyData);
    }

    @Override
    public DoubleColumn unique() {
        Set<Double> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Double[] uniqueArray = uniqueValues.toArray(new Double[0]);
        return create(getColumnName(), uniqueArray);
    }

    @Override
    public DoubleColumn filter(ColumnPredicate<Double> condition) {
        List<Double> filteredValues = new ArrayList<>();
        for (Double value : getValues()) {
            if (condition.test(value)) {
                filteredValues.add(value);
            }
        }

        Double[] filteredArray = filteredValues.toArray(new Double[0]);
        return create(getColumnName(), filteredArray);
    }

    @Override
    public DoubleColumn apply(ColumnFunction<Double, Double> function) {
        Double[] transformedValues = new Double[size()];
        for (int i = 0; i < size(); i++) {
            transformedValues[i] = function.apply(get(i));
        }

        return create(getColumnName(), transformedValues);
    }
}
