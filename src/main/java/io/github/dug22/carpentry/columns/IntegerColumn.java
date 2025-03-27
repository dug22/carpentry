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
import io.github.dug22.carpentry.column.numbers.IntegerColumnConversionFunctions;

import java.util.*;

public class IntegerColumn extends NumericColumn<Integer> implements IntegerColumnConversionFunctions {

    public IntegerColumn(String columnName) {
        super(columnName, Integer.class);
    }

    public IntegerColumn(String columnName, Integer[] values) {
        super(columnName, Integer.class, values);
    }

    public static IntegerColumn create(String columnName) {
        return new IntegerColumn(columnName);
    }

    public static IntegerColumn create(String columnName, Integer[] values) {
        return new IntegerColumn(columnName, values);
    }

    @Override
    public IntegerColumn copyEmpty() {
        return create(getColumnName());
    }

    @Override
    public IntegerColumn copy() {
        Integer[] copyData = Arrays.copyOf(getValues(), size());
        return create(getColumnName(), copyData);
    }

    @Override
    public IntegerColumn unique() {
        Set<Integer> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Integer[] uniqueArray = uniqueValues.toArray(new Integer[0]);
        return create(getColumnName(), uniqueArray);
    }

    @Override
    public IntegerColumn filter(ColumnPredicate<Integer> condition) {
        List<Integer> filteredValues = new ArrayList<>();
        for (Integer value : getValues()) {
            if (condition.test(value)) {
                filteredValues.add(value);
            }
        }

        Integer[] filteredArray = filteredValues.toArray(new Integer[0]);
        return create(getColumnName(), filteredArray);
    }

    @Override
    public IntegerColumn apply(ColumnFunction<Integer, Integer> function) {
        Integer[] transformedValues = new Integer[size()];
        for (int i = 0; i < size(); i++) {
            transformedValues[i] = function.apply(get(i));
        }

        return create(getColumnName(), transformedValues);
    }
}