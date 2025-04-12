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
import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.row.DataRows;

import java.lang.reflect.Array;
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
    @SuppressWarnings("unchecked")
    public DefaultDataFrame query(String queryInput) {
        if (queryInput == null || queryInput.trim().isEmpty()) {
            return dataFrame;
        }

        List<QueryToken> tokens = new QueryTokenizer(queryInput).tokenize();
        DataRows dataRows = new QueryParser(dataFrame, tokens).parse();
        ColumnMap columnMap = dataFrame.getColumnMap();
        int newSize = dataRows.size();
        dataFrame.clearRows();
        if (newSize > 0) {
            for (AbstractColumn<?> column : columnMap.values()) {
                Object[] newValues = (Object[]) Array.newInstance(column.getColumnType(), newSize);
                String columnName = column.getColumnName();
                for (int i = 0; i < newSize; i++) {
                    newValues[i] = dataRows.get(i).getRowData().getOrDefault(columnName, null);
                }
                ((AbstractColumn<Object>) column).setValues(newValues);
            }
        }

        return dataFrame;
    }
}