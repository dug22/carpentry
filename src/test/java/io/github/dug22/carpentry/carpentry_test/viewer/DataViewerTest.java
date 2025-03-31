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

package io.github.dug22.carpentry.carpentry_test.viewer;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.io.csv.CSVReaderBuilder;
import io.github.dug22.carpentry.viewer.DataViewer;
import io.github.dug22.carpentry.viewer.DataViewerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataViewerTest {

    private DefaultDataFrame dataFrame;

    @BeforeEach
    public void setUP() {
        dataFrame = DefaultDataFrame.load(new CSVReaderBuilder()
                .setURL("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/states.csv")
                .build());
    }

    @Test
    public void viewOriginalTest() throws InterruptedException{
        DataViewer dataViewer = new DataViewerBuilder(dataFrame)
                .setTitle("State Data")
                .setSize(500, 500)
                .build();
        assertNotNull(dataViewer);
        dataViewer.display();
        assertTrue(dataViewer.isVisible());
        Thread.sleep(5000);
    }

    @Test
    public void viewManipulatedTest() throws InterruptedException{
        DefaultDataFrame result = dataFrame.filter(dataFrame.column("State").startsWith( "A"));
        DataViewer dataViewer = new DataViewerBuilder(result)
                .setTitle("State Data")
                .setSize(500, 500)
                .build();
        assertNotNull(dataViewer);
        dataViewer.display();
        assertTrue(dataViewer.isVisible());
        Thread.sleep(5000);
    }
}
