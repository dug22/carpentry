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

package io.github.dug22.carpentry.carpentry_test;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.DataFrameFactory;
import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.columns.DoubleColumn;
import io.github.dug22.carpentry.columns.IntegerColumn;
import io.github.dug22.carpentry.columns.StringColumn;
import io.github.dug22.carpentry.io.csv.CSVReader;
import io.github.dug22.carpentry.io.csv.CSVReaderBuilder;
import io.github.dug22.carpentry.io.csv.OptionalCSVHeader;
import io.github.dug22.carpentry.io.csv.OptionalCSVHeaders;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataFrameFactoryTest {

    @Test
    public void createDataFrameWithEmptyColumnMapTest() {
        DefaultDataFrame dataFrame = DefaultDataFrame.create();
        assertNotNull(dataFrame);
        assertTrue(dataFrame.getColumnMap().isEmpty());
    }

    @Test
    public void createDataFrameWithColumnMapTest() {
        ColumnMap columnMap = new ColumnMap();
        columnMap.addColumn(StringColumn.create("name", getNames().toArray(new String[0])));
        columnMap.addColumn(StringColumn.create("job_title", getJobTitles().toArray(new String[0])));
        columnMap.addColumn(StringColumn.create("department", getDepartments().toArray(new String[0])));
        columnMap.addColumn(StringColumn.create("hire_date", getHireDates().toArray(new String[0])));
        columnMap.addColumn(IntegerColumn.create("salary", getSalaries().toArray(new Integer[0])));
        columnMap.addColumn(StringColumn.create("location", getLocations().toArray(new String[0])));
        assertFalse(columnMap.isEmpty());
        DefaultDataFrame df = DataFrameFactory.create(columnMap);
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void createDataFrameFromCSVFile() {
        DefaultDataFrame df = DefaultDataFrame.load(Path.of("src/test/resources/employees_v1.csv"));
        assertNotNull(df);
        df.rowColumnDetails();
        validateColumns(df);
    }

    @Test
    public void createDataFrameFromCSVFileWithCustomDelimiter() {
        DefaultDataFrame df = DefaultDataFrame.load(Path.of("src/test/resources/employees_v2.csv"), ";");
        assertNotNull(df);
        df.rowColumnDetails();
        validateColumns(df);
    }

    @Test
    public void createDataFrameFromCSVFileWithOptionalHeaders() {
        DefaultDataFrame df = DefaultDataFrame.load(
                Path.of("src/test/resources/employees_v1.csv"),
                OptionalCSVHeaders.of(
                        new OptionalCSVHeader("name", String.class),
                        new OptionalCSVHeader("job_title", String.class),
                        new OptionalCSVHeader("department", String.class),
                        new OptionalCSVHeader("hire_date", String.class),
                        new OptionalCSVHeader("salary", Double.class),
                        new OptionalCSVHeader("location", String.class)
                ));
        assertNotNull(df);
        df.rowColumnDetails();
        validateColumns(df);
    }

    @Test
    public void createDataFrameFromCSVFileWithOptionalHeadersAndDelimiter() {
        DefaultDataFrame df = DefaultDataFrame.load(
                Path.of("src/test/resources/employees_v2.csv"),
                OptionalCSVHeaders.of(
                        new OptionalCSVHeader("name", String.class),
                        new OptionalCSVHeader("job_title", String.class),
                        new OptionalCSVHeader("department", String.class),
                        new OptionalCSVHeader("hire_date", String.class),
                        new OptionalCSVHeader("salary", Double.class),
                        new OptionalCSVHeader("location", String.class)
                ), ";");
        assertNotNull(df);
        df.rowColumnDetails();
        validateColumns(df);
    }

    @Test
    public void createDataFrameFromCSVUrl() {
        DefaultDataFrame df = DefaultDataFrame.load("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/employees.csv");
        assertNotNull(df);
        df.rowColumnDetails();
        validateColumns(df);
    }

    @Test
    public void createDataFrameFromCSVUrlWithDelimiter() {
        DefaultDataFrame df = DefaultDataFrame.load("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/employees2.csv", ";");
        assertNotNull(df);
        df.rowColumnDetails();
        validateColumns(df);
    }

    @Test
    public void createDataFrameFromCSVUrlWithOptionalHeaders() {
        DefaultDataFrame df = DefaultDataFrame.load(
                "https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/employees.csv",
                OptionalCSVHeaders.of(
                        new OptionalCSVHeader("name", String.class),
                        new OptionalCSVHeader("job_title", String.class),
                        new OptionalCSVHeader("department", String.class),
                        new OptionalCSVHeader("hire_date", String.class),
                        new OptionalCSVHeader("salary", Double.class),
                        new OptionalCSVHeader("location", String.class)
                ));
        assertNotNull(df);
        df.rowColumnDetails();
        validateColumns(df);
    }

    @Test
    public void createDataFrameFromCSVUrlWithOptionalHeadersAndDelimiter() {
        DefaultDataFrame df = DefaultDataFrame.load(
                "https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/employees2.csv",
                OptionalCSVHeaders.of(
                        new OptionalCSVHeader("name", String.class),
                        new OptionalCSVHeader("job_title", String.class),
                        new OptionalCSVHeader("department", String.class),
                        new OptionalCSVHeader("hire_date", String.class),
                        new OptionalCSVHeader("salary", Double.class),
                        new OptionalCSVHeader("location", String.class)
                ), ";");
        assertNotNull(df);
        df.rowColumnDetails();
        validateColumns(df);
    }

    @Test
    public void createDataFrameFromCSVReaders() {
        CSVReader csvReaderOne = new CSVReaderBuilder()
                .setFilePath(Path.of("src/test/resources/employees_v1.csv"))
                .build();
        DefaultDataFrame dfOne = DefaultDataFrame.load(csvReaderOne);
        assertNotNull(dfOne);
        validateColumns(dfOne);

        CSVReader csvReaderTwo = new CSVReaderBuilder()
                .setURL("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/employees.csv")
                .build();
        DefaultDataFrame dfTwo = DefaultDataFrame.load(csvReaderTwo);
        assertNotNull(dfTwo);
        validateColumns(dfTwo);

        CSVReader csvReaderThree = new CSVReaderBuilder()
                .setFilePath(Path.of("src/test/resources/employees_v2.csv"))
                .setDelimiter(";")
                .build();
        DefaultDataFrame dfThree = DefaultDataFrame.load(csvReaderThree);
        assertNotNull(dfThree);
        validateColumns(dfThree);

        CSVReader csvReaderFour = new CSVReaderBuilder()
                .setURL("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/employees2.csv")
                .setDelimiter(";")
                .build();
        DefaultDataFrame dfFour = DefaultDataFrame.load(csvReaderFour);
        assertNotNull(dfFour);
        validateColumns(dfFour);

        CSVReader csvReaderFive = new CSVReaderBuilder()
                .setFilePath(Path.of("src/test/resources/employees_v1.csv"))
                .setHeaders(OptionalCSVHeaders.of(
                        new OptionalCSVHeader("name", String.class),
                        new OptionalCSVHeader("job_title", String.class),
                        new OptionalCSVHeader("department", String.class),
                        new OptionalCSVHeader("hire_date", String.class),
                        new OptionalCSVHeader("salary", Double.class),
                        new OptionalCSVHeader("location", String.class)
                )).build();
        DefaultDataFrame dfFive = DefaultDataFrame.load(csvReaderFive);
        assertNotNull(dfFive);
        validateColumns(dfFive);

        CSVReader csvReaderSix = new CSVReaderBuilder()
                .setFilePath(Path.of("src/test/resources/employees_v2.csv"))
                .setHeaders(OptionalCSVHeaders.of(
                        new OptionalCSVHeader("name", String.class),
                        new OptionalCSVHeader("job_title", String.class),
                        new OptionalCSVHeader("department", String.class),
                        new OptionalCSVHeader("hire_date", String.class),
                        new OptionalCSVHeader("salary", Double.class),
                        new OptionalCSVHeader("location", String.class)
                ))
                .setDelimiter(";")
                .build();
        DefaultDataFrame dfSix = DefaultDataFrame.load(csvReaderSix);
        assertNotNull(dfSix);
        validateColumns(dfSix);

        CSVReader csvReaderSeven = new CSVReaderBuilder()
                .setURL("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/employees.csv")
                .setHeaders(OptionalCSVHeaders.of(
                        new OptionalCSVHeader("name", String.class),
                        new OptionalCSVHeader("job_title", String.class),
                        new OptionalCSVHeader("department", String.class),
                        new OptionalCSVHeader("hire_date", String.class),
                        new OptionalCSVHeader("salary", Double.class),
                        new OptionalCSVHeader("location", String.class)
                )).build();
        DefaultDataFrame dfSeven = DefaultDataFrame.load(csvReaderSeven);
        assertNotNull(dfSeven);
        validateColumns(dfSeven);

        CSVReader csvReaderEight = new CSVReaderBuilder()
                .setURL("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/employees2.csv")
                .setHeaders(OptionalCSVHeaders.of(
                        new OptionalCSVHeader("name", String.class),
                        new OptionalCSVHeader("job_title", String.class),
                        new OptionalCSVHeader("department", String.class),
                        new OptionalCSVHeader("hire_date", String.class),
                        new OptionalCSVHeader("salary", Double.class),
                        new OptionalCSVHeader("location", String.class)
                ))
                .setDelimiter(";")
                .build();
        DefaultDataFrame dfEight = DefaultDataFrame.load(csvReaderEight);
        assertNotNull(dfEight);
        validateColumns(dfEight);
    }


    private void validateColumns(DataFrame dataFrame) {
        for (int i = 0; i < dataFrame.getColumnCount(); i++) {
            assertEquals(getNames().get(i), dataFrame.getStringColumn("name").get(i));
            assertEquals(getJobTitles().get(i), dataFrame.getStringColumn("job_title").get(i));
            assertEquals(getDepartments().get(i), dataFrame.getStringColumn("department").get(i));
            assertEquals(getHireDates().get(i), dataFrame.getStringColumn("hire_date").get(i));

            if (dataFrame.getColumn("salary") instanceof IntegerColumn) {
                assertEquals(getSalaries().get(i), dataFrame.getIntegerColumn("salary").get(i));
            } else if (dataFrame.getColumn("salary") instanceof DoubleColumn) {
                assertEquals(getSalariesAsDoubles().get(i), dataFrame.getDoubleColumn("salary").get(i));
            }
            assertEquals(getLocations().get(i), dataFrame.getStringColumn("location").get(i));
        }
    }

    private List<String> getNames() {
        return Arrays.asList("John Smith", "Emma Johnson", "Liam Brown", "Olivia Davis", "Noah Wilson", "Ava Taylor",
                "James Anderson", "Sophia Martinez", "William Lee", "Isabella Clark", "Mason White", "Mia Rodriguez",
                "Ethan Walker", "Charlotte Hall", "Alexander Young", "Amelia King", "Daniel Scott", "Harper Adams",
                "Henry Green", "Evelyn Perez", "Lucas Turner", "Lily Phillips", "Benjamin Carter", "Zoe Mitchell",
                "Jackson Rivera", "Scarlett Evans", "Gabriel Brooks", "Chloe Morgan", "Samuel Nguyen", "Ella Patel"
        );
    }

    private List<String> getJobTitles() {
        return Arrays.asList(
                "Software Engineer", "Marketing Manager", "Data Analyst", "HR Specialist", "Product Manager", "Graphic Designer",
                "Sales Representative", "DevOps Engineer", "Accountant", "Content Writer", "Systems Administrator", "Customer Support Lead",
                "Project Manager", "UX Designer", "Financial Analyst", "Recruiter", "Software Developer", "Sales Manager",
                "Network Engineer", "Business Analyst", "Operations Coordinator", "Social Media Specialist", "QA Engineer", "Training Coordinator",
                "Logistics Manager", "Web Developer", "Payroll Specialist", "Event Planner", "Security Analyst", "Product Designer"
        );
    }

    private List<String> getDepartments() {
        return Arrays.asList(
                "Engineering", "Marketing", "Analytics", "Human Resources", "Product", "Design", "Sales", "Engineering", "Finance", "Marketing",
                "IT", "Support", "Operations", "Design", "Finance", "Human Resources", "Engineering", "Sales", "IT", "Analytics",
                "Operations", "Marketing", "Engineering", "Human Resources", "Operations", "Engineering", "Finance", "Marketing", "IT", "Product"
        );
    }

    private List<String> getHireDates() {
        return Arrays.asList(
                "2020-05-12", "2019-08-23", "2021-03-15", null, "2022-01-09", "2020-11-30", "2018-06-17", "2021-09-01", "2017-04-22", "2023-02-14",
                "2019-10-05", null, "2020-07-19", "2021-12-03", "2018-09-27", "2022-06-11", "2020-02-28", "2019-03-16", null, "2021-07-25",
                "2020-10-08", "2022-04-19", "2019-12-12", "2021-05-06", "2018-08-14", "2020-03-21", "2017-11-09", null, "2021-01-30", "2022-09-18"
        );
    }

    private List<Integer> getSalaries() {
        return Arrays.asList(
                85000, null, 72000, 65000, 95000, 68000, null, 90000, 75000, 62000,
                78000, 60000, 88000, null, 82000, 67000, 87000, 92000, 83000, null,
                69000, 64000, 76000, null, 91000, 84000, 70000, 66000, 79000, null
        );
    }

    private List<Double> getSalariesAsDoubles() {
        return Arrays.asList(
                85000D, null, 72000D, 65000D, 95000D, 68000D, null, 90000D, 75000D, 62000D,
                78000D, 60000D, 88000D, null, 82000D, 67000D, 87000D, 92000D, 83000D, null,
                69000D, 64000D, 76000D, null, 91000D, 84000D, 70000D, 66000D, 79000D, null
        );
    }

    private List<String> getLocations() {
        return Arrays.asList(
                "Seattle", "New York", "Chicago", "Austin", null, "San Francisco", "Boston", "Denver", "Houston", null,
                "Phoenix", "Miami", "Portland", "Los Angeles", "Atlanta", null, "San Diego", "Dallas", "Raleigh", "Minneapolis",
                "Orlando", null, "Salt Lake City", "Nashville", "Charlotte", null, "Kansas City", "Las Vegas", "Columbus", "Philadelphia"
        );
    }
}
