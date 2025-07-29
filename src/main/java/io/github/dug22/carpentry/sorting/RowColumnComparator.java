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

package io.github.dug22.carpentry.sorting;

import io.github.dug22.carpentry.row.DataRow;

import java.util.Comparator;

public class RowColumnComparator implements Comparator<DataRow> {

    private final SortColumn[] sortColumns;

    /**
     * Creates a comparator using sort columns
     *
     * @param sortColumns sort columns
     */
    public RowColumnComparator(SortColumn[] sortColumns) {
        this.sortColumns = sortColumns;
    }

    /**
     * Compares two rows using the sort columns
     *
     * @param r1 first row
     * @param r2 second row
     * @return comparison result
     */
    @Override
    public int compare(DataRow r1, DataRow r2) {
        int comparisonResult = 0;

        for (SortColumn sortColumn : sortColumns) {
            String columnName = sortColumn.name();
            Object value1 = r1.get(columnName);
            Object value2 = r2.get(columnName);

            if (value1 == null && value2 == null) {
                continue;
            }
            if (value1 == null) {
                return 1;
            }
            if (value2 == null) {
                return -1;
            }

            comparisonResult = compareValues(value1, value2);
            comparisonResult = sortColumn.direction() == SortColumn.Direction.ASCENDING ? comparisonResult : -comparisonResult;
            if (comparisonResult != 0) {
                return comparisonResult;
            }
        }

        return comparisonResult;
    }

    /**
     * Compares two objects of potentially different types.
     *
     * @param a The first object to compare.
     * @param b The second object to compare.
     * @return the comparison result.
     * @throws IllegalArgumentException If the objects are of incompatible types.
     */
    private int compareValues(Object a, Object b) {
        if (a == null || b == null) {
            return (a == null) ? (b == null ? 0 : -1) : 1;
        }

        return switch (a) {
            case String s when b instanceof String -> s.compareTo((String) b);
            case Boolean aBoolean when b instanceof Boolean -> Boolean.compare(aBoolean, (Boolean) b);
            case Byte aByte when b instanceof Byte -> Byte.compare(aByte, (Byte) b);
            case Character c when b instanceof Character -> Character.compare(c, (Character) b);
            case Double v when b instanceof Double -> Double.compare(v, (Double) b);
            case Float v when b instanceof Float -> Float.compare(v, (Float) b);
            case Integer integer when b instanceof Integer -> Integer.compare(integer, (Integer) b);
            case Long l when b instanceof Long -> Long.compare(l, (Long) b);
            case Short i when b instanceof Short -> Short.compare(i, (Short) b);
            default ->
                    throw new IllegalArgumentException(String.format("Unsupported comparison between types: %s and %s", a.getClass(), b.getClass()));
        };
    }
}