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
import io.github.dug22.carpentry.column.parser.StringParser;

public class StringColumnType extends BaseColumnType  {

    private static final StringParser stringParser = new StringParser();

    public StringColumnType() {
        super(ID.String);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "String";
    }

    /**
     * {@inheritDoc}
     */
    public Class<String> getClassType(){
        return String.class;
    }

    /**
     * Gets the column type's parser.
     * @return the column type's parser.
     */
    public StringParser getParser() {
        return stringParser;
    }
}
