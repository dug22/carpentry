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
 */

package io.github.dug22.carpentry.query;

import java.util.ArrayList;
import java.util.List;

public class QueryTokenizer {

    private final String input;
    private int pos;

    /**
     * Constructor for QueryTokenizer.
     * Initializes tokenizer with input query string.
     *
     * @param input The query string to be tokenized.
     */
    public QueryTokenizer(String input) {
        this.input = input.trim();
        this.pos = 0;
    }

    /**
     * Tokenizes the input query string into a list of query tokens.
     *
     * @return A list of QueryToken objects representing the parsed query.
     */
    public List<QueryToken> tokenize() {
        List<QueryToken> tokens = new ArrayList<>();
        int inputLength = input.length();
        while (pos < inputLength) {
            char c = input.charAt(pos);
            switch (c) {
                case ' ', '\t', '\n', '\r' -> pos++;
                case '\'' -> {
                    if (!tokens.isEmpty() && tokens.getLast().type() == QueryTokenType.COMPARISON_OPERATOR) {
                        tokens.add(parseQuotedValue());
                    } else {
                        tokens.add(parseQuotedColumn());
                    }
                }
                case '>', '<', '=', '!', '~' -> tokens.add(parseOperator());
                case '&', '|' -> tokens.add(parseLogicalOperator());
                case '(' -> {
                    tokens.add(new QueryToken(QueryTokenType.LEFT_PARENTHESES, "("));
                    pos++;
                }
                case ')' -> {
                    tokens.add(new QueryToken(QueryTokenType.RIGHT_PARENTHESES, ")"));
                    pos++;
                }
                default -> tokens.add(parseValue());
            }
        }
        tokens.add(new QueryToken(QueryTokenType.EOF, ""));
        return tokens;
    }

    /**
     * Parses a quoted column name (e.g., 'column_name').
     *
     * @return A QueryToken for the quoted column name.
     */
    private QueryToken parseQuotedColumn() {
        int start = pos + 1;
        pos = input.indexOf('\'', start);
        if (pos == -1) throw new QueryException("Unclosed quote at position " + start);
        String value = input.substring(start - 1, pos + 1);
        pos++;
        return new QueryToken(QueryTokenType.COLUMN_NAME, value);
    }

    /**
     * Parses a quoted value (e.g., 'value').
     *
     * @return A QueryToken for the quoted value.
     */
    private QueryToken parseQuotedValue() {
        int start = pos + 1;
        pos = input.indexOf('\'', start);
        if (pos == -1) throw new QueryException("Unclosed quote at position " + start);
        String value = input.substring(start, pos);
        pos++;
        return new QueryToken(QueryTokenType.VALUE, value);
    }

    /**
     * Parses comparison operators (e.g., >, <, ==, !=).
     *
     * @return A QueryToken for the comparison operator.
     */
    private QueryToken parseOperator() {
        StringBuilder op = new StringBuilder();
        op.append(input.charAt(pos++));
        if (pos < input.length() && (input.charAt(pos) == '=' || (op.charAt(0) == '~' && input.charAt(pos) == '='))) {
            op.append(input.charAt(pos++));
        }
        String operator = op.toString();
        return switch (operator) {
            case ">", ">=", "<", "<=", "==", "!=", "~=" -> new QueryToken(QueryTokenType.COMPARISON_OPERATOR, operator);
            default -> throw new QueryException("Invalid operator: " + operator);
        };
    }

    /**
     * Parses a value (e.g., number, string, etc.) from the query.
     *
     * @return A QueryToken for the value.
     */
    private QueryToken parseValue() {
        StringBuilder value = new StringBuilder();
        while (pos < input.length() && !Character.isWhitespace(input.charAt(pos)) && "> <=!~&|()".indexOf(input.charAt(pos)) == -1) {
            value.append(input.charAt(pos++));
        }
        String val = value.toString();
        if (val.equalsIgnoreCase("AND") || val.equalsIgnoreCase("OR")) {
            return new QueryToken(QueryTokenType.BOOLEAN, val.toUpperCase());
        }
        if (!val.startsWith("'") || !val.endsWith("'")) {
            throw new QueryException("Value must be quoted: " + val);
        }

        return new QueryToken(QueryTokenType.VALUE, val);
    }

    /**
     * Parses logical operators (e.g., &&, ||).
     *
     * @return A QueryToken for the logical operator.
     */
    private QueryToken parseLogicalOperator() {
        StringBuilder operator = new StringBuilder();
        while (pos < input.length() && "&|".indexOf(input.charAt(pos)) != -1) {
            operator.append(input.charAt(pos++));
        }
        String value = operator.toString();
        if (value.equals("&&")) return new QueryToken(QueryTokenType.BOOLEAN, "AND");
        if (value.equals("||")) return new QueryToken(QueryTokenType.BOOLEAN, "OR");
        throw new QueryException("Invalid logical operator: " + value);
    }
}