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

package io.github.dug22.carpentry.column;

import io.github.dug22.carpentry.columns.StringColumn;
import io.github.dug22.carpentry.io.string.ColumnPrinter;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;

public abstract class AbstractColumn<T> {

    private String columnName;
    private Class<T> columnType;
    private T[] values;

    @SuppressWarnings("unchecked")
    public AbstractColumn(String columnName, Class<T> columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.values = (T[]) Array.newInstance(columnType, 0);
    }

    public AbstractColumn(String columnName, Class<T> columnType, T[] values) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.values = values;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Class<T> getColumnType() {
        return columnType;
    }

    @SuppressWarnings("unchecked")
    public void setColumnType(Class<?> columnType) {
        this.columnType = (Class<T>) columnType;
    }

    public T[] getValues() {
        return values;
    }

    public void setValues(T[] values) {
        this.values = values;
    }

    public void append(T value) {
        int currentSize = size();
        values = Arrays.copyOf(values, currentSize + 1);
        values[currentSize] = value;
    }

    public void appendNull() {
        append(null);
    }

    public void appendAll(T[] newValues) {
        int currentSize = size();
        int newSize = currentSize + newValues.length;
        values = Arrays.copyOf(values, newSize);
        System.arraycopy(newValues, 0, values, currentSize, newValues.length);
    }

    public void remove(T value) {
        if (value == null) {
            for (int i = 0; i < size(); i++) {
                if (values[i] == null) {
                    shiftLeft(i);
                    values = Arrays.copyOf(values, size() - 1);
                    return;
                }
            }
        } else {
            for (int i = 0; i < size(); i++) {
                if (value.equals(values[i])) {
                    shiftLeft(i);
                    values = Arrays.copyOf(values, size() - 1);
                    return;
                }
            }
        }
        throw new NoSuchElementException("Value not found");
    }

    private void shiftLeft(int index) {
        for (int i = index; i < size() - 1; i++) {
            values[i] = values[i + 1];
        }
        values[size() - 1] = null;
    }


    public void removeAtIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        for (int i = index; i < size() - 1; i++) {
            values[i] = values[i + 1];
        }

        values = Arrays.copyOf(values, size() - 1);
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public final void removeAll(T... valuesToRemove) {
        int currentSize = size();
        int newSize = 0;

        T[] newValues = (T[]) Array.newInstance(columnType, currentSize);

        for (int i = 0; i < currentSize; i++) {
            boolean toRemove = false;

            for (T valueToRemove : valuesToRemove) {
                if (values[i].equals(valueToRemove)) {
                    toRemove = true;
                    break;
                }
            }

            if (!toRemove) {
                newValues[newSize++] = values[i];
            }
        }

        values = Arrays.copyOf(newValues, newSize);
    }


    public boolean contains(T value) {
        for (T val : values) {
            if (val != null && val.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @SafeVarargs
    public final boolean containsAll(T... values) {
        for (T val : values) {
            if (!contains(val)) {
                return false;
            }
        }
        return true;
    }

    public T get(int index) {
        return values[index];
    }

    public void set(int index, T value) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        values[index] = value;
    }

    public boolean isNA(int index) {
        return values.length <= index || values[index] == null;
    }

    public boolean isNA(T value) {
        return value == null;
    }

    public int size() {
        return values.length;
    }

    @SuppressWarnings("unchecked")
    public void sortAscending() {
        Arrays.sort(values, (Comparator<? super T>) Comparator.naturalOrder());
    }

    @SuppressWarnings("unchecked")
    public void sortDescending() {
        Arrays.sort(values, (Comparator<? super T>) Comparator.reverseOrder());
    }

    public void replace(T[] oldValues, T newValue) {
        for (int i = 0; i < size(); i++) {
            T currentValue = get(i);
            for (T oldValue : oldValues) {
                if (currentValue.equals(oldValue)) {
                    set(i, newValue);
                    break;
                }
            }
        }
    }

    public StringColumn format(Function<T, String> formatFunction) {
        List<String> formattedData = new ArrayList<>();
        for (T element : values) {
            formattedData.add(formatFunction.apply(element));
        }

        String[] finalFormattedData = formattedData.toArray(new String[0]);
        return StringColumn.create(getColumnName(), finalFormattedData);
    }

    public abstract AbstractColumn<T> copyEmpty();

    public abstract AbstractColumn<T> copy();

    public abstract AbstractColumn<T> unique();

    public abstract AbstractColumn<T> filter(ColumnPredicate<T> condition);

    public abstract AbstractColumn<T> apply(ColumnFunction<T, T> function);

    public void show() {
        new ColumnPrinter().show(this);
    }

    public void head() {
        new ColumnPrinter().head(this);
    }

    public void head(int count) {
        new ColumnPrinter().head(this, count);
    }

    public void tail() {
        new ColumnPrinter().tail(this);
    }

    public void tail(int count) {
        new ColumnPrinter().tail(this, count);
    }
}
