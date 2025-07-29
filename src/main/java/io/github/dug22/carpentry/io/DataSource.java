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

import io.github.dug22.carpentry.io.source.FileSource;
import io.github.dug22.carpentry.io.source.URLSource;

import java.io.File;
import java.nio.charset.Charset;

public class DataSource {

    public static FileSource fromFile(File file, Charset charset) {
        return new FileSource(file, charset);
    }

    public static FileSource fromFile(File file) {
        return fromFile(file, null);
    }

    public static URLSource fromUrl(String link, Charset charset) {
        return new URLSource(link, charset);
    }

    public static URLSource fromUrl(String link) {
        return fromUrl(link, null);
    }
}
