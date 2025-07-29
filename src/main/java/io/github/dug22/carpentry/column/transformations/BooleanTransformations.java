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

import io.github.dug22.carpentry.column.ColumnBiFunction;
import io.github.dug22.carpentry.column.impl.BooleanColumn;

public interface BooleanTransformations extends Transformation {

    /**
     * Returns the Boolean value at the specified index.
     * @param index the row index
     * @return the Boolean value at the given index
     */
    Boolean get(int index);

    /**
     * Performs logical AND with one or more BooleanColumns.
     * @param columns the columns to AND with
     * @return a new BooleanColumn with the result of the AND operation
     */
    default BooleanColumn and(BooleanColumn... columns) {
        return performTransformativeOperation((a, b) -> a && b, "and", columns);
    }

    /**
     * Performs logical AND NOT (a && !b) with one or more BooleanColumns.
     * @param columns the columns to AND NOT with
     * @return a new BooleanColumn with the result of the AND NOT operation
     */
    default BooleanColumn andNot(BooleanColumn... columns) {
        return performTransformativeOperation((a, b) -> a && !b, "andNot", columns);
    }

    /**
     * Performs logical OR with one or more BooleanColumns.
     * @param columns the columns to OR with
     * @return a new BooleanColumn with the result of the OR operation
     */
    default BooleanColumn or(BooleanColumn... columns) {
        return performTransformativeOperation((a, b) -> a || b, "or", columns);
    }

    /**
     * Performs logical XOR with one or more BooleanColumns.
     * @param columns the columns to XOR with
     * @return a new BooleanColumn with the result of the XOR operation
     */
    default BooleanColumn xor(BooleanColumn... columns) {
        return performTransformativeOperation((a, b) -> a != b, "xor", columns);
    }

    /**
     * Inverts all boolean values in the column.
     * @return a new BooleanColumn with flipped values
     */
    default BooleanColumn flip() {
        String newName = name() + " (flipped)";
        BooleanColumn result = BooleanColumn.create(newName, new Boolean[size()]);
        for (int i = 0; i < size(); i++) {
            result.set(i, !get(i));
        }

        return result;
    }

    /**
     * Applies a binary logical operation across this column and additional columns.
     * @param logicalFunction the logical operation to apply
     * @param label a label describing the operation (used for naming)
     * @param columns the columns to apply the operation with
     * @return a new BooleanColumn with the result of the operation
     */
    private BooleanColumn performTransformativeOperation(ColumnBiFunction<Boolean, Boolean, Boolean> logicalFunction, String label, BooleanColumn... columns) {
        StringBuilder newName = new StringBuilder(name());
        for (BooleanColumn column : columns) {
            newName.append(" ").append(label).append(" : ").append(column.name());
        }

        BooleanColumn result = BooleanColumn.create(newName.toString());
        for (int i = 0; i < size(); i++) {
            boolean value = get(i);
            for (BooleanColumn column : columns) {
                value = logicalFunction.apply(value, column.get(i));
            }

            result.append(value);
        }

        return result;
    }
}
