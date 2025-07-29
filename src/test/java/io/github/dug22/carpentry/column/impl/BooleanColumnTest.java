package io.github.dug22.carpentry.column.impl;

import io.github.dug22.carpentry.column.parser.BooleanParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanColumnTest {

    private BooleanColumn booleanColumn;

    @BeforeEach
    public void setup() {
        booleanColumn = BooleanColumn.create("Booleans", new Boolean[]{true, false, true, false});
    }

    @Test
    public void appendTest() {
        booleanColumn.append(true);
        booleanColumn.appendNull();
        booleanColumn.appendAll(new Boolean[]{false, true});
        assertAll(
                () -> assertEquals(8, booleanColumn.size()),
                () -> assertArrayEquals(new Boolean[]{true, false, true, false, true, null, false, true}, booleanColumn.getValues())
        );
    }

    @Test
    public void copyTest() {
        BooleanColumn copy = booleanColumn.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void emptyCopyTest() {
        BooleanColumn emptyCopy = booleanColumn.emptyCopy();
        assertEquals(0, emptyCopy.size());
    }

    @Test
    public void uniqueTest() {
        BooleanColumn unique = booleanColumn.unique();
        assertAll(
                () -> assertEquals(2, unique.size()),
                () -> assertArrayEquals(new Boolean[]{true, false}, unique.getValues())
        );
    }

    @Test
    public void applyTest() {
        BooleanColumn apply = booleanColumn.apply(i -> i);
        assertAll(
                () -> assertEquals(4, apply.size()),
                () -> assertTrue(apply.containsAll(List.of(true)))
        );
    }

    @Test
    public void filterTest() {
        BooleanColumn filter = booleanColumn.filter(i -> i);
        assertAll(
                () -> assertEquals(2, filter.size()),
                () -> assertTrue(filter.containsAll(List.of(true)))
        );
    }

    @Test
    public void sortTest() {
        assertAll(
                () -> {
                    booleanColumn.sortAscending();
                    assertArrayEquals(new Boolean[]{false, false, true, true}, booleanColumn.getValues());
                },

                () -> {
                    booleanColumn.sortDescending();
                    assertArrayEquals(new Boolean[]{true, true, false, false}, booleanColumn.getValues());
                });

    }

    @Test
    public void replaceTest() {
        booleanColumn.replace(new Boolean[]{false}, true);
        assertFalse(booleanColumn.contains(false));
    }



    @Test
    public void parseTest(){
        BooleanParser parser = booleanColumn.getColumnParser();
        Set<String> TRUE_VALUES = Set.of(
                "true", "True", "TRUE", "t", "T", "yes", "Yes", "YES", "y", "Y"
        );

        for(String trueResult : TRUE_VALUES){
            assertEquals(true, parser.parse(trueResult));
        }

        Set<String> FALSE_VALUES = Set.of(
                "false", "False", "FALSE", "f", "F", "no", "No", "NO", "n", "N"
        );

        for(String falseResult : FALSE_VALUES){
            assertEquals(false, parser.parse(falseResult));
        }
    }
}
