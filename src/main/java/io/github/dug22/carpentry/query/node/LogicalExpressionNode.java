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

package io.github.dug22.carpentry.query.node;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.filter.FilterPredicate;
import io.github.dug22.carpentry.filter.FilterPredicateCondition;
import io.github.dug22.carpentry.query.QueryException;

public class LogicalExpressionNode implements QueryExpressionNode {

    private final String operator;
    private final QueryExpressionNode left, right;

    public LogicalExpressionNode(String operator, QueryExpressionNode left, QueryExpressionNode right){
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public FilterPredicate toFilterPredicate(DataFrame dataFrame) {
       return switch (operator){
            case "AND" -> FilterPredicateCondition.both(left.toFilterPredicate(dataFrame), right.toFilterPredicate(dataFrame));
            case "OR" -> FilterPredicateCondition.either(left.toFilterPredicate(dataFrame), right.toFilterPredicate(dataFrame));
            default -> throw new QueryException("Unknown logical operator: " + operator);
        };
    }
}
