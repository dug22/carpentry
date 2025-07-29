package io.github.dug22.carpentry.column.impl;

import io.github.dug22.carpentry.column.parser.CharacterParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterColumnTest {

    private CharacterColumn characterColumn;

    @BeforeEach
    public void setup(){
        characterColumn = CharacterColumn.create("Characters", new Character[]{'a', 'b', 'c', 'c'});
    }

    @Test
    public void appendTest(){
        characterColumn.append('e');
        characterColumn.appendNull();
        characterColumn.appendAll(new Character[]{'f', 'g'});
        assertAll(
                () -> assertEquals(8, characterColumn.size()),
                () -> assertArrayEquals(new Character[]{'a', 'b', 'c', 'c', 'e', '*', 'f', 'g'}, characterColumn.getValues())
        );
    }

    @Test
    public void copyTest(){
        CharacterColumn copy = characterColumn.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void emptyCopyTest(){
        CharacterColumn emptyCopy = characterColumn.emptyCopy();
        assertEquals(0, emptyCopy.size());
    }

    @Test
    public void uniqueTest(){
        CharacterColumn unique = characterColumn.unique();
        assertAll(
                () -> assertEquals(3, unique.size()),
                () -> assertArrayEquals(new Character[]{'a', 'b', 'c'}, unique.getValues())
        );
    }

    @Test
    public void applyTest(){
        CharacterColumn apply = characterColumn.apply(i -> (char) (i + 1));
        assertAll(
                () -> assertEquals(4, apply.size()),
                () -> assertArrayEquals(new Character[]{'b', 'c', 'd', 'd'}, apply.getValues())
        );
    }

    @Test
    public void filterTest(){
        CharacterColumn filter = (CharacterColumn) characterColumn.filter(i -> i == 'a' || i == 'b');
        assertAll(
                () -> assertEquals(2, filter.size()),
                () -> assertArrayEquals(new Character[]{'a', 'b'}, filter.getValues())
        );
    }

    @Test
    public void sortTest(){
        assertAll(
                () -> {
                    characterColumn.sortAscending();
                    assertArrayEquals(new Character[]{'a', 'b', 'c', 'c'}, characterColumn.getValues());
                },

                () -> {
                    characterColumn.sortDescending();
                    assertArrayEquals(new Character[]{'c', 'c', 'b', 'a'}, characterColumn.getValues());
                }
        );
    }

    @Test
    public void replaceTest(){
        characterColumn.replace(new Character[]{'b', 'c'}, 'd');
        assertArrayEquals(new Character[]{'a', 'd', 'd', 'd'}, characterColumn.getValues());
    }

    @Test
    public void parserTest(){
        CharacterParser parser = characterColumn.getColumnParser();
        String characterValueOne = "A";
        assertEquals('A', parser.parse(characterValueOne));
        String characterValueTwo = "True";
        assertEquals('T', parser.parse(characterValueTwo));
        String characterValueThree = "False";
        assertEquals('F', parser.parse(characterValueThree));
    }
}
