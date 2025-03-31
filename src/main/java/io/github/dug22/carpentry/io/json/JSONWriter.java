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

package io.github.dug22.carpentry.io.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.io.SourceWriter;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Set;

public class JSONWriter extends SourceWriter {

    private final ColumnMap columnMap;
    private final String filePath;
    private final Set<String> columnNames;

    public JSONWriter(ColumnMap columnMap, String filePath) {
        this.columnMap = columnMap;
        this.filePath = filePath;
        this.columnNames = columnMap.keySet();
    }

    public static JSONWriter getJSONWriter(ColumnMap columnMap, String filePath) {
        return new JSONWriter(columnMap, filePath);
    }

    @Override
    public void write() {
        if (!endsWithExtension()) {
            throw new JSONException("The file path must end with '.json'!");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            DataRows jsonData = new DataRows();
            int rowCount = columnMap.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                DataRow row = new DataRow();
                for (String columnName : columnNames) {
                    Object value = columnMap.getColumn(columnName).getValues()[i];
                    row.append(columnName, value);
                }
                jsonData.add(row);
            }

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(DataRow.class, (JsonSerializer<DataRow>) (src, a, context) -> context.serialize(src.getRowData()))
                    .create();
            String jsonOutput = gson.toJson(jsonData);
            writer.write(jsonOutput);
        } catch (Exception e) {
            throw new JSONException("Unfortunately, we could not save your DataFrame as a '.json' file", e);
        }
    }

    @Override
    protected boolean endsWithExtension() {
        return filePath.toLowerCase().endsWith(".json");
    }
}
