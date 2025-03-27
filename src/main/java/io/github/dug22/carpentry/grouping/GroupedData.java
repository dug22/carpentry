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

package io.github.dug22.carpentry.grouping;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.row.DataRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupedData {

    private final List<Group> groups;

    public GroupedData(DataFrame dataFrame, String[] groupByColumns) {
        this.groups = new ArrayList<>();
        groupData(dataFrame, groupByColumns);
    }

    private void groupData(DataFrame dataFrame, String[] groupByColumns) {
        for (DataRow row : dataFrame.getRows()) {
            if (isRowInvalid(row, groupByColumns)) {
                continue;
            }

            Object[] key = new Object[groupByColumns.length];
            for (int i = 0; i < groupByColumns.length; i++) {
                key[i] = row.getRowData().get(groupByColumns[i]);
            }

            Group group = findOrCreateGroup(key);
            group.addRow(row);
        }
    }


    private boolean isRowInvalid(DataRow row, String[] groupByColumns) {
        for (String column : groupByColumns) {
            Object value = row.getRowData().get(column);
            if (value == null) {
                return true;
            }
        }
        return false;
    }

    private Group findOrCreateGroup(Object[] key) {
        for (Group group : groups) {
            if (Arrays.equals(group.getKey(), key)) {
                return group;
            }
        }
        Group newGroup = new Group(key);
        groups.add(newGroup);
        return newGroup;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
