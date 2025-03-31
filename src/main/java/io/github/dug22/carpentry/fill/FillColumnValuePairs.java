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

package io.github.dug22.carpentry.fill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record FillColumnValuePairs(List<FillColumnValuePair> columnValuePairs) {

    /**
     * Returns an unmodifiable list of column-value pairs.
     *
     * @return an unmodifiable list of FillColumnValuePair objects
     */
    @Override
    public List<FillColumnValuePair> columnValuePairs() {
        return Collections.unmodifiableList(columnValuePairs);
    }

    /**
     * Creates a new instance of FillColumnValuePairs from an array of FillColumnValuePair objects.
     *
     * @param columnValuePairs the column-value pairs to be included in the new FillColumnValuePairs object
     * @return a new FillColumnValuePairs instance containing the provided column-value pairs
     */
    public static FillColumnValuePairs of(FillColumnValuePair... columnValuePairs) {
        List<FillColumnValuePair> columnValuePairsList = new ArrayList<>();
        Collections.addAll(columnValuePairsList, columnValuePairs);
        return new FillColumnValuePairs(columnValuePairsList);
    }
}