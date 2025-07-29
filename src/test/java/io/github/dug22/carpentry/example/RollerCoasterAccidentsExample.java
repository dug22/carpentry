package io.github.dug22.carpentry.example;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.aggregation.AggregationEntry;
import io.github.dug22.carpentry.aggregation.AggregationType;
import io.github.dug22.carpentry.sorting.SortColumn;

public class RollerCoasterAccidentsExample extends AbstractExample {

    public static void main(String[] args) {
        out("=== Coaster Accident Analysis ===");
        out();
        out("Let us start by creating a dataframe object and load the dataset from the following link: https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/roller-coaster-accidents.csv");
        final DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/roller-coaster-accidents.csv");

        out("\n=== DataFrame Details ===");
        dataFrame.info();
        out("\n=== First 5 Rows ===");
        dataFrame.head();

        // Total injuries per park
        out("\n=== Total Injuries Per Park ===");
        DataFrame injuriesPerPark = dataFrame.groupBy("bus_type").aggregate(new AggregationEntry("num_injured", AggregationType.SUM));
        injuriesPerPark.head();

        // Total injuries per year
        out("\n=== Total Injuries Per Year ===");
        DataFrame yearsDataframe = dataFrame.groupBy("year").aggregate(
                new AggregationEntry("num_injured", AggregationType.SUM),
                new AggregationEntry("mechanical", AggregationType.SUM),
                new AggregationEntry("op_error", AggregationType.SUM),
                new AggregationEntry("employee", AggregationType.SUM)
        );
        yearsDataframe.head();

        // Total injuries per ride type (sorted by injuries descending)
        out("\n=== Total Injuries Per Ride Type ===");
        DataFrame ridesDF = dataFrame.groupBy("device_category").aggregate(
                new AggregationEntry("num_injured", AggregationType.SUM)
        ).sort(new SortColumn[]{
                new SortColumn("num_injured_sum", SortColumn.Direction.DESCENDING)
        });
        ridesDF.head();
    }
}