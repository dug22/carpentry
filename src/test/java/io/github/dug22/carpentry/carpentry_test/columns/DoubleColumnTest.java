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

package io.github.dug22.carpentry.carpentry_test.columns;

import io.github.dug22.carpentry.column.numbers.NumberColumnFormatter;
import io.github.dug22.carpentry.columns.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleColumnTest {

    private final DoubleColumn column = DoubleColumn.create("Test");

    @BeforeEach
    public void setUp() {
        column.append(4.0);
        column.append(10.0);
        column.append(8.0);
        column.append(13.0);
    }

    @Test
    public void appendTest() {
        column.append(3.0);
        assertEquals(5, column.size());
        column.appendNull();
        assertNull(column.get(5));
        column.appendAll(new Double[]{3.0, 4.0});
        assertEquals(8, column.size());
    }

    @Test
    public void removeTest() {
        column.removeAtIndex(1);
        assertEquals(3, column.size());
        column.remove(8.0);
        assertFalse(column.contains(8.0));
        column.removeAll(4.0, 13.0);
        assertTrue(column.isEmpty());
    }

    @Test
    public void copyEmptyTest() {
        DoubleColumn emptyCopy = column.copyEmpty();
        assertTrue(emptyCopy.isEmpty());
    }

    @Test
    public void copyTest() {
        DoubleColumn copy = column.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void uniqueTest() {
        column.append(10.0);
        DoubleColumn unique = column.unique();
        assertEquals(4, unique.size());
    }

    @Test
    public void filterTest() {
        DoubleColumn filter = column.filter(a -> a > 10);
        assertEquals(1, filter.size());
        assertTrue(filter.contains(13.0));
    }

    @Test
    public void applyTest() {
        DoubleColumn apply = column.apply(a -> a * 2);
        assertEquals(8.0, apply.get(0));
        assertEquals(20.0, apply.get(1));
        assertEquals(16.0, apply.get(2));
        assertEquals(26.0, apply.get(3));
    }

    @Test
    public void sortTest() {
        column.sortAscending();
        assertArrayEquals(new Double[]{4.0, 8.0, 10.0, 13.0}, column.getValues());
        column.sortDescending();
        assertArrayEquals(new Double[]{13.0, 10.0, 8.0, 4.0}, column.getValues());
    }

    @Test
    public void replaceTest() {
        column.replace(new Double[]{10.0, 8.0}, 15.0);
        assertFalse(column.containsAll(10.0, 8.0));
    }
    @Test
    public void format() {
        StringColumn currency = column.format(NumberColumnFormatter.currency("en", "US"));
        String[] currencyData = new String[]{"$4.00", "$10.00", "$8.00", "$13.00"};
        for (int i = 0; i < currency.size(); i++) {
            assertEquals(currencyData[i], currency.get(i));
        }

        StringColumn sciNotation = column.format(NumberColumnFormatter.scientificNotation(3));
        String[] sciNotationData = new String[]{"4E0", "1E1", "8E0", "1.3E1"};
        for (int i = 0; i < sciNotation.size(); i++) {
            assertEquals(sciNotationData[i], sciNotation.get(i));
        }

        StringColumn standardNotation = column.format(NumberColumnFormatter.standardNotation(1));
        String[] standardNotationData = new String[]{"4", "10", "8", "13"};
        for (int i = 0; i < standardNotation.size(); i++) {
            assertEquals(standardNotationData[i], standardNotation.get(i));
        }
    }

    @Test
    public void numericMapTest() {
        DoubleColumn addition = column.plus(otherColumn()).asDoubleColumn();
        Double[] additionResults = new Double[]{14.0, 22.0, 17.0, 20.0};
        for (int i = 0; i < addition.size(); i++) {
            assertEquals(additionResults[i], addition.get(i));
        }

        DoubleColumn subtraction = column.minus(otherColumn()).asDoubleColumn();
        Double[] subtractionResults = new Double[]{-6.0, -2.0, -1.0, 6.0};
        for (int i = 0; i < subtraction.size(); i++) {
            assertEquals(subtractionResults[i], subtraction.get(i));
        }

        DoubleColumn product = column.times(otherColumn()).asDoubleColumn();
        Double[] productResults = new Double[]{40.0, 120.0, 72.0, 91.0};
        for (int i = 0; i < product.size(); i++) {
            assertEquals(productResults[i], product.get(i));
        }

        DoubleColumn division = column.divide(otherColumn()).asDoubleColumn();
        Double[] divisionResults = new Double[]{0.4, 0.833333, 0.888888, 1.857143};
        for (int i = 0; i < division.size(); i++) {
            assertEquals(divisionResults[i], division.get(i), 0.000001);
        }

        DoubleColumn ratio = column.asRatio();
        Double[] ratioResults = new Double[]{0.11428571428571428, 0.2857142857142857, 0.22857142857142856, 0.37142857142857144};
        for (int i = 0; i < ratio.size(); i++) {
            assertEquals(ratioResults[i], ratio.get(i));
        }

        DoubleColumn pct = column.asPercent();
        Double[] pctResults = new Double[]{11.428571428571429, 28.57142857142857, 22.857142857142856, 37.142857142857144};
        for (int i = 0; i < pct.size(); i++) {
            assertEquals(pctResults[i], pct.get(i));
        }

        DoubleColumn abs = column.absolute();
        Double[] absResults = new Double[]{4.0, 10.0, 8.0, 13.0};
        for (int i = 0; i < abs.size(); i++) {
            assertEquals(absResults[i], abs.get(i));
        }
    }

    @Test
    public void statisticsTest() {
        assertEquals(13.0, column.max());
        assertEquals(8.75, column.mean());
        assertEquals(9.0, column.median());
        assertEquals(4.0, column.min());
        assertEquals(4.0, column.mode());
        assertEquals(9.0, column.range());
        assertEquals( 3.774917217635375, column.std());
        assertEquals(3.2691742076555053, column.populationSTD());
        assertEquals(35.0, column.sum());
    }

    @Test
    public void asByteColumnTest() {
        ByteColumn byteColumn = column.asByteColumn();
        assertEquals((byte)4, byteColumn.get(0));
        assertEquals((byte)10, byteColumn.get(1));
        assertEquals((byte)8, byteColumn.get(2));
        assertEquals((byte)13, byteColumn.get(3));
    }

    @Test
    public void asFloatColumnTest() {
        FloatColumn floatColumn = column.asFloatColumn();
        assertEquals(4.0f, floatColumn.get(0));
        assertEquals(10.0f, floatColumn.get(1));
        assertEquals(8.0f, floatColumn.get(2));
        assertEquals(13.0f, floatColumn.get(3));
    }

    @Test
    public void asIntegerColumnTest() {
        IntegerColumn integerColumn = column.asIntegerColumn();
        assertEquals(4, integerColumn.get(0));
        assertEquals(10, integerColumn.get(1));
        assertEquals(8, integerColumn.get(2));
        assertEquals(13, integerColumn.get(3));
    }

    @Test
    public void asLongColumnTest() {
        LongColumn longColumn = column.asLongColumn();
        assertEquals(4, longColumn.get(0));
        assertEquals(10, longColumn.get(1));
        assertEquals(8, longColumn.get(2));
        assertEquals(13, longColumn.get(3));
    }

    @Test
    public void asShortColumnTest() {
        ShortColumn shortColumn = column.asShortColumn();
        assertEquals((short) 4, shortColumn.get(0));
        assertEquals((short) 10, shortColumn.get(1));
        assertEquals((short) 8, shortColumn.get(2));
        assertEquals((short) 13, shortColumn.get(3));
    }

    @Test
    public void asStringColumnTest() {
        StringColumn stringColumn = column.asStringColumn();
        assertEquals("4.0", stringColumn.get(0));
        assertEquals("10.0", stringColumn.get(1));
        assertEquals("8.0", stringColumn.get(2));
        assertEquals("13.0", stringColumn.get(3));
    }

    private DoubleColumn otherColumn() {
        return DoubleColumn.create("Test 2", new Double[]{10.0, 12.0, 9.0, 7.0});
    }
}
