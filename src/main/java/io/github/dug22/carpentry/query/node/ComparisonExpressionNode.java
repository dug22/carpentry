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
import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.column.ColumnType;
import io.github.dug22.carpentry.filter.FilterPredicate;
import io.github.dug22.carpentry.filter.FilterPredicateCondition;
import io.github.dug22.carpentry.query.QueryException;
import io.github.dug22.carpentry.utils.NumberMapperManager;

public class ComparisonExpressionNode implements QueryExpressionNode {
    private final String columnName;
    private final String operator;
    private final String value;

    public ComparisonExpressionNode(String columnName, String operator, String value) {
        this.columnName = columnName;
        this.operator = operator;
        this.value = value;
    }

    @SuppressWarnings("all")
    @Override
    public FilterPredicate toFilterPredicate(DataFrame dataFrame) {
        FilterPredicateCondition condition = dataFrame.column(columnName);
        Column<?> column = dataFrame.getColumn(columnName);
        ColumnType columnType = column.columnType();
        Class<?> columnTypeClazz = columnType.getClassType();
        return switch (columnTypeClazz.getName()) {
            case "java.lang.Boolean" -> {
                Boolean booleanValue = Boolean.parseBoolean(value);
                yield switch (operator) {
                    case "=" -> condition.objEq(booleanValue);
                    case "!=" -> condition.objNotEq(booleanValue);
                    default -> throw new QueryException("Unsupported operator for boolean column: " + operator);
                };
            }
            case "java.lang.Character" -> {
                Character characterValue = value.charAt(0);
                yield switch (operator) {
                    case "=" -> condition.objEq(characterValue);
                    case "!=" -> condition.objNotEq(characterValue);
                    default -> throw new QueryException("Unsupported operator for character column: " + operator);
                };
            }

            case "java.lang.String" -> switch (operator) {
                case "=" -> condition.objEq(value);
                case "!=" -> condition.objNotEq(value);
                case "~=" -> condition.matches(value);
                default -> throw new QueryException("Unsupported operator for string column: " + operator);
            };

            default -> {
                if (Number.class.isAssignableFrom(columnTypeClazz)) {
                    try {
                        for (NumberMapperManager.NumberMapperObject mapper : NumberMapperManager.getNumberMapperObjectList()) {
                            if (mapper.clazzType().isAssignableFrom(columnTypeClazz)) {
                                Comparable<?> numericValue = mapper.mapperFunction().apply(value);
                                yield switch (operator) {
                                    case "=" -> condition.eq((Comparable) numericValue);
                                    case "!=" -> condition.neq((Comparable) numericValue);
                                    case ">" -> condition.gt((Comparable) numericValue);
                                    case ">=" -> condition.gte((Comparable) numericValue);
                                    case "<" -> condition.lt((Comparable) numericValue);
                                    case "<=" -> condition.lte((Comparable) numericValue);
                                    default -> throw new QueryException("Unknown comparison operator: " + operator);
                                };
                            }
                        }
                    } catch (NumberFormatException e) {
                        throw new QueryException("Cannot map '" + value + "' to " + columnTypeClazz.getSimpleName() + ": " + e.getMessage());
                    }
                }
                throw new QueryException("Unsupported column type: " + columnTypeClazz.getSimpleName());
            }
        };
    }
}