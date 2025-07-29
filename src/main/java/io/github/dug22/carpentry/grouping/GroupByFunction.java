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

package io.github.dug22.carpentry.grouping;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.DataFrameFunction;
import io.github.dug22.carpentry.aggregation.AggregationEntry;
import io.github.dug22.carpentry.aggregation.AggregationType;
import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.column.impl.BooleanColumn;
import io.github.dug22.carpentry.column.impl.DoubleColumn;
import io.github.dug22.carpentry.column.impl.IntegerColumn;
import io.github.dug22.carpentry.column.impl.NumericColumn;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class GroupByFunction extends DataFrameFunction {

    private final boolean sort;
    private final String[] groupByColumns;
    private final GroupedData groupedData;

    public GroupByFunction(DataFrame dataFrame, boolean sort, String... groupByColumns) {
        super(dataFrame);
        this.groupByColumns = groupByColumns;
        this.sort = sort;
        this.groupedData = new GroupedData(dataFrame, groupByColumns);
    }

    private final DataFrame dataFrame = getDataFrame();

    /**
     * Performs aggregation on grouped data based on specified aggregation entries.
     * @param aggregationEntries The aggregation operations to perform
     * @return A new DataFrame containing the grouped and aggregated results
     */
    public DataFrame aggregate(AggregationEntry... aggregationEntries) {
        DataFrame newDataFrame = DataFrame.create();
        Arrays.stream(groupByColumns)
                .map(column -> dataFrame.getColumn(column).emptyCopy())
                .forEach(newDataFrame::addColumn);
        List<String> aggregationColumnNames = Arrays.stream(aggregationEntries)
                .map(entry -> {
                    String columnName = entry.columnName() + "_" + entry.type().getFunctionName();
                    if (entry.type() == AggregationType.COUNT ||
                        entry.type() == AggregationType.TRUE_COUNT ||
                        entry.type() == AggregationType.FALSE_COUNT) {
                        newDataFrame.addColumn(IntegerColumn.create(columnName));
                    } else {
                        newDataFrame.addColumn(DoubleColumn.create(columnName));
                    }
                    return columnName;
                })
                .toList();
        List<Group> groups = groupedData.getGroups();
        if (sort && groups.size() > 1) groups.sort(Comparator.naturalOrder());
        newDataFrame.getColumns().forEach(column -> populateColumnData(column, groups, aggregationEntries, aggregationColumnNames));
        return newDataFrame;
    }

    /**
     * Performs aggregation on numeric column, and boolean column types using the specified aggregation type.
     * @param defaultType The aggregation type to apply (e.g., SUM, COUNT)
     * @return A DataFrame containing the grouped and aggregated results
     */
    public DataFrame aggregate(AggregationType defaultType) {
        AggregationEntry[] autoEntries = dataFrame.getColumns().stream()
                .filter(col -> Arrays.stream(groupByColumns).noneMatch(g -> g.equals(col.name())))
                .filter(col -> col instanceof NumericColumn<?> || col instanceof BooleanColumn)
                .map(col -> {
                    AggregationType type = (defaultType == AggregationType.COUNT)
                            ? AggregationType.SUM
                            : defaultType;
                    return new AggregationEntry(col.name(), type);
                })
                .toArray(AggregationEntry[]::new);
        return aggregate(autoEntries);
    }

    /**
     * Populates the data for a column in the aggregated DataFrame based on group data and aggregation entries.
     * @param column The column to populate data for
     * @param groups The list of groups to process
     * @param aggregationEntries The aggregation operations to apply
     * @param aggregationColumnNames The names of the aggregated columns
     * @param <T> The type of the column data
     */
    @SuppressWarnings("unchecked")
    private <T> void populateColumnData(Column<?> column, List<Group> groups, AggregationEntry[] aggregationEntries, List<String> aggregationColumnNames) {
        T[] values = (T[]) Array.newInstance(column.columnType().getClassType(), groups.size());
        String columnName = column.name();
        int groupByIndex = IntStream.range(0, groupByColumns.length)
                .filter(i -> groupByColumns[i].equals(columnName))
                .findFirst()
                .orElse(-1);
        if (groupByIndex >= 0) {
            IntStream.range(0, groups.size()).forEach(index -> values[index] = (T) groups.get(index).getKey()[groupByIndex]);
        } else {
            int aggIndex = aggregationColumnNames.indexOf(columnName);
            if (aggIndex >= 0) {
                AggregationEntry agg = aggregationEntries[aggIndex];
                for (int index = 0; index < groups.size(); index++) {
                    values[index] = (T) agg.type().aggregate(groups.get(index).getColumnData(agg.columnName()));
                }
            }
        }
        ((Column<T>) column).setData(values);
    }

    /**
     * Returns the grouped data resulting from the grouping operation.
     * @return The given grouped data.
     */
    public GroupedData getGroupedData() {
        return groupedData;
    }
}
