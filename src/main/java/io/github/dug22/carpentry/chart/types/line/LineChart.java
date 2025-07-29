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

package io.github.dug22.carpentry.chart.types.line;

import io.github.dug22.carpentry.chart.AbstractChart;
import io.github.dug22.carpentry.chart.components.ColorComponent;
import io.github.dug22.carpentry.chart.components.FontStyle;
import io.github.dug22.carpentry.chart.components.GridLines;
import io.github.dug22.carpentry.chart.components.LabelFormatting;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LineChart extends AbstractChart<LineChartProperties> {

    private final String title = properties.getTitle();
    private final String xAxisLabel = properties.getXAxisLabel();
    private final String yAxisLabel = properties.getYAxisLabel();
    private final String legendTitle = properties.getLegendTitle();
    private final boolean isLegendVisible = properties.isLegendVisible();
    private final String caption = properties.getCaption();
    private final List<LineSeries> lineSeries = properties.getLineSeries();
    private final FontStyle fontStyle = properties.getFontStyle();
    private final ColorComponent backgroundColor = properties.getBackgroundColor();
    private final double figureWidth = properties.getFigureWidth();
    private final double figureHeight = properties.getFigureHeight();
    private final int marginTop = properties.getMarginTop();
    private final int marginRight = properties.getMarginRight();
    private final int marginBottom = properties.getMarginBottom();
    private final int marginLeft = properties.getMarginLeft();
    private final boolean gridLines = properties.isGridLines();
    private final List<ColorComponent> colors = properties.getColorComponents();
    private final int maxXLabelAmount = properties.getMaxXLabelAmount();
    private final int maxXLabelCharLength = properties.getMaxXLabelCharLength();
    private final List<String> xLabels = lineSeries
            .stream()
            .flatMap(series -> series.data().stream())
            .map(DataPoint::x)
            .distinct()
            .sorted()
            .toList();
    private final double chartWidth = figureWidth - marginLeft - marginRight;
    private final double chartHeight = figureHeight - marginTop - marginBottom;
    private final List<Double> allYValues = lineSeries
            .stream()
            .flatMap(series -> series.data().stream())
            .map(DataPoint::y)
            .filter(y -> !Nulls.isNull(y))
            .toList();
    private final double yMin = allYValues.stream().min(Double::compare).orElse(1.0);
    private final double yMax = allYValues.stream().max(Double::compare).orElse(100.00);
    private final double yRange = yMax - yMin;
    private final double[] yDomain = {yMin - yRange * 0.1, yMax + yRange * 0.1};

    public LineChart(LineChartProperties properties) {
        super(properties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getFigure() {
        StringBuilder figureBuilder = new StringBuilder();
        figureBuilder
                .append("<figure>\n")
                .append("<div class=\"figure-content\" style=\"background-color: ")
                .append(backgroundColor.getHexValue()).append(";\"></div>\n")
                .append("<svg width=\"").append(figureWidth).append("px\" height=\"").append(figureHeight)
                .append("px\" viewBox=\"0 0 ").append(figureWidth).append(" ").append(figureHeight)
                .append("\" class=\"line-chart\" aria-labelledby=\"title desc\" role=\"img\">\n")
                .append("<title id=\"title\">").append(caption).append("</title>\n")
                .append("<desc id=\"desc\">Line chart showing ").append(lineSeries.size()).append(" series.</desc>\n")
                .append(String.format("<g transform=\"translate(%d, %d)\">\n", marginLeft, marginTop))
                .append(String.format("<text x=\"%.2f\" y=\"-20\" text-anchor=\"middle\" font-weight=\"bold\" class=\"chart-title\">%s</text>\n",
                        chartWidth / 2, title));
        if (gridLines) {
            GridLines.generateHorizontalGridLines(figureBuilder, yDomain, chartWidth, chartHeight);
            GridLines.generateVerticalGridLines(figureBuilder, chartWidth, chartHeight);
        }
        figureBuilder.append(String.format("<g transform=\"translate(0, %.2f)\">\n", chartHeight));

        int labelCount = Math.min(maxXLabelAmount, xLabels.size());
        int step = (int) Math.ceil(xLabels.size() / (double) labelCount * 2);

        for (int i = 0; i < xLabels.size(); i += step) {
            double x = (i / (double) (xLabels.size() - 1)) * chartWidth;

            String label = xLabels.get(i);
            int maxChars = maxXLabelCharLength;
            if (label.length() > maxChars) {
                label = label.substring(0, maxChars) + "…";
            }

            figureBuilder.append(String.format(
                    "<text x=\"%.2f\" y=\"20\" text-anchor=\"end\" transform=\"rotate(-90 %.2f 0)\">%s</text>\n",
                    x, x, label));

        }

        figureBuilder.append(String.format(
                "<text x=\"%.2f\" y=\"%.2f\" text-anchor=\"middle\" class=\"axis-label\">%s</text>\n",
                chartWidth / 2, chartHeight / 3, xAxisLabel));

        figureBuilder.append(String.format(
                "<line x1=\"0\" x2=\"%.2f\" y1=\"0\" y2=\"0\" stroke=\"black\"/>\n", chartWidth));
        figureBuilder.append("</g>\n");

        figureBuilder.append("<g>\n");
        IntStream.range(0, 6).forEach(i -> {
            double value = yDomain[0] + (i / 5.0) * (yDomain[1] - yDomain[0]);
            double y = chartHeight - ((value - yDomain[0]) / (yDomain[1] - yDomain[0])) * chartHeight;
            figureBuilder.append(String.format(
                    "<text x=\"-10\" y=\"%.2f\" text-anchor=\"end\" dy=\"0.32em\">%s</text>\n",
                    y, LabelFormatting.format(value)));
        });

        figureBuilder.append(String.format(
                "<text x=\"-40\" y=\"%.2f\" text-anchor=\"middle\" transform=\"rotate(-90 -40 %.2f)\" class=\"axis-label\">%s</text>\n",
                chartHeight / 2, chartHeight / 2, yAxisLabel));

        figureBuilder.append(String.format(
                "<line x1=\"0\" x2=\"0\" y1=\"0\" y2=\"%.2f\" stroke=\"black\"/>\n", chartHeight));
        figureBuilder.append("</g>\n");

        IntStream.range(0, lineSeries.size()).forEach(i -> {
            LineSeries series = lineSeries.get(i);
            String strokeColor = i < colors.size() ? colors.get(i).getHexValue() : ColorComponent.getCustomColor(i).getHexValue();
            Map<String, Double> pointMap = series.data().stream()
                    .collect(Collectors.toMap(DataPoint::x, DataPoint::y, (e, f) -> f));
            String pathData = xLabels.stream()
                    .filter(value -> !Nulls.isNull(value))
                    .map(x -> {
                        Double yVal = pointMap.get(x);
                        double xPos = (xLabels.indexOf(x) / (double) (xLabels.size() - 1)) * chartWidth;
                        double yPos = chartHeight - ((yVal - yDomain[0]) / (yDomain[1] - yDomain[0])) * chartHeight;
                        return String.format("%.2f,%.2f", xPos, yPos);
                    })
                    .collect(Collectors.joining(" "));

            figureBuilder.append(String.format(
                    "<path d=\"M %s\" fill=\"none\" stroke=\"%s\" stroke-width=\"2\">\n",
                    pathData, strokeColor));

            figureBuilder.append(String.format(
                    "<title>%s</title><desc>Series %s with %d points.</desc></path>\n",
                    series.x(), series.x(), series.data().size()));
            xLabels.forEach(x -> {
                Double yVal = pointMap.get(x);
                if (!Nulls.isNull(yVal)) {
                    double xPos = (xLabels.indexOf(x) / (double) (xLabels.size() - 1)) * chartWidth;
                    double yPos = chartHeight - ((yVal - yDomain[0]) / (yDomain[1] - yDomain[0])) * chartHeight;

                    figureBuilder.append(String.format(
                            "<circle cx=\"%.2f\" cy=\"%.2f\" r=\"0.8\" fill=\"%s\" class=\"data-point\" data-series=\"%s\" data-x=\"%s\" data-y=\"%s\">\n",
                            xPos, yPos, strokeColor, series.x(), x, LabelFormatting.format(yVal)));
                    figureBuilder.append(String.format(
                            "<title>%s: (%s, %s)</title></circle>\n",
                            series.x(), x, LabelFormatting.format(yVal)));
                }
            });
        });

        if (isLegendVisible) {
            figureBuilder.append(getKeyLegend());
        } else {
            figureBuilder.append("</figure>");
        }

        return figureBuilder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getKeyLegend() {
        StringBuilder legendBuilder = new StringBuilder();
        legendBuilder.append("</g>\n</svg>\n")
                .append("<figcaption class=\"figure-key\">\n")
                .append("<h3>").append(legendTitle).append("</h3>\n")
                .append("<ul class=\"figure-key-list\">\n");

        IntStream.range(0, lineSeries.size()).forEach(i -> {
            LineSeries series = lineSeries.get(i);
            String color = i < colors.size() ? colors.get(i).getHexValue() : ColorComponent.getCustomColor(i).getHexValue();
            legendBuilder.append(String.format(
                    "<li data-index=\"%d\"><span class=\"shape-circle\" style=\"background-color: %s;\"></span> %s</li>\n",
                    i, color, series.x()));
        });

        legendBuilder.append("</ul>\n</figcaption>\n</figure>\n");

        return legendBuilder.toString();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected String getStyles() {

        return "html, body {\n" +
               "  height: 100%;\n" +
               "  margin: 0;\n" +
               String.format("  font: %dpx/1.4em %s;\n", fontStyle.fontSize(), fontStyle.fontFamily()) +
               String.format("  color: %s;\n", fontStyle.color().getHexValue()) +
               "  display: flex;\n" +
               "  align-items: center;\n" +
               "  justify-content: center;\n" +
               "}\n" +
               "* {\n" +
               "  box-sizing: border-box;\n" +
               "}\n" +
               ".line-chart {\n" +
               "  overflow: visible;\n" +
               "}\n" +
               ".axis-label {\n" +
               "  font-size: 0.8em;\n" +
               String.format("  fill: %s;\n", fontStyle.color().getHexValue()) +
               "}\n" +
               ".data-point:hover {\n" +
               "  r: 7;\n" +
               "  cursor: pointer;\n" +
               "}\n" +
               "figure {\n" +
               "  display: flex;\n" +
               "  justify-content: space-around;\n" +
               "  flex-direction: column;\n" +
               "  margin: 0 -15px;\n" +
               "}\n" +
               "@media (min-width: 768px) {\n" +
               "  figure {\n" +
               "    flex-direction: row;\n" +
               "  }\n" +
               "}\n" +
               ".figure-content,\n" +
               ".figure-key {\n" +
               "  flex: 1;\n" +
               "  padding: 0 15px;\n" +
               "  align-self: center;\n" +
               "}\n" +
               ".figure-content svg {\n" +
               "  height: auto;\n" +
               "}\n" +
               ".figure-key {\n" +
               "  min-width: 33%;\n" +
               "}\n" +
               ".figure-key [class*=\"shape-\"] {\n" +
               "  margin-right: 6px;\n" +
               "}\n" +
               ".figure-key-list {\n" +
               "  margin: 0;\n" +
               "  padding: 0;\n" +
               "  list-style: none;\n" +
               "}\n" +
               ".figure-key-list li {\n" +
               "  margin: 0 0 8px;\n" +
               "}\n" +
               ".shape-circle {\n" +
               "  display: inline-block;\n" +
               "  vertical-align: middle;\n" +
               "  width: 16px;\n" +
               "  height: 16px;\n" +
               "  border-radius: 50%;\n" +
               "}\n";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTemplateFile() {
        return "/line_chart_template.html";
    }
}