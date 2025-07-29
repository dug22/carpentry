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
import io.github.dug22.carpentry.column.impl.*;
import io.github.dug22.carpentry.utils.Nulls;

public interface DoubleColumnConversions extends ColumnConversion<Double> {

    /**
     * Converts a DoubleColumn to a ByteColumn
     * @return a ByteColumn
     */
    default ByteColumn asByteColumn() {
        return convertToColumn(
                ByteColumn.create(name(), new Byte[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(Byte.class) : get(i).byteValue());
    }

    /**
     * Converts a DoubleColumn to a FloatColumn
     * @return a FloatColumn
     */
    default FloatColumn asFloatColumn() {
        return convertToColumn(
                FloatColumn.create(name(), new Float[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(Float.class) : get(i).floatValue());
    }

    /**
     * Converts a DoubleColumn to an IntegerColumn
     * @return an IntegerColumn
     */
    default IntegerColumn asIntegerColumn() {
        return convertToColumn(
                IntegerColumn.create(name(), new Integer[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(Integer.class) : get(i).intValue());
    }

    /**
     * Converts a DoubleColumn to a LongColumn
     * @return a LongColumn
     */
    default LongColumn asLongColumn() {
        return convertToColumn(LongColumn.create(name(), new Long[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(Long.class) : get(i).longValue());
    }

    /**
     * Converts a DoubleColumn to a ShortColumn
     * @return a ShortColumn
     */
    default ShortColumn asShortColumn(){
        return convertToColumn(ShortColumn.create(name(), new Short[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(Short.class) : get(i).shortValue());
    }

    /**
     * Converts a DoubleColumn to a StringColumn
     * @return a StringColumn
     */
    default StringColumn asStringColumn() {
        return convertToColumn(
                StringColumn.create(name(), new String[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(String.class) : String.valueOf(get(i))
        );
    }
}
