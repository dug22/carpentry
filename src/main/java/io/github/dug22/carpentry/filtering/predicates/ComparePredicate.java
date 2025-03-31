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

package io.github.dug22.carpentry.filtering.predicates;

import io.github.dug22.carpentry.filtering.FilterPredicate;
import io.github.dug22.carpentry.row.DataRow;

public class ComparePredicate {

    private final String columnName;

    public ComparePredicate(String columnName) {
        this.columnName = columnName;
    }

    public <T extends Comparable<T>> FilterPredicate greaterThan(T value) {
        return row -> compare(row, value) > 0;
    }

    public <T extends Comparable<T>> FilterPredicate greaterThanOrEqual(T value) {
        return row -> compare(row, value) >= 0;
    }

    public <T extends Comparable<T>> FilterPredicate lessThan(T value) {
        return row -> compare(row, value) < 0;
    }

    public <T extends Comparable<T>> FilterPredicate lessThanOrEqual(T value) {
        return row -> compare(row, value) <= 0;
    }

    public <T extends Comparable<T>> FilterPredicate equal(T value) {
        return row -> compare(row, value) == 0;
    }

    public <T extends Comparable<T>> FilterPredicate notEqual(T value) {
        return row -> compare(row, value) != 0;
    }

    @SuppressWarnings("unchecked")
    public <T extends Comparable<T>> FilterPredicate between(T min, T max) {
        return row -> {
            T rowValue = (T) row.getRowData().get(columnName);
            return rowValue.compareTo(min) >= 0 && rowValue.compareTo(max) <= 0;
        };
    }

    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> int compare(DataRow row, T value) {
        T rowValue = (T) row.getRowData().get(columnName);
        if (rowValue == null) {
            return -1;
        }
        if(!(rowValue instanceof Number)) throw new IllegalArgumentException("Your row value must be a numeric type!");
        return rowValue.compareTo(value);
    }
}
