/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.github.dug22.carpentry.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.Temporal;

public class DateTimeHandler {

    // Formatter for various date-time formats
    public static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSXXX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
            .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"))
            .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
            .toFormatter();

    public static Temporal parseDateTime(String dateTime) {
        try {
            return OffsetDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        } catch (Exception e1) {
            try {
                return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
            } catch (Exception e2) {
                return LocalDate.parse(dateTime, DATE_TIME_FORMATTER);
            }
        }
    }

    public static int compareDates(Temporal date1, Temporal date2) {
        return switch (date1) {
            case OffsetDateTime offsetDateTime when date2 instanceof OffsetDateTime ->
                    offsetDateTime.compareTo((OffsetDateTime) date2);
            case LocalDateTime localDateTime when date2 instanceof LocalDateTime ->
                    localDateTime.compareTo((LocalDateTime) date2);
            case LocalDate localDate when date2 instanceof LocalDate -> localDate.compareTo((LocalDate) date2);
            case OffsetDateTime offsetDateTime -> offsetDateTime.toLocalDateTime().compareTo(toLocalDateTime(date2));
            case LocalDateTime localDateTime -> localDateTime.compareTo(toLocalDateTime(date2));
            case null, default -> {
                assert date1 instanceof LocalDate;
                yield ((LocalDate) date1).compareTo(toLocalDate(date2));
            }
        };
    }

    public static LocalDateTime toLocalDateTime(Temporal temporal) {
        return switch (temporal) {
            case LocalDateTime localDateTime -> localDateTime;
            case OffsetDateTime offsetDateTime -> offsetDateTime.toLocalDateTime();
            case LocalDate localDate -> localDate.atStartOfDay();
            case null, default -> throw new IllegalArgumentException("Unsupported Temporal type");
        };
    }

    public static LocalDate toLocalDate(Temporal temporal) {
        return switch (temporal) {
            case LocalDate localDate -> localDate;
            case OffsetDateTime offsetDateTime -> offsetDateTime.toLocalDate();
            case LocalDateTime localDateTime -> localDateTime.toLocalDate();
            case null, default -> throw new IllegalArgumentException("Unsupported Temporal type");
        };
    }
}
