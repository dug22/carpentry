/*
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *
 */

package io.github.dug22.carpentry.io.csv;

import io.github.dug22.carpentry.DataFrameInterface;
import io.github.dug22.carpentry.column.Column;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static io.github.dug22.carpentry.column.ColumnUtils.format;

public class CsvWritingUtil {

    /**
     * Writes the header row to the CSV if headers are enabled in properties.
     * @param dataFrame the DataFrame to write
     * @param properties the CSV writing properties
     * @param writer the output writer
     * @throws IOException if writing fails
     */
    protected static void writeHeaders(DataFrameInterface dataFrame, CsvWritingProperties properties, Writer writer) throws IOException {
        List<String> columnNames = dataFrame.columnNames();
        if (properties.doesIncludeHeaders()) {
            insertRow(writer, columnNames, null, properties);
        }
    }

    /**
     * Writes all data rows from the DataFrame to the CSV.
     * @param dataFrame the DataFrame to write
     * @param properties the CSV writing properties
     * @param writer the output writer
     * @throws IOException if writing fails
     */
    protected static void writeDataValues(DataFrameInterface dataFrame, CsvWritingProperties properties, Writer writer) throws IOException {
        int rowCount = dataFrame.getRowCount();
        List<Column<?>> columns = dataFrame.getColumns().stream().toList();
        for(int index = 0; index < rowCount; index++){
            List<Object> row = new ArrayList<>(columns.size());
            for(Column<?> column : columns){
                row.add(column.get(index));
            }
            insertRow(writer, row, columns, properties);
        }
    }

    /**
     * Writes a single row to the CSV, formatting values and applying quotes if necessary.
     * @param writer the output writer
     * @param row the row values to write
     * @param columns optional list of columns for formatting (null for header row)
     * @param properties the CSV writing properties
     * @throws IOException if writing fails
     */
    protected static void insertRow(Writer writer, List<?> row, List<Column<?>> columns, CsvWritingProperties properties) throws IOException {
        char delimiter = properties.getDelimiter();
        char quoteCharacter = properties.getQuoteCharacter();
        String lineSeparator = properties.getLineSeparator();
        for (int index = 0; index < row.size(); index++) {
            Object value = row.get(index);
            String stringValue = (columns != null) ? format(value, columns.get(index)) : String.valueOf(value);
            if (doesValueNeedQuotes(stringValue, delimiter)) {
                String escapedQuoteValue = stringValue.replace(String.valueOf(quoteCharacter), String.valueOf(quoteCharacter) + quoteCharacter);
                writer.write(quoteCharacter);
                writer.write(escapedQuoteValue);
                writer.write(quoteCharacter);
            } else {
                writer.write(stringValue);
            }

            if (index < row.size() - 1) {
                writer.write(delimiter);
            }
        }
        writer.write(lineSeparator);
    }


    /**
     * Determines if a value needs to be quoted in the CSV output.
     * @param value the string value
     * @param delimiter the delimiter character
     * @return true if the value should be quoted, false otherwise
     */
    private static boolean doesValueNeedQuotes(String value, char delimiter) {
        return value.contains(String.valueOf(delimiter)) ||
                value.contains("\"") ||
                value.contains("\n") ||
                value.contains("\r");
    }
}
