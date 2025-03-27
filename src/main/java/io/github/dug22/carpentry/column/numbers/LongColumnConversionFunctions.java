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

public interface LongColumnConversionFunctions {

    /**
     * Refers to the current LongColumn you want to convert
     * @return the current LongColumn that you want to convert.
     */
    private LongColumn longColumn() {
        return (LongColumn) this;
    }

    /**
     * Converts a LongColumn to a ByteColumn
     * @return a ByteColumn
     */
    default ByteColumn asByteColumn() {
        return ColumnConvertor.convert(longColumn(), Byte.class, Long::byteValue);
    }

    /**
     * Converts a LongColumn to a DoubleColumn
     * @return a DoubleColumn
     */
    default DoubleColumn asDoubleColumn() {
        return ColumnConvertor.convert(longColumn(), Double.class, Long::doubleValue);
    }

    /**
     * Converts a LongColumn to a FloatColumn
     * @return a FloatColumn
     */
    default FloatColumn asFloatColumn() {
        return ColumnConvertor.convert(longColumn(), Float.class, Long::floatValue);
    }

    /**
     * Converts a LongColumn to an IntegerColumn
     * @return an IntegerColumn
     */
    default IntegerColumn asIntegerColumn() {
        return ColumnConvertor.convert(longColumn(), Integer.class, Long::intValue);
    }

    /**
     * Converts a LongColumn to a ShortColumn
     * @return a ShortColumn
     */
    default ShortColumn asShortColumn() {
        return ColumnConvertor.convert(longColumn(), Short.class, Long::shortValue);
    }

    /**
     * Converts a LongColumn to a StringColumn
     * @return a StringColumn
     */
    default StringColumn asStringColumn() {
        return ColumnConvertor.convert(longColumn(), String.class, String::valueOf);
    }
}
