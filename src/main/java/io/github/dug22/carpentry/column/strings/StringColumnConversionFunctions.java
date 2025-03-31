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

package io.github.dug22.carpentry.column.strings;

import io.github.dug22.carpentry.column.ColumnConvertor;
import io.github.dug22.carpentry.columns.*;

public interface StringColumnConversionFunctions {

    /**
     * Refers to the current StringColumn you want to convert
     * @return the current StringColumn that you want to convert.
     */
    private StringColumn stringColumn() {
        return (StringColumn) this;
    }

    /**
     * Converts a StringColumn to a DoubleColumn
     * @return a DoubleColumn
     */
    default DoubleColumn asDoubleColumn() {
        return ColumnConvertor.convert(stringColumn(), Double.class, Double::parseDouble);
    }

    /**
     * Converts a StringColumn to an IntegerColumn
     * @return an IntegerColumn
     */
    default IntegerColumn asIntegerColumn() {
        return ColumnConvertor.convert(stringColumn(), Integer.class, Integer::parseInt);
    }

    /**
     * Converts a StringColumn to a FloatColumn
     * @return a FloatColumn
     */
    default FloatColumn asFloatColumn() {
        return ColumnConvertor.convert(stringColumn(), Float.class, Float::parseFloat);
    }

    /**
     * Converts a StringColumn to a CharacterColumn
     * @return a CharacterColumn
     */
    default CharacterColumn asCharacterColumn() {
        return ColumnConvertor.convert(stringColumn(), Character.class, i -> i.charAt(0));
    }
}
