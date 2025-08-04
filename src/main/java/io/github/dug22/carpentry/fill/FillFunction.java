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

package io.github.dug22.carpentry.fill;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.DataFrameFunction;
import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.column.ColumnType;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.stream.IntStream;

/**
 * Fills null values in specified DataFrame columns
 */
public class FillFunction extends DataFrameFunction {

    /**
     * Column-value pairs to fill nulls
     */
    private final FillColumnValuePair[] fillColumnValuePairs;

    private final DataFrame dataFrame = getDataFrame();

    /**
     * Constructor
     *
     * @param dataFrame            The DataFrame to operate on
     * @param fillColumnValuePairs Column-value mappings for filling
     */
    public FillFunction(DataFrame dataFrame, FillColumnValuePair[] fillColumnValuePairs) {
        super(dataFrame);
        this.fillColumnValuePairs = fillColumnValuePairs;
    }

    /**
     * Fills nulls in the specified columns with given values
     * Only fills if the value type matches the column type
     *
     * @param <T> Generic type of column elements
     * @return The modified DataFrame
     */
    @SuppressWarnings("unchecked")
    public <T> DataFrame fillNa() {
        for (FillColumnValuePair fillColumnValuePair : fillColumnValuePairs) {
            String columnName = fillColumnValuePair.columnName();
            Object dataValue = fillColumnValuePair.dataValue();
            Column<T> column = (Column<T>) dataFrame.getColumn(columnName);

            if (column != null) {
                IntStream.range(0, column.size()).forEach(index -> {
                    T currentValue = column.get(index);
                    ColumnType columnType = column.columnType();
                    Class<?> columnTypeClazz = columnType.getClassType();
                    Class<?> dataValueClazzType = dataValue.getClass();

                    if (Nulls.isNull(currentValue) && columnTypeClazz.isAssignableFrom(dataValueClazzType)) {
                        T typedFilledValue = (T) dataValue;
                        column.set(index, typedFilledValue);
                    }
                });
            }
        }

        return dataFrame;
    }
}
