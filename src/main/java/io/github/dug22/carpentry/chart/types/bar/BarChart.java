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

package io.github.dug22.carpentry.chart.types.bar;

import io.github.dug22.carpentry.chart.AbstractChart;
import io.github.dug22.carpentry.chart.components.ColorComponent;
import io.github.dug22.carpentry.chart.components.FontStyle;
import io.github.dug22.carpentry.chart.components.GridLines;
import io.github.dug22.carpentry.chart.components.LabelFormatting;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class BarChart extends AbstractChart<BarChartProperties> {

    private final String title = properties.getTitle();
    private final String xAxisLabel = properties.getXAxisLabel();
    private final String yAxisLabel = properties.getYAxisLabel();
    private final String legendTitle = properties.getLegendTitle();
    private final boolean isLegendVisible = properties.isLegendVisible();
    private final String caption = properties.getCaption();
    private final List<BarGroup> barGroups = properties.getBarGroups();
    private final FontStyle fontStyle = properties.getFontStyle();
    private final ColorComponent backgroundColor = properties.getBackgroundColor();
    private final double figureWidth = properties.getFigureWidth();
    private final double figureHeight = properties.getFigureHeight();
    private final int marginTop = properties.getMarginTop();
    private final int marginRight = properties.getMarginRight();
    private final int marginBottom = properties.getMarginBottom();
    private final int marginLeft = properties.getMarginLeft();
    private final boolean gridLines = properties.isGridLines();
    private final Map<String, ColorComponent> seriesColorMap;
    private final int maxXLabelAmount = properties.getMaxXLabelAmount();
    private final int maxXLabelCharLength = properties.getMaxXLabelCharLength();
    private final double chartWidth = figureWidth - marginLeft - marginRight;
    private final double chartHeight = figureHeight - marginTop - marginBottom;
    private final List<String> xLabels = barGroups.stream()
            .flatMap(group -> group.bars().stream())
            .map(Bar::x)
            .distinct()
            .toList();
    private final List<Double> allYValues = barGroups.stream()
            .flatMap(group -> group.bars().stream())
            .map(Bar::y)
            .filter(y -> !Nulls.isNull(y))
            .toList();
    private final double yMin = allYValues.stream().min(Double::compare).orElse(0.0);
    private final double yMax = allYValues.stream().max(Double::compare).orElse(100.0);
    private final double yRange = yMax - yMin;
    private final double[] yDomain = {Math.min(0, yMin - yRange * 0.1), yMax + yRange * 0.1};

    public BarChart(BarChartProperties properties) {
        super(properties);
        this.seriesColorMap = new LinkedHashMap<>();
        List<String> seriesNames = barGroups.stream()
                .flatMap(barGroup -> barGroup.bars().stream().map(Bar::seriesName))
                .distinct()
                .toList();
        for (int index = 0; index < seriesNames.size(); index++) {
            List<ColorComponent> colorComponents = properties.getColorComponents();
            seriesColorMap.put(seriesNames.get(index), colorComponents.get(index % colorComponents.size()));
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected String getFigure() {
        StringBuilder figureBuilder = new StringBuilder();
        figureBuilder.append("<figure>\n")
                .append("<div class=\"figure-content\" style=\"background-color: ")
                .append(backgroundColor.getHexValue()).append(";\"></div>\n")
                .append("<svg width=\"").append(figureWidth).append("px\" height=\"").append(figureHeight)
                .append("px\" viewBox=\"0 0 ").append(figureWidth).append(" ").append(figureHeight)
                .append("\" class=\"bar-chart\" aria-labelledby=\"title desc\" role=\"img\">\n")
                .append("<title id=\"title\">").append(caption).append("</title>\n")
                .append("<desc id=\"desc\">Bar chart showing ").append(barGroups.size()).append(" groups.</desc>\n")
                .append(String.format("<g transform=\"translate(%d, %d)\">\n", marginLeft, marginTop))
                .append(String.format("<text x=\"%.2f\" y=\"-20\" text-anchor=\"middle\" font-weight=\"bold\" class=\"chart-title\">%s</text>\n",
                        chartWidth / 2, title));

        if (gridLines) {
            GridLines.generateHorizontalGridLines(figureBuilder, yDomain, chartWidth, chartHeight);
            GridLines.generateVerticalGridLines(figureBuilder, chartWidth, chartHeight);
        }

        figureBuilder.append(String.format("<g transform=\"translate(0, %.2f)\">\n", chartHeight));
        double barGroupWidth = chartWidth / xLabels.size();
        int labelCount = Math.min(maxXLabelAmount, xLabels.size());
        int step = (int) Math.ceil((double) xLabels.size() / labelCount);
        for (int i = 0; i < xLabels.size(); i += step) {
            double x = (i / (double) xLabels.size()) * chartWidth + barGroupWidth / 2;
            String label = xLabels.get(i);
            int maxChars = maxXLabelCharLength;
            if (label.length() > maxChars) {
                label = label.substring(0, maxChars) + "…";
            }
            figureBuilder.append(String.format(
                    "<text x=\"%.2f\" y=\"20\" text-anchor=\"middle\">%s</text>\n",
                    x, label));
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
                chartHeight / 3, chartHeight / 2, yAxisLabel));
        figureBuilder.append(String.format(
                "<line x1=\"0\" x2=\"0\" y1=\"0\" y2=\"%.2f\" stroke=\"black\"/>\n", chartHeight));
        figureBuilder.append("</g>\n");

        double barWidth = barGroupWidth / (barGroups.stream().map(g -> g.bars().size()).max(Integer::compare).orElse(1) + 1);
        IntStream.range(0, xLabels.size()).forEach(i -> {
            String xLabel = xLabels.get(i);
            double groupX = (i / (double) xLabels.size()) * chartWidth;
            List<Bar> bars = barGroups.stream()
                    .flatMap(group -> group.bars().stream().filter(bar -> bar.x().equals(xLabel)))
                    .toList();
            IntStream.range(0, bars.size()).forEach(j -> {
                Bar bar = bars.get(j);
                double yValue = bar.y();
                if (!Nulls.isNull(yValue)) {
                    double barX = groupX + (j + 0.5) * barWidth;
                    double barHeight = ((yValue - yDomain[0]) / (yDomain[1] - yDomain[0])) * chartHeight;
                    double barY = chartHeight - barHeight;
                    String color = seriesColorMap.getOrDefault(bar.seriesName(), ColorComponent.BLACK).getHexValue();

                    figureBuilder.append(String.format(
                            "<rect x=\"%.2f\" y=\"%.2f\" width=\"%.2f\" height=\"%.2f\" fill=\"%s\" class=\"bar\" data-series=\"%s\" data-x=\"%s\" data-y=\"%s\">\n",
                            barX, barY, barWidth * 0.8, barHeight, color, bar.seriesName(), bar.x(), LabelFormatting.format(yValue)));
                    figureBuilder.append(String.format(
                            "<title>%s: (%s, %s)</title></rect>\n",
                            bar.seriesName(), bar.x(), LabelFormatting.format(yValue)));
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
        legendBuilder.append("</g>\n</svg>\n</div>\n")
                .append("<figcaption class=\"figure-key\">\n")
                .append("<h3>").append(legendTitle).append("</h3>\n")
                .append("<ul class=\"figure-key-list\">\n");
        int index = 0;
        for (Map.Entry<String, ColorComponent> entry : seriesColorMap.entrySet()) {
            String seriesName = entry.getKey();
            String color = entry.getValue().getHexValue();
            legendBuilder.append(String.format(
                    "<li data-index=\"%d\"><span class=\"shape-rect\" style=\"background-color: %s;\"></span> %s</li>\n",
                    index++, color, seriesName));
        }

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
               "  font: " + fontStyle.fontSize() + "px/1.4em " + fontStyle.fontFamily() + ";\n" +
               "  color: " + fontStyle.color().getHexValue() + ";\n" +
               "  display: flex;\n" +
               "  align-items: center;\n" +
               "  justify-content: center;\n" +
               "}\n" +
               "* {\n" +
               "  box-sizing: border-box;\n" +
               "}\n" +
               ".bar-chart {\n" +
               "  overflow: visible;\n" +
               "}\n" +
               ".axis-label {\n" +
               "  font-size: 0.8em;\n" +
               "  fill: " + fontStyle.color().getHexValue() + ";\n" +
               "}\n" +
               ".bar:hover {\n" +
               "  opacity: 0.8;\n" +
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
               ".shape-rect {\n" +
               "  display: inline-block;\n" +
               "  vertical-align: middle;\n" +
               "  width: 16px;\n" +
               "  height: 16px;\n" +
               "}";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTemplateFile() {
        return "/bar_chart_template.html";
    }
}