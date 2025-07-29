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

import io.github.dug22.carpentry.DataFrameInterface;
import io.github.dug22.carpentry.join.JoinColumn;
import io.github.dug22.carpentry.join.JoinDetails;
import io.github.dug22.carpentry.join.JoinUtil;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;

import java.util.ArrayList;
import java.util.List;

public class OuterJoin extends BaseJoinStrategy {
    public OuterJoin(JoinUtil joinUtil) {
        super(joinUtil);
    }

    /**
     * Performs an outer join
     * @param joinDetails The details specifying the join configuration
     * @return an outer joined DataFrame
     */
    @Override
    public DataFrameInterface join(JoinDetails joinDetails) {
        DataFrameInterface result = joinUtil.createResultDataFrame(joinDetails);
        DataFrameInterface leftDataFrame = joinDetails.left();
        DataFrameInterface rightDataFrame = joinDetails.right();
        DataRows leftDataFrameRows = leftDataFrame.getRows();
        DataRows rightDataFrameRows = rightDataFrame.getRows();
        int rightDataFrameRowsSize = rightDataFrameRows.size();
        List<Integer> matchedRightIndices = new ArrayList<>();
        JoinColumn[] joinColumns = joinDetails.joinColumns();


        for (DataRow leftRow : leftDataFrameRows) {
            boolean matched = false;
            for (int i = 0; i < rightDataFrameRowsSize && !matched; i++) {
                if (!matchedRightIndices.contains(i)) {
                    DataRow rightRow = rightDataFrameRows.get(i);
                    if (joinUtil.rowsMatch(leftRow, rightRow, joinColumns)) {
                        joinUtil.mergeRow(result, leftRow, rightRow, joinDetails);
                        matchedRightIndices.add(i);
                        matched = true;
                    }
                }
            }
            if (!matched) {
                joinUtil.mergeRow(result, leftRow, null, joinDetails);
            }
        }

        for (int i = 0; i < rightDataFrameRowsSize; i++) {
            if (!matchedRightIndices.contains(i)) {
                DataRow rightRow = rightDataFrameRows.get(i);
                joinUtil.mergeRow(result, null, rightRow, joinDetails);
            }
        }
        return result;
    }
}