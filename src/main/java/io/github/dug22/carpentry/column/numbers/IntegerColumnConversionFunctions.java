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

public interface IntegerColumnConversionFunctions {


    /**
     * Refers to the current IntegerColumn you want to convert
     * @return the current IntegerColumn that you want to convert.
     */
    private IntegerColumn integerColumn(){
        return (IntegerColumn) this;
    }

    /**
     * Converts an IntegerColumn to a ByteColumn
     * @return a ByteColumn
     */
    default ByteColumn asByteColumn(){
        return ColumnConvertor.convert(integerColumn(), Byte.class, Integer::byteValue);
    }

    /**
     * Converts an IntegerColumn to a DoubleColumn
     * @return a DoubleColumn
     */
    default DoubleColumn asDoubleColumn(){
        return ColumnConvertor.convert(integerColumn(), Double.class, Integer::doubleValue);
    }

    /**
     * Converts an IntegerColumn to a FloatColumn
     * @return a FloatColumn
     */
    default FloatColumn asFloatColumn(){
        return ColumnConvertor.convert(integerColumn(), Float.class, Integer::floatValue);
    }

    /**
     * Converts an IntegerColumn to a LongColumn
     * @return a LongColumn
     */
    default LongColumn asLongColumn(){
        return ColumnConvertor.convert(integerColumn(), Long.class, Integer::longValue);
    }

    /**
     * Converts an IntegerColumn to a ShortColumn
     * @return a ShortColumn
     */
    default ShortColumn asShortColumn(){
        return ColumnConvertor.convert(integerColumn(), Short.class, Integer::shortValue);
    }

    /**
     * Converts an IntegerColumn to a StringColumn
     * @return a StringColumn
     */
    default StringColumn asStringColumn(){
        return ColumnConvertor.convert(integerColumn(), String.class, String::valueOf);
    }
}
