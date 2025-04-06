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
import io.github.dug22.carpentry.drop.How;
import io.github.dug22.carpentry.fill.FillColumnValuePairs;
import io.github.dug22.carpentry.filtering.FilterPredicate;
import io.github.dug22.carpentry.filtering.FilterPredicateCondition;
import io.github.dug22.carpentry.grouping.GroupByFunction;
import io.github.dug22.carpentry.rename.RenameMap;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;
import io.github.dug22.carpentry.sorting.SortColumn;

import java.util.List;

public interface DataFrame {

    /**
     * Copies a given DataFrame.
     *
     * @return a new DataFrame that is an exact copy of the original DataFrame.
     */
    DataFrame copy();

    /**
     * Adds a column to a given DataFrame.
     *
     * @param column the specified column you want to add.
     * @return the current state of the DataFrame 
     */
    DataFrame addColumn(AbstractColumn<?> column);

    /**
     * Adds multiple columns to a given DataFrame.
     *
     * @param columns the specified columns you want to add.
     * @return the current state of the DataFrame 
     */
    DataFrame addColumns(AbstractColumn<?>... columns);

    /**
     * Adds a column to the DataFrame at a specific position.
     *
     * @param index  the position where the column will be inserted.
     * @param column the column you want to add.
     * @return the current state of the DataFrame 
     */
    DataFrame addColumnAtPosition(int index, AbstractColumn<?> column);

    /**
     * Adds a Boolean column to the DataFrame.
     *
     * @param columnName the name of the column.
     * @param values     the values to be inserted in the column.
     * @return the current state of the DataFrame 
     */
    DataFrame addBooleanColumn(String columnName, Boolean[] values);

    /**
     * Adds a Byte column to the DataFrame.
     *
     * @param columnName the name of the column.
     * @param values     the values to be inserted in the column.
     * @return the current state of the DataFrame 
     */
    DataFrame addByteColumn(String columnName, Byte[] values);

    /**
     * Adds a Character column to the DataFrame.
     *
     * @param columnName the name of the column.
     * @param values     the values to be inserted in the column.
     * @return the current state of the DataFrame 
     */
    DataFrame addCharacterColumn(String columnName, Character[] values);

    /**
     * Adds a Double column to the DataFrame.
     *
     * @param columnName the name of the column.
     * @param values     the values to be inserted in the column.
     * @return the current state of the DataFrame 
     */
    DataFrame addDoubleColumn(String columnName, Double[] values);

    /**
     * Adds a Float column to the DataFrame.
     *
     * @param columnName the name of the column.
     * @param values     the values to be inserted in the column.
     * @return the current state of the DataFrame 
     */
    DataFrame addFloatColumn(String columnName, Float[] values);

    /**
     * Adds an Integer column to the DataFrame.
     *
     * @param columnName the name of the column.
     * @param values     the values to be inserted in the column.
     * @return the current state of the DataFrame 
     */
    DataFrame addIntegerColumn(String columnName, Integer[] values);

    /**
     * Adds a Long column to the DataFrame.
     *
     * @param columnName the name of the column.
     * @param values     the values to be inserted in the column.
     * @return the current state of the DataFrame 
     */
    DataFrame addLongColumn(String columnName, Long[] values);

    /**
     * Adds a Number column to the DataFrame.
     *
     * @param columnName the name of the column.
     * @param values     the values to be inserted in the column.
     * @return the current state of the DataFrame 
     */
    DataFrame addNumberColumn(String columnName, Number[] values);

    /**
     * Adds a Short column to the DataFrame.
     *
     * @param columnName the name of the column.
     * @param values     the values to be inserted in the column.
     * @return the current state of the DataFrame 
     */
    DataFrame addShortColumn(String columnName, Short[] values);

    /**
     * Adds a String column to the DataFrame.
     *
     * @param columnName the name of the column.
     * @param values     the values to be inserted in the column.
     * @return the current state of the DataFrame 
     */
    DataFrame addStringColumn(String columnName, String[] values);

    /**
     * Removes a column from the DataFrame by its name.
     *
     * @param columnName the name of the column to be removed.
     * @return the current state of the DataFrame 
     */
    DataFrame removeColumn(String columnName);

    /**
     * Removes a column from the DataFrame by the column object.
     *
     * @param column the column to be removed.
     * @return the current state of the DataFrame 
     */
    DataFrame removeColumn(AbstractColumn<?> column);

