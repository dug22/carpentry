package io.github.dug22.carpentry.column;

import io.github.dug22.carpentry.column.impl.IntegerColumn;
import io.github.dug22.carpentry.column.impl.StringColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColumnMapTest {

    private ColumnMap columnMap;

    @BeforeEach
    public void setup() {
        columnMap = new ColumnMap();
        columnMap.addColumn(IntegerColumn.create("Integers", new Integer[]{1, 2, 3, 4}));
        columnMap.addColumn(StringColumn.create("Strings", new String[]{"this", "is", "a", "test"}));
        columnMap.addColumn(StringColumn.create("Strings", new String[]{"This", "is", "a", "test"}));

    }

    @Test
    public void containsColumnTest() {
        assertTrue(columnMap.containsColumn("Integers"));
        assertTrue(columnMap.containsColumn("Strings"));
        assertTrue(columnMap.containsColumn("Strings_a"));
    }

    @Test
    public void getRowCountTest() {
        assertEquals(4, columnMap.getRowCount());
    }

    @Test
    public void indexOfKeyTest(){
        assertEquals(0, columnMap.indexOfKey("Integers"));
        assertEquals(1, columnMap.indexOfKey("Strings"));
        assertEquals(2, columnMap.indexOfKey("Strings_a"));
    }
}
