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

package io.github.dug22.carpentry.join;

import io.github.dug22.carpentry.DataFrameInterface;

/**
 * A record representing the details of a join operation between two DataFrames.
 *
 * @param left The left DataFrame to join
 * @param right The right DataFrame to join
 * @param joinType The type of join to perform (e.g., INNER, LEFT, RIGHT, OUTER)
 * @param joinColumns An array of JoinColumn objects specifying the columns to join on
 * @param leftSuffix The suffix to append to column names from the left DataFrame to resolve naming conflicts
 * @param rightSuffix The suffix to append to column names from the right DataFrame to resolve naming conflicts
 */
public record JoinDetails(DataFrameInterface left, DataFrameInterface right, JoinType joinType, JoinColumn[] joinColumns,
                          String leftSuffix, String rightSuffix) {

    /**
     * Constructs a JoinDetails instance with the specified parameters, ensuring suffixes are non-null.
     *
     * @param left The left DataFrame to join
     * @param right The right DataFrame to join
     * @param joinType The type of join to perform
     * @param joinColumns An array of JoinColumn objects specifying the columns to join on
     * @param leftSuffix The suffix for left DataFrame column names, defaults to empty string if null
     * @param rightSuffix The suffix for right DataFrame column names, defaults to empty string if null
     */
    public JoinDetails(DataFrameInterface left, DataFrameInterface right, JoinType joinType,
                       JoinColumn[] joinColumns, String leftSuffix, String rightSuffix) {
        this.left = left;
        this.right = right;
        this.joinType = joinType;
        this.joinColumns = joinColumns;
        this.leftSuffix = leftSuffix != null ? leftSuffix : "";
        this.rightSuffix = rightSuffix != null ? rightSuffix : "";
    }
}

