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
import java.util.*;

/**
 * Represents a collection of groups created from a DataFrame based on specified columns.
 */
public class GroupedData {

    /**
     * The list of groups formed by grouping the DataFrame.
     */
    private final List<Group> groups;

    /**
     * Constructs a GroupedData instance by grouping the DataFrame based on the specified columns.
     * @param dataFrame The DataFrame to group
     * @param groupByColumns The names of the columns to group by
     */
    public GroupedData(DataFrame dataFrame, String[] groupByColumns) {
        this.groups = new ArrayList<>();
        groupData(dataFrame, groupByColumns);
    }

    /**
     * Groups the DataFrame rows based on the values in the specified columns, creating a Group for each unique key.
     * @param dataFrame The DataFrame to group
     * @param groupByColumns The names of the columns to group by
     */
    private void groupData(DataFrame dataFrame, String[] groupByColumns) {
        Map<KeyWrapper, Group> groupMap = new LinkedHashMap<>();
        int rowCount = dataFrame.getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            Object[] key = new Object[groupByColumns.length];
            boolean isValid = true;
            for (int i = 0; i < groupByColumns.length; i++) {
                Column<?> column = dataFrame.getColumn(groupByColumns[i]);
                key[i] = column.get(rowIndex);
                if (key[i] == null) {
                    isValid = false;
                    break;
                }
            }

            if (!isValid) continue;

            KeyWrapper keyWrapper = new KeyWrapper(key);
            Group group = groupMap.computeIfAbsent(keyWrapper, k -> {
                Group newGroup = new Group(k.key, dataFrame);
                groups.add(newGroup);
                return newGroup;
            });
            group.getRowIndexes().add(rowIndex);
        }
    }

    /**
     * Returns the list of groups created from the DataFrame.
     * @return The list of groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * A record used to wrap an array of objects (group key) for use as a map key, providing custom equality and hash code.
     * @param key The array of objects representing the group key
     */
    private record KeyWrapper(Object[] key) {

        /**
         * Checks if this KeyWrapper is equal to another object by comparing the key arrays.
         * @param o The object to compare with
         * @return true if the objects are equal, false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            KeyWrapper that = (KeyWrapper) o;
            return Arrays.equals(key, that.key);
        }

        /**
         * Computes the hash code for the key array.
         * @return The hash code of the key array
         */
        @Override
        public int hashCode() {
            return Arrays.hashCode(key);
        }
    }
}