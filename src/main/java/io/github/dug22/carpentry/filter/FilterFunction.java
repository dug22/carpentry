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

package io.github.dug22.carpentry.filter;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.DataFrameFunction;
import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.column.ColumnType;
import io.github.dug22.carpentry.row.DataRow;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilterFunction extends DataFrameFunction {


    public FilterFunction(DataFrame dataFrame) {
        super(dataFrame);
    }

    private DataFrame dataFrame = getDataFrame();

    @SuppressWarnings("unchecked")
    public <T> DataFrame filter(FilterPredicate filterPredicate) {
        List<Integer> keptIndexes = new ArrayList<>();
        ColumnMap columnMap = dataFrame.getColumnMap();
        Collection<Column<?>> columnValues = columnMap.values();
        int rowCount = dataFrame.getRowCount();
        for (int index = 0; index < rowCount; index++) {
            DataRow dataRow = new DataRow(dataFrame, index);
            if (filterPredicate.test(dataRow)) {
                keptIndexes.add(index);
            }
        }

        DataFrame newDataFrame = DataFrame.create();
        int keptIndexesSize = keptIndexes.size();
        for (Column<?> column : columnValues) {
            ColumnType columnType = column.columnType();
            Class<?> columnTypeClazz = columnType.getClassType();
            T[] data = (T[]) column.getValues();
            T[] newData = (T[]) Array.newInstance(columnTypeClazz, keptIndexesSize);
            for (int index = 0; index < keptIndexesSize; index++) {
                newData[index] = data[keptIndexes.get(index)];
            }
            Column<T> newColumn = (Column<T>) column.emptyCopy();
            newColumn.setData(newData);
            newDataFrame.addColumn(newColumn);
        }

        return newDataFrame;
    }
}