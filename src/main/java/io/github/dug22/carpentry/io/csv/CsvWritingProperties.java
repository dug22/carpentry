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

import io.github.dug22.carpentry.io.DataLocation;
import io.github.dug22.carpentry.io.WritingProperties;
import io.github.dug22.carpentry.io.exceptions.CsvException;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Configuration class for writing CSV files with customizable properties.
 */
public class CsvWritingProperties implements WritingProperties {

    private DataLocation location;
    private boolean autoClosable;
    private char delimiter = ',';
    private char quoteCharacter = '"';
    private boolean includeHeaders = true;
    private String lineSeparator = System.lineSeparator();

    // Default constructor
    public CsvWritingProperties() {
    }

    /**
     * Sets the data location using a DataLocation object.
     * @param location the data location
     * @return this instance for chaining
     */
    public CsvWritingProperties setLocation(DataLocation location) {
        this.location = location;
        return this;
    }

    /**
     * Sets the data location using an OutputStream.
     * @param stream the output stream
     * @return this instance for chaining
     */
    public CsvWritingProperties setLocation(OutputStream stream) {
        this.location = new DataLocation(stream);
        return this;
    }

    /**
     * Sets the data location using a Writer.
     * @param writer the writer
     * @return this instance for chaining
     */
    public CsvWritingProperties setLocation(Writer writer) {
        this.location = new DataLocation(writer);
        return this;
    }

    /**
     * Sets the data location using a File.
     * Automatically sets autoClosable to true.
     * @param file the file
     * @return this instance for chaining
     */
    public CsvWritingProperties setLocation(File file) {
        this.location = new DataLocation(file);
        this.autoClosable = true;
        return this;
    }

    /**
     * Sets the delimiter character used in the CSV.
     * @param delimiter the delimiter character
     * @return this instance for chaining
     */
    public CsvWritingProperties setDelimiter(char delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    /**
     * Sets the character used to quote fields.
     * @param quoteCharacter the quote character
     * @return this instance for chaining
     */
    public CsvWritingProperties setQuoteCharacter(char quoteCharacter) {
        this.quoteCharacter = quoteCharacter;
        return this;
    }

    /**
     * Specifies whether to include headers in the CSV output.
     * @param includeHeaders true to include headers, false otherwise
     * @return this instance for chaining
     */
    public CsvWritingProperties includeHeaders(boolean includeHeaders) {
        this.includeHeaders = includeHeaders;
        return this;
    }

    /**
     * Sets the line separator to use between rows.
     * @param lineSeparator the line separator string
     * @return this instance for chaining
     */
    public CsvWritingProperties setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
        return this;
    }

    /**
     * Gets the data location for output.
     * @return the data location
     */
    public DataLocation getLocation() {
        return location;
    }

    /**
     * Checks whether the location should be auto-closed.
     * @return true if auto-closable, false otherwise
     */
    protected boolean isAutoClosable() {
        return autoClosable;
    }

    /**
     * Gets the delimiter character.
     * @return the delimiter
     */
    public char getDelimiter() {
        return delimiter;
    }

    /**
     * Gets the quote character.
     * @return the quote character
     */
    public char getQuoteCharacter() {
        return quoteCharacter;
    }

    /**
     * Checks whether headers should be included.
     * @return true if headers are included, false otherwise
     */
    public boolean doesIncludeHeaders() {
        return includeHeaders;
    }

    /**
     * Gets the line separator string.
     * @return the line separator
     */
    public String getLineSeparator() {
        return lineSeparator;
    }

    /**
     * Validates and finalizes the configuration.
     * @return this instance
     * @throws CsvException if location is not set
     */
    public CsvWritingProperties build() {
        if (location == null) throw new CsvException("A data location must be initialized before building!");
        return this;
    }
}

