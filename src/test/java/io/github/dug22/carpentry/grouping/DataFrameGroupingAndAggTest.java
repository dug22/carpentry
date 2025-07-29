package io.github.dug22.carpentry.grouping;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.aggregation.AggregationEntry;
import io.github.dug22.carpentry.aggregation.AggregationType;
import io.github.dug22.carpentry.column.impl.BooleanColumn;
import io.github.dug22.carpentry.column.impl.DoubleColumn;
import io.github.dug22.carpentry.column.impl.IntegerColumn;
import io.github.dug22.carpentry.column.impl.StringColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class DataFrameGroupingAndAggTest {

    private DataFrame dataFrame;

    @BeforeEach
    public void setup() {
        dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/titanic.csv");
    }

    @Test
    public void groupAndAggTestOne() {
        DataFrame result = dataFrame.filter(dataFrame.column("Survived").eq(1)).groupBy("Sex").aggregate(new AggregationEntry("Survived", AggregationType.COUNT));
        StringColumn expectedColumnOne = StringColumn.create("Sex", new String[]{"female", "male"});
        IntegerColumn expectedColumnTwo = IntegerColumn.create("Survived_count", new Integer[]{233, 109});
        assertAll(
                () -> assertArrayEquals(expectedColumnOne.getValues(), result.getColumn("Sex").getValues()),
                () -> assertArrayEquals(expectedColumnTwo.getValues(), result.getColumn("Survived_count").getValues())
        );
    }

    @Test
    public void groupAndAggTestTwo() {
        DataFrame result = dataFrame.groupBy("Sex").aggregate(new AggregationEntry("Survived", AggregationType.MEAN));
        StringColumn expectedColumnOne = StringColumn.create("Sex", new String[]{"female", "male"});
        DoubleColumn expectedColumnTwo = DoubleColumn.create("Survived_mean", new Double[]{0.7420382165605095, 0.18890814558058924D});
        assertAll(
                () -> assertArrayEquals(expectedColumnOne.getValues(), result.getColumn("Sex").getValues()),
                () -> assertArrayEquals(expectedColumnTwo.getValues(), result.getColumn("Survived_mean").getValues())
        );
    }

    @Test
    public void groupAndAggTestThree() {
        IntegerColumn currentSurvivedColumn = dataFrame.intColumn("Survived");
        BooleanColumn newSurvivedColumn = BooleanColumn.create("Survived");
        for (int index = 0; index < currentSurvivedColumn.size(); index++) {
            Integer value = currentSurvivedColumn.get(index);
            if (value == 1) {
                newSurvivedColumn.append(true);
            } else if (value == 0) {
                newSurvivedColumn.append(false);
            }
        }

        dataFrame = dataFrame.replaceColumn(1, newSurvivedColumn);

        DataFrame result = dataFrame.groupBy("Sex").aggregate(
                new AggregationEntry("Survived", AggregationType.TRUE_COUNT),
                new AggregationEntry("Survived", AggregationType.FALSE_COUNT)
        );

        StringColumn expectedColumnOne = StringColumn.create("Sex", new String[]{"female", "male"});
        IntegerColumn expectedColumnTwo = IntegerColumn.create("Survived_true_count", new Integer[]{233, 109});
        IntegerColumn expectedColumnThree = IntegerColumn.create("Survived_false_count", new Integer[]{81, 468});

        assertAll(
                () -> assertArrayEquals(expectedColumnOne.getValues(), result.getColumn("Sex").getValues()),
                () -> assertArrayEquals(expectedColumnTwo.getValues(), result.getColumn("Survived_true_count").getValues()),
                () -> assertArrayEquals(expectedColumnThree.getValues(), result.getColumn("Survived_false_count").getValues())
        );
    }

    @Test
    public void groupAndAggTestFour() {
        DataFrame result = dataFrame.groupBy("Pclass").aggregate(
                new AggregationEntry("Fare", AggregationType.MAX));
        IntegerColumn expectedColumnOne = IntegerColumn.create("Pclass", new Integer[]{1, 2, 3});
        DoubleColumn expectedColumnTwo = DoubleColumn.create("Fare_max", new Double[]{512.3292, 73.5, 69.55});
        assertAll(
                () -> assertArrayEquals(expectedColumnOne.getValues(), result.getColumn("Pclass").getValues()),
                () -> assertArrayEquals(expectedColumnTwo.getValues(), result.getColumn("Fare_max").getValues())
        );
    }
}
