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

package io.github.dug22.carpentry.carpentry_test.query;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.columns.IntegerColumn;
import io.github.dug22.carpentry.columns.StringColumn;
import io.github.dug22.carpentry.io.csv.CSVReader;
import io.github.dug22.carpentry.io.csv.CSVReaderBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataFrameQueryTest {

    private DefaultDataFrame dataFrame;
    private final CSVReader csvReader = new CSVReaderBuilder()
            .setURL("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/palmer%20penguins.csv")
            .build();

    @BeforeEach
    public void setUp() {
        dataFrame = DefaultDataFrame.load(csvReader);
    }

    /**
     * Here we are filtering for penguins whose species equals Adelie
     */
    @Test
    public void filterForAdelieSpeciesTest() {
        DefaultDataFrame result = dataFrame.query("'species' == 'Adelie'");
        StringColumn speciesColumn = result.getStringColumn("species");
        assertTrue(speciesColumn.containsAll("Adelie"));
    }

    /**
     * Here we are filtering for penguins whose species equals Adelie and their sex equals male.
     */
    @Test
    public void filterForAdelieMalesTest() {
        DefaultDataFrame result = dataFrame.query("'species' == 'Adelie' AND 'sex' == 'male'");
        StringColumn speciesColumn = result.getStringColumn("species");
        assertTrue(speciesColumn.containsAll("Adelie"));
        StringColumn sexColumn = result.getStringColumn("sex");
        assertTrue(sexColumn.containsAll("male"));
    }

    /**
     * Here we are filtering for penguins whose species equals Adelie or Gentoo and their sex equals male.
     */
    @Test
    public void filterForAdelieOrGentooMalesTest() {
        DefaultDataFrame result = dataFrame.query("('species' == 'Adelie' AND 'sex' == 'male') OR ('species' == 'Gentoo' AND 'sex' == 'male')");
        StringColumn speciesColumn = result.getStringColumn("species");
        assertTrue(speciesColumn.containsAll("Adelie", "Gentoo"));
        StringColumn sexColumn = result.getStringColumn("sex");
        assertTrue(sexColumn.containsAll("male"));
    }

    /**
     * Here we are filtering for penguins whose species equals Adelie and their body mass is greater than 4050 grams.
     */
    @Test
    public void filterForAdelieWithBodyMassOver4050Test() {
        DefaultDataFrame result = dataFrame.query("'species' == 'Adelie' AND 'body_mass_g' > '4050'");
        StringColumn speciesColumn = result.getStringColumn("species");
        assertTrue(speciesColumn.containsAll("Adelie"));
        IntegerColumn bodyMassColumn = result.getIntegerColumn("body_mass_g");
        for (int i = 0; i < bodyMassColumn.size(); i++) {
            assertTrue(bodyMassColumn.get(i) > 4050.0);
        }
    }

    /**
     * Here we are filtering for penguins whose species equals Adelie and their body mass is less than 4050 grams.
     */
    @Test
    public void filterForAdelieWithBodyMassUnder4050Test() {
        DefaultDataFrame result = dataFrame.query("'species' == 'Adelie' AND 'body_mass_g' < '4050'");
        StringColumn speciesColumn = result.getStringColumn("species");
        assertTrue(speciesColumn.containsAll("Adelie"));
        IntegerColumn bodyMassColumn = result.getIntegerColumn("body_mass_g");
        for (int i = 0; i < bodyMassColumn.size(); i++) {
            assertTrue(bodyMassColumn.get(i) < 4050.0);
        }
    }

    /**
     * Here we are filtering for penguins whose species equals Gentoo, their sex equals female, and the year is not 2008.
     */
    @Test
    public void filterForGentooFemaleWhereYearDoesNotEqual2008() {
        DefaultDataFrame result = dataFrame.query("'species' == 'Gentoo' AND 'sex' == 'female' AND 'year' != '2008'");
        StringColumn speciesColumn = result.getStringColumn("species");
        assertTrue(speciesColumn.containsAll("Gentoo"));
        StringColumn sexColumn = result.getStringColumn("sex");
        assertTrue(sexColumn.containsAll("female"));
        IntegerColumn yearColumn = result.getIntegerColumn("year");
        for (int i = 0; i < yearColumn.size(); i++) {
            assertTrue(yearColumn.get(i) != 2008);
        }
    }

    /**
     * Here we are filtering for penguins whose species is Gentoo and island is Biscoe, or species is Adelie and island is Dream, and if their sex equals male.
     */
    @Test
    public void filterForGentooBiscoeOrAdelieDreamWhereSexEqualsMale(){
        DefaultDataFrame result = dataFrame.query("(('species' == 'Gentoo' AND 'island' == 'Biscoe') OR ('species' == 'Adelie' AND 'island' == 'Dream')) AND 'sex' == 'male'");
        StringColumn speciesColumn = result.getStringColumn("species");
        assertTrue(speciesColumn.containsAll("Gentoo", "Adelie"));
        StringColumn islandColumn = result.getStringColumn("island");
        assertTrue(islandColumn.containsAll("Biscoe", "Dream"));
        StringColumn sexColumn = result.getStringColumn("sex");
        assertTrue(sexColumn.containsAll("male"));
        result.shape();
    }
}
