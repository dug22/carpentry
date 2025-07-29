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

public class DoughnutChartBuilder extends AbstractChartBuilder<DoughnutChartBuilder, DoughnutChartProperties> {

    private final DataFrame dataFrame;
    private String categoryColumn;
    private List<String> valueColumns;
    private List<ColorComponent> colors;
    private String title;
    private String legendTitle;
    private Boolean isLegendVisible;
    private String caption;
    private FontStyle fontStyle;
    private ColorComponent backgroundStyle;
    private Double doughnutThickness;
    private Double doughnutHoleSize;
    private Boolean showPercentages;
    private Boolean shadowEffect;
    private Double explode;
    private Double figureWidth;
    private Double figureHeight;
    private String outputFile;

    public DoughnutChartBuilder(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    public static DoughnutChartBuilder create(DataFrame dataFrame) {
        return new DoughnutChartBuilder(dataFrame);
    }

    public DoughnutChartBuilder setCategoryColumn(String categoryColumn) {
        this.categoryColumn = categoryColumn;
        return this;
    }

    public DoughnutChartBuilder setValueColumns(List<String> valueColumns) {
        this.valueColumns = valueColumns != null ? new ArrayList<>(valueColumns) : null;
        return this;
    }

    public DoughnutChartBuilder setColors(List<ColorComponent> colors) {
        this.colors = colors != null ? new ArrayList<>(colors) : null;
        return this;
    }

    public DoughnutChartBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public DoughnutChartBuilder setLegendTitle(String legendTitle) {
        this.legendTitle = legendTitle;
        return this;
    }

    public DoughnutChartBuilder setLegendVisible(boolean isLegendVisible) {
        this.isLegendVisible = isLegendVisible;
        return this;
    }

    public DoughnutChartBuilder setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public DoughnutChartBuilder setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
        return this;
    }

    public DoughnutChartBuilder setBackgroundStyle(ColorComponent backgroundStyle) {
        this.backgroundStyle = backgroundStyle;
        return this;
    }

    public DoughnutChartBuilder setDoughnutThickness(double doughnutThickness) {
        this.doughnutThickness = doughnutThickness;
        return this;
    }

    public DoughnutChartBuilder setDoughnutHoleSize(double doughnutHoleSize) {
        this.doughnutHoleSize = doughnutHoleSize;
        return this;
    }

    public DoughnutChartBuilder setShowPercentages(boolean showPercentages) {
        this.showPercentages = showPercentages;
        return this;
    }

    public DoughnutChartBuilder setShadowEffect(boolean shadowEffect) {
        this.shadowEffect = shadowEffect;
        return this;
    }

    public DoughnutChartBuilder setExplode(double explode) {
        this.explode = explode;
        return this;
    }

    public DoughnutChartBuilder setFigureSize(double figureWidth, double figureHeight) {
        this.figureWidth = figureWidth;
        this.figureHeight = figureHeight;
        return this;
    }

    public DoughnutChartBuilder setOutputFile(String outputFile) {
        this.outputFile = outputFile;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoughnutChartBuilder build() {
        Objects.requireNonNull(dataFrame, "DataFrame must not be null");
        Objects.requireNonNull(categoryColumn, "Category column must not be null");
        Objects.requireNonNull(valueColumns, "Value columns must not be null");
        if (valueColumns.isEmpty()) {
            throw new IllegalArgumentException("At least one value column must be provided");
        }
        if (!dataFrame.containsColumn(categoryColumn)) {
            throw new IllegalArgumentException("Category column '" + categoryColumn + "' not found in DataFrame");
        }
        for (String col : valueColumns) {
            if (!dataFrame.containsColumn(col)) {
                throw new IllegalArgumentException("Value column '" + col + "' not found in DataFrame");
            }
        }

        Column<?> labelCol = dataFrame.getColumn(categoryColumn);
        List<String> labels = Arrays.stream(labelCol.getValues())
                .map(obj -> obj != null ? obj.toString() : "null")
                .toList();

        DoughnutChartProperties properties = getProperties();
        int segmentIndex = 0;
        for (String valueColName : valueColumns) {
            Column<?> column = dataFrame.getColumn(valueColName);
            DoubleColumn doubleColumn;
            if (column instanceof IntegerColumn) {
                doubleColumn = ((IntegerColumn) column).asDoubleColumn();
            } else if (column instanceof DoubleColumn) {
                doubleColumn = (DoubleColumn) column;
            } else {
                throw new IllegalArgumentException("Column '" + valueColName + "' must be numeric");
            }

            for (int i = 0; i < doubleColumn.size() && i < labels.size(); i++) {
                double value = doubleColumn.getDouble(i);
                if (value < 0) {
                    throw new IllegalArgumentException("Segment value cannot be negative: " + value);
                }
                String label = labels.get(i) + " (" + valueColName + ")";
                ColorComponent color = colors != null && segmentIndex < colors.size() ? colors.get(segmentIndex) : ColorComponent.getCustomColor(segmentIndex);
                properties.addSegment(label, value, color);
                segmentIndex++;
            }
        }

        if (properties.getDoughnutSegments().isEmpty()) {
            throw new IllegalArgumentException("No valid segments created from DataFrame");
        }


        DoughnutChart chart = new DoughnutChart(properties);
        String html = chart.build();
        chart.save(getOutputFile(), html);
        return this;
    }

    @Override
    protected DoughnutChartProperties getProperties() {
        DoughnutChartProperties properties = new DoughnutChartProperties();
        if (title != null) properties.setTitle(title);
        if (legendTitle != null) properties.setLegendTitle(legendTitle);
        if (caption != null) properties.setCaption(caption);
        if (fontStyle != null) properties.setFontStyle(fontStyle);
        if (backgroundStyle != null) properties.setBackgroundColor(backgroundStyle);
        if (figureWidth != null && figureHeight != null) properties.setFigureSize(figureWidth, figureHeight);
        if (showPercentages != null) properties.setShowPercentages(showPercentages);
        if (shadowEffect != null) properties.setShadowEffect(shadowEffect);
        if (explode != null) properties.setExplode(explode);
        if (doughnutThickness != null) properties.setDoughnutThickness(doughnutThickness);
        if (doughnutHoleSize != null) properties.setHoleSize(doughnutHoleSize);
        if (isLegendVisible != null) properties.setLegendVisible(isLegendVisible);
        return properties;
    }

    @Override
    public String getOutputFile() {
        return outputFile != null ? outputFile : "doughnut_chart.html";
    }
}
