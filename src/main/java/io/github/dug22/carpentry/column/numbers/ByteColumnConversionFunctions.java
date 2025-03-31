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

public interface ByteColumnConversionFunctions {

    /**
     * Refers to the current ByteColumn you want to convert
     * @return the current ByteColumn that you want to convert.
     */
    private ByteColumn byteColumn(){
        return (ByteColumn) this;
    }

    /**
     * Converts a ByteColumn to a DoubleColumn
     * @return a DoubleColumn
     */
    default DoubleColumn asDoubleColumn() {
        return ColumnConvertor.convert(byteColumn(), Double.class, Byte::doubleValue);
    }

    /**
     * Converts a ByteColumn to a FloatColumn
     * @return a FloatColumn
     */
    default FloatColumn asFloatColumn() {
        return ColumnConvertor.convert(byteColumn(), Float.class, Byte::floatValue);
    }

    /**
     * Converts a ByteColumn to an IntegerColumn
     * @return an IntegerColumn
     */
    default IntegerColumn asIntegerColumn() {
        return ColumnConvertor.convert(byteColumn(), Integer.class, Byte::intValue);
    }

    /**
     * Converts a ByteColumn to a LongColumn
     * @return a LongColumn
     */
    default LongColumn asLongColumn() {
        return ColumnConvertor.convert(byteColumn(), Long.class, Byte::longValue);
    }

    /**
     * Converts a ByteColumn to a ShortColumn
     * @return a ShortColumn
     */
    default ShortColumn asShortColumn() {
        return ColumnConvertor.convert(byteColumn(), Short.class, Byte::shortValue);
    }

    /**
     * Converts a ByteColumn to a StringColumn
     * @return a StringColumn
     */
    default StringColumn asStringColumn() {
        return ColumnConvertor.convert(byteColumn(), String.class, String::valueOf);
    }
}
