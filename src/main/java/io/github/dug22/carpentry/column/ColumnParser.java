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

package io.github.dug22.carpentry.column;

import io.github.dug22.carpentry.utils.Nulls;

public interface ColumnParser<T> {

    /**
     * Checks if the given value can be parsed or not
     * @param value the value that we want to parse
     * @return if the given value can be parsed or not
     */
    boolean canParse(String value);

    /**
     * Parses the given string value into an instance of type T.
     *
     * @param value the string value to parse
     * @return the parsed value of type T
     * @throws IllegalArgumentException if the value cannot be parsed
     */
    T parse(String value);

    /**
     * Checks if the given string represents a null or missing value.
     *
     * @param value the string value to check
     * @return {@code true} if the value is considered null or missing, {@code false} otherwise
     */
    default boolean isNA(String value) {
        return Nulls.isNull(value);
    }

    /**
     * Removes all occurrences of the specified character from the input string.
     *
     * @param value the input string
     * @param remove the character to remove
     * @return a new string with all occurrences of {@code remove} removed or the original string if {@code value} is null or does not contain {@code remove}
     */
    default String removeComponent(final String value, final char remove) {
        if (value == null || value.indexOf(remove) == -1) return value;
        StringBuilder sb = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c != remove) sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Removes all commas from the input string.
     *
     * @param value the input string
     * @return a new string with all commas removed,
     *         or the original string if {@code value} is null or contains no commas
     */
    default String removeComma(final String value) {
        return removeComponent(value, ',');
    }
}