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

package io.github.dug22.carpentry.grouping;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.column.Column;

import java.util.ArrayList;
import java.util.List;

public class Group implements Comparable<Group> {

    private final Object[] key;
    private final List<Integer> rowIndexes;
    private final DataFrame dataFrame;

    public Group(Object[] key, DataFrame dataFrame) {
        this.key = key;
        this.rowIndexes = new ArrayList<>();
        this.dataFrame = dataFrame;
    }

    /**
     * Retrieves the data for a specified column for all row indexes in this group.
     * @param columnName The name of the column to retrieve data from
     * @return A list of column data corresponding to the group's row indexes
     */
    public List<Object> getColumnData(String columnName) {
        Column<?> column = dataFrame.getColumn(columnName);
        List<Object> columnData = new ArrayList<>(rowIndexes.size());
        for (int index : rowIndexes) {
            columnData.add(column.get(index));
        }
        return columnData;
    }

    /**
     * Returns the key identifying this group.
     * @return The array of objects representing the group's key
     */
    public Object[] getKey() {
        return key;
    }

    /**
     * Compares this group to another group based on their keys for sorting purposes.
     * @param otherGroup The group to compare to
     * @return A negative integer, zero, or a positive integer as this group is less than, equal to, or greater than the specified group
     */
    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(Group otherGroup) {
        int keysLength = key.length;
        for (int index = 0; index < keysLength; index++) {
            Comparable<Object> keyA = (Comparable<Object>) key[index];
            Comparable<Object> keyB = (Comparable<Object>) otherGroup.key[index];
            int comparison = keyA.compareTo(keyB);
            if (comparison != 0) return comparison;
        }
        return 0;
    }

    /**
     * Returns the list of row indexes associated with this group.
     * @return The list of row indexes
     */
    public List<Integer> getRowIndexes() {
        return rowIndexes;
    }
}