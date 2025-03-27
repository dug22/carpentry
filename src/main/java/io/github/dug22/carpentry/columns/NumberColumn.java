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
import io.github.dug22.carpentry.column.numbers.NumberColumnConversionFunctions;

import java.util.*;

public class NumberColumn extends NumericColumn<Number> implements NumberColumnConversionFunctions {

    public NumberColumn(String columnName) {
        super(columnName, Number.class);
    }

    public NumberColumn(String columnName, Number[] values) {
        super(columnName, Number.class, values);
    }

    public static NumberColumn create(String columnName) {
        return new NumberColumn(columnName);
    }

    public static NumberColumn create(String columnName, Number[] values) {
        return new NumberColumn(columnName, values);
    }

    @Override
    public NumberColumn copyEmpty() {
        return create(getColumnName());
    }

    @Override
    public NumberColumn copy() {
        Number[] copyData = Arrays.copyOf(getValues(), size());
        return create(getColumnName(), copyData);
    }

    @Override
    public NumberColumn unique() {
        Set<Number> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Number[] uniqueArray = uniqueValues.toArray(new Number[0]);
        return create(getColumnName(), uniqueArray);
    }

    @Override
    public NumberColumn filter(ColumnPredicate<Number> condition) {
        List<Number> filteredValues = new ArrayList<>();
        for (Number value : getValues()) {
            if (condition.test(value)) {
                filteredValues.add(value);
            }
        }

        Number[] filteredArray = filteredValues.toArray(new Number[0]);
        return create(getColumnName(), filteredArray);
    }

    @Override
    public NumberColumn apply(ColumnFunction<Number, Number> function) {
        Number[] transformedValues = new Number[size()];
        for (int i = 0; i < size(); i++) {
            transformedValues[i] = function.apply(get(i));
        }

        return create(getColumnName(), transformedValues);
    }
}