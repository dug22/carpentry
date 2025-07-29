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

package io.github.dug22.carpentry.column.parser;

import io.github.dug22.carpentry.column.ColumnParser;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.Set;

public class BooleanParser implements ColumnParser<Boolean> {

    /**
     * A set of values that can be parsed to true
     */
    private static final Set<String> TRUE_VALUES = Set.of(
            "true", "True", "TRUE", "t", "T", "yes", "Yes", "YES", "y", "Y"
    );

    /**
     * A set of value that can be parsed to false
     */
    private static final Set<String> FALSE_VALUES = Set.of(
            "false", "False", "FALSE", "f", "F", "no", "No", "NO", "n", "N"
    );

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canParse(String value) {
        if (isNA(value)) return true;
        value = value.trim();
        return TRUE_VALUES.contains(value) || FALSE_VALUES.contains(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean parse(String value) {
        if (isNA(value)) return Nulls.getDefaultNullValue(Boolean.class);
        value = value.trim();
        if (TRUE_VALUES.contains(value)) {
            return true;
        } else if (FALSE_VALUES.contains(value)) {
            return false;
        } else {
            return Nulls.getDefaultNullValue(Boolean.class);
        }
    }
}
