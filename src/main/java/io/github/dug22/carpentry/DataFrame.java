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

package io.github.dug22.carpentry;

import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.column.ColumnException;
import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.column.ColumnTypes;
import io.github.dug22.carpentry.column.impl.*;
import io.github.dug22.carpentry.column.other.SkewnessFormula;
import io.github.dug22.carpentry.column.other.Statistics;
import io.github.dug22.carpentry.drop.DropFunction;
import io.github.dug22.carpentry.drop.How;
import io.github.dug22.carpentry.fill.FillColumnValuePair;
import io.github.dug22.carpentry.fill.FillFunction;
import io.github.dug22.carpentry.filter.FilterFunction;
import io.github.dug22.carpentry.filter.FilterPredicate;
import io.github.dug22.carpentry.filter.FilterPredicateCondition;
import io.github.dug22.carpentry.grouping.GroupByFunction;
import io.github.dug22.carpentry.io.DataFrameReader;
import io.github.dug22.carpentry.io.DataFrameWriter;
import io.github.dug22.carpentry.io.string.DataFramePrinter;
import io.github.dug22.carpentry.join.*;
import io.github.dug22.carpentry.join.impl.*;
import io.github.dug22.carpentry.query.QueryFunction;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;
import io.github.dug22.carpentry.sorting.SortColumn;
import io.github.dug22.carpentry.sorting.SortingFunction;
import io.github.dug22.carpentry.utils.Nulls;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class DataFrame implements DataFrameInterface {

    //The backbone of structuring the given DataFrame together
    private final ColumnMap columnMap = new ColumnMap();

    /**
     * Returns a new DataFrame
     */
    private DataFrame() {

    }

    /**
     * Returns a new DataFrame initialized with the given columns
     *
     * @param columns the provided columns
     */
    protected DataFrame(Column<?>... columns) {
        for (Column<?> column : columns) {
            this.addColumn(column);
        }
    }

    /**
     * Returns a new DataFrame initialized with the provided columns
     *
     * @param columns the provided columns
     */
    protected DataFrame(Collection<Column<?>> columns) {
        for (Column<?> column : columns) {
            this.addColumn(column);
        }
    }

    /**
     * Creates an empty DataFrame (no columns or rows)
     *
     * @return an empty DataFrame
     */
    public static DataFrame create() {
        return new DataFrame();
    }


    /**
     * Creates a DataFrame with the provided columns
     *
     * @return a new DataFrame initialized with the provided columns
     */
    public static DataFrame create(Column<?>... columns) {
        return new DataFrame(columns);
    }

    /**
     * Creates a DataFrame with the provided columns
     *
     * @return a new DataFrame initialized  with the provided columns
     */
    public static DataFrame create(Collection<Column<?>> columns) {
        return new DataFrame(columns);
    }


    public static DataFrameReader read() {
        return new DataFrameReader();
    }

    public DataFrameWriter write() {
        return new DataFrameWriter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame copy() {
        Collection<Column<?>> columnValues = columnMap.values();
        return create(columnValues);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame copyEmpty() {
        DataFrame copy = new DataFrame();
        for (Column<?> column : columnMap.values()) {
            copy.addColumn(column.emptyCopy());
        }

        return copy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame addBooleanColumn(String columnName, Boolean[] columnData) {
        this.addColumn(BooleanColumn.create(columnName, columnData));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame addByteColumn(String columnName, Byte[] columnData) {
        this.addColumn(ByteColumn.create(columnName, columnData));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame addCharacterColumn(String columnName, Character[] columnData) {
        this.addColumn(CharacterColumn.create(columnName, columnData));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrameInterface addDateColumn(String columnName, LocalDate[] columnData) {
        return this.addColumn(DateColumn.create(columnName, columnData));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrameInterface addDateColumn(String columnName, String[] columnData) {
        return this.addColumn(DateColumn.create(columnName, columnData));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrameInterface addDateTimeColumn(String columnName, LocalDateTime[] columnData) {
        return this.addColumn(DateTimeColumn.create(columnName, columnData));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrameInterface addDateTimeColumn(String columnName, String[] columnData) {
        return this.addColumn(DateTimeColumn.create(columnName, columnData));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame addDoubleColumn(String name, Double[] data) {
        this.addColumn(DoubleColumn.create(name, data));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame addFloatColumn(String name, Float[] data) {
        this.addColumn(FloatColumn.create(name, data));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame addIntegerColumn(String name, Integer[] data) {
        this.addColumn(IntegerColumn.create(name, data));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame addLongColumn(String name, Long[] data) {
        this.addColumn(LongColumn.create(name, data));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame addShortColumn(String name, Short[] data) {
        this.addColumn(ShortColumn.create(name, data));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame addStringColumn(String name, String[] data) {
        this.addColumn(StringColumn.create(name, data));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame addColumn(Column<?> column) {
        columnMap.addColumn(column);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame addColumns(Column<?>... columns) {
        Arrays.stream(columns).forEach(this::addColumn);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame insertColumn(int position, Column<?> column) {
        ColumnMap newMap = new ColumnMap();
        int currentIndex = 0;
        for (Column<?> existingColumn : columnMap.values()) {
            if (currentIndex == position) {
                newMap.addColumn(column);
            }
            newMap.addColumn(existingColumn);
            currentIndex++;
        }
        if (position == columnMap.size()) {
            newMap.addColumn(column);
        }
        columnMap.clear();
        for (Column<?> col : newMap.values()) {
            columnMap.addColumn(col);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame removeColumn(String columnName) {
        columnMap.remove(columnName);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame removeColumn(int position) {
        if (position < 0 || position >= columnMap.size()) {
            throw new IllegalArgumentException("Position " + position + " is out of bounds");
        }
        int currentIndex = 0;
        String columnNameToRemove = null;
        for (Column<?> column : columnMap.values()) {
            if (currentIndex == position) {
                columnNameToRemove = column.name();
                break;
            }
            currentIndex++;
        }
        if (columnNameToRemove != null) {
            columnMap.remove(columnNameToRemove);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsColumn(String columnName) {
        return columnMap.containsKey(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsColumnAtPosition(int position) {
        return position >= 0 && position < columnMap.size();
    }

    @Override
    public boolean containsColumns(String... columnNames) {
        return Arrays.stream(columnNames).allMatch(columnMap::containsKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsColumnsAtPositions(int... positions) {
        return Arrays.stream(positions).allMatch(pos -> pos >= 0 && pos < columnMap.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame replaceColumn(int position, Column<?> newColumn) {
        if (position < 0 || position >= columnMap.size()) {
            throw new IllegalArgumentException("Position " + position + " is out of bounds");
        }
        List<String> orderedNames = new ArrayList<>(columnMap.keySet());
        String columnNameToReplace = orderedNames.get(position);
        columnMap.remove(columnNameToReplace);
        orderedNames.set(position, newColumn.name());
        LinkedHashMap<String, Column<?>> newColumnMap = new LinkedHashMap<>();
        for (String name : orderedNames) {
            if (name.equals(newColumn.name())) {
                newColumnMap.put(name, newColumn);
            } else {
                newColumnMap.put(name, columnMap.get(name));
            }
        }
        columnMap.clear();
        columnMap.putAll(newColumnMap);
        return this;
    }


    public DataFrame replaceColumns(Map<Integer, Column<?>> indexMap){
        for(Map.Entry<Integer, Column<?>> entry : indexMap.entrySet()){
            replaceColumn(entry.getKey(), entry.getValue());
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumnCount() {
        return columnMap.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRowCount() {
        if (isEmpty()) {
            return 0;
        }
        return columnMap.values().iterator().next().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataRow getRow(int rowIndex) {
        return new DataRow(this, rowIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataRows getRows() {
        return new DataRows(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> DataFrame addRow(DataRow row) {
        for (Column<?> column : columnMap.values()) {
            T value = row.get(column.name());
            if (value == null) {
                column.appendNull();
            } else {
                Column<T> typedColumn = (Column<T>) column;
                typedColumn.append(value);
            }
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public DataFrame addRow(Map<String, Object> rowData) {
        for (Column<?> column : columnMap.values()) {
            Object value = rowData.getOrDefault(column.name(), null);
            ((Column<Object>) column).append(value);
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return columnMap.isEmpty();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public DataRows selectRows(String columnName, Object value) {
        return selectRows(column(columnName).objEq(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataRows selectRows(FilterPredicate predicate) {
        List<DataRow> dataRows = getRows().stream().filter(predicate::test).toList();
        DataFrame filteredDataFrame = DataFrame.create(columnMap.values()); // Create a copy of the original DataFrame
        return new DataRows(filteredDataFrame, dataRows);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanColumn booleanColumn(String name) {
        Column<?> column = columnMap.get(name);
        if (column.columnType() == ColumnTypes.BOOLEAN_COLUMN_TYPE) return (BooleanColumn) column;
        throw new ColumnException("Column " + name + " is not a Boolean Column");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteColumn byteColumn(String name) {
        Column<?> column = columnMap.get(name);
        if (column.columnType() == ColumnTypes.BYTE_COLUMN_TYPE) return (ByteColumn) column;
        throw new ColumnException("Column " + name + " is not a Byte Column");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharacterColumn charColumn(String name) {
        Column<?> column = columnMap.get(name);
        if (column.columnType() == ColumnTypes.CHARACTER_COLUMN_TYPE) return (CharacterColumn) column;
        throw new ColumnException("Column " + name + " is not a Character Column");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DateColumn dateColumn(String name) {
        Column<?> column = columnMap.get(name);
        if (column.columnType() == ColumnTypes.DATE_COLUMN_TYPE) return (DateColumn) column;
        throw new ColumnException("Column " + name + " is not a Double Column");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DateTimeColumn dateTimeColumn(String name) {
        Column<?> column = columnMap.get(name);
        if (column.columnType() == ColumnTypes.DATE_TIME_COLUMN_TYPE) return (DateTimeColumn) column;
        throw new ColumnException("Column " + name + " is not a Double Column");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleColumn doubleColumn(String name) {
        Column<?> column = columnMap.get(name);
        if (column.columnType() == ColumnTypes.DOUBLE_COLUMN_TYPE) return (DoubleColumn) column;
        throw new ColumnException("Column " + name + " is not a Double Column");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatColumn floatColumn(String name) {
        Column<?> column = columnMap.get(name);
        if (column.columnType() == ColumnTypes.FLOAT_COLUMN_TYPE) return (FloatColumn) column;
        throw new ColumnException("Column " + name + " is not a Float Column");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegerColumn intColumn(String name) {
        Column<?> column = columnMap.get(name);
        if (column.columnType() == ColumnTypes.INTEGER_COLUMN_TYPE) return (IntegerColumn) column;
        throw new ColumnException("Column " + name + " is not a Integer Column");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LongColumn longColumn(String name) {
        Column<?> column = columnMap.get(name);
        if (column.columnType() == ColumnTypes.LONG_COLUMN_TYPE) return (LongColumn) column;
        throw new ColumnException("Column " + name + " is not a Long Column");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortColumn shortColumn(String name) {
        Column<?> column = columnMap.get(name);
        if (column.columnType() == ColumnTypes.SHORT_COLUMN_TYPE) return (ShortColumn) column;
        throw new ColumnException("Column " + name + " is not a Short Column");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringColumn stringColumn(String name) {
        Column<?> column = columnMap.get(name);
        if (column.columnType() == ColumnTypes.STRING_COLUMN_TYPE) return (StringColumn) column;
        throw new ColumnException("Column " + name + " is not a String Column");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> columnNames() {
        return new ArrayList<>(columnMap.keySet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Column<?> getColumn(String name) {
        return columnMap.getColumn(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Column<?> getColumn(int index) {
        int indexCount = 0;
        for (Map.Entry<String, Column<?>> columnEntry : columnMap.entrySet()) {
            if (indexCount == index) {
                return columnEntry.getValue();
            }
            indexCount++;
        }

        throw new IndexOutOfBoundsException("Invalid column index: " + index);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        new DataFramePrinter().show(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void head() {
        new DataFramePrinter().head(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void head(int count) {
        new DataFramePrinter().head(this, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tail() {
        new DataFramePrinter().tail(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tail(int count) {
        new DataFramePrinter().tail(this, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame drop(Column<?> column) {
        DropFunction dropFunction = new DropFunction(this, column);
        return dropFunction.dropColumn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame drop(Column<?>... columns) {
        DropFunction dropFunction = new DropFunction(this, columns);
        return dropFunction.dropColumns();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame drop(String columnName) {
        DropFunction dropFunction = new DropFunction(this, columnName);
        return dropFunction.dropColumnsByColumnName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame drop(String... columnNames) {
        DropFunction dropFunction = new DropFunction(this, columnNames);
        return dropFunction.dropColumnsByColumnNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame drop(int columnIndex) {
        DropFunction dropFunction = new DropFunction(this, columnIndex);
        return dropFunction.dropColumnByColumnIndex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame drop(int... columnIndexes) {
        DropFunction dropFunction = new DropFunction(this, columnIndexes);
        return dropFunction.dropColumnByColumnIndexes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame dropNa(How how) {
        DropFunction dropFunction = new DropFunction(this, how);
        return dropFunction.dropNA();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame dropNa(String... columnNames) {
        DropFunction dropFunction = new DropFunction(this, columnNames);
        return dropFunction.dropNAByColumnNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame sort(SortColumn[] sortColumns) {
        SortingFunction sortingFunction = new SortingFunction(this, sortColumns);
        return sortingFunction.sort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame filter(FilterPredicate filterPredicate) {
        FilterFunction filterFunction = new FilterFunction(this);
        return filterFunction.filter(filterPredicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame where(FilterPredicate filterPredicate) {
        return filter(filterPredicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame query(String queryExpression) {
        QueryFunction queryFunction = new QueryFunction(this, queryExpression);
        return queryFunction.query();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame fillNa(FillColumnValuePair[] fillColumnValuePairs) {
        FillFunction fillFunction = new FillFunction(this, fillColumnValuePairs);
        return fillFunction.fillNa();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupByFunction groupBy(String... groupByColumns) {
        return new GroupByFunction(this, true, groupByColumns);
    }


    public DataFrame group(String... groupByColumns) {
        new GroupByFunction(this, true, groupByColumns);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupByFunction groupByUnsorted(String... groupByColumns) {
        return new GroupByFunction(this, false, groupByColumns);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame join(DataFrame other, JoinType joinType, String leftJoinColumn, String rightJoinColumn) {
        return join(other, joinType, new JoinColumn[]{new JoinColumn(leftJoinColumn, rightJoinColumn)}, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame join(DataFrame other, JoinType joinType, String leftJoinColumn, String rightJoinColumn,
                          String leftSuffix, String rightSuffix) {
        return join(other, joinType, new JoinColumn[]{new JoinColumn(leftJoinColumn, rightJoinColumn)},
                leftSuffix, rightSuffix);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame join(DataFrame other, JoinType joinType, JoinColumn[] joinColumns) {
        return join(other, joinType, joinColumns, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame join(DataFrame other, JoinType joinType, JoinColumn[] joinColumns,
                          String leftSuffix, String rightSuffix) {
        JoinDetails joinDetails = new JoinDetails(this, other, joinType, joinColumns, leftSuffix, rightSuffix);
        JoinUtil joinUtil = new DefaultJoinUtil();
        JoinStrategy joinOperation;
        switch (joinType) {
            case INNER -> joinOperation = new InnerJoin(joinUtil);
            case OUTER -> joinOperation = new OuterJoin(joinUtil);
            case RIGHT -> joinOperation = new RightJoin(joinUtil);
            case LEFT -> joinOperation = new LeftJoin(joinUtil);
            default -> throw new UnsupportedOperationException("Unknown join type: " + joinType);
        }
        return (DataFrame) joinOperation.join(joinDetails);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilterPredicateCondition column(String columnName) {
        return new FilterPredicateCondition(this, columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Column<?>> getColumns() {
        return columnMap.values();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String shape() {
        int rowCount = getRowCount();
        int columnCount = getColumnCount();
        return  "[" + rowCount + "," + columnCount + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame skew() {
        return skew(SkewnessFormula.FISHER_PEARSON);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataFrame skew(SkewnessFormula formula) {
        List<String> columnNames = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        for (Column<?> column : columnMap.values()) {
            if (column instanceof NumericColumn<?>) {
                columnNames.add(column.name());
                values.add(((Statistics) column).skew(formula));
            } else if (column instanceof BooleanColumn) {
                column = ((BooleanColumn) column).asDoubleColumn();
                columnNames.add(column.name());
                values.add(((Statistics) column).skew(formula));
            }
        }

        if (columnNames.isEmpty()) {
            throw new IllegalArgumentException("Your DataFrame must contain at least one numeric or boolean column.");
        }

        DataFrame result = DataFrame.create();
        for (int i = 0; i < columnNames.size(); i++) {
            result.addDoubleColumn(columnNames.get(i), new Double[]{values.get(i)});
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renameColumn(Map<String, String> renameMap) {
        List<String> columnNames = columnNames();
        for (String columnName : columnNames) {
            if (renameMap.containsKey(columnName)) {
                int index = columnMap.indexOfKey(columnName);
                Column<?> column = getColumn(columnName);
                column.setName(renameMap.get(columnName));
                replaceColumn(index, getColumn(columnName));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumnIndex(String columnName){
        return columnMap.indexOfKey(columnName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(){
        System.out.println("<class '" + DataFrame.class.getName() + "'>");
        int columnCount = getColumnCount();
        int rowCount = getRowCount();
        System.out.println("Range Index: " + rowCount + " entries, 0 to " + (rowCount - 1));
        System.out.println("Data columns (total " + columnCount + " columns):");
        System.out.printf("%-20s%-20s%-20s%-20s%n", "#", "Column", "Non-Null Count", "dType");
        System.out.printf("%-20s%-20s%-20s%-20s%n", "---", "------", "--------------", "-----");
        Collection<Column<?>> columns = getColumns();
        int columnIndex = 0;
        Map<String, Integer> dataTypesMap = new LinkedHashMap<>();
        for(Column<?> column : columns){
            String columnName = column.name();
            long nonNullCount = Arrays.stream(column.getValues()).filter(i -> !Nulls.isNull(i)).count();
            String dataType = column.columnType().getName();
            System.out.printf("%-20d%-20s%-20d%-20s%n", columnIndex, columnName, nonNullCount, dataType);
            dataTypesMap.put(dataType, dataTypesMap.getOrDefault(dataType, 0) + 1);
            columnIndex++;
        }

        StringBuilder typeSummary = new StringBuilder("dtypes: ");
        dataTypesMap.forEach((key, value) -> typeSummary.append(key).append("(").append(value).append("), "));
        if (!typeSummary.isEmpty()) {
            typeSummary.setLength(typeSummary.length() - 2);
        }
        System.out.println(typeSummary);

        System.out.println("DataFrame Shape: " + shape());
    }

    /**
     * Getter for columnMap
     *
     * @return the given ColumnMap
     */
    public ColumnMap getColumnMap() {
        return columnMap;
    }
}