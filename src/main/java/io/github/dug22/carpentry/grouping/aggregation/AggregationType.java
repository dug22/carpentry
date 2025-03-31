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

package io.github.dug22.carpentry.grouping.aggregation;

import java.util.List;
import java.util.Objects;

public enum AggregationType {

    COUNT {
        @Override
        public Object aggregate(List<Object> values) {
            printNACount(getFunctionName(), values);
            int count = 0;
            for (Object value : values) {
                if (value == null) {
                    continue;
                }
                count++;
            }
            return count;
        }

        @Override
        public String getFunctionName() {
            return "count";
        }
    },

    MAX {
        @Override
        public Object aggregate(List<Object> values) {
            printNACount(getFunctionName(), values);
            return values.stream()
                    .filter(Objects::nonNull)
                    .mapToDouble(value -> ((Number) value).doubleValue())
                    .max()
                    .orElse(Double.NaN);
        }

        @Override
        public String getFunctionName() {
            return "max";
        }
    },

    MEAN {
        @Override
        public Object aggregate(List<Object> values) {
            printNACount(getFunctionName(), values);
            return values.stream()
                    .filter(Objects::nonNull)
                    .map(value -> ((Number) value).doubleValue())
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(Double.NaN);
        }

        @Override
        public String getFunctionName() {
            return "mean";
        }
    },

    MIN {
        @Override
        public Object aggregate(List<Object> values) {
            printNACount(getFunctionName(), values);
            return values.stream()
                    .filter(Objects::nonNull)
                    .mapToDouble(value -> ((Number) value).doubleValue())
                    .min()
                    .orElse(Double.NaN);
        }

        @Override
        public String getFunctionName() {
            return "min";
        }
    },

    STD(1) {
        @Override
        public Object aggregate(List<Object> values) {
            return computeStandardDeviation(values, ddof, getFunctionName());
        }

        @Override
        public String getFunctionName() {
            return "std";
        }
    },

    STD_POPULATION(0) {
        @Override
        public Object aggregate(List<Object> values) {
            return computeStandardDeviation(values, ddof, getFunctionName());
        }

        @Override
        public String getFunctionName() {
            return "std_population";
        }
    },

    SUM {
        @Override
        public Object aggregate(List<Object> values) {
            printNACount(getFunctionName(), values);
            return values.stream()
                    .filter(Objects::nonNull)
                    .mapToDouble(value -> ((Number) value).doubleValue())
                    .sum();
        }

        @Override
        public String getFunctionName() {
            return "sum";
        }
    };

    protected final int ddof;

    AggregationType(int ddof) {
        this.ddof = ddof;
    }

    AggregationType() {
        this.ddof = -1;
    }

    public abstract Object aggregate(List<Object> values);

    public abstract String getFunctionName();

    private static void printNACount(String aggFunction, List<Object> values) {
        int naCount = (int) values.stream().filter(Objects::isNull).count();
        if (naCount > 0) {
            System.out.printf(aggFunction + "() ignored %d NA values%n", naCount);
        }
    }

    private static Object computeStandardDeviation(List<Object> values, int ddof, String functionName) {
        printNACount(functionName, values);
        List<Double> validValues = values.stream()
                .filter(Objects::nonNull)
                .map(value -> ((Number) value).doubleValue())
                .toList();
        double mean = validValues.stream()
                .mapToDouble(Number::doubleValue)
                .average()
                .orElse(Double.NaN);
        double sumOfSquares = validValues.stream()
                .mapToDouble(Number::doubleValue)
                .map(value -> Math.pow(value - mean, 2))
                .sum();
        int count = validValues.size();
        return count > ddof ? Math.sqrt(sumOfSquares / (count - ddof)) : Double.NaN;
    }
}