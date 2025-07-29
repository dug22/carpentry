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

package io.github.dug22.carpentry.drop;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.DataFrameFunction;
import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.*;

/**
 * Drops columns or rows from a DataFrame
 */
public class DropFunction extends DataFrameFunction {

    private final Set<String> NA_VALUES = new HashSet<>(Arrays.asList("NA", "N/A", "", "-", "--", "NULL"));

    /**
     * Null-check rule
     */
    private How how;

    /**
     * Single column
     */
    private Column<?> column;

    /**
     * Multiple columns
     */
    private Column<?>[] columns;

    /**
     * Single column name
     */
    private String columnName;

    /**
     * Multiple column names
     */
    private String[] columnNames;

    /**
     * Multiple column indexes
     */
    private int[] columnIndexes;

    /**
     * Single column index
     */
    private int columnIndex;

    private final DataFrame dataFrame = getDataFrame();

    /**
     * Constructor to drop a single column
     */
    public DropFunction(DataFrame dataFrame, Column<?> column) {
        super(dataFrame);
        this.column = column;
    }

    /**
     * Constructor to drop multiple columns
     */
    public DropFunction(DataFrame dataFrame, Column<?>[] columns) {
        super(dataFrame);
        this.columns = columns;
    }

    /**
     * Constructor to drop by a single column name
     */
    public DropFunction(DataFrame dataFrame, String columnName) {
        super(dataFrame);
        this.columnName = columnName;
    }

    /**
     * Constructor to drop by multiple column names
     */
    public DropFunction(DataFrame dataFrame, String[] columnNames) {
        super(dataFrame);
        this.columnNames = columnNames;
    }

    /**
     * Constructor to drop by single column index
     */
    public DropFunction(DataFrame dataFrame, int columnIndex) {
        super(dataFrame);
        this.columnIndex = columnIndex;
    }

    /**
     * Constructor to drop by multiple column indexes
     */
    public DropFunction(DataFrame dataFrame, int[] columnIndexes) {
        super(dataFrame);
        this.columnIndexes = columnIndexes;
    }

    /**
     * Constructor for dropNA rule
     */
    public DropFunction(DataFrame dataFrame, How how) {
        super(dataFrame);
        this.how = how;
    }

    /**
     * Drop a single column
     */
    public DataFrame dropColumn() {
        return dataFrame.removeColumn(column.name());
    }

    /**
     * Drop multiple columns
     */
    public DataFrame dropColumns() {
        for (Column<?> column : columns) {
            dataFrame.removeColumn(column.name());
        }
        return dataFrame;
    }

    /**
     * Drop a column by name
     */
    public DataFrame dropColumnsByColumnName() {
        return dataFrame.removeColumn(columnName);
    }

    /**
     * Drop columns by names
     */
    public DataFrame dropColumnsByColumnNames() {
        for (String columnName : columnNames) {
            dataFrame.removeColumn(columnName);
        }
        return dataFrame;
    }

    /**
     * Drop a column by index
     */
    public DataFrame dropColumnByColumnIndex() {
        return dataFrame.removeColumn(columnIndex);
    }

    /**
     * Drop columns by indexes
     */
    public DataFrame dropColumnByColumnIndexes() {
        for (int index : columnIndexes) {
            dataFrame.removeColumn(index);
        }
        return dataFrame;
    }


    /**
     * Drop rows with nulls according to How.ANY or How.ALL
     */
    public DataFrame dropNA() {
        DataRows originalDataRows = dataFrame.getRows();
        DataRows keptDataRows = new DataRows(); // Don't pass existing DataFrame!

        for (DataRow row : originalDataRows) {
            Map<String, Object> rowMap = row.toMap();
            Collection<Object> rowValues = rowMap.values();

            boolean keep;
            if (how == How.ANY) {
                keep = rowValues.stream().noneMatch(this::isMissing);
            } else if (how == How.ALL) {
                keep = rowValues.stream().anyMatch(v -> !isMissing(v));
            } else {
                throw new IllegalArgumentException("Unsupported How: " + how);
            }

            if (keep) {
                keptDataRows.add(row);
            }
        }

        return createDataFrameWithKeptRows(keptDataRows);
    }


    /**
     * Drop rows with nulls in specific columns
     */
    public DataFrame dropNAByColumnNames() {
        DataRows originalDataRows = dataFrame.getRows();
        DataRows keptDataRows = new DataRows(dataFrame);

        for (DataRow row : originalDataRows) {
            boolean keep = true;
            Map<String, Object> rowMap = row.toMap();
            for (String columnName : columnNames) {
                Object value = rowMap.get(columnName);
                if (Nulls.isNull(value)) {
                    keep = false;
                    break;
                }
            }
            if (keep) {
                keptDataRows.remove(row);
            }
        }

        return createDataFrameWithKeptRows(keptDataRows);
    }

    /**
     * Builds a new DataFrame with kept rows
     */
    private DataFrame createDataFrameWithKeptRows(DataRows keptRows) {
        DataFrame newDataFrame = DataFrame.create();


        List<String> columnNames = dataFrame.columnNames();
        for (String columnName : columnNames) {
            Column<?> sourceCol = dataFrame.getColumn(columnName);
            Column<?> newCol = sourceCol.emptyCopy();
            newDataFrame.addColumn(newCol);
        }


        for (DataRow keptRow : keptRows) {
            newDataFrame.addRow(keptRow.toMap());
        }

        return newDataFrame;
    }

    private boolean isMissing(Object value) {
        return Nulls.isNull(value);
    }
}