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

package io.github.dug22.carpentry.column.transformations;

import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.column.impl.StringColumn;

public interface StringTransformations extends Transformation {

    String get(int index);

    /**
     * Converts all strings in the column to uppercase.
     * @return a new StringColumn with uppercase values
     */
    default StringColumn uppercase() {
        StringColumn newStringColumn = StringColumn.create(name() + " (uppercase)");
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                newStringColumn.appendNull();
            } else {
                String value = get(index);
                newStringColumn.append(value.toUpperCase());
            }
        }

        return newStringColumn;
    }

    /**
     * Converts all strings in the column to lowercase.
     * @return a new StringColumn with lowercase values
     */
    default StringColumn lowercase() {
        StringColumn newStringColumn = StringColumn.create(name() + " (lowercase)");
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                newStringColumn.appendNull();
            } else {
                String value = get(index);
                newStringColumn.append(value.toLowerCase());
            }
        }

        return newStringColumn;
    }

    /**
     * Capitalizes the first letter of each word in the strings.
     * @return a new StringColumn with capitalized words
     */
    default StringColumn capitalize() {
        StringColumn newStringColumn = StringColumn.create(name() + " (capitalized)");
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                newStringColumn.appendNull();
            } else {
                String value = get(index);
                String[] words = value.split("\\s+");
                StringBuilder capitalized = new StringBuilder();
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    capitalized.append(Character.toUpperCase(word.charAt(0)))
                            .append(word.length() > 1 ? word.substring(1).toLowerCase() : "");
                    if (i < words.length - 1) {
                        capitalized.append(" ");
                    }
                }
                newStringColumn.append(capitalized.toString());
            }
        }

        return newStringColumn;
    }

    /**
     * Repeats each string in the column n times.
     * @param n number of repetitions
     * @return a new StringColumn with repeated strings
     */
    default StringColumn repeat(int n) {
        StringColumn newStringColumn = StringColumn.create(String.format("%s (repeat %d)", name(), n));
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                newStringColumn.appendNull();
            } else {
                String value = get(index);
                newStringColumn.append(value.repeat(n));
            }
        }

        return newStringColumn;
    }

    /**
     * Trims whitespace from both ends of each string.
     * @return a new StringColumn with trimmed strings
     */
    default StringColumn trim() {
        StringColumn newStringColumn = StringColumn.create(name() + " (trim)");
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                newStringColumn.appendNull();
            } else {
                String value = get(index);
                newStringColumn.append(value.trim());
            }
        }

        return newStringColumn;
    }

    /**
     * Concatenates the current string with values from other columns, separated by a given string.
     * @param separator the string to insert between joined values
     * @param columns other columns to join
     * @return a new StringColumn with concatenated strings
     */
    default StringColumn join(String separator, Column<?>... columns) {
        StringColumn newStringColumn = StringColumn.create(name() + " (column appended)");
        for (Column<?> column : columns) {
            if (column.size() != size()) {
                throw new IllegalArgumentException("All columns must have the same size!");
            }
        }

        for (int index = 0; index < size(); index++) {
            StringBuilder result = new StringBuilder(get(index));
            for (Column<?> column : columns) {
                result.append(separator).append(column.get(index));
            }

            newStringColumn.append(result.toString());
        }

        return newStringColumn;
    }

    /**
     * Replaces the first substring matching the regex with a replacement string.
     * @param reg the regex pattern
     * @param replacement the replacement string
     * @return a new StringColumn with replacements
     */
    default StringColumn replaceFirst(String reg, String replacement) {
        StringColumn newStringColumn = StringColumn.create(name() + " (replace_first)");
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                newStringColumn.appendNull();
            } else {
                String value = get(index);
                newStringColumn.append(value.replaceFirst(reg, replacement));
            }
        }

        return newStringColumn;
    }

    /**
     * Replaces all substrings matching the regex with a replacement string.
     * @param reg the regex pattern
     * @param replacement the replacement string
     * @return a new StringColumn with replacements
     */
    default StringColumn replaceAll(String reg, String replacement) {
        StringColumn newStringColumn = StringColumn.create(name() + " (replace_all)");
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                newStringColumn.appendNull();
            } else {
                String value = get(index);
                newStringColumn.append(value.replaceAll(reg, replacement));
            }
        }

        return newStringColumn;
    }

    /**
     * Truncates strings to a maximum width, appending "..." if truncated.
     * @param maxWidth maximum string length including ellipsis
     * @return a new StringColumn with truncated values
     */
    default StringColumn truncate(int maxWidth) {
        final String defaultTruncatedValue = "...";
        StringColumn newStringColumn = StringColumn.create(name() + " (truncate)");
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                newStringColumn.appendNull();
            } else {
                String value = get(index);
                if (value.length() > maxWidth) {
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
        }

        return newStringColumn;
    }

    /**
     * Returns substrings starting from the specified index.
     * @param start the starting index (inclusive)
     * @return a new StringColumn with substrings
     */
    default StringColumn subString(int start) {
        StringColumn newStringColumn = StringColumn.create(name() + " (sub)");
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                newStringColumn.appendNull();
            } else {
                String value = get(index);
                newStringColumn.append(value.substring(start));
            }
        }

        return newStringColumn;
    }

    /**
     * Returns substrings between the specified start and end indexes.
     * @param start the starting index (inclusive)
     * @param end the ending index (exclusive)
     * @return a new StringColumn with substrings
     */
    default StringColumn subString(int start, int end) {
        StringColumn newStringColumn = StringColumn.create(name() + " (sub)");
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                newStringColumn.appendNull();
            } else {
                String value = get(index);
                newStringColumn.append(value.substring(start, end));
            }
        }

        return newStringColumn;
    }
}
