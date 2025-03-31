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

package io.github.dug22.carpentry.column.strings;

import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.columns.StringColumn;

public interface StringMapFunctions {

    int size();

    String get(int index);

    String getColumnName();

    /**
     * Creates a new StringColumn with each string capitalized (first letter uppercase, rest lowercase).
     */
    default StringColumn capitalize() {
        StringColumn newStringColumn = StringColumn.create(getColumnName() + " [capitalized]");
        for (int i = 0; i < size(); i++) {
            String value = get(i).substring(0, 1).toUpperCase() + get(i).substring(1).toLowerCase();
            newStringColumn.append(value);
        }
        return newStringColumn;
    }

    /**
     * Creates a new StringColumn with all strings converted to uppercase.
     */
    default StringColumn upperCase() {
        StringColumn newStringColumn = StringColumn.create(getColumnName() + " [ucase]");
        for (int i = 0; i < size(); i++) {
            String value = get(i).toUpperCase();
            newStringColumn.append(value);
        }
        return newStringColumn;
    }

    /**
     * Creates a new StringColumn with all strings converted to lowercase.
     */
    default StringColumn lowerCase() {
        StringColumn newStringColumn = StringColumn.create(getColumnName() + " [lcase]");
        for (int i = 0; i < size(); i++) {
            String value = get(i).toLowerCase();
            newStringColumn.append(value);
        }
        return newStringColumn;
    }

    /**
     * Creates a new StringColumn with each string repeated a specified number of times.
     */
    default StringColumn repeat(int times) {
        StringColumn newStringColumn = StringColumn.create(String.format("%s [rep %d]", getColumnName(), times));
        for (int i = 0; i < size(); i++) {
            String value = get(i);
            newStringColumn.append(value.repeat(times));
        }
        return newStringColumn;
    }

    /**
     * Creates a new StringColumn with leading and trailing whitespace removed from each string.
     */
    default StringColumn trim() {
        StringColumn newStringColumn = StringColumn.create(getColumnName() + " [trim]");
        for (int i = 0; i < size(); i++) {
            String value = get(i).trim();
            newStringColumn.append(value);
        }
        return newStringColumn;
    }

    /**
     * Creates a new StringColumn by joining this column's strings with others using a separator.
     */
    default StringColumn join(String separator, AbstractColumn<?>... columns) {
        StringColumn newStringColumn = StringColumn.create(getColumnName() + " [column appended]");
        int size = size();
        for (AbstractColumn<?> column : columns) {
            if (column.size() != size) {
                throw new IllegalArgumentException("All columns must have the same size.");
            }
        }
        for (int i = 0; i < size; i++) {
            StringBuilder result = new StringBuilder(get(i));
            for (AbstractColumn<?> stringColumn : columns) {
                result.append(separator).append(stringColumn.get(i));
            }
            newStringColumn.append(result.toString());
        }
        return newStringColumn;
    }

    /**
     * Creates a new StringColumn with the first occurrence of a regex pattern replaced in each string.
     */
    default StringColumn replaceFirst(String reg, String replacement) {
        StringColumn newStringColumn = StringColumn.create(getColumnName() + " [repl]");
        for (int i = 0; i < size(); i++) {
            String value = get(i);
            newStringColumn.append(value.replaceFirst(reg, replacement));
        }
        return newStringColumn;
    }

    /**
     * Creates a new StringColumn with all occurrences of a regex pattern replaced in each string.
     */
    default StringColumn replaceAll(String reg, String replacement) {
        StringColumn newStringColumn = StringColumn.create(getColumnName() + " [repl]");
        for (int i = 0; i < size(); i++) {
            String value = get(i);
            newStringColumn.append(value.replaceAll(reg, replacement));
        }
        return newStringColumn;
    }

    /**
     * Creates a new StringColumn with each string truncated to a maximum width, appending '...' if needed.
     */
    default StringColumn truncate(int maxWidth) {
        final String defaultTruncatedValue = "...";
        StringColumn newStringColumn = StringColumn.create(getColumnName() + " [truncate]");
        for (int i = 0; i < size(); i++) {
            String value = get(i);
            if (value != null && value.length() > maxWidth) {
                int safeLength = maxWidth - defaultTruncatedValue.length();
                if (safeLength > 0) {
                    newStringColumn.append(value.substring(0, safeLength) + defaultTruncatedValue);
                } else {
                    newStringColumn.append(defaultTruncatedValue);
                }
            } else {
                newStringColumn.append(value);
            }
        }
        return newStringColumn;
    }

    /**
     * Creates a new StringColumn with each string extracted from a start index to the end.
     */
    default StringColumn substring(int start) {
        StringColumn newStringColumn = StringColumn.create(getColumnName() + " [sub]");
        for (int i = 0; i < size(); i++) {
            String value = get(i);
            newStringColumn.append(value.substring(start));
        }
        return newStringColumn;
    }

    /**
     * Creates a new StringColumn with each string extracted between a start and end index.
     */
    default StringColumn substring(int start, int end) {
        StringColumn newStringColumn = StringColumn.create(getColumnName() + " [sub]");
        for (int i = 0; i < size(); i++) {
            String value = get(i);
            newStringColumn.append(value.substring(start, end));
        }
        return newStringColumn;
    }
}
