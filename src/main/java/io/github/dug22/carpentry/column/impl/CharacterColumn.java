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
import io.github.dug22.carpentry.column.conversion.CharacterColumnConversions;
import io.github.dug22.carpentry.column.parser.CharacterParser;
import io.github.dug22.carpentry.column.transformations.CharacterTransformations;
import io.github.dug22.carpentry.column.type.CharacterColumnType;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class CharacterColumn extends BaseColumn<Character> implements CharacterTransformations, CharacterColumnConversions {

    public CharacterColumn(String name) {
        super(name, ColumnTypes.CHARACTER_COLUMN_TYPE, ColumnTypes.CHARACTER_COLUMN_TYPE.getParser());
    }

    public CharacterColumn(String name, Character[] data) {
        super(name, ColumnTypes.CHARACTER_COLUMN_TYPE, data, ColumnTypes.CHARACTER_COLUMN_TYPE.getParser());
    }

    public static CharacterColumn create(String columnName) {
        return new CharacterColumn(columnName);
    }


    public static CharacterColumn create(String columnName, Character[] data) {
        return new CharacterColumn(columnName, data);
    }

    @Override
    public void appendNull(){
        append(Nulls.getDefaultNullValue(Character.class));
    }

    @Override
    public CharacterColumn copy() {
        Character[] dataCopy = Arrays.copyOf(getValues(), size());
        return create(name(), dataCopy);
    }

    @Override
    public CharacterColumn emptyCopy() {
        return create(name());
    }

    @Override
    public CharacterColumn unique() {
        Set<Character> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Character[] uniqueData = uniqueValues.toArray(new Character[0]);
        return create(name() + " (unique)", uniqueData);
    }

    @Override
    public CharacterColumn filter(ColumnPredicate<? super Character> condition) {
        return (CharacterColumn) super.filter(condition);
    }

    @Override
    public CharacterColumn apply(ColumnFunction<? super Character, ? extends Character> function) {
        return (CharacterColumn) super.apply(function);
    }

    @Override
    public boolean isAbsent(Character value) {
        return Nulls.isNull(value);
    }

    @Override
    public CharacterColumnType columnType() {
        return (CharacterColumnType) super.columnType();
    }

    @Override
    public CharacterParser getColumnParser() {
        return (CharacterParser) super.getColumnParser();
    }

    @Override
    public void setColumnParser(ColumnParser<Character> columnParser) {
        super.setColumnParser(columnParser);
    }

}
