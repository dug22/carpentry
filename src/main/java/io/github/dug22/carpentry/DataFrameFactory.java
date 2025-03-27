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

package io.github.dug22.carpentry;

import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.io.csv.CSVHeaders;
import io.github.dug22.carpentry.io.csv.CSVReader;
import io.github.dug22.carpentry.io.csv.CSVReaderBuilder;
import io.github.dug22.carpentry.io.json.JSONHeaders;
import io.github.dug22.carpentry.io.json.JSONReader;
import io.github.dug22.carpentry.io.json.JSONReaderBuilder;

import java.nio.file.Path;
import java.util.List;

public class DataFrameFactory {

    public static DefaultDataFrame create() {
        return new DefaultDataFrame();
    }

    public static DefaultDataFrame create(ColumnMap columnMap) {
        return new DefaultDataFrame(columnMap);
    }

    public static DefaultDataFrame loadFromCSVFile(Path filePath, CSVHeaders headers) {
        return loadFromCSVFile(filePath, headers, ",");
    }

    public static DefaultDataFrame loadFromCSVFile(Path filePath, CSVHeaders headers, String delimiter) {
        return loadFromCSVSource(filePath, headers, delimiter);
    }

    public static DefaultDataFrame loadFromCSVUrl(String url, CSVHeaders headers) {
        return loadFromCSVUrl(url, headers, ",");
    }

    public static DefaultDataFrame loadFromCSVUrl(String url, CSVHeaders headers, String delimiter) {
        return loadFromCSVSource(url, headers, delimiter);
    }

    public static DefaultDataFrame loadFromCSVReader(CSVReader reader) {
        try {
            return reader.load();
        } catch (Exception e) {
            throw new DataFrameException("An error occurred while loading the DataFrame.", e);
        }
    }

    public static DefaultDataFrame loadFromJSONFile(Path filePath, JSONHeaders headers) {
        return loadFromJSONFile(filePath, headers, null);
    }

    public static DefaultDataFrame loadFromJSONFile(Path filePath, JSONHeaders headers, List<String> rowKeys) {
        return loadFromJSONSource(filePath, headers, rowKeys);
    }

    public static DefaultDataFrame loadFromJSONUrl(String url, JSONHeaders headers) {
        return loadFromJSONUrl(url, headers, null);
    }

    public static DefaultDataFrame loadFromJSONUrl(String url, JSONHeaders headers, List<String> rowKeys) {
        return loadFromJSONSource(url, headers, rowKeys);
    }

    public static DefaultDataFrame loadFromJSONReader(JSONReader reader){
        try{
            return reader.load();
        }catch (Exception e){
            throw new DataFrameException("An error occurred while loading the DataFrame.", e);
        }
    }

    private static DefaultDataFrame loadFromCSVSource(Object source, CSVHeaders headers, String delimiter) {
        try {
            CSVReaderBuilder builder = new CSVReaderBuilder()
                    .setHeaders(headers)
                    .setDelimiter(delimiter);

            if (source instanceof Path) {
                builder.setFilePath((Path) source);
            } else if (source instanceof String) {
                builder.setURL((String) source);
            } else {
                throw new IllegalArgumentException("Unsupported source type.");
            }

            CSVReader reader = builder.build();
            return reader.load();
        } catch (Exception e) {
            throw new DataFrameException("An error occurred while creating the DataFrame.", e);
        }
    }

    private static DefaultDataFrame loadFromJSONSource(Object source, JSONHeaders headers, List<String> rowKeys) {
        try {
            JSONReaderBuilder builder = new JSONReaderBuilder()
                    .setHeaders(headers);

            if (rowKeys != null) {
                builder.setRowKeys(rowKeys);
            }

            if (source instanceof Path) {
                builder.setFilePath((Path) source);
            } else if (source instanceof String) {
                builder.setURL((String) source);
            } else {
                throw new IllegalArgumentException("Unsupported source type: " + source.getClass().getName());
            }

            JSONReader reader = builder.build();
            return reader.load();
        } catch (Exception e) {
            throw new DataFrameException("An error occurred while creating the DataFrame from JSON source.", e);
        }
    }
}