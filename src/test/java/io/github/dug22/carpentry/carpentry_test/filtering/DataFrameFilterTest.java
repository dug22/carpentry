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

package io.github.dug22.carpentry.carpentry_test.filtering;

import io.github.dug22.carpentry.DefaultDataFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static io.github.dug22.carpentry.filtering.FilterPredicateCondition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataFrameFilterTest {

    private DefaultDataFrame dataFrame;

    @BeforeEach
    public void setUp() {
        dataFrame = DefaultDataFrame.create()
                .addIntegerColumn("ID", new Integer[]{1, 2, 3, 4, 5})
                .addStringColumn("Name", new String[]{"Mark", "Andy", "James", "John", "Kim"})
                .addStringColumn("DOB", new String[]{"05-20-1995", "04-23-1996", "10-20-1992", "04-30-1997", "03-15-1999"})
                .addDoubleColumn("Weight", new Double[]{160D, 145D, 165D, 190D, 115D})
                .addStringColumn("Department", new String[]{"HR", "Marketing", "Sales", "HR", "Sales"});
    }

    @Test
    public void greaterThanTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Weight").gt(180D));
        assertEquals(1, result.getRowCount());
    }

    @Test
    public void greaterThanOrEqualToTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Weight").gte(160D));
        assertEquals(3, result.getRowCount());
    }

    @Test
    public void lessThanTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Weight").lt(180D));
        assertEquals(4, result.getRowCount());
    }

    @Test
    public void lessThanOrEqualToTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Weight").lte(165D));
        assertEquals(4, result.getRowCount());
    }

    @Test
    public void equalToTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Name").objEq("Andy"));
        assertEquals(1, result.getRowCount());
    }

    @Test
    public void notEqualToTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Name").objNotEq("Andy"));
        assertEquals(4, result.getRowCount());
    }

    @Test
    public void betweenTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Weight").between(120D, 180D));
        assertEquals(3, result.getRowCount());
    }

    @Test
    public void inTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Department").in(Arrays.asList("HR", "Sales")));
        assertEquals(4, result.getRowCount());
    }

    @Test
    public void notInTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Department").notIn(Arrays.asList("HR", "Sales")));
        assertEquals(1, result.getRowCount());
    }

    @Test
    public void andTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Name").startsWith("A")
                .and(dataFrame.column("Weight").gte(135D)));
        assertEquals(1, result.getRowCount());
    }

    @Test
    public void orTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Department").in(Arrays.asList("HR", "Sales"))
                .or(dataFrame.column("Weight").gte(150D)));
        assertEquals(4, result.getRowCount());
    }

    @Test
    public void notTest() {
        DefaultDataFrame result = dataFrame.filter(not(dataFrame.column("Weight").gt(185D)));
        assertEquals(4, result.getRowCount());
    }

    @Test
    public void eitherTest(){
        DefaultDataFrame result = dataFrame.filter(either(dataFrame.column("DOB").isInMonth(Month.MARCH), dataFrame.column("DOB").isInMonth(Month.APRIL)));
        assertEquals(3, result.getRowCount());
    }

    @Test
    public void bothTest(){
        DefaultDataFrame result = dataFrame.filter(both(dataFrame.column("Name").startsWith("A"), dataFrame.column("DOB").isInMonth(Month.APRIL)));
        assertEquals(1, result.getRowCount());
    }

    @Test
    public void isInMonthTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("DOB").isInMonth(Month.APRIL).or(dataFrame.column("DOB").isInMonth(Month.AUGUST)));
        assertEquals(2, result.getRowCount());
    }

    @Test
    public void isBeforeTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("DOB").isBefore(LocalDate.of(1996, 10, 20)));
        assertEquals(3, result.getRowCount());
    }

    @Test
    public void isAfterTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("DOB").isAfter(LocalDate.of(1996, 10, 20)));
        assertEquals(2, result.getRowCount());
    }

    @Test
    public void isBetweenDatesTest() {
        LocalDate startDate = LocalDate.of(1996, 1, 1);
        LocalDate endDate = LocalDate.of(1998, 1, 1);
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("DOB").isBetween(startDate, endDate));
        assertEquals(2, result.getRowCount());
    }

    @Test
    public void startsWithTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Name").startsWith("A"));
        assertEquals(1, result.getRowCount());
    }

    @Test
    public void endsWithTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Name").endsWith("y"));
        assertEquals(1, result.getRowCount());
    }

    @Test
    public void containsTest() {
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("Department").contains("H"));
        assertEquals(2, result.getRowCount());
    }
}