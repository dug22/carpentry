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
import io.github.dug22.carpentry.column.ColumnCreator;
import io.github.dug22.carpentry.columns.NumberColumn;
import io.github.dug22.carpentry.grouping.aggregation.AggregationRecord;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;
import io.github.dug22.carpentry.sorting.RowColumnComparator;
import io.github.dug22.carpentry.sorting.SortColumn;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GroupByFunction {

    private final DefaultDataFrame dataFrame;
    private final boolean sort;
    private final String[] groupByColumns;
    private final GroupedData groupedData;

    public GroupByFunction(DefaultDataFrame dataFrame, boolean sort, String... groupByColumns) {
        this.dataFrame = dataFrame;
        this.sort = sort;
        this.groupByColumns = groupByColumns;
        this.groupedData = new GroupedData(dataFrame, groupByColumns);
    }

    /**
     * Groups the data by specified columns without any aggregation.
     * @return a new DataFrame with grouped rows.
     */
    public DefaultDataFrame groupOnly() {
        DefaultDataFrame result = DefaultDataFrame.create();
        for (String groupByColumn : groupByColumns) {
            result.addColumn(ColumnCreator.createNewColumnInstance(dataFrame.getColumn(groupByColumn)));
        }

        List<String> originalColumns = dataFrame.getColumnNames();
        for (String column : originalColumns) {
            if (!Arrays.asList(groupByColumns).contains(column)) {
                result.addColumn(ColumnCreator.createNewColumnInstance(dataFrame.getColumn(column)));
            }
        }

        DataRows allRows = new DataRows(dataFrame, dataFrame.getRows());

        if (sort) {

            SortColumn[] sortColumns = Arrays.stream(groupByColumns)
                    .map(column -> new SortColumn(column, SortColumn.Direction.ASCENDING))
                    .toArray(SortColumn[]::new);

            allRows.sort(new RowColumnComparator(sortColumns));
        }

        for (DataRow row : allRows) {
            result.addRow(row);
        }

        return result;
    }

    /**
     * Groups the data by specified columns and performs aggregation operations on other columns.
     * @param aggregationRecords an array of aggregation operations to perform.
     * @return a new DataFrame with the grouped and aggregated data.
     */
    public DefaultDataFrame aggregate(AggregationRecord[] aggregationRecords) {
        DefaultDataFrame result = DefaultDataFrame.create();
        for (String column : groupByColumns) {
            result.addColumn(ColumnCreator.createNewColumnInstance(dataFrame.getColumn(column)));
        }

        for (AggregationRecord aggregationRecord : aggregationRecords) {
            String newColumnName = aggregationRecord.columnName() + "_" + aggregationRecord.type().getFunctionName();
            result.addColumn(NumberColumn.create(newColumnName));
        }

        if (sort) groupedData.getGroups().sort(Comparator.naturalOrder());

        for (Group group : groupedData.getGroups()) {
            DataRow newRow = new DataRow();
            for (int i = 0; i < groupByColumns.length; i++) {
                newRow.append(groupByColumns[i], group.getKey()[i]);
            }

            for (AggregationRecord aggregationRecord : aggregationRecords) {
                List<Object> columnValues = group.getValuesForColumn(aggregationRecord.columnName());
                Object aggregatedValue = aggregationRecord.type().aggregate(columnValues);
                String newColName = aggregationRecord.columnName() + "_" + aggregationRecord.type().getFunctionName();
                newRow.append(newColName, aggregatedValue);
            }

            result.addRow(newRow);
        }

        return result;
    }
}
