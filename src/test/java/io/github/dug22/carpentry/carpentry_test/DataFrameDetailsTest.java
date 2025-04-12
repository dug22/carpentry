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

package io.github.dug22.carpentry.carpentry_test;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnMap;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataFrameDetailsTest {

    private final DefaultDataFrame dataFrame = DefaultDataFrame.create()
            .addStringColumn("Name", new String[]{"Bob", "Mark", "Andrew"})
            .addIntegerColumn("Age", new Integer[]{35, 42, 39})
            .addStringColumn("Sex", new String[]{"Male", "Male", "Male"});

    private final ColumnMap columnMap = dataFrame.getColumnMap();

    @Test
    public void shapeTest() {
        assertEquals(3, dataFrame.getColumnCount());
        assertEquals(3, columnMap.getRowCount());
        dataFrame.shape();
    }

    @Test
    public void rowColumnDetailsTest() {
        int rangeIndex = columnMap.getRowCount();
        assertEquals(3, rangeIndex);
        Map<String, Integer> dataTypesMap = new LinkedHashMap<>();
        for (AbstractColumn<?> column : columnMap.values()) {
            long nonNullCount = Arrays.stream(column.getValues()).filter(Objects::nonNull).count();
            assertEquals(3, nonNullCount);
            String dataType = column.getColumnType().getSimpleName();
            dataTypesMap.put(dataType, dataTypesMap.getOrDefault(dataType, 0) + 1);
        }

        String[] expectedColumnNames = new String[]{"Name", "Age", "Sex"};
        for (String expectedColumnName : expectedColumnNames) {
            assertTrue(columnMap.containsColumn(expectedColumnName));
        }
        assertEquals(2, dataTypesMap.get("String"));
        assertEquals(1, dataTypesMap.get("Integer"));
        dataFrame.rowColumnDetails();
    }

    @Test
    public void dataframeSizeInBytesTest() {
        long sizeInBytes = columnMap.entrySet().stream().mapToLong(entry -> entry.toString().getBytes(StandardCharsets.UTF_8).length).sum();
        assertEquals(178, sizeInBytes);
        dataFrame.dataframeSizeInBytes();
    }
}
