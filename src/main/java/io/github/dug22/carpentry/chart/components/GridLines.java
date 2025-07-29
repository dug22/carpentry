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

package io.github.dug22.carpentry.chart.components;

import java.util.stream.IntStream;

/**
 * GridLines generate lines that extend from the axes across the plot area. This is only used for bar charts and line charts
 */
public class GridLines {

    private static final int GRID_LINE_COUNT = 6;
    private static final double GRID_DIVISION = 5;

    /**
     * Generate horizontal grid lines within the chart.
     * @param figureBuilder is a StringBuilder used to build out the figure of the chart.
     * @param yDomain the y-axis data range.
     * @param chartWidth the width of the chart.
     * @param chartHeight the height of the chart.
     */
    public static void generateHorizontalGridLines(StringBuilder figureBuilder, double[] yDomain, double chartWidth, double chartHeight) {
        IntStream.range(0, GRID_LINE_COUNT)
                .mapToDouble(i -> yDomain[0] + (i / GRID_DIVISION) * (yDomain[1] - yDomain[0]))
                .map(value -> chartHeight - ((value - yDomain[0]) / (yDomain[1] - yDomain[0])) * chartHeight)
                .forEach(y -> figureBuilder.append(String.format(
                        "<line x1=\"0\" x2=\"%.2f\" y1=\"%.2f\" y2=\"%.2f\" stroke=\"#e0e0e0\" stroke-dasharray=\"2,2\"/>\n",
                        chartWidth, y, y)));
    }

    /**
     * Generate vertical grid lines within the chart.
     * @param figureBuilder is a StringBuilder used to build out the figure of the chart.
     * @param chartWidth the width of the chart.
     * @param chartHeight the height of the chart.
     */
    public static void generateVerticalGridLines(StringBuilder figureBuilder, double chartWidth, double chartHeight) {
        IntStream.range(0, GRID_LINE_COUNT)
                .mapToDouble(i -> (i / GRID_DIVISION) * chartWidth)
                .forEach(x -> figureBuilder.append(String.format(
                                "<line x1=\"%.2f\" x2=\"%.2f\" y1=\"0\" y2=\"%.2f\" stroke=\"#e0e0e0\" stroke-dasharray=\"2,2\"/>\n",
                                x, x, chartHeight)));
    }
}
