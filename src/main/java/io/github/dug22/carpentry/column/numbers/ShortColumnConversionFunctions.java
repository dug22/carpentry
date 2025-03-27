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

public interface ShortColumnConversionFunctions {

    private ShortColumn shortColumn() {
        return (ShortColumn) this;
    }

    default ByteColumn asByteColumn() {
        return ColumnConvertor.convert(shortColumn(), Byte.class, Short::byteValue);
    }

    default DoubleColumn asDoubleColumn() {
        return ColumnConvertor.convert(shortColumn(), Double.class, Short::doubleValue);
    }

    default FloatColumn asFloatColumn() {
        return ColumnConvertor.convert(shortColumn(), Float.class, Short::floatValue);
    }

    default IntegerColumn asIntegerColumn() {
        return ColumnConvertor.convert(shortColumn(), Integer.class, Short::intValue);
    }

    default LongColumn asLongColumn() {
        return ColumnConvertor.convert(shortColumn(), Long.class, Short::longValue);
    }

    default StringColumn asStringColumn() {
        return ColumnConvertor.convert(shortColumn(), String.class, String::valueOf);
    }
}
