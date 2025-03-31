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

import io.github.dug22.carpentry.column.AbstractColumn;

import static io.github.dug22.carpentry.utility.DisplayBlocks.*;

public class ColumnPrinter extends Printer<AbstractColumn<?>> {

    @Override
    public void show(AbstractColumn<?> column) {
        show(column, column.size());
    }

    @Override
    public void head(AbstractColumn<?> column) {
        head(column, DEFAULT_ROW_COUNT);
    }

    @Override
    public void head(AbstractColumn<?> column, int count) {
        show(column, Math.min(count, column.size()));
    }

    @Override
    public void tail(AbstractColumn<?> column) {
        tail(column, DEFAULT_ROW_COUNT);
    }

    @Override
    public void tail(AbstractColumn<?> column, int count) {
        int total = column.size();
        int start = Math.max(0, total - count);
        showReverse(column, count, start);
    }


    private void show(AbstractColumn<?> column, int count) {
        show(column, count, 0);
    }

    @Override
    @SuppressWarnings("all")
    protected void show(AbstractColumn<?> column, int count, int start) {
        int columnWidth = calculateColumnWidth(column, count, start);
        printTopBorder(columnWidth);
        printColumnHeader(column.getColumnName(), columnWidth);
        printMiddleBorder(columnWidth);
        printData(column, columnWidth, count, start, false);
        printBottomBorder(columnWidth);
    }

    @Override
    protected void showReverse(AbstractColumn<?> column, int count, int start) {
        int columnWidth = calculateColumnWidth(column, count, start);
        printTopBorder(columnWidth);
        printColumnHeader(column.getColumnName(), columnWidth);
        printMiddleBorder(columnWidth);
        printData(column, columnWidth, count, start, true);
        printBottomBorder(columnWidth);
    }

    protected int calculateColumnWidth(AbstractColumn<?> column, int count, int start) {
        int maxWidth = column.getColumnName().length();
        for (int i = start; i < start + count && i < column.size(); i++) {
            int cellWidth = String.valueOf(column.get(i)).length();
            maxWidth = Math.max(maxWidth, cellWidth);
        }
        return maxWidth + 2;
    }

    private void printTopBorder(int columnWidth) {
        System.out.print(TOP_LEFT_CORNER);
        System.out.print(HORIZONTAL_LINE.repeat(columnWidth));
        System.out.println(TOP_RIGHT_CORNER);
    }

    private void printMiddleBorder(int columnWidth) {
        System.out.print(LEFT_INTERSECTION);
        System.out.print(HORIZONTAL_LINE.repeat(columnWidth));
        System.out.println(RIGHT_INTERSECTION);
    }

    private void printBottomBorder(int columnWidth) {
        System.out.print(BOTTOM_LEFT_CORNER);
        System.out.print(HORIZONTAL_LINE.repeat(columnWidth));
        System.out.println(BOTTOM_RIGHT_CORNER);
    }

    private void printColumnHeader(String columnName, int columnWidth) {
        System.out.print(VERTICAL_LINE);
        System.out.printf(" %-" + (columnWidth - 1) + "s" + VERTICAL_LINE, columnName);
        System.out.println();
    }

    private void printData(AbstractColumn<?> column, int columnWidth, int count, int start, boolean reverse) {
        int total = column.size();
        for (int i = 0; i < count && start + i < total; i++) {
            int index = reverse ? total - 1 - i : start + i;
            System.out.print(VERTICAL_LINE);
            System.out.printf(" %-" + (columnWidth - 1) + "s" + VERTICAL_LINE, column.get(index));
            System.out.println();
        }
    }

    private void appendTopBorder(StringBuilder sb, int columnWidth) {
        sb.append(TOP_LEFT_CORNER);
        sb.append(HORIZONTAL_LINE.repeat(columnWidth));
        sb.append(TOP_RIGHT_CORNER);
        sb.append("\n"); // Added newline to ensure header starts on a new line
    }

    private void appendMiddleBorder(StringBuilder sb, int columnWidth) {
        sb.append(LEFT_INTERSECTION);
        sb.append(HORIZONTAL_LINE.repeat(columnWidth));
        sb.append(RIGHT_INTERSECTION);
        sb.append("\n");
    }

    private void appendBottomBorder(StringBuilder sb, int columnWidth) {
        sb.append(BOTTOM_LEFT_CORNER);
        sb.append(HORIZONTAL_LINE.repeat(columnWidth));
        sb.append(BOTTOM_RIGHT_CORNER);
    }

    private void appendColumnHeader(StringBuilder sb, String columnName, int columnWidth) {
        sb.append(VERTICAL_LINE);
        sb.append(" ");
        sb.append(String.format("%-" + (columnWidth - 1) + "s", columnName));
        sb.append(VERTICAL_LINE);
        sb.append("\n");
    }

    private void appendData(StringBuilder sb, AbstractColumn<?> column, int columnWidth, int count, int start, boolean reverse) {
        int total = column.size();
        for (int i = 0; i < count && start + i < total; i++) {
            int index = reverse ? total - 1 - i : start + i;
            sb.append(VERTICAL_LINE);
            sb.append(String.format(" %-" + (columnWidth - 1) + "s" + VERTICAL_LINE, column.get(index)));
            sb.append("\n");
        }
    }

    public String toString(AbstractColumn<?> column, int count, int start, boolean reverse) {
        StringBuilder sb = new StringBuilder();
        int columnWidth = calculateColumnWidth(column, count, start);
        appendTopBorder(sb, columnWidth);
        appendColumnHeader(sb, column.getColumnName(), columnWidth);
        appendMiddleBorder(sb, columnWidth);
        appendData(sb, column, columnWidth, count, start, reverse);
        appendBottomBorder(sb, columnWidth);
        return sb.toString();
    }
}