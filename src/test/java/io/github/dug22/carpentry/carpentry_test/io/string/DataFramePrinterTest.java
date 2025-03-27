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

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.io.string.DataFramePrinter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataFramePrinterTest {

    private final DefaultDataFrame df = DefaultDataFrame.create()
            .addIntegerColumn("Integers", new Integer[]{100, 200, 300})
            .addStringColumn("Strings", new String[]{"How", "Are", "You"});

    @Test
    public void printTest() {
        DataFramePrinter printer = new DataFramePrinter();
        String expected =
                        "┌──────────┬────────┐\n" +
                        "│ Integers │ String │\n" +
                        "├──────────┼────────┤\n" +
                        "│ 100      │ How    │\n" +
                        "│ 200      │ Are    │\n" +
                        "│ 300      │ You    │\n" +
                        "└──────────┴────────┘\n";
        String actual = printer.toString(df, 3, 0, false);
        assertEquals(expected, actual);
    }

    @Test
    public void printReverseTest() {
        DataFramePrinter printer = new DataFramePrinter();
        String expected =
                        "┌──────────┬────────┐\n" +
                        "│ Integers │ String │\n" +
                        "├──────────┼────────┤\n" +
                        "│ 300      │ You    │\n" +
                        "│ 200      │ Are    │\n" +
                        "└──────────┴────────┘\n";
        String actual = printer.toString(df, 2, 0, true);
        assertEquals(expected, actual);
    }
}