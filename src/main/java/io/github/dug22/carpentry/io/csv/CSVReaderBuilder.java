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

import java.nio.file.Path;

public class CSVReaderBuilder {

    private Path filePath;
    private String urlString;
    private CSVHeaders headers;
    private String delimiter = ",";

    public CSVReaderBuilder setFilePath(Path filePath) {
        this.filePath = filePath;
        return this;
    }

    public CSVReaderBuilder setURL(String urlString) {
        this.urlString = urlString;
        return this;
    }

    public CSVReaderBuilder setHeaders(CSVHeaders headers) {
        this.headers = headers;
        return this;
    }

    public CSVReaderBuilder addCSVHeader(CSVHeader header) {
        if (this.headers == null) {
            this.headers = CSVHeaders.of();
        }
        this.headers = this.headers.addCSVHeader(header);
        return this;
    }

    public CSVReaderBuilder setDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public CSVReader build() {
        if (headers == null || headers.getHeaders().isEmpty()) {
            throw new CSVException("Headers must be provided");
        }

        if (filePath != null) {
            return CSVReader.fromFile(filePath, headers, delimiter);
        } else if (urlString != null) {
            return CSVReader.fromURL(urlString, headers, delimiter);
        } else {
            throw new CSVException("Either file path or URL must be provided!");
        }
    }
}

