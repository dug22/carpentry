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

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.DataFrameInterface;
import io.github.dug22.carpentry.column.*;
import io.github.dug22.carpentry.column.impl.*;
import io.github.dug22.carpentry.column.type.*;
import io.github.dug22.carpentry.io.AbstractDataSource;
import io.github.dug22.carpentry.io.ColumnTypeDetector;
import io.github.dug22.carpentry.io.exceptions.CsvException;
import io.github.dug22.carpentry.utils.Nulls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvReadingUtil {

    private static final int BUFFER_SIZE = 32768;

    /**
     * Loads a CSV into a DataFrame using the specified reading properties.
     * @param properties the CSV reading configuration
     * @return a DataFrame containing the parsed data
     * @throws IOException if an I/O error occurs
     */
    protected static DataFrameInterface loadCsv(CsvReadingProperties properties) throws IOException {
        AbstractDataSource source = properties.getSource();
        char delimiter = properties.getDelimiter();
        char quoteCharacter = properties.getQuoteCharacter();
        char escapeCharacter = properties.getEscapeCharacter();
        boolean hasHeaders = properties.hasHeaders();
        boolean allowDuplicateColumnNames = properties.allowDuplicateColumnNames();
        int maxColumnCharacterLength = properties.getMaxColumnCharacterLength();

        try (Reader reader = source.getReader(); BufferedReader bufferedReader = new BufferedReader(reader, BUFFER_SIZE)) {
            String[] headers = readAndProcessHeaders(bufferedReader, delimiter, quoteCharacter, escapeCharacter,
                    maxColumnCharacterLength, hasHeaders, allowDuplicateColumnNames);
            List<String[]> rows = readRows(bufferedReader, delimiter, quoteCharacter, escapeCharacter,
                    maxColumnCharacterLength, headers.length);
            ColumnType[] columnTypes = detectColumnTypes(rows, headers.length);
            List<Column<?>> columns = buildColumns(headers, columnTypes, rows, properties);
            return DataFrame.create(columns);
        }
    }

    /**
     * Reads and processes the header line of the CSV file.
     * @return an array of column headers
     */
    private static String[] readAndProcessHeaders(
            BufferedReader bufferedReader,
            char delimiter,
            char quoteCharacter,
            char escapeCharacter,
            int maxColumnCharacterLength,
            boolean hasHeaders,
            boolean allowDuplicateColumnNames
    ) throws IOException {
        String headerLine = bufferedReader.readLine();
        String[] headers = splitLine(headerLine, delimiter, quoteCharacter, escapeCharacter, maxColumnCharacterLength);
        if (!hasHeaders) {
            headers = generateDefaultHeaders(headers.length);
        } else if (!allowDuplicateColumnNames) {
            validateUniqueHeaders(headers);
        }
        return headers;
    }

    /**
     * Reads all remaining rows in the CSV after the headers.
     * @return list of row value arrays
     */
    private static List<String[]> readRows(
            BufferedReader bufferedReader,
            char delimiter,
            char quoteCharacter,
            char escapeCharacter,
            int maxColumnCharacterLength,
            int headerCount
    ) throws IOException {
        List<String[]> rows = new ArrayList<>();
        String line;
        StringBuilder buffer = new StringBuilder();
        boolean inQuotes;
        List<String> rowValues = new ArrayList<>();
        int colIndex;

        while ((line = bufferedReader.readLine()) != null) {
            rowValues.clear();
            buffer.setLength(0);
            inQuotes = false;
            colIndex = 0;

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == quoteCharacter && (i == 0 || line.charAt(i - 1) != escapeCharacter)) {
                    inQuotes = !inQuotes;
                    continue;
                }
                if (c == delimiter && !inQuotes) {
                    String value = buffer.toString();
                    if (value.length() > maxColumnCharacterLength) {
                        value = value.substring(0, maxColumnCharacterLength) + "...";
                    }
                    rowValues.add(value.isEmpty() ? null : value);
                    buffer.setLength(0);
                    colIndex++;
                } else {
                    buffer.append(c);
                }
            }

            String value = buffer.toString();
            if (value.length() > maxColumnCharacterLength) {
                throw new CsvException("Value in column " + colIndex + " exceeds max length of " + maxColumnCharacterLength);
            }
            rowValues.add(value.isEmpty() ? null : value);

            while (rowValues.size() < headerCount) {
                rowValues.add(null);
            }
            rows.add(rowValues.toArray(new String[0]));
        }

        return rows;
    }

    /**
     * Splits a single line into values based on delimiter and quote/escape rules.
     * @return an array of split values
     */
    private static String[] splitLine(String line, char delimiter, char quoteCharacter, char escapeCharacter, int maxColumnCharLength) {
        List<String> result = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        boolean isSurroundedInQuotes = false;
        int lineLength = line.length();

        for (int index = 0; index < lineLength; index++) {
            char character = line.charAt(index);
            if (character == quoteCharacter && (index == 0 || line.charAt(index - 1) != escapeCharacter)) {
                isSurroundedInQuotes = !isSurroundedInQuotes;
            }
            if (character == delimiter && !isSurroundedInQuotes) {
                String value = buffer.toString();
                int valueLength = value.length();
                if (valueLength > maxColumnCharLength) {
                    value = value.substring(0, maxColumnCharLength - 1);
                }
                result.add(value.trim());
                buffer.setLength(0);
            } else {
                buffer.append(character);
            }
        }

        String value = buffer.toString();
        if (value.length() > maxColumnCharLength) {
            value = value.substring(0, maxColumnCharLength - 1);
        }
        result.add(value.trim());

        return result.toArray(new String[0]);
    }

    /**
     * Generates default headers if CSV has no header row.
     * @param count the number of columns
     * @return default header names
     */
    private static String[] generateDefaultHeaders(int count) {
        String[] headers = new String[count];
        for (int i = 0; i < count; i++) {
            headers[i] = "column_" + i;
        }
        return headers;
    }

    /**
     * Ensures all column headers are unique.
     * @param headers the column headers to check
     */
    private static void validateUniqueHeaders(String[] headers) {
        List<String> seen = new ArrayList<>();
        for (String header : headers) {
            if (seen.contains(header)) {
                throw new CsvException("Duplicate column name detected: " + header);
            }
            seen.add(header);
        }
    }

    /**
     * Detects the data type of each column based on CSV row values.
     * @param rows the data rows
     * @param headerCount number of columns
     * @return array of detected column types
     */
    private static ColumnType[] detectColumnTypes(List<String[]> rows, int headerCount) {
        return ColumnTypeDetector.detectColumnTypes(rows, headerCount);
    }

    /**
     * Builds a list of typed columns from the parsed CSV data.
     * @param headers the column headers
     * @param columnTypes the detected column types
     * @param rows the data rows
     * @param properties CSV reading configuration
     * @return list of constructed columns
     */
    private static List<Column<?>> buildColumns(String[] headers, ColumnType[] columnTypes, List<String[]> rows, CsvReadingProperties properties) {
        List<Column<?>> columns = new ArrayList<>();
        for (int i = 0; i < headers.length; i++) {
            columns.add(buildColumn(headers[i], columnTypes[i], rows, i, properties));
        }
        return columns;
    }

    /**
     * Builds a single column of a given type from row data.
     * @param columnName the column name
     * @param columnType the detected type
     * @param rows the row data
     * @param columnIndex index of the column in the row
     * @param properties reading configuration
     * @return constructed column
     */
    private static Column<?> buildColumn(String columnName, ColumnType columnType, List<String[]> rows, int columnIndex, CsvReadingProperties properties) {
        return switch (columnType) {
            case DateColumnType ignored -> {
                DateColumn column = (DateColumn) buildColumn(
                        columnName, rows, columnIndex,
                        value -> ColumnTypeDetector.parseValue(value, columnType),
                        LocalDate[]::new,
                        DateColumn::create
                );
                column.setParser(properties.getDateParser(columnName));
                column.setOutputFormatter(properties.getDateFormatter(columnName));
                yield column;
            }
            case DateTimeColumnType ignored -> {
                DateTimeColumn column = (DateTimeColumn)
                        buildColumn(
                                columnName, rows, columnIndex,
                                value -> ColumnTypeDetector.parseValue(value, columnType),
                                LocalDateTime[]::new,
                                DateTimeColumn::create
                        );
                column.setParser(properties.getDateTimeParser(columnName));
                column.setOutputFormatter(properties.getDateTimeFormatter(columnName));
                yield column;
            }
            case BooleanColumnType ignored -> buildColumn(
                    columnName, rows, columnIndex,
                    value -> ColumnTypeDetector.parseValue(value, columnType),
                    Boolean[]::new,
                    BooleanColumn::create
            );
            case IntegerColumnType ignored -> buildColumn(
                    columnName, rows, columnIndex,
                    value -> ColumnTypeDetector.parseValue(value, columnType),
                    Integer[]::new,
                    IntegerColumn::create
            );
            case DoubleColumnType ignored -> buildColumn(
                    columnName, rows, columnIndex,
                    value -> ColumnTypeDetector.parseValue(value, columnType),
                    Double[]::new,
                    DoubleColumn::create
            );
            case LongColumnType ignored -> buildColumn(
                    columnName, rows, columnIndex,
                    value -> ColumnTypeDetector.parseValue(value, columnType),
                    Long[]::new,
                    LongColumn::create
            );
            default -> buildColumn(
                    columnName, rows, columnIndex,
                    value -> ColumnTypeDetector.parseValue(value, columnType),
                    String[]::new,
                    StringColumn::create
            );
        };
    }

    /**
     * Builds a generic column of type T using parsing and factory functions.
     * @param columnName the column name
     * @param rows the data rows
     * @param columnIndex index of the column
     * @param parser function to parse a string value into type T
     * @param dataFunction function to create array of type T
     * @param columnCreator function to create a column from data
     * @return constructed column
     * @param <T> the column's data type
     */
    private static <T> Column<?> buildColumn(
            String columnName,
            List<String[]> rows,
            int columnIndex,
            ColumnFunction<String, T> parser,
            ColumnIntFunction<T[]> dataFunction,
            ColumnBiFunction<String, T[], Column<?>> columnCreator
    ) {
        T[] data = dataFunction.apply(rows.size());
        for (int index = 0; index < rows.size(); index++) {
            String[] row = rows.get(index);
            String value = columnIndex < row.length ? row[columnIndex] : null;
            if (!Nulls.isNull(value)) {
                data[index] = parser.apply(value);
            } else {
                data[index] = Nulls.getDefaultNullValue((Class<T>) data.getClass().getComponentType());
            }
        }
        return columnCreator.apply(columnName, data);
    }
}