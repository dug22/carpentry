package io.github.dug22.carpentry.drop;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.row.DataRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class DataFrameDropTest {

    private DataFrame dataFrame;

    @BeforeEach
    public void setup() {
        dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/palmer%20penguins.csv");
    }

    @Test
    public void dropColumTest() {
        dataFrame = dataFrame.drop("sex");
        assertFalse(dataFrame.containsColumn("sex"));
    }

    @Test
    public void dropMultipleColumnsTest() {
        dataFrame = dataFrame.drop("sex", "year");
        assertFalse(dataFrame.containsColumns("sex", "year"));
    }

    @Test
    public void dropColumnByIndexTest() {
        dataFrame = dataFrame.drop(7);
        assertFalse(dataFrame.containsColumn("year"));
    }

    @Test
    public void dropMultipleColumnsByIndexTest() {
        dataFrame = dataFrame.drop(0, 5);
        assertFalse(dataFrame.containsColumns("species", "year"));
    }

    @Test
    public void dropAnyTest() {
        dataFrame = dataFrame.dropNa(How.ANY);
        Set<?> actualResults = Arrays.stream(dataFrame.getColumn("sex").getValues()).collect(Collectors.toSet());
        assertEquals(Set.of("female", "male"), actualResults);
    }

    @Test
    public void dropAllTest() {
        int originalRowCount = dataFrame.getRowCount();

        //Here we are taking row 1 and setting all their values to null to properly demonstrate how drop How.ALL works
        DataRow dataRow = dataFrame.getRows().get(1);
        Map<String, Object> row = dataRow.toMap();
        row.replaceAll((k, v) -> null);
        for (String columnName : row.keySet()) {
            Column<?> column = dataFrame.getColumn(columnName);
            column.set(dataRow.getIndex(), null);
        }
        dataFrame = dataFrame.dropNa(How.ALL);
        int currentRowCount = dataFrame.getRowCount();
        assertNotEquals(originalRowCount, currentRowCount);
    }
}
