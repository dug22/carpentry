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
import io.github.dug22.carpentry.chart.types.bar.BarChartBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BarChartTest {

    private final DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/planets.csv");

    @Test
    public void barChartTest() {
        BarChartBuilder barChart = BarChartBuilder.create(dataFrame)
                .setTitle("Planet Mass (kg) and Volume (km³) Comparison")
                .setXData("Planet")
                .setYData(List.of("Mass (kg)", "Volume (km³)"))
                .setColors(List.of(ColorComponent.BLUE, ColorComponent.TURQUOISE))
                .setBackgroundColor(ColorComponent.WHITE)
                .setXAxisLabel("Planets")
                .setYAxisLabel("Planet Mass (kg) and Volume(km³)")
                .setLegendTitle("Mass (kg) and Volume(km³)")
                .setLegendVisible(true)
                .setFigureSize(750D, 600D)
                .setGridLines(true)
                .setOutputFile("bar_chart_test.html")
                .build();
        assertTrue(new File(barChart.getOutputFile()).exists());
        //barChart.display();
    }
}
