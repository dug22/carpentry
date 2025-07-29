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

package io.github.dug22.carpentry.column.type;

import io.github.dug22.carpentry.column.BaseColumnType;
import io.github.dug22.carpentry.column.parser.DateTimeParser;

import java.time.LocalDateTime;
import java.util.Locale;

public class DateTimeColumnType extends BaseColumnType {

    private static final DateTimeParser dateTimeParser = new DateTimeParser(Locale.getDefault(), DateTimeParser.getDefaultFormatters(), true);

    public DateTimeColumnType(){
        super(ID.Date);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "DateTime";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getClassType() {
        return LocalDateTime.class;
    }

    /**
     * Gets the column type's parser.
     * @return the column type's parser.
     */
    public DateTimeParser getParser() {
        return dateTimeParser;
    }
}
