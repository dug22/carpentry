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

package io.github.dug22.carpentry.query.operator;

public enum QueryComparisonOperator {

    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL(">="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL("<="),
    EQUAL("=="),
    NOT_EQUAL("!="),
    MATCHES("~=");

    private final String symbol;


    /**
     * Constructor for QueryComparisonOperator.
     * Initializes the operator with its symbol.
     *
     * @param symbol The symbol representing the comparison operator.
     */
    QueryComparisonOperator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Compares two objects using the specified operator.
     *
     * @param left The left object to compare.
     * @param operator The comparison operator.
     * @param right The right object to compare.
     * @return true if the comparison is successful, false otherwise.
     */
    public static boolean compare(Object left, String operator, Object right) {
        for (QueryComparisonOperator comparisonOperator : values()) {
            if (comparisonOperator.symbol.equals(operator)) {
                return comparisonOperator.evaluate(left, right);
            }
        }

        return false;
    }

    /**
     * Evaluates the comparison between two objects based on the operator.
     *
     * @param left The left object to compare.
     * @param right The right object to compare.
     * @return true if the comparison holds, false otherwise.
     */
    @SuppressWarnings("unchecked")
    private boolean evaluate(Object left, Object right) {
        if (left == null || right == null) {
            return left == null && right == null;
        }

        Comparable<Object> comparableLeft = (Comparable<Object>) left;
        return switch (this) {
            case GREATER_THAN -> comparableLeft.compareTo(right) > 0;
            case GREATER_THAN_OR_EQUAL -> comparableLeft.compareTo(right) >= 0;
            case LESS_THAN -> comparableLeft.compareTo(right) < 0;
            case LESS_THAN_OR_EQUAL -> comparableLeft.compareTo(right) <= 0;
            case EQUAL -> {
                if (left instanceof Number leftValue && right instanceof Number rightValue) {
                    yield leftValue.doubleValue() == rightValue.doubleValue();
                }
                yield left.equals(right);
            }
            case NOT_EQUAL -> {
                if (left instanceof Number leftValue && right instanceof Number rightValue) {
                    yield leftValue.doubleValue() != rightValue.doubleValue();
                }
                yield !left.equals(right);
            }
            case MATCHES -> ((String) left).matches((String) right);
        };
    }
}
