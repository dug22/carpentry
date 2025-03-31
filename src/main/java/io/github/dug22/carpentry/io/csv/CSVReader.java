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

package io.github.dug22.carpentry.io.csv;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.io.SourceReader;
import io.github.dug22.carpentry.io.TypeInference;
import io.github.dug22.carpentry.io.type.ColumnType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVReader extends SourceReader {

    private final OptionalCSVHeaders headers;
    private final String delimiter;
    private final boolean inferTypes;

    /**
     * Constructs a CSVReader with the given parameters.
     *
     * @param filePath file path or null
     * @param urlString URL or null
     * @param headers CSV headers or null
     * @param delimiter field separator
     * @param inferTypes whether to infer column types
     */
    private CSVReader(Path filePath, String urlString, OptionalCSVHeaders headers, String delimiter, boolean inferTypes) {
        super(filePath, urlString);
        this.headers = headers;
        this.delimiter = delimiter;
        this.inferTypes = inferTypes;
    }

    /**
     * Creates a CSVReader from a file path.
     *
     * @param filePath source file path
     * @param headers CSV headers or null
     * @param delimiter field separator
     * @param inferTypes whether to infer column types
     * @return new CSVReader instance
     */
    public static CSVReader fromFile(Path filePath, OptionalCSVHeaders headers, String delimiter, boolean inferTypes) {
        return new CSVReader(filePath, null, headers, delimiter, inferTypes);
    }

    /**
     * Creates a CSVReader from a URL.
     *
     * @param urlString source URL
     * @param headers CSV headers or null
     * @param delimiter field separator
     * @param inferTypes whether to infer column types
     * @return new CSVReader instance
     */
    public static CSVReader fromURL(String urlString, OptionalCSVHeaders headers, String delimiter, boolean inferTypes) {
        return new CSVReader(null, urlString, headers, delimiter, inferTypes);
    }

    /**
     * Loads CSV data into a DataFrame.
     *
     * @return loaded DataFrame
     * @throws IOException if I/O error occurs
     * @throws URISyntaxException if URL is malformed
     */
    @Override
    public DefaultDataFrame load() throws IOException, URISyntaxException {
        validateSource();
        if (filePath != null) {
            return loadCSVFromFile(filePath, headers, delimiter, inferTypes);
        } else {
            return loadCSVFromURL(urlString, headers, delimiter, inferTypes);
        }
    }

    /**
     * Loads CSV from a file into a DataFrame.
     *
     * @param filePath source file path
     * @param headers CSV headers or null
     * @param delimiter field separator
     * @param inferTypes whether to infer column types
     * @return loaded DataFrame
     * @throws IOException if I/O error occurs
     */
    private DefaultDataFrame loadCSVFromFile(Path filePath, OptionalCSVHeaders headers, String delimiter, boolean inferTypes) throws IOException {
        BufferedReader reader = Files.newBufferedReader(filePath);
        return loadCSV(reader, headers, delimiter, inferTypes);
    }

    /**
     * Loads CSV from a URL into a DataFrame.
     *
     * @param urlString source URL
     * @param headers CSV headers or null
     * @param delimiter field separator
     * @param inferTypes whether to infer column types
     * @return loaded DataFrame
     * @throws IOException if I/O error occurs
     * @throws URISyntaxException if URL is malformed
     */
    private DefaultDataFrame loadCSVFromURL(String urlString, OptionalCSVHeaders headers, String delimiter, boolean inferTypes) throws IOException, URISyntaxException {
        URI uri = new URI(urlString);
        URL url = uri.toURL();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        return loadCSV(reader, headers, delimiter, inferTypes);
    }

    /**
     * Loads CSV data from a reader into a DataFrame.
     *
     * @param reader input reader
     * @param headers CSV headers or null
     * @param delimiter field separator
     * @param inferTypes whether to infer column types
     * @return loaded DataFrame
     * @throws IOException if I/O error occurs
     */
    @SuppressWarnings("unchecked")
    private DefaultDataFrame loadCSV(BufferedReader reader, OptionalCSVHeaders headers, String delimiter, boolean inferTypes) throws IOException {
        String[] csvHeaders;
        if (headers != null && !headers.getHeaders().isEmpty()) {
            csvHeaders = headers.getHeaders().stream()
                    .map(OptionalCSVHeader::headerName)
                    .toArray(String[]::new);
            reader.readLine();
        } else {
            csvHeaders = reader.readLine().split(delimiter);
        }

        List<String[]> rows = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            rows.add(parseCsvLine(line, delimiter));
        }
        reader.close();

        ColumnMap columnMap = new ColumnMap();

        if (inferTypes) {
            ColumnType<?>[] columnTypes = TypeInference.inferColumnTypes(rows, csvHeaders.length);

            for (int i = 0; i < csvHeaders.length; i++) {
                AbstractColumn<?> column = TypeInference.createColumnInstance(csvHeaders[i], columnTypes[i].getColumnType());
                columnMap.addColumn(column);
            }

            for (String[] row : rows) {
                for (int i = 0; i < csvHeaders.length; i++) {
                    AbstractColumn<Object> column = (AbstractColumn<Object>) columnMap.getColumn(csvHeaders[i]);
                    Object value = columnTypes[i].parse(row[i]);
                    column.append(value);
                }
            }
        } else if (headers != null) {
            for (OptionalCSVHeader csvHeader : headers.getHeaders()) {
                AbstractColumn<?> column = TypeInference.createColumnInstance(csvHeader.headerName(), csvHeader.headerType());
                columnMap.addColumn(column);
            }

            for (String[] row : rows) {
                for (int i = 0; i < csvHeaders.length; i++) {
                    for (OptionalCSVHeader header : headers.getHeaders()) {
                        if (csvHeaders[i].equals(header.headerName())) {
                            AbstractColumn<Object> column = (AbstractColumn<Object>) columnMap.getColumn(header.headerName());
                            Object value = TypeInference.parseValue(row[i], header.headerType());
                            column.append(value);
                            break;
                        }
                    }
                }
            }
        }

        return DefaultDataFrame.create(columnMap);
    }

    /**
     * Parses a CSV line into an array of strings.
     *
     * @param line input line
     * @param delimiter field separator
     * @return array of parsed values
     */
    private String[] parseCsvLine(String line, String delimiter) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == delimiter.charAt(0) && !inQuotes) {
                result.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(sb.toString().trim());
        return result.toArray(new String[0]);
    }
}