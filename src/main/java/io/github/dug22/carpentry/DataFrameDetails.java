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

package io.github.dug22.carpentry;

import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnMap;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class DataFrameDetails {

    /**
     * Prints the shape of the current dataframe
     * @param columnMap the current ColumnMap
     */
    public static void shape(ColumnMap columnMap) {
        int[] shape = new int[]{columnMap.getRowCount(), columnMap.size()};
        System.out.println("dataframe shape: [" + shape[0] + "x" + shape[1] + "]");
    }

    /**
     * Prints the row and column details about a given dataframe
     * @param columnMap the current ColumnMap
     */
    public static void rowColumnDetails(ColumnMap columnMap) {
        System.out.println("<class '" + DataFrame.class.getName() + "'>");
        int rowCount = columnMap.isEmpty() ? 0 : columnMap.getRowCount();
        System.out.println("Range Index: " + rowCount + " entries, 0 to " + (rowCount - 1));
        System.out.println("Data columns (total " + columnMap.size() + " columns):");
        System.out.printf("%-4s%-12s%-18s%-12s%n", "#", "Column", "Non-Null Count", "dType");
        System.out.printf("%-4s%-12s%-18s%-12s%n", "---", "------", "--------------", "-----");
        int columnIndex = 0;
        Map<String, Integer> dataTypesMap = new LinkedHashMap<>();
        for (AbstractColumn<?> column : columnMap.values()) {
            String columnName = column.getColumnName();
            long nonNullCount = Arrays.stream(column.getValues()).filter(Objects::nonNull).count();
            String dataType = column.getColumnType().getSimpleName();
            System.out.printf("%-4d%-12s%-18d%-12s%n", columnIndex, columnName, nonNullCount, dataType);
            dataTypesMap.put(dataType, dataTypesMap.getOrDefault(dataType, 0) + 1);
            columnIndex++;
        }

        StringBuilder typeSummary = new StringBuilder("dtypes: ");
        dataTypesMap.forEach((key, value) -> typeSummary.append(key).append("(").append(value).append("), "));
        if (!typeSummary.isEmpty()) {
            typeSummary.setLength(typeSummary.length() - 2);
        }
        System.out.println(typeSummary);

    }

    /**
     * Prints the dataframe size
     * @param columnMap the current ColumnMap
     */
    public static void dataframeSizeInBytes(ColumnMap columnMap){
        long sizeInBytes = columnMap.entrySet().stream().mapToLong(entry -> entry.toString().getBytes(StandardCharsets.UTF_8).length).sum();
        System.out.println("dataframe size: " + sizeInBytes + " bytes");
    }

    /**
     * Gives you a full insight of information about a given DataFrame.
     * @param columnMap the current ColumnMap
     */
    public static void info(ColumnMap columnMap){
        shape(columnMap);
        rowColumnDetails(columnMap);
        dataframeSizeInBytes(columnMap);
    }
}
