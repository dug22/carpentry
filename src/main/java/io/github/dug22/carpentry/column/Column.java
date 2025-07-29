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

package io.github.dug22.carpentry.column;

import io.github.dug22.carpentry.io.string.ColumnPrinter;
import io.github.dug22.carpentry.utils.Nulls;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public interface Column<T> extends Iterable<T> {

    /**
     * Sets a name for a column
     *
     * @param name the column name;
     */
    void setName(String name);

    /**
     * Sets the data values for a column
     *
     * @param data the array of data.
     */
    void setData(T[] data);

    /**
     * Gets the name of the column
     *
     * @return the name of the column
     */
    String name();

    /**
     * Gets the column type, for a given column
     *
     * @return the column type, for a given column
     */
    ColumnType columnType();

    /**
     * Gets the column size
     *
     * @return the size of a given column.
     */
    int size();

    /**
     * Retrieves a value from a column
     *
     * @param index the position of where a value is located within the column's data array.
     * @return the retrieved value from a column based off the passed index.
     */
    T get(int index);

    /**
     * Returns a column's entire data array
     *
     * @return a column's entire data array
     */
    T[] getValues();

    /**
     * Adds a value to a column
     *
     * @param value the value to add to a column
     */
    void append(T value);

    /**
     * Adds a null value to a column
     */
    void appendNull();

    /**
     * Adds a collection of values to a column
     *
     * @param values the values to add to a column
     */
    void appendAll(T[] values);

    /**
     * Checks to see if a column contains a specific value
     *
     * @param value target value that a column may contain.
     * @return if a column contains the given value
     */
    boolean contains(T value);

    /**
     * Checks to see if a column contains a list of values.
     *
     * @param values target values that a column may contain
     * @return if a column contains the list of values.
     */
    boolean containsAll(List<T> values);

    /**
     * Replaces a value at the specified position within the column
     *
     * @param index the position of where to replace the value within the column
     * @param value the value you wish to insert.
     */
    void set(int index, T value);

    /**
     * Checks to see if a value is absent/missing within a column based on a passed index.
     *
     * @param index the position
     * @return true or false to see if a value is missing within a column based on a passed index.
     */
    boolean isAbsent(int index);

    /**
     * Checks to see if a value is absent/missing within a column based on a passed value.
     *
     * @param value the value
     * @return true or false to see if a value is missing within a column based on a passed value.
     */
    boolean isAbsent(T value);

    /**
     * Sets a value to be null based on a passed index
     *
     * @param index the position within the data array
     */
    default void setAbsent(int index) {
        set(index, (T) Nulls.getDefaultNullValue(columnType().getClassType()));
    }

    /**
     * Checks to see if the given index is in bounds
     *
     * @param index the position of the data array
     * @return if the given index is in bounds
     */
    default boolean isIndexOutBounds(int index) {
        return index < 0 || index >= size();
    }

    /**
     * If a value is missing you can replace it with a default value of your choice.
     *
     * @param value the value you wish to replace null values with.
     * @return a new column with those null values being filled in with the passed value.
     */
    default Column<T> setMissingTo(T value) {
        Column<T> result = copy();
        for (int i = 0; i < size(); i++) {
            if (isAbsent(i)) {
                result.set(i, value);
            }
        }
        return result;
    }

    /**
     * Removes missing values within a column
     *
     * @return a new column with those missing values removed.
     */
    default Column<T> removeMissing() {
        Column<T> column = emptyCopy();
        for (int i = 0; i < size(); i++) {
            if (!isAbsent(i)) {
                column.append(get(i));
            }
        }
        return column;
    }

    /**
     * Clears the column
     */
    void clear();

    /**
     * Sorts the column data in ascending order.
     */
    void sortAscending();

    /**
     * Sorts the column data in descending order.
     */
    void sortDescending();

    /**
     * Checks to see if a column is empty.
     *
     * @return if a column is empty.
     */
    boolean isEmpty();

    /**
     * Replaces old values with new values
     *
     * @param oldValues values you wish to replace
     * @param newValue  values you wish to replace old values with.
     */
    void replace(T[] oldValues, T newValue);

    /**
     * Creates an exact copy of the column
     *
     * @return a copied column (reserving the name and data)
     */
    Column<T> copy();

    /**
     * Creates an empty copy of a column
     *
     * @return an empty copy of a column (reserving only the name)
     */
    Column<T> emptyCopy();

    /**
     * Creates a column that is unique, removing duplicate values
     *
     * @return a column that is unique, removing duplicate values.
     */
    Column<T> unique();

    /**
     * Creates a rolling window view of the column with the specified window size.
     *
     * @param windowSize the size of the rolling window
     * @return a RollingColumn wrapping this column with the specified window size
     */
    default RollingColumn rolling(int windowSize) {
        return new RollingColumn(this, windowSize);
    }

    /**
     * Filters the column based on a condition, keeping only values that satisfy the predicate.
     *
     * @param condition the predicate to test each element
     * @return a new column containing only the elements that satisfy the condition
     */
    @SuppressWarnings("unchecked")
    default Column<T> filter(ColumnPredicate<? super T> condition) {
        List<Integer> keptIndexes = new ArrayList<>(copy().size());
        int startingIndex = 0;
        int overallSize = size();
        for (int index = startingIndex; index < overallSize; index++) {
            boolean passingCondition = !isAbsent(index) && condition.test(get(index));
            if (passingCondition) {
                keptIndexes.add(index);
            }
        }
        Column<T> newColumn = emptyCopy();
        T[] values = getValues();
        T[] filteredData = (T[]) Array.newInstance(columnType().getClassType(), keptIndexes.size());

        for (int i = 0; i < keptIndexes.size(); i++) {
            filteredData[i] = values[keptIndexes.get(i)];
        }

        newColumn.setData(filteredData);
        return newColumn;
    }

    /**
     * Applies a function to each non-absent element in the column, returning a new column with the results.
     *
     * @param function the function to apply to each element
     * @return a new column with the function applied to each non-absent element
     */
    default Column<T> apply(ColumnFunction<? super T, ? extends T> function) {
        Column<T> column = copy();
        IntStream.range(0, size())
                .parallel()
                .filter(i -> !isAbsent(i))
                .forEach(i -> column.set(i, function.apply(get(i))));
        return column;
    }

    /**
     * Displays the entire column.
     */
    default void show() {
        new ColumnPrinter().show(this);
    }

    /**
     * Displays the first five rows of the column.
     */
    default void head() {
        new ColumnPrinter().head(this);
    }

    /**
     * Displays the first specified number of rows of the column.
     *
     * @param count the number of rows to display
     */
    default void head(int count) {
        new ColumnPrinter().head(this, count);
    }

    /**
     * Displays the last five rows of the column.
     */
    default void tail() {
        new ColumnPrinter().tail(this);
    }

    /**
     * Displays the last specified number of rows of the column.
     *
     * @param count the number of rows to display
     */
    default void tail(int count) {
        new ColumnPrinter().tail(this, count);
    }
}