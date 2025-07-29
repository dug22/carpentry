/*
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *
 */

package io.github.dug22.carpentry.chart;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.chart.components.ColorComponent;
import io.github.dug22.carpentry.chart.types.doughnut.DoughnutChartBuilder;
import io.github.dug22.carpentry.column.impl.DoubleColumn;
import io.github.dug22.carpentry.column.impl.StringColumn;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoughnutChartTest {

    private final DataFrame dataFrame = DataFrame.create(
            StringColumn.create("Fruit", new String[]{"Apples", "Bananas", "Oranges", "Cherries"}),
            DoubleColumn.create("Amount", new Double[]{30.0, 40.0, 50.0, 50.0})
    );

    @Test
    public void doughnutChartTest() {
        DoughnutChartBuilder doughnutChart = DoughnutChartBuilder.create(dataFrame)
                .setTitle("Fruit Breakdown")
                .setCategoryColumn("Fruit")
                .setValueColumns(List.of("Amount"))
                .setLegendTitle("Fruits")
                .setLegendVisible(true)
                .setDoughnutThickness(4)
                .setColors(List.of(ColorComponent.RED, ColorComponent.YELLOW, ColorComponent.ORANGE, ColorComponent.MAROON))
                .setOutputFile("doughnut_chart_test.html")
                .build();
        assertTrue(new File(doughnutChart.getOutputFile()).exists());
        //doughnutChart.display();
    }
}
