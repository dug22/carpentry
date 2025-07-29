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
import io.github.dug22.carpentry.column.ColumnTypes;
import io.github.dug22.carpentry.column.impl.*;
import io.github.dug22.carpentry.utils.Nulls;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface StringColumnConversions extends ColumnConversion<String> {

    default BooleanColumn asBooleanColumn() {
        return convertToColumn(
                BooleanColumn.create(name(), new Boolean[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(Boolean.class) : ColumnTypes.BOOLEAN_COLUMN_TYPE.getParser().parse(get(i)));
    }

    /**
     * Converts a StringColumn to a DoubleColumn
     *
     * @return a DoubleColumn
     */
    default DoubleColumn asDoubleColumn() {
        return convertToColumn(
                DoubleColumn.create(name(), new Double[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(Double.class) : ColumnTypes.DOUBLE_COLUMN_TYPE.getParser().parse(get(i)));
    }

    /**
     * Converts a StringColumn to an IntegerColumn
     *
     * @return an IntegerColumn
     */
    default IntegerColumn asIntegerColumn() {
        return convertToColumn(IntegerColumn.create(name(), new Integer[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(Integer.class) : ColumnTypes.INTEGER_COLUMN_TYPE.getParser().parse(get(i)));
    }

    /**
     * Converts a StringColumn to a FloatColumn
     *
     * @return a FloatColumn
     */
    default FloatColumn asFloatColumn() {
        return convertToColumn(
                FloatColumn.create(name(), new Float[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(Float.class) : ColumnTypes.FLOAT_COLUMN_TYPE.getParser().parse(get(i)));
    }

    /**
     * Converts a StringColumn to a LongColumn
     *
     * @return a LongColumn
     */
    default LongColumn asLongColumn() {
        return convertToColumn(
                LongColumn.create(name(), new Long[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(Long.class) : ColumnTypes.LONG_COLUMN_TYPE.getParser().parse(get(i)));
    }

    /**
     * Converts a StringColumn to a CharacterColumn
     *
     * @return a CharacterColumn
     */
    default CharacterColumn asCharacterColumn() {
        return convertToColumn(
                CharacterColumn.create(name(), new Character[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(Character.class) : get(i).charAt(0));
    }

    /**
     * Converts a StringColumn to a DateColumn
     *
     * @return a DateColumn
     */
    default DateColumn asDateColumn() {
        return convertToColumn(DateColumn.create(name(), new LocalDate[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(LocalDate.class) : ColumnTypes.DATE_COLUMN_TYPE.getParser().parse(get(i)));
    }

    /**
     * Converts a StringColumn to a DateTimeColumn
     *
     * @return a DateTimeColumn
     */
    default DateTimeColumn asDateTimeColumn() {
        return convertToColumn(DateTimeColumn.create(name(), new LocalDateTime[size()]),
                (i) -> isAbsent(i) ? Nulls.getDefaultNullValue(LocalDateTime.class) : ColumnTypes.DATE_TIME_COLUMN_TYPE.getParser().parse(get(i)));
    }
}
