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

package io.github.dug22.carpentry.column.numbers;

import io.github.dug22.carpentry.column.ColumnConvertor;
import io.github.dug22.carpentry.columns.*;

public interface DoubleColumnConversionFunctions {

    /**
     * Refers to the current DoubleColumn you want to convert
     * @return the current DoubleColumn that you want to convert.
     */
    private DoubleColumn doubleColumn() {
        return (DoubleColumn) this;
    }

    /**
     * Converts a DoubleColumn to a ByteColumn
     * @return a ByteColumn
     */
    default ByteColumn asByteColumn() {
        return ColumnConvertor.convert(doubleColumn(), Byte.class, Double::byteValue);
    }

    /**
     * Converts a DoubleColumn to a FloatColumn
     * @return a FloatColumn
     */
    default FloatColumn asFloatColumn() {
        return ColumnConvertor.convert(doubleColumn(), Float.class, Double::floatValue);
    }

    /**
     * Converts a DoubleColumn to an IntegerColumn
     * @return an IntegerColumn
     */
    default IntegerColumn asIntegerColumn() {
        return ColumnConvertor.convert(doubleColumn(), Integer.class, Double::intValue);
    }

    /**
     * Converts a DoubleColumn to a LongColumn
     * @return a LongColumn
     */
    default LongColumn asLongColumn() {
        return ColumnConvertor.convert(doubleColumn(), Long.class, Double::longValue);
    }

    /**
     * Converts a DoubleColumn to a ShortColumn
     * @return a ShortColumn
     */
    default ShortColumn asShortColumn() {
        return ColumnConvertor.convert(doubleColumn(), Short.class, Double::shortValue);
    }

    /**
     * Converts a DoubleColumn to a StringColumn
     * @return a StringColumn
     */
    default StringColumn asStringColumn() {
        return ColumnConvertor.convert(doubleColumn(), String.class, String::valueOf);
    }
}
