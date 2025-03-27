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

package io.github.dug22.carpentry.filtering.predicates;

import io.github.dug22.carpentry.filtering.FilterPredicate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.temporal.Temporal;

import static io.github.dug22.carpentry.utility.DateTimeHandler.compareDates;
import static io.github.dug22.carpentry.utility.DateTimeHandler.parseDateTime;

public class DatePredicate {

    private final String columnName;

    public DatePredicate(String columnName){
        this.columnName = columnName;
    }

    public FilterPredicate isInMonth(Month month){
        return row -> {
            Temporal dateTime = parseDateTime((String) row.getRowData().get(columnName));
            if (dateTime instanceof OffsetDateTime) {
                return ((OffsetDateTime) dateTime).getMonth() == month;
            } else if (dateTime instanceof LocalDateTime) {
                return ((LocalDateTime) dateTime).getMonth() == month;
            } else {
                return ((LocalDate) dateTime).getMonth() == month;
            }
        };
    }

    public FilterPredicate isBefore(Temporal dateTime) {
        return row -> {
            Temporal rowDateTime = parseDateTime((String) row.getRowData().get(columnName));
            return compareDates(rowDateTime, dateTime) < 0;
        };
    }

    public FilterPredicate isAfter(Temporal dateTime) {
        return row -> {
            Temporal rowDateTime = parseDateTime((String) row.getRowData().get(columnName));
            return compareDates(rowDateTime, dateTime) > 0;
        };
    }

    public FilterPredicate isBetween(Temporal startDateTime, Temporal endDateTime) {
        return row -> {
            Temporal rowDateTime = parseDateTime((String) row.getRowData().get(columnName));
            return compareDates(rowDateTime, startDateTime) >= 0 && compareDates(rowDateTime, endDateTime) <= 0;
        };
    }
}
