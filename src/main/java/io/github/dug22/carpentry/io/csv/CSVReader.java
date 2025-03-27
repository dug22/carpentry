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

package io.github.dug22.carpentry.io.csv;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.AbstractColumn;
import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.io.SourceReader;
import io.github.dug22.carpentry.io.TypeInference;

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

    private final CSVHeaders headers;
    private final String delimiter;

    private CSVReader(Path filePath, String urlString, CSVHeaders headers, String delimiter) {
        super(filePath, urlString);
        this.headers = headers;
        this.delimiter = delimiter;
    }

    public static CSVReader fromFile(Path filePath, CSVHeaders headers, String delimiter) {
        return new CSVReader(filePath, null, headers, delimiter);
    }

    public static CSVReader fromURL(String urlString, CSVHeaders headers, String delimiter) {
        return new CSVReader(null, urlString, headers, delimiter);
    }

    @Override
    public DefaultDataFrame load() throws IOException, URISyntaxException {
        validateSource();
        if (headers == null || headers.getHeaders().isEmpty()) {
            throw new CSVException("Headers must be provided");
        }

        if (filePath != null) {
            return loadCSVFromFile(filePath, headers, delimiter);
        } else {
            return loadCSVFromURL(urlString, headers, delimiter);
        }
    }

    private static DefaultDataFrame loadCSVFromFile(Path filePath, CSVHeaders headers, String delimiter) throws IOException {
        BufferedReader reader = Files.newBufferedReader(filePath);
        return loadCSV(reader, headers, delimiter);
    }

    private static DefaultDataFrame loadCSVFromURL(String urlString, CSVHeaders headers, String delimiter) throws IOException, URISyntaxException {
        URI uri = new URI(urlString);
        URL url = uri.toURL();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        return loadCSV(reader, headers, delimiter);
    }

    @SuppressWarnings("unchecked")
    private static DefaultDataFrame loadCSV(BufferedReader reader, CSVHeaders headers, String delimiter) throws IOException {
        String line;
        String[] csvHeaders = reader.readLine().split(delimiter);
        List<String[]> rows = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            rows.add(parseCsvLine(line, delimiter));
        }

        reader.close();

        ColumnMap columnMap = new ColumnMap();

        for (CSVHeader csvHeader : headers.getHeaders()) {
            Class<?> columnType = csvHeader.headerType();
            AbstractColumn<?> column = TypeInference.createColumnInstance(csvHeader.headerName(), columnType);
            columnMap.addColumn(column);
        }

        for (String[] row : rows) {
            for (int i = 0; i < csvHeaders.length; i++) {
                for (CSVHeader header : headers.getHeaders()) {
                    if (csvHeaders[i].equals(header.headerName())) {
                        AbstractColumn<Object> column = (AbstractColumn<Object>) columnMap.getColumn(header.headerName());
                        Object value = TypeInference.parseValue(row[i], header.headerType());
                        column.append(value);
                        break;
                    }
                }
            }
        }

        return DefaultDataFrame.create(columnMap);
    }

    private static String[] parseCsvLine(String line, String delimiter) {
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