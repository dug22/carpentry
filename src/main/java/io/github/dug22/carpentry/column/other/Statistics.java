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

package io.github.dug22.carpentry.column.other;

import io.github.dug22.carpentry.utils.Nulls;

import java.util.*;

public interface Statistics {

    private Integer getDefaultNullValue() {
        return Nulls.getDefaultNullValue(Integer.class);
    }

    private Double getDefaultValue() {
        return Nulls.getDefaultNullValue(Double.class);
    }

    int size();

    Double getDouble(int index);

    String name();

    boolean isAbsent(int index);

    /**
     * Counts how many values are within a column
     *
     * @return count of values
     */
    default int count() {
        return size();
    }

    /**
     * Counts how many null values are within a column
     *
     * @return null count of values
     */
    default int countNull() {
        int naCount = 0;
        int currentSize = size();
        for (int index = 0; index < currentSize; index++) {
            if (isAbsent(index)) {
                naCount++;
            }
        }

        return naCount;
    }

    /**
     * Counts how many non-null values are within a column
     *
     * @return non-null count of values
     */
    default int countNonNull() {
        return size() - countNull();
    }

    /**
     * Returns the maximum of all values in this column
     *
     * @return minimum of all values
     */
    default double max() {
        double max = Double.NEGATIVE_INFINITY;
        int naCount = 0;
        int currentSize = size();
        for (int index = 0; index < currentSize; index++) {
            if (isAbsent(index)) {
                naCount++;
                continue;
            }

            max = Math.max(max, getDouble(index));
        }

        if (naCount > 0) {
            System.out.printf("max() ignored %d missing elements%n", naCount);
        }

        return max;
    }

    /**
     * Returns the mean of all values in this column
     *
     * @return mean of all values
     */
    default double mean() {
        int naCount = 0;
        double sum = 0d;
        int count = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (isAbsent(i)) {
                naCount++;
                continue;
            }

            count++;
            sum += getDouble(i);
        }

        if (naCount > 0) {
            System.out.printf("mean() ignored %d NA%n", naCount);
        }

