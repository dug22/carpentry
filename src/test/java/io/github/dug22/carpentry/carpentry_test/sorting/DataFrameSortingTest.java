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

package io.github.dug22.carpentry.carpentry_test.sorting;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.io.csv.CSVHeader;
import io.github.dug22.carpentry.io.csv.CSVHeaders;
import io.github.dug22.carpentry.io.csv.CSVReader;
import io.github.dug22.carpentry.io.csv.CSVReaderBuilder;
import io.github.dug22.carpentry.sorting.SortColumn;
import io.github.dug22.carpentry.sorting.SortColumns;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataFrameSortingTest {

    private final CSVReader reader = new CSVReaderBuilder()
            .setURL("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/abalone.csv")
            .setHeaders(CSVHeaders.of(
                    new CSVHeader("sex", String.class),
                    new CSVHeader("length", Double.class),
                    new CSVHeader("diameter", Double.class),
                    new CSVHeader("height", Double.class),
                    new CSVHeader("whole_weight", Double.class),
                    new CSVHeader("shucked_weight", Double.class),
                    new CSVHeader("viscera_weight", Double.class),
                    new CSVHeader("shell_weight", Double.class),
                    new CSVHeader("rings", Integer.class)
            )).build();

    private DefaultDataFrame dataFrame;

    @BeforeEach
    public void setUp() {
        dataFrame = DefaultDataFrame.load(reader);
    }

    @Test
    public void sortAscending() {
        DefaultDataFrame result = dataFrame.sort(SortColumns.of("rings", SortColumn.Direction.ASCENDING));
        Integer[] ringsColumn = result.getIntegerColumn("rings").getValues();
        for (int i = 1; i < ringsColumn.length; i++) {
            assertTrue(ringsColumn[i] >= ringsColumn[i - 1], "The data is not sorted at index " + i);
        }
    }

    @Test
    public void sortDescending() {
        DefaultDataFrame result = dataFrame.sort(SortColumns.of("rings", SortColumn.Direction.DESCENDING));
        Integer[] ringsColumn = result.getIntegerColumn("rings").getValues();
        for (int i = 1; i < ringsColumn.length; i++) {
            assertTrue(ringsColumn[i] <= ringsColumn[i - 1], "The data is not sorted at index " + i);
        }
    }

    @Test
    public void sortInParallelAscending() {
        DefaultDataFrame result = dataFrame.sortInParallel(SortColumns.of("rings", SortColumn.Direction.ASCENDING));
        Integer[] ringsColumn = result.getIntegerColumn("rings").getValues();
        for (int i = 1; i < ringsColumn.length; i++) {
            assertTrue(ringsColumn[i] >= ringsColumn[i - 1], "The data is not sorted at index " + i);
        }
    }

    @Test
    public void sortInParallelDescending() {
        DefaultDataFrame result = dataFrame.sortInParallel(SortColumns.of("rings", SortColumn.Direction.DESCENDING));
        Integer[] ringsColumn = result.getIntegerColumn("rings").getValues();
        for (int i = 1; i < ringsColumn.length; i++) {
            assertTrue(ringsColumn[i] <= ringsColumn[i - 1], "The data is not sorted at index " + i);
        }
    }

    @Test
    public void sortMultipleColumnsAscending() {
        DefaultDataFrame result = dataFrame.sort(SortColumns.of(
                "rings", SortColumn.Direction.ASCENDING,
                "height", SortColumn.Direction.ASCENDING));
        Integer[] ringsColumn = result.getIntegerColumn("rings").getValues();
        for (int i = 1; i < ringsColumn.length; i++) {
            assertTrue(ringsColumn[i] >= ringsColumn[i - 1], "The data is not sorted at index " + i);
        }

        Double[] heightColumn = result.getDoubleColumn("height").getValues();
        for (int i = 1; i < heightColumn.length; i++) {
            if (ringsColumn[i].equals(ringsColumn[i - 1])) {
                assertTrue(heightColumn[i] >= heightColumn[i - 1], "The data is not sorted by height at index " + i);
            }
        }
    }

    @Test
    public void sortMultipleColumnsDescending() {
        DefaultDataFrame result = dataFrame.sort(SortColumns.of(
                "rings", SortColumn.Direction.DESCENDING,
                "height", SortColumn.Direction.DESCENDING));
        Integer[] ringsColumn = result.getIntegerColumn("rings").getValues();
        for (int i = 1; i < ringsColumn.length; i++) {
            assertTrue(ringsColumn[i] <= ringsColumn[i - 1], "The data is not sorted at index " + i);
        }
        Double[] heightColumn = result.getDoubleColumn("height").getValues();
        for (int i = 1; i < heightColumn.length; i++) {
            if (ringsColumn[i].equals(ringsColumn[i - 1])) {
                assertTrue(heightColumn[i] <= heightColumn[i - 1], "The data is not sorted by height at index " + i);
            }
        }
    }

    @Test
    public void sortMultipleColumnsWithMixedDirections() {
        DefaultDataFrame result = dataFrame.sort(SortColumns.of(
                "rings", SortColumn.Direction.ASCENDING,
                "height", SortColumn.Direction.DESCENDING));

        Integer[] ringsColumn = result.getIntegerColumn("rings").getValues();
        for (int i = 1; i < ringsColumn.length; i++) {
            assertTrue(ringsColumn[i] >= ringsColumn[i - 1], "The data is not sorted by rings at index " + i);
        }

        Double[] heightColumn = result.getDoubleColumn("height").getValues();
        for (int i = 1; i < heightColumn.length; i++) {
            if (ringsColumn[i].equals(ringsColumn[i - 1])) {
                assertTrue(heightColumn[i] <= heightColumn[i - 1], "The data is not sorted by height at index " + i);
            }
        }
    }

    @Test
    public void sortMultipleColumnsInParallelWithMixedDirections() {
        DefaultDataFrame result = dataFrame.sortInParallel(SortColumns.of(
                "rings", SortColumn.Direction.ASCENDING,
                "height", SortColumn.Direction.DESCENDING));

        Integer[] ringsColumn = result.getIntegerColumn("rings").getValues();
        for (int i = 1; i < ringsColumn.length; i++) {
            assertTrue(ringsColumn[i] >= ringsColumn[i - 1], "The data is not sorted by rings at index " + i);
        }

        Double[] heightColumn = result.getDoubleColumn("height").getValues();
        for (int i = 1; i < heightColumn.length; i++) {
            if (ringsColumn[i].equals(ringsColumn[i - 1])) {
                assertTrue(heightColumn[i] <= heightColumn[i - 1], "The data is not sorted by height at index " + i);
            }
        }
    }
}