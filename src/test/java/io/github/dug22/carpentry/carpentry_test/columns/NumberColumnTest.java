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

import io.github.dug22.carpentry.columns.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NumberColumnTest {

    private final NumberColumn column = NumberColumn.create("Test");

    @BeforeEach
    public void setUp() {
        column.append(4);
        column.append(10);
        column.append(8);
        column.append(13);
    }

    @Test
    public void appendTest() {
        column.append(3);
        assertEquals(5, column.size());
        column.appendNull();
        assertNull(column.get(5));
        column.appendAll(new Number[]{3, 4});
        assertEquals(8, column.size());
    }

    @Test
    public void removeTest() {
        column.removeAtIndex(1);
        assertEquals(3, column.size());
        column.remove(8);
        assertFalse(column.contains(8));
        column.removeAll(4, 13);
        assertTrue(column.isEmpty());
    }

    @Test
    public void copyEmptyTest() {
        NumberColumn emptyCopy = column.copyEmpty();
        assertTrue(emptyCopy.isEmpty());
    }

    @Test
    public void copyTest() {
        NumberColumn copy = column.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void uniqueTest() {
        column.append(10);
        NumberColumn unique = column.unique();
        assertEquals(4, unique.size());
    }

    @Test
    public void filterTest() {
        NumberColumn filter = column.filter(a -> a.doubleValue() > 10);
        assertEquals(1, filter.size());
        assertTrue(filter.contains(13));
    }

    @Test
    public void applyTest() {
        NumberColumn apply = column.apply(a -> a.doubleValue() * 2);
        assertEquals(8.0, apply.get(0).doubleValue(), 0.0001);
        assertEquals(20.0, apply.get(1).doubleValue(), 0.0001);
        assertEquals(16.0, apply.get(2).doubleValue(), 0.0001);
        assertEquals(26.0, apply.get(3).doubleValue(), 0.0001);
    }

    @Test
    public void sortTest() {
        column.sortAscending();
        assertArrayEquals(new Number[]{4,8,10,13}, column.getValues());
        column.sortDescending();
        assertArrayEquals(new Number[]{13, 10, 8, 4}, column.getValues());
    }

    @Test
    public void replaceTest() {
        column.replace(new Number[]{10, 8}, 15);
        assertFalse(column.containsAll(10, 8));
    }


    @Test
    public void numericMapTest() {
        NumberColumn addition = column.plus(otherColumn());
        Number[] additionResults = new Double[]{14D, 22D, 17D, 20D};
        for (int i = 0; i < addition.size(); i++) {
            assertEquals(additionResults[i].doubleValue(), addition.get(i).doubleValue());
        }

        NumberColumn subtraction = column.minus(otherColumn());
        Number[] subtractionResults = new Double[]{-6D, -2D, -1D, 6D};
        for (int i = 0; i < subtraction.size(); i++) {
            assertEquals(subtractionResults[i].doubleValue(), subtraction.get(i).doubleValue());
        }

        NumberColumn product = column.times(otherColumn());
        Number[] productResults = new Number[]{40D, 120D, 72D, 91D};
        for (int i = 0; i < product.size(); i++) {
            assertEquals(productResults[i], product.get(i));
        }

        NumberColumn division = column.divide(otherColumn());
        Number[] divisionResults = new Number[]{0.4, 0.833333, 0.888888, 1.857143};
        for (int i = 0; i < division.size(); i++) {
            assertEquals(divisionResults[i].doubleValue(), division.get(i).doubleValue(), 0.000001);
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
    public void asByteColumnTest() {
        ByteColumn byteColumn = column.asByteColumn();
        assertEquals((byte) 4, byteColumn.get(0));
        assertEquals((byte) 10, byteColumn.get(1));
        assertEquals((byte) 8, byteColumn.get(2));
        assertEquals((byte) 13, byteColumn.get(3));
    }

    @Test
    public void asDoubleColumnTest() {
        DoubleColumn doubleColumn = column.asDoubleColumn();
        assertEquals(4.0, doubleColumn.get(0));
        assertEquals(10.0, doubleColumn.get(1));
        assertEquals(8.0, doubleColumn.get(2));
        assertEquals(13.0, doubleColumn.get(3));
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
        assertEquals("4", stringColumn.get(0));
        assertEquals("10", stringColumn.get(1));
        assertEquals("8", stringColumn.get(2));
        assertEquals("13", stringColumn.get(3));
    }

    @Test
    public void statisticsTest() {
        assertEquals(13.0, column.max());
        assertEquals(8.75, column.mean(), 0.0001);
        assertEquals(9.0, column.median(), 0.0001);
        assertEquals(4.0, column.min());
        assertEquals(4, column.mode());
        assertEquals(9.0, column.range());
        assertEquals( 3.774917217635375, column.std());
        assertEquals(3.2691742076555053, column.populationSTD());
        assertEquals(35.0, column.sum());
    }

    private NumberColumn otherColumn() {
        return NumberColumn.create("Test 2", new Number[]{10, 12, 9, 7});
    }
}
