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

package io.github.dug22.carpentry.drop;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DropFunction {

    private final DefaultDataFrame dataFrame;

    public DropFunction(DefaultDataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    /**
     * Drops columns from a given DataFrame.
     * @param columnNames the columns to drop.
     * @return a new DataFrame with the applied drop function.
     */
    public DefaultDataFrame drop(String... columnNames) {
        ColumnMap currentColumnMap = dataFrame.getColumnMap();
        for (String columnName : columnNames) {
            if (currentColumnMap.containsColumn(columnName)) {
                currentColumnMap.removeColumn(columnName);
            } else {
                System.out.println("Warning: " + columnName + " doesn't exist");
            }
        }

        return DefaultDataFrame.create(currentColumnMap);
    }

    /**
     * Drops columns from a given DataFrame.
     * @param indexes the columns to drop (based on their position in the DataFrame).
     * @return a new DataFrame with the applied drop function.
     */
    public DefaultDataFrame drop(int... indexes) {
        ColumnMap currentColumnMap = dataFrame.getColumnMap();
        int currentIndex = 0;
        List<String> columnsToDrop = new ArrayList<>();
        for (String columnName : currentColumnMap.keySet()) {
            for (int index : indexes) {
                if (currentIndex == index) {
                    columnsToDrop.add(columnName);
                }
            }

            currentIndex++;
        }

        return drop(columnsToDrop.toArray(new String[0]));
    }

    /**
     * Drop null rows from a given DataFrame.
     * @param how dictates how to drop null values.
     *  HOW.ANY Drops a rows if any null values are present.
     *  HOW.ALL drops a row if all values are null.
     * @return a new DataFrame with the rows with null values removed
     */
    public DefaultDataFrame dropNA(How how) {
        DataRows originalRows = dataFrame.getRows();
        DataRows keptRows = new DataRows();
        for (DataRow row : originalRows) {
            boolean keepRow;
            if (how == How.ANY) {
                keepRow = row.getRowData().values().stream().noneMatch(Objects::isNull);
            } else {
                keepRow = row.getRowData().values().stream().anyMatch(Objects::nonNull);
            }

            if (keepRow) {
                keptRows.add(row);
            }
        }


        return createNewDataFrameWithKeptRows(keptRows);
    }

    /**
     * Drops rows with null values in specific columns from the DataFrame.
     *
     * @param columnNames the columns to check for null values
     * @return a new DataFrame with rows having non-null values in the specified columns
     */
    public DefaultDataFrame dropNA(String... columnNames) {
        DataRows originalRows = dataFrame.getRows();
        DataRows keptRows = new DataRows();
        for (DataRow dataRow : originalRows) {
            boolean keepRow = true;
            for (String columnName : columnNames) {
                Object value = dataRow.getRowData().get(columnName);
                if (value == null) {
                    keepRow = false;
                    break;
                }
            }

            if (keepRow) {
                keptRows.add(dataRow);
            }
        }

        return createNewDataFrameWithKeptRows(keptRows);
    }

    /**
     * Creates a new DataFrame with only the rows that were kept after applying the drop function.
     *
     * @param keptRows the rows that should be kept in the new DataFrame
     * @return a new DataFrame containing only the kept rows
     */
    private DefaultDataFrame createNewDataFrameWithKeptRows(DataRows keptRows) {
        DefaultDataFrame newDataFrame = DefaultDataFrame.create();
        ColumnMap currentColumnMap = dataFrame.getColumnMap();
        for (String columnName : currentColumnMap.keySet()) {
            AbstractColumn<?> newColumn = dataFrame.getColumn(columnName).copyEmpty();
            newDataFrame.addColumn(newColumn);
        }

        for (DataRow keptRow : keptRows) {
            newDataFrame.addRow(keptRow);
        }

        return newDataFrame;
    }
}
