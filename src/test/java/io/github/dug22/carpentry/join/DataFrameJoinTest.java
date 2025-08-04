package io.github.dug22.carpentry.join;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.column.impl.IntegerColumn;
import io.github.dug22.carpentry.column.impl.StringColumn;
import io.github.dug22.carpentry.row.DataRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataFrameJoinTest {

    private DataFrame dataFrameOne;
    private DataFrame dataFrameTwo;

    @BeforeEach
    public void setup() {
        dataFrameOne = DataFrame.create(
                IntegerColumn.create("ID", new Integer[]{1, 2, 3,}),
                StringColumn.create("Name", new String[]{"Bob", "John", "Andrew"})
        );

         dataFrameTwo = DataFrame.create(
                IntegerColumn.create("ID", new Integer[]{1, 2, 4}),
                IntegerColumn.create("Age", new Integer[]{25, 30, 22})
        );
    }


    @Test
    public void innerJoinTest() {
        DataFrame innerJoin = dataFrameOne.join(dataFrameTwo, JoinType.INNER, "ID", "ID");
        // Expected output equivalent to:
        // ┌────┬────────┬─────┐
        // │ ID │ Name   │ Age │
        // ├────┼────────┼─────┤
        // │ 1  │ Bob    │ 25  │
        // │ 2  │ John   │ 30  │
        // └────┴────────┴─────┘

        assertEquals(3, innerJoin.getColumnCount(), "Result should have 3 columns");
        assertEquals(Arrays.asList("ID", "Name", "Age"), innerJoin.columnNames(), "Column names should match expected");
        assertEquals(2, innerJoin.getRowCount(), "Inner join should return 2 rows");
        checkJoinedRow(innerJoin.getRow(0), 1, "Bob", 25);
        checkJoinedRow(innerJoin.getRow(1), 2, "John", 30);
    }

    @Test
    public void leftJoinTest() {
        DataFrame leftJoin = dataFrameOne.join(dataFrameTwo, JoinType.LEFT, "ID", "ID", "_left", "_right");
        // Expected output equivalent to:
        // ┌────┬───────────┬───────────┐
        // │ ID │ Name_left │ Age_right │
        // ├────┼───────────┼───────────┤
        // │ 1  │ Bob       │ 25        │
        // │ 2  │ John      │ 30        │
        // │ 3  │ Andrew    │           │
        // └────┴───────────┴───────────┘

        assertEquals(3, leftJoin.getColumnCount(), "Result should have 3 columns");
        assertEquals(Arrays.asList("ID", "Name_left", "Age_right"), leftJoin.columnNames(), "Column names should match expected");
        assertEquals(3, leftJoin.getRowCount(), "Left join should return 3 rows");

        checkJoinedRow(leftJoin.getRow(0), 1, "Bob", 25);
        checkJoinedRow(leftJoin.getRow(1), 2, "John", 30);
        checkJoinedRow(leftJoin.getRow(2), 3, "Andrew", null);
    }

    @Test
    public void rightJoinTest() {
        DataFrame rightJoin = dataFrameOne.join(dataFrameTwo, JoinType.RIGHT, "ID", "ID");
        //rightJoin.show();
        // Expected output equivalent to:
        // ┌────┬────────┬─────┐
        // │ ID │ Name   │ Age │
        // ├────┼────────┼─────┤
        // │ 1  │ Bob    │ 25  │
        // │ 2  │ John   │ 30  │
        // │ 4  │ null   │ 22  │
        // └────┴────────┴─────┘

        assertEquals(3, rightJoin.getColumnCount(), "Result should have 3 columns");
        assertEquals(Arrays.asList("ID", "Name", "Age"), rightJoin.columnNames(), "Column names should match expected");
        assertEquals(3, rightJoin.getRowCount(), "Right join should return 3 rows");

        checkJoinedRow(rightJoin.getRow(0), 1, "Bob", 25);
        checkJoinedRow(rightJoin.getRow(1), 2, "John", 30);
        checkJoinedRow(rightJoin.getRow(2), 4, null, 22);
    }

    @Test
    public void outerJoinTest() {
        DataFrame outerJoin = dataFrameOne.join(dataFrameTwo, JoinType.OUTER, "ID", "ID");
        // Expected output equivalent to:
        // ┌────┬────────┬─────┐
        // │ ID │ Name   │ Age │
        // ├────┼────────┼─────┤
        // │ 1  │ Bob    │ 25  │
        // │ 2  │ John   │ 30  │
        // │ 3  │ Andrew │ null│
        // │ 4  │ null   │ 22  │
        // └────┴────────┴─────┘

        assertEquals(3, outerJoin.getColumnCount(), "Result should have 3 columns");
        assertEquals(Arrays.asList("ID", "Name", "Age"), outerJoin.columnNames(), "Column names should match expected");
        assertEquals(4, outerJoin.getRowCount(), "Outer join should return 4 rows");

        checkJoinedRow(outerJoin.getRow(0), 1, "Bob", 25);
        checkJoinedRow(outerJoin.getRow(1), 2, "John", 30);
        checkJoinedRow(outerJoin.getRow(2), 3, "Andrew", null);
        checkJoinedRow(outerJoin.getRow(3), 4, null, 22);
    }


    private void checkJoinedRow(DataRow row, Object id, Object name, Object age) {
        assertEquals(id, row.get("ID"));
        assertEquals(name, row.get(row.toMap().containsKey("Name_left") ? "Name_left" : "Name"));
        assertEquals(age, row.get(row.toMap().containsKey("Age_right") ? "Age_right" : "Age"));
    }
}