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

package io.github.dug22.carpentry.carpentry_test.column;

import io.github.dug22.carpentry.column.ColumnCreator;
import io.github.dug22.carpentry.columns.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class ColumnCreatorTest {

    @Test
    public void createNewBooleanColumnInstanceTest() {
        BooleanColumn column = BooleanColumn.create("Booleans", new Boolean[]{true, false, true, false});
        BooleanColumn newInstance = (BooleanColumn) ColumnCreator.createNewColumnInstance(column);
        assertInstanceOf(BooleanColumn.class, newInstance);
        assertEquals("Booleans", newInstance.getColumnName());
    }

    @Test
    public void createNewByteColumnInstanceTest() {
        ByteColumn column = ByteColumn.create("Bytes", new Byte[]{1, 2, 3, 4});
        ByteColumn newInstance = (ByteColumn) ColumnCreator.createNewColumnInstance(column);
        assertInstanceOf(ByteColumn.class, newInstance);
        assertEquals("Bytes", newInstance.getColumnName());
    }

    @Test
    public void createNewCharColumnInstanceTest() {
        CharacterColumn column = CharacterColumn.create("Chars", new Character[]{'a', 'b', 'c', 'd'});
        CharacterColumn newInstance = (CharacterColumn) ColumnCreator.createNewColumnInstance(column);
        assertInstanceOf(CharacterColumn.class, newInstance);
        assertEquals("Chars", newInstance.getColumnName());
    }

    @Test
    public void createNewDoubleColumnInstanceTest() {
        DoubleColumn column = DoubleColumn.create("Doubles", new Double[]{1.0, 2.5, 3.7, 4.2});
        DoubleColumn newInstance = (DoubleColumn) ColumnCreator.createNewColumnInstance(column);
        assertInstanceOf(DoubleColumn.class, newInstance);
        assertEquals("Doubles", newInstance.getColumnName());
    }

    @Test
    public void createNewFloatColumnInstanceTest() {
        FloatColumn column = FloatColumn.create("Floats", new Float[]{1.0f, 2.5f, 3.7f, 4.2f});
        FloatColumn newInstance = (FloatColumn) ColumnCreator.createNewColumnInstance(column);
        assertInstanceOf(FloatColumn.class, newInstance);
        assertEquals("Floats", newInstance.getColumnName());
    }

    @Test
    public void createNewIntegerColumnInstanceTest() {
        IntegerColumn column = IntegerColumn.create("Integers", new Integer[]{1, 2, 3, 4});
        IntegerColumn newInstance = (IntegerColumn) ColumnCreator.createNewColumnInstance(column);
        assertInstanceOf(IntegerColumn.class, newInstance);
        assertEquals("Integers", newInstance.getColumnName());
    }

    @Test
    public void createNewLongColumnInstanceTest() {
        LongColumn column = LongColumn.create("Longs", new Long[]{1L, 2L, 3L, 4L});
        LongColumn newInstance = (LongColumn) ColumnCreator.createNewColumnInstance(column);
        assertInstanceOf(LongColumn.class, newInstance);
        assertEquals("Longs", newInstance.getColumnName());
    }

    @Test
    public void createNewNumberColumnInstanceTest() {
        NumberColumn column = NumberColumn.create("Numbers", new Number[]{1, 2.5, 3L, 4.2f});
        NumberColumn newInstance = (NumberColumn) ColumnCreator.createNewColumnInstance(column);
        assertInstanceOf(NumberColumn.class, newInstance);
        assertEquals("Numbers", newInstance.getColumnName());
    }

    @Test
    public void createNewShortColumnInstanceTest() {
        ShortColumn column = ShortColumn.create("Shorts", new Short[]{1, 2, 3, 4});
        ShortColumn newInstance = (ShortColumn) ColumnCreator.createNewColumnInstance(column);
        assertInstanceOf(ShortColumn.class, newInstance);
        assertEquals("Shorts", newInstance.getColumnName());
    }

    @Test
    public void createNewStringColumnInstanceTest() {
        StringColumn column = StringColumn.create("Strings", new String[]{"A", "B", "C", "D"});
        StringColumn newInstance = (StringColumn) ColumnCreator.createNewColumnInstance(column);
        assertInstanceOf(StringColumn.class, newInstance);
        assertEquals("Strings", newInstance.getColumnName());
    }
}
