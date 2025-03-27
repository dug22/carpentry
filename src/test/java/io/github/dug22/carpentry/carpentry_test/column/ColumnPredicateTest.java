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

package io.github.dug22.carpentry.carpentry_test.column;

import io.github.dug22.carpentry.column.ColumnPredicate;
import io.github.dug22.carpentry.columns.DoubleColumn;
import io.github.dug22.carpentry.columns.IntegerColumn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ColumnPredicateTest {

    @Test
    public void columnPredicateTest(){
        ColumnPredicate<Double> doubleColumnPredicate = i -> i > 10;
        DoubleColumn doubleColumn = DoubleColumn.create("Doubles", new Double[]{10D, 10D, 15D}).filter(doubleColumnPredicate);
        Double[] expectedDoubleValues= new Double[]{15D};
        assertArrayEquals(expectedDoubleValues, doubleColumn.getValues());

        ColumnPredicate<Integer> integerColumnPredicate = i -> i > 10;
        IntegerColumn integerColumn = IntegerColumn.create("Integers", new Integer[]{10, 10, 15}).filter(integerColumnPredicate);
        Integer[] expectedIntegerValues = new Integer[]{15};
        assertArrayEquals(expectedIntegerValues, integerColumn.getValues());
    }
}
