package io.github.dug22.carpentry.fill;

import io.github.dug22.carpentry.DataFrame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DataFrameFillTest {

    private DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/palmer%20penguins.csv");

    @Test
    public void fillNATest() {
        dataFrame = dataFrame.fillNa(new FillColumnValuePair[]{
                new FillColumnValuePair("bill_length_mm", -1.0),
                new FillColumnValuePair("bill_depth_mm", -1.0),
                new FillColumnValuePair("flipper_length_mm", -1),
                new FillColumnValuePair("body_mass_g", -1),
                new FillColumnValuePair("sex", "Not specified")
        });
        dataFrame.head();

        assertAll(
                () -> assertFalse(dataFrame.getColumn("bill_length_mm").contains(null)),
                () -> assertFalse(dataFrame.getColumn("bill_depth_mm").contains(null)),
                () -> assertFalse(dataFrame.getColumn("flipper_length_mm").contains(null)),
                () -> assertFalse(dataFrame.getColumn("body_mass_g").contains(null)),
                () -> assertFalse(dataFrame.stringColumn("sex").contains("NA")
                ));
    }
}