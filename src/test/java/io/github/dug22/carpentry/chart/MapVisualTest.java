package io.github.dug22.carpentry.chart;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.chart.types.geo.MapVisualBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapVisualTest {

    private final DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/country_locations.csv");

    @Test
    public void mapTest() {
        MapVisualBuilder countryLocations = MapVisualBuilder.create(dataFrame)
                .setPlotPointsPrefix("Country")
                .plotPoints("LatLong") //In order to plot lat and lon cords they must look like this "lat,lon"
                .setTitle("Country Locations")
                .setOutputFile("countries.html")
                .build();
        assertTrue(new File(countryLocations.getOutputFile()).exists());
        //countryLocations.display();
        //If you want to choose a world map template you can do  .setWorldMapTemplate(WorldMapTemplate.DELTA_BLUE_WORLD_MAP); by default it is set to DEFAULT_WORLD_MAP.
    }
}
