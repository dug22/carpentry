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

import io.github.dug22.carpentry.columns.CharacterColumn;
import io.github.dug22.carpentry.columns.StringColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CharacterColumnTest {

    private final CharacterColumn column = CharacterColumn.create("Test");

    @BeforeEach
    public void setUp(){
        column.append('a');
        column.append('b');
        column.append('c');
        column.append('e');
        column.append('d');
        column.append('e');
    }

    @Test
    public void appendTest(){
        column.append('1');
        assertEquals(7, column.size());
        column.appendNull();
        assertNull(column.getValues()[7]);
        column.appendAll(new Character[]{'5','8'});
        assertEquals(10, column.size());
    }

    @Test
    public void removeTest(){
        column.removeAtIndex(1);
        assertEquals(5, column.size());
        column.remove('a');
        assertFalse(column.contains('a'));
        column.removeAll('c', 'd');
        assertFalse(column.contains('c'));
        assertFalse(column.contains('d'));
    }

    @Test
    public void copyEmptyTest(){
        CharacterColumn emptyCopy= column.copyEmpty();
        assertTrue(emptyCopy.isEmpty());
    }

    @Test
    public void copyTest(){
        CharacterColumn copy = column.copy();
        assertEquals(6, copy.size());
    }

    @Test
    public void uniqueTest(){
        CharacterColumn unique = column.unique();
        assertEquals(5, unique.size());
    }

    @Test
    public void filterTest(){
        CharacterColumn filter = column.filter(a -> a.equals('e'));
        assertEquals(2, filter.size());
        assertTrue(filter.contains('e'));
    }

    @Test
    public void applyTest(){
        CharacterColumn apply = column.apply(Character::toUpperCase);
        for(int i = 0; i < apply.size(); i++){
            assertEquals(apply.get(i), Character.toUpperCase(apply.get(i)));
        }
    }

    @Test
    public void sortTest(){
        column.sortAscending();
        assertArrayEquals(new Character[]{'a', 'b', 'c', 'd', 'e', 'e'}, column.getValues());
        column.sortDescending();
        assertArrayEquals(new Character[]{'e', 'e', 'd', 'c', 'b', 'a'}, column.getValues());
    }

    @Test
    public void replaceTest(){
        column.replace(new Character[]{'a', 'b'}, 'n');
        assertFalse(column.containsAll('a', 'b'));
    }

    @Test
    public void upperCaseTest(){
        CharacterColumn upperCase = column.upperCase();
        for(int i = 0; i < upperCase.size(); i++){
            assertEquals(upperCase.get(i),Character.toUpperCase(upperCase.get(i)));
        }
    }

    @Test
    public void lowerCaseTest(){
        CharacterColumn lowerChase = column.lowerChase();
        for(int i = 0; i < lowerChase.size(); i++){
            assertEquals(lowerChase.get(i),Character.toLowerCase(lowerChase.get(i)));
        }
    }

    @Test
    public void asStringColumnTest(){
        StringColumn newStringColumn = column.asStringColumn();
        assertEquals("a", newStringColumn.get(0));
        assertEquals("b", newStringColumn.get(1));
        assertEquals("c", newStringColumn.get(2));
        assertEquals("e", newStringColumn.get(3));
        assertEquals("d", newStringColumn.get(4));
        assertEquals("e", newStringColumn.get(5));
    }
}
