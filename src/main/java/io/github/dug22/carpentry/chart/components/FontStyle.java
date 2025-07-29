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

/**
 * FontStyle allows you to specify the font you want to use within your charts
 * @param fontFamily the font family (Times New Roman, Arial, sans-serif, etc)
 * @param fontSize the size of the font
 * @param color the color of the text.
 */
public record FontStyle(String fontFamily, int fontSize, ColorComponent color) {
}
