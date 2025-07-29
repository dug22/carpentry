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

package io.github.dug22.carpentry.column;

import io.github.dug22.carpentry.column.impl.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ColumnUtils {

    public static String format(Object cell, Column<?> column) {
        if (cell == null) return "";

        return switch (column) {
            case DateTimeColumn dateTimeColumn when cell instanceof LocalDateTime localDateTime -> {
                try {
                    yield localDateTime.format(dateTimeColumn.getOutputFormatter());
                } catch (DateTimeParseException | IllegalArgumentException e) {
                    yield localDateTime.toString();
                }
            }

            case DateColumn dateColumn when cell instanceof LocalDate localDate -> {
                try {
                    yield localDate.format(dateColumn.getOutputFormatter());
                } catch (DateTimeParseException | IllegalArgumentException e) {
                    yield localDate.toString();
                }
            }

            default -> String.valueOf(cell);
        };
    }
}
