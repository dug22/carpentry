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

package io.github.dug22.carpentry.carpentry_test.example;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.grouping.aggregation.AggregationType;
import io.github.dug22.carpentry.grouping.aggregation.Aggregations;
import io.github.dug22.carpentry.io.csv.CSVReader;
import io.github.dug22.carpentry.io.csv.CSVReaderBuilder;

public class MiniTitanicExample extends AbstractExample {


    public static void main(String[] args) {
        out("We must first create a CSVReader with the required dataset link to read and required CSV Headers");
        CSVReader reader = new CSVReaderBuilder()
                .setURL("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/titanic.csv")
                .build();
        DefaultDataFrame df = DefaultDataFrame.load(reader);
        out();
        out("Next we'll display this DataFrame's shape");
        df.shape();
        out();
        out("Next we'll display the first 5 rows");
        df.head();
        out();
        out("Next we'll filter for those who did survive.");
        DefaultDataFrame didSurviveDataFrame = df.filter(df.column("Survived").eq(1));
        didSurviveDataFrame.head();
        out();
        out("Next, we'll group the data by sex and survival status, then aggregate the count of survivors based on their sex.");
        DefaultDataFrame groupedDataFrame = didSurviveDataFrame.groupBy("Sex", "Survived").aggregate(Aggregations.of("Sex", AggregationType.COUNT));
        groupedDataFrame.head();
        out("Next we'll save this DataFrame as a .csv file");
        groupedDataFrame.saveAsCsv("D:\\grouped_titanic_survivals.csv");
    }
}
