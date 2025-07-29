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

import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.column.ColumnUtils;

import static io.github.dug22.carpentry.utils.DisplayBlocks.*;

public class ColumnPrinter extends Printer<Column<?>, Integer> {

    /**
     * Displays the entire column by calling the show method with the full column size.
     * @param column The column to be displayed
     */
    @Override
    public void show(Column<?> column) {
        show(column, column.size());
    }

    /**
     * Displays the first DEFAULT_ROW_COUNT rows of the column.
     * @param column The column to display the head of
     */
    @Override
    public void head(Column<?> column) {
        head(column, DEFAULT_ROW_COUNT);
    }

    /**
     * Displays the first 'count' rows of the column, limited to the column's size.
     * @param column The column to display the head of
     * @param count The number of rows to display
     */
    @Override
    public void head(Column<?> column, int count) {
        show(column, Math.min(count, column.size()));
    }

    /**
     * Displays the last DEFAULT_ROW_COUNT rows of the column.
     * @param column The column to display the tail of
     */
    @Override
    public void tail(Column<?> column) {
        tail(column, DEFAULT_ROW_COUNT);
    }

    /**
     * Displays the last 'count' rows of the column, starting from the appropriate index.
     * @param column The column to display the tail of
     * @param count The number of rows to display
     */
    @Override
    public void tail(Column<?> column, int count) {
        int total = column.size();
        int start = Math.max(0, total - count);
        showReverse(column, count, start);
    }

    /**
     * Prints the column data starting from 'start' index for 'count' rows with borders.
     * @param column The column to be printed
     * @param count The number of rows to print
     * @param start The starting index for printing
     */
    @Override
    @SuppressWarnings("all")
    protected void show(Column<?> column, int count, int start) {
        int columnWidth = calculateWidths(column, count, start);
        printTopBorder(columnWidth);
        printHeaders(column, columnWidth);
        printMiddleBorder(columnWidth);
        printData(column, columnWidth, count, start, false);
        printBottomBorder(columnWidth);
    }

    /**
     * Prints the column data in reverse order starting from 'start' index for 'count' rows with borders.
     * @param column The column to be printed
     * @param count The number of rows to print
     * @param start The starting index for printing
     */
    @Override
    protected void showReverse(Column<?> column, int count, int start) {
        int columnWidth = calculateWidths(column, count, start);
        printTopBorder(columnWidth);
        printHeaders(column, columnWidth);
        printMiddleBorder(columnWidth);
        printData(column, columnWidth, count, start, true);
        printBottomBorder(columnWidth);
    }

    /**
     * Calculates the maximum width needed for the column based on the header and data content.
     * @param column The column to calculate width for
     * @param count The number of rows to consider
     * @param start The starting index for width calculation
     * @return The calculated width including padding
     */
    @Override
    protected Integer calculateWidths(Column<?> column, int count, int start) {
        int maxWidth = column.name().length();
        for (int i = start; i < start + count && i < column.size(); i++) {
            int cellWidth = String.valueOf(column.get(i)).length();
            maxWidth = Math.max(maxWidth, cellWidth);
        }

        return maxWidth + 2;
    }

    /**
     * Prints the top border of the table with the specified width.
     * @param widths The width of the column
     */
    @Override
    protected void printTopBorder(Integer widths) {
        System.out.print(TOP_LEFT_CORNER);
        System.out.print(HORIZONTAL_LINE.repeat(widths));
        System.out.println(TOP_RIGHT_CORNER);
    }

    /**
     * Prints the middle border of the table with the specified width.
     * @param widths The width of the column
     */
    @Override
    protected void printMiddleBorder(Integer widths) {
        System.out.print(LEFT_INTERSECTION);
        System.out.print(HORIZONTAL_LINE.repeat(widths));
        System.out.println(RIGHT_INTERSECTION);
    }

    /**
     * Prints the bottom border of the table with the specified width.
     * @param widths The width of the column
     */
    @Override
    protected void printBottomBorder(Integer widths) {
        System.out.print(BOTTOM_LEFT_CORNER);
        System.out.print(HORIZONTAL_LINE.repeat(widths));
        System.out.println(BOTTOM_RIGHT_CORNER);
    }

    /**
     * Prints the header of the column with the specified width.
     * @param column The column whose header is to be printed
     * @param widths The width of the column
     */
    @Override
    protected void printHeaders(Column<?> column, Integer widths) {
        System.out.print(VERTICAL_LINE);
        System.out.printf(" %-" + (widths - 1) + "s" + VERTICAL_LINE, column.name());
        System.out.println();
    }

    /**
     * Prints the data rows of the column, either in normal or reverse order.
     * @param column The column to print data from
     * @param widths The width of the column
     * @param count The number of rows to print
     * @param start The starting index for printing
     * @param reverse Whether to print in reverse order
     */
    @Override
    protected void printData(Column<?> column, Integer widths, int count, int start, boolean reverse) {
        int total = column.size();
        for (int i = 0; i < count && start + i < total; i++) {
            int index = reverse ? total - 1 - i : start + i;
            Object value = column.get(index);
            String cellString = ColumnUtils.format(value, column);
            System.out.print(VERTICAL_LINE);
            System.out.printf(" %-" + (widths - 1) + "s" + VERTICAL_LINE, cellString);
            System.out.println();
        }
    }
}
