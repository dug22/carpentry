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

package io.github.dug22.carpentry.carpentry_test;

import io.github.dug22.carpentry.DataFrame;
import io.github.dug22.carpentry.DataFrameFactory;
import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.column.ColumnMap;
import io.github.dug22.carpentry.columns.StringColumn;
import io.github.dug22.carpentry.io.csv.CSVHeader;
import io.github.dug22.carpentry.io.csv.CSVHeaders;
import io.github.dug22.carpentry.io.csv.CSVReader;
import io.github.dug22.carpentry.io.csv.CSVReaderBuilder;
import io.github.dug22.carpentry.io.json.JSONHeader;
import io.github.dug22.carpentry.io.json.JSONHeaders;
import io.github.dug22.carpentry.io.json.JSONReader;
import io.github.dug22.carpentry.io.json.JSONReaderBuilder;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataFrameFactoryTest {

    private final ColumnMap columnMap = new ColumnMap();

    private final CSVHeaders csvHeaders = CSVHeaders.of(
            new CSVHeader("State", String.class),
            new CSVHeader("Code", String.class)
    );
    private final Path csvFilePathOne = Path.of("src/test/resources/states_v1.csv");
    private final Path csvFilePathTwo = Path.of("src/test/resources/states_v2.csv");
    private final String csvURLOne = "https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/states_v1.csv";
    private final String csvURLTwo = "https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/states_v2.csv";
    private final CSVReader csvReaderOne = new CSVReaderBuilder()
            .setFilePath(csvFilePathOne)
            .setHeaders(csvHeaders)
            .build();
    private final CSVReader csvReaderTwo = new CSVReaderBuilder()
            .setURL(csvURLOne)
            .setHeaders(csvHeaders)
            .build();
    private final CSVReader csvReaderThree = new CSVReaderBuilder()
            .setURL(csvURLTwo)
            .setHeaders(csvHeaders)
            .setDelimiter(";")
            .build();

    private final JSONHeaders jsonHeaders = JSONHeaders.of(
            new JSONHeader("State", String.class),
            new JSONHeader("Code", String.class)
    );
    private final Path jsonFilePathOne = Path.of("src/test/resources/states.json");
    private final Path jsonFilePathTwo = Path.of("src/test/resources/states_with_keys.json");
    private final String jsonURLOne = "https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/states.json";
    private final String jsonURLTwo = "https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/states_with_id_keys.json";
    private final JSONReader jsonReaderOne = new JSONReaderBuilder()
            .setFilePath(jsonFilePathOne)
            .setHeaders(jsonHeaders)
            .build();
    private final JSONReader jsonReaderTwo = new JSONReaderBuilder()
            .setURL(jsonURLOne)
            .setHeaders(jsonHeaders)
            .build();
    private final JSONReader jsonReaderThree = new JSONReaderBuilder()
            .setURL(jsonURLTwo)
            .setHeaders(jsonHeaders)
            .setRowKeys(getStateIDKeys())
            .build();


    @Test
    public void testCreateWithoutParams() {
        DefaultDataFrame df = DefaultDataFrame.create();
        assertNotNull(df);
        assertTrue(df.getColumnMap().isEmpty());
    }

    @Test
    public void testCreateWithColumnMap() {
        columnMap.addColumn(StringColumn.create("State", getStates().toArray(new String[0])));
        columnMap.addColumn(StringColumn.create("Code", getStateCodes().toArray(new String[0])));
        DefaultDataFrame df = DataFrameFactory.create(columnMap);
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void testLoadFromCsvFileOne() {
        DefaultDataFrame df = DefaultDataFrame.load(csvFilePathOne, csvHeaders);
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void testLoadFromCsvFileTwo() {
        DefaultDataFrame df = DefaultDataFrame.load(csvFilePathTwo, csvHeaders, ";");
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void testLoadFromCsvURLOne() {
        DefaultDataFrame df = DefaultDataFrame.load(csvURLOne, csvHeaders);
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void testLoadFromCsvURLTwo() {
        DefaultDataFrame df = DefaultDataFrame.load(csvURLTwo, csvHeaders, ";");
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void testLoadFromCsvReaderOne() {
        DefaultDataFrame df = DefaultDataFrame.load(csvReaderOne);
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void testLoadFromCsvReaderTwo() {
        DefaultDataFrame df = DefaultDataFrame.load(csvReaderTwo);
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void testLoadFromCsvReaderThree() {
        DefaultDataFrame df = DefaultDataFrame.load(csvReaderThree);
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void testLoadFromJsonFileOne() {
        DefaultDataFrame df = DefaultDataFrame.load(jsonFilePathOne, jsonHeaders);
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void testLoadFromJsonFileTwo() {
        DefaultDataFrame df = DefaultDataFrame.load(jsonFilePathTwo, jsonHeaders, getStateIDKeys());
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void testLoadFromJsonURLOne() {
        DefaultDataFrame df = DefaultDataFrame.load(jsonURLOne, jsonHeaders);
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void testLoadFromJsonURLTwo() {
        DefaultDataFrame df = DefaultDataFrame.load(jsonURLTwo, jsonHeaders, getStateIDKeys());
        assertNotNull(df);
        validateColumns(df);
    }


    @Test
    public void testLoadFromJsonReaderOne() {
        DefaultDataFrame df = DefaultDataFrame.load(jsonReaderOne);
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void testLoadFromJsonReaderTwo() {
        DefaultDataFrame df = DefaultDataFrame.load(jsonReaderTwo);
        assertNotNull(df);
        validateColumns(df);
    }

    @Test
    public void testLoadFromJsonReaderThree() {
        DefaultDataFrame df = DefaultDataFrame.load(jsonReaderThree);
        assertNotNull(df);
        validateColumns(df);
    }

    private void validateColumns(DataFrame df) {
        for (int i = 0; i < df.getColumnMap().size(); i++) {
            assertEquals(getStates().get(i), df.getStringColumn("State").get(i));
            assertEquals(getStateCodes().get(i), df.getStringColumn("Code").get(i));
        }
    }


    private List<String> getStates() {
        return List.of(
                "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
                "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
                "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
                "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico",
                "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
                "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
                "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
        );
    }

    private List<String> getStateCodes() {
        return List.of(
                "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE",
                "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS",
                "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS",
                "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY",
                "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV",
                "WI", "WY"
        );
    }

    private List<String> getStateIDKeys() {
        return Arrays.asList(
                "id_1", "id_2", "id_3", "id_4", "id_5", "id_6", "id_7", "id_8",
                "id_9", "id_10", "id_11", "id_12", "id_13", "id_14", "id_15",
                "id_16", "id_17", "id_18", "id_19", "id_20", "id_21", "id_22",
                "id_23", "id_24", "id_25", "id_26", "id_27", "id_28", "id_29",
                "id_30", "id_31", "id_32", "id_33", "id_34", "id_35", "id_36",
                "id_37", "id_38", "id_39", "id_40", "id_41", "id_42", "id_43",
                "id_44", "id_45", "id_46", "id_47", "id_48", "id_49", "id_50"
        );
    }
}