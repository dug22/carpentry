/*
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *
 */

package io.github.dug22.carpentry.io.string;

import io.github.dug22.carpentry.DataFrameInterface;
import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.column.ColumnUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.github.dug22.carpentry.utils.DisplayBlocks.*;

public class DataFramePrinter extends Printer<DataFrameInterface, Map<String, Integer>> {

    /**
     * Displays the entire DataFrame by calling the show method with the full row count.
     *
     * @param dataFrame The DataFrame to be displayed
     */
    @Override
    public void show(DataFrameInterface dataFrame) {
        show(dataFrame, dataFrame.getRowCount());
    }

    /**
     * Displays the first DEFAULT_ROW_COUNT rows of the DataFrame.
     *
     * @param dataFrame The DataFrame to display the head of
     */
    @Override
    public void head(DataFrameInterface dataFrame) {
        head(dataFrame, DEFAULT_ROW_COUNT);
    }

    /**
     * Displays the first 'count' rows of the DataFrame, limited to the DataFrame's row count.
     *
     * @param dataFrame The DataFrame to display the head of
     * @param count     The number of rows to display
     */
    @Override
    public void head(DataFrameInterface dataFrame, int count) {
        show(dataFrame, Math.min(count, dataFrame.getRowCount()));
    }

    /**
     * Displays the last DEFAULT_ROW_COUNT rows of the DataFrame.
     *
     * @param dataFrame The DataFrame to display the tail of
     */
    @Override
    public void tail(DataFrameInterface dataFrame) {
        tail(dataFrame, DEFAULT_ROW_COUNT);
    }

    /**
     * Displays the last 'rowCount' rows of the DataFrame, starting from the appropriate index.
     *
     * @param dataFrame The DataFrame to display the tail of
     * @param rowCount  The number of rows to display
     */
    @Override
    public void tail(DataFrameInterface dataFrame, int rowCount) {
        int totalRows = dataFrame.getRowCount();
        int startRow = Math.max(0, totalRows - rowCount);
        showReverse(dataFrame, rowCount, startRow);
    }

    /**
     * Prints the DataFrame data starting from 'start' index for 'count' rows with borders.
     *
     * @param dataFrame The DataFrame to be printed
     * @param count     The number of rows to print
     * @param start     The starting index for printing
     */
    @Override
    protected void show(DataFrameInterface dataFrame, int count, int start) {
        Map<String, Integer> widths = calculateWidths(dataFrame, count, start);
        printTopBorder(widths);
        printHeaders(dataFrame, widths);
        printMiddleBorder(widths);
        printData(dataFrame, widths, count, start, false);
        printBottomBorder(widths);
    }

    /**
     * Prints the DataFrame data in reverse order starting from 'start' index for 'count' rows with borders.
     *
     * @param dataFrame The DataFrame to be printed
     * @param count     The number of rows to print
     * @param start     The starting index for printing
     */
    @Override
    protected void showReverse(DataFrameInterface dataFrame, int count, int start) {
        Map<String, Integer> widths = calculateWidths(dataFrame, count, start);
        printTopBorder(widths);
        printHeaders(dataFrame, widths);
        printMiddleBorder(widths);
        printData(dataFrame, widths, count, start, true);
        printBottomBorder(widths);
    }

    /**
     * Calculates the maximum width needed for each column based on the header and data content.
     *
     * @param dataFrame The DataFrame to calculate widths for
     * @param count     The number of rows to consider
     * @param start     The starting index for width calculation
     * @return A map of column names to their calculated widths including padding
     */
    @Override
    protected Map<String, Integer> calculateWidths(DataFrameInterface dataFrame, int count, int start) {
        Map<String, Integer> columnWidths = new LinkedHashMap<>();
        for (String columnName : dataFrame.columnNames()) {
            int maxWidth = columnName.length();
            Column<?> column = dataFrame.getColumn(columnName);
            for (int i = start; i < start + count && i < column.size(); i++) {
                int cellWidth = String.valueOf(column.get(i)).length();
                maxWidth = Math.max(maxWidth, cellWidth);
            }
            columnWidths.put(columnName, maxWidth + 2);
        }
        return columnWidths;
    }

    /**
     * Prints the top border of the table using the specified column widths.
     *
     * @param widths A map of column names to their widths
     */
    @Override
    protected void printTopBorder(Map<String, Integer> widths) {
        System.out.print(TOP_LEFT_CORNER);
        printHorizontalBorder(widths, TOP_INTERSECTION);
        System.out.println(TOP_RIGHT_CORNER);
    }

    /**
     * Prints the middle border of the table using the specified column widths.
     *
     * @param widths A map of column names to their widths
     */
    @Override
    protected void printMiddleBorder(Map<String, Integer> widths) {
        System.out.print(LEFT_INTERSECTION);
        printHorizontalBorder(widths, CROSS_INTERSECTION);
        System.out.println(RIGHT_INTERSECTION);
    }

    /**
     * Prints the bottom border of the table using the specified column widths.
     *
     * @param widths A map of column names to their widths
     */
    @Override
    protected void printBottomBorder(Map<String, Integer> widths) {
        System.out.print(BOTTOM_LEFT_CORNER);
        printHorizontalBorder(widths, BOTTOM_INTERSECTION);
        System.out.println(BOTTOM_RIGHT_CORNER);
    }

    /**
     * Prints the data rows of the DataFrame, either in normal or reverse order.
     *
     * @param dataFrame The DataFrame to print data from
     * @param widths    A map of column names to their widths
     * @param count     The number of rows to print
     * @param start     The starting index for printing
     * @param reverse   Whether to print in reverse order
     */
    @Override
    protected void printData(DataFrameInterface dataFrame, Map<String, Integer> widths, int count, int start, boolean reverse) {
        int maxRowCount = dataFrame.getRowCount();
        for (int i = 0; i < count && start + i < maxRowCount; i++) {
            int rowIndex = reverse ? maxRowCount - 1 - i : start + i;
            System.out.print(VERTICAL_LINE);
            for (String columnName : dataFrame.columnNames()) {
                Column<?> column = dataFrame.getColumn(columnName);
                Object cell = rowIndex < column.size() ? column.get(rowIndex) : null;
                String cellValue = ColumnUtils.format(cell, column);
                System.out.printf(" %-" + (widths.get(columnName) - 1) + "s" + VERTICAL_LINE, cellValue);
            }
            System.out.println();
        }
    }

    /**
     * Prints the headers of the DataFrame columns with the specified widths.
     *
     * @param dataFrame The DataFrame whose headers are to be printed
     * @param widths    A map of column names to their widths
     */
    @Override
    protected void printHeaders(DataFrameInterface dataFrame, Map<String, Integer> widths) {
        System.out.print(VERTICAL_LINE);
        for (String columnName : dataFrame.columnNames()) {
            System.out.printf(" %-" + (widths.get(columnName) - 1) + "s" + VERTICAL_LINE, columnName);
        }
        System.out.println();
    }

    /**
     * Prints a horizontal border for the table using the specified intersection character.
     *
     * @param widths       A map of column names to their widths
     * @param intersection The character to use at column intersections
     */
    private void printHorizontalBorder(Map<String, Integer> widths, String intersection) {
        for (Integer width : widths.values()) {
            System.out.print(HORIZONTAL_LINE.repeat(width));
            System.out.print(intersection);
        }
        System.out.print("\b");
    }
}