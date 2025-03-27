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

public class ShortColumnTest {

    private final ShortColumn column = ShortColumn.create("Test");

    @BeforeEach
    public void setUp() {
        column.append((short) 4);
        column.append((short) 10);
        column.append((short) 8);
        column.append((short) 13);
    }

    @Test
    public void appendTest() {
        column.append((short) 3);
        assertEquals(5, column.size());
        column.appendNull();
        assertNull(column.get(5));
        column.appendAll(new Short[]{3, 4});
        assertEquals(8, column.size());
    }

    @Test
    public void removeTest() {
        column.removeAtIndex(1);
        assertEquals(3, column.size());
        column.remove((short) 8);
        assertFalse(column.contains((short) 8));
        column.removeAll((short) 4, (short) 13);
        assertTrue(column.isEmpty());
    }

    @Test
    public void copyEmptyTest() {
        ShortColumn emptyCopy = column.copyEmpty();
        assertTrue(emptyCopy.isEmpty());
    }

    @Test
    public void copyTest() {
        ShortColumn copy = column.copy();
        assertEquals(4, copy.size());
    }

    @Test
    public void uniqueTest() {
        column.append((short) 10);
        ShortColumn unique = column.unique();
        assertEquals(4, unique.size());
    }

    @Test
    public void filterTest() {
        ShortColumn filter = column.filter(a -> a > 10);
        assertEquals(1, filter.size());
        assertTrue(filter.contains((short) 13));
    }

    @Test
    public void applyTest() {
        ShortColumn apply = column.apply(a -> (short) (a * 2));
        assertEquals((short) 8, apply.get(0));
        assertEquals((short) 20, apply.get(1));
        assertEquals((short) 16, apply.get(2));
        assertEquals((short) 26, apply.get(3));
    }

    @Test
    public void sortTest() {
        column.sortAscending();
        assertArrayEquals(new Short[]{4, 8, 10, 13}, column.getValues());
        column.sortDescending();
        assertArrayEquals(new Short[]{13, 10, 8, 4}, column.getValues());
    }

    @Test
    public void replaceTest() {
        column.replace(new Short[]{10, 8}, (short) 15);
        assertFalse(column.containsAll((short) 10, (short) 8));
    }


    @Test
    public void numericMapTest() {
        ShortColumn addition = column.plus(otherColumn()).asShortColumn();
        Short[] additionResults = new Short[]{(short) 14, (short) 22, (short) 17, (short) 20};
        for (int i = 0; i < addition.size(); i++) {
            assertEquals(additionResults[i], addition.get(i));
        }

        ShortColumn subtraction = column.minus(otherColumn()).asShortColumn();
        Short[] subtractionResults = new Short[]{(short) -6, (short) -2, (short) -1, (short) 6};
        for (int i = 0; i < subtraction.size(); i++) {
            assertEquals(subtractionResults[i], subtraction.get(i));
        }

        ShortColumn product = column.times(otherColumn()).asShortColumn();
        Short[] productResults = new Short[]{(short) 40, (short) 120, (short) 72, (short) 91};
        for (int i = 0; i < product.size(); i++) {
            assertEquals(productResults[i], product.get(i));
        }

        ShortColumn division = column.divide(otherColumn()).asShortColumn();
        Short[] divisionResults = new Short[]{(short) 0, (short) 0, (short) 0, (short) 1};
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
        assertEquals((short) 13, column.max());
        assertEquals(8.75, column.mean());
        assertEquals(9.0, column.median());
        assertEquals((short) 4, column.min());
        assertEquals((short) 4, column.mode());
        assertEquals((short) 9, column.range());
        assertEquals( 3.774917217635375, column.std());
        assertEquals(3.2691742076555053, column.populationSTD());
        assertEquals((short) 35, column.sum());
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
    public void asStringColumnTest() {
        StringColumn stringColumn = column.asStringColumn();
        assertEquals("4", stringColumn.get(0));
        assertEquals("10", stringColumn.get(1));
        assertEquals("8", stringColumn.get(2));
        assertEquals("13", stringColumn.get(3));
    }

    private ShortColumn otherColumn() {
        return ShortColumn.create("Test 2", new Short[]{10, 12, 9, 7});
    }
}
