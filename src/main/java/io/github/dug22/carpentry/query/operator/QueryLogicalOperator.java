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

package io.github.dug22.carpentry.query.operator;

import io.github.dug22.carpentry.query.QueryToken;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public enum QueryLogicalOperator {

    AND("AND", "&&", "and"),
    OR("OR", "||", "or");

    private final String[] values;

    /**
     * Constructor for QueryLogicalOperator.
     * Initializes the logical operator with its possible string representations.
     *
     * @param values The possible representations for the logical operator (e.g., "AND", "&&", "and").
     */
    QueryLogicalOperator(String... values) {
        this.values = values;
    }

    /**
     * Converts a string value to the corresponding QueryLogicalOperator.
     *
     * @param value The logical operator as a string.
     * @return The corresponding QueryLogicalOperator.
     * @throws IllegalArgumentException if the value doesn't match any known logical operator.
     */
    private static QueryLogicalOperator fromValue(String value) {
        for (QueryLogicalOperator operator : values()) {
            for (String opValue : operator.values) {
                if (opValue.equalsIgnoreCase(value)) {
                    return operator;
                }
            }
        }
        throw new IllegalArgumentException("Unsupported logical operator: " + value);
    }

    /**
     * Combines two sets of data rows based on the logical operator (AND/OR).
     *
     * @param left The first set of data rows.
     * @param right The second set of data rows.
     * @param operatorToken The logical operator token (AND or OR).
     * @return The combined set of data rows based on the logical operator.
     * @throws IllegalArgumentException if an unsupported logical operator is encountered.
     */
    public static DataRows combineResults(DataRows left, DataRows right, QueryToken operatorToken) {
        QueryLogicalOperator operator = fromValue(operatorToken.value());
        switch (operator) {
            case AND:
                left.retainAll(right);
                return left;

            case OR:
                Set<DataRow> combinedRows = new LinkedHashSet<>(left);
                combinedRows.addAll(right);
                return new DataRows(left.getDataFrame(), new ArrayList<>(combinedRows));

            default:
                throw new IllegalArgumentException("Unsupported logical operator: " + operatorToken.value());
        }
    }
}