        return sum / count;
    }

    /**
     * Returns the 1st quantile of the data within this column
     *
     * @return the 1st quantile of the data within this column
     */
    default double quantile1() {
        return getPercentile("quantile1", 25);
    }

    /**
     * Returns the median or 50 percentile of the data within this column
     *
     * @return median of all values
     */
    default double median() {
        return getPercentile("median", 50);
    }

    /**
     * Returns the 3rd quantile of the data within this column
     *
     * @return the 3rd quantile of the data within this column
     */
    default double quantile3() {
        return getPercentile("quantile3", 75);
    }


    /**
     * Returns the minimum of all values in this column
     *
     * @return minimum of all values
     */
    default double min() {
        double min = Double.MAX_VALUE;
        int naCount = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (isAbsent(i)) {
                naCount++;
                continue;
            }

            min = Math.min(min, getDouble(i));
        }

        if (naCount > 0) {
            System.out.printf("min() ignored %d NA%n", naCount);
        }

        return min;
    }

    /**
     * Returns the mode of all values in this column
     *
     * @return mode of all values
     */
    default double mode() {
        Map<Double, Integer> frequencyMap = new HashMap<>();
        int naCount = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (isAbsent(i)) {
                naCount++;
                continue;
            }

            frequencyMap.put(getDouble(i), frequencyMap.getOrDefault(getDouble(i), 0) + 1);
        }

        if (naCount > 0) {
            System.out.printf("mode() ignored %d NA%n", naCount);
        }

        double mode = -1;
        int maxCount = 0;

        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                mode = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return mode;
    }

    /**
     * Returns the range of all values in this column
     *
     * @return range of all values
     */
    default double range() {
        return max() - min();
    }

    /**
     * Returns the sample standard deviation of all values in this column
     *
     * @return the sample standard deviation of all values
     */
    default double std() {
        double variance = variance();
        return !Double.isNaN(variance) ? Math.sqrt(variance) : Double.NaN;
    }

    /**
     * Returns the population standard deviation of all values in this column
     *
     * @return the population standard deviation of all values
     */
    default double populationSTD() {
        double variance = populationVariance();
        return !Double.isNaN(variance) ? Math.sqrt(variance) : Double.NaN;
    }

    /**
     * Returns the sum of all values in this column
     *
     * @return the sum of all values
     */
    default double sum() {
        int naCount = 0;
        double sum = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (isAbsent(i)) {
                naCount++;
                continue;
            }

            sum += getDouble(i);
        }

        if (naCount > 0) {
            System.out.printf("sum() ignored %d NA%n", naCount);
        }

        return sum;
    }


    /**
     * Calculates the variance of the column using sample variance (default degrees of freedom = 1).
     *
     * @return the variance of the column, or NaN if the mean is NaN
     */
    private double variance() {
        return variance(1);
    }

    /**
     * Calculates the population variance of the column (degrees of freedom = 0).
     *
     * @return the population variance of the column, or NaN if the mean is NaN
     */
    private double populationVariance() {
        return variance(0);
    }

    /**
     * Calculates the variance of the column with a specified degrees of freedom.
     *
     * @param ddof the degrees of freedom to use (1 for sample variance, 0 for population variance)
     * @return the variance of the column, or NaN if the mean is NaN or insufficient data
     */
    private double variance(int ddof) {
        double mean = mean();
        if (Double.isNaN(mean) || mean == 0) {
            return Double.NaN;
        }

        double sumOfSquares = 0d;
        int count = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            if (isAbsent(i)) {
                continue;
            }

            sumOfSquares += Math.pow(getDouble(i) - mean, 2);
            count++;
        }
        return count > 1 ? sumOfSquares / (count - ddof) : Double.NaN;
    }

    /**
     * Calculates the given percentile of the non-missing column values using linear interpolation.
     *
     * @param percentile value between 0 and 100
     * @return percentile value
     */
    private double getPercentile(String methodName, double percentile) {
        int naCount = 0;
        List<Double> values = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (!isAbsent(i)) {
                values.add(getDouble(i));
            } else {
                naCount++;
            }
        }

        if (values.isEmpty()) return Double.NaN;
        Collections.sort(values);
        double index = (percentile / 100) * (values.size() - 1);
        int lower = (int) Math.floor(index);
        int upper = (int) Math.ceil(index);

        if (lower == upper) {
            return values.get(lower);
        }

        if (naCount > 0) {
            System.out.printf("%s() ignored %d NA%n", methodName, naCount);

        }
        double weight = index - lower;
        return values.get(lower) * (1 - weight) + values.get(upper) * weight;
    }

    /**
     * Performs the given skew formula
     * @param formula the passed formula
     * @return the skew result
     */
    default double skew(SkewnessFormula formula) {
        int naCount = 0;
        int size = size();
        List<Double> values = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (isAbsent(i)) {
                naCount++;
                continue;
            }

            values.add(getDouble(i));
        }
        if (values.size() < 2) {
            return Double.NaN;
        }

        if (naCount > 0) {
            System.out.printf("skew() ignored %d NA%n", naCount);
        }

        switch (formula) {
            case FISHER_PEARSON -> {
                return pearsonSkewness(values);
            }
            case BOWLEY -> {
                return bowleySkewness(values);
            }
            case KELLY -> {
                return kellySkewness(values);
            }
            default -> throw new IllegalArgumentException("Unknown formula: " + formula.name());
        }
    }

    /**
     * Calculates the Pearson's moment coefficient of skewness for the given list of values.
     * Requires at least 3 values.
     *
     * @param values a List of Double values to calculate skewness from
     * @return the Pearson skewness coefficient, or Double.NaN if calculation is not possible
     */
    private double pearsonSkewness(List<Double> values) {
        int n = values.size();
        if (n < 3) return Double.NaN;

        double mean = mean();
        double stdDev = std();

        if (Double.isNaN(mean) || Double.isNaN(stdDev) || stdDev == 0) {
            return Double.NaN;
        }

        double sumCubed = 0.0;
        for (double value : values) {
            double diff = value - mean;
            sumCubed += diff * diff * diff;
        }

        return (n * sumCubed) / ((n - 1.0) * (n - 2.0) * Math.pow(stdDev, 3));
    }

    /**
     * Calculates Bowley's skewness coefficient using quartiles.
     * Requires the values list to be non-empty.
     *
     * @param values a List of Double values to calculate skewness from
     * @return the Bowley skewness coefficient, or Double.NaN if calculation is not possible
     */
    private double bowleySkewness(List<Double> values) {
        if (values.isEmpty()) {
            return Double.NaN;
        }
        Collections.sort(values);
        double q1 = getPercentile("quantile1", 25);
        double median = getPercentile("median", 50);
        double q3 = getPercentile("quantile3", 75);

        double denominator = q3 - q1;
        if (denominator == 0) {
            return Double.NaN;
        }

        return (q3 - 2 * median + q1) / denominator;
    }

    /**
     * Calculates Kelly's skewness coefficient using percentiles (10th, 50th, 90th).
     * Requires the values list to be non-empty.
     *
     * @param values a List of Double values to calculate skewness from
     * @return the Kelly skewness coefficient, or Double.NaN if calculation is not possible
     */
    private double kellySkewness(List<Double> values) {
        if (values.isEmpty()) {
            return Double.NaN;
        }
        Collections.sort(values);
        double p10 = getPercentile("percentile10", 10);
        double median = getPercentile("median", 50);
        double p90 = getPercentile("percentile90", 90);

        double denominator = p90 - p10;
        if (denominator == 0) {
            return Double.NaN;
        }

        return (p90 - 2 * median + p10) / denominator;
    }
}
