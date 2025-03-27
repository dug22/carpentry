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

package io.github.dug22.carpentry.column.characters;

import io.github.dug22.carpentry.columns.CharacterColumn;

public interface CharacterMapFunctions {

    String getColumnName();

    int size();

    Character get(int index);

    /**
     * Converts all character elements in the current CharacterColumn to uppercase.
     * @return a new CharacterColumn with all characters converted to uppercase.
     */
    default CharacterColumn upperCase() {
        CharacterColumn newCharacterColumn = CharacterColumn.create(getColumnName() + " [ucase]");
        for (int i = 0; i < size(); i++) {
            Character value = Character.toUpperCase(get(i));
            newCharacterColumn.append(value);
        }
        return newCharacterColumn;
    }

    /**
     * Converts all character elements in the current CharacterColumn to lowercase.
     * @return a new CharacterColumn with all characters converted to lowercase.
     */
    default CharacterColumn lowerChase() {
        CharacterColumn newCharacterColumn = CharacterColumn.create(getColumnName() + " [lcase]");
        for (int i = 0; i < size(); i++) {
            Character value = Character.toLowerCase(get(i));
            newCharacterColumn.append(value);
        }
        return newCharacterColumn;
    }
}
