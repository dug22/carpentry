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

package io.github.dug22.carpentry.carpentry_test.io;

import io.github.dug22.carpentry.DefaultDataFrame;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataFrameExporterTest {

    private final DefaultDataFrame df = DefaultDataFrame.create()
            .addIntegerColumn("ID", new Integer[]{1, 2, 3})
            .addStringColumn("Username", new String[]{"testuser1", "testuser2", "testuser3"})
            .addDoubleColumn("Score", new Double[]{85.90, 92.35, 90.46});

    @Test
    public void saveDataFrameAsCSVTestOne() {
        df.saveAsCsv("src/test/resources/user_scores_v1.csv");
        assertTrue(Files.exists(Path.of("src/test/resources/user_scores_v1.csv")));
    }

    @Test
    public void saveDataFrameAsCSVTestTwo() {
        df.saveAsCsv("src/test/resources/user_scores_v2.csv", ";");
        assertTrue(Files.exists(Path.of("src/test/resources/user_scores_v2.csv")));
    }

    @Test
    public void saveDataFrameAsJsonTest() {
        df.saveAsJson("src/test/resources/user_scores.json");
        assertTrue(Files.exists(Path.of("src/test/resources/user_scores.json")));
    }
}
