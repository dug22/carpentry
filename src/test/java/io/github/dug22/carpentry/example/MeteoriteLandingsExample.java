package io.github.dug22.carpentry.example;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.chart.types.geo.MapVisualBuilder;
import io.github.dug22.carpentry.chart.types.geo.WorldMapTemplate;
import io.github.dug22.carpentry.column.impl.StringColumn;

public class MeteoriteLandingsExample extends AbstractExample {

    public static void main(String[] args) {
        out("=== Meteorite Landings Analysis ===");
        out();
        out("Let us start by creating a dataframe object and load the dataset from the following link: https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/meteorite_landings.csv");
        DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/meteorite_landings.csv");
        out();
        out("Let's first clean our dataset and drop rows with missing coordinates, years, and mass");
        dataFrame = dataFrame.dropNa("reclat", "reclong", "mass (g)", "year");
        dataFrame = dataFrame.query("(`reclong` <= `180` AND `reclong` >= `-180`) AND (`reclat` != `0` OR `reclong` != `0`)");
        out();
        out("Information about our dataframe after cleaning it");
        dataFrame.info();
        out();
        out("Displaying the first 5 rows");
        dataFrame.head();
        out("Let's filter meteorites discovered in the most recent century. First, we need to find the maximum recorded year.");
        double maxYear = dataFrame.doubleColumn("year").max();
        out("Max Year: " + maxYear);
        out();
        out("Now to filter between the year 1913 to 2013");
        dataFrame = dataFrame.query("`year` >= `1913` AND `year` <= `2013`");
        dataFrame.head();
        out();
        out("Let's plot the latitude and longitude of where these meteorites were discovered. We can use Carpentry's built in MapVisualizer");
        out();
        out("But first we need to make modifications to the GeoLocation column. We must remove the parenthesis");
        StringColumn geoLocationColumn = dataFrame.stringColumn("GeoLocation");
        StringColumn newGeoLocationColumn = geoLocationColumn.emptyCopy();
        for(int index = 0; index < geoLocationColumn.size(); index++){
            String geoPoint = geoLocationColumn.get(index);
            geoPoint = geoPoint.replace("(", "");
            geoPoint = geoPoint.replace(")", "");
            newGeoLocationColumn.append(geoPoint);
        }
        out();
        out("Let's take a look at the column");
        newGeoLocationColumn.head();
        out();
        out("It looks good now we can visualize where these meteorites landed.");
        int geoLocationIndex = dataFrame.getColumnIndex("GeoLocation");
        dataFrame = dataFrame.replaceColumn(geoLocationIndex, newGeoLocationColumn);
        MapVisualBuilder meteoriteLandingsVisual = MapVisualBuilder.create(dataFrame)
                .setPlotPointsPrefix("name")
                .plotPoints(geoLocationColumn.name())
                .setWorldMapTemplate(WorldMapTemplate.DELTA_BLUE_WORLD_MAP)
                .setTitle("Meteorite Landings 1913-2013")
                .setOutputFile("meteorite-landings.html")
                .build();
        meteoriteLandingsVisual.display();
        out();
        out("Let's export out final dataframe");
        //dataFrame.write().toCsv(new File("meteorite-landings.csv"));
    }
}
