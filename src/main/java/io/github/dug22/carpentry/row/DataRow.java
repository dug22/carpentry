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

package io.github.dug22.carpentry.row;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class DataRow {

    private final Map<String, Object> rowData;

    public DataRow() {
        this.rowData = new LinkedHashMap<>();
    }

    public DataRow(Map<String, Object> rowData) {
        this.rowData = rowData;
    }

    public void append(String name, Object value) {
        rowData.put(name, value);
    }

    public Map<String, Object> getRowData() {
        return rowData;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        DataRow other = (DataRow) obj;
        return this.getRowData().equals(other.getRowData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRowData());
    }
}
