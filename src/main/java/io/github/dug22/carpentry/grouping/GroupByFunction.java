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

package io.github.dug22.carpentry.grouping;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnCreator;
import io.github.dug22.carpentry.columns.NumberColumn;
import io.github.dug22.carpentry.grouping.aggregation.AggregationRecord;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GroupByFunction {
    private final DefaultDataFrame dataFrame;
    private final boolean sort;
    private final String[] groupByColumns;
    private final GroupedData groupedData;

    public GroupByFunction(DefaultDataFrame dataFrame, boolean sort, String... groupByColumns) {
        this.dataFrame = dataFrame;
        this.groupByColumns = groupByColumns;
        this.sort = sort;
        this.groupedData = new GroupedData(dataFrame, groupByColumns);
    }

    /**
     * Groups the data by specified columns and performs aggregation operations on other columns.
     * @param aggregationRecords an array of aggregation operations to perform.
     * @return a new DataFrame with the grouped and aggregated data.
     */
    @SuppressWarnings("unchecked")
    public DefaultDataFrame aggregate(AggregationRecord[] aggregationRecords) {
        DefaultDataFrame result = DefaultDataFrame.create();

        for (String column : groupByColumns) {
            result.addColumn(ColumnCreator.createNewColumnInstance(dataFrame.getColumn(column)));
        }

        List<String> aggColumnNames = new ArrayList<>();
        for (AggregationRecord agg : aggregationRecords) {
            String newColumnName = agg.columnName() + "_" + agg.type().getFunctionName();
            result.addColumn(NumberColumn.create(newColumnName));
            aggColumnNames.add(newColumnName);
        }

        List<Group> groups = groupedData.getGroups();
        int groupCount = groups.size();
        if (sort && groupCount > 1) {
            groups.sort(Comparator.naturalOrder());
        }

        for (AbstractColumn<?> column : result.getColumnMap().values()) {
            Object[] values = (Object[]) Array.newInstance(column.getColumnType(), groupCount);
            String columnName = column.getColumnName();
            int rowIndex = 0;

            int groupByIndex = -1;
            for (int i = 0; i < groupByColumns.length; i++) {
                if (groupByColumns[i].equals(columnName)) {
                    groupByIndex = i;
                    break;
                }
            }

            if (groupByIndex >= 0) {
                for (Group group : groups) {
                    values[rowIndex++] = group.getKey()[groupByIndex];
                }
            } else {
                int aggIndex = aggColumnNames.indexOf(columnName);
                if (aggIndex >= 0) {
                    AggregationRecord agg = aggregationRecords[aggIndex];
                    for (Group group : groups) {
                        List<Object> columnValues = group.getValuesForColumn(agg.columnName());
                        values[rowIndex++] = agg.type().aggregate(columnValues);
                    }
                }
            }
            ((AbstractColumn<Object>) column).setValues(values);
        }

        return result;
    }
}