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

package io.github.dug22.carpentry.filter.predicates;

import io.github.dug22.carpentry.filter.FilterPredicate;

import java.util.regex.Pattern;

public class StringPredicate {

    private final String columnName;
    private final String stringValue;

    public StringPredicate(String columnName, String stringValue) {
        this.columnName = columnName;
        this.stringValue = stringValue;
    }

    public FilterPredicate startsWith() {
        return row -> {
            Object value = row.get(columnName);
            return value instanceof String && ((String) value).startsWith(stringValue);
        };
    }

    public FilterPredicate endsWith() {
        return row -> {
            Object value = row.get(columnName);
            return value instanceof String && ((String) value).endsWith(stringValue);
        };
    }

    public FilterPredicate contains() {
        return row -> {
            Object value = row.get(columnName);
            return value instanceof String && ((String) value).contains(stringValue);
        };
    }

    public FilterPredicate matches() {
        Pattern pattern = Pattern.compile(stringValue, Pattern.CASE_INSENSITIVE); // optional flag
        return row -> {
            Object value = row.get(columnName);
            return value instanceof String && pattern.matcher((String) value).find();
        };
    }
}