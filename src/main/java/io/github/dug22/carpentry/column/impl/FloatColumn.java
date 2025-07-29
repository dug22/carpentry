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
import io.github.dug22.carpentry.column.conversion.FloatColumnConversions;
import io.github.dug22.carpentry.column.format.NumericColumnFormatter;
import io.github.dug22.carpentry.column.parser.FloatParser;
import io.github.dug22.carpentry.column.type.FloatColumnType;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class FloatColumn extends NumberColumn<Float> implements FloatColumnConversions {

    public FloatColumn(String name){
        super(name, ColumnTypes.FLOAT_COLUMN_TYPE, ColumnTypes.FLOAT_COLUMN_TYPE.getParser());
        setOutputFormatter(NumericColumnFormatter.NumberColumnFormatterTypes.FLOATING_POINT_DEFAULT.init());
    }

    public FloatColumn(String name, Float[] data){
        super(name, ColumnTypes.FLOAT_COLUMN_TYPE, data, ColumnTypes.FLOAT_COLUMN_TYPE.getParser());
        setOutputFormatter(NumericColumnFormatter.NumberColumnFormatterTypes.FLOATING_POINT_DEFAULT.init());
    }

    public static FloatColumn create(String name){
        return new FloatColumn(name);
    }

    public static FloatColumn create(String name, Float[] data){
        return new FloatColumn(name, data);
    }

    @Override
    public void appendNull(){
        append(Nulls.getDefaultNullValue(Float.class));
    }


    @Override
    public FloatColumn copy() {
        Float[] dataCopy = Arrays.copyOf(getValues(), size());
        return create(name(), dataCopy);
    }

    @Override
    public FloatColumn emptyCopy() {
        return create(name());
    }

    @Override
    public FloatColumn unique() {
        Set<Float> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Float[] uniqueData = uniqueValues.toArray(new Float[0]);
        return create(name() + " (unique)", uniqueData);
    }

    @Override
    public FloatColumn filter(ColumnPredicate<? super Float> condition) {
        return (FloatColumn) super.filter(condition);
    }

    @Override
    public FloatColumn apply(ColumnFunction<? super Float, ? extends Float> function) {
        return (FloatColumn) super.apply(function);
    }

    @Override
    public boolean isAbsent(Float value){
        return Nulls.isNull(value);
    }

    @Override
    public FloatColumnType columnType(){
        return (FloatColumnType) super.columnType();
    }

    @Override
    public FloatParser getColumnParser() {
        return (FloatParser) super.getColumnParser();
    }

    @Override
    public void setColumnParser(ColumnParser<Float> columnParser) {
        super.setColumnParser(columnParser);
    }

    @Override
    public Double getDouble(int index) {
        Float value = get(index);
        if (isAbsent(index)) return Nulls.getDefaultNullValue(Double.class);
        return value.doubleValue();
    }
}
