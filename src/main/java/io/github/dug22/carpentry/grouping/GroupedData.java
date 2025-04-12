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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupedData {
    private final List<Group> groups;

    public GroupedData(DataFrame dataFrame, String[] groupByColumns) {
        this.groups = new ArrayList<>();
        groupData(dataFrame, groupByColumns);
    }

    private void groupData(DataFrame dataFrame, String[] groupByColumns) {
        Map<KeyWrapper, Group> groupMap = new HashMap<>();
        int rowCount = dataFrame.getRowCount();

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            Object[] key = new Object[groupByColumns.length];
            boolean isValid = true;

            for (int i = 0; i < groupByColumns.length; i++) {
                AbstractColumn<?> column = dataFrame.getColumn(groupByColumns[i]);
                key[i] = column.get(rowIndex);
                if (key[i] == null) {
                    isValid = false;
                    break;
                }
            }

            if (!isValid) {
                continue;
            }

            KeyWrapper keyWrapper = new KeyWrapper(key);
            Group group = groupMap.computeIfAbsent(keyWrapper, k -> {
                Group newGroup = new Group(k.key, dataFrame);
                groups.add(newGroup);
                return newGroup;
            });
            group.addRowIndex(rowIndex);
        }
    }

    public List<Group> getGroups() {
        return groups;
    }

    private record KeyWrapper(Object[] key) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            KeyWrapper that = (KeyWrapper) o;
            return Arrays.equals(key, that.key);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(key);
        }
    }
}