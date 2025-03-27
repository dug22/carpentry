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

import io.github.dug22.carpentry.row.DataRow;

import java.util.ArrayList;
import java.util.List;

public class Group implements Comparable<Group> {

    private final Object[] key;
    private final List<DataRow> rows;

    public Group(Object[] key) {
        this.key = key;
        this.rows = new ArrayList<>();
    }

    public List<Object> getValuesForColumn(String columnName) {
        List<Object> values = new ArrayList<>();
        for (DataRow row : rows) {
            values.add(row.getRowData().get(columnName));
        }
        return values;
    }

    public void addRow(DataRow row) {
        rows.add(row);
    }

    public Object[] getKey() {
        return key;
    }

    public List<DataRow> getRows() {
        return rows;
    }

    @Override
    @SuppressWarnings("all")
    public int compareTo(Group other) {
        for (int i = 0; i < this.key.length; i++) {
            Comparable key1 = (Comparable) this.key[i];
            Comparable key2 = (Comparable) other.key[i];
            int comparison = key1.compareTo(key2);
            if (comparison != 0) return comparison;
        }
        return 0;
    }
}

