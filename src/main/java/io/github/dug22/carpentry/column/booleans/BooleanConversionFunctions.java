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

import io.github.dug22.carpentry.column.ColumnConvertor;
import io.github.dug22.carpentry.columns.*;

public interface BooleanConversionFunctions {

    /**
     * Refers to the current BooleanColumn you want to convert
     * @return the current BooleanColumn that you want to convert.
     */
    private BooleanColumn booleanColumn() {
        return (BooleanColumn) this;
    }

    /**
     * Converts a BooleanColumn to a DoubleColumn
     * @return a DoubleColumn
     */
    default DoubleColumn asDoubleColumn() {
        return ColumnConvertor.convert(booleanColumn(), Double.class, a -> a ? 1.0 : 0.0);
    }

    /**
     * Converts a BooleanColumn to an IntegerColumn
     * @return a IntegerColumn
     */
    default IntegerColumn asIntegerColumn() {
        return ColumnConvertor.convert(booleanColumn(), Integer.class, a -> a ? 1 : 0);
    }

    /**
     * Converts a BooleanColumn to a StringColumn
     * @return a StringColumn
     */
    default StringColumn asStringColumn() {
        return ColumnConvertor.convert(booleanColumn(), String.class, String::valueOf);
    }

    /**
     * Converts a BooleanColumn to a CharacterColumn
     * @return a CharacterColumn
     */
    default CharacterColumn asCharacterColumn() {
        return ColumnConvertor.convert(booleanColumn(), Character.class, a -> a ? 't' : 'f');
    }
}
