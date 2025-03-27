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

package io.github.dug22.carpentry.carpentry_test.rename;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.rename.RenameMap;
import io.github.dug22.carpentry.utility.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataFrameRenameTest {

    private final DefaultDataFrame dataFrame = DefaultDataFrame.create()
            .addStringColumn("Strings", new String[]{"A","B","C","D"})
            .addIntegerColumn("Integers", new Integer[]{1,2,3,4})
            .addDoubleColumn("Doubles", new Double[]{1D,2D,3D,4D})
            .addCharacterColumn("Characters", new Character[]{'a','b','c','d'})
            .addNumberColumn("Numbers", new Number[]{1,2,3D,4F});

    @Test
    public void renameTest(){
        RenameMap renameMap = RenameMap.of(
                new Pair<>("Integers", "Ints"),
                new Pair<>("Characters", "Chars"),
                new Pair<>("Numbers", "Numbs")
        );

        DataFrame result = dataFrame.renameColumns(renameMap);
        assertFalse(result.containsColumn("Integers"));
        assertTrue(result.containsColumn("Ints"));
        assertFalse(result.containsColumn("Characters"));
        assertTrue(result.containsColumn("Chars"));
        assertFalse(result.containsColumn("Numbers"));
        assertTrue(result.containsColumn("Numbs"));
    }
}
