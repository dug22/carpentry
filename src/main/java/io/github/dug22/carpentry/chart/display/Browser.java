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

package io.github.dug22.carpentry.chart.display;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Browser {

    /**
     * Launches the default browser to display a given chart file.
     * @param file the html file to open.
     */
    public static void browse(File file){
        if(!file.getName().endsWith("html")){
            throw new IllegalArgumentException("File doesn't end in .html!");
        }
        try {
            if(Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(file.toURI());
            }
        } catch (IOException e) {
            throw new UnsupportedOperationException("An error has occurred! Browser not supported!", e);
        }
    }
}
