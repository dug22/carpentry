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


package io.github.dug22.carpentry.benchmarks;

import io.github.dug22.carpentry.column.impl.IntegerColumn;

import java.util.stream.IntStream;

public class IntColumnBenchmark {

    public static void main(String[] args) {
        SimpleBenchmark bench = SimpleBenchmark.create()
                .warmups(10)
                .measurements(10);

        SimpleBenchmarkRunnable task = () -> {
            IntegerColumn integerColumn = IntegerColumn.create("Integers", IntStream.range(0, 1_000_000).boxed().toArray(Integer[]::new));
            integerColumn.apply(i -> i + 2);
            integerColumn = integerColumn.filter(i -> i >= 10 && i <= 200_000);
            integerColumn.sortDescending();
        };
        bench.measure("Integer Column Test", task);
    }
}
