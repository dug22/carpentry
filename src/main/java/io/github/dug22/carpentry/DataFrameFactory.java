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
import io.github.dug22.carpentry.io.csv.OptionalCSVHeaders;
import io.github.dug22.carpentry.io.csv.CSVReader;
import io.github.dug22.carpentry.io.csv.CSVReaderBuilder;

import java.nio.file.Path;

public class DataFrameFactory {

    public static DefaultDataFrame create() {
        return new DefaultDataFrame();
    }

    public static DefaultDataFrame create(ColumnMap columnMap) {
        return new DefaultDataFrame(columnMap);
    }

    public static DefaultDataFrame loadFromCSVFile(Path filePath) {
        return loadFromCSVSource(filePath, null, ",", true);
    }

    public static DefaultDataFrame loadFromCSVFile(Path filePath, String delimiter) {
        return loadFromCSVSource(filePath, null, delimiter, true);
    }

    public static DefaultDataFrame loadFromCSVFile(Path filePath, OptionalCSVHeaders headers) {
        return loadFromCSVSource(filePath, headers, ",", false);
    }

    public static DefaultDataFrame loadFromCSVFile(Path filePath, OptionalCSVHeaders headers, String delimiter) {
        return loadFromCSVSource(filePath, headers, delimiter, false);
    }

    public static DefaultDataFrame loadFromCSVUrl(String url) {
        return loadFromCSVSource(url, null, ",", true);
    }

    public static DefaultDataFrame loadFromCSVUrl(String url, String delimiter) {
        return loadFromCSVSource(url, null, delimiter, true);
    }

    public static DefaultDataFrame loadFromCSVUrl(String url, OptionalCSVHeaders headers) {
        return loadFromCSVSource(url, headers, ",", false);
    }

    public static DefaultDataFrame loadFromCSVUrl(String url, OptionalCSVHeaders headers, String delimiter) {
        return loadFromCSVSource(url, headers, delimiter, false);
    }

    public static DefaultDataFrame loadFromCSVReader(CSVReader reader) {
        try {
            return reader.load();
        } catch (Exception e) {
            throw new DataFrameException("An error occurred while loading the DataFrame.", e);
        }
    }


    private static DefaultDataFrame loadFromCSVSource(Object source, OptionalCSVHeaders headers, String delimiter, boolean inferTypes) {
        try {
            CSVReaderBuilder builder = new CSVReaderBuilder()
                    .setDelimiter(delimiter);

            if (headers != null && !inferTypes) {
                builder.setHeaders(headers);
            }

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
}

