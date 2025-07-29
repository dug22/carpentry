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

package io.github.dug22.carpentry.io.csv;

import io.github.dug22.carpentry.column.ColumnTypes;
import io.github.dug22.carpentry.column.format.temporal.DateColumnFormatter;
import io.github.dug22.carpentry.column.format.temporal.DateTimeColumnFormatter;
import io.github.dug22.carpentry.column.parser.DateParser;
import io.github.dug22.carpentry.column.parser.DateTimeParser;
import io.github.dug22.carpentry.io.AbstractDataSource;
import io.github.dug22.carpentry.io.DataSource;
import io.github.dug22.carpentry.io.ReadingProperties;
import io.github.dug22.carpentry.io.exceptions.CsvException;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CsvReadingProperties implements ReadingProperties {

    protected AbstractDataSource source;
    protected boolean hasHeaders = true;
    protected boolean allowDuplicateColumnNames = false;
    protected int maxColumnCharacterLength = 3096;
    private char delimiter = ',';
    private char quoteCharacter = '"';
    private char escapeCharacter = '\\';
    private DateParser dateParser = ColumnTypes.DATE_COLUMN_TYPE.getParser();
    private DateColumnFormatter dateFormatter = DateColumnFormatter.getDefault();
    private DateTimeParser dateTimeParser = ColumnTypes.DATE_TIME_COLUMN_TYPE.getParser();
    private DateTimeColumnFormatter dateTimeFormatter = DateTimeColumnFormatter.getDefault();
    private final Map<String, DateParser> dateParsers = new HashMap<>();
    private final Map<String, DateColumnFormatter> dateFormatters = new HashMap<>();
    private final Map<String, DateTimeParser> dateTimeParsers = new HashMap<>();
    private final Map<String, DateTimeColumnFormatter> dateTimeFormatters = new HashMap<>();
    public CsvReadingProperties() {

    }

    public CsvReadingProperties setSource(AbstractDataSource source) {
        this.source = source;
        return this;
    }

    public CsvReadingProperties setSource(File file) {
        this.source = DataSource.fromFile(file);
        return this;
    }

    public CsvReadingProperties setSource(File file, Charset charset) {
        this.source = DataSource.fromFile(file, charset);
        return this;
    }

    public CsvReadingProperties setSource(String urlLink) {
        this.source = DataSource.fromUrl(urlLink);
        return this;
    }

    public CsvReadingProperties setSource(String urlLink, Charset charset) {
        this.source = DataSource.fromUrl(urlLink, charset);
        return this;
    }

    public CsvReadingProperties hasHeaders(boolean hasHeaders) {
        this.hasHeaders = hasHeaders;
        return this;
    }

    public CsvReadingProperties allowDuplicateColumnNames(boolean allowDuplicateColumnNames) {
        this.allowDuplicateColumnNames = allowDuplicateColumnNames;
        return this;
    }

    public CsvReadingProperties setMaxColumnCharacterLength(int maxColumnCharacterLength) {
        this.maxColumnCharacterLength = maxColumnCharacterLength;
        return this;
    }

    public CsvReadingProperties setDelimiter(char delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public CsvReadingProperties setQuoteCharacter(char quoteCharacter) {
        this.quoteCharacter = quoteCharacter;
        return this;
    }

    public CsvReadingProperties setEscapeCharacter(char escapeCharacter) {
        this.escapeCharacter = escapeCharacter;
        return this;
    }


    // New setters for column-specific date parsers and formatters
    public CsvReadingProperties setDateParser(String columnName, DateParser parser) {
        dateParsers.put(columnName, parser != null ? parser : ColumnTypes.DATE_COLUMN_TYPE.getParser());
        return this;
    }

    public CsvReadingProperties setDateFormatter(String columnName, DateColumnFormatter formatter) {
        dateFormatters.put(columnName, formatter != null ? formatter : DateColumnFormatter.getDefault());
        return this;
    }

    // New setters for column-specific datetime parsers and formatters
    public CsvReadingProperties setDateTimeParser(String columnName, DateTimeParser parser) {
        dateTimeParsers.put(columnName, parser != null ? parser : ColumnTypes.DATE_TIME_COLUMN_TYPE.getParser());
        return this;
    }

    public CsvReadingProperties setDateTimeFormatter(String columnName, DateTimeColumnFormatter formatter) {
        dateTimeFormatters.put(columnName, formatter != null ? formatter : DateTimeColumnFormatter.getDefault());
        return this;
    }

    // Global setters for date
    public CsvReadingProperties setDateFormat(String pattern) {
        this.dateParser = dateParser.withFormat(pattern);
        this.dateFormatter = dateFormatter.withPattern(pattern);
        return this;
    }

    public CsvReadingProperties setDateLocale(Locale locale) {
        this.dateParser = dateParser.withLocale(locale);
        this.dateFormatter = dateFormatter.withLocale(locale);
        return this;
    }

    // Global setters for datetime
    public CsvReadingProperties setDateTimeFormat(String pattern) {
        this.dateTimeParser = dateTimeParser.withFormat(pattern);
        this.dateTimeFormatter = dateTimeFormatter.withPattern(pattern);
        return this;
    }

    public CsvReadingProperties setDateTimeLocale(Locale locale) {
        this.dateTimeParser = dateTimeParser.withLocale(locale);
        this.dateTimeFormatter = dateTimeFormatter.withLocale(locale);
        return this;
    }

    // Global setters for parser and formatter instances
    public CsvReadingProperties setDateParser(DateParser parser) {
        this.dateParser = parser != null ? parser : ColumnTypes.DATE_COLUMN_TYPE.getParser();
        return this;
    }

    public CsvReadingProperties setDateFormatter(DateColumnFormatter formatter) {
        this.dateFormatter = formatter != null ? formatter : DateColumnFormatter.getDefault();
        return this;
    }

    public CsvReadingProperties setDateTimeParser(DateTimeParser parser) {
        this.dateTimeParser = parser != null ? parser : ColumnTypes.DATE_TIME_COLUMN_TYPE.getParser();
        return this;
    }

    public CsvReadingProperties setDateTimeFormatter(DateTimeColumnFormatter formatter) {
        this.dateTimeFormatter = formatter != null ? formatter : DateTimeColumnFormatter.getDefault();
        return this;
    }


    public AbstractDataSource getSource() {
        return source;
    }

    public int getMaxColumnCharacterLength() {
        return maxColumnCharacterLength;
    }

    public boolean allowDuplicateColumnNames() {
        return allowDuplicateColumnNames;
    }

    public boolean hasHeaders() {
        return hasHeaders;
    }

    public char getDelimiter() {
        return delimiter;
    }

    public char getQuoteCharacter() {
        return quoteCharacter;
    }

    public char getEscapeCharacter() {
        return escapeCharacter;
    }
    public DateParser getDateParser(String columnName) {
        return dateParsers.getOrDefault(columnName, dateParser);
    }

    public DateColumnFormatter getDateFormatter(String columnName) {
        return dateFormatters.getOrDefault(columnName, dateFormatter);
    }

    public DateTimeParser getDateTimeParser(String columnName) {
        return dateTimeParsers.getOrDefault(columnName, dateTimeParser);
    }

    public DateTimeColumnFormatter getDateTimeFormatter(String columnName) {
        return dateTimeFormatters.getOrDefault(columnName, dateTimeFormatter);
    }


    public CsvReadingProperties build() {
        if (source == null) throw new CsvException("A data source must be initialized before building!");
        return this;
    }
}
