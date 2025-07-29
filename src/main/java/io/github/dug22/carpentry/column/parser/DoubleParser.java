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

package io.github.dug22.carpentry.column.parser;

import io.github.dug22.carpentry.column.ColumnParser;
import io.github.dug22.carpentry.utils.Nulls;

import java.text.NumberFormat;

public class DoubleParser implements ColumnParser<Double> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canParse(String value) {
        if (isNA(value)) return true;
        try {
            String cleanedValue = removeComma(value);
            if (isPercentage(cleanedValue)) {
                NumberFormat.getPercentInstance().parse(cleanedValue);
        } else {
                Double.parseDouble(cleanedValue);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public Double parse(final String value) {
        if (isNA(value)) {
            return Nulls.getDefaultNullValue(Double.class);
        }
        String cleanedValue = removeComma(value);
        try {
            if (cleanedValue.endsWith("%")) {
                return Double.parseDouble(cleanedValue.substring(0, cleanedValue.length() - 1)) / 100;
            }
            return Double.parseDouble(cleanedValue);
        }catch (NumberFormatException exception){
            return Nulls.getDefaultNullValue(Double.class);
        }
    }

    /**
     * Checks if given text contains a %
     * @param value the text to parse
     * @return if the given text contains a %
     */
    private boolean isPercentage(String value) {
        try {
            return value.charAt(value.length() - 1) == '%'
                    && !Nulls.isNull(Double.parseDouble(value.substring(0, value.length() - 1)));
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}
