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

package io.github.dug22.carpentry.column;

import io.github.dug22.carpentry.columns.*;

public class ColumnCreator {

    /**
     * Creates a new instance of the same type as the given column.
     *
     * @param column the original column whose type is used to create the new instance
     * @param <T> the type of the column
     * @return a new instance of the same type as the given column
     * @throws ColumnException if the column type is unrecognized or null
     */
    @SuppressWarnings("unchecked")
    public static <T> AbstractColumn<T> createNewColumnInstance(AbstractColumn<T> column) {
        return switch (column) {
            case BooleanColumn ignored -> (AbstractColumn<T>) BooleanColumn.create(column.getColumnName());
            case ByteColumn ignored -> (AbstractColumn<T>) ByteColumn.create(column.getColumnName());
            case CharacterColumn ignored -> (AbstractColumn<T>) CharacterColumn.create(column.getColumnName());
            case DoubleColumn ignored -> (AbstractColumn<T>) DoubleColumn.create(column.getColumnName());
            case FloatColumn ignored -> (AbstractColumn<T>) FloatColumn.create(column.getColumnName());
            case IntegerColumn ignored -> (AbstractColumn<T>) IntegerColumn.create(column.getColumnName());
            case LongColumn ignored -> (AbstractColumn<T>) LongColumn.create(column.getColumnName());
            case NumberColumn ignored -> (AbstractColumn<T>) NumberColumn.create(column.getColumnName());
            case ShortColumn ignored -> (AbstractColumn<T>) ShortColumn.create(column.getColumnName());
            case StringColumn ignored -> (AbstractColumn<T>) StringColumn.create(column.getColumnName());
            case null, default -> throw new ColumnException("Unexpected value: " + column);
        };
    }
}