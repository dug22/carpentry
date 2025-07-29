package io.github.dug22.carpentry.query;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.column.impl.StringColumn;
import io.github.dug22.carpentry.row.DataRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataFrameQueryTest {

    private DataFrame dataFrame;

    @BeforeEach
    public void setup() {
        dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/titanic.csv");
    }

    /**
     * <b>General Rule</b>
     * Remember to surround the column name and value you're referencing with the following character `` (backticks)
     */

    @Test
    public void simpleQueryTest() {
        DataFrame result = dataFrame.query("`PassengerId` >= `5`");
        int passengerId = result.getRow(0).get("PassengerId");
        assertEquals(5, passengerId);
    }

    @Test
    public void regexQueryTest() {
        DataFrame result = dataFrame.query("`Name` ~= `^[aeiouAEIOU]`");
        StringColumn nameColumn = result.stringColumn("Name");
        for (int index = 0; index < nameColumn.size(); index++) {
            String name = nameColumn.get(index);
            char firstCharacter = Character.toUpperCase(name.charAt(0));
            assertTrue("AEIOU".indexOf(firstCharacter) >= 0);
        }
    }

    @Test
    public void logicalOrQueryTest() {
        DataFrame result = dataFrame.query("`Embarked` = `C` OR `Embarked` = `S`");
        for (int index = 0; index < result.getRowCount(); index++) {
            DataRow row = result.getRow(index);
            String embarked = row.get("Embarked");
            assertTrue(embarked.equalsIgnoreCase("C") || embarked.equalsIgnoreCase("S"));
        }
    }

    @Test
    public void logicalAndQueryTest() {
        DataFrame result = dataFrame.query("`Sex` = `female` AND `Survived` = `1`");
        for (int index = 0; index < result.getRowCount(); index++) {
            DataRow row = result.getRow(index);
            String sex = row.get("Sex");
            int survived = row.get("Survived");
            assertEquals("female", sex);
            assertEquals(1, survived);
        }
    }

    @Test
    public void compoundLogicalQueryTest() {
        DataFrame result = dataFrame.query("(`Sex` = `female` AND `Survived` = `1`) OR (`Sex` = `male` AND `Survived` = `1`)");
        for (int index = 0; index < result.getRowCount(); index++) {
            DataRow row = result.getRow(index);
            String sex = row.get("Sex");
            int survived = row.get("Survived");
            assertTrue(sex.equalsIgnoreCase("female") || sex.equalsIgnoreCase("male"));
            assertEquals(1, survived);
        }
    }

    @Test
    public void complexCompoundLogicalQueryTest(){
        DataFrame result = dataFrame.query("(`Fare` >= `50.00` AND `Survived` = `1`) AND (`Name` ~= `^[aeiouAEIOU]` AND `PassengerId` > `500`) AND (`Embarked` = `S`)");
        for (int index = 0; index < result.getRowCount(); index++) {
            DataRow row = result.getRow(index);
            Double fare = row.get("Fare");
            int survived = row.get("Survived");
            String name = row.get("Name");
            char firstCharacter = Character.toUpperCase(name.charAt(0));
            int passengerID = row.get("PassengerId");
            String embarked = row.get("Embarked");
            assertTrue(fare >= 50D);
            assertEquals(1, survived);
            assertTrue("AEIOU".indexOf(firstCharacter) >= 0);
            assertTrue(passengerID >= 500);
            assertTrue(embarked.equalsIgnoreCase("S"));
        }
    }

    @Test
    public void invalidQueryTest() {
        assertThrows(QueryException.class, () -> dataFrame.query("`Survived` === `1`"));
    }
}