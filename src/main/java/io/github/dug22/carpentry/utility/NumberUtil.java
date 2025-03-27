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

package io.github.dug22.carpentry.utility;

public class NumberUtil {

    @SuppressWarnings("unchecked")
    public static <T extends Number> T convert(Number n, Class<T> type) {
        return switch (type.getName()) {
            case "java.lang.Double" -> (T) Double.valueOf(n.doubleValue());
            case "java.lang.Integer" -> (T) Integer.valueOf(n.intValue());
            case "java.lang.Float" -> (T) Float.valueOf(n.floatValue());
            case "java.lang.Long" -> (T) Long.valueOf(n.longValue());
            case "java.lang.Short" -> (T) Short.valueOf(n.shortValue());
            case "java.lang.Byte" -> (T) Byte.valueOf(n.byteValue());
            case "java.lang.Number" -> (T) n;
            default -> throw new IllegalStateException("Unexpected value: " + type.getName());
        };
    }
}
