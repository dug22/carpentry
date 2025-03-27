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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CSVHeaders {

    private final List<CSVHeader> headers;

    private CSVHeaders(List<CSVHeader> headers) {
        this.headers = headers;
    }

    public List<CSVHeader> getHeaders() {
        return Collections.unmodifiableList(headers);
    }

    public static CSVHeaders of(CSVHeader... headers) {
        List<CSVHeader> headerList = new ArrayList<>();
        Collections.addAll(headerList, headers);
        return new CSVHeaders(headerList);
    }

    public CSVHeaders addCSVHeader(CSVHeader header) {
        List<CSVHeader> newHeaders = new ArrayList<>(this.headers);
        newHeaders.add(header);
        return new CSVHeaders(newHeaders);
    }
}
