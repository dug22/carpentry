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

package io.github.dug22.carpentry.chart.types.geo;

public enum WorldMapTemplate {

    STANDARD_WORLD_MAP("standard-world-map.png",  1278.0, 674.0),
    DELTA_BLUE_WORLD_MAP("delta-blue-world-map.png", 1608, 854);

    private final String mapImageFilePath;
    private final double mapImageWidth;
    private final double mapImageHeight;

    WorldMapTemplate(String mapImageFilePath, double mapImageWidth, double mapImageHeight) {
        this.mapImageFilePath = mapImageFilePath;
        this.mapImageWidth = mapImageWidth;
        this.mapImageHeight = mapImageHeight;
    }

    public String getMapImageFilePath() {
        return mapImageFilePath;
    }

    public double getMapImageWidth() {
        return mapImageWidth;
    }

    public double getMapImageHeight() {
        return mapImageHeight;
    }
}
