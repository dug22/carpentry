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

package io.github.dug22.carpentry.io.string;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.github.dug22.carpentry.utility.DisplayBlocks.*;

public class DataFramePrinter extends Printer<DataFrame> {

    @Override
    public void show(DataFrame df) {
        show(df, df.getColumnMap().getRowCount());
    }

    @Override
    public void head(DataFrame df) {
        head(df, DEFAULT_ROW_COUNT);
    }

    @Override
    public void head(DataFrame df, int rowCount) {
        show(df, Math.min(rowCount, df.getColumnMap().getRowCount()));
    }

    @Override
    public void tail(DataFrame df) {
        tail(df, DEFAULT_ROW_COUNT);
    }

    @Override
    public void tail(DataFrame df, int rowCount) {
        int totalRows = df.getColumnMap().getRowCount();
        int startRow = Math.max(0, totalRows - rowCount);
        showReverse(df, rowCount, startRow);
    }

    private void show(DataFrame df, int rowCount) {
        show(df, rowCount, 0);
    }

    @Override
    @SuppressWarnings("all")
    protected void show(DataFrame df, int rowCount, int startRow) {
        ColumnMap columnMap = df.getColumnMap();
        List<String> columnNames = new ArrayList<>(columnMap.keySet());
        Map<String, Integer> columnWidths = calculateColumnWidths(columnMap, columnNames, rowCount, startRow);
        printTopBorder(columnWidths);
        printHeaderRow(columnNames, columnWidths);
        printMiddleBorder(columnWidths);
        printDataRows(columnMap, columnNames, columnWidths, rowCount, startRow, false);
        printBottomBorder(columnWidths);
    }

    @Override
    protected void showReverse(DataFrame df, int rowCount, int startRow) {
        ColumnMap columnMap = df.getColumnMap();
        List<String> columnNames = new ArrayList<>(columnMap.keySet());
        Map<String, Integer> columnWidths = calculateColumnWidths(columnMap, columnNames, rowCount, startRow);
        printTopBorder(columnWidths);
        printHeaderRow(columnNames, columnWidths);
        printMiddleBorder(columnWidths);
        printDataRows(columnMap, columnNames, columnWidths, rowCount, startRow, true);
        printBottomBorder(columnWidths);
    }

    protected Map<String, Integer> calculateColumnWidths(ColumnMap columnMap, List<String> columnNames, int rowCount, int startRow) {
        Map<String, Integer> columnWidths = new LinkedHashMap<>();
        for (String columnName : columnNames) {
            int maxWidth = columnName.length();
            AbstractColumn<?> column = columnMap.getColumn(columnName);
            for (int i = startRow; i < startRow + rowCount && i < column.size(); i++) {
                int cellWidth = String.valueOf(column.get(i)).length();
                maxWidth = Math.max(maxWidth, cellWidth);
            }
            columnWidths.put(columnName, maxWidth + 2); // Add padding
        }
        return columnWidths;
    }

    private void printTopBorder(Map<String, Integer> columnWidths) {
        System.out.print(TOP_LEFT_CORNER);
        printHorizontalBorder(columnWidths, TOP_INTERSECTION);
        System.out.println(TOP_RIGHT_CORNER);
    }

    private void printMiddleBorder(Map<String, Integer> columnWidths) {
        System.out.print(LEFT_INTERSECTION);
        printHorizontalBorder(columnWidths, CROSS_INTERSECTION);
        System.out.println(RIGHT_INTERSECTION);
    }

    private void printBottomBorder(Map<String, Integer> columnWidths) {
        System.out.print(BOTTOM_LEFT_CORNER);
        printHorizontalBorder(columnWidths, BOTTOM_INTERSECTION);
        System.out.println(BOTTOM_RIGHT_CORNER);
    }

    private void printHorizontalBorder(Map<String, Integer> columnWidths, String intersection) {
        for (Integer width : columnWidths.values()) {
            System.out.print(HORIZONTAL_LINE.repeat(width));
            System.out.print(intersection);
        }
        System.out.print("\b");
    }

