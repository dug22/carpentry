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

package io.github.dug22.carpentry.column.transformations;

import io.github.dug22.carpentry.column.impl.CharacterColumn;

public interface CharacterTransformations extends Transformation {

    /**
     * Returns the character value at the specified index.
     * @param i the row index
     * @return the Character value at the given index
     */
    Character get(int i);

    /**
     * Converts all characters in the column to uppercase.
     * Null values are preserved.
     * @return a new CharacterColumn with uppercase characters
     */
    default CharacterColumn uppercase() {
        CharacterColumn newCharacterColumn = CharacterColumn.create(name() + " (uppercase)");
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                newCharacterColumn.appendNull();
            } else {
                Character value = get(index);
                newCharacterColumn.append(Character.toUpperCase(value));
            }
        }

        return newCharacterColumn;
    }

    /**
     * Converts all characters in the column to lowercase.
     * Null values are preserved.
     * @return a new CharacterColumn with lowercase characters
     */
    default CharacterColumn lowercase() {
        CharacterColumn newCharacterColumn = CharacterColumn.create(name() + " (lowercase)");
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                newCharacterColumn.appendNull();
            } else {
                Character value = get(index);
                newCharacterColumn.append(Character.toLowerCase(value));
            }
        }

        return newCharacterColumn;
    }
}
