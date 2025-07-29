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

package io.github.dug22.carpentry.aggregation;

import io.github.dug22.carpentry.column.Column;
import io.github.dug22.carpentry.column.ColumnType;
import io.github.dug22.carpentry.column.ColumnTypes;
import io.github.dug22.carpentry.column.impl.DoubleColumn;
import io.github.dug22.carpentry.column.impl.IntegerColumn;
import io.github.dug22.carpentry.utils.Nulls;

import java.util.List;
import java.util.Objects;

public enum AggregationType {

    COUNT {
        @Override
        public Object aggregate(List<Object> values) {
            printNACount(getFunctionName(), values);
            int count = 0;
            for (Object value : values) {
                if (Nulls.isNull(value)) continue;
                count++;
            }
            return count;
        }

        @Override
        public String getFunctionName() {
            return "count";
        }

        @Override
        public ColumnType columnType() {
            return ColumnTypes.INTEGER_COLUMN_TYPE;
        }
        @Override
        public Column<?> createEmptyAggregationColumn(String name) {
            return new IntegerColumn(name);
        }
    },

    MAX {
        @Override
        public Object aggregate(List<Object> values) {
            printNACount(getFunctionName(), values);
            return values.stream()
                    .filter(i -> !Nulls.isNull(i))
                    .mapToDouble(value -> ((Number) value).doubleValue())
                    .max()
                    .orElse(Double.NaN);
        }

        @Override
        public String getFunctionName() {
            return "max";
        }

        @Override
        public ColumnType columnType() {
            return ColumnTypes.DOUBLE_COLUMN_TYPE;
        }

        @Override
        public Column<?> createEmptyAggregationColumn(String name) {
            return new DoubleColumn(name);
        }
    },

    MEAN {
        @Override
        public Object aggregate(List<Object> values) {
            printNACount(getFunctionName(), values);
            return values.stream()
                    .filter(i -> !Nulls.isNull(i))
                    .map(value -> ((Number) value).doubleValue())
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(Double.NaN);
        }

        @Override
        public String getFunctionName() {
            return "mean";
        }

        @Override
        public ColumnType columnType() {
            return ColumnTypes.DOUBLE_COLUMN_TYPE;
        }

        @Override
        public Column<?> createEmptyAggregationColumn(String name) {
            return new DoubleColumn(name);
        }
    },

    MIN {
        @Override
        public Object aggregate(List<Object> values) {
            printNACount(getFunctionName(), values);
            return values.stream()
                    .filter(i -> !Nulls.isNull(i))
                    .mapToDouble(value -> ((Number) value).doubleValue())
                    .min()
                    .orElse(Double.NaN);
        }

        @Override
        public String getFunctionName() {
            return "min";
        }

        @Override
        public ColumnType columnType() {
            return ColumnTypes.DOUBLE_COLUMN_TYPE;
        }

        @Override
        public Column<?> createEmptyAggregationColumn(String name) {
            return new DoubleColumn(name);
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

        @Override
        public ColumnType columnType() {
            return ColumnTypes.DOUBLE_COLUMN_TYPE;
        }

        @Override
        public Column<?> createEmptyAggregationColumn(String name) {
            return new DoubleColumn(name);
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

        @Override
        public ColumnType columnType() {
            return ColumnTypes.DOUBLE_COLUMN_TYPE;
        }

        @Override
        public Column<?> createEmptyAggregationColumn(String name) {
            return new DoubleColumn(name);
        }
    },

    SUM {
        @Override
        public Object aggregate(List<Object> values) {
            printNACount(getFunctionName(), values);
            return values.stream()
                    .filter(i -> !Nulls.isNull(i))
                    .mapToDouble(value -> {
                        if (value instanceof Number number) {
                            return number.doubleValue();
                        } else if (value instanceof Boolean bool) {
                            return bool ? 1.0 : 0;
                        }else{
                            throw new IllegalArgumentException("SUM aggregation only supports numeric or boolean values. Got: " + value.getClass());
                        }
                    })
                    .sum();
        }

        @Override
        public String getFunctionName() {
            return "sum";
        }

        @Override
        public ColumnType columnType() {
            return ColumnTypes.DOUBLE_COLUMN_TYPE;
        }

        @Override
        public Column<?> createEmptyAggregationColumn(String name) {
            return new DoubleColumn(name);
        }
    },

    TRUE_COUNT {
        @Override
        public Object aggregate(List<Object> values) {
            printNACount(getFunctionName(), values);
            return (int) values.stream()
                    .filter(i -> !Nulls.isNull(i))
                    .filter(value -> value instanceof Boolean)
                    .map((value) -> (Boolean) value)
                    .filter(Boolean::booleanValue)
                    .count();
        }

        @Override
        public String getFunctionName() {
            return "true_count";
        }

        @Override
        public ColumnType columnType() {
            return ColumnTypes.INTEGER_COLUMN_TYPE;
        }

        @Override
        public Column<?> createEmptyAggregationColumn(String name) {
            return new IntegerColumn(name);
        }
    },

    FALSE_COUNT {
        @Override
        public Object aggregate(List<Object> values) {
            printNACount(getFunctionName(), values);
            return (int) values.stream()
                    .filter(i -> !Nulls.isNull(i))
                    .filter(value -> value instanceof Boolean)
                    .map((value) -> (Boolean) value)
                    .filter(value -> !value)
                    .count();
        }

        @Override
        public String getFunctionName() {
            return "false_count";
        }

        @Override
        public ColumnType columnType() {
            return ColumnTypes.INTEGER_COLUMN_TYPE;
        }

        @Override
        public Column<?> createEmptyAggregationColumn(String name) {
            return new IntegerColumn(name);
        }
    };

    //Stands for "Delta Degrees of Freedom"
    protected final int ddof;

    AggregationType(int ddof) {
        this.ddof = ddof;
    }

    AggregationType() {
        this.ddof = -1;
    }

    public abstract Object aggregate(List<Object> values);

    public abstract String getFunctionName();

    public abstract ColumnType columnType();

    public abstract Column<?> createEmptyAggregationColumn(String name);

    private static void printNACount(String aggFunction, List<Object> values) {
        int naCount = (int) values.stream().filter(Objects::isNull).count();
        if (naCount > 0) {
            System.out.printf(aggFunction + "() ignored %d NA values%n", naCount);
        }
    }

    private static Object computeStandardDeviation(List<Object> values, int ddof, String functionName) {
        printNACount(functionName, values);
        List<Double> validValues = values.stream()
                .filter(i -> !Nulls.isNull(i))
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
