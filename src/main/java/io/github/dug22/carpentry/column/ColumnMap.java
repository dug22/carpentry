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

package io.github.dug22.carpentry.column;

import java.util.*;

public class ColumnMap extends LinkedHashMap<String, AbstractColumn<?>> {

    public ColumnMap() {
        super();
    }

    public ColumnMap(Map<String, AbstractColumn<?>> map) {
        super(map);
    }

    /**
     * Adds a column to the ColumnMap
     * Note if a duplicate key column is detected, that column name will be applied with an additional suffix after it.
     *
     * @param column the column you want to add.
     */
    public void addColumn(AbstractColumn<?> column) {
        String name = column.getColumnName();
        int maxRowCount = getRowCount();
        while (column.size() < maxRowCount) {
            column.appendNull();
        }

        if (containsColumn(name)) {
            char suffix = 'a';
            while (containsKey(name + "_" + suffix)) {
                suffix++;
            }
            put(name + "_" + suffix, column);
        } else {
            put(name, column);
        }
    }


    public AbstractColumn<?> getColumn(String name) {
        return get(name);
    }

    public boolean containsColumn(String name) {
        return containsKey(name);
    }

    public void removeColumn(String name) {
        remove(name);
    }

    public int getRowCount() {
        int maxRowCount = 0;
        for (AbstractColumn<?> column : this.values()) {
            maxRowCount = Math.max(maxRowCount, column.size());
        }
        return maxRowCount;
    }

    public int indexOfKey(String key) {
        List<String> keys = new ArrayList<>(this.keySet());
        return keys.indexOf(key);
    }
}