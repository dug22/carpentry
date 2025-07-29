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

package io.github.dug22.carpentry.io.source;

import io.github.dug22.carpentry.io.AbstractDataSource;
import io.github.dug22.carpentry.io.exceptions.DataSourceException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.Charset;

public class URLSource extends AbstractDataSource {

    private final String link;

    public URLSource(String link, Charset charset){
        super(charset);
        this.link = link;
    }

    @Override
    public Reader getReader() {
        return new InputStreamReader(getStream(), charset);
    }

    @Override
    public InputStream getStream() {
        try{
            return URI.create(link).toURL().openStream();
        }catch (IOException e){
            throw new DataSourceException("An issue occurred!");
        }
    }

    public String getLink() {
        return link;
    }
}
