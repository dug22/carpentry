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

package io.github.dug22.carpentry.query;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.query.operator.QueryComparisonOperator;
import io.github.dug22.carpentry.query.operator.QueryLogicalOperator;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;

import java.util.BitSet;
import java.util.List;

public class QueryParser {

    private final List<QueryToken> tokens;
    private int currentPos;
    private final DefaultDataFrame dataFrame;

    /**
     * Constructor for QueryParser.
     * Initializes the parser with a DataFrame and a list of query tokens.
     *
     * @param dataFrame The DataFrame to be queried.
     * @param tokens The list of tokens representing the query.
     */
    public QueryParser(DefaultDataFrame dataFrame, List<QueryToken> tokens) {
        this.dataFrame = dataFrame;
        this.tokens = tokens;
        this.currentPos = 0;
    }

    /**
     * Starts the parsing process and returns the result as DataRows.
     *
     * @return The resulting DataRows after parsing the query.
     */
    public DataRows parse() {
        return parseExpression();
    }

    /**
     * Parses the expression by handling logical operators and primary expressions.
     *
     * @return The resulting DataRows after evaluating the expression.
     */
    private DataRows parseExpression() {
        DataRows result = parsePrimaryExpression();

        while (!isEndOfTokens()) {
            if (isNextToken(QueryTokenType.BOOLEAN)) {
                QueryToken operator = consumeToken();
                DataRows right = parsePrimaryExpression();
                result = QueryLogicalOperator.combineResults(result, right, operator);
            } else {
                break;
            }
        }

        return result;
    }

    /**
     * Parses primary expressions such as parentheses or comparison expressions.
     *
     * @return The result of parsing the primary expression.
     */
    private DataRows parsePrimaryExpression() {
        if (isEndOfTokens()) {
            throw new QueryException("Unexpected end of tokens");
        }

        if (isNextToken(QueryTokenType.LEFT_PARENTHESES)) {
            consumeToken();
            DataRows result = parseExpression();
            match(QueryTokenType.RIGHT_PARENTHESES);
            return result;
        }

        return parseComparisonExpression();
    }

    /**
     * Parses comparison expressions like column comparisons.
     *
     * @return The DataRows resulting from the comparison.
     */
    private DataRows parseComparisonExpression() {
        QueryToken column = match(QueryTokenType.COLUMN_NAME);
        QueryToken operator = match(QueryTokenType.COMPARISON_OPERATOR);
        QueryToken value = match(QueryTokenType.VALUE);
        return evaluateComparison(column, operator, value);
    }

    /**
     * Evaluates a comparison between a column and a value.
     *
     * @param column The column to compare.
     * @param operator The comparison operator.
     * @param value The value to compare with.
     * @return The DataRows that match the comparison.
     */
    private DataRows evaluateComparison(QueryToken column, QueryToken operator, QueryToken value) {
        String columnName = column.value().replace("'", "");
        AbstractColumn<?> dataColumn = dataFrame.getColumn(columnName);
        Object valueToCompare = parseValue(value.value(), dataColumn.getColumnType());
        int rowCount = dataColumn.size();
        BitSet keepIndices = new BitSet(rowCount);

        for (int i = 0; i < rowCount; i++) {
            if (isMatchingRow(dataColumn.get(i), operator.value(), valueToCompare)) {
                keepIndices.set(i);
            }
        }

        DataRows matchingRows = new DataRows(dataFrame);
        for (int i = keepIndices.nextSetBit(0); i >= 0; i = keepIndices.nextSetBit(i + 1)) {
            DataRow row = new DataRow();
            for (AbstractColumn<?> col : dataFrame.getColumnMap().values()) {
                row.append(col.getColumnName(), col.get(i));
            }
            matchingRows.add(row);
        }

        return matchingRows;
    }

    /**
     * Checks if a row's column value matches the comparison criteria.
     *
     * @param columnValue The column's value to check.
     * @param operatorValue The operator used in the comparison.
     * @param valueToCompare The value to compare against.
     * @return true if the row matches the comparison, otherwise false.
     */
    private boolean isMatchingRow(Object columnValue, String operatorValue, Object valueToCompare) {
        return QueryComparisonOperator.compare(columnValue, operatorValue, valueToCompare);
    }


    /**
     * Parses the value of a token and returns the corresponding object.
     *
     * @param value The value to parse.
     * @return The parsed value as an Object
     */
    private Object parseValue(String value, Class<?> columnType) {
        if (value == null) {
            return null;
        }
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value);
        }
        try {
            if (columnType == Integer.class) return Integer.parseInt(value);
            if (columnType == Double.class) return Double.parseDouble(value);
            if (columnType == Long.class) return Long.parseLong(value);
            if (columnType == Float.class) return Float.parseFloat(value);
            if (columnType == String.class) return value;
            if (columnType == Boolean.class) return Boolean.parseBoolean(value);
            if (columnType == Character.class && value.length() == 1) return value.charAt(0);
            return value;
        } catch (NumberFormatException e) {
            return value;
        }
    }

    /**
     * Matches the next token with the expected token type and consumes it.
     *
     * @param expectedType The expected type of the next token.
     * @return The matching token.
     */
    private QueryToken match(QueryTokenType expectedType) {
        if (isEndOfTokens()) {
            throw new QueryException("Unexpected end of tokens while expecting " + expectedType);
        }
        QueryToken token = tokens.get(currentPos);
        if (token.type() == expectedType) {
            return consumeToken();
        }
        throw new QueryException("Expected " + expectedType + " but got " + token.type() + " at position " + currentPos);
    }

    /**
     * Checks if the current position has reached the end of the token list.
     *
     * @return true if the end of tokens is reached, otherwise false.
     */
    private boolean isEndOfTokens() {
        return currentPos >= tokens.size();
    }

    /**
     * Checks if the next token matches the expected type.
     *
     * @param type The expected token type.
     * @return true if the next token matches the type, otherwise false.
     */
    private boolean isNextToken(QueryTokenType type) {
        return !isEndOfTokens() && tokens.get(currentPos).type() == type;
    }


    /**
     * Consumes the current token and advances the position.
     *
     * @return The consumed token.
     */
    private QueryToken consumeToken() {
        return tokens.get(currentPos++);
    }
}