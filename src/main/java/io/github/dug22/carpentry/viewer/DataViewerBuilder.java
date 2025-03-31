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

package io.github.dug22.carpentry.viewer;

import io.github.dug22.carpentry.DefaultDataFrame;

public class DataViewerBuilder {

    private DefaultDataFrame dataFrame;
    private String title = "Data Viewer";
    private int width = 800;
    private int height = 600;

    public DataViewerBuilder(DefaultDataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }


    public DataViewerBuilder setDataFrame(DefaultDataFrame dataFrame) {
        this.dataFrame = dataFrame;
        return this;
    }

    public DataViewerBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public DataViewerBuilder setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public DataViewer build() {
        if (this.dataFrame != null) {
            return new DataViewer(this.title, this.width, this.height, this.dataFrame);
        } else {
            throw new DataViewerException("There was an issue creating your Data Viewer! Your DataFrame seems to be null");
        }
    }
}
