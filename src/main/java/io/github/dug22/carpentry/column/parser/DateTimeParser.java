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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DateTimeParser implements ColumnParser<LocalDateTime> {

    private final Locale locale;
    private final List<DateTimeFormatter> formatters;
    private final boolean strict;

    /**
     * Default DateTimeParser with the default values of Locale, Formatters, and strict passed.
     */
    public DateTimeParser(){
        this(Locale.getDefault(), getDefaultFormatters(), true);
    }

    public DateTimeParser(Locale locale, List<DateTimeFormatter> formatters, boolean strict){
        this.locale = locale != null ? locale : Locale.getDefault();
        this.formatters = new ArrayList<>(formatters != null ? formatters : getDefaultFormatters());
        this.strict = strict;
    }

    /**
     * Returns a list of acceptable datetime formats.
     * @return a list of acceptable/common datetime formats.
     */
    public static List<DateTimeFormatter> getDefaultFormatters() {
        return Arrays.asList(
                DateTimeFormatter.ofPattern("yyyyMMdd['T'HH:mm[:ss]]"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy[ HH:mm[:ss]]"),
                DateTimeFormatter.ofPattern("MM-dd-yyyy[ HH:mm[:ss]]"),
                DateTimeFormatter.ofPattern("MM.dd.yyyy[ HH:mm[:ss]]"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd['T'HH:mm[:ss]]"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd[ HH:mm[:ss]]"),
                caseInsensitiveFormatter("dd/MMM/yyyy[ HH:mm[:ss]]"),
                caseInsensitiveFormatter("dd-MMM-yyyy[ HH:mm[:ss]]"),
                DateTimeFormatter.ofPattern("M/d/yyyy[ HH:mm[:ss]]"),
                DateTimeFormatter.ofPattern("M/d/yy[ HH:mm[:ss]]"),
                caseInsensitiveFormatter("MMM/dd/yyyy[ HH:mm[:ss]]"),
                caseInsensitiveFormatter("MMM-dd-yyyy[ HH:mm[:ss]]"),
                caseInsensitiveFormatter("MMM/dd/yy[ HH:mm[:ss]]"),
                caseInsensitiveFormatter("MMM-dd-yy[ HH:mm[:ss]]"),
                caseInsensitiveFormatter("MMM/d/yyyy[ HH:mm[:ss]]"),
                caseInsensitiveFormatter("MMM dd, yyyy[ HH:mm[:ss]]"),
                caseInsensitiveFormatter("MMM d, yyyy[ HH:mm[:ss]]"),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME,
                DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm:ss a"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm[:ss]")
        );
    }

    /**
     * Parses case insensitive datetime patterns
     * @param pattern the given datetime pattern
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
            parseDateTime(value.trim());
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime parse(String value) {
        if (isNA(value)) return Nulls.getDefaultNullValue(LocalDateTime.class);
        return parseDateTime(value.trim());
    }

    /**
     * Parses text as a LocaleDateTime
     * @param text the text to parse
     * @return text as a LocaleDateTime
     */
    private LocalDateTime parseDateTime(String text) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDateTime.parse(text, formatter.withLocale(locale));
            } catch (DateTimeParseException ignored) {
            }
        }
        if (strict) {
            throw new DateTimeParseException("Cannot parse value as date: " + text, text, 0);
        }
        return Nulls.getDefaultNullValue(LocalDateTime.class);
    }

    /**
     * Returns a new DateTimeParser with an additional format at the beginning.
     * @param pattern The date and time pattern string.
     * @return A new DateTimeParser with the added format.
     */
    public DateTimeParser withFormat(String pattern) {
        List<DateTimeFormatter> newFormatters = new ArrayList<>(formatters);
        newFormatters.addFirst(DateTimeFormatter.ofPattern(pattern));
        return new DateTimeParser(locale, newFormatters, strict);
    }

    /**
     * Returns a new DateTimeParser with an additional DateTimeFormatter at the beginning.
     * @param formatter The DateTimeFormatter to add.
     * @return A new DateTimeParser with the added formatter.
     */
    public DateTimeParser withFormat(DateTimeFormatter formatter) {
        List<DateTimeFormatter> newFormatters = new ArrayList<>(formatters);
        newFormatters.addFirst(formatter);
        return new DateTimeParser(locale, newFormatters, strict);
    }

    /**
     * Returns a new DateTimeParser with new DateTimeFormatters prepended to the existing list.
     * @param newFormats A list of DateTimeFormatter objects to add.
     * @return A new DateTimeParser with the added formatters.
     */
    public DateTimeParser withFormats(List<DateTimeFormatter> newFormats) {
        List<DateTimeFormatter> newFormatters = new ArrayList<>(newFormats);
        newFormatters.addAll(formatters);
        return new DateTimeParser(locale, newFormatters, strict);
    }

    /**
     * Returns a new DateTimeParser with the specified Locale.
     * @param locale The Locale to use for parsing.
     * @return A new DateTimeParser with the updated locale.
     */
    public DateTimeParser withLocale(Locale locale) {
        return new DateTimeParser(locale, formatters, strict);
    }

    /**
     * Returns a new DateTimeParser with the specified strictness setting.
     * @param strict True for strict parsing, false for lenient.
     * @return A new DateTimeParser with the updated strictness.
     */
    public DateTimeParser withStrict(boolean strict) {
        return new DateTimeParser(locale, formatters, strict);
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
     * Returns whether date time parsing is strict.
     * @return True if strict parsing is enabled, false otherwise.
     */
    public boolean isStrict() {
        return strict;
    }
}
