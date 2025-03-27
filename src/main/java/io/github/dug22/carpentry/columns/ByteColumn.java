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
import io.github.dug22.carpentry.column.numbers.ByteColumnConversionFunctions;

import java.util.*;

public class ByteColumn extends NumericColumn<Byte> implements ByteColumnConversionFunctions {

    public ByteColumn(String columnName) {
        super(columnName, Byte.class);
    }

    public ByteColumn(String name, Byte[] values) {
        super(name, Byte.class, values);
    }

    public static ByteColumn create(String columnName) {
        return new ByteColumn(columnName);
    }

    public static ByteColumn create(String columnName, Byte[] values) {
        return new ByteColumn(columnName, values);
    }

    @Override
    public ByteColumn copyEmpty() {
        return create(getColumnName());
    }

    @Override
    public ByteColumn copy() {
        Byte[] copyData = Arrays.copyOf(getValues(), size());
        return create(getColumnName(), copyData);
    }

    @Override
    public ByteColumn unique() {
        Set<Byte> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Byte[] uniqueArray = uniqueValues.toArray(new Byte[0]);
        return create(getColumnName(), uniqueArray);
    }

    @Override
    public ByteColumn filter(ColumnPredicate<Byte> condition) {
        List<Byte> filteredValues = new ArrayList<>();
        for (Byte value : getValues()) {
            if (condition.test(value)) {
                filteredValues.add(value);
            }
        }

        Byte[] filteredArray = filteredValues.toArray(new Byte[0]);
        return create(getColumnName(), filteredArray);
    }

    @Override
    public ByteColumn apply(ColumnFunction<Byte, Byte> function) {
        Byte[] transformedValues = new Byte[size()];
        for (int i = 0; i < size(); i++) {
            transformedValues[i] = function.apply(get(i));
        }

        return create(getColumnName(), transformedValues);
    }
}
