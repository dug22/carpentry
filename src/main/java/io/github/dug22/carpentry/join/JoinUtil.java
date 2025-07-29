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

package io.github.dug22.carpentry.join;

import io.github.dug22.carpentry.DataFrameInterface;
import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.column.ColumnException;
import io.github.dug22.carpentry.column.impl.*;
import io.github.dug22.carpentry.row.DataRow;

public interface JoinUtil {

    DataFrameInterface createResultDataFrame(JoinDetails joinDetails);
    boolean rowsMatch(DataRow leftRow, DataRow rightRow, JoinColumn[] joinColumns);
    void mergeRow(DataFrameInterface result, DataRow leftRow, DataRow rightRow, JoinDetails joinDetails);

    default Column<?> createEmptyColumn(Column<?> column, String newName) {
        return switch (column) {
            case BooleanColumn ignoredBooleanColumn -> BooleanColumn.create(newName);
            case ByteColumn ignoredByteColumn -> ByteColumn.create(newName);
            case CharacterColumn ignoredCharacterColumn -> CharacterColumn.create(newName);
            case DateColumn ignoredDateColumn -> DateColumn.create(newName);
            case DateTimeColumn ignoredDateTimeColumn -> DateTimeColumn.create(newName);
            case DoubleColumn ignoredDoubleColumn -> DoubleColumn.create(newName);
            case FloatColumn ignoredFloatColumn -> FloatColumn.create(newName);
            case IntegerColumn ignoredIntegerColumn -> IntegerColumn.create(newName);
            case LongColumn ignoredLongColumn -> LongColumn.create(newName);
            case ShortColumn ignoredShortColumn -> ShortColumn.create(newName);
            case StringColumn ignoredStringColumn -> StringColumn.create(newName);
            default -> throw new ColumnException("Unexpected column type: " + column);
        };
    }
}
