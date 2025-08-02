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
import io.github.dug22.carpentry.io.DataLocation;
import io.github.dug22.carpentry.io.DataWriter;
import io.github.dug22.carpentry.io.exceptions.CsvException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class CsvWriter implements DataWriter<CsvWritingProperties> {

    /**
     * Writes a DataFrame to a given DataLocation using default CSV writing properties.
     * @param dataFrame the DataFrame to write
     * @param location the output location
     */
    @Override
    public void write(DataFrameInterface dataFrame, DataLocation location) {
        write(dataFrame, new CsvWritingProperties().setLocation(location).build());
    }


    /**
     * Writes a DataFrame to a location using the specified CsvWritingProperties.
     * Handles headers, data, and resource cleanup.
     * @param dataFrame the DataFrame to write
     * @param properties the writing properties
     */
    @Override
    public void write(DataFrameInterface dataFrame, CsvWritingProperties properties) {
        Writer writer = null;
        try {
            writer = getWriter(properties);
            CsvWritingUtil.writeHeaders(dataFrame, properties, writer);
            CsvWritingUtil.writeDataValues(dataFrame, properties, writer);
            if (!properties.isAutoClosable()) {
                writer.flush();
            } else {
                writer.close();
            }
        } catch (IOException e) {
            throw new CsvException("Error writing csv file: " + e.getMessage(), e);
        } finally {
            if (writer != null && properties.isAutoClosable()) {
                try {
                    writer.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Retrieves the Writer from the properties' location.
     * Prioritizes Writer if available, otherwise wraps the OutputStream.
     * @param properties the writing properties
     * @return a Writer for writing the CSV output
     */
    @Override
    public Writer getWriter(CsvWritingProperties properties) {
        DataLocation location = properties.getLocation();
        return location.writer() != null ? location.writer() : new OutputStreamWriter(location.stream());
    }
}