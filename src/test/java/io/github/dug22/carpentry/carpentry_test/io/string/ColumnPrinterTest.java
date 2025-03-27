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

package io.github.dug22.carpentry.carpentry_test.io.string;

import io.github.dug22.carpentry.columns.IntegerColumn;
import io.github.dug22.carpentry.io.string.ColumnPrinter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColumnPrinterTest {

    private final IntegerColumn integerColumn = IntegerColumn.create("Integers", new Integer[]{1,2,3,4});

    @Test
    public void printTest(){
        ColumnPrinter columnPrinter = new ColumnPrinter();
        String expected =
                "┌──────────┐\n" +
                "│ Integers │\n" +
                "├──────────┤\n" +
                "│ 1        │\n" +
                "│ 2        │\n" +
                "│ 3        │\n" +
                "│ 4        │\n" +
                "└──────────┘";
        String actual = columnPrinter.toString(integerColumn, 4, 0, false);
        assertEquals(expected, actual);
    }

    @Test
    public void printReverseTest(){
        ColumnPrinter columnPrinter = new ColumnPrinter();
        String expected =
                "┌──────────┐\n" +
                "│ Integers │\n" +
                "├──────────┤\n" +
                "│ 4        │\n" +
                "│ 3        │\n" +
                "│ 2        │\n" +
                "│ 1        │\n" +
                "└──────────┘";
        String actual = columnPrinter.toString(integerColumn, 4, 0, true);
        assertEquals(expected, actual);
    }
}
