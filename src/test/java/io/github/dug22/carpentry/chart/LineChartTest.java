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
import io.github.dug22.carpentry.chart.components.FontStyle;
import io.github.dug22.carpentry.chart.types.line.LineChartBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LineChartTest {

    private final DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/aapl_stock_data.csv");

    @Test
    public void lineChartTest(){
        LineChartBuilder lineChart = LineChartBuilder.create(dataFrame)
                .setTitle("AAPL Open vs Closed")
                .setXData("Date")
                .setXAxisLabel("Date")
                .setYData(List.of("Open", "Close"))
                .setYAxisLabel("APPL Open & Closed Prices")
                .setBackgroundColor(ColorComponent.WHITE)
                .setColors(List.of(ColorComponent.GREEN, ColorComponent.RED))
                .setLegendTitle("AAPL Stock Prices Legend")
                .setLegendVisible(true)
                .setGridLines(true)
                .setFontStyle(new FontStyle("Arial", 14, ColorComponent.BLACK))
                .setFigureSize(750D, 600D)
                .setOutputFile("line_chart_test.html")
                .build();
        assertTrue(new File(lineChart.getOutputFile()).exists());
        //lineChart.display();
    }
}
