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

package io.github.dug22.carpentry.io;

import java.io.*;

public record DataLocation(OutputStream stream, Writer writer) {

    public DataLocation(File file) {
        this(toStream(file));
    }

    public DataLocation(OutputStream stream) {
        this(stream, null);
    }

    public DataLocation(Writer writer) {
        this(null, writer);
    }

    @Override
    public OutputStream stream() {
        return stream;
    }

    @Override
    public Writer writer() {
        return writer;
    }

    private static OutputStream toStream(File file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new UncheckedIOException("File " + file.getName() + " does not exist", e);
        }
    }
}
