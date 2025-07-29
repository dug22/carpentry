/*
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *
 */

package io.github.dug22.carpentry.column.transformations;

import io.github.dug22.carpentry.column.ColumnBiFunction;
import io.github.dug22.carpentry.column.ColumnException;
import io.github.dug22.carpentry.column.impl.DoubleColumn;
import io.github.dug22.carpentry.column.impl.NumericColumn;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

public interface NumberTransformations extends Transformation {

    Double getDouble(int i);

    /**
     * Adds a constant value to each element in the column.
     */
    default DoubleColumn add(double value) {
        return performOperation(Double::sum, '+', value);
    }

    /**
     * Adds each element of the given column to this column.
     */
    default DoubleColumn plus(NumericColumn<?> other) {
        return performOperation(other, Double::sum, '+');
    }

    /**
     * Adds the values of all provided columns element-wise to this column.
     */
    default DoubleColumn plus(List<NumericColumn<?>> others) {
        return performOperation(others, Double::sum, '+');
    }

    /**
     * Subtracts a constant value from each element in the column.
     */
    default DoubleColumn subtract(double value) {
        return performOperation((a, b) -> a - b, '-', value);
    }

    /**
     * Subtracts each element of the given column from this column.
     */
    default DoubleColumn minus(NumericColumn<?> other) {
        return performOperation(other, (a, b) -> a - b, '-');
    }

    /**
     * Subtracts all provided columns element-wise from this column.
     */
    default DoubleColumn minus(List<NumericColumn<?>> others) {
        return performOperation(others, (a, b) -> a - b, '-');
    }

    /**
     * Multiplies each element in the column by a constant.
     */
    default DoubleColumn multiply(double value) {
        return performOperation((a, b) -> a * b, '*', value);
    }

    /**
     * Multiplies each element of the given column with this column.
     */
    default DoubleColumn times(NumericColumn<?> other) {
        return performOperation(other, (a, b) -> a * b, '*');
    }

    /**
     * Multiplies all provided columns element-wise with this column.
     */
    default DoubleColumn times(List<NumericColumn<?>> others) {
        return performOperation(others, (a, b) -> a * b, '*');
    }

    /**
     * Divides each element in the column by a constant.
     */
    default DoubleColumn divide(double value) {
        return performOperation((a, b) -> a / b, '/', value);
    }

    /**
     * Divides each element of this column by the corresponding element in the given column.
     */
    default DoubleColumn divide(NumericColumn<?> other) {
        return performOperation(other, (a, b) -> a / b, '/');
    }

    /**
     * Raises each element to the given power.
     */
    default DoubleColumn pow(double value) {
        return performOperation(Math::pow, '^', value);
    }

    /**
     * Raises each element to the power of the corresponding value in the given column.
     */
    default DoubleColumn pow(NumericColumn<?> other) {
        return performOperation(other, Math::pow, '^');
    }

    /**
     * Squares each element in the column.
     */
    default DoubleColumn square() {
        DoubleColumn newDoubleColumn = pow(2);
        newDoubleColumn.setName(name() + " (square)");
        return newDoubleColumn;
    }

    /**
     * Cubes each element in the column.
     */
    default DoubleColumn cube() {
        DoubleColumn newDoubleColumn = pow(3);
        newDoubleColumn.setName(name() + " (cubed)");
        return newDoubleColumn;
    }

    /**
     * Converts each value to its percentage of the column's total sum.
     */
    default DoubleColumn toPercent() {
        double sum = 0;
        for (int i = 0; i < size(); i++) {
            if (!isAbsent(i)) {
                sum += getDouble(i);
            }
        }

        final double sumResult = sum;
        return performOperation((i) -> sumResult == 0 ? 0.0 : (i / sumResult) * 100, "%");
    }

    /**
     * Converts each value to its ratio of the column's total sum.
     */
    default DoubleColumn toRatio() {
        double sum = 0;
        for (int i = 0; i < size(); i++) {
            if (!isAbsent(i)) {
                sum += getDouble(i);
            }
        }

        final double sumResult = sum;
        return performOperation((i) -> sumResult == 0 ? 0.0 : i / sumResult, "ratio");
    }

