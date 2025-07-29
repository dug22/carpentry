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
import io.github.dug22.carpentry.column.impl.CharacterColumn;
import io.github.dug22.carpentry.column.impl.DoubleColumn;
import io.github.dug22.carpentry.column.impl.IntegerColumn;
import io.github.dug22.carpentry.column.impl.StringColumn;
import io.github.dug22.carpentry.utils.Nulls;

public interface BooleanColumnConversions extends ColumnConversion<Boolean> {

    /**
     * Converts a BooleanColumn to a CharacterColumn
     * @return a CharacterColumn
     */
    default CharacterColumn asCharacterColumn() {
        return convertToColumn(
                CharacterColumn.create(name(), new Character[size()]),
                (i) -> {
                    if (isAbsent(i)) return Nulls.getDefaultNullValue(Character.class);
                    return get(i) ? 't' : 'f';
                }
        );
    }

    /**
     * Converts a BooleanColumn to a DoubleColumn
     * @return a DoubleColumn
     */
    default DoubleColumn asDoubleColumn() {
        return convertToColumn(
                DoubleColumn.create(name(), new Double[size()]),
                (i) -> {
                    if (isAbsent(i)) return Nulls.getDefaultNullValue(Double.class);
                    return get(i) ? 1.0 : 0.0;
                }
        );
    }

    /**
     * Converts a BooleanColumn to an IntegerColumn
     * @return a IntegerColumn
     */
    default IntegerColumn asIntegerColumn(){
        return convertToColumn(
                IntegerColumn.create(name(), new Integer[size()]),
                (i) -> {
                    if(isAbsent(i)) return Nulls.getDefaultNullValue(Integer.class);
                    return get(i) ? 1 : 0;
                }
        );
    }

    /**
     * Converts a BooleanColumn to a StringColumn
     * @return a StringColumn
     */
    default StringColumn asStringColumn() {
        return convertToColumn(
                StringColumn.create(name(), new String[size()]),
                (i) -> {
                    if (isAbsent(i)) return Nulls.getDefaultNullValue(String.class);
                    return "" + get(i);
                }
        );
    }
}
