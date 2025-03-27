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

package io.github.dug22.carpentry.fill;

import io.github.dug22.carpentry.DataFrameException;
import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;

public class FillFunction {

    private final DefaultDataFrame dataFrame;

    public FillFunction(DefaultDataFrame dataFrame){
        this.dataFrame = dataFrame;
    }

    /**
     * Fills null values in specified columns with provided values.
     *
     * @param columnValuePairs column-value pairs for filling
     * @return modified DataFrame
     * @throws IllegalArgumentException if a column doesn't exist
     */
    public DefaultDataFrame fillNA(FillColumnValuePairs columnValuePairs) {
        for (FillColumnValuePair pair : columnValuePairs.columnValuePairs()) {
            String columnName = pair.columnName();
            Object fillValue = pair.value();

            if (!dataFrame.getColumnMap().containsColumn(columnName)) {
                throw new IllegalArgumentException("Column " + columnName + " does not exist in the DataFrame.");
            }

            AbstractColumn<?> column = dataFrame.getColumn(columnName);
            fillNullColumn(column, fillValue);
        }

        return dataFrame;
    }

    /**
     * Fills null values in a specific column with the provided value.
     *
     * @param column the column to fill
     * @param fillValue value to replace nulls with
     * @param <T> type of column
     */
    @SuppressWarnings("unchecked")
    private <T> void fillNullColumn(AbstractColumn<T> column, Object fillValue) {
        for (int i = 0; i < column.size(); i++) {
            T currentValue = column.get(i);
            if (currentValue == null) {
                if (fillValue != null && column.getColumnType().isAssignableFrom(fillValue.getClass())) {
                    T typedFillValue = (T) fillValue;
                    column.set(i, typedFillValue);
                } else {
                    throw new DataFrameException(
                            "Cannot assign value of type " + (fillValue != null ? fillValue.getClass().getSimpleName() : "null") + " to column '" + column.getColumnName() + "' of type " + column.getColumnType().getSimpleName()
                    );
                }
            }
        }
    }
}
