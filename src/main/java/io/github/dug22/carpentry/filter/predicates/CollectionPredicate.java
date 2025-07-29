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

package io.github.dug22.carpentry.filter.predicates;

import io.github.dug22.carpentry.filter.FilterPredicate;

import java.util.Collection;

public class CollectionPredicate {

    private final String columnName;

    public CollectionPredicate(String columnName) {
        this.columnName = columnName;
    }

    public FilterPredicate in(Collection<?> values) {
        return row -> values.contains(row.get(columnName));
    }

    public FilterPredicate notIn(Collection<?> values) {
        return row -> !values.contains(row.get(columnName));
    }
}