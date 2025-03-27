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

package io.github.dug22.carpentry.rename;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnCreator;
import io.github.dug22.carpentry.column.ColumnMap;

import java.util.List;

public class RenameFunction {

    private final DefaultDataFrame dataFrame;

    public RenameFunction(DefaultDataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    /**
     * Renames columns in a DataFrame based on a provided rename map.
     * If a column name exists in the rename map, it is renamed to the new name specified in the map.
     *
     * @param renameMap A map containing old column names as keys and new column names as values.
     * @return The updated DataFrame with renamed columns.
     * @param <T> The type of data in the columns.
     */
    @SuppressWarnings("unchecked")
    public <T> DefaultDataFrame renameColumns(RenameMap renameMap) {
        List<String> columnNames = dataFrame.getColumnNames();
        ColumnMap currentColumnMap = dataFrame.getColumnMap();
        ColumnMap newColumnMap = new ColumnMap();
        for (String columnName : columnNames) {
            if (renameMap.contains(columnName)) {
                String newName = renameMap.getNewName(columnName);
                AbstractColumn<?> oldColumn = currentColumnMap.getColumn(columnName);
                AbstractColumn<T> newColumn = (AbstractColumn<T>) ColumnCreator.createNewColumnInstance(oldColumn);
                newColumn.setColumnName(newName);
                T[] oldColumnData = (T[]) oldColumn.getValues();
                newColumn.setValues(oldColumnData);
                newColumnMap.addColumn(newColumn);
            } else {
                newColumnMap.addColumn(currentColumnMap.get(columnName));
            }
        }

        currentColumnMap.clear();
        currentColumnMap.putAll(newColumnMap);
        return dataFrame;
    }
}
