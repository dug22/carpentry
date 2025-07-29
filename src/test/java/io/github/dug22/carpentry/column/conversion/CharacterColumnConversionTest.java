package io.github.dug22.carpentry.column.conversion;

import io.github.dug22.carpentry.column.impl.CharacterColumn;
import io.github.dug22.carpentry.column.impl.StringColumn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterColumnConversionTest {

    @Test
    public void asStringColumnTest(){
        CharacterColumn characterColumn = CharacterColumn.create("Characters", new Character[]{'a', 'b', 'c'});
        StringColumn stringColumn = characterColumn.asStringColumn();
        assertAll(
                () -> assertEquals(3, stringColumn.size()),
                () -> assertArrayEquals(new String[]{"a", "b", "c"}, stringColumn.getValues())
        );
    }
}
