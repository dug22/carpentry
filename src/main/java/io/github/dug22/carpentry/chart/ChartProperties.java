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

import io.github.dug22.carpentry.chart.components.ColorComponent;
import io.github.dug22.carpentry.chart.components.FontStyle;

public class ChartProperties {

    private String title = "Chart Title";
    private String caption = "";
    private String legendTitle = "Legend Title";
    private boolean isLegendVisible = true;
    private FontStyle fontStyle = new FontStyle("Arial, sans-serif", 16, ColorComponent.BLACK);
    private ColorComponent backgroundColor = ColorComponent.WHITE;
    private double figureWidth = 800;
    private double figureHeight = 600;

    public ChartProperties(){

    }

    protected void setTitle(String title){
        this.title = title;
        
    }

    protected void setCaption(String caption){
        this.caption = caption;
        
    }

    protected void setLegendTitle(String legendTitle) {
        this.legendTitle = legendTitle;
        
    }

    protected void setLegendVisible(boolean legendVisible) {
        isLegendVisible = legendVisible;
        
    }

    protected void setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
        
    }

    protected void setBackgroundColor(ColorComponent backgroundColor) {
        this.backgroundColor = backgroundColor;
        
    }

    protected void setFigureSize(double figureWidth, double figureHeight) {
        if(figureWidth <= 0 || figureHeight <= 0){
            throw new IllegalArgumentException("Figure width and height must be greater than 0!");
        }
        this.figureWidth = figureWidth;
        this.figureHeight = figureHeight;
    }

    protected String getTitle() {
        return title;
    }

    protected String getCaption() {
        return caption;
    }

    protected String getLegendTitle() {
        return legendTitle;
    }

    protected boolean isLegendVisible() {
        return isLegendVisible;
    }

    protected FontStyle getFontStyle() {
        return fontStyle;
    }

    protected ColorComponent getBackgroundColor() {
        return backgroundColor;
    }

    protected double getFigureWidth() {
        return figureWidth;
    }

    protected double getFigureHeight() {
        return figureHeight;
    }
}
