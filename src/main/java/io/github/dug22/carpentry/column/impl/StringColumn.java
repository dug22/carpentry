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

import io.github.dug22.carpentry.column.*;
import io.github.dug22.carpentry.column.conversion.StringColumnConversions;
import io.github.dug22.carpentry.column.parser.StringParser;
import io.github.dug22.carpentry.column.transformations.StringTransformations;
import io.github.dug22.carpentry.column.type.StringColumnType;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class StringColumn extends BaseColumn<String> implements StringTransformations, StringColumnConversions {

    public StringColumn(String name) {
        super(name, ColumnTypes.STRING_COLUMN_TYPE, ColumnTypes.STRING_COLUMN_TYPE.getParser());
    }

    public StringColumn(String name, String[] data) {
        super(name, ColumnTypes.STRING_COLUMN_TYPE, data, ColumnTypes.STRING_COLUMN_TYPE.getParser());
    }

    public static StringColumn create(String columnName) {
        return new StringColumn(columnName);
    }

    public static StringColumn create(String columnName, String[] data) {
        return new StringColumn(columnName, data);
    }

    @Override
    public void appendNull(){
        append(Nulls.getDefaultNullValue(String.class));
    }


    @Override
    public StringColumn copy() {
        String[] dataCopy = Arrays.copyOf(getValues(), size());
        return create(name(), dataCopy);
    }

    @Override
    public StringColumn emptyCopy() {
        return create(name());
    }

    @Override
    public StringColumn unique() {
        Set<String> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        String[] uniqueData = uniqueValues.toArray(new String[0]);
        return create(name() + " (unique)", uniqueData);
    }

    @Override
    public StringColumn filter(ColumnPredicate<? super String> condition) {
        return (StringColumn) super.filter(condition);
    }

    @Override
    public StringColumn apply(ColumnFunction<? super String, ? extends String> function) {
        return (StringColumn) super.apply(function);
    }

    @Override
    public boolean isAbsent(String value) {
        return Nulls.isNull(value);
    }

    @Override
    public StringColumnType columnType() {
        return (StringColumnType) super.columnType();
    }

    @Override
    public StringParser getColumnParser() {
        return (StringParser) super.getColumnParser();
    }

    @Override
    public void setColumnParser(ColumnParser<String> columnParser) {
        super.setColumnParser(columnParser);
    }

}