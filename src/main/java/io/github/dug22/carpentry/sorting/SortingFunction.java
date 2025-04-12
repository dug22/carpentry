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

package io.github.dug22.carpentry.sorting;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SortingFunction {

    private final DefaultDataFrame dataFrame;

    public SortingFunction(DefaultDataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    /**
     * Sorts the DataFrame based on the provided columns.
     * The sorting can be done either in parallel or sequentially.
     *
     * @param sortColumns The columns and their sorting directions.
     * @param isParallel If true, sorting is done in parallel.
     * @param <T> The type of column values.
     * @return A new DataFrame with sorted rows.
     */
    @SuppressWarnings("unchecked")
    private <T> DefaultDataFrame sort(SortColumn[] sortColumns, boolean isParallel) {
        DataRows dataRows = dataFrame.getRows();
        RowColumnComparator rowColumnComparator = new RowColumnComparator(sortColumns);
        if (isParallel) {
            DataRow[] rowArray = dataRows.toArray(new DataRow[0]);
            Arrays.parallelSort(rowArray, rowColumnComparator);
            dataRows.clear();
            dataRows.addAll(Arrays.asList(rowArray));
        } else {
            dataRows.sort(rowColumnComparator);
        }

        for (AbstractColumn<?> column : dataFrame.getColumnMap().values()) {
            Object newValues = Array.newInstance(
                    column.getColumnType(),
                    dataRows.size()
            );
            for (int i = 0; i < dataRows.size(); i++) {
                Object value = dataRows.get(i).getRowData().get(column.getColumnName());
                Array.set(newValues, i, value);
            }

            ((AbstractColumn<T>) column).setValues((T[]) newValues);
        }

        return dataFrame;
    }

    /**
     * Sorts the DataFrame sequentially based on the provided columns.
     *
     * @param columns The columns and their sorting directions.
     * @return A new DataFrame with sorted rows.
     */
    public DefaultDataFrame sort(SortColumn[] columns) {
        return sort(columns, false);
    }

    /**
     * Sorts the DataFrame in parallel based on the provided columns.
     *
     * @param columns The columns and their sorting directions.
     * @return A new DataFrame with sorted rows.
     */
    public DefaultDataFrame sortInParallel(SortColumn[] columns) {
        return sort(columns, true);
    }
}