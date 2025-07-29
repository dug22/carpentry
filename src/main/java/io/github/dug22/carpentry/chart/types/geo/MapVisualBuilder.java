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

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.chart.AbstractChartBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapVisualBuilder extends AbstractChartBuilder<MapVisualBuilder, MapVisualProperties> {

    private final DataFrame dataFrame;
    private WorldMapTemplate worldMapTemplate;
    private String coordinatePrefixColumn;
    private String coordinatesColumn;
    private String title;
    private String outputFile;

    public MapVisualBuilder(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    public static MapVisualBuilder create(DataFrame dataFrame) {
        return new MapVisualBuilder(dataFrame);
    }

    public MapVisualBuilder setWorldMapTemplate(WorldMapTemplate worldMapTemplate) {
        this.worldMapTemplate = worldMapTemplate;
        return this;
    }

    public MapVisualBuilder plotPoints(String coordinateColumn) {
        this.coordinatesColumn = coordinateColumn;
        return this;
    }

    public MapVisualBuilder setPlotPointsPrefix(String coordinatePrefixColumn) {
        this.coordinatePrefixColumn = coordinatePrefixColumn;
        return this;
    }

    public MapVisualBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public MapVisualBuilder setOutputFile(String outputFile) {
        this.outputFile = outputFile;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapVisualBuilder build() {
        Objects.requireNonNull(dataFrame, "DataFrame must not be null!");

        if (!dataFrame.containsColumn(coordinatesColumn)) {
            throw new IllegalArgumentException("Coordinates column '" + coordinatesColumn + "' not found in DataFrame");
        }

        if (!dataFrame.containsColumn(coordinatePrefixColumn)) {
            throw new IllegalArgumentException("Label Prefix column '" + coordinatePrefixColumn + "' not found in DataFrame");
        }

        List<GeoPoint> geoPoints = new ArrayList<>();
        Object[] coordinateValues = dataFrame.getColumn(coordinatesColumn).getValues();
        Object[] prefixValues = dataFrame.getColumn(coordinatePrefixColumn).getValues();

        if (coordinateValues.length != prefixValues.length) {
            throw new IllegalArgumentException("Geo column and prefix column must have the same number of rows");
        }

        for (int i = 0; i < coordinateValues.length; i++) {
            String coordinateString = coordinateValues[i] != null ? coordinateValues[i].toString() : "";
            String coordinatePrefix = prefixValues[i] != null ? prefixValues[i].toString() : "";
            String[] coord = coordinateString.split(",");
            if (coord.length != 2) {
                throw new IllegalArgumentException("Invalid geopoint format at index " + i + ": " + coordinateString);
            }
            try {
                double latitude = Double.parseDouble(coord[0].trim());
                double longitude = Double.parseDouble(coord[1].trim());
                if (latitude < -90 || latitude > 90) {
                    throw new IllegalArgumentException("Invalid latitude value at index " + i + ": " + latitude);
                }
                if (longitude < -180 || longitude > 180) {
                    throw new IllegalArgumentException("Invalid longitude value at index " + i + ": " + longitude);
                }
                geoPoints.add(new GeoPoint(coordinateString, coordinatePrefix));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number format in geopoint at index " + i + ": " + coordinateString);
            }
        }

        if (geoPoints.isEmpty()) {
            throw new IllegalArgumentException("No valid geopoints created from DataFrame");
        }

        MapVisualProperties properties = getProperties();
        properties.setGeoPoints(geoPoints);

        MapVisual chart = new MapVisual(properties);
        String html = chart.build();
        chart.save(getOutputFile(), html);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MapVisualProperties getProperties() {
        MapVisualProperties properties = new MapVisualProperties();
        if (title != null) properties.setTitle(title);
        if (worldMapTemplate != null) properties.setWorldMapTemplate(worldMapTemplate);
        return properties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOutputFile() {
        return outputFile != null ? outputFile : "map_visual.html";
    }
}