    private void printHeaderRow(List<String> columnNames, Map<String, Integer> columnWidths) {
        System.out.print(VERTICAL_LINE);
        for (String columnName : columnNames) {
            System.out.printf(" %-" + (columnWidths.get(columnName) - 1) + "s" + VERTICAL_LINE, columnName);
        }
        System.out.println();
    }

    private void printDataRows(ColumnMap columnMap, List<String> columnNames, Map<String, Integer> columnWidths, int rowCount, int startRow, boolean reverse) {
        int maxRowCount = columnMap.getRowCount();

        for (int i = 0; i < rowCount && startRow + i < maxRowCount; i++) {
            int rowIndex = reverse ? maxRowCount - 1 - i : startRow + i;
            System.out.print(VERTICAL_LINE);
            for (String columnName : columnNames) {
                AbstractColumn<?> column = columnMap.getColumn(columnName);
                String cellValue = rowIndex < column.size() ? String.valueOf(column.get(rowIndex)) : null;
                System.out.printf(" %-" + (columnWidths.get(columnName) - 1) + "s" + VERTICAL_LINE, cellValue);
            }
            System.out.println();
        }
    }

    private void appendTopBorder(StringBuilder sb, Map<String, Integer> columnWidths) {
        sb.append(TOP_LEFT_CORNER);
        appendHorizontalBorder(sb, columnWidths, TOP_INTERSECTION);
        sb.append(TOP_RIGHT_CORNER).append("\n");
    }

    private void appendMiddleBorder(StringBuilder sb, Map<String, Integer> columnWidths) {
        sb.append(LEFT_INTERSECTION);
        appendHorizontalBorder(sb, columnWidths, CROSS_INTERSECTION);
        sb.append(RIGHT_INTERSECTION).append("\n");
    }

    private void appendBottomBorder(StringBuilder sb, Map<String, Integer> columnWidths) {
        sb.append(BOTTOM_LEFT_CORNER);
        appendHorizontalBorder(sb, columnWidths, BOTTOM_INTERSECTION);
        sb.append(BOTTOM_RIGHT_CORNER).append("\n");
    }

    private void appendHorizontalBorder(StringBuilder sb, Map<String, Integer> columnWidths, String intersection) {
        for (Integer width : columnWidths.values()) {
            sb.append(HORIZONTAL_LINE.repeat(width)).append(intersection);
        }
        sb.deleteCharAt(sb.length() - 1);
    }

    private void appendHeaderRow(StringBuilder sb, List<String> columnNames, Map<String, Integer> columnWidths) {
        sb.append(VERTICAL_LINE);
        for (String columnName : columnNames) {
            sb.append(String.format(" %-" + (columnWidths.get(columnName) - 1) + "s" + VERTICAL_LINE, columnName));
        }
        sb.append("\n");
    }

    private void appendDataRows(StringBuilder sb, ColumnMap columnMap, List<String> columnNames, Map<String, Integer> columnWidths, int rowCount, int startRow, boolean reverse) {
        int maxRowCount = columnMap.getRowCount();
        for (int i = 0; i < rowCount && startRow + i < maxRowCount; i++) {
            int rowIndex = reverse ? maxRowCount - 1 - i : startRow + i;
            sb.append(VERTICAL_LINE);
            for (String columnName : columnNames) {
                AbstractColumn<?> column = columnMap.getColumn(columnName);
                String cellValue = rowIndex < column.size() ? String.valueOf(column.get(rowIndex)) : null;
                sb.append(String.format(" %-" + (columnWidths.get(columnName) - 1) + "s" + VERTICAL_LINE, cellValue));
            }
            sb.append("\n");
        }
    }

    public String toString(DataFrame df, int rowCount, int startRow, boolean reverse) {
        StringBuilder sb = new StringBuilder();
        ColumnMap columnMap = df.getColumnMap();
        List<String> columnNames = new ArrayList<>(columnMap.keySet());
        Map<String, Integer> columnWidths = calculateColumnWidths(columnMap, columnNames, rowCount, startRow);
        appendTopBorder(sb, columnWidths);
        appendHeaderRow(sb, columnNames, columnWidths);
        appendMiddleBorder(sb, columnWidths);
        appendDataRows(sb, columnMap, columnNames, columnWidths, rowCount, startRow, reverse);
        appendBottomBorder(sb, columnWidths);
        return sb.toString();
    }
}