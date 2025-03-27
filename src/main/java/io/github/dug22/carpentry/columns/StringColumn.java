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

import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnFunction;
import io.github.dug22.carpentry.column.ColumnPredicate;
import io.github.dug22.carpentry.column.strings.StringColumnConversionFunctions;
import io.github.dug22.carpentry.column.strings.StringMapFunctions;

import java.util.*;

public class StringColumn extends AbstractColumn<String> implements StringColumnConversionFunctions, StringMapFunctions {


    public StringColumn(String columnName) {
        super(columnName, String.class);
    }

    public StringColumn(String name, String[] values) {
        super(name, String.class, values);
    }

    public static StringColumn create(String columnName) {
        return new StringColumn(columnName);
    }


    public static StringColumn create(String columnName, String[] values) {
        return new StringColumn(columnName, values);
    }

    @Override
    public StringColumn copyEmpty() {
        return create(getColumnName());
    }

    @Override
    public StringColumn copy() {
        String[] copyData = Arrays.copyOf(getValues(), size());
        return create(getColumnName(), copyData);
    }

    @Override
    public StringColumn unique() {
        Set<String> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        String[] uniqueArray = uniqueValues.toArray(new String[0]);
        return create(getColumnName(), uniqueArray);
    }

    @Override
    public StringColumn filter(ColumnPredicate<String> condition) {
        List<String> filteredValues = new ArrayList<>();
        for (String value : getValues()) {
            if (condition.test(value)) {
                filteredValues.add(value);
            }
        }

        String[] filteredArray = filteredValues.toArray(new String[0]);
        return create(getColumnName(), filteredArray);
    }

    @Override
    public StringColumn apply(ColumnFunction<String, String> function) {
        String[] transformedValues = new String[size()];
        for (int i = 0; i < size(); i++) {
            transformedValues[i] = function.apply(get(i));
        }

        return create(getColumnName(), transformedValues);
    }
}
