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

package io.github.dug22.carpentry.query;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;

import java.util.List;

public class QueryProcessor {

    private final DefaultDataFrame dataFrame;

    public QueryProcessor(DefaultDataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    /**
     * Processes a query string, tokenizes it, parses it, and applies the results to the DataFrame.
     *
     * @param queryInput The query string to be executed.
     * @return The updated DataFrame after executing the query.
     */
    public DefaultDataFrame query(String queryInput) {
        QueryTokenizer tokenizer = new QueryTokenizer(queryInput);
        List<QueryToken> tokens = tokenizer.tokenize();
        QueryParser parser = new QueryParser(dataFrame, tokens);
        DataRows dataRows = parser.parse();
        dataFrame.clearRows();
        for (DataRow row : dataRows) {
            dataFrame.addRow(row);
        }
        return dataFrame;
    }
}