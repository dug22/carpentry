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

import io.github.dug22.carpentry.column.BaseColumn;
import io.github.dug22.carpentry.column.ColumnFunction;
import io.github.dug22.carpentry.column.ColumnPredicate;
import io.github.dug22.carpentry.column.ColumnTypes;
import io.github.dug22.carpentry.column.conversion.DateTimeColumnConversions;
import io.github.dug22.carpentry.column.format.temporal.DateTimeColumnFormatter;
import io.github.dug22.carpentry.column.parser.DateTimeParser;
import io.github.dug22.carpentry.column.transformations.DateTimeTransformation;
import io.github.dug22.carpentry.column.type.DateTimeColumnType;
import io.github.dug22.carpentry.utils.Nulls;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateTimeColumn extends BaseColumn<LocalDateTime> implements DateTimeColumnConversions, DateTimeTransformation {

    private DateTimeParser parser;
    private DateTimeColumnFormatter dateTimeColumnFormatter;

    public DateTimeColumn(String name) {
        super(name, ColumnTypes.DATE_TIME_COLUMN_TYPE, ColumnTypes.DATE_TIME_COLUMN_TYPE.getParser());
        this.dateTimeColumnFormatter = DateTimeColumnFormatter.getDefault();
    }

    public DateTimeColumn(String name, LocalDateTime[] data) {
        super(name, ColumnTypes.DATE_TIME_COLUMN_TYPE, data, ColumnTypes.DATE_TIME_COLUMN_TYPE.getParser());
        this.dateTimeColumnFormatter = DateTimeColumnFormatter.getDefault();
    }

    public DateTimeColumn(String name, LocalDateTime[] data, DateTimeColumnFormatter formatter){
        super(name, ColumnTypes.DATE_TIME_COLUMN_TYPE, data, ColumnTypes.DATE_TIME_COLUMN_TYPE.getParser());
        this.dateTimeColumnFormatter = formatter;
    }

    public static DateTimeColumn create(String columnName) {
        return new DateTimeColumn(columnName);
    }

    public static DateTimeColumn create(String columnName, LocalDateTime[] data) {
        return new DateTimeColumn(columnName, data);
    }

    public static DateTimeColumn create(String columnName, String[] data) {
        DateTimeColumn column = create(columnName);
        column.parser = ColumnTypes.DATE_TIME_COLUMN_TYPE.getParser();
        LocalDateTime[] parsedData = new LocalDateTime[data.length];
        for (int i = 0; i < data.length; i++) {
            parsedData[i] = Nulls.isNull(data[i]) || column.parser.isNA(data[i]) ? Nulls.getDefaultNullValue(LocalDateTime.class) : column.parser.parse(data[i]);
        }
        column.setData(parsedData);
        return column;
    }
    public static DateTimeColumn create(String columnName, String[] data, DateTimeColumnFormatter formatter) {
        DateTimeColumn column = create(columnName);
        column.setOutputFormatter(formatter);
        column.parser = ColumnTypes.DATE_TIME_COLUMN_TYPE.getParser();
        LocalDateTime[] parsedData = new LocalDateTime[data.length];
        for (int i = 0; i < data.length; i++) {
            parsedData[i] = Nulls.isNull(data[i]) || column.parser.isNA(data[i]) ? Nulls.getDefaultNullValue(LocalDateTime.class) : column.parser.parse(data[i]);
        }
        column.setData(parsedData);
        return column;
    }

    public static DateTimeColumn create(String columnName, String[] data, DateTimeParser parser) {
        DateTimeColumn column = create(columnName);
        column.parser = parser != null ? parser : ColumnTypes.DATE_TIME_COLUMN_TYPE.getParser();
        LocalDateTime[] parsedData = new LocalDateTime[data.length];
        for (int i = 0; i < data.length; i++) {
            parsedData[i] = Nulls.isNull(data[i]) || column.parser.isNA(data[i]) ? Nulls.getDefaultNullValue(LocalDateTime.class) : column.parser.parse(data[i]);
        }
        column.setData(parsedData);
        return column;
    }

    public LocalDateTime parse(String value) {
        return parser.parse(value);
    }

    @Override
    public void appendNull(){
        append(Nulls.getDefaultNullValue(LocalDateTime.class));
    }


    @Override
    public DateTimeColumn copy() {
        LocalDateTime[] dataCopy = Arrays.copyOf(getValues(), size());
        DateTimeColumn copy = create(name(), dataCopy);
        copy.parser = parser;
        copy.dateTimeColumnFormatter = dateTimeColumnFormatter;
        return copy;
    }

    @Override
    public DateTimeColumn emptyCopy() {
        DateTimeColumn emptyCopy = create(name());
        emptyCopy.parser = parser;
        emptyCopy.dateTimeColumnFormatter = dateTimeColumnFormatter;
        return emptyCopy;
    }

    @Override
    public DateTimeColumn unique() {
        Set<LocalDateTime> uniqueValues = new LinkedHashSet<>(Arrays.asList(getValues()));
        LocalDateTime[] uniqueData = uniqueValues.toArray(new LocalDateTime[0]);
        DateTimeColumn unique = create(name() + " (unique)", uniqueData);
        unique.parser = parser;
        unique.dateTimeColumnFormatter = dateTimeColumnFormatter;
        return unique;
    }

    @Override
    public boolean isAbsent(LocalDateTime value) {
        return Nulls.isNull(value);
    }

    @Override
    public boolean isAbsent(int index) {
        return isAbsent(getValues()[index]);
    }

    @Override
    public DateTimeColumnType columnType() {
        return ColumnTypes.DATE_TIME_COLUMN_TYPE;
    }

    @Override
    public DateTimeColumn filter(ColumnPredicate<? super LocalDateTime> condition) {
        DateTimeColumn filteredColumn = (DateTimeColumn) super.filter(condition);
        filteredColumn.parser = parser;
        filteredColumn.dateTimeColumnFormatter = dateTimeColumnFormatter;
        return filteredColumn;
    }

    @Override
    public DateTimeColumn apply(ColumnFunction<? super LocalDateTime, ? extends LocalDateTime> function) {
        DateTimeColumn appliedColumn = (DateTimeColumn) super.apply(function);
        appliedColumn.parser = parser;
        appliedColumn.dateTimeColumnFormatter = dateTimeColumnFormatter;
        return appliedColumn;
    }


    public String[] asStringArray() {
        String[] result = new String[size()];
        for (int i = 0; i < size(); i++) {
            result[i] = dateTimeColumnFormatter.format(getValues()[i]);
        }
        return result;
    }

    public String[] asStringArray(String pattern) {
        DateTimeColumnFormatter tempFormatter = dateTimeColumnFormatter.withPattern(pattern);
        String[] result = new String[size()];
        for (int i = 0; i < size(); i++) {
            result[i] = tempFormatter.format(getValues()[i]);
        }
        return result;
    }

    public DateTimeColumn addInputFormat(String pattern) {
        this.parser = parser.withFormat(pattern);
        return this;
    }

    public DateTimeColumn addInputFormat(DateTimeFormatter formatter) {
        this.parser = parser.withFormat(formatter);
        return this;
    }

    public DateTimeColumn addInputFormats(List<DateTimeFormatter> formats) {
        this.parser = parser.withFormats(formats);
        return this;
    }

    public DateTimeColumn setInputLocale(Locale locale) {
        this.parser = parser.withLocale(locale);
        return this;
    }

    public DateTimeColumn setStrict(boolean strict) {
        this.parser = parser.withStrict(strict);
        return this;
    }

    public DateTimeColumn setParser(DateTimeParser parser) {
        this.parser = parser != null ? parser : ColumnTypes.DATE_TIME_COLUMN_TYPE.getParser();
        return this;
    }

    public DateTimeColumn setOutputFormat(String pattern) {
        this.dateTimeColumnFormatter = dateTimeColumnFormatter.withPattern(pattern);
        return this;
    }

    public DateTimeColumn setOutputFormatter(DateTimeColumnFormatter formatter) {
        this.dateTimeColumnFormatter = formatter != null ? formatter : DateTimeColumnFormatter.getDefault();
        return this;
    }

    public DateTimeColumn setOutputLocale(Locale locale) {
        this.dateTimeColumnFormatter = dateTimeColumnFormatter.withLocale(locale);
        return this;
    }

    public DateTimeColumn setUseOrdinal(boolean useOrdinal) {
        this.dateTimeColumnFormatter = dateTimeColumnFormatter.withOrdinal(useOrdinal);
        return this;
    }

    public DateTimeColumnFormatter getDateTimeColumnFormatter() {
        return dateTimeColumnFormatter;
    }

    public DateTimeFormatter getOutputFormatter() {
        return dateTimeColumnFormatter.getFormatter();
    }

    @Override
    public DateTimeParser getColumnParser() {
        return (DateTimeParser) super.getColumnParser();
    }

    public Locale getOutputLocale() {
        return dateTimeColumnFormatter.getLocale();
    }

    public boolean isUsingOrdinal() {
        return dateTimeColumnFormatter.isUsingOrdinal();
    }
}
