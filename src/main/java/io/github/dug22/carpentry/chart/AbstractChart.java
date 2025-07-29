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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractChart<P extends ChartProperties> {

    //Each chart type have their own chart properties.
    protected P properties;

    /**
     * Default constructor for AbstractChart.class
     * @param properties the specified chart properties.
     */
    public AbstractChart(P properties) {
        this.properties = properties;
    }

    /**
     * Loads the specified chart template and replaces the placeholders with the appropriate given values
     * @return the full built out chart.
     */
    public String build() {
        String webPageTitle = getProperties().getTitle();
        String styles = "<style>\n" + getStyles() + "</style>\n";
        String figure = getFigure();
        String template = loadTemplate(getTemplateFile());
        return template.replace("{Web_Page_Title}", webPageTitle)
                .replace("{STYLES}", styles)
                .replace("{FIGURE_TEMPLATE}", figure);
    }

    /**
     * Creates the SVG figure for a given chart.
     *
     * @return the SVG figure of the chart.
     */
    protected abstract String getFigure();

    /**
     * Creates the legend for a given chart
     * @return the legend design for a given chart.
     */
    protected abstract String getKeyLegend();


    /**
     * Provides the inline CSS styles used for rendering a chart.
     *
     * @return a string containing the complete CSS styles for the chart SVG.
     */
    protected abstract String getStyles();

    /**
     * Retrieves the appropriate HTML chart template
     * @return the appropriate HTML chart template
     */
    protected abstract String getTemplateFile();

    /**
     * Saves a chart as an HTML file
     * @param filePath the HTML output file path.
     * @param content the given html content.
     */
    public void save(String filePath, String content) {
        try {
            Files.writeString(Paths.get(filePath), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a specific chart template
     * @param resourcePath the chart template
     * @return the loaded chart template.
     */
    public String loadTemplate(String resourcePath) {
        try (InputStream inputStream = AbstractChart.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource " + resourcePath + " not found!");
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalArgumentException("We were having trouble loading your resource " + resourcePath);
        }
    }

    /**
     * Gets the specified chart properties.
     * @return the chart properties
     */
    public P getProperties() {
        return properties;
    }

}
