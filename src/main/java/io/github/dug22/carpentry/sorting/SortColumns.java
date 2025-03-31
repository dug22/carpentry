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

package io.github.dug22.carpentry.sorting;

public class SortColumns {

    /**
     * Creates an array of SortColumn objects from pairs of column names and directions.
     * The arguments must alternate between column names (String) and sort directions (Direction).
     *
     * @param args An even number of arguments representing column names and their corresponding sort directions.
     * @return An array of SortColumn objects.
     * @throws IllegalArgumentException If an odd number of arguments is provided.
     */
    public static SortColumn[] of(Object... args) {
        int length = args.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("You must provide an even number of arguments: columnName, direction pairs.");
        }

        SortColumn[] sortColumns = new SortColumn[length / 2];
        for (int i = 0; i < length; i += 2) {
            String columnName = (String) args[i];
            SortColumn.Direction direction = (SortColumn.Direction) args[i + 1];
            sortColumns[i / 2] = new SortColumn(columnName, direction);
        }

        return sortColumns;
    }
}
