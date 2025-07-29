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

package io.github.dug22.carpentry.column.parser;

import io.github.dug22.carpentry.column.ColumnParser;
import io.github.dug22.carpentry.utils.Nulls;

public class ShortParser implements ColumnParser<Short> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canParse(String value) {
        if (isNA(value)) return true;
        try {
            String cleanValue = removeComma(value);
            Short.parseShort(cleanValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short parse(String value) {
        if (isNA(value)) return null;
        String cleanValue = removeComma(value);
        try {
            return Short.parseShort(cleanValue);
        }catch (Exception e){
            return Nulls.getDefaultNullValue(Short.class);
        }
    }
}
