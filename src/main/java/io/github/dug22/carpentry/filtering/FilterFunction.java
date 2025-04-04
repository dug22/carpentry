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


package io.github.dug22.carpentry.filtering;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;

public class FilterFunction {

    private final DefaultDataFrame dataFrame;

    public FilterFunction(DefaultDataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    /**
     * Filters rows in the DataFrame based on a provided predicate.
     *
     * @param predicate the condition to test each row against
     * @return a new DataFrame containing only the rows that match the predicate
     */
    public DefaultDataFrame filter(FilterPredicate predicate) {
        DataRows dataRows = new DataRows();
        for (DataRow dataRow : dataFrame.getRows()) {
            if (predicate.test(dataRow)) {
                dataRows.add(dataRow);
            }
        }

        DefaultDataFrame filteredDataFrame = dataFrame.copy();
        filteredDataFrame.clearRows();
        dataRows.forEach(filteredDataFrame::addRow);
        return filteredDataFrame;
    }
}