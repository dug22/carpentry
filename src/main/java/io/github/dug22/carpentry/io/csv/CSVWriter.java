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

package io.github.dug22.carpentry.io.csv;

import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.io.SourceWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CSVWriter extends SourceWriter {

    private final ColumnMap columnMap;
    private final String filePath;
    private final String delimiter;
    private final Set<String> columnNames;

    public CSVWriter(ColumnMap columnMap, String filePath, String delimiter) {
        this.columnMap = columnMap;
        this.filePath = filePath;
        this.delimiter = delimiter;
        this.columnNames = columnMap.keySet();
    }

    public static CSVWriter getCSVWriter(ColumnMap columnMap, String filePath) {
        return new CSVWriter(columnMap, filePath, ",");
    }

    public static CSVWriter getCSVWriter(ColumnMap columnMap, String filePath, String delimiter) {
        return new CSVWriter(columnMap, filePath, delimiter);
    }

    @Override
    public void write() {
        if (!endsWithExtension()) {
            throw new CSVException("The file path must end with '.csv'!");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.join(delimiter, columnNames));
            writer.newLine();
            for (int i = 0; i < columnMap.getRowCount(); i++) {
                List<String> rowValues = new ArrayList<>();
                for (String columnName : columnNames) {
                    Object value = columnMap.getColumn(columnName).getValues()[i];
                    rowValues.add(formatCsvValue(value));
                }

                writer.write(String.join(delimiter, rowValues));
                writer.newLine();
            }
        } catch (Exception e) {
            throw new CSVException("Unfortunately, we could not save your DataFrame as '.csv' file");
        }
    }

    @Override
    protected boolean endsWithExtension() {
        return filePath.toLowerCase().endsWith(".csv");
    }

    private String formatCsvValue(Object value) {
        if (value == null) {
            return "";
        }
        String stringValue = value.toString();
        if (stringValue.contains(",") || stringValue.contains("\"") || stringValue.contains("\n")) {
            return "\"" + stringValue.replace("\"", "\"\"") + "\"";
        }
        return stringValue;
    }
}
