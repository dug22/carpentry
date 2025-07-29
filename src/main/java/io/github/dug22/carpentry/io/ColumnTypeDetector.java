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

package io.github.dug22.carpentry.io;

import io.github.dug22.carpentry.column.ColumnType;
import io.github.dug22.carpentry.column.ColumnTypes;
import io.github.dug22.carpentry.column.type.*;

import java.util.List;

public class ColumnTypeDetector {

    private static final ColumnType[] DEFAULT_COLUMN_TYPES = {
            ColumnTypes.DATE_COLUMN_TYPE,
            ColumnTypes.DATE_TIME_COLUMN_TYPE,
            ColumnTypes.BOOLEAN_COLUMN_TYPE,
            ColumnTypes.INTEGER_COLUMN_TYPE,
            ColumnTypes.DOUBLE_COLUMN_TYPE,
            ColumnTypes.LONG_COLUMN_TYPE,
            ColumnTypes.STRING_COLUMN_TYPE
    };

    /**
     * Checks if a given string value can be parsed as the specified column type.
     * @param value the string value to check
     * @param columnType the column type to test parsing against
     * @return true if the value can be parsed as the column type, false otherwise
     */
    private static boolean canParse(String value, ColumnType columnType) {
        return switch (columnType) {
            case DateColumnType dateColumnType -> dateColumnType.getParser().canParse(value);
            case DateTimeColumnType dateTimeColumnType -> dateTimeColumnType.getParser().canParse(value);
            case BooleanColumnType booleanColumnType -> booleanColumnType.getParser().canParse(value);
            case IntegerColumnType integerColumnType -> integerColumnType.getParser().canParse(value);
            case DoubleColumnType doubleColumnType -> doubleColumnType.getParser().canParse(value);
            case LongColumnType longColumnType -> longColumnType.getParser().canParse(value);
            case StringColumnType ignored ->  true;
            default -> throw new IllegalArgumentException("Invalid Column Type: " + columnType);
        };
    }

    /**
     * Parses a string value into an object of the specified column type.
     * @param value the string value to parse
     * @param columnType the column type to parse the value into
     * @param <T> the generic return type corresponding to the column type
     * @return the parsed value as an instance of T
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseValue(String value, ColumnType columnType) {
        return switch (columnType) {
            case DateColumnType dateColumnType -> (T) dateColumnType.getParser().parse(value);
            case DateTimeColumnType dateTimeColumnType -> (T) dateTimeColumnType.getParser().parse(value);
            case BooleanColumnType booleanColumnType -> (T) booleanColumnType.getParser().parse(value);
            case IntegerColumnType integerColumnType -> (T) integerColumnType.getParser().parse(value);
            case DoubleColumnType doubleColumnType -> (T) doubleColumnType.getParser().parse(value);
            case LongColumnType longColumnType -> (T) longColumnType.getParser().parse(value);
            case StringColumnType ignored -> (T) value;
            default -> throw new IllegalArgumentException("Invalid Column Type: " + columnType);
        };
    }

    /**
     * Detects the best fitting column type for a single column given all row data.
     * @param rows the list of rows containing column data
     * @param index the index of the column to analyze
     * @return the detected column type for this column
     */
    private static ColumnType detectTypeForColumn(List<String[]> rows, int index) {
        for (ColumnType columnType : DEFAULT_COLUMN_TYPES) {
            boolean allValuesParseable = true;
            for (String[] row : rows) {
                if (index < row.length && row[index] != null && !row[index].isEmpty()) {
                    if (!canParse(row[index], columnType)) {
                        allValuesParseable = false;
                        break;
                    }
                }
            }
            if (allValuesParseable) {
                return columnType;
            }
        }
        return ColumnTypes.STRING_COLUMN_TYPE;
    }

    /**
     * Detects column types for all columns in the given dataset.
     * @param rows the data rows to analyze
     * @param columnCount the number of columns to detect types for
     * @return an array of detected column types, one for each column
     */
    public static ColumnType[] detectColumnTypes(List<String[]> rows, int columnCount) {
        ColumnType[] columnTypes = new ColumnType[columnCount];
        for (int index = 0; index < columnCount; index++) {
            columnTypes[index] = detectTypeForColumn(rows, index);
        }
        return columnTypes;
    }

    /**
     * Returns the default set of column types used for detection.
     * @return an array of default column types
     */
    public static ColumnType[] getDefaultColumnTypes() {
        return DEFAULT_COLUMN_TYPES;
    }
}