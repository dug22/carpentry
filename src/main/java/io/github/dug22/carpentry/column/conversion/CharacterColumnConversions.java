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

package io.github.dug22.carpentry.column.conversion;

import io.github.dug22.carpentry.column.ColumnConversion;
import io.github.dug22.carpentry.column.impl.StringColumn;
import io.github.dug22.carpentry.utils.Nulls;

public interface CharacterColumnConversions extends ColumnConversion<Character> {

    /**
     * Converts a CharacterColumn to a StringColumn
     * @return a StringColumn
     */
    default StringColumn asStringColumn() {
        return convertToColumn(
                StringColumn.create(name(), new String[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(String.class) : String.valueOf(get(i))
        );
    }
}
