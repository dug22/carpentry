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
import io.github.dug22.carpentry.io.csv.CsvWriter;
import io.github.dug22.carpentry.io.csv.CsvWritingProperties;

import java.io.File;
import java.io.OutputStream;

public class DataFrameWriter {

    private final DataFrame dataFrame;

    public DataFrameWriter(DataFrame dataFrame){
        this.dataFrame = dataFrame;
    }

    public void toCsv(DataLocation location){
        CsvWriter writer = new CsvWriter();
        writer.write(dataFrame, location);
    }

    public void toCsv(File file){
        toCsv(new DataLocation(file));
    }

    public void toCsv(OutputStream stream){
        toCsv(new DataLocation(stream));
    }

    public void toCsv(CsvWritingProperties properties){
        CsvWriter writer = new CsvWriter();
        writer.write(dataFrame, properties);
    }
}