    /**
     * Checks if a column exists in the DataFrame by its name.
     *
     * @param columnName the name of the column.
     * @return true if the column exists, false otherwise.
     */
    boolean containsColumn(String columnName);

    /**
     * Checks if multiple columns exist in the DataFrame.
     *
     * @param columnNames the names of the columns to check.
     * @return true if all specified columns exist, false otherwise.
     */
    boolean containsColumns(String... columnNames);

    /**
     * Adds a row to the DataFrame using the specified values.
     *
     * @param values the values for the new row.
     * @return the current state of the DataFrame 
     */
    @SuppressWarnings("all")
    DataFrame addRow(Object... values);

    /**
     * Allows you to add all data as a 2D Object Array
     * @param dataRows a 2D Object array where each inner array represents a row of data
     * @return the current state of the DataFrame with the new rows appended.
     */
    DefaultDataFrame addRows(Object[][] dataRows);

    /**
     * Adds a row to the DataFrame using a DataRow object.
     *
     * @param dataRow the DataRow object to be added.
     * @return the current state of the DataFrame 
     */
    @SuppressWarnings("all")
    DataFrame addRow(DataRow dataRow);

    /**
     * Retrieves all rows in the DataFrame.
     *
     * @return the rows in the DataFrame.
     */
    DataRows getRows();

    /**
     * Clears all rows in the DataFrame.
     *
     * @param <T> the type of the data in the DataFrame.
     */
    <T> void clearRows();

    /**
     * Retrieves a specific column by its name.
     *
     * @param columnName the name of the column to retrieve.
     * @return the column object.
     */
    AbstractColumn<?> getColumn(String columnName);

    /**
     * Retrieves a FilterPredicateCondition for a specified column.
     *
     * @param columnName the name of the column.
     * @return the FilterPredicateCondition for the column.
     */
    FilterPredicateCondition column(String columnName);

    /**
     * Retrieves all column names in the DataFrame.
     *
     * @return a list of column names.
     */
    List<String> getColumnNames();

    /**
     * Retrieves the Boolean column by its name.
     *
     * @param columnName the name of the column.
     * @return the Boolean column.
     */
    BooleanColumn getBooleanColumn(String columnName);

    /**
     * Retrieves the Byte column by its name.
     *
     * @param columnName the name of the column.
     * @return the Byte column.
     */
    ByteColumn getByteColumn(String columnName);

    /**
     * Retrieves the Character column by its name.
     *
     * @param columnName the name of the column.
     * @return the Character column.
     */
    CharacterColumn getCharacterColumn(String columnName);

    /**
     * Retrieves the Double column by its name.
     *
     * @param columnName the name of the column.
     * @return the Double column.
     */
    DoubleColumn getDoubleColumn(String columnName);

    /**
     * Retrieves the Float column by its name.
     *
     * @param columnName the name of the column.
     * @return the Float column.
     */
    FloatColumn getFloatColumn(String columnName);

    /**
     * Retrieves the Integer column by its name.
     *
     * @param columnName the name of the column.
     * @return the Integer column.
     */
    IntegerColumn getIntegerColumn(String columnName);

    /**
     * Retrieves the Long column by its name.
     *
     * @param columnName the name of the column.
     * @return the Long column.
     */
    LongColumn getLongColumn(String columnName);

    /**
     * Retrieves the Number column by its name.
     *
     * @param columnName the name of the column.
     * @return the Number column.
     */
    NumberColumn getNumberColum(String columnName);

    /**
     * Retrieves the Short column by its name.
     *
     * @param columnName the name of the column.
     * @return the Short column.
     */
    ShortColumn getShortColumn(String columnName);

    /**
     * Retrieves the String column by its name.
     *
     * @param columnName the name of the column.
     * @return the String column.
     */
    StringColumn getStringColumn(String columnName);

    /**
     * Prints the shape (rows and columns) of the DataFrame.
     */
    void shape();

    /**
     * Displays detailed row and column information of the DataFrame.
     */
    void rowColumnDetails();

    /**
     * Displays the size of the DataFrame in bytes.
     */
    void dataframeSizeInBytes();

    /**
     * Displays basic information about the DataFrame.
     */
    void info();

    /**
     * Displays the DataFrame.
     */
    void show();

    /**
     * Displays the first few rows (default 5) of the DataFrame.
     */
    void head();

    /**
     * Displays the first few rows of the DataFrame with a specified size.
     *
     * @param size the number of rows to display.
     */
    void head(int size);

    /**
     * Displays the last few rows (default 5) of the DataFrame.
     */
    void tail();

