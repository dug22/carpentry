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

package io.github.dug22.carpentry.column.impl;

import io.github.dug22.carpentry.column.*;
import io.github.dug22.carpentry.column.conversion.DateColumnConversions;
import io.github.dug22.carpentry.column.format.temporal.DateColumnFormatter;
import io.github.dug22.carpentry.column.parser.DateParser;
import io.github.dug22.carpentry.column.transformations.DateTransformations;
import io.github.dug22.carpentry.column.type.DateColumnType;
import io.github.dug22.carpentry.utils.Nulls;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateColumn extends BaseColumn<LocalDate> implements DateTransformations, DateColumnConversions {
    private DateParser parser;
    private DateColumnFormatter formatter;

    public DateColumn(String name) {
        super(name, ColumnTypes.DATE_COLUMN_TYPE, ColumnTypes.DATE_COLUMN_TYPE.getParser());
        this.formatter = DateColumnFormatter.getDefault();
    }

    public DateColumn(String name, LocalDate[] data) {
        super(name, ColumnTypes.DATE_COLUMN_TYPE, data, ColumnTypes.DATE_COLUMN_TYPE.getParser());
        this.formatter = DateColumnFormatter.getDefault();
    }

    public DateColumn(String name, LocalDate[] data, DateColumnFormatter formatter) {
        super(name, ColumnTypes.DATE_COLUMN_TYPE, data, ColumnTypes.DATE_COLUMN_TYPE.getParser());
        this.formatter = formatter;
    }

    public static DateColumn create(String columnName) {
        return new DateColumn(columnName);
    }

    public static DateColumn create(String columnName, LocalDate[] data) {
        return new DateColumn(columnName, data);
    }

    public static DateColumn create(String columnName, LocalDate[] data, DateColumnFormatter formatter) {
        return new DateColumn(columnName, data, formatter);
    }

    public static DateColumn create(String columnName, String[] data) {
        DateColumn column = create(columnName);
        LocalDate[] parsedData = new LocalDate[data.length];
        column.parser = column.parser != null ? column.parser : ColumnTypes.DATE_COLUMN_TYPE.getParser();
        for (int i = 0; i < data.length; i++) {
            parsedData[i] = Nulls.isNull(data[i]) || column.parser.isNA(data[i]) ? Nulls.getDefaultNullValue(LocalDate.class) : column.parser.parse(data[i]);
        }
        column.setData(parsedData);
        return column;
    }

    public static DateColumn create(String columnName, String[] data, DateColumnFormatter formatter) {
        DateColumn column = create(columnName);
        column.setOutputFormatter(formatter);
        LocalDate[] parsedData = new LocalDate[data.length];
        column.parser = column.parser != null ? column.parser : ColumnTypes.DATE_COLUMN_TYPE.getParser();
        for (int i = 0; i < data.length; i++) {
            parsedData[i] = Nulls.isNull(data[i]) || column.parser.isNA(data[i]) ? Nulls.getDefaultNullValue(LocalDate.class) : column.parser.parse(data[i]);
        }
        column.setData(parsedData);
        return column;
    }

    public static DateColumn create(String columnName, String[] data, DateParser parser) {
        DateColumn column = create(columnName);
        column.parser = parser != null ? parser : ColumnTypes.DATE_COLUMN_TYPE.getParser();
        LocalDate[] parsedData = new LocalDate[data.length];
        for (int i = 0; i < data.length; i++) {
            parsedData[i] = Nulls.isNull(data[i]) || column.parser.isNA(data[i]) ? Nulls.getDefaultNullValue(LocalDate.class) : column.parser.parse(data[i]);
        }
        column.setData(parsedData);
        return column;
    }

    public LocalDate parse(String value) {
        return parser.parse(value);
    }

    @Override
    public void appendNull() {
        append(Nulls.getDefaultNullValue(LocalDate.class));
    }

    @Override
    public DateColumn copy() {
        LocalDate[] dataCopy = Arrays.copyOf(getValues(), size());
        DateColumn copy = create(name(), dataCopy);
        copy.parser = parser;
        copy.formatter = formatter;
        return copy;
    }

    @Override
    public DateColumn emptyCopy() {
        DateColumn emptyCopy = create(name());
        emptyCopy.parser = parser;
        emptyCopy.formatter = formatter;
        return emptyCopy;
    }

    @Override
    public DateColumn unique() {
        Set<LocalDate> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        LocalDate[] uniqueData = uniqueValues.toArray(new LocalDate[0]);
        DateColumn unique = new DateColumn(name() + " (unique)", uniqueData);
        unique.parser = parser;
        unique.formatter = formatter;
        return unique;
    }

    @Override
    public boolean isAbsent(LocalDate value) {
        return Nulls.isNull(value);
    }

    @Override
    public boolean isAbsent(int index) {
        return isAbsent(getValues()[index]);
    }

    @Override
    public DateColumnType columnType() {
        return ColumnTypes.DATE_COLUMN_TYPE;
    }

    @Override
    public DateColumn filter(ColumnPredicate<? super LocalDate> condition) {
        DateColumn filteredColumn = (DateColumn) super.filter(condition);
        filteredColumn.parser = parser;
        filteredColumn.formatter = formatter;
        return filteredColumn;
    }

    @Override
    public DateColumn apply(ColumnFunction<? super LocalDate, ? extends LocalDate> function) {
        DateColumn appliedColumn = (DateColumn) super.apply(function);
        appliedColumn.parser = parser;
        appliedColumn.formatter = formatter;
        return appliedColumn;
    }

    public String[] asStringArray() {
        String[] result = new String[size()];
        for (int i = 0; i < size(); i++) {
            result[i] = formatter.format(getValues()[i]);
        }
        return result;
    }

    public String[] asStringArray(String pattern) {
        DateColumnFormatter tempFormatter = formatter.withPattern(pattern);
        String[] result = new String[size()];
        for (int i = 0; i < size(); i++) {
            result[i] = tempFormatter.format(getValues()[i]);
        }
        return result;
    }

    public DateColumn addInputFormat(String pattern) {
        this.parser = parser.withFormat(pattern);
        return this;
    }

    public DateColumn addInputFormat(DateTimeFormatter formatter) {
        this.parser = parser.withFormat(formatter);
        return this;
    }

    public DateColumn addInputFormats(List<DateTimeFormatter> formats) {
        this.parser = parser.withFormats(formats);
        return this;
    }

    public DateColumn setInputLocale(Locale locale) {
        this.parser = parser.withLocale(locale);
        return this;
    }

    public DateColumn setStrict(boolean strict) {
        this.parser = parser.withStrict(strict);
        return this;
    }

    public DateColumn setParser(DateParser parser) {
        this.parser = parser != null ? parser : ColumnTypes.DATE_COLUMN_TYPE.getParser();
        return this;
    }

    public DateColumn setOutputFormat(String pattern) {
        this.formatter = formatter.withPattern(pattern);
        return this;
    }

    public DateColumn setOutputFormatter(DateColumnFormatter formatter) {
        this.formatter = formatter != null ? formatter : DateColumnFormatter.getDefault();
        return this;
    }

    public DateColumn setOutputLocale(Locale locale) {
        this.formatter = formatter.withLocale(locale);
        return this;
    }

    public DateColumn setUseOrdinal(boolean useOrdinal) {
        this.formatter = formatter.withOrdinal(useOrdinal);
        return this;
    }

    public DateColumnFormatter getDateColumnFormatter() {
        return formatter;
    }

    public Locale getInputLocale() {
        return parser.getLocale();
    }

    public List<DateTimeFormatter> getInputFormatters() {
        return parser.getFormatters();
    }

    public boolean isStrict() {
        return parser.isStrict();
    }

    public DateTimeFormatter getOutputFormatter() {
        return formatter.getFormatter();
    }

    public Locale getOutputLocale() {
        return formatter.getLocale();
    }

    public boolean isUsingOrdinal() {
        return formatter.isUsingOrdinal();
    }

    @Override
    public DateParser getColumnParser() {
        return (DateParser) super.getColumnParser();
    }

    @Override
    public void setColumnParser(ColumnParser<LocalDate> columnParser) {
        super.setColumnParser(columnParser);
    }
}