/*
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *
 */

package io.github.dug22.carpentry.column;

public interface ColumnConversion<T> {

    T get(int index);

    String name();

    int size();

    boolean isAbsent(int index);

    /**
     * Converts a column to a different column type
     * @param column the column you wish to convert
     * @param mapper grabs all values by index to convert
     * @return converts a column to a different column type
     * @param <T> the new column data type
     * @param <C> the original column data type
     */
    default <T, C extends Column<T>> C convertToColumn(C column, ColumnIntFunction<T> mapper) {
        for (int i = 0; i < size(); i++) {
            column.set(i, mapper.apply(i));
        }
        return column;
    }
}
