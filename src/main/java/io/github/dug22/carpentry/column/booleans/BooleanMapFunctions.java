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

package io.github.dug22.carpentry.column.booleans;


import io.github.dug22.carpentry.columns.BooleanColumn;

public interface BooleanMapFunctions {

    Boolean get(int i);

    void set(int i, Boolean element);

    int size();


    String getColumnName();

    /**
     * Performs a logical AND operation between this Boolean Column and other Boolean columns
     * @param columns other BooleanColumns
     * @return a new BooleanColumn representing the result of the AND operation
     */
    default BooleanColumn and(BooleanColumn... columns) {
        StringBuilder newName = new StringBuilder(getColumnName());
        for (BooleanColumn column : columns) {
            newName.append(" and : ").append(column.getColumnName());
        }
        BooleanColumn result = new BooleanColumn(newName.toString());
        for (int i = 0; i < size(); i++) {
            boolean value = get(i);
            for (BooleanColumn column : columns) {
                value = value && column.get(i);
            }
            result.append(value);
        }
        return result;
    }

    /**
     * Performs a logical AND NOT operation between this Boolean Column and other Boolean columns
     * @param columns other BooleanColumns
     * @return a new BooleanColumn representing the result of the AND NOT operation
     */
    default BooleanColumn andNot(BooleanColumn... columns) {
        StringBuilder newName = new StringBuilder(getColumnName());
        for (BooleanColumn column : columns) {
            newName.append(" andNot : ").append(column.getColumnName());
        }
        BooleanColumn result = new BooleanColumn(newName.toString());
        for (int i = 0; i < size(); i++) {
            boolean value = get(i);
            for (BooleanColumn column : columns) {
                value = value && !column.get(i);
            }
            result.append(value);
        }
        return result;
    }

    /**
     * Performs a logical OR operation between this Boolean Column and other Boolean columns
     * @param columns other BooleanColumns
     * @return a new BooleanColumn representing the result of the OR operation
     */
    default BooleanColumn or(BooleanColumn... columns) {
        StringBuilder newName = new StringBuilder(getColumnName());
        for (BooleanColumn column : columns) {
            newName.append(" or : ").append(column.getColumnName());
        }
        BooleanColumn result = new BooleanColumn(newName.toString());
        for (int i = 0; i < size(); i++) {
            boolean value = get(i);
            for (BooleanColumn column : columns) {
                value = value || column.get(i);
            }
            result.append(value);
        }
        return result;
    }

    /**
     * Performs a logical XOR operation between this Boolean Column and other Boolean columns
     * @param columns other BooleanColumns
     * @return a new BooleanColumn representing the result of the XOR operation
     */
    default BooleanColumn xor(BooleanColumn... columns) {
        StringBuilder newName = new StringBuilder(getColumnName());
        for (BooleanColumn column : columns) {
            newName.append(" xor : ").append(column.getColumnName());
        }
        BooleanColumn result = new BooleanColumn(newName.toString());
        for (int i = 0; i < size(); i++) {
            boolean value = get(i);
            for (BooleanColumn column : columns) {
                value = value != column.get(i);
            }
            result.append(value);
        }
        return result;
    }

    /**
     * Flips the Boolean values in this column. The resulting Boolean column will have the inverse
     * value for each entry (true becomes false, and false becomes true).
     *
     * @return a new BooleanColumn with the flipped values
     */
    default BooleanColumn flip() {
        String newName = getColumnName() + " [flipped]";
        BooleanColumn result = new BooleanColumn(newName);
        for (int i = 0; i < size(); i++) {
            result.append(!get(i));
        }
        return result;
    }
}
