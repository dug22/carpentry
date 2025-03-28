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
import io.github.dug22.carpentry.columns.BooleanColumn;
import io.github.dug22.carpentry.columns.DoubleColumn;
import io.github.dug22.carpentry.io.csv.CSVHeader;
import io.github.dug22.carpentry.io.csv.CSVHeaders;
import io.github.dug22.carpentry.io.csv.CSVReader;
import io.github.dug22.carpentry.io.csv.CSVReaderBuilder;

import java.util.List;

public class EmployeeSalesExample extends AbstractExample {

    public static void main(String[] args) {
        out("We begin by creating a CSVReader to load the dataset from the provided URL and define the necessary headers.");
        CSVReader reader = new CSVReaderBuilder()
                .setURL("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/employee%20sales.csv")
                .setHeaders(
                        CSVHeaders.of(
                                new CSVHeader("Employee", String.class),
                                new CSVHeader("January", Integer.class),
                                new CSVHeader("February", Integer.class),
                                new CSVHeader("March", Integer.class),
                                new CSVHeader("April", Integer.class),
                                new CSVHeader("May", Integer.class),
                                new CSVHeader("June", Integer.class),
                                new CSVHeader("July", Integer.class),
                                new CSVHeader("August", Integer.class),
                                new CSVHeader("September", Integer.class),
                                new CSVHeader("October", Integer.class),
                                new CSVHeader("November", Integer.class),
                                new CSVHeader("December", Integer.class)
                        )
                ).build();

        DefaultDataFrame df = DefaultDataFrame.load(reader);
        out("Let's take a look at the shape of this DataFrame.");
        df.shape();
        out("Now, let's display the first 5 rows.");
        df.head();
        out("Next, we'll calculate the totalSales sales for each employee by summing their sales across all months.");
        DoubleColumn totalSales = df.getIntegerColumn("January").plus(List.of(
                df.getIntegerColumn("February"),
                df.getIntegerColumn("March"),
                df.getIntegerColumn("April"),
                df.getIntegerColumn("May"),
                df.getIntegerColumn("June"),
                df.getIntegerColumn("July"),
                df.getIntegerColumn("August"),
                df.getIntegerColumn("September"),
                df.getIntegerColumn("October"),
                df.getIntegerColumn("November"),
                df.getIntegerColumn("December")
        )).asDoubleColumn();
        totalSales.setColumnName("Overall Sale Amount");
        out("Now, we will calculate the average monthly sales for each employee.");
        DoubleColumn avgSales = totalSales.copy().apply(overall_sales -> overall_sales / 12);
        avgSales.setColumnName("Monthly Average Sale Amount");
        df = df.addColumn(totalSales).addColumn(avgSales);
        df.show();
        out("Let's now remove the monthly sales columns from the DataFrame.");
        df = df.drop("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        df.show();
        out("Next, we'll identify which employee had the best overall sales.");
        double maxOverAllSale = df.getDoubleColumn("Overall Sale Amount").max();
        Boolean[] hasThBestSalesOverall = new Boolean[df.getDoubleColumn("Overall Sale Amount").size()];
        for (int i = 0; i < hasThBestSalesOverall.length; i++) {
            hasThBestSalesOverall[i] = df.getDoubleColumn("Overall Sale Amount").get(i) == maxOverAllSale;
        }
        BooleanColumn hasTheBestSalesColumn = BooleanColumn.create("Had The Best Overall Sales", hasThBestSalesOverall);
        df.addColumn(hasTheBestSalesColumn);
        df.show();
        out("Now, let's filter the DataFrame to only show the employee with the best overall sales, excluding others.");
        DefaultDataFrame finalResult = df.query("'Had The Best Overall Sales' == 'true'").drop("Had The Best Overall Sales");
        finalResult.show();
        out("Employee I achieved the best overall sales.");
    }
}
