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
import io.github.dug22.carpentry.column.conversion.DoubleColumnConversions;
import io.github.dug22.carpentry.column.format.NumericColumnFormatter;
import io.github.dug22.carpentry.column.parser.DoubleParser;
import io.github.dug22.carpentry.column.type.DoubleColumnType;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class DoubleColumn extends NumberColumn<Double> implements DoubleColumnConversions {

    public DoubleColumn(String name) {
        super(name, ColumnTypes.DOUBLE_COLUMN_TYPE, ColumnTypes.DOUBLE_COLUMN_TYPE.getParser());
        setOutputFormatter(NumericColumnFormatter.NumberColumnFormatterTypes.FLOATING_POINT_DEFAULT.init());
    }

    public DoubleColumn(String name, Double[] data) {
        super(name, ColumnTypes.DOUBLE_COLUMN_TYPE, data, ColumnTypes.DOUBLE_COLUMN_TYPE.getParser());
        setOutputFormatter(NumericColumnFormatter.NumberColumnFormatterTypes.FLOATING_POINT_DEFAULT.init());
    }

    public static DoubleColumn create(String columnName) {
        return new DoubleColumn(columnName);
    }

    public static DoubleColumn create(String columnName, Double[] data) {
        return new DoubleColumn(columnName, data);
    }

    @Override
    public void appendNull(){
        append(Nulls.getDefaultNullValue(Double.class));
    }


    @Override
    public DoubleColumn copy() {
        Double[] dataCopy = Arrays.copyOf(getValues(), size());
        return create(name(), dataCopy);
    }

    @Override
    public DoubleColumn emptyCopy() {
        return create(name());
    }

    @Override
    public DoubleColumn unique() {
        Set<Double> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Double[] uniqueData = uniqueValues.toArray(new Double[0]);
        return create(name() + " (unique)", uniqueData);
    }

    @Override
    public DoubleColumn filter(ColumnPredicate<? super Double> condition) {
        return (DoubleColumn) super.filter(condition);
    }

    @Override
    public DoubleColumn apply(ColumnFunction<? super Double, ? extends Double> function) {
        return (DoubleColumn) super.apply(function);
    }

    public boolean isAbsent(Double value){
        return Nulls.isNull(value);
    }

    @Override
    public DoubleColumnType columnType(){
        return (DoubleColumnType) super.columnType();
    }

    @Override
    public DoubleParser getColumnParser() {
        return (DoubleParser) super.getColumnParser();
    }

    @Override
    public void setColumnParser(ColumnParser<Double> columnParser) {
        super.setColumnParser(columnParser);
    }

    @Override
    public Double getDouble(int index) {
        if (isAbsent(index)) return Nulls.getDefaultNullValue(Double.class);
        return get(index);
    }
}
