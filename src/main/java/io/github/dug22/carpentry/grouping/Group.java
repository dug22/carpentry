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

package io.github.dug22.carpentry.grouping;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.row.DataRow;

import java.util.ArrayList;
import java.util.List;

public class Group implements Comparable<Group> {
    private final Object[] key;
    private final List<Integer> rowIndices;
    private final DataFrame dataFrame;

    public Group(Object[] key, DataFrame dataFrame) {
        this.key = key;
        this.rowIndices = new ArrayList<>();
        this.dataFrame = dataFrame;
    }

    public void addRowIndex(int rowIndex) {
        rowIndices.add(rowIndex);
    }

    public List<Object> getValuesForColumn(String columnName) {
        AbstractColumn<?> column = dataFrame.getColumn(columnName);
        List<Object> values = new ArrayList<>(rowIndices.size());
        for (int index : rowIndices) {
            values.add(column.get(index));
        }
        return values;
    }

    public Object[] getKey() {
        return key;
    }

    public List<DataRow> getRows() {
        List<DataRow> rows = new ArrayList<>(rowIndices.size());
        for (int index : rowIndices) {
            DataRow row = new DataRow();
            for (AbstractColumn<?> col : dataFrame.getColumnMap().values()) {
                row.append(col.getColumnName(), col.get(index));
            }
            rows.add(row);
        }
        return rows;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(Group other) {
        for (int i = 0; i < key.length; i++) {
            Comparable<Object> key1 = (Comparable<Object>) key[i];
            Comparable<Object> key2 = (Comparable<Object>) other.key[i];
            int comparison = key1.compareTo(key2);
            if (comparison != 0) return comparison;
        }
        return 0;
    }
}