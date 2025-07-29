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

import io.github.dug22.carpentry.chart.ChartProperties;

import java.util.ArrayList;
import java.util.List;

public class MapVisualProperties extends ChartProperties {

    private String title;
    private WorldMapTemplate worldMapTemplate = WorldMapTemplate.STANDARD_WORLD_MAP;
    private List<GeoPoint> geoPoints = new ArrayList<>();

    public MapVisualProperties(){

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setGeoPoints(List<GeoPoint> geoPoints) {
        this.geoPoints = geoPoints;
    }

    public List<GeoPoint> getGeoPoints() {
        return geoPoints;
    }

    public void setWorldMapTemplate(WorldMapTemplate worldMapTemplate) {
        this.worldMapTemplate = worldMapTemplate;
    }

    public WorldMapTemplate getWorldMapTemplate() {
        return worldMapTemplate;
    }
}
