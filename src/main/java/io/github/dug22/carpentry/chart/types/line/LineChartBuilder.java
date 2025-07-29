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

public class LineChartBuilder extends AbstractChartBuilder<LineChartBuilder, LineChartProperties> {

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

    public LineChartBuilder(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    public static LineChartBuilder create(DataFrame dataFrame) {
        return new LineChartBuilder(dataFrame);
    }

    public LineChartBuilder setXData(String xColumnName) {
        this.xColumnName = xColumnName;
        return this;
    }

    public LineChartBuilder setYData(List<String> yColumnNames) {
        this.yColumnNames = yColumnNames != null ? new ArrayList<>(yColumnNames) : null;
        return this;
    }

    public LineChartBuilder setColors(List<ColorComponent> colors) {
        this.colors = colors != null ? new ArrayList<>(colors) : null;
        return this;
    }

    public LineChartBuilder setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
        return this;
    }

    public LineChartBuilder setBackgroundColor(ColorComponent backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public LineChartBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public LineChartBuilder setXAxisLabel(String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
        return this;
    }

    public LineChartBuilder setYAxisLabel(String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
        return this;
    }

    public LineChartBuilder setLegendTitle(String legendTitle) {
        this.legendTitle = legendTitle;
        return this;
    }

    public LineChartBuilder setLegendVisible(Boolean isLegendVisible){
        this.isLegendVisible = isLegendVisible;
        return this;
    }

    public LineChartBuilder setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public LineChartBuilder setFigureSize(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be greater than 0");
        }
        this.width = width;
        this.height = height;
        return this;
    }

    public LineChartBuilder setGridLines(boolean gridLines) {
        this.gridLines = gridLines;
        return this;
    }

    public LineChartBuilder setOutputFile(String outputFile) {
        this.outputFile = outputFile;
        return this;
    }

    public LineChartBuilder setMargins(int marginTop, int marginRight, int marginBottom, int marginLeft) {
        if (marginTop < 0 || marginRight < 0 || marginBottom < 0 || marginLeft < 0) {
            throw new IllegalArgumentException("Margins cannot be negative");
        }
        this.marginTop = marginTop;
        this.marginRight = marginRight;
        this.marginBottom = marginBottom;
        this.marginLeft = marginLeft;
        return this;
    }

    public LineChartBuilder setMaxXLabelAmount(int maxXLabelAmount){
        this.maxXLabelAmount = maxXLabelAmount;
        return this;
    }

    public LineChartBuilder setMaxXLabelCharLength(int maxXLabelCharLength){
        this.maxXLabelCharLength = maxXLabelCharLength;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LineChartBuilder build() {
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
                .map(obj -> obj != null ? obj.toString() : "null")
                .toList();
        List<LineSeries> seriesList = new ArrayList<>();
        for (String yColName : yColumnNames) {
            Column<?> yColumn = dataFrame.getColumn(yColName);
            List<DataPoint> points = getDataPoints(yColName, yColumn, xValues);
            seriesList.add(new LineSeries(yColName, points));
        }

        LineChartProperties properties = getProperties();
        properties.setLineSeries(seriesList);
        LineChart chart = new LineChart(properties);
        String html = chart.build();
        chart.save(getOutputFile(), html);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected LineChartProperties getProperties() {
        LineChartProperties properties = new LineChartProperties();
        if (title != null) properties.setTitle(title);
        if (xAxisLabel != null) properties.setXAxisLabel(xAxisLabel);
        if (yAxisLabel != null) properties.setYAxisLabel(yAxisLabel);
        if (legendTitle != null) properties.setLegendTitle(legendTitle);
        if(isLegendVisible != null) properties.setLegendVisible(isLegendVisible);
        if (caption != null) properties.setCaption(caption);
        if (fontStyle != null) properties.setFontStyle(fontStyle);
        if (backgroundColor != null) properties.setBackgroundColor(backgroundColor);
        if (width != null && height != null) properties.setFigureSize(width, height);
        if (gridLines != null) properties.setGridLines(gridLines);
        if (colors != null) properties.setColorComponents(colors);
        if (marginTop != null && marginRight != null && marginBottom != null && marginLeft != null) {
            properties.setMargins(marginTop, marginRight, marginBottom, marginLeft);
        }
        if(maxXLabelAmount != null) properties.setMaxXLabelAmount(maxXLabelAmount);
        if(maxXLabelCharLength != null) properties.setMaxXLabelCharLength(maxXLabelCharLength);
        return properties;
    }


    /**
     * Converts values from a numeric y-axis column into a list of {@link DataPoint} objects,
     * pairing each y-value with its corresponding x-axis label.
     *
     * @param yColName the name of the y-axis column (used for error reporting).
     * @param yColumn the y-axis column containing numeric data (must be {@code DoubleColumn} or {@code IntegerColumn}).
     * @param xValues the list of x-axis values to pair with the y-values.
     * @return a list of {@code DataPoint} objects representing (x, y) pairs.
     * @throws IllegalArgumentException if the y-column contains non-numeric data types.
     */

    private static List<DataPoint> getDataPoints(String yColName, Column<?> yColumn, List<String> xValues) {
        List<Double> yValues = new ArrayList<>();
        for (Object value : yColumn.getValues()) {
            if (value == null) {
                yValues.add(Double.NaN);
            } else if (yColumn instanceof DoubleColumn) {
                yValues.add((Double) value);
            } else if (yColumn instanceof IntegerColumn) {
                yValues.add(((Integer) value).doubleValue());
            } else {
                throw new IllegalArgumentException("Y-column '" + yColName + "' must be numeric (Double or Integer)");
            }
        }

        List<DataPoint> points = new ArrayList<>();
        for (int i = 0; i < xValues.size(); i++) {
            points.add(new DataPoint(xValues.get(i), yValues.get(i)));
        }
        return points;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOutputFile() {
        return outputFile != null ? outputFile : "line_chart.html";
    }
}