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

import io.github.dug22.carpentry.chart.display.Browser;
import java.io.File;

/**
 * Abstract base class for building charts with specific properties.
 * @param <B> the type of the chart builder (e.g., a concrete builder like PieChartBuilder)
 * @param <P> the type of the chart properties (e.g., PieChartProperties)
 */
public abstract class AbstractChartBuilder<B, P> {

    /**
     * Retrieves the file path of the generated HTML chart file.
     * @return the file path as a String
     * @throws IllegalStateException if the output file is not set or invalid
     */
    public abstract String getOutputFile();

    /**
     * Constructs the chart instance based on the provided properties.
     * @return the constructed chart builder instance of type B
     */
    public abstract B build();

    /**
     * Retrieves the chart-specific properties for the chart being built.
     * @return the chart properties of type P
     */
    protected abstract P getProperties();

    /**
     * Opens the generated HTML chart file in the default web browser.
     * @throws UnsupportedOperationException if the browser isn't supported.
     */
    public void display() {
        Browser.browse(new File(getOutputFile()));
    }
}
