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

package io.github.dug22.carpentry.io.json;

import java.nio.file.Path;
import java.util.List;

public class JSONReaderBuilder {

    private Path filePath;
    private String urlString;
    private JSONHeaders headers;
    private List<String> rowKeys;

    public JSONReaderBuilder setFilePath(Path filePath) {
        this.filePath = filePath;
        return this;
    }

    public JSONReaderBuilder setURL(String urlString) {
        this.urlString = urlString;
        return this;
    }

    public JSONReaderBuilder setHeaders(JSONHeaders headers) {
        this.headers = headers;
        return this;
    }

    public JSONReaderBuilder addJSONHeader(JSONHeader header){
        if(header == null){
            this.headers = JSONHeaders.of();
        }

        this.headers = this.headers.addJSONHeader(header);
        return this;
    }

    public JSONReaderBuilder setRowKeys(List<String> rowKeys) {
        this.rowKeys = rowKeys;
        return this;
    }

    public JSONReader build() {
        if (headers == null || headers.getHeaders().isEmpty()) {
            throw new JSONException("Headers must be provided");
        }

        if (filePath != null) {
            return JSONReader.fromFile(filePath, headers, rowKeys);
        } else if (urlString != null) {
            return JSONReader.fromURL(urlString, headers, rowKeys);
        } else {
            throw new JSONException("Either file path or URL must be provided!");
        }
    }
}


