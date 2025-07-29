package io.github.dug22.carpentry.column.transformation;

import io.github.dug22.carpentry.column.impl.StringColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class StringTransformationTest {

    private StringColumn stringColumn;

    @BeforeEach
    public void setup(){
        stringColumn = StringColumn.create("Animals", new String[]{"Dogs", "Cats", "Fishes"});
    }

    @Test
    public void repeatTest(){
        int n = 2;
        StringColumn result = stringColumn.repeat(n);
        assertArrayEquals(new String[]{"DogsDogs", "CatsCats", "FishesFishes"}, result.getValues());
    }

    @Test
    public void joinTest(){
        StringColumn otherColumn = StringColumn.create("Description", new String[]{"are a man's best friend", "are very fast", "live underwater"});
        StringColumn result = stringColumn.join(" ", otherColumn);
        String[] expectedValues = new String[]{
                "Dogs are a man's best friend",
                "Cats are very fast",
                "Fishes live underwater"
        };
        assertArrayEquals(expectedValues, result.getValues());
    }

    @Test
    public void replaceTest(){
        String regexPattern = "[aeiouAEIOU]";
        StringColumn replaceFirstResult = stringColumn.replaceFirst(regexPattern, "*");
        assertArrayEquals(new String[]{"D*gs", "C*ts", "F*shes"}, replaceFirstResult.getValues());
        StringColumn replaceAllResult = stringColumn.replaceAll(regexPattern, "*");
        assertArrayEquals(new String[]{"D*gs", "C*ts", "F*sh*s"}, replaceAllResult.getValues());
    }

    @Test
    public void truncateTest(){
        StringColumn result = stringColumn.truncate(5);
        assertArrayEquals(new String[]{"Dogs", "Cats", "Fi..."}, result.getValues());
    }

    @Test
    public void subStringTest(){
        int start = 1;
        int end = 3;
        StringColumn substringColumnOne = stringColumn.subString(start);
        assertArrayEquals(new String[]{"ogs", "ats", "ishes"}, substringColumnOne.getValues());
        StringColumn subStringColumnTwo = stringColumn.subString(start, end);
        assertArrayEquals(new String[]{"og", "at", "is"}, subStringColumnTwo.getValues());
    }
}
