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

package io.github.dug22.carpentry.chart.types.doughnut;

import io.github.dug22.carpentry.chart.AbstractChart;
import io.github.dug22.carpentry.chart.components.ColorComponent;
import io.github.dug22.carpentry.chart.components.FontStyle;

import java.util.ArrayList;
import java.util.List;

public class DoughnutChart extends AbstractChart<DoughnutChartProperties> {


    private final String title = properties.getTitle();
    private final String legendTitle = properties.getLegendTitle();
    private final boolean isLegendVisible = properties.isLegendVisible();
    private final String caption = properties.getCaption();
    private final List<DoughnutSegment> doughnutSegments = properties.getDoughnutSegments();
    private final FontStyle fontStyle = properties.getFontStyle();
    private final ColorComponent backgroundStyle = properties.getBackgroundColor();
    private final double doughnutThickness = properties.getDoughnutThickness();
    private final double doughnutHoleSize = properties.getHoleSize();
    private final boolean showPercentages = properties.arePercentagesVisible();
    private final boolean shadowEffect = properties.hasShadowEffect();
    private final double explode = properties.getExplode();
    private final double figureWidth = properties.getFigureWidth();
    private final double figureHeight = properties.getFigureHeight();
    private final double segmentTotal = doughnutSegments.stream().mapToDouble(DoughnutSegment::value).sum();
    private final double centerX = 21;
    private final double centerY = 21;
    private final double segmentRadius = (21 + doughnutHoleSize) / 2;
    private double currentAngle = 0;
    private final List<Double> midAngles = new ArrayList<>();
    private final List<Double> percentages = new ArrayList<>();
    private final List<Double> explodeXOffsets = new ArrayList<>();
    private final List<Double> explodeYOffsets = new ArrayList<>();

