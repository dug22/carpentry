package io.github.dug22.carpentry.chart;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.chart.components.ColorComponent;
import io.github.dug22.carpentry.chart.types.pie.PieChartBuilder;
import io.github.dug22.carpentry.column.impl.DoubleColumn;
import io.github.dug22.carpentry.column.impl.StringColumn;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PieChartTest {

    private final DataFrame dataFrame = DataFrame.create(
            StringColumn.create("Fruit", new String[]{"Apples", "Bananas", "Oranges", "Cherries"}),
            DoubleColumn.create("Amount", new Double[]{30.0, 40.0, 50.0, 50.0})
    );

    @Test
    public void pieChartTest(){
        PieChartBuilder pieChart = PieChartBuilder.create(dataFrame)
                .setTitle("Fruit Breakdown")
                .setCategoryColumn("Fruit")
                .setValueColumns(List.of("Amount"))
                .setLegendTitle("Fruits")
                .setLegendVisible(true)
                .setColors(List.of(ColorComponent.RED, ColorComponent.YELLOW, ColorComponent.ORANGE, ColorComponent.MAROON))
                .setOutputFile("pie_chart_test.html")
                .build();
        assertTrue(new File(pieChart.getOutputFile()).exists());
        //pieChart.display();
    }
}
