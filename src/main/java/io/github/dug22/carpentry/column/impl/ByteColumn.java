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

package io.github.dug22.carpentry.column.impl;

import io.github.dug22.carpentry.column.ColumnFunction;
import io.github.dug22.carpentry.column.ColumnParser;
import io.github.dug22.carpentry.column.ColumnPredicate;
import io.github.dug22.carpentry.column.ColumnTypes;
import io.github.dug22.carpentry.column.conversion.ByteColumnConversions;
import io.github.dug22.carpentry.column.parser.ByteParser;
import io.github.dug22.carpentry.column.type.ByteColumnType;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class ByteColumn extends NumberColumn<Byte> implements ByteColumnConversions {

    public ByteColumn(String name) {
        super(name, ColumnTypes.BYTE_COLUMN_TYPE, ColumnTypes.BYTE_COLUMN_TYPE.getParser());
    }

    public ByteColumn(String name, Byte[] data) {
        super(name, ColumnTypes.BYTE_COLUMN_TYPE, data, ColumnTypes.BYTE_COLUMN_TYPE.getParser());
    }

    public static ByteColumn create(String columnName) {
        return new ByteColumn(columnName);
    }

    public static ByteColumn create(String columnName, Byte[] data) {
        return new ByteColumn(columnName, data);
    }

    @Override
    public void appendNull() {
        append(Nulls.getDefaultNullValue(Byte.class));
    }

    @Override
    public ByteColumn copy() {
        Byte[] dataCopy = Arrays.copyOf(getValues(), size());
        return create(name(), dataCopy);
    }

    @Override
    public ByteColumn emptyCopy() {
        return create(name());
    }

    @Override
    public ByteColumn unique() {
        Set<Byte> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Byte[] uniqueData = uniqueValues.toArray(new Byte[0]);
        return create(name() + " (unique)", uniqueData);
    }

    @Override
    public ByteColumn filter(ColumnPredicate<? super Byte> condition) {
        return (ByteColumn) super.filter(condition);
    }

    @Override
    public ByteColumn apply(ColumnFunction<? super Byte, ? extends Byte> function) {
        return (ByteColumn) super.apply(function);
    }

    @Override
    public boolean isAbsent(Byte value) {
        return Nulls.isNull(value);
    }

    @Override
    public ByteColumnType columnType() {
        return (ByteColumnType) super.columnType();
    }

    @Override
    public ByteParser getColumnParser() {
        return (ByteParser) super.getColumnParser();
    }

    @Override
    public void setColumnParser(ColumnParser<Byte> columnParser) {
        super.setColumnParser(columnParser);
    }

    @Override
    public Double getDouble(int index) {
        Byte value = get(index);
        if (isAbsent(index)) return Nulls.getDefaultNullValue(Double.class);
        return value.doubleValue();
    }
}
