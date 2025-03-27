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

import io.github.dug22.carpentry.utility.NumberUtil;

import java.util.*;

public interface StatisticFunctions<T extends Number> {

    int size();

    T get(int index);

    String getColumnName();

    Class<T> getColumnType();

    boolean isNA(int index);


    /**
     * Returns the maximum of all values in this column
     *
     * @return minimum of all values
     */
    default T max() {
        double max = Double.NEGATIVE_INFINITY;
        int naCount = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (get(i) == null) {
                naCount++;
                continue;
            }
            max = Math.max(max, get(i).doubleValue());
        }

        if (naCount > 0) {
            System.out.printf("max() ignored %d NA", naCount);
        }

        return NumberUtil.convert(max, getColumnType());
    }

    /**
     * Returns the mean of all values in this column
     *
     * @return mean of all values
     */
    default Double mean() {
        int naCount = 0;
        double sum = 0d;
        int count = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (isNA(i)) {
                naCount++;
                continue;
            }

            count++;
            sum += get(i).doubleValue();
        }

        if (naCount > 0) {
            System.out.printf("mean() ignored %d NA", naCount);
        }

        return sum / count;
    }

    /**
     * Returns the median of all values in this column
     *
     * @return median of all values
     */
    default Double median() {
        List<Double> nonNullValues = new ArrayList<>();
        int naCount = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (isNA(i)) {
                naCount++;
                continue;
            }

            nonNullValues.add(get(i).doubleValue());
        }

        if (naCount > 0) {
            System.out.printf("median() ignored %d NA", naCount);
        }

        Collections.sort(nonNullValues);
        int n = nonNullValues.size();
        if (n == 0) {
            return Double.NaN;
        }

        if (n % 2 != 0) {
            return nonNullValues.get(n / 2);
        } else {
            return (nonNullValues.get(n / 2 - 1) + nonNullValues.get(n / 2)) / 2;
        }
    }

    /**
     * Returns the minimum of all values in this column
     *
     * @return minimum of all values
     */
    default T min() {
        double min = Double.MAX_VALUE;
        int naCount = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (isNA(i)) {
                naCount++;
                continue;
            }

            min = Math.min(min, get(i).doubleValue());
        }

        if (naCount > 0) {
            System.out.printf("min() ignored %d NA", naCount);
        }

        return NumberUtil.convert(min, getColumnType());
    }

    /**
     * Returns the mode of all values in this column
     *
     * @return mode of all values
     */
    default T mode() {
        Map<T, Integer> frequencyMap = new HashMap<>();
        int naCount = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (isNA(i)) {
                naCount++;
                continue;
            }

            frequencyMap.put(get(i), frequencyMap.getOrDefault(get(i), 0) + 1);
        }

        if (naCount > 0) {
            System.out.printf("mode() ignored %d NA", naCount);
        }

        T mode = null;
        int maxCount = 0;

        for (Map.Entry<T, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                mode = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return NumberUtil.convert(mode, getColumnType());
    }

    /**
     * Returns the range of all values in this column
     *
     * @return range of all values
     */
    default T range() {
        double range = max().doubleValue() - min().doubleValue();
        return NumberUtil.convert(range, getColumnType());
    }


    /**
     * Returns the sample standard deviation of all values in this column
     *
     * @return the sample standard deviation of all values
     */
    default Double std() {
        double variance = variance();
        return !Double.isNaN(variance) ? Math.sqrt(variance) : Double.NaN;
    }

    /**
     * Returns the population standard deviation of all values in this column
     *
     * @return the population standard deviation of all values
     */
    default Double populationSTD() {
        double variance = populationVariance();
        return !Double.isNaN(variance) ? Math.sqrt(variance) : Double.NaN;
    }

    /**
     * Returns the sum of all values in this column
     *
     * @return the sum of all values
     */
    default T sum() {
        int naCount = 0;
        double sum = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (isNA(i)) {
                naCount++;
                continue;
            }

            sum += get(i).doubleValue();
        }

        if (naCount > 0) {
            System.out.printf("sum() ignored %d NA", naCount);
        }

        return NumberUtil.convert(sum, getColumnType());
    }


    /**
     * Calculates the variance of the column using sample variance (default degrees of freedom = 1).
     *
     * @return the variance of the column, or NaN if the mean is NaN
     */
    private Double variance() {
        return variance(1);
    }

    /**
     * Calculates the population variance of the column (degrees of freedom = 0).
     *
     * @return the population variance of the column, or NaN if the mean is NaN
     */
    private Double populationVariance() {
        return variance(0);
    }

    /**
     * Calculates the variance of the column with a specified degrees of freedom.
     *
     * @param ddof the degrees of freedom to use (1 for sample variance, 0 for population variance)
     * @return the variance of the column, or NaN if the mean is NaN or insufficient data
     */
    private Double variance(int ddof) {
        double mean = mean();
        if (Double.isNaN(mean)) {
            return Double.NaN;
        }

        double sumOfSquares = 0d;
        int count = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (isNA(i)) {
                continue;
            }

            sumOfSquares += Math.pow(get(i).doubleValue() - mean, 2);
            count++;
        }

        return count > 1 ? sumOfSquares / (count - ddof) : Double.NaN;
    }
}
