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

package io.github.dug22.carpentry.column.format.temporal;

import io.github.dug22.carpentry.column.AbstractColumnFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public abstract class AbstractTemporalFormatter<T> extends AbstractColumnFormatter {

    private final DateTimeFormatter formatter;
    private final Locale locale;
    private final boolean useOrdinal;

    public AbstractTemporalFormatter(DateTimeFormatter formatter, Locale locale, boolean useOrdinal) {
        this.formatter = Objects.requireNonNull(formatter);
        this.locale = locale != null ? locale : Locale.getDefault();
        this.useOrdinal = useOrdinal;
    }

    public String format(T instance) {
        LocalDate date;
        LocalDateTime dateTime;
        if (instance instanceof LocalDate) {
            date = (LocalDate) instance;
            String baseFormat = date.format(formatter.withLocale(locale));
            if (useOrdinal) {
                int day = date.getDayOfMonth();
                String ordinal = getOrdinalSuffix(day);
                return baseFormat.replaceFirst("\\b" + day + "\\b", day + ordinal);
            }
            return baseFormat;
        } else if (instance instanceof LocalDateTime) {
            dateTime = (LocalDateTime) instance;
            String baseFormat = dateTime.format(formatter.withLocale(locale));
            if (useOrdinal) {
                int day = dateTime.getDayOfMonth();
                String ordinal = getOrdinalSuffix(day);
                return baseFormat.replaceFirst("\\b" + day + "\\b", day + ordinal);
            }
            return baseFormat;
        }else{
            throw new IllegalArgumentException("The given instance must be an instance of LocaleDate or LocalDateTime");
        }
    }

    private String getOrdinalSuffix(int day) {
        return switch (day % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }

    public abstract AbstractTemporalFormatter<T> withPattern(String pattern);

    public abstract AbstractTemporalFormatter<T> withLocale(Locale locale);

    public abstract AbstractTemporalFormatter<T> withOrdinal(boolean useOrdinal);

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public Locale getLocale() {
        return locale;
    }

    public boolean isUsingOrdinal() {
        return useOrdinal;
    }
}
