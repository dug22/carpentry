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

package io.github.dug22.carpentry.row;

import io.github.dug22.carpentry.DataFrame;

import java.util.ArrayList;
import java.util.List;

public class DataRows extends ArrayList<DataRow> {

    private DataFrame dataFrame;

    public DataRows() {
        super();
        this.dataFrame = null;
    }

    public DataRows(DataFrame dataFrame) {
        super(dataFrame.getRowCount());
        this.dataFrame = dataFrame;
        for (int i = 0; i < dataFrame.getRowCount(); i++) {
            add(new DataRow(dataFrame, i));
        }
    }

    public DataRows(DataFrame dataFrame, List<DataRow> rows) {
        super(rows.size());
        this.dataFrame = dataFrame;
        addAll(rows);
    }

    public void setDataFrame(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    public DataFrame toDataFrame() {
        DataFrame df = dataFrame.copyEmpty();
        if (df.isEmpty()) {
            return df;
        }

        for (DataRow row : this) {
            df.addRow(row);
        }
        return df;
    }

    public DataFrame getDataFrame() {
        return dataFrame;
    }
}