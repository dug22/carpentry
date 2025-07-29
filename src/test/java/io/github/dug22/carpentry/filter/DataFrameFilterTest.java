package io.github.dug22.carpentry.filter;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.row.DataRow;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static io.github.dug22.carpentry.filter.FilterPredicateCondition.and;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataFrameFilterTest {


    private final DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/user_stats.csv");


    @Test
    public void filterTest() {
        DataFrame result = dataFrame.filter(
                and(
                        dataFrame.column("wins").gte(500),
                        dataFrame.column("id").between(0, 100_000),
                        dataFrame.column("join_date").isInMonth(Month.MARCH),
                        dataFrame.column("join_date").isInYear(2019)
                )
        );

        int currentRowCount = result.getRowCount();
        assertTrue(currentRowCount > 0);
        for (int i = 0; i < currentRowCount; i++) {
            DataRow row = result.getRow(i);
            int wins = row.get("wins");
            int id = row.get("id");
            LocalDate joinDate = result.getRow(i).get("join_date");
            assertTrue(wins >= 500);
            assertTrue(id >=0 && id <= 100_000);
            assertTrue(joinDate.getMonth() == Month.MARCH && joinDate.getYear() == 2019);
        }
    }
}
