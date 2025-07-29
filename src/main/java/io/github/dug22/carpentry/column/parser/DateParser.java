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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DateParser implements ColumnParser<LocalDate> {

    private final Locale locale;
    private final List<DateTimeFormatter> formatters;
    private final boolean strict;

    /**
     * Default DateParser with the default values of Locale, Formatters, and strict passed.
     */
    public DateParser() {
        this(Locale.getDefault(), getDefaultFormatters(), true);
    }

    public DateParser(Locale locale, List<DateTimeFormatter> formatters, boolean strict) {
        this.locale = locale != null ? locale : Locale.getDefault();
        this.formatters = new ArrayList<>(formatters != null ? formatters : getDefaultFormatters());
        this.strict = strict;
    }

    /**
     * Returns a list of acceptable date formats.
     * @return a list of acceptable/common date formats.
     */
    public static List<DateTimeFormatter> getDefaultFormatters() {
        return Arrays.asList(
                DateTimeFormatter.ofPattern("yyyyMMdd"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("MM-dd-yyyy"),
                DateTimeFormatter.ofPattern("MM.dd.yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd"),
                caseInsensitiveFormatter("dd/MMM/yyyy"),
                caseInsensitiveFormatter("dd-MMM-yyyy"),
                DateTimeFormatter.ofPattern("M/d/yyyy"),
                DateTimeFormatter.ofPattern("M/d/yy"),
                caseInsensitiveFormatter("MMM/dd/yyyy"),
                caseInsensitiveFormatter("MMM-dd-yyyy"),
                caseInsensitiveFormatter("MMM/dd/yy"),
                caseInsensitiveFormatter("MMM-dd-yy"),
                caseInsensitiveFormatter("MMM/d/yyyy"),
                caseInsensitiveFormatter("MMM dd, yyyy"),
                caseInsensitiveFormatter("MMM d, yyyy"),
                DateTimeFormatter.ISO_LOCAL_DATE
        );
    }

    /**
     * Parses case insensitive date patterns
     * @param pattern the given date pattern
     * @return a DateTimeFormatter with these applied factors.
     */
    private static DateTimeFormatter caseInsensitiveFormatter(String pattern) {
        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(pattern)
                .toFormatter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canParse(String value) {
        if (isNA(value)) return !strict;
        try {
            parseDate(value.trim());
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate parse(String value) {
        if (isNA(value)) return Nulls.getDefaultNullValue(LocalDate.class);
        return parseDate(value.trim());
    }

    /**
     * Parses text as a LocaleDate
     * @param text the text to parse
     * @return text as a LocaleDate
     */
    private LocalDate parseDate(String text) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(text, formatter.withLocale(locale));
            } catch (DateTimeParseException ignored) {
            }
        }
        if (strict) {
            throw new DateTimeParseException("Cannot parse value as date: " + text, text, 0);
        }
        return Nulls.getDefaultNullValue(LocalDate.class);
    }

    /**
     * Returns a new DateParser with an additional format at the beginning.
     * @param pattern The date and time pattern string.
     * @return A new DateParser with the added format.
     */
    public DateParser withFormat(String pattern) {
        List<DateTimeFormatter> newFormatters = new ArrayList<>(formatters);
        newFormatters.addFirst(DateTimeFormatter.ofPattern(pattern));
        return new DateParser(locale, newFormatters, strict);
    }

    /**
     * Returns a new DateParser with an additional DateTimeFormatter at the beginning.
     * @param formatter The DateTimeFormatter to add.
     * @return A new DateParser with the added formatter.
     */
    public DateParser withFormat(DateTimeFormatter formatter) {
        List<DateTimeFormatter> newFormatters = new ArrayList<>(formatters);
        newFormatters.addFirst(formatter);
        return new DateParser(locale, newFormatters, strict);
    }

    /**
     * Returns a new DateParser with new DateTimeFormatters prepended to the existing list.
     * @param newFormats A list of DateTimeFormatter objects to add.
     * @return A new DateParser with the added formatters.
     */
    public DateParser withFormats(List<DateTimeFormatter> newFormats) {
        List<DateTimeFormatter> newFormatters = new ArrayList<>(newFormats);
        newFormatters.addAll(formatters);
        return new DateParser(locale, newFormatters, strict);
    }

    /**
     * Returns a new DateParser with the specified Locale.
     * @param locale The Locale to use for parsing.
     * @return A new DateParser with the updated locale.
     */
    public DateParser withLocale(Locale locale) {
        return new DateParser(locale, formatters, strict);
    }

    /**
     * Returns a new DateParser with the specified strictness setting.
     * @param strict True for strict parsing, false for lenient.
     * @return A new DateParser with the updated strictness.
     */
    public DateParser withStrict(boolean strict) {
        return new DateParser(locale, formatters, strict);
    }

    /**
     * Returns the given Locale
     * @return the Locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Returns the acceptable date formats
     * @return the acceptable date formats.
     */
    public List<DateTimeFormatter> getFormatters() {
        return new ArrayList<>(formatters);
    }

    /**
     * Returns whether date parsing is strict.
     * @return True if strict parsing is enabled, false otherwise.
     */
    public boolean isStrict() {
        return strict;
    }
}
