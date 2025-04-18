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

package io.github.dug22.carpentry.carpentry_test.drop;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.drop.How;
import io.github.dug22.carpentry.io.csv.CSVReader;
import io.github.dug22.carpentry.io.csv.CSVReaderBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DataFrameDropTest {

    private DefaultDataFrame dataFrame;

    private final CSVReader csvReader = new CSVReaderBuilder()
            .setURL("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/employees.csv")
            .build();

    @BeforeEach
    public void setUp() {
        dataFrame = DefaultDataFrame.load(csvReader);
    }

    @Test
    public void dropColumnsByNameTest() {
        DefaultDataFrame result = dataFrame.drop("salary", "location");
        assertFalse(result.containsColumns("salary", "location"));
        assertEquals(4, result.getColumnCount());
    }

    @Test
    public void dropColumnsByIndexTest() {
        DefaultDataFrame result = dataFrame.drop(4, 5);
        assertFalse(result.containsColumns("salary", "location"));
        assertEquals(4, result.getColumnCount());
    }

    @Test
    public void dropNATestOne() {
        int originalRowSize = 30;
        int dropRowSize = 15;
        assertEquals(originalRowSize, dataFrame.getRows().size());
        DefaultDataFrame result = dataFrame.dropNA(How.ANY);
        assertEquals(dropRowSize, result.getRowCount());
    }

    @Test
    public void dropNATestTwo() {
        int originalRowSize = 30;
        int dropRowSize = 30;
        assertEquals(originalRowSize, dataFrame.getRows().size());
        DefaultDataFrame result = dataFrame.dropNA(How.ALL);
        assertEquals(dropRowSize, result.getRowCount());
    }

    @Test
    public void dropNATestThree() {
        int originalRowSize = 30;
        int dropRowSize = 20;
        assertEquals(originalRowSize, dataFrame.getRows().size());
        DefaultDataFrame result = dataFrame.dropNA("hire_date", "salary");
        assertEquals(dropRowSize, result.getRowCount());
    }
}
