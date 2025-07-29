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
import io.github.dug22.carpentry.column.impl.*;
import io.github.dug22.carpentry.column.other.SkewnessFormula;
import io.github.dug22.carpentry.drop.How;
import io.github.dug22.carpentry.fill.FillColumnValuePair;
import io.github.dug22.carpentry.filter.FilterPredicate;
import io.github.dug22.carpentry.filter.FilterPredicateCondition;
import io.github.dug22.carpentry.grouping.GroupByFunction;
import io.github.dug22.carpentry.join.JoinColumn;
import io.github.dug22.carpentry.join.JoinType;
import io.github.dug22.carpentry.row.DataRow;
import io.github.dug22.carpentry.row.DataRows;
import io.github.dug22.carpentry.sorting.SortColumn;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DataFrameInterface {

    /**
     * Copies a DataFrame instance
     *
     * @return a new copy of a DataFrame instance
     */
    DataFrameInterface copy();

    /**
     * Copies an empty copy of a DataFrame instance
     *
     * @return a new empty copy of a DataFrame instance
     */
    DataFrameInterface copyEmpty();

    /**
     * Adds a boolean column to the given DataFrame
     *
     * @param columnName the name of the column
     * @param columnData the data of the given column
     * @return a new DataFrame with the added boolean column.
     */
    DataFrameInterface addBooleanColumn(String columnName, Boolean[] columnData);

    /**
     * Adds a byte column to the given DataFrame
     *
     * @param columnName the name of the column
     * @param columnData the data of the given column
     * @return a new DataFrame with the added byte column.
     */
    DataFrameInterface addByteColumn(String columnName, Byte[] columnData);

    /**
     * Adds a character column to the given DataFrame
     *
     * @param columnName the name of the column
     * @param columnData the data of the given column
     * @return a new DataFrame with the added character column.
     */
    DataFrameInterface addCharacterColumn(String columnName, Character[] columnData);

    /**
     * Adds a date column to the given DataFrame
     *
     * @param columnName the name of the column
     * @param columnData the data of the given column
     * @return a new DataFrame with the added date column.
     */
    DataFrameInterface addDateColumn(String columnName, LocalDate[] columnData);

    /**
     * Adds a date column to the given DataFrame
     *
     * @param columnName the name of the column
     * @param columnData the data of the given column
     * @return a new DataFrame with the added date column.
     */
    DataFrameInterface addDateColumn(String columnName, String[] columnData);

    /**
     * Adds a date time column to the given DataFrame
     *
     * @param columnName the name of the column
     * @param columnData the data of the given column
     * @return a new DataFrame with the added date time column.
     */
    DataFrameInterface addDateTimeColumn(String columnName, LocalDateTime[] columnData);

    /**
     * Adds a date time column to the given DataFrame
     *
     * @param columnName the name of the column
     * @param columnData the data of the given column
     * @return a new DataFrame with the added date time column.
     */
    DataFrameInterface addDateTimeColumn(String columnName, String[] columnData);


    /**
     * Adds a double column to the given DataFrame
     *
     * @param columnName the name of the column
     * @param columnData the data of the given column
     * @return a new DataFrame with the added double column.
     */
    DataFrameInterface addDoubleColumn(String columnName, Double[] columnData);

    /**
     * Adds a float column to the given DataFrame
     *
     * @param columnName the name of the column
     * @param columnData the data of the given column
     * @return a new DataFrame with the added float column.
     */
    DataFrameInterface addFloatColumn(String columnName, Float[] columnData);

    /**
     * Adds an integer column to the given DataFrame
     *
     * @param columnName the name of the column
     * @param columnData the data of the given column
     * @return a new DataFrame with the added integer column.
     */
    DataFrameInterface addIntegerColumn(String columnName, Integer[] columnData);

    /**
     * Adds a long column to the given DataFrame
     *
     * @param columnName the name of the column
     * @param columnData the data of the given column
     * @return a DataFrame with the added long column.
     */
    DataFrameInterface addLongColumn(String columnName, Long[] columnData);

    /**
     * Adds a byte column to the given DataFrame
     *
     * @param columnName the name of the column
     * @param columnData the data of the given column
     * @return a new DataFrame with the added short column.
     */
    DataFrameInterface addShortColumn(String columnName, Short[] columnData);

    /**
     * Adds a string column to the given DataFrame
     *
     * @param columnName the name of the column
     * @param columnData the data of the given column
     * @return a new DataFrame with the added string column.
     */
    DataFrameInterface addStringColumn(String columnName, String[] columnData);

    /**
     * Adds a column of any type to the given DataFrame
     *
     * @param column the column you wish to add to the DataFrame
     * @return a new DataFrame with the added column
     */
    DataFrameInterface addColumn(Column<?> column);

    /**
     * Adds multiple columns of any type to the given DataFrame
     *
     * @param columns the columns you wish to add to the DataFrame
     * @return a new DataFrame with the added columns
     */
    DataFrameInterface addColumns(Column<?>... columns);

    /**
     * Inserts a column at a specific position into the given DataFrame.
     *
     * @param position the index where the column will be inserted.
     * @param column   the column to insert.
     * @return a new DataFrame with the inserted column.
     */
    DataFrameInterface insertColumn(int position, Column<?> column);

    /**
     * Removes a column from the given DataFrame based on the column name
     *
     * @param columnName the name of the column
     * @return a DataFrame with that column removed.
     */
    DataFrameInterface removeColumn(String columnName);

    /**
     * Removes a column from the given DataFrame based on the column's position
     *
     * @param position the index where the column is located.
     * @return a DataFrame with that column removed.
     */
    DataFrameInterface removeColumn(int position);

    /**
     * Checks to see if a DataFrame contains a specific column name.
     *
     * @param columnName the name of the column.
     * @return true if the column exists, false otherwise.
     */
    boolean containsColumn(String columnName);

    /**
     * Checks if a column exists at the given position.
     *
     * @param position the index of the column.
     * @return true if a column exists at the position, false otherwise.
     */
    boolean containsColumnAtPosition(int position);

    /**
     * Checks if the DataFrame contains all specified column names.
     *
     * @param columnNames an array of column names to check.
     * @return true if all specified columns exist, false otherwise.
     */
    boolean containsColumns(String... columnNames);

    /**
     * Checks if columns exist at all specified positions.
     *
     * @param positions an array of column indices to check.
     * @return true if columns exist at all specified positions, false otherwise.
     */
    boolean containsColumnsAtPositions(int... positions);

    /**
     * Replaces an existing column at a specific position with a new column.
     *
     * @param position  the index of the column to replace.
     * @param newColumn the new column to insert.
     * @return a new DataFrameInterface with the column replaced.
     */
    DataFrameInterface replaceColumn(int position, Column<?> newColumn);

    /**
     * Gets the total number of columns in the DataFrame.
     *
     * @return the number of columns.
     */
    int getColumnCount();

    /**
     * Retrieves a specific row from the DataFrame by its index.
     *
     * @param rowIndex the index of the row to retrieve.
     * @return the DataRow at the specified index.
     */
    DataRow getRow(int rowIndex);

    /**
     * Retrieves all rows from the DataFrame.
     *
     * @return a DataRows object containing all rows.
     */
    DataRows getRows();

    /**
     * Adds a new row to the end of the DataFrame.
     *
     * @param row the DataRow to add.
     * @return a new DataFrame with the added row.
     */
    <T> DataFrameInterface addRow(DataRow row);

    DataFrameInterface addRow(Map<String, Object> rowData);

    /**
     * Gets the total number of rows in the DataFrame.
     *
     * @return the number of rows.
     */
    int getRowCount();

    /**
     * Checks if the DataFrame contains no rows.
     *
     * @return true if the DataFrame is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Selects rows that match a specific column name and value.
     *
     * @param columnName the name of the column to match.
     * @param value      the value to match.
     * @return a DataRows object containing the matching rows.
     */
    DataRows selectRows(String columnName, Object value);

    /**
     * Selects rows that satisfy a filter predicate.
     *
     * @param filterPredicate the predicate to apply.
     * @return a DataRows object containing the matching rows.
     */
    DataRows selectRows(FilterPredicate filterPredicate);

    /**
     * Retrieves a boolean column by its name.
     *
     * @param name the name of the boolean column.
     * @return the BooleanColumn with the specified name.
     */
    BooleanColumn booleanColumn(String name);

    /**
     * Retrieves a byte column by its name.
     *
     * @param name the name of the byte column.
     * @return the ByteColumn with the specified name.
     */
    ByteColumn byteColumn(String name);

    /**
     * Retrieves a character column by its name.
     *
     * @param name the name of the character column.
     * @return the CharacterColumn with the specified name.
     */
    CharacterColumn charColumn(String name);

    /**
     * Retrieves a date column by its name.
     *
     * @param name the name of the date column.
     * @return the DateColumn with the specified name.
     */
    DateColumn dateColumn(String name);

    /**
     * Retrieves a date time column by its name.
     *
     * @param name the name of the date time column.
     * @return the DateTimeColumn with the specified name.
     */
    DateTimeColumn dateTimeColumn(String name);

    /**
     * Retrieves a double column by its name.
     *
     * @param name the name of the double column.
     * @return the DoubleColumn with the specified name.
     */
    DoubleColumn doubleColumn(String name);

    /**
     * Retrieves a float column by its name.
     *
     * @param name the name of the float column.
     * @return the FloatColumn with the specified name.
     */
    FloatColumn floatColumn(String name);

    /**
     * Retrieves an integer column by its name.
     *
     * @param name the name of the integer column.
     * @return the IntegerColumn with the specified name.
     */
    IntegerColumn intColumn(String name);

    /**
     * Retrieves a long column by its name.
     *
     * @param name the name of the long column.
     * @return the LongColumn with the specified name.
     */
    LongColumn longColumn(String name);

    /**
     * Retrieves a short column by its name.
     *
     * @param name the name of the short column.
     * @return the ShortColumn with the specified name.
     */
    ShortColumn shortColumn(String name);

    /**
     * Retrieves a string column by its name.
     *
     * @param name the name of the string column.
     * @return the StringColumn with the specified name.
     */
    StringColumn stringColumn(String name);

    /**
     * Gets a list of all column names in the DataFrame.
     *
     * @return a list of column names.
     */
    List<String> columnNames();

    /**
     * Retrieves a column by its name.
     *
     * @param name the name of the column to retrieve.
     * @return the Column with the specified name.
     */
    Column<?> getColumn(String name);

    /**
     * Retrieves a column by its index.
     *
     * @param index the index of the column to retrieve.
     * @return the Column with the specified index.
     */
    Column<?> getColumn(int index);

    /**
     * Displays the entire DataFrame.
     */
    void show();

    /**
     * Displays the first 5 rows of the DataFrame.
     */
    void head();

    /**
     * Displays a specified number of first rows of the DataFrame.
     *
     * @param count the number of rows to display from the head.
     */
    void head(int count);

    /**
     * Displays the last 5 rows of the DataFrame.
     */
    void tail();

    /**
     * Displays a specified number of last rows of the DataFrame.
     *
     * @param count the number of rows to display from the tail.
     */
    void tail(int count);

    /**
     * Drops the specified column from the DataFrame.
     *
     * @param column the column to drop.
     * @return a new DataFrame without the dropped column.
     */
    DataFrameInterface drop(Column<?> column);

    /**
     * Drops the specified columns from the DataFrame.
     *
     * @param columns the columns to drop.
     * @return a new DataFrame without the dropped columns.
     */
    DataFrameInterface drop(Column<?>... columns);

    /**
     * Drops the column with the specified name from the DataFrame.
     *
     * @param columnName the name of the column to drop.
     * @return a new DataFrame without the dropped column.
     */
    DataFrameInterface drop(String columnName);


    /**
     * Drops the columns with the specified names from the DataFrame.
     *
     * @param columnNames the names of the columns to drop.
     * @return a new DataFrame without the dropped columns.
     */
    DataFrameInterface drop(String... columnNames);

    /**
     * Drops the column at the specified index.
     *
     * @param columnIndex the index of the column to drop.
     * @return a new DataFrame without the dropped column.
     */
    DataFrameInterface drop(int columnIndex);

    /**
     * Drops the columns at the specified indexes.
     *
     * @param columnIndexes the indexes of the columns to drop.
     * @return a new DataFrame without the dropped columns.
     */
    DataFrameInterface drop(int... columnIndexes);

    /**
     * Drops rows with null values based on the specified strategy.
     *
     * @param how the strategy to determine which rows to drop.
     * @return a new DataFrame without the rows containing nulls.
     */
    DataFrameInterface dropNa(How how);

    /**
     * Drops rows with null values in the specified columns.
     *
     * @param columnNames the names of the columns to consider for dropping rows.
     * @return a new DataFrame without the rows containing nulls in the specified columns.
     */
    DataFrameInterface dropNa(String... columnNames);

    /**
     * Sorts the DataFrame based on one or more columns.
     *
     * @param sortColumns an array of SortColumn objects specifying sorting criteria.
     * @return a new DataFrame sorted according to the specified columns.
     */
    DataFrameInterface sort(SortColumn[] sortColumns);

    /**
     * Filters rows in the DataFrame based on a predicate.
     *
     * @param predicate the FilterPredicate to apply for filtering.
     * @return a new DataFrame containing rows that satisfy the predicate.
     */
    DataFrameInterface filter(FilterPredicate predicate);


    /**
     * Filters rows in the DataFrame based on a predicate.
     *
     * @param predicate the FilterPredicate to apply for filtering.
     * @return a new DataFrame containing rows that satisfy the predicate.
     */
    DataFrameInterface where(FilterPredicate predicate);


    /**
     * Executes a query expression on the DataFrame.
     *
     * @param queryExpression the query expression to apply.
     * @return a new DataFrame with the filtered result.
     */
    DataFrameInterface query(String queryExpression);

    /**
     * Fills null values in specified columns with provided values.
     *
     * @param fillColumnValuePairs Mapping of column names to fill values.
     * @return This DataFrame with nulls filled.
     */
    DataFrameInterface fillNa(FillColumnValuePair[] fillColumnValuePairs);

    /**
     * Groups the DataFrame by the specified columns.
     *
     * @param groupByColumns the column names to group by.
     * @return a new DataFrame with the columns you're grouping by
     */
    DataFrame group(String... groupByColumns);

    /**
     * Groups the DataFrame by the specified columns.
     *
     * @param groupByColumns the column names to group by.
     * @return a GroupByFunction for further grouping operations.
     */
    GroupByFunction groupBy(String... groupByColumns);

    /**
     * Groups the DataFrame by the specified columns without sorting the groups.
     *
     * @param groupByColumns the column names to group by.
     * @return a GroupByFunction for further grouping operations.
     */
    GroupByFunction groupByUnsorted(String... groupByColumns);

    /**
     * Performs a join with another DataFrame.
     *
     * @param other           the DataFrame to join with.
     * @param joinType        the type of join to perform.
     * @param leftJoinColumn  the name of the column in this DataFrame to join on.
     * @param rightJoinColumn the name of the column in the other DataFrame to join on.
     * @return a new DataFrame representing the join result.
     */
    DataFrame join(DataFrame other, JoinType joinType, String leftJoinColumn, String rightJoinColumn);

    /**
     * Performs a join with another DataFrame, specifying suffixes for duplicate columns.
     *
     * @param other           the DataFrame to join with.
     * @param joinType        the type of join to perform.
     * @param leftJoinColumn  the name of the column in this DataFrame to join on.
     * @param rightJoinColumn the name of the column in the other DataFrame to join on.
     * @param leftSuffix      the suffix for duplicate columns from this DataFrame.
     * @param rightSuffix     the suffix for duplicate columns from the other DataFrame.
     * @return a new DataFrame representing the join result.
     */
    DataFrame join(DataFrame other, JoinType joinType, String leftJoinColumn, String rightJoinColumn, String leftSuffix, String rightSuffix);

    /**
     * Performs a join with another DataFrame on multiple columns.
     *
     * @param other       the DataFrame to join with.
     * @param joinType    the type of join to perform.
     * @param joinColumns the join columns from both DataFrames.
     * @return a new DataFrame representing the join result.
     */
    DataFrame join(DataFrame other, JoinType joinType, JoinColumn[] joinColumns);

    /**
     * Performs a join with another DataFrame on multiple columns, specifying suffixes for duplicate columns.
     *
     * @param other       the DataFrame to join with.
     * @param joinType    the type of join to perform.
     * @param joinColumns the join columns from both DataFrames.
     * @param leftSuffix  the suffix for duplicate columns from this DataFrame.
     * @param rightSuffix the suffix for duplicate columns from the other DataFrame.
     * @return a new DataFrame representing the join result.
     */
    DataFrame join(DataFrame other, JoinType joinType, JoinColumn[] joinColumns, String leftSuffix, String rightSuffix);

    /**
     * Creates a filter predicate condition for a specified column.
     *
     * @param columnName the name of the column to base the condition on.
     * @return a FilterPredicateCondition object for building filter criteria.
     */
    FilterPredicateCondition column(String columnName);

    /**
     * Retrieves all columns in the DataFrame.
     *
     * @return a collection of columns.
     */
    Collection<Column<?>> getColumns();

    /**
     * Returns an array representing the dimensionality of the dataframe.
     */
    String shape();

    /**
     * Computes the skewness of each numeric column using Fisher Pearson's formula
     * @return a DataFrame with skewness values for each numeric column
     */
    DataFrame skew();

    /**
     * Computes the skewness of each numeric column using the specified formula.
     *
     * @param formula the skewness formula to apply
     * @return a DataFrame with skewness values for each numeric column
     */
    DataFrame skew(SkewnessFormula formula);

    /**
     * Renames old column names with new column names
     * @param renameMap allows to instantiate what column name to replace with Key = old column name V = new column name
     */
    void renameColumn(Map<String, String> renameMap);

    /**
     * Gets the index of a column within a given DataFrame
     * @param columnName the name of the column
     * @return the index/position of a column within a given dataframe
     */
    int getColumnIndex(String columnName);

    /**
     * Provides important details/information about a dataframe (like its structure information, column data type, etc.)
     */
    void info();
}

