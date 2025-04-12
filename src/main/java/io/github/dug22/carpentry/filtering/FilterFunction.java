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


package io.github.dug22.carpentry.filtering;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.row.DataRow;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FilterFunction {

    private final DefaultDataFrame dataFrame;

    public FilterFunction(DefaultDataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    /**
     * Filters rows in the DataFrame based on a provided predicate.
     *
     * @param predicate the condition to test each row against
     * @return a new DataFrame containing only the rows that match the predicate
     */
    @SuppressWarnings("all")
    public <T> DefaultDataFrame filter(FilterPredicate predicate) {
        ColumnMap columnMap = dataFrame.getColumnMap();
        int rowCount = columnMap.getRowCount();

        List<Integer> keepIndices = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            DataRow row = new DataRow();
            for (AbstractColumn<?> column : columnMap.values()) {
                row.append(column.getColumnName(), column.get(i));
            }
            if (predicate.test(row)) {
                keepIndices.add(i);
            }
        }

        for (AbstractColumn<?> column : columnMap.values()) {
            T[] values = (T[]) column.getValues();
            T[] newValues = (T[]) Array.newInstance(column.getColumnType(), keepIndices.size());
            for (int i = 0; i < keepIndices.size(); i++) {
                newValues[i] = values[keepIndices.get(i)];
            }
            AbstractColumn<T> typedColumn = (AbstractColumn<T>) column;
            typedColumn.setValues(newValues);
        }

        return dataFrame;
    }
}