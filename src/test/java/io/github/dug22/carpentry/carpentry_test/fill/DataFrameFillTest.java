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

package io.github.dug22.carpentry.carpentry_test.fill;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.columns.DoubleColumn;
import io.github.dug22.carpentry.columns.StringColumn;
import io.github.dug22.carpentry.fill.FillColumnValuePair;
import io.github.dug22.carpentry.fill.FillColumnValuePairs;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataFrameFillTest {

    private final DefaultDataFrame dataFrame = DefaultDataFrame.create()
            .addIntegerColumn("ID", IntStream.range(1, 5).boxed().toArray(Integer[]::new))
            .addStringColumn("Name", new String[]{"Sam", "Andy", "James", "John"})
            .addStringColumn("Favorite Food", new String[]{"Pizza", null, "Chicken", null})
            .addDoubleColumn("Height (ft)", new Double[]{67.8, 67.4, 70.1, null});

    @Test
    public void fillNATest() {
        DataFrame result = dataFrame.fillNA(
                FillColumnValuePairs.of(
                        new FillColumnValuePair("Favorite Food", "Not Listed"),
                        new FillColumnValuePair("Height (ft)", Double.NaN)));
        StringColumn foodColumn = result.getStringColumn("Favorite Food");
        assertEquals("Pizza", foodColumn.get(0));
        assertEquals("Not Listed", foodColumn.get(1));
        assertEquals("Chicken", foodColumn.get(2));
        assertEquals("Not Listed", foodColumn.get(3));

        DoubleColumn heightColumn = result.getDoubleColumn("Height (ft)");
        assertEquals(67.8, heightColumn.get(0));
        assertEquals(67.4, heightColumn.get(1));
        assertEquals(70.1, heightColumn.get(2));
        assertTrue(Double.isNaN(heightColumn.get(3)));
    }
}
