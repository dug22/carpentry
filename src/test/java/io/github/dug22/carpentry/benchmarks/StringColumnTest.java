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

import io.github.dug22.carpentry.column.impl.StringColumn;

import java.util.concurrent.ThreadLocalRandom;

public class StringColumnTest {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static String generateRandomText(int length) {
        StringBuilder sb = new StringBuilder(length);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SimpleBenchmark bench = SimpleBenchmark.create()
                .warmups(10)
                .measurements(10);
        int textAmount = 1_000_000;
        int textLength = 10;
        SimpleBenchmarkRunnable task = () -> {
            String[] values = new String[textAmount];
            StringColumn stringColumn = StringColumn.create("Strings", values);
            for (int i = 0; i < textAmount; i++) {
                stringColumn.set(i, generateRandomText(textLength));
            }

            stringColumn = stringColumn.filter(i -> i.startsWith("S"));
            stringColumn.sortDescending();
        };
        bench.measure("String Column Test", task);
    }
}
