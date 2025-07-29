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

package io.github.dug22.carpentry.column.format;

import io.github.dug22.carpentry.column.AbstractColumnFormatter;
import io.github.dug22.carpentry.column.ColumnFunction;
import io.github.dug22.carpentry.utils.Nulls;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

/**
 * Formats numeric column values using predefined or custom number formats.
 */
public class NumericColumnFormatter extends AbstractColumnFormatter {

    /** The NumberFormat instance used to format values */
    private final NumberFormat numberFormatter;

    /**
     * Predefined formatter types for numeric columns.
     */
    public enum NumberColumnFormatterTypes {

        /**
         * Format as a percentage (requires fractional digits).
         */
        PERCENT(optionalParameter -> {
            int fractionalDigits = (int) optionalParameter.orElseThrow(() ->
                    new IllegalArgumentException("PERCENT formatter requires fractional digits as parameter"));
            NumberFormat format = NumberFormat.getPercentInstance();
            format.setGroupingUsed(false);
            format.setMinimumFractionDigits(fractionalDigits);
            format.setMaximumFractionDigits(fractionalDigits);
            return new NumericColumnFormatter(format);
        }),

        /**
         * Format with high precision floating point.
         */
        FLOATING_POINT_DEFAULT(param -> {
            NumberFormat format = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.getDefault()));
            format.setMaximumFractionDigits(340);
            format.setMaximumIntegerDigits(340);
            format.setGroupingUsed(false);
            return new NumericColumnFormatter(format);
        }),

        /**
         * Format as integers with no grouping (e.g., 1000).
         */
        INTS(param -> {
            NumberFormat format = new DecimalFormat();
            format.setGroupingUsed(false);
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(0);
            return new NumericColumnFormatter(format);
        }),

        /**
         * Format as integers with grouping (e.g., 1,000).
         */
        INTS_WITH_GROUPING(param -> {
            NumberFormat format = new DecimalFormat();
            format.setGroupingUsed(true);
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(0);
            return new NumericColumnFormatter(format);
        }),

        /**
         * Format as fixed-point with grouping (requires fractional digits).
         */
        FIXED_WITH_GROUPING(optionalParameter -> {
            int fractionalDigits = (int) optionalParameter.orElseThrow(() ->
                    new IllegalArgumentException("FIXED_WITH_GROUPING formatter requires fractional digits as parameter"));
            NumberFormat format = new DecimalFormat();
            format.setGroupingUsed(true);
            format.setMinimumFractionDigits(fractionalDigits);
            format.setMaximumFractionDigits(fractionalDigits);
            return new NumericColumnFormatter(format);
        }),

        /**
         * Format as currency (requires Locale).
         */
        CURRENCY(optionalParameter -> {
            Locale locale = (Locale) optionalParameter.orElseThrow(() ->
                    new IllegalArgumentException("CURRENCY formatter requires a Locale as parameter"));
            NumberFormat format = NumberFormat.getCurrencyInstance(locale);
            return new NumericColumnFormatter(format);
        }),

        /**
         * Use default formatting (no special formatting applied).
         */
        STANDARD(param -> new NumericColumnFormatter());

        private final ColumnFunction<Optional<Object>, NumericColumnFormatter> columnFunction;

        NumberColumnFormatterTypes(ColumnFunction<Optional<Object>, NumericColumnFormatter> columnFunction) {
            this.columnFunction = columnFunction;
        }

        /**
         * Initialize formatter with no parameter.
         */
        public NumericColumnFormatter init() {
            return columnFunction.apply(Optional.empty());
        }

        /**
         * Initialize formatter with a parameter.
         * @param parameter Format configuration parameter
         */
        public NumericColumnFormatter init(Object parameter) {
            return columnFunction.apply(Optional.of(parameter));
        }

        /**
         * Initialize formatter with an optional parameter.
         * @param optionalParameter Optional configuration
         */
        public NumericColumnFormatter init(Optional<Object> optionalParameter) {
            return columnFunction.apply(optionalParameter);
        }
    }

    /**
     * Creates a default formatter (no formatting applied).
     */
    public NumericColumnFormatter() {
        this.numberFormatter = null;
    }

    /**
     * Creates a formatter with a custom NumberFormat.
     * @param numberFormatter The NumberFormat instance
     */
    public NumericColumnFormatter(NumberFormat numberFormatter) {
        this.numberFormatter = numberFormatter;
    }

    /**
     * Format a long value.
     */
    public String format(long value) {
        return formatValue(value);
    }

    /**
     * Format an int value.
     */
    public String format(int value) {
        return formatValue(value);
    }

    /**
     * Format a short value.
     */
    public String format(short value) {
        return formatValue(value);
    }

    /**
     * Format a float value. Handles NaN specially.
     */
    public String format(float value) {
        if (Nulls.isNull(value)) {
            return String.valueOf(Nulls.getDefaultNullValue(Float.class));
        }
        return formatValue(value);
    }

    /**
     * Format a double value. Handles NaN specially.
     */
    public String format(double value) {
        if (Nulls.isNull(value)) {
            return String.valueOf(Nulls.getDefaultNullValue(Double.class));
        }
        return formatValue(value);
    }

    /**
     * Format any numeric value using the configured formatter.
     * @param value The number to format
     * @return Formatted string
     */
    public String formatValue(Number value) {
        return (numberFormatter == null) ? String.valueOf(value) : numberFormatter.format(value);
    }
}

