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

package io.github.dug22.carpentry.io;

import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.columns.*;

public class TypeInference {

    public static Object parseValue(String value, Class<?> type) {
        if (value == null || value.isEmpty() || value.equals("NA") || value.equals("NaN") || value.equals("na")) {
            return null;
        }
        if (type == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type == Byte.class) {
            return Byte.parseByte(value);
        } else if (type == Character.class) {
            return value.charAt(0);
        } else if (type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == Float.class) {
            return Float.parseFloat(value);
        } else if (type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == Long.class) {
            return Long.parseLong(value);
        } else if (type == Number.class) {
            return Double.parseDouble(value);
        } else if (type == Short.class) {
            return Short.parseShort(value);
        } else {
            return value;
        }
    }

    public static AbstractColumn<?> createColumnInstance(String header, Class<?> type) {
        if (type == Boolean.class) {
            return BooleanColumn.create(header);
        } else if (type == Byte.class) {
            return ByteColumn.create(header);
        } else if (type == Character.class) {
            return CharacterColumn.create(header);
        } else if (type == Double.class) {
            return DoubleColumn.create(header);
        } else if (type == Float.class) {
            return FloatColumn.create(header);
        } else if (type == Integer.class) {
            return IntegerColumn.create(header);
        } else if (type == Long.class) {
            return LongColumn.create(header);
        } else if (type == Number.class) {
            return NumberColumn.create(header);
        } else if (type == Short.class) {
            return ShortColumn.create(header);
        } else {
            return StringColumn.create(header);
        }
    }
}
