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
import io.github.dug22.carpentry.column.conversion.LongColumnConversions;
import io.github.dug22.carpentry.column.format.NumericColumnFormatter;
import io.github.dug22.carpentry.column.parser.LongParser;
import io.github.dug22.carpentry.column.type.LongColumnType;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class LongColumn extends NumberColumn<Long> implements LongColumnConversions {

    public LongColumn(String name) {
        super(name, ColumnTypes.LONG_COLUMN_TYPE, ColumnTypes.LONG_COLUMN_TYPE.getParser());
        setOutputFormatter(NumericColumnFormatter.NumberColumnFormatterTypes.INTS.init());
    }

    public LongColumn(String name, Long[] data) {
        super(name, ColumnTypes.LONG_COLUMN_TYPE, data, ColumnTypes.LONG_COLUMN_TYPE.getParser());
        setOutputFormatter(NumericColumnFormatter.NumberColumnFormatterTypes.INTS.init());
    }

    public static LongColumn create(String columnName) {
        return new LongColumn(columnName);
    }


    public static LongColumn create(String columnName, Long[] data) {
        return new LongColumn(columnName, data);
    }

    @Override
    public void appendNull(){
        append(Nulls.getDefaultNullValue(Long.class));
    }


    @Override
    public LongColumn copy() {
        Long[] dataCopy = Arrays.copyOf(getValues(), size());
        return create(name(), dataCopy);
    }

    @Override
    public LongColumn emptyCopy() {
        return create(name());
    }

    @Override
    public LongColumn unique() {
        Set<Long> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Long[] uniqueData = uniqueValues.toArray(new Long[0]);
        return create(name() + " (unique)", uniqueData);
    }

    @Override
    public LongColumn filter(ColumnPredicate<? super Long> condition) {
        return (LongColumn) super.filter(condition);
    }

    @Override
    public LongColumn apply(ColumnFunction<? super Long, ? extends Long> function) {
        return (LongColumn) super.apply(function);
    }

    @Override
    public boolean isAbsent(Long value){
        return Nulls.isNull(value);
    }

    public LongColumnType columnType(){
        return (LongColumnType) super.columnType();
    }

    @Override
    public LongParser getColumnParser() {
        return (LongParser) super.getColumnParser();
    }

    @Override
    public void setColumnParser(ColumnParser<Long> columnParser) {
        super.setColumnParser(columnParser);
    }

    @Override
    public Double getDouble(int index) {
        Long value = get(index);
        if (isAbsent(index)) return Nulls.getDefaultNullValue(Double.class);
        return value.doubleValue();
    }
}
