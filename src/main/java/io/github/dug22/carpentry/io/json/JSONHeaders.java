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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JSONHeaders {

    private final List<JSONHeader> headers;

    public JSONHeaders(List<JSONHeader> headers) {
        this.headers = headers;
    }

    public List<JSONHeader> getHeaders() {
        return Collections.unmodifiableList(headers);
    }

    public static JSONHeaders of(JSONHeader... headers) {
        List<JSONHeader> headerList = new ArrayList<>();
        Collections.addAll(headerList, headers);
        return new JSONHeaders(headerList);
    }

    public JSONHeaders addJSONHeader(JSONHeader header) {
        List<JSONHeader> newHeaders = new ArrayList<>(this.headers);
        newHeaders.add(header);
        return new JSONHeaders(newHeaders);
    }
}
