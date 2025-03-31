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

package io.github.dug22.carpentry.carpentry_test.columns;

import io.github.dug22.carpentry.columns.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BooleanColumnTest {

    private final BooleanColumn column = BooleanColumn.create("Test");

    @BeforeEach
    public void setUp() {
        column.append(true);
        column.append(false);
        column.append(true);
        column.append(false);
    }

    @Test
    public void appendTest() {
        column.append(false);
        assertEquals(5, column.size());
        column.appendNull();
        assertNull(column.getValues()[5]);
        column.appendAll(new Boolean[]{true, false, true});
        assertEquals(9, column.size());
    }

    @Test
    public void removeTest() {
        column.removeAtIndex(1);
        assertEquals(3, column.size());
        column.removeAll(true);
        assertEquals(1, column.size());
        column.remove(false);
        assertEquals(0, column.size());
    }

    @Test
    public void copyEmptyTest() {
        BooleanColumn emptyCopy = column.copyEmpty();
        assertTrue(emptyCopy.isEmpty());
    }

    @Test
    public void copyTest() {
        BooleanColumn copy = column.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void uniqueTest() {
        BooleanColumn unique = column.unique();
        assertEquals(2, unique.size());
    }

    @Test
    public void filterTest() {
        BooleanColumn filter = column.filter(a -> a);
        assertTrue(filter.containsAll(true));
    }

    @Test
    public void applyTest() {
        BooleanColumn apply = column.apply(a -> a);
        assertTrue(apply.containsAll(true));
    }

    @Test
    public void sortTest(){
        column.sortAscending();
        assertArrayEquals(new Boolean[]{false, false, true, true}, column.getValues());
        column.sortDescending();
        assertArrayEquals(new Boolean[]{true, true, false, false}, column.getValues());
    }

    @Test
    public void replaceTest(){
        column.replace(new Boolean[]{false}, true);
        assertFalse(column.contains(false));
    }

    @Test
    public void andTest() {
        BooleanColumn firstOther = BooleanColumn.create("Test2", new Boolean[]{false, false, true, false});
        BooleanColumn secondOther = BooleanColumn.create("Test3", new Boolean[]{true, false, false, true});
        BooleanColumn and = column.and(firstOther, secondOther);
        assertEquals(4, and.size());
        assertFalse(and.get(0));
        assertFalse(and.get(1));
        assertFalse(and.get(2));
        assertFalse(and.get(3));
    }

    @Test
    public void andNotTest() {
        BooleanColumn firstOther = BooleanColumn.create("Test2", new Boolean[]{false, false, true, false});
        BooleanColumn secondOther = BooleanColumn.create("Test3", new Boolean[]{true, false, true, true});
        BooleanColumn andNot = column.andNot(firstOther, secondOther);
        assertEquals(4, andNot.size());
        assertFalse(andNot.get(0));
        assertFalse(andNot.get(1));
        assertFalse(andNot.get(2));
        assertFalse(andNot.get(3));;
    }

    @Test
    public void orTest() {
        BooleanColumn firstOther = BooleanColumn.create("Test2", new Boolean[]{false, false, true, false});
        BooleanColumn secondOther = BooleanColumn.create("Test3", new Boolean[]{false, true, false, true});
        BooleanColumn or = column.or(firstOther, secondOther);
        assertEquals(4, or.size());
        assertTrue(or.get(0));
        assertTrue(or.get(1));
        assertTrue(or.get(2));
        assertTrue(or.get(3));
    }

    @Test
    public void xorTest() {
        BooleanColumn firstOther = BooleanColumn.create("Test2", new Boolean[]{false, false, true, false});
        BooleanColumn secondOther = BooleanColumn.create("Test3",new Boolean[]{true, true, true, false});
        BooleanColumn xor = column.xor(firstOther, secondOther);
        assertEquals(4, xor.size());
        assertFalse(xor.get(0));
        assertTrue(xor.get(1));
        assertTrue(xor.get(2));
        assertFalse(xor.get(3));
    }

    @Test
    public void flipTest(){
        BooleanColumn flip = column.flip();
        assertEquals(4, flip.size());
        assertFalse(flip.get(0));
        assertTrue(flip.get(1));
        assertFalse(flip.get(2));
        assertTrue(flip.get(3));
    }

    @Test
    public void asDoubleColumnTest(){
        DoubleColumn doubleColumn = column.asDoubleColumn();
        assertEquals(4, doubleColumn.size());
        assertEquals(1D, doubleColumn.get(0));
        assertEquals(0D, doubleColumn.get(1));
        assertEquals(1D, doubleColumn.get(2));
        assertEquals(0D, doubleColumn.get(3));
    }

    @Test
    public void asIntegerTest(){
        IntegerColumn integerColumn = column.asIntegerColumn();
        assertEquals(4, integerColumn.size());
        assertEquals(1, integerColumn.get(0));
        assertEquals(0, integerColumn.get(1));
        assertEquals(1, integerColumn.get(2));
        assertEquals(0, integerColumn.get(3));
    }

    @Test
    public void asStringColumn(){
        StringColumn stringColumn = column.asStringColumn().capitalize();
        assertEquals(4, stringColumn.size());
        assertEquals("True", stringColumn.get(0));
        assertEquals("False", stringColumn.get(1));
        assertEquals("True", stringColumn.get(2));
        assertEquals("False", stringColumn.get(3));
    }

    @Test
    public void asCharacterColumnTest(){
        CharacterColumn characterColumn = column.asCharacterColumn().upperCase();
        assertEquals(4, characterColumn.size());
        assertEquals('T', characterColumn.get(0));
        assertEquals('F', characterColumn.get(1));
        assertEquals('T', characterColumn.get(2));
        assertEquals('F', characterColumn.get(3));
    }
}
