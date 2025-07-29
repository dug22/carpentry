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

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.io.csv.CsvReader;
import io.github.dug22.carpentry.io.csv.CsvReadingProperties;
import io.github.dug22.carpentry.io.source.FileSource;
import io.github.dug22.carpentry.io.source.URLSource;

import java.io.File;
import java.nio.charset.Charset;

public class DataFrameReader {

    public DataFrame csv(File file){
        FileSource fileSource = DataSource.fromFile(file);
        CsvReader reader = new CsvReader();
        return (DataFrame) reader.load(fileSource);
    }

    public DataFrame csv(File file, Charset charset){
        FileSource fileSource = DataSource.fromFile(file, charset);
        CsvReader reader = new CsvReader();
        return (DataFrame) reader.load(fileSource);
    }

    public DataFrame csv(String urlLink){
        URLSource urlSource = DataSource.fromUrl(urlLink);
        CsvReader reader = new CsvReader();
        return (DataFrame) reader.load(urlSource);
    }

    public DataFrame csv(String urlLink, Charset charset){
        URLSource urlSource = DataSource.fromUrl(urlLink, charset);
        CsvReader reader = new CsvReader();
        return (DataFrame) reader.load(urlSource);
    }

    public DataFrame csv(CsvReadingProperties properties){
        CsvReader reader = new CsvReader();
        return (DataFrame) reader.load(properties);
    }
}
