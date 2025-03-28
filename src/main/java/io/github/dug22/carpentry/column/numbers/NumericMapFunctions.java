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

package io.github.dug22.carpentry.column.numbers;

import io.github.dug22.carpentry.column.ColumnException;
import io.github.dug22.carpentry.columns.DoubleColumn;
import io.github.dug22.carpentry.columns.NumberColumn;
import io.github.dug22.carpentry.columns.NumericColumn;
import io.github.dug22.carpentry.utility.NumberUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public interface NumericMapFunctions<T extends Number> {

    int size();

    T get(int index);

    String getColumnName();

    boolean isNA(int index);

    boolean isNA(T value);

    Class<T> getColumnType();

    /**
     * Adds the values of this column and another column.
     *
     * @param other the column to add
     * @return a new column with the sum of values
     */
    default NumberColumn plus(NumericColumn<T> other) {
        return performOperation(other, Double::sum, '+');
    }

    /**
     * Adds the values of this column and other multiple columns.
     *
     * @param others the columns to add
     * @return a new column with the sum of values
     */
    default NumberColumn plus(List<NumericColumn<T>> others) {
        return performOperation(others, Double::sum, '+');
    }

    /**
     * Subtracts the values of this column and another column.
     *
     * @param other the column to subtract
     * @return a new column with the difference of values
     */
    default NumberColumn minus(NumericColumn<T> other) {
        return performOperation(other, (a, b) -> a - b, '-');
    }

    /**
     * Subtracts the values of this column and other multiple columns.
     *
     * @param others the columns to subtract
     * @return a new column with the difference of values
     */
    default NumberColumn minus(List<NumericColumn<T>> others){
        return performOperation(others, (a, b) -> a-b, '-');
    }

    /**
     * Multiplies the values of this column and another column.
     *
     * @param other the column to multiply
     * @return a new column with the product of values
     */
    default NumberColumn times(NumericColumn<T> other) {
        return performOperation(other, (a, b) -> a * b, '*');
    }

    /**
     * Multiplies the values of this column and other multiple columns.
     *
     * @param others the columns to multiply
     * @return a new column with the product of values
     */
    default NumberColumn times(List<NumericColumn<T>> others){
        return performOperation(others, (a, b) -> a * b, '*');
    }

    /**
     * Divides the values of this column by another column.
     *
     * @param other the column to divide by
     * @return a new column with the quotient of values
     */
    default NumberColumn divide(NumericColumn<T> other) {
        return performOperation(other, (a, b) -> a / b, '/');
    }

    /**
     * Divides the values of this column and other multiple columns.
     *
     * @param others the columns to divide by
     * @return a new column with the quotient of values
     */
    default NumberColumn divide(List<NumericColumn<T>> others){
        return performOperation(others, (a, b) -> a / b, '/');
    }

    /**
     * Raises the values of this column to the power of values in another column.
     *
     * @param other the column containing exponents
     * @return a new column with the power of values
     */
    default NumberColumn pow(NumericColumn<T> other) {
        return performOperation(other, Math::pow, '^');
    }

    /**
     * Converts each value in the column to its ratio relative to the sum of all values.
     *
     * @return a new column with the ratio of each value to the total sum
     */
    default DoubleColumn asRatio() {
        double sum = 0;
        for (int i = 0; i < size(); i++) {
            if (!isNA(i)) {
                sum += get(i).doubleValue();
            }
        }

        List<Double> resultData = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (isNA(i) || sum == 0) {
                resultData.add(null);
            } else {
                double ratio = get(i).doubleValue() / sum;
                resultData.add(ratio);
            }
        }

        Double[] finalResultData = resultData.toArray(new Double[0]);
        return DoubleColumn.create(getColumnName() + " [ratio]", finalResultData);
    }

    /**
     * Converts each value in the column to its percentage relative to the sum of all values.
     *
     * @return a new column with the percentage of each value relative to the total sum
     */
    default DoubleColumn asPercent() {
        double sum = 0;
        for (int i = 0; i < size(); i++) {
            if (!isNA(i)) {
                sum += get(i).doubleValue();
            }
        }

        List<Double> resultData = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (isNA(i) || sum == 0) {
                resultData.add(null);
            } else {
                double percent = (get(i).doubleValue() / sum) * 100;
                resultData.add(percent);
            }
        }

        Double[] finalResultData = resultData.toArray(new Double[0]);
        return DoubleColumn.create(getColumnName() + " [percent]", finalResultData);
    }

    /**
     * Converts each value in the column to its absolute value (positive number).
     *
     * @return a new column with the absolute values of each element
     */
    default DoubleColumn absolute() {
        List<Double> resultData = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (isNA(i)) {
                resultData.add(null);
            } else {
                resultData.add(Math.abs(get(i).doubleValue()));
            }
        }

        Double[] finalResultData = resultData.toArray(new Double[0]);
        return DoubleColumn.create(getColumnName() + " [abs]", finalResultData);
    }

    /**
     * Helper method to perform an arithmetic operation between two numeric columns.
     *
     * @param other           the column to perform the operation with
     * @param operation       the arithmetic operation to apply (e.g., sum, subtraction, etc.)
     * @param operationSymbol the symbol representing the operation (e.g., '+', '-', etc.)
     * @return a new column with the result of applying the operation between the two columns
     */
    private NumberColumn performOperation(NumericColumn<T> other, BiFunction<Double, Double, Double> operation, char operationSymbol) {
        if (this.size() != other.size()) {
            throw new ColumnException("Columns must have the same size.");
        }

        List<Number> numbersList = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (isNA(get(i)) || isNA(other.get(i))) {
                numbersList.add(null);
            } else {
                Double result = operation.apply(get(i).doubleValue(), other.get(i).doubleValue());
                numbersList.add(NumberUtil.convert(result, getColumnType()));
            }
        }

        Number[] resultValues = numbersList.toArray(new Number[0]);
        String resultColumnName = getColumnName() + " " + operationSymbol + " " + other.getColumnName();
        return NumberColumn.create(resultColumnName, resultValues);
    }

    private NumberColumn performOperation(List<NumericColumn<T>> others, BiFunction<Double, Double, Double> operation, char operationSymbol) {
        if (this.size() != others.getFirst().size()) {
            throw new IllegalArgumentException("Columns must have the same size.");
        }

        List<Number> numbersList = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            Double result = get(i).doubleValue();
            for (NumericColumn<T> other : others) {
                if (isNA(get(i)) || isNA(other.get(i))) {
                    result = Double.NaN;
                    continue;
                }
                result = operation.apply(result, other.get(i).doubleValue());
            }

            numbersList.add(NumberUtil.convert(result, getColumnType()));
        }

        Number[] resultValues = numbersList.toArray(new Number[0]);
        String resultColumnName = getColumnName() + " " + operationSymbol + " " + String.join(" + ", others.stream().map(NumericColumn::getColumnName).toArray(String[]::new));
        return NumberColumn.create(resultColumnName, resultValues);
    }
}
