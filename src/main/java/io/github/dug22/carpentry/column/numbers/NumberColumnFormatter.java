/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.github.dug22.carpentry.column.numbers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Function;

public class NumberColumnFormatter {

    /**
     * Formats given values as a percentage with a specified number of fractional digits.
     *
     * @param fractionalDigits the number of digits to display after the decimal point
     * @return a function that takes a Double value and returns a formatted string representing the percentage
     */
    public static Function<Double, String> percent(int fractionalDigits) {
        return value -> {
            if (value == null) {
                return "NA";
            }
            NumberFormat format = NumberFormat.getPercentInstance();
            format.setGroupingUsed(false);
            format.setMinimumFractionDigits(fractionalDigits);
            format.setMaximumFractionDigits(fractionalDigits);
            return format.format(value);
        };
    }

    /**
     * Formats given values as currency based on the specified language and country locale.
     *
     * @param language the language of the desired locale (e.g., "en" for English)
     * @param country the country of the desired locale (e.g., "US" for United States)
     * @return a function that takes a Double value and returns a formatted string representing the currency
     */
    public static Function<Double, String> currency(String language, String country) {
        return value -> {
            if (value == null) {
                return "NA";
            }
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.of(language, country));
            return format.format(value);
        };
    }

    /**
     * Formats given values in scientific notation with a specified number of decimal places.
     *
     * @param decimalPlaces the number of decimal places to display
     * @return a function that takes a Double value and returns a formatted string in scientific notation
     */
    public static Function<Double, String> scientificNotation(int decimalPlaces) {
        return value -> {
            if (value == null) {
                return "NaN";
            }
            String pattern = "0." + "#".repeat(decimalPlaces) + "E0";
            DecimalFormat format = new DecimalFormat(pattern);
            return format.format(value);
        };
    }

    /**
     * Formats given values in standard notation (i.e., a regular number with the specified number of decimal places).
     *
     * @param decimalPlaces the number of decimal places to display
     * @return a function that takes a Double value and returns a formatted string in standard notation
     */
    public static Function<Double, String> standardNotation(int decimalPlaces) {
        return value -> {
            if (value == null) {
                return "NaN";
            }

            String pattern = "0." + "#".repeat(decimalPlaces);
            DecimalFormat format = new DecimalFormat(pattern);
            return format.format(value);
        };
    }
}
