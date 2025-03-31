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

package io.github.dug22.carpentry.column;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

public class ColumnConvertor {

    /**
     * Converts a given column into another column type by applying a transformation function.
     *
     * @param originalColumn the column to be converted
     * @param type the target column class type (e.g., IntegerColumn, StringColumn)
     * @param function the function used to convert each item from the original column to the target type
     * @param <T> the type of the original column
     * @param <U> the type of the target column
     * @param <R> the target column type which extends AbstractColumn<U>
     * @return a new column of the specified target type with the transformed data
     * @throws ColumnException if the column conversion fails
     */
    @SuppressWarnings("unchecked")
    public static <T, U, R extends AbstractColumn<U>> R convert(AbstractColumn<T> originalColumn, Class<U> type, ColumnFunction<T, U> function) {
        int originalSize = originalColumn.size();
        U[] convertedData = (U[]) Array.newInstance(type, originalSize);

        for (int i = 0; i < originalSize; i++) {
            T item = originalColumn.get(i);
            convertedData[i] = (item != null) ? function.apply(item) : null;
        }

        try {
            String className = "io.github.dug22.carpentry.columns." + type.getSimpleName() + "Column";
            Class<?> columnClass = Class.forName(className);
            Constructor<?> constructor = columnClass.getConstructor(String.class, type.arrayType());
            R newColumn = (R) constructor.newInstance(originalColumn.getColumnName(), convertedData);
            newColumn.setValues(convertedData);
            return newColumn;
        } catch (Exception e) {
            throw new ColumnException("Failed to convert the original column!", e);
        }
    }
}
