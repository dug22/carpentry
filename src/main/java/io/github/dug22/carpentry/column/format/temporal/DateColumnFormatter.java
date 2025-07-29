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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateColumnFormatter extends AbstractTemporalFormatter<LocalDate> {

    private static final DateColumnFormatter DEFAULT = new DateColumnFormatter(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            Locale.getDefault(),
            false
    );

    private DateColumnFormatter(DateTimeFormatter formatter, Locale locale, boolean useOrdinal) {
        super(formatter, locale, useOrdinal);
    }

    public static DateColumnFormatter getDefault() {
        return DEFAULT;
    }

    public DateColumnFormatter withPattern(String pattern) {
        return new DateColumnFormatter(DateTimeFormatter.ofPattern(pattern), getLocale(), isUsingOrdinal());
    }

    public DateColumnFormatter withLocale(Locale locale) {
        return new DateColumnFormatter(getFormatter(), locale, isUsingOrdinal());
    }

    public DateColumnFormatter withOrdinal(boolean useOrdinal) {
        return new DateColumnFormatter(getFormatter(), getLocale(), isUsingOrdinal());
    }
}