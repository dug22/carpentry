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

package io.github.dug22.carpentry.io.csv;

import io.github.dug22.carpentry.DataFrameInterface;
import io.github.dug22.carpentry.io.AbstractDataSource;
import io.github.dug22.carpentry.io.DataReader;
import io.github.dug22.carpentry.io.exceptions.CsvException;

import java.io.IOException;

public class CsvReader implements DataReader<CsvReadingProperties> {

    /**
     * CSV DataFrame loader implementation.
     * Loads CSV data into a DataFrame using default or custom properties.
     */
    @Override
    public DataFrameInterface load(AbstractDataSource dataSource) {
        CsvReadingProperties properties = new CsvReadingProperties()
                .setSource(dataSource)
                .hasHeaders(true)
                .allowDuplicateColumnNames(false)
                .setMaxColumnCharacterLength(3096)
                .setDelimiter(',')
                .setQuoteCharacter('"')
                .setEscapeCharacter('\\')
                .build();
        return load(properties);
    }

    /**
     * Loads a CSV into a DataFrame using the provided reading properties.
     * @param properties the CSV reading configuration
     * @return the loaded DataFrame
     */
    @Override
    public DataFrameInterface load(CsvReadingProperties properties) {
        try {
            return CsvReadingUtil.loadCsv(properties);
        } catch (IOException e) {
            throw new CsvException("Something went wrong trying to read the given csv file!");
        }
    }
}