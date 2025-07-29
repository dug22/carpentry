package io.github.dug22.carpentry.column.transformation;

import io.github.dug22.carpentry.column.impl.CharacterColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CharacterTransformationsTest {

    private CharacterColumn characterColumn;

    @BeforeEach
    public void setup(){
        characterColumn = CharacterColumn.create("Vowels", new Character[]{'a', 'e', 'i', 'o', 'u'});
    }

    @Test
    public void uppercaseAndLowercaseTest(){
        CharacterColumn uppercase = characterColumn.uppercase();
        assertArrayEquals(new Character[]{'A', 'E', 'I', 'O', 'U'}, uppercase.getValues());
        CharacterColumn lowercase = uppercase.lowercase();
        assertArrayEquals(new Character[]{'a', 'e', 'i', 'o', 'u'}, lowercase.getValues());
    }
}
