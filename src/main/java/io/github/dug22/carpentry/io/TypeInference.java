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

import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.columns.*;
import io.github.dug22.carpentry.io.type.*;

import java.util.List;

public class TypeInference {

    //Default Column Types
    private static final ColumnType<?>[] DEFAULT_COLUMN_TYPES = {
            new BooleanColumnType(),
            new IntegerColumnType(),
            new LongColumnType(),
            new DoubleColumnType(),
            new ByteColumnType(),
            new StringColumnType()
    };


    /**
     * Parses a string into a typed object (Boolean, Double, Integer, or String).
     * Returns null for NA-like values.
     *
     * @param value string to parse
     * @param type  target type
     * @return parsed object or null
     */
    public static Object parseValue(String value, Class<?> type) {
        if (value == null || value.isEmpty() || value.equals("NA") || value.equals("NaN") ||
                value.equals("na") || value.equals("Na") || value.equals("*") || value.equals("none") ||
                value.equals("-") || value.equals("undefined")) {
            return null;
        }
        if (type == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type == Character.class) {
            return value.charAt(0);
        } else if (type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == Float.class) {
            return Float.parseFloat(value);
        } else if (type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == Long.class) {
            return Long.parseLong(value);
        } else if (type == Number.class) {
            return Double.parseDouble(value);
        } else if (type == Short.class) {
            return Short.parseShort(value);
        } else {
            return value;
        }
    }

    /**
     * Creates a column instance of the given type and name.
     * Defaults to String if type not supported.
     *
     * @param name column name
     * @param type column type
     * @return new column instance
     */
    public static AbstractColumn<?> createColumnInstance(String name, Class<?> type) {
        if (type == Boolean.class) return BooleanColumn.create(name);
        if (type == Byte.class) return ByteColumn.create(name);
        if (type == CharacterColumn.class) return CharacterColumn.create(name);
        if (type == Double.class) return DoubleColumn.create(name);
        if (type == Float.class) return FloatColumn.create(name);
        if (type == Integer.class) return IntegerColumn.create(name);
        if (type == Long.class) return LongColumn.create(name);
        if (type == NumberColumn.class) return NumberColumn.create(name);
        if (type == ShortColumn.class) return ShortColumn.create(name);
        return StringColumn.create(name);
    }

    /**
     * Infers column types from CSV rows using default types.
     *
     * @param rows        CSV rows
     * @param columnCount number of columns
     * @return inferred column types
     */
    public static ColumnType<?>[] inferColumnTypes(List<String[]> rows, int columnCount) {
        ColumnType<?>[] columnTypes = new ColumnType[columnCount];

        for (int i = 0; i < columnCount; i++) {
            for (ColumnType<?> type : DEFAULT_COLUMN_TYPES) {
                boolean canParseAll = true;
                for (String[] row : rows) {
                    if (i >= row.length) continue;
                    String value = row[i].trim();
                    if (!type.canParse(value)) {
                        canParseAll = false;
                        break;
                    }
                }
                if (canParseAll) {
                    columnTypes[i] = type;
                    break;
                }
            }
        }

        return columnTypes;
    }
}
