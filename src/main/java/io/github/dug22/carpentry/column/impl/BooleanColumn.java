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
import io.github.dug22.carpentry.column.conversion.BooleanColumnConversions;
import io.github.dug22.carpentry.column.parser.BooleanParser;
import io.github.dug22.carpentry.column.transformations.BooleanTransformations;
import io.github.dug22.carpentry.column.type.BooleanColumnType;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class BooleanColumn extends BaseColumn<Boolean> implements BooleanTransformations, BooleanColumnConversions {

    public BooleanColumn(String name) {
        super(name, ColumnTypes.BOOLEAN_COLUMN_TYPE, ColumnTypes.BOOLEAN_COLUMN_TYPE.getParser());
    }

    public BooleanColumn(String columnName, Boolean[] data) {
        super(columnName, ColumnTypes.BOOLEAN_COLUMN_TYPE, data, ColumnTypes.BOOLEAN_COLUMN_TYPE.getParser());
    }

    public static BooleanColumn create(String columnName) {
        return new BooleanColumn(columnName);
    }


    public static BooleanColumn create(String columnName, Boolean[] data) {
        return new BooleanColumn(columnName, data);
    }

    @Override
    public void appendNull(){
        append(Nulls.getDefaultNullValue(Boolean.class));
    }

    @Override
    public BooleanColumn copy() {
        Boolean[] dataCopy = Arrays.copyOf(getValues(), size());
        return create(name(), dataCopy);
    }


    @Override
    public BooleanColumn emptyCopy() {
        return create(name());
    }


    @Override
    public BooleanColumn unique() {
        Set<Boolean> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Boolean[] uniqueData = uniqueValues.toArray(new Boolean[0]);
        return new BooleanColumn(name() + " (unique)", uniqueData);
    }

    @Override
    public BooleanColumn filter(ColumnPredicate<? super Boolean> condition) {
        return (BooleanColumn) super.filter(condition);
    }

    @Override
    public BooleanColumn apply(ColumnFunction<? super Boolean, ? extends Boolean> function) {
        return (BooleanColumn) super.apply(function);
    }

    @Override
    public boolean isAbsent(Boolean value) {
        return Nulls.isNull(value);
    }

    @Override
    public BooleanColumnType columnType() {
        return (BooleanColumnType) super.columnType();
    }

    @Override
    public BooleanParser getColumnParser() {
        return (BooleanParser) super.getColumnParser();
    }

    @Override
    public void setColumnParser(ColumnParser<Boolean> columnParser) {
        super.setColumnParser(columnParser);
    }
}