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

public interface FloatColumnConversionFunctions {

    /**
     * Refers to the current FloatColumn you want to convert
     * @return the current FloatColumn that you want to convert.
     */
    private FloatColumn floatColumn(){
        return (FloatColumn) this;
    }

    /**
     * Converts a FloatColumn to a ByteColumn
     * @return a ByteColumn
     */
    default ByteColumn asByteColumn() {
        return ColumnConvertor.convert(floatColumn(), Byte.class, Float::byteValue);
    }

    /**
     * Converts a FloatColumn to a DoubleColumn
     * @return a DoubleColumn
     */
    default DoubleColumn asDoubleColumn() {
        return ColumnConvertor.convert(floatColumn(), Double.class, Float::doubleValue);
    }

    /**
     * Converts a FloatColumn to an IntegerColumn
     * @return an IntegerColumn
     */
    default IntegerColumn asIntegerColumn() {
        return ColumnConvertor.convert(floatColumn(), Integer.class, Float::intValue);
    }

    /**
     * Converts a FloatColumn to a LongColumn
     * @return a LongColumn
     */
    default LongColumn asLongColumn() {
        return ColumnConvertor.convert(floatColumn(), Long.class, Float::longValue);
    }

    /**
     * Converts a FloatColumn to a ShortColumn
     * @return a LongColumn
     */
    default ShortColumn asShortColumn() {
        return ColumnConvertor.convert(floatColumn(), Short.class, Float::shortValue);
    }

    /**
     * Converts a FloatColumn to a StringColumn
     * @return a StringColumn
     */
    default StringColumn asStringColumn() {
        return ColumnConvertor.convert(floatColumn(), String.class, String::valueOf);
    }
}
