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

public class ByteColumnTest {

    private final ByteColumn column = ByteColumn.create("Test");

    @BeforeEach
    public void setUp() {
        column.append((byte) 4);
        column.append((byte) 10);
        column.append((byte) 8);
        column.append((byte) 13);
    }

    @Test
    public void appendTest() {
        column.append((byte) 3);
        assertEquals(5, column.size());
        column.appendNull();
        assertNull(column.get(5));
        column.appendAll(new Byte[]{3, 4});
        assertEquals(8, column.size());
    }

    @Test
    public void removeTest() {
        column.removeAtIndex(1);
        assertEquals(3, column.size());
        column.remove((byte) 8);
        assertFalse(column.contains((byte) 8));
        column.removeAll((byte) 4, (byte) 13);
        assertTrue(column.isEmpty());
    }

    @Test
    public void copyEmptyTest() {
        ByteColumn emptyCopy = column.copyEmpty();
        assertTrue(emptyCopy.isEmpty());
    }

    @Test
    public void copyTest() {
        ByteColumn copy = column.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void uniqueTest() {
        column.append((byte) 10);
        ByteColumn unique = column.unique();
        assertEquals(4, unique.size());
    }

    @Test
    public void filterTest() {
        ByteColumn filter = column.filter(a -> a > 10);
        assertEquals(1, filter.size());
        assertTrue(filter.contains((byte) 13));
    }

    @Test
    public void applyTest() {
        ByteColumn apply = column.apply(a -> (byte) (a * 2));
        assertEquals((byte) 8, apply.get(0));
        assertEquals((byte) 20, apply.get(1));
        assertEquals((byte) 16, apply.get(2));
        assertEquals((byte) 26, apply.get(3));
    }

    @Test
    public void sortTest() {
        column.sortAscending();
        assertArrayEquals(new Byte[]{4, 8, 10, 13}, column.getValues());
        column.sortDescending();
        assertArrayEquals(new Byte[]{13, 10, 8, 4}, column.getValues());
    }

    @Test
    public void replaceTest() {
        column.replace(new Byte[]{10, 8}, (byte) 15);
        assertFalse(column.containsAll((byte) 10, (byte) 8));
    }

    @Test
    public void numericMapTest() {
        ByteColumn addition = column.plus(otherColumn()).asByteColumn();
        Byte[] additionResults = new Byte[]{14, 22, 17, 20};
        for (int i = 0; i < addition.size(); i++) {
            assertEquals(additionResults[i], addition.get(i));
        }

        ByteColumn subtraction = column.minus(otherColumn()).asByteColumn();
        Byte[] subtractionResults = new Byte[]{-6, -2, -1, 6};
        for (int i = 0; i < subtraction.size(); i++) {
            assertEquals(subtractionResults[i], subtraction.get(i));
        }

        ByteColumn product = column.times(otherColumn()).asByteColumn();
        Byte[] productResults = new Byte[]{40, 120, 72, 91};
        for (int i = 0; i < product.size(); i++) {
            assertEquals(productResults[i], product.get(i));
        }

        ByteColumn division = column.divide(otherColumn()).asByteColumn();
        Byte[] divisionResults = new Byte[]{0, 0, 0, 1};
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
        assertEquals((byte) 13, column.max());
        assertEquals(8.75, column.mean());
        assertEquals(9.0, column.median());
        assertEquals((byte) 4, column.min());
        assertEquals((byte) 4, column.mode());
        assertEquals((byte) 9, column.range());
        assertEquals( 3.774917217635375, column.std());
        assertEquals(3.2691742076555053, column.populationSTD());
        assertEquals((byte) 35, column.sum());
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

    private ByteColumn otherColumn() {
        return ByteColumn.create("Test 2", new Byte[]{10, 12, 9, 7});
    }
}