    /**
     * Converts all values in the column to their absolute values.
     */
    default DoubleColumn toAbsolute() {
        return performOperation(Math::abs, "abs");
    }

    /**
     * Takes the square root of each value.
     */
    default DoubleColumn sqrt() {
        return performOperation(Math::sqrt, "sqrt");
    }

    /**
     * Negates each value in the column.
     */
    default DoubleColumn negative() {
        return performOperation((i) -> i * -1, "negative");
    }

    /**
     * Applies cosine to each value.
     */
    default DoubleColumn cos() {
        return performOperation(Math::cos, "cos");
    }

    /**
     * Applies sine to each value.
     */
    default DoubleColumn sin(){
        return performOperation(Math::sin, "sin");
    }

    /**
     * Applies tangent to each value.
     */
    default DoubleColumn tan(){
        return performOperation(Math::tan, "tan");
    }

    /**
     * Applies hyperbolic tangent to each value.
     */
    default DoubleColumn tanh(){
        return performOperation(Math::tanh, "tanh");
    }

    /**
     * Applies natural logarithm to each value.
     */
    default DoubleColumn logN(){
        return performOperation(Math::log, "logN");
    }

    /**
     * Applies base-10 logarithm to each value.
     */
    default DoubleColumn log10(){
        return performOperation(Math::log10, "log10");
    }

    /**
     * Applies natural logarithm of (1 + x) to each value.
     */
    default DoubleColumn log1p(){
        return performOperation(Math::log1p, "log1p");
    }

    /**
     * Rounds each value to the given number of decimal places.
     */
    default DoubleColumn round(int decimalPlaces){
        if(decimalPlaces < 0) throw new IllegalArgumentException("decimalPlace must be non-negative");
        double scale = Math.pow(10, decimalPlaces);
        return performOperation((i) -> (double) Math.round(i * scale) / scale, "rounded");
    }

    private DoubleColumn performOperation(NumericColumn<?> other, ColumnBiFunction<Double, Double, Double> operation, char symbol) {
        if (this.size() != other.size()) {
            throw new ColumnException("Both columns must have the same size");
        }

        Double[] resultData = new Double[size()];
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index) || other.isAbsent(index)) {
                resultData[index] = Nulls.getDefaultNullValue(Double.class);;
            } else {
                Double result = operation.apply(getDouble(index), other.getDouble(index));
                resultData[index] = result;
            }
        }

        String resultName = name() + " " + symbol + " " + other.name();
        return DoubleColumn.create(resultName, resultData);
    }

    private DoubleColumn performOperation(List<NumericColumn<?>> others, ColumnBiFunction<Double, Double, Double> operation, char symbol) {
        if (size() != others.getFirst().size()) throw new ColumnException("All columns must have the same size!");
        Double[] resultData = new Double[size()];
        for (int index = 0; index < size(); index++) {
            Double result = getDouble(index);
            for (NumericColumn<?> other : others) {
                if (isAbsent(index) || other.isAbsent(index)) {
                    result = Nulls.getDefaultNullValue(Double.class);;
                    continue;
                }

                result = operation.apply(result, other.getDouble(index));
            }
            resultData[index] = result;
        }

        String resultName = name() + " " + symbol + " " + String.join(" + ", others.stream().map(NumericColumn::name).toArray(String[]::new));
        return DoubleColumn.create(resultName, resultData);
    }

    private DoubleColumn performOperation(ColumnBiFunction<Double, Double, Double> function, char symbol, double value) {
        Double[] resultData = new Double[size()];
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) resultData[index] = Nulls.getDefaultNullValue(Double.class);
            else resultData[index] = function.apply(getDouble(index), value);
        }
        return DoubleColumn.create(name() + " " + symbol + " " + value, resultData);
    }

    private DoubleColumn performOperation(DoubleUnaryOperator operation, String operationName){
        Double[] resultData = new Double[size()];
        for (int index = 0; index < size(); index++) {
            if (isAbsent(index)) {
                resultData[index] = Nulls.getDefaultNullValue(Double.class);;
            } else {
                resultData[index] = operation.applyAsDouble(getDouble(index));
            }
        }
        return DoubleColumn.create(name() + " (" + operationName + ")", resultData);
    }
}
