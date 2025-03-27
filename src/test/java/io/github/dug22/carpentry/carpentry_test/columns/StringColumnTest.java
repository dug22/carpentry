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

package io.github.dug22.carpentry.carpentry_test.columns;

import io.github.dug22.carpentry.columns.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringColumnTest {

    private final StringColumn column = StringColumn.create("Test");

    @BeforeEach
    public void setUp() {
        column.append("This");
        column.append("is");
        column.append("a");
        column.append("test");
    }

    @Test
    public void appendTest(){
        column.append("1");
        assertEquals(5, column.size());
        column.appendNull();
        assertNull(column.getValues()[5]);
        column.appendAll(new String[]{"Test2", "Test3"});
        assertEquals(8, column.size());
    }

    @Test
    public void removeTest(){
        column.removeAtIndex(1);
        assertEquals(3, column.size());
        column.remove("This");
        assertFalse(column.contains("This"));
        column.removeAll("a", "test");
        assertTrue(column.isEmpty());
    }

    @Test
    public void copyEmptyTest(){
        StringColumn emptyCopy= column.copyEmpty();
        assertTrue(emptyCopy.isEmpty());
    }

    @Test
    public void copyTest(){
        StringColumn copy = column.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void uniqueTest(){
        StringColumn unique = column.unique();
        assertEquals(4, unique.size());
    }

    @Test
    public void filterTest(){
        StringColumn filter = column.filter(a -> a.equals("test"));
        assertEquals(1, filter.size());
        assertTrue(filter.contains("test"));
    }

    @Test
    public void applyTest(){
        StringColumn apply = column.apply(String::toUpperCase);
        for(int i = 0; i < apply.size(); i++){
            assertEquals(apply.get(i), apply.get(i).toUpperCase());
        }
    }

    @Test
    public void sort() {
        StringColumn newColumn = column.lowerCase();

        newColumn.sortAscending();
        for (int i = 0; i < 4; i++) {
            assertEquals(new String[]{"a", "is", "test", "this"}[i], newColumn.get(i));
        }

        newColumn.sortDescending();
        for (int i = 0; i < 4; i++) {
            assertEquals(new String[]{"this", "test", "is", "a"}[i], newColumn.get(i));
        }
    }


    @Test
    public void replaceTest() {
        column.replace(new String[]{"this", "is"}, "thisis");
        assertFalse(column.containsAll("this", "is"));
    }


    @Test
    public void capitalizeTest(){
        StringColumn capitalize = column.capitalize();
        for(int i = 0; i < capitalize.size(); i++){
            assertTrue(Character.isUpperCase(capitalize.get(i).charAt(0)));
        }
    }

    @Test
    public void upperCaseTest(){
        StringColumn upperCase = column.upperCase();
        for(int i = 0; i < upperCase.size(); i++){
            assertEquals(upperCase.get(i), upperCase.get(i).toUpperCase());
        }
    }

    @Test
    public void lowerCaseTest(){
        StringColumn lowerCase = column.lowerCase();
        for(int i = 0; i < lowerCase.size(); i++){
            assertEquals(lowerCase.get(i), lowerCase.get(i).toLowerCase());
        }
    }

    @Test
    public void repeatTest(){
        StringColumn repeat = column.repeat(3);
        assertTrue(repeat.containsAll("ThisThisThis", "isisis", "aaa","testtesttest"));
    }

    @Test
    public void trimTest(){
        column.append("Trim Test ");
        StringColumn trim = column.trim();
        assertEquals("Trim Test", trim.getValues()[4]);
    }

    @Test
    public void join(){
        StringColumn join = column.join(":", IntegerColumn.create("Test2", new Integer[]{1,2,3,4}));
        assertEquals("This:1",join.getValues()[0]);
        assertEquals("is:2",join.getValues()[1]);
        assertEquals("a:3",join.getValues()[2]);
        assertEquals("test:4",join.getValues()[3]);
    }

    @Test
    public void replaceFirstTest(){
        column.append("Replace Test");
        StringColumn replaceFirst = column.replaceFirst("\\b\\w+\\b", "Replacement");
        for(int i = 0; i < replaceFirst.size() - 1; i++){
            assertEquals("Replacement", replaceFirst.get(i));
        }

        assertEquals("Replacement Test", replaceFirst.get(4));
    }

    @Test
    public void replaceAllTest(){
        StringColumn replaceAll = column.replaceAll("\\b\\w+\\b", "Replacement");
        for(int i = 0; i < replaceAll.size(); i++){
            assertEquals("Replacement", replaceAll.get(i));
        }
    }

    @Test
    public void truncateTest(){
        StringColumn truncate = column.truncate(2);
        assertEquals(3, truncate.get(0).length());
        assertEquals(2, truncate.get(1).length());
        assertEquals(1, truncate.get(2).length());
        assertEquals(3, truncate.get(3).length());
    }

    @Test
    public void substringTest1(){
        StringColumn substring = column.substring(1);
        assertEquals("his", substring.get(0));
        assertEquals("s", substring.get(1));
        assertEquals("", substring.get(2));
        assertEquals("est", substring.get(3));
    }

    @Test
    public void substringTest2(){
        StringColumn substring = column.substring(0,1);
        assertEquals("T", substring.get(0));
        assertEquals("i", substring.get(1));
        assertEquals("a", substring.get(2));
        assertEquals("t", substring.get(3));
    }

    @Test
    public void asDoubleColumnTest(){
        convertToNumericText();
        DoubleColumn doubleColumn = column.asDoubleColumn();
        for(int i = 0; i < doubleColumn.size(); i++){
            assertEquals(i, doubleColumn.get(i));
        }
    }

    @Test
    public void asIntColumnTest() {
        convertToNumericText();
        IntegerColumn integerColumn = column.asIntegerColumn();
        for (int i = 0; i < integerColumn.size(); i++) {
            assertEquals(i, integerColumn.get(i));
        }
    }

    @Test
    public void asFloatColumnTest(){
        convertToNumericText();
        FloatColumn floatColumn = column.asFloatColumn();
        for(int i = 0; i < floatColumn.size(); i++){
            assertEquals(i, floatColumn.get(i));
        }
    }

    @Test
    public void asCharacterColumnTest(){
        CharacterColumn characterColumn = column.asCharacterColumn();
        assertEquals('T', characterColumn.get(0));
        assertEquals('i', characterColumn.get(1));
        assertEquals('a', characterColumn.get(2));
        assertEquals('t', characterColumn.get(3));
    }

    private void convertToNumericText(){
        for(int i = 0; i < column.size(); i++){
            String original = column.get(i);
            String converted = String.valueOf(i);
            column.set(i, converted);
            assertNotEquals(original, converted);
        }
    }
}
