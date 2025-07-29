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

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.DataFrameFunction;
import io.github.dug22.carpentry.filter.FilterPredicate;
import io.github.dug22.carpentry.query.node.QueryExpressionNode;

public class QueryFunction extends DataFrameFunction {

    private final String queryExpression;

    public QueryFunction(DataFrame dataFrame, String queryExpression){
        super(dataFrame);
        this.queryExpression = queryExpression;
    }

    private final DataFrame dataFrame = getDataFrame();

    public DataFrame query(){
        return dataFrame.where(parseFilter());
    }

    private FilterPredicate parseFilter(){
        QueryTokenizer tokenizer = new QueryTokenizer(queryExpression);
        QueryParser parser = new QueryParser(tokenizer);
        QueryExpressionNode queryExpressionNode = parser.parse();
        return queryExpressionNode.toFilterPredicate(dataFrame);
    }
}
