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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryTokenizer {

    private final List<String> tokens = new ArrayList<>();
    private int position = 0;

    public QueryTokenizer(String queryExpression) {
        Pattern pattern = Pattern.compile("`[^`]+`|\\(|\\)|\\bAND\\b|\\bOR\\b|>=|<=|!=|=|<|>|~=");
        Matcher matcher = pattern.matcher(queryExpression);
        while (matcher.find()) {
            String token = matcher.group();
            if (!token.matches("\\s+")) {
                tokens.add(token);
            }
        }
    }

    public QueryToken toToken(String value) {
        switch (value) {
            case "AND" -> {
                return new QueryToken(QueryToken.TokenType.AND, value);
            }
            case "OR" -> {
                return new QueryToken(QueryToken.TokenType.OR, value);
            }
            case "(" -> {
                return new QueryToken(QueryToken.TokenType.LEFT_PARENTHESIS, value);
            }
            case ")" -> {
                return new QueryToken(QueryToken.TokenType.RIGHT_PARENTHESIS, value);
            }
            case ">=", "<=", "!=", "=", ">", "<", "~=" -> {
                return new QueryToken(QueryToken.TokenType.OPERATOR, value);
            }
            default -> {
                if (value.startsWith("`") && value.endsWith("`")) {
                    int valueLength = value.length();
                    String valueSubstring = value.substring(1, valueLength - 1);
                    return new QueryToken(QueryToken.TokenType.BACKTICK_VALUE, valueSubstring);
                }
                throw new QueryException("Unknown token: " + value);
            }
        }
    }

    public QueryToken next() {
        if (!hasNext()) {
            return new QueryToken(QueryToken.TokenType.EOF, "");
        }
        String nextToken = tokens.get(position++);
        return toToken(nextToken);
    }

    public boolean hasNext() {
        return position < tokens.size();
    }

    public QueryToken peek() {
        if (position < tokens.size()) {
            return toToken(tokens.get(position));
        }
        return new QueryToken(QueryToken.TokenType.EOF, "");
    }
}