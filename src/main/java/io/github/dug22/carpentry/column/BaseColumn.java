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

import io.github.dug22.carpentry.utils.Nulls;

import java.lang.reflect.Array;
import java.util.*;

public abstract class BaseColumn<T> implements Column<T> {

    private String name;
    private final ColumnType columnType;
    private T[] data;
    protected ColumnParser<T> columnParser;

    @SuppressWarnings("unchecked")
    public BaseColumn(final String name, ColumnType columnType, ColumnParser<T> columnParser) {
        setName(name);
        this.columnType = columnType;
        this.data = (T[]) Array.newInstance(this.columnType.getClassType(), 0);
        this.columnParser = columnParser;
    }

    public BaseColumn(final String name, ColumnType columnType, T[] data, ColumnParser<T> columnParser) {
        setName(name);
        this.columnType = columnType;
        this.data = data != null ? data : (T[]) Array.newInstance(this.columnType.getClassType(), 0);
        this.columnParser = columnParser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setName(final String name) {
        this.name = name.trim();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setData(T[] data) {
        this.data = data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ColumnType columnType() {
        return columnType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return data.length;
    }

    /**
     * {@inheritDoc}
     */
    public T get(int index) {
        return data[index];
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public T[] getValues() {
        T[] result = (T[]) Array.newInstance(columnType.getClassType(), size());
        System.arraycopy(data, 0, result, 0, result.length);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void append(T value) {
        int currentSize = size();
        data = Arrays.copyOf(data, currentSize + 1);
        data[currentSize] = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void appendAll(T[] values) {
        int currentSize = size();
        int newSize = currentSize + values.length;
        data = Arrays.copyOf(data, newSize);
        System.arraycopy(values, 0, data, currentSize, values.length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAbsent(int index) {
        if (isIndexOutBounds(index)) {
            return true;
        }

        T value = get(index);
        return isAbsent(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T value) {
        for (int i = 0; i < size(); i++) {
            if (getValues()[i] == value) return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(List<T> values) {
        return values.stream().allMatch(this::contains);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(int index, T value) {
        data[index] = value;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        data = (T[]) Array.newInstance(columnType.getClassType(), 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sortAscending() {
        Arrays.sort(data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sortDescending() {
        Arrays.sort(data, Collections.reverseOrder());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void replace(T[] oldValues, T newValue) {
        for (int i = 0; i < size(); i++) {
            for (T oldValue : oldValues) {
                if (oldValue != null && oldValue.equals(data[i])) {
                    data[i] = newValue;
                    break;
                } else if (Nulls.isNull(oldValue) && data[i] == null) {
                    data[i] = newValue;
                    break;
                }
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Column<T> filter(ColumnPredicate<? super T> condition) {
        return Column.super.filter(condition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Column<T> apply(ColumnFunction<? super T, ? extends T> function) {
        return Column.super.apply(function);
    }

    /**
     * Returns an iterator over the elements in this column's data array.
     * The iterator traverses the column's values in order, but does not support
     * removal of elements.
     *
     * @return an iterator for the column's data array
     * @throws UnsupportedOperationException if the remove operation is attempted
     * @throws NoSuchElementException if there are no more elements to iterate
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove isn't supported by this given operator");
            }

            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T[] values = getValues();
                return values[index++];
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        Column.super.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void head() {
        Column.super.head();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void head(int count) {
        Column.super.head(count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tail() {
        Column.super.tail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tail(int count) {
        Column.super.tail(count);
    }

    /**
     * {@inheritDoc}
     */
    public ColumnParser<T> getColumnParser() {
        return columnParser;
    }

    /**
     * {@inheritDoc}
     */
    public void setColumnParser(ColumnParser<T> columnParser) {
        this.columnParser = columnParser;
    }
}
