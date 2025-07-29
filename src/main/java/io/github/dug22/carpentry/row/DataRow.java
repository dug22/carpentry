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

package io.github.dug22.carpentry.row;


import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.column.Column;
import java.util.*;

public class DataRow implements Row {
    private final DataFrame dataFrame;
    private final int rowIndex;
    private final List<String> columnNames;

    public DataRow(DataFrame dataFrame, int rowIndex) {
        this.dataFrame = dataFrame;
        this.rowIndex = rowIndex;
        this.columnNames = dataFrame.columnNames();
        if (rowIndex < 0 || rowIndex >= dataFrame.getRowCount()) {
            throw new IllegalArgumentException("Row index " + rowIndex + " is out of bounds");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String columnName) {
        Column<?> column = dataFrame.getColumn(columnName);
        return (T) column.get(rowIndex);
    }

    @Override
    public <T> T get(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columnNames.size()) {
            throw new IllegalArgumentException("Column index " + columnIndex + " is out of bounds");
        }
        String columnName = columnNames.get(columnIndex);
        return get(columnName);
    }

    @Override
    public int getIndex() {
        return rowIndex;
    }

    @Override
    public boolean isAbsent(String columnName) {
        Column<?> column = dataFrame.getColumn(columnName);
        return column.isAbsent(rowIndex);
    }

    @Override
    public boolean isAbsent(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columnNames.size()) {
            throw new IllegalArgumentException("Column index " + columnIndex + " is out of bounds");
        }
        String columnName = columnNames.get(columnIndex);
        return isAbsent(columnName);
    }

    @Override
    public Object[] toArray() {
        Object[] values = new Object[columnNames.size()];
        for (int i = 0; i < columnNames.size(); i++) {
            values[i] = get(i);
        }
        return values;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        for (String columnName : columnNames) {
            map.put(columnName, get(columnName));
        }
        return map;
    }

    @Override
    public List<String> getColumnNames() {
        return new ArrayList<>(columnNames);
    }
}