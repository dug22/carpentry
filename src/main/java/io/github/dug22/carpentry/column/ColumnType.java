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

package io.github.dug22.carpentry.column;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ColumnType {

    /**
     * Column Type IDs
     */
    enum ID {
        Boolean,
        Byte,
        Character,
        Date,
        DateTime,
        Double,
        Float,
        Integer,
        Long,
        Short,
        String,
    }

    /**
     * Gets the ID of the given column type
     * @return the ID of the given column type
     */
    ID getID();

    /**
     * Gets the name of the given column type
     * @return the name of the given column type
     */
    String getName();

    /**
     * Maps a given Java class to a corresponding ColumnType.ID enum value.
     * This method uses pattern matching to determine the type identity,
     * supporting various primitive wrapper types and date/time classes.
     *
     * @param clazz the Class object representing a Java type
     * @return the associated ColumnType.ID for the provided class
     * @throws IllegalStateException if the class type is not recognized
     */
    static ColumnType.ID of(Class<?> clazz) {
        return switch (clazz) {
            case Class<?> booleanClazz when booleanClazz == Boolean.class -> ID.Boolean;
            case Class<?> byteClazz when byteClazz == Byte.class -> ID.Byte;
            case Class<?> charClazz when charClazz == Character.class -> ID.Character;
            case Class<?> localDateClazz when localDateClazz == LocalDate.class -> ID.Date;
            case Class<?> localDateTimeClazz when localDateTimeClazz == LocalDateTime.class -> ID.DateTime;
            case Class<?> doubleClazz when doubleClazz == Double.class -> ID.Double;
            case Class<?> floatClazz when floatClazz == Float.class -> ID.Float;
            case Class<?> intClazz when intClazz == Integer.class -> ID.Integer;
            case Class<?> longClazz when longClazz == Long.class -> ID.Long;
            case Class<?> shortClazz when shortClazz == Short.class -> ID.Short;
            case Class<?> stringClazz when stringClazz == String.class -> ID.String;
            default -> throw new IllegalStateException("Unexpected value: " + clazz);
        };
    }


    Class<?> getClassType();
}