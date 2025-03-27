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

package io.github.dug22.carpentry.sorting;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnCreator;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;

import java.util.List;

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
        List<DataRow> sortedDataRows = isParallel ? dataRows.parallelStream()
                .sorted(rowColumnComparator)
                .toList() : dataRows.stream().sorted(rowColumnComparator).toList();
        DefaultDataFrame sortedDataFrame = DefaultDataFrame.create();
        for (AbstractColumn<?> column : dataFrame.getColumnMap().values()) {
            AbstractColumn<T> sortedColumn = (AbstractColumn<T>) ColumnCreator.createNewColumnInstance(column);
            for (DataRow row : sortedDataRows) {
                T value = (T) row.getRowData().get(column.getColumnName());
                sortedColumn.append(value);
            }
            sortedDataFrame.addColumn(sortedColumn);
        }

        return sortedDataFrame;
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
