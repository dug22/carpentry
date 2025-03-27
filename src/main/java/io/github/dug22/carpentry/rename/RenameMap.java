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

package io.github.dug22.carpentry.rename;

import io.github.dug22.carpentry.utility.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RenameMap {
    private final Map<String, String> renameMap;

    public RenameMap(Map<String, String> renameMap) {
        this.renameMap = Collections.unmodifiableMap(renameMap);
    }

    @SafeVarargs
    public static RenameMap of(Pair<String, String>... columnPairs) {
        Map<String, String> map = new HashMap<>();
        for (Pair<String, String> pair : columnPairs) {
            map.put(pair.getFirst(), pair.getSecond());
        }
        return new RenameMap(map);
    }

    public String getNewName(String oldName) {
        return renameMap.get(oldName);
    }

    public boolean contains(String columnName) {
        return renameMap.containsKey(columnName);
    }

    public Set<String> getOldNames() {
        return renameMap.keySet();
    }

    public Set<Map.Entry<String, String>> entrySet() {
        return renameMap.entrySet();
    }

    public Map<String, String> asMap() {
        return renameMap;
    }
}
