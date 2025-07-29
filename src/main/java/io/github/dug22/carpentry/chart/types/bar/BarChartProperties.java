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

import io.github.dug22.carpentry.chart.ChartProperties;
import io.github.dug22.carpentry.chart.components.ColorComponent;
import io.github.dug22.carpentry.chart.components.FontStyle;

import java.util.ArrayList;
import java.util.List;

public class BarChartProperties extends ChartProperties {

    private String xAxisLabel = "X Data";
    private String yAxisLabel = "Y Data";
    private List<BarGroup> barGroups;
    private int marginTop = 20;
    private int marginRight = 20;
    private int marginBottom = 50;
    private int marginLeft = 60;
    private boolean gridLines = true;
    private List<ColorComponent> colorComponents = List.of(ColorComponent.BLUE, ColorComponent.RED, ColorComponent.GREEN);
    private int maxXLabelAmount = 15;
    private int maxXLabelCharLength = 6;

    public BarChartProperties() {
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);

    }

    @Override
    public void setCaption(String caption) {
        super.setCaption(caption);

    }

    @Override
    public void setLegendTitle(String legendTitle) {
        super.setLegendTitle(legendTitle);

    }

    @Override
    public void setLegendVisible(boolean legendVisible) {
        super.setLegendVisible(legendVisible);

    }

    @Override
    public void setFontStyle(FontStyle fontStyle) {
        super.setFontStyle(fontStyle);

    }

    @Override
    public void setBackgroundColor(ColorComponent backgroundColor) {
        super.setBackgroundColor(backgroundColor);

    }

    @Override
    public void setFigureSize(double figureWidth, double figureHeight) {
        super.setFigureSize(figureWidth, figureHeight);

    }

    public void setXAxisLabel(String xAxisLabel) {
        this.xAxisLabel = xAxisLabel;
    }

    public void setYAxisLabel(String yAxisLabel) {
        this.yAxisLabel = yAxisLabel;
    }

    public void setBarGroups(List<BarGroup> barGroups){
        this.barGroups = barGroups;
    }

    public void setMargins(int top, int right, int bottom, int left) {
        this.marginTop = top;
        this.marginRight = right;
        this.marginBottom = bottom;
        this.marginLeft = left;
    }

    public void setGridLines(boolean gridLines) {
        this.gridLines = gridLines;
    }

    public void setColorComponents(List<ColorComponent> colorComponents) {
        this.colorComponents = new ArrayList<>(colorComponents);
    }

    public void setMaxXLabelAmount(int maxXLabelAmount){
        this.maxXLabelAmount = maxXLabelAmount;
    }

    public void setMaxXLabelCharLength(int maxXLabelCharLength){
        this.maxXLabelCharLength = maxXLabelCharLength;
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public String getCaption() {
        return super.getCaption();
    }

    @Override
    public String getLegendTitle() {
        return super.getLegendTitle();
    }

    @Override
    public boolean isLegendVisible() {
        return super.isLegendVisible();
    }

    @Override
    public FontStyle getFontStyle() {
        return super.getFontStyle();
    }

    @Override
    public ColorComponent getBackgroundColor() {
        return super.getBackgroundColor();
    }

    @Override
    public double getFigureWidth() {
        return super.getFigureWidth();
    }

    @Override
    public double getFigureHeight() {
        return super.getFigureHeight();
    }

    public String getXAxisLabel() {
        return xAxisLabel;
    }

    public String getYAxisLabel() {
        return yAxisLabel;
    }


    public List<BarGroup> getBarGroups() {
        return barGroups;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public boolean isGridLines() {
        return gridLines;
    }

    public List<ColorComponent> getColorComponents() {
        return colorComponents;
    }

    public int getMaxXLabelAmount() {
        return maxXLabelAmount;
    }

    public int getMaxXLabelCharLength() {
        return maxXLabelCharLength;
    }
}
