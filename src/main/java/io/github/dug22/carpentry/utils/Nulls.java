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

package io.github.dug22.carpentry.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Nulls {

    private static final Set<String> NULL_VALUES = new HashSet<>(Arrays.asList(
            "", " ", "NA", "NaN", "N/A", "null", "NULL", "-", "--", ".", "?", "na","n/a","nan"
    ));

    private static final Map<Class<?>, Object> DEFAULT_NULLS = new HashMap<>();

    static {
        DEFAULT_NULLS.put(Boolean.class, null);
        DEFAULT_NULLS.put(Byte.class, Byte.MIN_VALUE);
        DEFAULT_NULLS.put(Character.class, '*');
        DEFAULT_NULLS.put(Double.class, Double.NaN);
        DEFAULT_NULLS.put(Float.class, Float.NaN);
        DEFAULT_NULLS.put(Integer.class, Integer.MIN_VALUE);
        DEFAULT_NULLS.put(LocalDate.class, LocalDate.of(1900, 1, 1));
        DEFAULT_NULLS.put(LocalDateTime.class, LocalDateTime.of(1900, 1, 1, 0, 0, 0));
        DEFAULT_NULLS.put(Long.class, Long.MIN_VALUE);
        DEFAULT_NULLS.put(Short.class, Short.MIN_VALUE);
        DEFAULT_NULLS.put(String.class, "NA");
    }

    public static boolean isNull(Object value) {
        return switch (value) {
            case null -> true;
            case Double d when d.isNaN() -> true;
            case Float f when f.isNaN() -> true;
            case String s -> NULL_VALUES.contains(s.trim()); // trim to normalize
            default -> false;
        };
    }

    @SuppressWarnings("unchecked")
    public static <T> T ofDefault(T value, Class<?> type) {
        return isNull(value) ? (T) getDefaultNullValue(type) : value;
    }

    public static <T> T getDefaultNullValue(Class<T> type) {
        return type.cast(DEFAULT_NULLS.get(type));
    }

    public static void addCustomNull(String value) {
        NULL_VALUES.add(value.trim());
    }

    public static void removeCustomNull(String value) {
        NULL_VALUES.remove(value.trim());
    }

    public static <T> void registerDefaultNull(Class<T> type, T defaultValue) {
        DEFAULT_NULLS.put(type, defaultValue);
    }
}
