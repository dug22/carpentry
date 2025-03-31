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

public interface NumberColumnConversionFunctions {


    /**
     * Refers to the current NumberColumn you want to convert
     * @return the current NumberColumn that you want to convert.
     */
    private NumberColumn numberColumn() {
        return (NumberColumn) this;
    }

    /**
     * Converts a NumberColumn to a ByteColumn
     * @return a ByteColumn
     */
    default ByteColumn asByteColumn() {
        return ColumnConvertor.convert(numberColumn(), Byte.class, Number::byteValue);
    }

    /**
     * Converts a NumberColumn to a DoubleColumn
     * @return a DoubleColumn
     */
    default DoubleColumn asDoubleColumn() {
        return ColumnConvertor.convert(numberColumn(), Double.class, Number::doubleValue);
    }

    /**
     * Converts a NumberColumn to a FloatColumn
     * @return a FloatColumn
     */
    default FloatColumn asFloatColumn() {
        return ColumnConvertor.convert(numberColumn(), Float.class, Number::floatValue);
    }

    /**
     * Converts a NumberColumn to an IntegerColumn
     * @return an IntegerColumn
     */
    default IntegerColumn asIntegerColumn() {
        return ColumnConvertor.convert(numberColumn(), Integer.class, Number::intValue);
    }

    /**
     * Converts a NumberColumn to a LongColumn
     * @return a LongColumn
     */
    default LongColumn asLongColumn() {
        return ColumnConvertor.convert(numberColumn(), Long.class, Number::longValue);
    }

    /**
     * Converts a NumberColumn to a ShortColumn
     * @return a ShortColumn
     */
    default ShortColumn asShortColumn() {
        return ColumnConvertor.convert(numberColumn(), Short.class, Number::shortValue);
    }

    /**
     * Converts a NumberColumn to a StringColumn
     * @return a StringColumn
     */
    default StringColumn asStringColumn() {
        return ColumnConvertor.convert(numberColumn(), String.class, String::valueOf);
    }

}
