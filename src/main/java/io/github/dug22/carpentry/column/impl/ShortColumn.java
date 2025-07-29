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
import io.github.dug22.carpentry.column.conversion.ShortColumnConversions;
import io.github.dug22.carpentry.column.format.NumericColumnFormatter;
import io.github.dug22.carpentry.column.parser.ShortParser;
import io.github.dug22.carpentry.column.type.ShortColumnType;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class ShortColumn extends NumberColumn<Short> implements ShortColumnConversions {

    public ShortColumn(String name) {
        super(name, ColumnTypes.SHORT_COLUMN_TYPE, ColumnTypes.SHORT_COLUMN_TYPE.getParser());
        setOutputFormatter(NumericColumnFormatter.NumberColumnFormatterTypes.INTS.init());
    }

    public ShortColumn(String name, Short[] data) {
        super(name, ColumnTypes.SHORT_COLUMN_TYPE, data, ColumnTypes.SHORT_COLUMN_TYPE.getParser());
        setOutputFormatter(NumericColumnFormatter.NumberColumnFormatterTypes.INTS.init());
    }

    public static ShortColumn create(String columnName) {
        return new ShortColumn(columnName);
    }


    public static ShortColumn create(String columnName, Short[] data) {
        return new ShortColumn(columnName, data);
    }

    @Override
    public void appendNull(){
        append(Nulls.getDefaultNullValue(Short.class));
    }


    @Override
    public ShortColumn copy() {
        Short[] dataCopy = Arrays.copyOf(getValues(), size());
        return create(name(), dataCopy);
    }

    @Override
    public ShortColumn emptyCopy() {
        return create(name());
    }

    @Override
    public ShortColumn unique() {
        Set<Short> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Short[] uniqueData = uniqueValues.toArray(new Short[0]);
        return create(name() + " (unique)", uniqueData);
    }

    @Override
    public ShortColumn filter(ColumnPredicate<? super Short> condition) {
        return (ShortColumn) super.filter(condition);
    }

    @Override
    public ShortColumn apply(ColumnFunction<? super Short, ? extends Short> function) {
        return (ShortColumn) super.apply(function);
    }


    @Override
    public boolean isAbsent(Short value){
        return Nulls.isNull(value);
    }

    @Override
    public ShortColumnType columnType() {
        return (ShortColumnType) super.columnType();
    }

    @Override
    public ShortParser getColumnParser() {
        return (ShortParser) super.getColumnParser();
    }

    @Override
    public void setColumnParser(ColumnParser<Short> columnParser) {
        super.setColumnParser(columnParser);
    }

    @Override
    public Double getDouble(int index) {
        Short value = get(index);
        if (isAbsent(index)) return Nulls.getDefaultNullValue(Double.class);
        return value.doubleValue();
    }
}
