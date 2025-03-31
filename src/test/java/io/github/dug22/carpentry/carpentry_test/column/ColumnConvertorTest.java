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

import io.github.dug22.carpentry.columns.BooleanColumn;
import io.github.dug22.carpentry.columns.CharacterColumn;
import io.github.dug22.carpentry.columns.DoubleColumn;
import io.github.dug22.carpentry.columns.StringColumn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColumnConvertorTest {

    @Test
    public void convertTest() {
        BooleanColumn booleanColumn = BooleanColumn.create("Booleans", new Boolean[]{true, true, true, true});
        assertEquals(4, booleanColumn.size());
        DoubleColumn doubleColumn = booleanColumn.asDoubleColumn();
        doubleColumn.setColumnName("Booleans as Doubles");
        assertEquals(4, doubleColumn.size());
        doubleColumn.show();

        StringColumn stringColumn = StringColumn.create("Sex", new String[]{"Male", "Female", "Male", "Female"});
        assertEquals(4, stringColumn.size());
        CharacterColumn characterColumn = stringColumn.asCharacterColumn();
        assertEquals(4, characterColumn.size());
        characterColumn.show();
    }
}