    public DoughnutChart(DoughnutChartProperties properties) {
        super(properties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getFigure() {
        double offset = 25;
        StringBuilder figureBuilder = new StringBuilder();
        figureBuilder.append("<figure>\n<div class=\"figure-content\" style=\"background-color: ").append(backgroundStyle).append(";\">\n")
                .append("<h1 align=center>").append(title).append("</h1>\n")
                .append("<svg width=\"").append(figureWidth).append("px\" height=\"").append(figureHeight).append("px\" viewBox=\"0 0 42 42\" class=\"doughnut\" ")
                .append("aria-labelledby=\"title desc\" role=\"img\">\n")
                .append("<title id=\"title\">").append(caption).append("</title>\n")
                .append("<desc id=\"desc\">Doughnut chart showing ").append((int) segmentTotal)
                .append(" total ").append(title.toLowerCase()).append(".</desc>\n");
        if (shadowEffect) {
            figureBuilder.append("<filter id=\"shadow\"><feDropShadow dx=\"0.2\" dy=\"0.2\" stdDeviation=\"0.5\" flood-opacity=\"0.5\"/></filter>\n");
        }
        figureBuilder.append("<circle class=\"doughnut-hole\" cx=\"21\" cy=\"21\" r=\"").append(doughnutHoleSize).append("\" fill=\"").append(backgroundStyle).append("\" role=\"presentation\"/>\n");
        for (int index = 0; index < doughnutSegments.size(); index++) {
            DoughnutSegment segment = doughnutSegments.get(index);
            double percent = (segment.value() / segmentTotal) * 100;
            percentages.add(percent);
            double dasharray = Math.round(percent * 100.0) / 100.0;
            double remainder = 100 - dasharray;

            double segmentAngle = (dasharray / 100.0) * 360.0;
            double midAngle = currentAngle + segmentAngle / 2;
            double angleRad = Math.toRadians(midAngle - 90);
            double explodeX = explode * Math.cos(angleRad);
            double explodeY = explode * Math.sin(angleRad);
            double cx = centerX + explodeX;
            double cy = centerY + explodeY;

            figureBuilder.append(String.format(
                    "<circle class=\"doughnut-segment-" + index + "\" cx=\"%.2f\" cy=\"%.2f\" r=\"%.3f\" fill=\"transparent\" " +
                    "stroke=\"%s\" stroke-width=\"%.2f\" stroke-dasharray=\"%.2f %.2f\" stroke-dashoffset=\"%.2f\"%s>\n" +
                    "<title>%s</title><desc>%s segment spanning %.2f%% of the chart.</desc></circle>\n",
                    cx, cy, segmentRadius, segment.color().getHexValue(), doughnutThickness,
                    dasharray, remainder, offset, shadowEffect ? " filter=\"url(#shadow)\"" : "",
                    segment.label() + " " + Math.round(percent) + "%", segment.label(), dasharray
            ));

            midAngles.add(midAngle);
            explodeXOffsets.add(explodeX);
            explodeYOffsets.add(explodeY);

            offset -= dasharray;
            currentAngle += segmentAngle;
        }

        if (showPercentages) {
            for (int i = 0; i < midAngles.size(); i++) {
                double angleDeg = midAngles.get(i);
                double angleRad = Math.toRadians(angleDeg - 90);

                double x = centerX + explodeXOffsets.get(i) + segmentRadius * Math.cos(angleRad);
                double y = centerY + explodeYOffsets.get(i) + segmentRadius * Math.sin(angleRad);

                String percentText = String.format("%.0f%%", percentages.get(i));

                figureBuilder.append(String.format(
                        "<text x=\"%.2f\" y=\"%.2f\" fill=\"#000\" font-size=\"1.5\" text-anchor=\"middle\" dominant-baseline=\"middle\">%s</text>\n",
                        x + 1.75, y, percentText
                ));
            }
        }

        if (isLegendVisible) {
            figureBuilder.append(getKeyLegend());
        } else {
            figureBuilder.append("<figure>");
        }
        return figureBuilder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getKeyLegend() {
        StringBuilder legendBuilder = new StringBuilder();
        legendBuilder
                .append("<g class=\"chart-text\">\n")
                .append("<text x=\"50%\" y=\"50%\" class=\"chart-number\">")
                .append((int) segmentTotal).append("</text>\n")
                .append("</g>\n</svg>\n</div>\n<figcaption class=\"figure-key\">\n")
                .append("<h3>").append(legendTitle).append("</h3>\n")
                .append("<ul class=\"figure-key-list\">\n");
        for (int index = 0; index < doughnutSegments.size(); index++) {
            DoughnutSegment segment = doughnutSegments.get(index);
            legendBuilder
                    .append("<li data-index=")
                    .append(index)
                    .append("><span class=\"shape-circle\" style=\"background-color: ")
                    .append(segment.color().getHexValue())
                    .append(";\"></span> ").append(segment.label())
                    .append(" (").append(segment.value()).append(")</li>\n");
        }
        legendBuilder.append("</ul>\n</figcaption>\n</figure>");
        return legendBuilder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getStyles() {
        return "html, body {\n" +
               "  height: 100%;\n" +
               "  margin: 0;" +
               "  font: " + fontStyle.fontSize() + "px/1.4em " + fontStyle.fontFamily() + ";\n" +
               "  color: " + fontStyle.color().getHexValue() + ";\n" +
               "  display: flex;\n " +
               " align-items: center;\n" +
               " justify-content: center;\n" +
               "}\n" +
               "* {\n  box-sizing: border-box;\n}\n" +
               ".chart-text {\n  fill: #000;\n  transform: translateY(0.25em);\n}\n" +
               ".chart-number {\n  font-size: 0.6em;\n  text-anchor: middle;\n  transform: translateY(-0.25em);\n}\n" +
               "figure {\n  display: flex;\n  justify-content: space-around;\n  flex-direction: column;\n  margin: 0 -15px;\n}\n" +
               "@media (min-width: 768px) {\n  figure {\n    flex-direction: row;\n  }\n}\n" +
               ".figure-content,\n.figure-key {\n  flex: 1;\n  padding: 0 15px;\n  align-self: center;\n}\n" +
               ".figure-content svg {\n  height: auto;\n}\n" +
               ".figure-key {\n  min-width: 66%;\n}\n" +
               ".figure-key [class*=\"shape-\"] {\n  margin-right: 6px;\n}\n" +
               ".figure-key-list {\n  margin: 0;\n  padding: 0;\n  list-style: none;\n}\n" +
               ".figure-key-list li {\n  margin: 0 0 8px;\n}\n" +
               ".shape-circle {\n  display: inline-block;\n  vertical-align: middle;\n  width: 32px;\n  height: 32px;\n  border-radius: 50%;\n}\n" +
               ".sr-only {\n  position: absolute;\n  width: 1px;\n  height: 1px;\n  margin: -1px;\n  padding: 0;\n  overflow: hidden;\n  clip: rect(0,0,0,0);\n  border: 0;\n}\n";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTemplateFile() {
        return "/doughnut_chart_template.html";
    }
}