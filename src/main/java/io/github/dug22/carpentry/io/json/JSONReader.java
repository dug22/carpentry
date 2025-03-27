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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.io.SourceReader;
import io.github.dug22.carpentry.io.TypeInference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JSONReader extends SourceReader {

    private final JSONHeaders headers;
    private final Gson gson;
    private final List<String> rowKeys;

    private JSONReader(Path filePath, String urlString, JSONHeaders headers, List<String> rowKeys) {
        super(filePath, urlString);
        this.headers = headers;
        this.gson = new Gson();
        this.rowKeys = rowKeys != null ? rowKeys : Collections.emptyList();
    }


    public static JSONReader fromFile(Path filePath, JSONHeaders headers, List<String> rowKeys) {
        return new JSONReader(filePath, null, headers, rowKeys);
    }

    public static JSONReader fromURL(String urlString, JSONHeaders headers, List<String> rowKeys) {
        return new JSONReader(null, urlString, headers, rowKeys);
    }

    @Override
    public DefaultDataFrame load() throws IOException, URISyntaxException {
        validateSource();
        if (headers == null || headers.getHeaders().isEmpty()) {
            throw new JSONException("Headers must be provided for JSON parsing");
        }

        BufferedReader reader = filePath != null
                ? Files.newBufferedReader(filePath)
                : new BufferedReader(new InputStreamReader(new URI(urlString).toURL().openStream()));

        DefaultDataFrame df = loadJSON(reader);
        reader.close();
        return df;
    }

    private DefaultDataFrame loadJSON(BufferedReader reader) {
        JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
        List<JsonObject> rows = extractRows(jsonElement);
        return buildDataFrame(rows);
    }

    private List<JsonObject> extractRows(JsonElement jsonElement) {
        List<JsonObject> rows = new ArrayList<>();
        JsonObject rootObject = jsonElement.isJsonObject() ? jsonElement.getAsJsonObject() : null;

        if (rootObject == null && !rowKeys.isEmpty()) {
            throw new JSONException("Root must be an object when rowKeys are specified");
        }

        if (rowKeys.isEmpty()) {
            extractRowsFromTarget(jsonElement, rows);
        } else {
            for (String rowPath : rowKeys) {
                JsonElement target = jsonElement;
                if (!rowPath.isEmpty()) {
                    for (String part : rowPath.split("\\.")) {
                        if (target.isJsonObject() && target.getAsJsonObject().has(part)) {
                            target = target.getAsJsonObject().get(part);
                        } else {
                            throw new JSONException("Invalid row key: " + rowPath);
                        }
                    }
                }
                extractRowsFromTarget(target, rows);
            }
        }

        if (rows.isEmpty()) {
            throw new JSONException("No valid rows found in JSON data");
        }
        return rows;
    }

    private void extractRowsFromTarget(JsonElement target, List<JsonObject> rows) {
        if (target.isJsonArray()) {
            JsonArray jsonArray = target.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                if (element.isJsonObject()) {
                    rows.add(element.getAsJsonObject());
                }
            }
        } else if (target.isJsonObject()) {
            JsonObject jsonObject = target.getAsJsonObject();
            for (String key : jsonObject.keySet()) {
                JsonElement element = jsonObject.get(key);
                if (element.isJsonObject()) {
                    JsonObject row = element.getAsJsonObject();
                    if (headers.getHeaders().stream().anyMatch(h -> h.headerName().equals("key"))) {
                        row.addProperty("key", key);
                    }
                    rows.add(row);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void appendRowFromObject(JsonObject jsonObject, ColumnMap columnMap) {
        for (JSONHeader header : headers.getHeaders()) {
            String headerName = header.headerName();
            JsonElement valueElement = getNestedValue(jsonObject, headerName);
            if (valueElement != null) {
                String valueString = valueElement.isJsonNull() ? null : valueElement.toString().replace("\"", "");
                AbstractColumn<Object> column = (AbstractColumn<Object>) columnMap.getColumn(headerName);
                Object value = TypeInference.parseValue(valueString, header.headerType());
                column.append(value);
            }
        }
    }

    private JsonElement getNestedValue(JsonObject jsonObject, String path) {
        String[] parts = path.split("\\.");
        JsonElement current = jsonObject;
        for (String part : parts) {
            if (current.isJsonObject() && current.getAsJsonObject().has(part)) {
                current = current.getAsJsonObject().get(part);
            } else {
                return null;
            }
        }
        return current;
    }

    private DefaultDataFrame buildDataFrame(List<JsonObject> rows) {
        ColumnMap columnMap = new ColumnMap();
        for (JSONHeader header : headers.getHeaders()) {
            Class<?> columnType = header.headerType();
            AbstractColumn<?> column = TypeInference.createColumnInstance(header.headerName(), columnType);
            columnMap.addColumn(column);
        }

        for (JsonObject row : rows) {
            appendRowFromObject(row, columnMap);
        }

        return DefaultDataFrame.create(columnMap);
    }
}