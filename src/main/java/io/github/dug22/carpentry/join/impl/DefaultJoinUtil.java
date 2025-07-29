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

package io.github.dug22.carpentry.join.impl;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.DataFrameInterface;
import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.join.JoinColumn;
import io.github.dug22.carpentry.join.JoinDetails;
import io.github.dug22.carpentry.join.JoinUtil;
import io.github.dug22.carpentry.row.DataRow;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class DefaultJoinUtil implements JoinUtil {

    /**
     * Creates a resulting joined DataFrame with the implemented join details
     * @param joinDetails set of details about what dataframe you wish to join with and what kind of join you would like to perform
     * @return a resulting joined DataFrame with the implemented join details
     */
    @Override
    public DataFrame createResultDataFrame(JoinDetails joinDetails) {
        DataFrame result = DataFrame.create();
        DataFrameInterface leftDataFrame = joinDetails.left();
        DataFrameInterface rightDataFrame = joinDetails.right();
        JoinColumn[] joinColumns = joinDetails.joinColumns();
        String[] leftJoinColumns = new String[joinColumns.length];
        String[] rightJoinColumns = new String[joinColumns.length];
        IntStream.range(0, joinColumns.length)
                .forEach(index -> {
                    leftJoinColumns[index] = joinColumns[index].leftColumn();
                    rightJoinColumns[index] = joinColumns[index].rightColumn();
                });
        Collection<Column<?>> leftDataFrameColumns = leftDataFrame.getColumns();
        for (Column<?> column : leftDataFrameColumns) {
            String columnName = Arrays.stream(leftJoinColumns).noneMatch(column.name()::equals) ? column.name() + joinDetails.leftSuffix() : column.name();
            result.addColumn(createEmptyColumn(column, columnName));
        }

        Collection<Column<?>> rightDataFrameColumns = rightDataFrame.getColumns();
        for (Column<?> column : rightDataFrameColumns) {
            String columnName = column.name();
            if (Arrays.stream(rightJoinColumns).noneMatch(columnName::equals)) {
                columnName = columnName + joinDetails.rightSuffix();
                result.addColumn(createEmptyColumn(column, columnName));
            }
        }
        return result;
    }

    /**
     * Checks if two rows match based on the values in the specified join columns.
     * @param leftRow The row from the left DataFrame
     * @param rightRow The row from the right DataFrame
     * @param joinColumns The columns to compare for the join condition
     * @return true if the rows match on all join columns, false otherwise
     */
    @Override
    public boolean rowsMatch(DataRow leftRow, DataRow rightRow, JoinColumn[] joinColumns) {
        for (JoinColumn joinColumn : joinColumns) {
            Object leftValue = leftRow.get(joinColumn.leftColumn());
            Object rightValue = rightRow.get(joinColumn.rightColumn());
            if (!leftValue.equals(rightValue)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merges a row from the left and right DataFrames into the result DataFrame based on join details.
     * @param result The DataFrame to add the merged row to
     * @param leftRow The row from the left DataFrame, may be null for certain join types
     * @param rightRow The row from the right DataFrame, may be null for certain join types
     * @param joinDetails The details specifying the join configuration
     */
    @Override
    public void mergeRow(DataFrameInterface result, DataRow leftRow, DataRow rightRow, JoinDetails joinDetails) {
        Map<String, Object> rowData = new HashMap<>();
        for (Column<?> column : result.getColumns()) {
            String columnName = column.name();
            DataFrameInterface leftDataFrame = joinDetails.left();
            DataFrameInterface rightDataFrame = joinDetails.right();
            String baseName = columnName.replace(joinDetails.leftSuffix(), "").replace(joinDetails.rightSuffix(), "");

            if (leftRow != null && leftDataFrame.containsColumn(baseName)) {
                rowData.put(columnName, leftRow.get(baseName));
            } else if (rightRow != null && rightDataFrame.containsColumn(baseName)) {
                rowData.put(columnName, rightRow.get(baseName));
            } else {
                String joinColumnName = Arrays.stream(joinDetails.joinColumns())
                        .filter(jc -> jc.leftColumn().equals(baseName) || jc.rightColumn().equals(baseName))
                        .findFirst()
                        .map(JoinColumn::leftColumn)
                        .orElse(null);
                if (joinColumnName != null && leftRow != null) {
                    rowData.put(columnName, leftRow.get(joinColumnName));
                } else {
                    rowData.put(columnName, null);
                }
            }
        }
        result.addRow(rowData);
    }
}