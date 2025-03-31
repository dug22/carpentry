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

package io.github.dug22.carpentry.carpentry_test.utility;

import io.github.dug22.carpentry.utility.NumberUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberUtilTest {

    @Test
    public void convertTest() {
        Integer integerValue = 1002;
        Double doubleValue = convert(integerValue, Double.class);
        assertEquals(1002.0, doubleValue);

        Double doubleVal = 1234.56;
        Integer intValue = convert(doubleVal, Integer.class);
        assertEquals(1234, intValue);

        Float floatValue = convert(integerValue, Float.class);
        assertEquals(1002.0f, floatValue);

        Long longValue = 32767L;
        Short shortValue = convert(longValue, Short.class);
        assertEquals(Short.valueOf((short) 32767), shortValue);

        Short shortVal = 127;
        Byte byteValue = convert(shortVal, Byte.class);
        assertEquals(Byte.valueOf((byte) 127), byteValue);

        Integer sameTypeValue = convert(integerValue, Integer.class);
        assertEquals(integerValue, sameTypeValue);
    }

    private <T extends Number> T convert(Number number, Class<T> type) {
        return NumberUtil.convert(number, type);
    }
}
