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

import java.text.DecimalFormat;
import java.util.logging.Logger;

public class SimpleBenchmark {

    private static final Logger logger = Logger.getLogger(SimpleBenchmark.class.getSimpleName());

    private int warmupIterations = 10;
    private int measureIterations = 10;

    private long totalTimeNano, totalMemoryBytes;

    public static SimpleBenchmark create() {
        return new SimpleBenchmark();
    }

    public SimpleBenchmark warmups(int iterations) {
        this.warmupIterations = iterations;
        return this;
    }

    public SimpleBenchmark measurements(int iterations) {
        this.measureIterations = iterations;
        return this;
    }


    public void measure(String label, SimpleBenchmarkRunnable task) {
        restoreJvm();
        logger.info("Warming up...");
        for (int i = 0; i < warmupIterations; i++) {
            task.run();
        }

        restoreJvm();
        logger.info("Measuring: " + label);

        totalTimeNano = 0;
        totalMemoryBytes = 0;

        for (int i = 0; i < measureIterations; i++) {
            restoreJvm();
            long beforeMem = memoryUsed();
            long start = System.nanoTime();

            task.run();

            long end = System.nanoTime();
            long afterMem = memoryUsed();

            totalTimeNano += (end - start);
            totalMemoryBytes += (afterMem - beforeMem);
        }

        printResults(label);
    }

    private void printResults(String label) {
        DecimalFormat df = new DecimalFormat("#,##0.000");
        double avgTimeMs = totalTimeNano / 1_000_000.0 / measureIterations;
        double totalTimeSec = totalTimeNano / 1_000_000_000.0;
        double tps = measureIterations / totalTimeSec;
        double avgMemMb = totalMemoryBytes / (1024.0 * 1024.0) / measureIterations;

        logger.info(String.format("""
                
                %s
                -------------------------------
                Avg Time : %s ms
                Total    : %s s
                TPS      : %s
                Mem Use  : %s MB
                -------------------------------
                """, label, df.format(avgTimeMs), df.format(totalTimeSec), df.format(tps), df.format(avgMemMb)));
    }

    private static void restoreJvm() {
        long prev = memoryUsed();
        for (int i = 0; i < 10; i++) {
            System.gc();
            long current = memoryUsed();
            if (current >= prev) {
                break;
            }
            prev = current;
        }
    }

    private static long memoryUsed() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
