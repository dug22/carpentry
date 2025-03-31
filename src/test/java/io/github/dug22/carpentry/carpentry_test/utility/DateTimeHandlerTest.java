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

package io.github.dug22.carpentry.carpentry_test.utility;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.Temporal;

import static io.github.dug22.carpentry.utility.DateTimeHandler.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateTimeHandlerTest {

    @Test
    public void parseDateTimeTest() {
        assertEquals(LocalDate.of(2025, 3, 10), parseDateTime("10/03/2025"));
        assertEquals(LocalDate.of(2025, 3, 10), parseDateTime("2025-03-10"));
        assertEquals(LocalDateTime.of(2025, 3, 10, 14, 30), parseDateTime("2025-03-10 14:30"));
        assertEquals(LocalDateTime.of(2025, 3, 10, 14, 30, 45), parseDateTime("2025-03-10 14:30:45"));
        assertEquals(OffsetDateTime.parse("2025-03-10T14:30:45+00:00"), parseDateTime("2025-03-10T14:30:45+00:00"));
    }

    @Test
    public void compareDatesTest() {
        Temporal date1 = LocalDate.of(2025, 3, 10);
        Temporal date2 = LocalDate.of(2025, 3, 11);
        assertTrue(compareDates(date1, date2) < 0);
        Temporal dateTime1 = LocalDateTime.of(2025, 3, 10, 14, 0);
        Temporal dateTime2 = LocalDateTime.of(2025, 3, 10, 16, 0);
        assertTrue(compareDates(dateTime1, dateTime2) < 0);
        Temporal offsetDateTime1 = OffsetDateTime.parse("2025-03-10T14:00:00+00:00");
        Temporal offsetDateTime2 = OffsetDateTime.parse("2025-03-10T16:00:00+00:00");
        assertTrue(compareDates(offsetDateTime1, offsetDateTime2) < 0);
    }

    @Test
    public void toLocalDateTimeTest() {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse("2025-03-10T14:00:00+00:00");
        LocalDateTime expected = LocalDateTime.of(2025, 3, 10, 14, 0);
        assertEquals(expected, toLocalDateTime(offsetDateTime));
        LocalDate localDate = LocalDate.of(2025, 3, 10);
        assertEquals(LocalDateTime.of(2025, 3, 10, 0, 0), toLocalDateTime(localDate));
    }

    @Test
    public void toLocalDateTest() {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse("2025-03-10T14:00:00+00:00");
        LocalDate expected = LocalDate.of(2025, 3, 10);
        assertEquals(expected, toLocalDate(offsetDateTime));
        LocalDateTime localDateTime = LocalDateTime.of(2025, 3, 10, 14, 0);
        assertEquals(expected, toLocalDate(localDateTime));
    }
}
