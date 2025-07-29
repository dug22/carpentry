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

package io.github.dug22.carpentry.column;

import io.github.dug22.carpentry.column.type.*;

public interface ColumnTypes {

    BooleanColumnType BOOLEAN_COLUMN_TYPE = new BooleanColumnType();
    ByteColumnType BYTE_COLUMN_TYPE = new ByteColumnType();
    CharacterColumnType CHARACTER_COLUMN_TYPE = new CharacterColumnType();
    DateColumnType DATE_COLUMN_TYPE = new DateColumnType();
    DateTimeColumnType DATE_TIME_COLUMN_TYPE = new DateTimeColumnType();
    DoubleColumnType DOUBLE_COLUMN_TYPE = new DoubleColumnType();
    FloatColumnType FLOAT_COLUMN_TYPE = new FloatColumnType();
    IntegerColumnType INTEGER_COLUMN_TYPE = new IntegerColumnType();
    LongColumnType LONG_COLUMN_TYPE = new LongColumnType();
    ShortColumnType SHORT_COLUMN_TYPE = new ShortColumnType();
    StringColumnType STRING_COLUMN_TYPE = new StringColumnType();
}