    /**
     * Displays the last few rows of the DataFrame with a specified size.
     *
     * @param size the number of rows to display.
     */
    void tail(int size);

    /**
     * Drops columns from the DataFrame by their names.
     *
     * @param columnNames the names of the columns to be dropped.
     * @return the current state of the DataFrame 
     */
    DataFrame drop(String... columnNames);

    /**
     * Drops columns from the DataFrame by their indexes.
     *
     * @param indexes the indexes of the columns to be dropped.
     * @return the current state of the DataFrame 
     */
    DataFrame drop(int... indexes);

    /**
     * Drops rows with missing values (NA) based on the specified strategy.
     *
     * @param how the strategy for handling missing values.
     * @return the current state of the DataFrame 
     */
    DataFrame dropNA(How how);

    /**
     * Drops rows with missing values (NA) from specific columns.
     *
     * @param columnNames the columns from which missing values should be dropped.
     * @return the current state of the DataFrame 
     */
    DataFrame dropNA(String... columnNames);

    /**
     * Fills missing values (NA) in the DataFrame with specified values.
     *
     * @param fillColumnValuePairs the mapping of column names to values for filling missing data.
     * @return the current state of the DataFrame 
     */
    DataFrame fillNA(FillColumnValuePairs fillColumnValuePairs);

    /**
     * Filters the rows of the DataFrame based on a condition.
     *
     * @param condition the filter condition.
     * @return the filtered DataFrame.
     */
    DataFrame filter(FilterPredicate condition);

    /**
     * Groups the DataFrame by specified columns.
     *
     * @param groupByColumns the columns to group by.
     * @return a new DataFrame grouped by the specified columns.
     */
    DefaultDataFrame groupBy(List<String> groupByColumns);

    /**
     * Groups the DataFrame by specified columns without sorting.
     *
     * @param groupByColumns the columns to group by.
     * @return a new DataFrame grouped by the specified columns.
     */
    DefaultDataFrame groupByUnsorted(List<String> groupByColumns);

    /**
     * Groups the DataFrame by specified columns and returns a GroupByFunction.
     *
     * @param groupByColumns the columns to group by.
     * @return the GroupByFunction for further operations.
     */
    GroupByFunction groupBy(String... groupByColumns);

    /**
     * Groups the DataFrame by specified columns without sorting and returns a GroupByFunction.
     *
     * @param groupByColumns the columns to group by.
     * @return the GroupByFunction for further operations.
     */
    GroupByFunction groupByUnsorted(String... groupByColumns);

    /**
     * Executes a query on the DataFrame.
     *
     * @param inputString the query string.
     * @return a new DataFrame with the query results.
     */
    DataFrame query(String inputString);

    /**
     * Renames columns in the DataFrame.
     *
     * @param renameMap the mapping of old column names to new column names.
     * @return the current state of the DataFrame 
     */
    DataFrame renameColumns(RenameMap renameMap);

    /**
     * Sorts the DataFrame by the specified columns.
     *
     * @param columns the columns to sort by.
     * @return a new DataFrame sorted by the specified columns.
     */
    DataFrame sort(SortColumn[] columns);

    /**
     * Sorts the DataFrame in parallel by the specified columns.
     *
     * @param columns the columns to sort by.
     * @return a new DataFrame sorted by the specified columns in parallel.
     */
    DataFrame sortInParallel(SortColumn[] columns);

    /**
     * Retrieves the column map of the DataFrame.
     *
     * @return the column map.
     */
    ColumnMap getColumnMap();

    /**
     * Gets the index of a column by its name.
     *
     * @param columnName the name of the column.
     * @return the index of the column.
     */
    default int getIndexOfColumn(String columnName) {
        return getColumnMap().indexOfKey(columnName);
    }

    /**
     * Gets the number of columns in the DataFrame.
     *
     * @return the number of columns.
     */
    default int getColumnCount() {
        return getColumnMap().size();
    }

    /**
     * Gets the number of rows in the DataFrame.
     *
     * @return the number of rows.
     */
    default int getRowCount() {
        return getRows().size();
    }

    /**
     * Appends a value to a specified column in the DataFrame.
     *
     * @param column the column to append the value to.
     * @param value  the value to append to the column.
     * @param <T>    the type of the column.
     */
    @SuppressWarnings("unchecked")
    default <T> void appendValueToColumn(AbstractColumn<T> column, Object value) {
        T castedValue = (T) value;
        column.append(castedValue);
    }
}