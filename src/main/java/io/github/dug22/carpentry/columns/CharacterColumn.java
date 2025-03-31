/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.github.dug22.carpentry.columns;

import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnFunction;
import io.github.dug22.carpentry.column.ColumnPredicate;
import io.github.dug22.carpentry.column.characters.CharacterConversionFunctions;
import io.github.dug22.carpentry.column.characters.CharacterMapFunctions;

import java.util.*;

public class CharacterColumn extends AbstractColumn<Character> implements CharacterConversionFunctions, CharacterMapFunctions {

    public CharacterColumn(String columnName) {
        super(columnName, Character.class);
    }

    public CharacterColumn(String columnName, Character[] values) {
        super(columnName, Character.class, values);
    }

    public static CharacterColumn create(String columnName) {
        return new CharacterColumn(columnName);
    }


    public static CharacterColumn create(String columnName, Character[] values) {
        return new CharacterColumn(columnName, values);
    }


    @Override
    public CharacterColumn copyEmpty() {
        return create(getColumnName());
    }

    @Override
    public CharacterColumn copy() {
        Character[] copyData = Arrays.copyOf(getValues(), size());
        return create(getColumnName(), copyData);
    }

    @Override
    public CharacterColumn unique() {
        Set<Character> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        Character[] uniqueArray = uniqueValues.toArray(new Character[0]);
        return create(getColumnName(), uniqueArray);
    }

    @Override
    public CharacterColumn filter(ColumnPredicate<Character> condition) {
        List<Character> filteredValues = new ArrayList<>();
        for (Character value : getValues()) {
            if (condition.test(value)) {
                filteredValues.add(value);
            }
        }

        Character[] filteredArray = filteredValues.toArray(new Character[0]);
        return create(getColumnName(), filteredArray);
    }

    @Override
    public CharacterColumn apply(ColumnFunction<Character, Character> function) {
        Character[] transformedValues = new Character[size()];
        for (int i = 0; i < size(); i++) {
            transformedValues[i] = function.apply(get(i));
        }

        return create(getColumnName(), transformedValues);
    }
}
