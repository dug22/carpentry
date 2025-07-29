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

package io.github.dug22.carpentry.column;

import io.github.dug22.carpentry.aggregation.AggregationType;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RollingColumn {
    private final Column<?> column;
    private final int window;

    /**
     * Constructs a RollingColumn instance for performing rolling window calculations.
     *
     * @param column the input column containing the data to process
     * @param window the size of the rolling window for calculations
     */
    public RollingColumn(Column<?> column, int window) {
        this.column = column;
        this.window = window;
    }

    /**
     * Performs a rolling aggregation on the column using the specified aggregation type.
     * @param <T>            the type of the column's data
     * @param aggregationType the type of aggregation to perform (e.g., sum, mean)
     * @return a new Column containing the results of the rolling aggregation
     * @throws IllegalArgumentException if the aggregation result type does not match the expected column type
     */
    @SuppressWarnings("unchecked")
    public <T> Column<?> calc(AggregationType aggregationType) {
        Column<T> result = (Column<T>) aggregationType.createEmptyAggregationColumn(
                column.name() + "_" + window + "_" + aggregationType.getFunctionName()
        );
        for (int i = 0; i < window - 1; i++) {
            result.appendNull();
        }
        Object[] data = column.getValues();
        for (int i = 0; i <= column.size() - window; i++) {
            List<Object> windowValues = new ArrayList<>(Arrays.asList(data).subList(i, i + window));
            Object aggResult = aggregationType.aggregate(windowValues);
            if (Nulls.isNull(aggResult)) {
                result.appendNull();
            } else if (aggregationType.columnType().getClassType().isInstance(aggResult)) {
                result.append((T) aggResult);
            } else {
                throw new IllegalArgumentException(
                        "Aggregation result type " + aggResult.getClass() +
                        " does not match expected type " + aggregationType.columnType().getClassType()
                );
            }
        }
        return result;
    }
}