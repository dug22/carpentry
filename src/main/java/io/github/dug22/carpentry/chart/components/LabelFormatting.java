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

package io.github.dug22.carpentry.chart.components;

public class LabelFormatting {

    /**
     * Formats labels in scientific notation if their value is greater than one billion
     * @param value the value to format
     * @return the value in scientific notation
     */
    public static String format(double value) {
        return Math.abs(value) >= 1_000_000_000 ? String.format("%.1E", value) : String.format("%.0f", value);
    }
}
