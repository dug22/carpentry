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

package io.github.dug22.carpentry.carpentry_test.grouping;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.columns.IntegerColumn;
import io.github.dug22.carpentry.columns.StringColumn;
import io.github.dug22.carpentry.grouping.aggregation.AggregationType;
import io.github.dug22.carpentry.grouping.aggregation.Aggregations;
import io.github.dug22.carpentry.io.csv.OptionalCSVHeader;
import io.github.dug22.carpentry.io.csv.OptionalCSVHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.dug22.carpentry.filtering.FilterPredicateCondition.both;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataFrameGroupingTest {

    private DefaultDataFrame dataFrame;

    private final OptionalCSVHeaders headers = OptionalCSVHeaders.of(
            new OptionalCSVHeader("species", String.class),
            new OptionalCSVHeader("island", String.class),
            new OptionalCSVHeader("bill_length_mm", Double.class),
            new OptionalCSVHeader("bill_depth_mm", Double.class),
            new OptionalCSVHeader("flipper_length_mm", Double.class),
            new OptionalCSVHeader("body_mass_g", Double.class),
            new OptionalCSVHeader("sex", String.class),
            new OptionalCSVHeader("year", Integer.class)
    );

    @BeforeEach
    public void setUp() {
        dataFrame = DefaultDataFrame.load("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/palmer%20penguins.csv", headers);
    }

    @Test
    public void groupByTest() {
        DefaultDataFrame result = dataFrame.groupBy(List.of("species", "sex"));
        String[] expectedColumns = new String[]{"species",  "sex", "island", "bill_length_mm","bill_depth_mm", "flipper_length_mm","body_mass_g","year"};
        for(int i = 0; i < expectedColumns.length; i++){
            assertEquals(i, result.getIndexOfColumn(expectedColumns[i]));
        }
    }

    @Test
    public void groupByUnsortedTest() {
        DefaultDataFrame result = dataFrame.groupBy(List.of("species", "sex"));
        String[] expectedColumns = new String[]{"species",  "sex", "island", "bill_length_mm","bill_depth_mm", "flipper_length_mm","body_mass_g","year"};
        for(int i = 0; i < expectedColumns.length; i++){
            assertEquals(i, result.getIndexOfColumn(expectedColumns[i]));
        }

    }

    @Test
    public void groupByFilterTest() {
        DefaultDataFrame result = dataFrame.groupBy(List.of("species", "sex")).filter(both(dataFrame.column("sex").objEq("male"), dataFrame.column("year").eq(2009)));
        String[] expectedColumns = new String[]{"species",  "sex", "island", "bill_length_mm","bill_depth_mm", "flipper_length_mm","body_mass_g","year"};
        for(int i = 0; i < expectedColumns.length; i++){
            assertEquals(i, result.getIndexOfColumn(expectedColumns[i]));
        }
        StringColumn sexColumn = result.getStringColumn("sex");
        for (int i = 0; i < sexColumn.size(); i++) {
            assertEquals("male", sexColumn.get(i));
        }
        IntegerColumn yearColumn = result.getIntegerColumn("year");
        for (int i = 0; i < yearColumn.size(); i++) {
            assertEquals(2009, yearColumn.get(i));
        }
    }

    @Test
    public void aggregationTest() {
        DefaultDataFrame result = dataFrame.groupBy("species", "island")
                .aggregate(Aggregations.of(
                        "sex", AggregationType.COUNT,
                        "bill_length_mm", AggregationType.MEAN,
                        "bill_depth_mm", AggregationType.MEAN,
                        "flipper_length_mm", AggregationType.MEAN,
                        "body_mass_g", AggregationType.MEAN)
                );
        String[] expectedColumns = new String[]{"species", "island", "sex_count","bill_length_mm_mean","bill_depth_mm_mean", "flipper_length_mm_mean","body_mass_g_mean"};
        for(int i = 0; i < expectedColumns.length; i++){
            assertEquals(i, result.getIndexOfColumn(expectedColumns[i]));
        }
    }

    @Test
    public void aggregationMixTest() {
        DefaultDataFrame result = dataFrame.groupBy("species", "island")
                .aggregate(Aggregations.of(
                        "sex", AggregationType.COUNT,
                        "bill_length_mm", AggregationType.MAX,
                        "bill_depth_mm", AggregationType.MEAN,
                        "flipper_length_mm", AggregationType.MIN,
                        "body_mass_g", AggregationType.STD,
                        "year", AggregationType.SUM)
                );
        String[] expectedColumns = new String[]{"species", "island", "sex_count","bill_length_mm_max","bill_depth_mm_mean", "flipper_length_mm_min","body_mass_g_std","year_sum"};
        for(int i = 0; i < expectedColumns.length; i++){
            assertEquals(i, result.getIndexOfColumn(expectedColumns[i]));
        }

        result.show();
    }
}
