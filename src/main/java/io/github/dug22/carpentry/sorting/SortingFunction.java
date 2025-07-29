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

package io.github.dug22.carpentry.sorting;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.DataFrameFunction;
import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.column.ColumnType;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;

import java.lang.reflect.Array;
import java.util.Collection;

public class SortingFunction extends DataFrameFunction {

    private final SortColumn[] sortColumns;

    public SortingFunction(DataFrame dataFrame, SortColumn[] sortColumns){
        super(dataFrame);
        this.sortColumns = sortColumns;
    }

    private final DataFrame dataFrame = getDataFrame();

    @SuppressWarnings("unchecked")
    public <T> DataFrame sort(){
        DataRows dataRows = dataFrame.getRows();
        RowColumnComparator rowColumnComparator = new RowColumnComparator(sortColumns);
        dataRows.sort(rowColumnComparator);
        int dataRowsSize = dataRows.size();
        ColumnMap columnMap = dataFrame.getColumnMap();
        Collection<Column<?>> columnMapValues = columnMap.values();
        for(Column<?> column : columnMapValues){
            ColumnType columnType = column.columnType();
            Class<?> columnTypeClazz = columnType.getClassType();
            Object newDataValues = Array.newInstance(columnTypeClazz, dataRowsSize);
            for(int index = 0; index < dataRowsSize; index++){
                DataRow dataRow = dataRows.get(index);
                String columnName = column.name();
                Object dataValue = dataRow.get(columnName);
                Array.set(newDataValues, index, dataValue);
            }

            ((Column<T>)column).setData((T[]) newDataValues);
        }

        return dataFrame;
    }
}
