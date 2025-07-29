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
import io.github.dug22.carpentry.join.JoinDetails;
import io.github.dug22.carpentry.join.JoinUtil;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;

public class LeftJoin extends BaseJoinStrategy {

    public LeftJoin(JoinUtil joinUtil) {
        super(joinUtil);
    }

    /**
     * Performs a left join
     * @param joinDetails The details specifying the join configuration
     * @return a left joined DataFrame
     */
    @Override
    public DataFrameInterface join(JoinDetails joinDetails) {
        DataFrameInterface result = joinUtil.createResultDataFrame(joinDetails);
        DataFrameInterface leftDataFrame = joinDetails.left();
        DataRows leftDataFrameRows = leftDataFrame.getRows();
        for (DataRow leftRow : leftDataFrameRows) {
            boolean matched = false;
            DataFrameInterface rightDataFrame = joinDetails.right();
            DataRows rightDataFrameRows = rightDataFrame.getRows();
            for (DataRow rightRow : rightDataFrameRows) {
                if (joinUtil.rowsMatch(leftRow, rightRow, joinDetails.joinColumns())) {
                    joinUtil.mergeRow(result, leftRow, rightRow, joinDetails);
                    matched = true;
                }
            }
            if (!matched) {
                joinUtil.mergeRow(result, leftRow, null, joinDetails);
            }
        }
        return result;
    }
}