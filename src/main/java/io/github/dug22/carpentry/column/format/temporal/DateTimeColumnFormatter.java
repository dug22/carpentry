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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeColumnFormatter extends AbstractTemporalFormatter<LocalDateTime> {

    private static final DateTimeColumnFormatter DEFAULT = new DateTimeColumnFormatter(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            Locale.getDefault(),
            false
    );

    public DateTimeColumnFormatter(DateTimeFormatter formatter, Locale locale, boolean useOrdinal) {
        super(formatter, locale, useOrdinal);
    }

    public static DateTimeColumnFormatter getDefault() {
        return DEFAULT;
    }

    public DateTimeColumnFormatter withPattern(String pattern) {
        return new DateTimeColumnFormatter(DateTimeFormatter.ofPattern(pattern), getLocale(), isUsingOrdinal());
    }

    public DateTimeColumnFormatter withLocale(Locale locale) {
        return new DateTimeColumnFormatter(getFormatter(), locale, isUsingOrdinal());
    }

    public DateTimeColumnFormatter withOrdinal(boolean useOrdinal) {
        return new DateTimeColumnFormatter(getFormatter(), getLocale(), useOrdinal);
    }
}
