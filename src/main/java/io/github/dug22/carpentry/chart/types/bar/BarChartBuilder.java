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

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.chart.components.ColorComponent;
import io.github.dug22.carpentry.chart.components.FontStyle;
import io.github.dug22.carpentry.chart.AbstractChartBuilder;
import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.column.impl.DoubleColumn;
import io.github.dug22.carpentry.column.impl.IntegerColumn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BarChartBuilder extends AbstractChartBuilder<BarChartBuilder, BarChartProperties> {

    private final DataFrame dataFrame;
    private String xColumnName;
    private List<String> yColumnNames;
    private List<ColorComponent> colors;
    private FontStyle fontStyle;
    private ColorComponent backgroundColor;
    private String title;
    private String xAxisLabel;
    private String yAxisLabel;
    private String legendTitle;
    private Boolean isLegendVisible;
    private String caption;
    private Double width;
    private Double height;
    private Boolean gridLines;
    private String outputFile;
    private Integer marginTop;
    private Integer marginRight;
    private Integer marginBottom;
    private Integer marginLeft;
    private Integer maxXLabelAmount;
    private Integer maxXLabelCharLength;
    private final List<BarGroup> barGroups = new ArrayList<>();

    public BarChartBuilder(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    public static BarChartBuilder create(DataFrame dataFrame) {
        return new BarChartBuilder(dataFrame);
    }

    public BarChartBuilder setXData(String xColumnName) {
        this.xColumnName = xColumnName;
        return this;
    }

    public BarChartBuilder setYData(List<String> yColumnNames) {
        this.yColumnNames = yColumnNames != null ? new ArrayList<>(yColumnNames) : null;
        return this;
    }

    public BarChartBuilder setColors(List<ColorComponent> colors) {
        this.colors = colors != null ? new ArrayList<>(colors) : null;
        return this;
    }

    public BarChartBuilder setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
        return this;
    }

    public BarChartBuilder setBackgroundColor(ColorComponent backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public BarChartBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BarChartBuilder setXAxisLabel(String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
        return this;
    }

    public BarChartBuilder setYAxisLabel(String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
        return this;
    }

    public BarChartBuilder setLegendTitle(String legendTitle) {
        this.legendTitle = legendTitle;
        return this;
    }

    public BarChartBuilder setLegendVisible(Boolean isLegendVisible) {
        this.isLegendVisible = isLegendVisible;
        return this;
    }

    public BarChartBuilder setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public BarChartBuilder setFigureSize(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be greater than 0");
        }
        this.width = width;
        this.height = height;
        return this;
    }

    public BarChartBuilder setGridLines(boolean gridLines) {
        this.gridLines = gridLines;
        return this;
    }

    public BarChartBuilder setOutputFile(String outputFile) {
        this.outputFile = outputFile;
        return this;
    }

    public BarChartBuilder setMargins(int marginTop, int marginRight, int marginBottom, int marginLeft) {
        if (marginTop < 0 || marginRight < 0 || marginBottom < 0 || marginLeft < 0) {
            throw new IllegalArgumentException("Margins cannot be negative");
        }
        this.marginTop = marginTop;
        this.marginRight = marginRight;
        this.marginBottom = marginBottom;
        this.marginLeft = marginLeft;
        return this;
    }

    public BarChartBuilder setMaxXLabelAmount(int maxXLabelAmount) {
        this.maxXLabelAmount = maxXLabelAmount;
        return this;
    }

    public BarChartBuilder setMaxXLabelCharLength(int maxXLabelCharLength) {
        this.maxXLabelCharLength = maxXLabelCharLength;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BarChartBuilder build() {
        Objects.requireNonNull(dataFrame, "DataFrame must not be null");
        Objects.requireNonNull(xColumnName, "X column name must not be null");
        Objects.requireNonNull(yColumnNames, "Y column names must not be null");
        if (yColumnNames.isEmpty()) {
            throw new IllegalArgumentException("At least one Y column name must be provided");
        }
        if (!dataFrame.containsColumn(xColumnName)) {
            throw new IllegalArgumentException("X-axis column '" + xColumnName + "' not found in DataFrame");
        }
        for (String yCol : yColumnNames) {
            if (!dataFrame.containsColumn(yCol)) {
                throw new IllegalArgumentException("Y-axis column '" + yCol + "' not found in DataFrame");
            }
        }

        Column<?> xColumn = dataFrame.getColumn(xColumnName);
        List<String> xValues = Arrays.stream(xColumn.getValues())
                .map(object -> object != null ? object.toString() : "null")
                .distinct()
                .toList();

        for (String x : xValues) {
            List<Bar> bars = new ArrayList<>();
            for (String yColName : yColumnNames) {
                Column<?> yColumn = dataFrame.getColumn(yColName);
                int index = Arrays.asList(xColumn.getValues()).indexOf(x);
                double yValue = index >= 0 && index < yColumn.getValues().length ?
                        (yColumn instanceof DoubleColumn ? (Double) yColumn.getValues()[index] :
                                yColumn instanceof IntegerColumn ? ((Integer) yColumn.getValues()[index]).doubleValue() : Double.NaN)
                        : Double.NaN;
                if (!Double.isNaN(yValue)) {
                    bars.add(new Bar(x, yValue, yColName));
                }
            }
            barGroups.add(new BarGroup(x, bars));
        }

        BarChartProperties properties = getProperties();
        BarChart chart = new BarChart(properties);
        String html = chart.build();
        chart.save(getOutputFile(), html);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BarChartProperties getProperties() {
        BarChartProperties properties = new BarChartProperties();
        if (title != null) properties.setTitle(title);
        if (xAxisLabel != null) properties.setXAxisLabel(xAxisLabel);
        if (yAxisLabel != null) properties.setYAxisLabel(yAxisLabel);
        if (legendTitle != null) properties.setLegendTitle(legendTitle);
        if (isLegendVisible != null) properties.setLegendVisible(isLegendVisible);
        if (caption != null) properties.setCaption(caption);
        if (fontStyle != null) properties.setFontStyle(fontStyle);
        if (backgroundColor != null) properties.setBackgroundColor(backgroundColor);
        if (width != null && height != null) properties.setFigureSize(width, height);
        if (gridLines != null) properties.setGridLines(gridLines);
        if (colors != null) properties.setColorComponents(colors);
        if (marginTop != null && marginRight != null && marginBottom != null && marginLeft != null) {
            properties.setMargins(marginTop, marginRight, marginBottom, marginLeft);
        }
        if (maxXLabelAmount != null) properties.setMaxXLabelAmount(maxXLabelAmount);
        if (maxXLabelCharLength != null) properties.setMaxXLabelCharLength(maxXLabelCharLength);
        properties.setBarGroups(barGroups);
        return properties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOutputFile() {
        return outputFile != null ? outputFile : "bar_chart.html";
    }
}
