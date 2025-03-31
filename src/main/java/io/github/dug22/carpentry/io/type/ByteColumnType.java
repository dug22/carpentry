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

package io.github.dug22.carpentry.io.type;

public class ByteColumnType implements ColumnType<Byte> {

    @Override
    public boolean canParse(String value) {
        if (isNA(value)) return true;
        value = value.trim();
        try {
            if (value.startsWith("0x") || value.startsWith("0X")) {
                Byte.parseByte(value.substring(2), 16);
            } else if (value.startsWith("0b") || value.startsWith("0B")) {
                Byte.parseByte(value.substring(2), 2);
            } else {
                Byte.parseByte(value);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Class<Byte> getColumnType() {
        return Byte.class;
    }

    @Override
    public Byte parse(String value) {
        if (isNA(value)) return null;
        value = value.trim();
        try {
            if (value.startsWith("0x") || value.startsWith("0X")) {
                return Byte.parseByte(value.substring(2), 16);
            } else if (value.startsWith("0b") || value.startsWith("0B")) {
                return Byte.parseByte(value.substring(2), 2);
            } else {
                return Byte.parseByte(value);
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
