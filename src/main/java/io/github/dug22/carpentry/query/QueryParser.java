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

package io.github.dug22.carpentry.query;

import io.github.dug22.carpentry.query.node.ComparisonExpressionNode;
import io.github.dug22.carpentry.query.node.LogicalExpressionNode;
import io.github.dug22.carpentry.query.node.QueryExpressionNode;

public class QueryParser {

    private final QueryTokenizer tokenizer;

    public QueryParser(QueryTokenizer tokenizer){
        this.tokenizer = tokenizer;
    }

    public QueryExpressionNode parse(){

        return parseExpression(0);
    }

    private QueryExpressionNode parseExpression(int minPrecedence) {
        QueryExpressionNode left;

        QueryToken token = tokenizer.next();
        if (token.tokenType() == QueryToken.TokenType.EOF) {
            throw new QueryException("Unexpected end of input");
        }

        if (token.tokenType() == QueryToken.TokenType.LEFT_PARENTHESIS) {
            left = parseExpression(0);
            QueryToken next = tokenizer.next();
            if (next.tokenType() != QueryToken.TokenType.RIGHT_PARENTHESIS) {
                throw new QueryException("Expected closing parenthesis, found: " + (next.tokenType() == QueryToken.TokenType.EOF ? "EOF" : next.value()));
            }
        } else if (token.tokenType() == QueryToken.TokenType.BACKTICK_VALUE) {
            String column = token.value();
            QueryToken op = tokenizer.next();
            if (op.tokenType() == QueryToken.TokenType.EOF) {
                throw new QueryException("Expected operator after column name, found: EOF");
            }
            if (op.tokenType() != QueryToken.TokenType.OPERATOR) {
                throw new QueryException("Expected operator after column name, found: " + op.value());
            }
            QueryToken val = tokenizer.next();
            if (val.tokenType() == QueryToken.TokenType.EOF) {
                throw new QueryException("Expected value after operator, found: EOF");
            }
            if (val.tokenType() != QueryToken.TokenType.BACKTICK_VALUE) {
                throw new QueryException("Expected backtick-quoted value after operator, found: " + val.value());
            }
            left = new ComparisonExpressionNode(column, op.value(), val.value());
        } else {
            throw new QueryException("Unexpected token: " + (token.value().isEmpty() ? "empty" : token.value()));
        }

        while (true) {
            QueryToken next = tokenizer.peek();
            if (next.tokenType() == QueryToken.TokenType.EOF || next.tokenType() == QueryToken.TokenType.RIGHT_PARENTHESIS) {
                break;
            }
            int precedence = getPrecedence(next.tokenType());
            if (precedence < minPrecedence) {
                break;
            }

            tokenizer.next();
            QueryExpressionNode right = parseExpression(precedence + 1);
            left = new LogicalExpressionNode(next.value(), left, right);
        }

        return left;
    }

    private int getPrecedence(QueryToken.TokenType tokenType) {
        if(tokenType == QueryToken.TokenType.AND) return 2;
        else if (tokenType == QueryToken.TokenType.OPERATOR) return 1;
        else return 0;
    }
}
