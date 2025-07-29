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

package io.github.dug22.carpentry.chart.types.pie;

import io.github.dug22.carpentry.chart.ChartProperties;
import io.github.dug22.carpentry.chart.components.ColorComponent;
import io.github.dug22.carpentry.chart.components.FontStyle;

import java.util.ArrayList;
import java.util.List;

public class PieChartProperties extends ChartProperties {

    private final List<PieSlice> pieSlices = new ArrayList<>();
    private boolean showPercentages = true;
    private boolean shadowEffect = false;
    private double explode = 0;

    public PieChartProperties() {

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

    public void addSlice(String label, double value, ColorComponent color) {
        this.pieSlices.add(new PieSlice(label, value, color));

    }

    public void setShowPercentages(boolean show) {
        this.showPercentages = show;

    }

    public void setShadowEffect(boolean shadow) {
        this.shadowEffect = shadow;

    }

    public void setExplode(double explode) {
        if (explode < 0) {
            throw new IllegalArgumentException("Explode value must be non-negative");
        }
        if (explode > 1.5) {
            throw new IllegalArgumentException("Explode value cannot exceed a value over 1.5");
        }
        this.explode = explode;

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

    public List<PieSlice> getPieSlices() {
        return pieSlices;
    }

    public boolean arePercentagesVisible() {
        return showPercentages;
    }

    public boolean hasShadowEffect() {
        return shadowEffect;
    }

    public double getExplode() {
        return explode;
    }
}
