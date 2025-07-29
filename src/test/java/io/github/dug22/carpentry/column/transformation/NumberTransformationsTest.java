package io.github.dug22.carpentry.column.transformation;

import io.github.dug22.carpentry.column.impl.DoubleColumn;
import io.github.dug22.carpentry.column.impl.NumericColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class NumberTransformationsTest {

    private DoubleColumn doubleColumn;

    @BeforeEach
    public void setup(){
        doubleColumn = DoubleColumn.create("Doubles", new Double[]{1D, 2D, 3D});
    }

    @Test
    public void addTest(){
        DoubleColumn resultingColumn = doubleColumn.add(3);
        assertArrayEquals(new Double[]{4D, 5D, 6D}, resultingColumn.getValues());
    }

    @Test
    public void plusTest(){
        DoubleColumn resultingColumn = doubleColumn.plus(DoubleColumn.create("More Doubles", new Double[]{1d,2d,3d}));
        assertArrayEquals(new Double[]{2d, 4d, 6d}, resultingColumn.getValues());

        List<NumericColumn<?>> doubleColumnList = List.of(
                DoubleColumn.create("Doubles 1", new Double[]{1d, 2d, 3d}),
                DoubleColumn.create("Doubles 2", new Double[]{1d, 2d, 3d}),
                DoubleColumn.create("Doubles 3", new Double[]{1d, 2d, 3d})
        );

        resultingColumn = doubleColumn.plus(doubleColumnList);
        assertArrayEquals(new Double[]{4d, 8d, 12d}, resultingColumn.getValues());
    }

    @Test
    public void subtractTest(){
        DoubleColumn resultingColumn = doubleColumn.subtract(3);
        assertArrayEquals(new Double[]{-2d, -1d, 0d}, resultingColumn.getValues());
    }

    @Test
    public void minusTest(){
        DoubleColumn resultingColumn = doubleColumn.minus(DoubleColumn.create("More Doubles", new Double[]{1d,2d,3d}));
        assertArrayEquals(new Double[]{0d, 0d, 0d}, resultingColumn.getValues());

        List<NumericColumn<?>> doubleColumnList = List.of(
                DoubleColumn.create("Doubles 1", new Double[]{1d, 2d, 3d}),
                DoubleColumn.create("Doubles 2", new Double[]{1d, 2d, 3d}),
                DoubleColumn.create("Doubles 3", new Double[]{1d, 2d, 3d})
        );

        resultingColumn = doubleColumn.minus(doubleColumnList);
        assertArrayEquals(new Double[]{-2d, -4d, -6d}, resultingColumn.getValues());
    }

    @Test
    public void multiplyTest(){
        DoubleColumn resultingColumn = doubleColumn.multiply(3);
        assertArrayEquals(new Double[]{3d, 6d, 9d}, resultingColumn.getValues());
    }

    @Test
    public void timesTest(){
        DoubleColumn resultingColumn = doubleColumn.times(DoubleColumn.create("More Doubles", new Double[]{1d,2d,3d}));
        assertArrayEquals(new Double[]{1d, 4d, 9d}, resultingColumn.getValues());

        List<NumericColumn<?>> doubleColumnList = List.of(
                DoubleColumn.create("Doubles 1", new Double[]{1d, 2d, 3d}),
                DoubleColumn.create("Doubles 2", new Double[]{1d, 2d, 3d}),
                DoubleColumn.create("Doubles 3", new Double[]{1d, 2d, 3d})
        );

        resultingColumn = doubleColumn.times(doubleColumnList);
        assertArrayEquals(new Double[]{1d, 16d, 81d}, resultingColumn.getValues());
    }

    @Test
    public void divideTest(){
        DoubleColumn resultingColumn = doubleColumn.divide(1);
        assertArrayEquals(new Double[]{1d, 2d, 3d}, resultingColumn.getValues());
        resultingColumn = doubleColumn.divide(DoubleColumn.create("More Doubles", new Double[]{1d, 2d, 3d}));
        assertArrayEquals(new Double[]{1d, 1d, 1d}, resultingColumn.getValues());
    }

    @Test
    public void powTest(){
        DoubleColumn resultingColumn = doubleColumn.pow(2);
        assertArrayEquals(new Double[]{1d, 4d, 9d}, resultingColumn.getValues());
        resultingColumn = doubleColumn.pow(DoubleColumn.create("More Doubles", new Double[]{1d, 2d, 3d}));
        assertArrayEquals(new Double[]{1d, 4d, 27d}, resultingColumn.getValues());
    }

    @Test
    public void squareTest() {
        DoubleColumn resultingColumn = doubleColumn.square();
        assertArrayEquals(new Double[]{1D, 4D, 9D}, resultingColumn.getValues());
    }

    @Test
    public void cubeTest() {
        DoubleColumn resultingColumn = doubleColumn.cube();
        assertArrayEquals(new Double[]{1D, 8D, 27D}, resultingColumn.getValues());
    }

    @Test
    public void toPercentTest() {
        DoubleColumn resultingColumn = doubleColumn.toPercent();
        assertArrayEquals(new Double[]{16.666666666666664, 33.33333333333333, 50.0}, resultingColumn.getValues());
    }

    @Test
    public void toPercentZeroSumTest() {
        doubleColumn = DoubleColumn.create("Doubles", new Double[]{0D, 0D, 0D});
        DoubleColumn resultingColumn = doubleColumn.toPercent();
        assertArrayEquals(new Double[]{0D, 0D, 0D}, resultingColumn.getValues());
    }

    @Test
    public void toRatioTest() {
        DoubleColumn resultingColumn = doubleColumn.toRatio();
        assertArrayEquals(new Double[]{0.16666666666666666, 0.3333333333333333, 0.5}, resultingColumn.getValues());
    }

    @Test
    public void toRatioZeroSumTest() {
        doubleColumn = DoubleColumn.create("Doubles", new Double[]{0D, 0D, 0D});
        DoubleColumn resultingColumn = doubleColumn.toRatio();
        assertArrayEquals(new Double[]{0D, 0D, 0D}, resultingColumn.getValues());
    }

    @Test
    public void toAbsoluteTest() {
        doubleColumn = DoubleColumn.create("Doubles", new Double[]{-1D, -2D, 3D});
        DoubleColumn resultingColumn = doubleColumn.toAbsolute();
        assertArrayEquals(new Double[]{1D, 2D, 3D}, resultingColumn.getValues());
    }

    @Test
    public void sqrtTest() {
        DoubleColumn resultingColumn = doubleColumn.sqrt();
        assertArrayEquals(new Double[]{1.0, 1.4142135623730951, 1.7320508075688772}, resultingColumn.getValues());
    }

    @Test
    public void negativeTest() {
        DoubleColumn resultingColumn = doubleColumn.negative();
        assertArrayEquals(new Double[]{-1D, -2D, -3D}, resultingColumn.getValues());
    }

    @Test
    public void cosTest() {
        DoubleColumn resultingColumn = doubleColumn.cos();
        assertArrayEquals(new Double[]{Math.cos(1), Math.cos(2), Math.cos(3)}, resultingColumn.getValues());
    }

    @Test
    public void sinTest() {
        DoubleColumn resultingColumn = doubleColumn.sin();
        assertArrayEquals(new Double[]{Math.sin(1), Math.sin(2), Math.sin(3)}, resultingColumn.getValues());
    }

    @Test
    public void tanTest() {
        DoubleColumn resultingColumn = doubleColumn.tan();
        assertArrayEquals(new Double[]{Math.tan(1), Math.tan(2), Math.tan(3)}, resultingColumn.getValues());
    }

    @Test
    public void tanhTest() {
        DoubleColumn resultingColumn = doubleColumn.tanh();
        assertArrayEquals(new Double[]{Math.tanh(1), Math.tanh(2), Math.tanh(3)}, resultingColumn.getValues());
    }

    @Test
    public void logNTest() {
        DoubleColumn resultingColumn = doubleColumn.logN();
        assertArrayEquals(new Double[]{Math.log(1), Math.log(2), Math.log(3)}, resultingColumn.getValues());
    }

    @Test
    public void log10Test() {
        DoubleColumn resultingColumn = doubleColumn.log10();
        assertArrayEquals(new Double[]{Math.log10(1), Math.log10(2), Math.log10(3)}, resultingColumn.getValues());
    }

    @Test
    public void log1pTest() {
        DoubleColumn resultingColumn = doubleColumn.log1p();
        assertArrayEquals(new Double[]{Math.log1p(1), Math.log1p(2), Math.log1p(3)}, resultingColumn.getValues());
    }

    @Test
    public void roundTest() {
        doubleColumn = DoubleColumn.create("Doubles", new Double[]{1.23456D, 2.34567D, 3.45678D});
        DoubleColumn resultingColumn = doubleColumn.round(2);
        assertArrayEquals(new Double[]{1.23D, 2.35D, 3.46D}, resultingColumn.getValues());
    }
}
