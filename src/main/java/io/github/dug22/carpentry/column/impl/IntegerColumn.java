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
import io.github.dug22.carpentry.column.conversion.IntegerColumnConversions;
import io.github.dug22.carpentry.column.format.NumericColumnFormatter;
import io.github.dug22.carpentry.column.parser.IntegerParser;
import io.github.dug22.carpentry.column.type.IntegerColumnType;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class IntegerColumn extends NumberColumn<Integer> implements IntegerColumnConversions {

    public IntegerColumn(String name) {
        super(name, ColumnTypes.INTEGER_COLUMN_TYPE, ColumnTypes.INTEGER_COLUMN_TYPE.getParser());
        setOutputFormatter(NumericColumnFormatter.NumberColumnFormatterTypes.INTS.init());

    }

    public IntegerColumn(String name, Integer[] data) {
        super(name, ColumnTypes.INTEGER_COLUMN_TYPE, data, ColumnTypes.INTEGER_COLUMN_TYPE.getParser());
        setOutputFormatter(NumericColumnFormatter.NumberColumnFormatterTypes.INTS.init());
    }

    public static IntegerColumn create(String columnName) {
        return new IntegerColumn(columnName);
    }


    public static IntegerColumn create(String columnName, Integer[] data) {
        return new IntegerColumn(columnName, data);
    }

    @Override
    public void appendNull(){
        append(Nulls.getDefaultNullValue(Integer.class));
    }


    @Override
    public IntegerColumn copy() {
        Integer[] dataCopy = Arrays.copyOf(getValues(), size());
        return create(name(), dataCopy);
    }

    @Override
    public IntegerColumn emptyCopy() {
        return create(name());
    }

    @Override
    public IntegerColumn unique() {
        Set<Integer> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Integer[] uniqueData = uniqueValues.toArray(new Integer[0]);
        return create(name() + " (unique)", uniqueData);
    }

    @Override
    public IntegerColumn filter(ColumnPredicate<? super Integer> condition) {
        return (IntegerColumn) super.filter(condition);
    }

    @Override
    public IntegerColumn apply(ColumnFunction<? super Integer, ? extends Integer> function) {
        return (IntegerColumn) super.apply(function);
    }

    @Override
    public boolean isAbsent(Integer value) {
        return Nulls.isNull(value);
    }

    @Override
    public IntegerColumnType columnType() {
        return (IntegerColumnType) super.columnType();
    }

    @Override
    public IntegerParser getColumnParser() {
        return (IntegerParser) super.getColumnParser();
    }

    @Override
    public void setColumnParser(ColumnParser<Integer> columnParser) {
        super.setColumnParser(columnParser);
    }

    @Override
    public Double getDouble(int index) {
        Integer value = get(index);
        if (isAbsent(index)) return Nulls.getDefaultNullValue(Double.class);
        return value.doubleValue();
    }
}
