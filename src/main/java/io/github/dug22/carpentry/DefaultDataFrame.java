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

package io.github.dug22.carpentry;

import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.columns.*;
import io.github.dug22.carpentry.drop.DropFunction;
import io.github.dug22.carpentry.drop.How;
import io.github.dug22.carpentry.fill.FillColumnValuePairs;
import io.github.dug22.carpentry.fill.FillFunction;
import io.github.dug22.carpentry.filtering.FilterFunction;
import io.github.dug22.carpentry.filtering.FilterPredicate;
import io.github.dug22.carpentry.filtering.FilterPredicateCondition;
import io.github.dug22.carpentry.grouping.GroupByFunction;
import io.github.dug22.carpentry.io.DataFrameExporter;
import io.github.dug22.carpentry.io.csv.OptionalCSVHeaders;
import io.github.dug22.carpentry.io.csv.CSVReader;
import io.github.dug22.carpentry.io.string.DataFramePrinter;
import io.github.dug22.carpentry.query.QueryProcessor;
import io.github.dug22.carpentry.rename.RenameFunction;
import io.github.dug22.carpentry.rename.RenameMap;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;
import io.github.dug22.carpentry.sorting.SortColumn;
import io.github.dug22.carpentry.sorting.SortingFunction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DefaultDataFrame implements DataFrame {

    private final ColumnMap columnMap;

    public DefaultDataFrame(ColumnMap columnMap) {
        this.columnMap = columnMap;
    }

    public DefaultDataFrame() {
        this.columnMap = new ColumnMap();
    }

    public static DefaultDataFrame create() {
        return new DefaultDataFrame();
    }

    public static DefaultDataFrame create(ColumnMap columnMap) {
        return DataFrameFactory.create(columnMap);
    }

    public static DefaultDataFrame load(Path filePath) {
        return DataFrameFactory.loadFromCSVFile(filePath);
    }

    public static DefaultDataFrame load(Path filePath, String delimiter) {
        return DataFrameFactory.loadFromCSVFile(filePath, delimiter);
    }

    public static DefaultDataFrame load(Path filePath, OptionalCSVHeaders headers) {
        return DataFrameFactory.loadFromCSVFile(filePath, headers);
    }

    public static DefaultDataFrame load(Path filePath, OptionalCSVHeaders headers, String delimiter) {
        return DataFrameFactory.loadFromCSVFile(filePath, headers, delimiter);
    }

    public static DefaultDataFrame load(String url) {
        return DataFrameFactory.loadFromCSVUrl(url);
    }

    public static DefaultDataFrame load(String url, String delimiter) {
        return DataFrameFactory.loadFromCSVUrl(url, delimiter);
    }

    public static DefaultDataFrame load(String url, OptionalCSVHeaders headers) {
        return DataFrameFactory.loadFromCSVUrl(url, headers);
    }

    public static DefaultDataFrame load(String url, OptionalCSVHeaders headers, String delimiter) {
        return DataFrameFactory.loadFromCSVUrl(url, headers, delimiter);
    }

    public static DefaultDataFrame load(CSVReader reader) {
        return DataFrameFactory.loadFromCSVReader(reader);
    }


    public void saveAsCsv(String filePath) {
        DataFrameExporter.toCsv(columnMap, filePath);
    }

    public void saveAsCsv(String filePath, String delimiter) {
        DataFrameExporter.toCsv(columnMap, filePath, delimiter);
    }

    public void saveAsJson(String filePath) {
        DataFrameExporter.toJson(columnMap, filePath);
    }

    @Override
    public DefaultDataFrame copy() {
        ColumnMap copyColumnMap = new ColumnMap();
        columnMap.values().forEach(copyColumnMap::addColumn);
        return create(copyColumnMap);
    }


    @Override
    public DefaultDataFrame addColumn(AbstractColumn<?> column) {
        columnMap.addColumn(column);
        return this;
    }

    @Override
    public DefaultDataFrame addColumns(AbstractColumn<?>... columns) {
        Arrays.stream(columns).forEach(this::addColumn);
        return this;
    }

    @Override
    public DefaultDataFrame addColumnAtPosition(int index, AbstractColumn<?> column) {
        ColumnMap newColumnMap = new ColumnMap();
        int currentIndex = 0;
        for (String columnName : columnMap.keySet()) {
            if (currentIndex == index) {
                newColumnMap.addColumn(column);
            }

            newColumnMap.put(columnName, columnMap.getColumn(columnName));
            currentIndex++;
        }

        if (index == currentIndex) {
            newColumnMap.addColumn(column);
        }

        columnMap.clear();
        columnMap.putAll(newColumnMap);
        return this;
    }

    @Override
    public DefaultDataFrame addBooleanColumn(String columnName, Boolean[] values) {
        return addColumn(BooleanColumn.create(columnName, values));
    }

    @Override
    public DefaultDataFrame addByteColumn(String columnName, Byte[] values) {
        return addColumn(ByteColumn.create(columnName, values));
    }

    @Override
    public DefaultDataFrame addCharacterColumn(String columnName, Character[] values) {
        return addColumn(CharacterColumn.create(columnName, values));
    }

    @Override
    public DefaultDataFrame addDoubleColumn(String columnName, Double[] values) {
        return addColumn(DoubleColumn.create(columnName, values));
    }

    @Override
    public DefaultDataFrame addFloatColumn(String columnName, Float[] values) {
        return addColumn(FloatColumn.create(columnName, values));
    }

    @Override
    public DefaultDataFrame addIntegerColumn(String columnName, Integer[] values) {
        return addColumn(IntegerColumn.create(columnName, values));
    }

    @Override
    public DefaultDataFrame addLongColumn(String columnName, Long[] values) {
        return addColumn(LongColumn.create(columnName, values));
    }

    @Override
    public DefaultDataFrame addNumberColumn(String columnName, Number[] values) {
        return addColumn(NumberColumn.create(columnName, values));
    }

    @Override
    public DefaultDataFrame addShortColumn(String columnName, Short[] values) {
        return addColumn(ShortColumn.create(columnName, values));
    }

    @Override
    public DefaultDataFrame addStringColumn(String columnName, String[] values) {
        return addColumn(StringColumn.create(columnName, values));
    }

    @Override
    public DefaultDataFrame removeColumn(String columnName) {
        columnMap.removeColumn(columnName);
        return this;
    }

    @Override
    public boolean containsColumn(String columnName) {
        return columnMap.containsColumn(columnName);
    }

    @Override
    public boolean containsColumns(String... columnNames) {
        for (String columnName : columnNames) {
            return columnMap.containsColumn(columnName);
        }

        return false;
    }

    @Override
    public DefaultDataFrame removeColumn(AbstractColumn<?> column) {
        columnMap.values().remove(column);
        return this;
    }

    @Override
    public DefaultDataFrame addRow(Object... values) {
        if (values.length != columnMap.size()) {
            throw new IllegalArgumentException("Row size does not match number of columns.");
        }

        int i = 0;
        for (AbstractColumn<?> column : columnMap.values()) {
            Object value = values[i++];

            if (!column.getColumnType().isInstance(value)) {
                throw new IllegalArgumentException(
                        "Type mismatch: Value '" + value + "' is not compatible with column '" + column.getColumnName() + "'"
                );
            }

            appendValueToColumn(column, value);
        }
        return this;
    }

    @Override
    public DefaultDataFrame addRows(Object[]... dataRows) {
        for (Object[] row : dataRows) {
            addRow(row);
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DataFrame addRow(DataRow row) {
        for (AbstractColumn<?> column : columnMap.values()) {
            Object value = row.getRowData().get(column.getColumnName());
            if (value == null) {
                column.appendNull();
            } else {
                AbstractColumn<Object> typedColumn = (AbstractColumn<Object>) column;
                typedColumn.append(value);
            }
        }
        return this;
    }

    @Override
    public DataRows getRows() {
        List<DataRow> rows = new ArrayList<>();
        int rowCount = columnMap.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            DataRow row = new DataRow();
            for (AbstractColumn<?> column : columnMap.values()) {
                row.append(column.getColumnName(), column.get(i));
            }
            rows.add(row);
        }

        return new DataRows(this, rows);
    }

    @Override
    @SuppressWarnings("all")
    public <T> void clearRows() {
        for (AbstractColumn column : columnMap.values()) {
            T[] values = (T[]) java.lang.reflect.Array.newInstance(
                    column.getColumnType(),
                    0
            );
            column.setValues(values);
        }
    }

    @Override
    public AbstractColumn<?> getColumn(String columnName) {
        if (!columnMap.containsColumn(columnName)) {
            throw new IllegalArgumentException("No column found with name: " + columnName);
        }
        return columnMap.getColumn(columnName);
    }

    @Override
    public FilterPredicateCondition column(String columnName) {
        return new FilterPredicateCondition(columnName);
    }

    @Override
    public List<String> getColumnNames() {
        return new ArrayList<>(columnMap.keySet());
    }

    @Override
    public BooleanColumn getBooleanColumn(String columnName) {
        return (BooleanColumn) getColumn(columnName);
    }

    @Override
    public ByteColumn getByteColumn(String columnName) {
        return (ByteColumn) getColumn(columnName);
    }

    @Override
    public CharacterColumn getCharacterColumn(String columnName) {
        return (CharacterColumn) getColumn(columnName);
    }

    @Override
    public DoubleColumn getDoubleColumn(String columnName) {
        return (DoubleColumn) getColumn(columnName);
    }

    @Override
    public FloatColumn getFloatColumn(String columnName) {
        return (FloatColumn) getColumn(columnName);
    }

    @Override
    public IntegerColumn getIntegerColumn(String columnName) {
        return (IntegerColumn) getColumn(columnName);
    }

    @Override
    public LongColumn getLongColumn(String columnName) {
        return (LongColumn) getColumn(columnName);
    }

    @Override
    public NumberColumn getNumberColum(String columnName) {
        return (NumberColumn) getColumn(columnName);
    }

    @Override
    public ShortColumn getShortColumn(String columnName) {
        return (ShortColumn) getColumn(columnName);
    }

    @Override
    public StringColumn getStringColumn(String columnName) {
        return (StringColumn) getColumn(columnName);
    }

    @Override
    public void shape() {
        DataFrameDetails.shape(columnMap);
    }

    @Override
    public void rowColumnDetails() {
        DataFrameDetails.rowColumnDetails(columnMap);
    }

    @Override
    public void dataframeSizeInBytes() {
        DataFrameDetails.dataframeSizeInBytes(columnMap);
    }

    @Override
    public void info() {
        DataFrameDetails.info(columnMap);
    }

    @Override
    public void show() {
        new DataFramePrinter().show(this);
    }

    @Override
    public void head() {
        new DataFramePrinter().head(this);
    }

    @Override
    public void head(int count) {
        new DataFramePrinter().head(this, count);
    }

    @Override
    public void tail() {
        new DataFramePrinter().tail(this);
    }

    @Override
    public void tail(int count) {
        new DataFramePrinter().tail(this, count);
    }

    @Override
    public DefaultDataFrame drop(String... columnNames) {
        return new DropFunction(this).drop(columnNames);
    }

    @Override
    public DefaultDataFrame drop(int... indexes) {
        return new DropFunction(this).drop(indexes);
    }

    @Override
    public DefaultDataFrame dropNA(How how) {
        return new DropFunction(this).dropNA(how);
    }

    @Override
    public DefaultDataFrame dropNA(String... columnNames) {
        return new DropFunction(this).dropNA(columnNames);
    }

    @Override
    public DefaultDataFrame fillNA(FillColumnValuePairs fillColumnValuePairs) {
        return new FillFunction(this).fillNA(fillColumnValuePairs);
    }

    @Override
    public DefaultDataFrame filter(FilterPredicate condition) {
        return new FilterFunction(this).filter(condition);
    }

    public DefaultDataFrame groupBy(List<String> groupByColumns) {
        return new GroupByFunction(this, true, groupByColumns.toArray(new String[0])).groupOnly();
    }

    @Override
    public DefaultDataFrame groupByUnsorted(List<String> groupByColumns) {
        return new GroupByFunction(this, false, groupByColumns.toArray(new String[0])).groupOnly();
    }

    @Override
    public GroupByFunction groupBy(String... groupByColumns) {
        return new GroupByFunction(this, true, groupByColumns);
    }

    @Override
    public GroupByFunction groupByUnsorted(String... groupByColumns) {
        return new GroupByFunction(this, true, groupByColumns);
    }

    @Override
    public DefaultDataFrame query(String inputString) {
        return new QueryProcessor(this).query(inputString);
    }

    @Override
    public DefaultDataFrame renameColumns(RenameMap renameMap) {
        return new RenameFunction(this).renameColumns(renameMap);
    }

    @Override
    public DefaultDataFrame sort(SortColumn[] columns) {
        return new SortingFunction(this).sort(columns);
    }

    @Override
    public DefaultDataFrame sortInParallel(SortColumn[] columns) {
        return new SortingFunction(this).sortInParallel(columns);
    }

    @Override
    public ColumnMap getColumnMap() {
        return columnMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultDataFrame that = (DefaultDataFrame) o;
        return Objects.equals(columnMap, that.columnMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnMap);
    }
}
