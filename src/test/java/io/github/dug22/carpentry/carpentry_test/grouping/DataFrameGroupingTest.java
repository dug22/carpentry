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
import io.github.dug22.carpentry.grouping.aggregation.AggregationType;
import io.github.dug22.carpentry.grouping.aggregation.Aggregations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataFrameGroupingTest {

    private DefaultDataFrame dataFrame;

    @BeforeEach
    public void setUp() {
        dataFrame = DefaultDataFrame.load("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/palmer%20penguins.csv");
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
    }
}
