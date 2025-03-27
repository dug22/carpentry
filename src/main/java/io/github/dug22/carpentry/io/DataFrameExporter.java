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

package io.github.dug22.carpentry.io;

import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.io.csv.CSVWriter;
import io.github.dug22.carpentry.io.json.JSONWriter;

public class DataFrameExporter {

    /**
     * Saves a DataFrame as a CSV file with default delimiter.
     * @param columnMap the current columnMap
     * @param filePath the path where the CSV file will be saved
     */
    public static void toCsv(ColumnMap columnMap, String filePath) {
        CSVWriter.getCSVWriter(columnMap, filePath).write();
    }

    /**
     * Saves a DataFrame as a CSV file with a custom delimiter.
     * @param columnMap the current columnMap
     * @param filePath the path where the CSV file will be saved
     * @param delimiter the delimiter to use in the CSV file
     */
    public static void toCsv(ColumnMap columnMap, String filePath, String delimiter) {
        CSVWriter.getCSVWriter(columnMap, filePath, delimiter).write();
    }

    /**
     * Saves a DataFrame as a JSON file.
     * @param columnMap the current columnMap
     * @param filePath the path where the JSON file will be saved
     */
    public static void toJson(ColumnMap columnMap, String filePath) {
        JSONWriter.getJSONWriter(columnMap, filePath).write();
    }
}
