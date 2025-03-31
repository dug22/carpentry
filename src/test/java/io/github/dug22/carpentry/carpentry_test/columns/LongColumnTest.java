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

public class LongColumnTest {

    private final LongColumn column = LongColumn.create("Test");

    @BeforeEach
    public void setUp() {
        column.append(4L);
        column.append(10L);
        column.append(8L);
        column.append(13L);
    }

    @Test
    public void appendTest() {
        column.append(3L);
        assertEquals(5, column.size());
        column.appendNull();
        assertNull(column.get(5));
        column.appendAll(new Long[]{3L, 4L});
        assertEquals(8, column.size());
    }

    @Test
    public void removeTest() {
        column.removeAtIndex(1);
        assertEquals(3, column.size());
        column.remove(8L);
        assertFalse(column.contains(8L));
        column.removeAll(4L, 13L);
        assertTrue(column.isEmpty());
    }

    @Test
    public void copyEmptyTest() {
        LongColumn emptyCopy = column.copyEmpty();
        assertTrue(emptyCopy.isEmpty());
    }

    @Test
    public void copyTest() {
        LongColumn copy = column.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void uniqueTest() {
        column.append(10L);
        LongColumn unique = column.unique();
        assertEquals(4, unique.size());
    }

    @Test
    public void filterTest() {
        LongColumn filter = column.filter(a -> a > 10);
        assertEquals(1, filter.size());
        assertTrue(filter.contains(13L));
    }

    @Test
    public void applyTest() {
        LongColumn apply = column.apply(a -> a * 2);
        assertEquals(8L, apply.get(0));
        assertEquals(20L, apply.get(1));
        assertEquals(16L, apply.get(2));
        assertEquals(26L, apply.get(3));
    }

    @Test
    public void sortTest() {
        column.sortAscending();
        assertArrayEquals(new Long[]{4L, 8L, 10L, 13L}, column.getValues());
        column.sortDescending();
        assertArrayEquals(new Long[]{13L, 10L, 8L, 4L}, column.getValues());
    }

    @Test
    public void replaceTest() {
        column.replace(new Long[]{10L, 8L}, 15L);
        assertFalse(column.containsAll(10L, 8L));
    }


    @Test
    public void numericMapTest() {
        LongColumn addition = column.plus(otherColumn()).asLongColumn();
        Long[] additionResults = new Long[]{14L, 22L, 17L, 20L};
        for (int i = 0; i < addition.size(); i++) {
            assertEquals(additionResults[i], addition.get(i));
        }

        LongColumn subtraction = column.minus(otherColumn()).asLongColumn();
        Long[] subtractionResults = new Long[]{-6L, -2L, -1L, 6L};
        for (int i = 0; i < subtraction.size(); i++) {
            assertEquals(subtractionResults[i], subtraction.get(i));
        }

        LongColumn product = column.times(otherColumn()).asLongColumn();
        Long[] productResults = new Long[]{40L, 120L, 72L, 91L};
        for (int i = 0; i < product.size(); i++) {
            assertEquals(productResults[i], product.get(i));
        }

        LongColumn division = column.divide(otherColumn()).asLongColumn();
        Long[] divisionResults = new Long[]{0L, 0L, 0L, 1L};
        for (int i = 0; i < division.size(); i++) {
            assertEquals(divisionResults[i], division.get(i));
        }

        DoubleColumn otherColumn = otherColumn().asDoubleColumn();
        DoubleColumn pow = column.asDoubleColumn().pow(otherColumn).asDoubleColumn();
        Double[] powResults = new Double[]{1048576D, 1E12, 134217728D, 62748517D};
        for (int i = 0; i < pow.size(); i++) {
            assertEquals(powResults[i], pow.get(i));
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
        assertEquals(13L, column.max());
        assertEquals(8.75, column.mean());
        assertEquals(9.0, column.median());
        assertEquals(4L, column.min());
        assertEquals(4L, column.mode());
        assertEquals(9L, column.range());
        assertEquals( 3.774917217635375, column.std());
        assertEquals(3.2691742076555053, column.populationSTD());
        assertEquals(35L, column.sum());
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
        assertEquals(4f, floatColumn.get(0));
        assertEquals(10f, floatColumn.get(1));
        assertEquals(8f, floatColumn.get(2));
        assertEquals(13f, floatColumn.get(3));
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

    private LongColumn otherColumn() {
        return LongColumn.create("Test 2", new Long[]{10L, 12L, 9L, 7L});
    }
}
