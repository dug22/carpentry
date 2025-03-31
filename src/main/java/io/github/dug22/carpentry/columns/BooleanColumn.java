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

import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnFunction;
import io.github.dug22.carpentry.column.ColumnPredicate;
import io.github.dug22.carpentry.column.booleans.BooleanConversionFunctions;
import io.github.dug22.carpentry.column.booleans.BooleanMapFunctions;

import java.util.*;

public class BooleanColumn extends AbstractColumn<Boolean> implements BooleanConversionFunctions, BooleanMapFunctions {


    public BooleanColumn(String columnName) {
        super(columnName, Boolean.class);
    }

    public BooleanColumn(String name, Boolean[] values) {
        super(name, Boolean.class, values);
    }

    public static BooleanColumn create(String columnName) {
        return new BooleanColumn(columnName);
    }


    public static BooleanColumn create(String columnName, Boolean[] values) {
        return new BooleanColumn(columnName, values);
    }

    @Override
    public BooleanColumn copyEmpty() {
        return create(getColumnName());
    }

    @Override
    public BooleanColumn copy() {
        Boolean[] copyData = Arrays.copyOf(getValues(), size());
        return create(getColumnName(), copyData);
    }

    @Override
    public BooleanColumn unique() {
        Set<Boolean> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Boolean[] uniqueArray = uniqueValues.toArray(new Boolean[0]);
        return create(getColumnName(), uniqueArray);
    }

    @Override
    public BooleanColumn filter(ColumnPredicate<Boolean> condition) {
        List<Boolean> filteredValues = new ArrayList<>();
        for (Boolean value : getValues()) {
            if (condition.test(value)) {
                filteredValues.add(value);
            }
        }

        Boolean[] filteredArray = filteredValues.toArray(new Boolean[0]);
        return create(getColumnName(), filteredArray);
    }

    @Override
    public BooleanColumn apply(ColumnFunction<Boolean, Boolean> function) {
        Boolean[] transformedValues = new Boolean[size()];
        for (int i = 0; i < size(); i++) {
            transformedValues[i] = function.apply(get(i));
        }

        return create(getColumnName(), transformedValues);
    }
}
