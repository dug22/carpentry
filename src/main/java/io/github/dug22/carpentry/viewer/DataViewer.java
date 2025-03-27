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

package io.github.dug22.carpentry.viewer;

import io.github.dug22.carpentry.DefaultDataFrame;
import io.github.dug22.carpentry.row.DataRow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class DataViewer {

    private final JFrame frame;
    private final DefaultDataFrame dataFrame;

    DataViewer(String title, int width, int height, DefaultDataFrame dataFrame) {
        this.frame = new JFrame(title);
        JTable table = new JTable();
        this.dataFrame = dataFrame;
        this.frame.setDefaultCloseOperation(3);
        this.frame.setLayout(new BorderLayout());
        this.frame.setSize(width, height);
        JScrollPane scrollPane = new JScrollPane(table);
        this.frame.add(scrollPane, "Center");
        java.util.List<String> columnNames = dataFrame.getColumnNames();
        List<DataRow> rows = dataFrame.getRows();
        Object[][] data = new Object[rows.size()][columnNames.size()];

        for (int i = 0; i < rows.size(); ++i) {
            DataRow row = rows.get(i);

            for (int j = 0; j < columnNames.size(); ++j) {
                data[i][j] = row.getRowData().get(columnNames.get(j));
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames.toArray());
        table.setModel(tableModel);
        JButton exportButton = new JButton("Export to CSV");
        exportButton.addActionListener((e) -> this.exportToCSV());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(exportButton);
        this.frame.add(buttonPanel, "South");
    }

    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a location to save the CSV file");
        int userSelection = fileChooser.showSaveDialog(this.frame);
        if (userSelection == 0) {
            File fileToSave = fileChooser.getSelectedFile();
            if (fileToSave != null) {
                try {
                    this.dataFrame.saveAsCsv(fileToSave.getAbsolutePath());
                    JOptionPane.showMessageDialog(this.frame, "Data successfully exported to " + fileToSave.getAbsolutePath());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this.frame, "Error while exporting data: " + ex.getMessage());
                }
            }
        }

    }

    public void display() {
        this.frame.setVisible(true);
    }

    public boolean isVisible() {
        return this.frame.isVisible();
    }

    public void dispose() {
        if (frame != null) {
            frame.dispose();
        }
    }
}
