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

package io.github.dug22.carpentry.column.impl;

import io.github.dug22.carpentry.column.BaseColumn;
import io.github.dug22.carpentry.column.ColumnParser;
import io.github.dug22.carpentry.column.ColumnType;
import io.github.dug22.carpentry.column.format.NumericColumnFormatter;

import java.text.NumberFormat;

public abstract class NumberColumn<T extends Number> extends BaseColumn<T> implements NumericColumn<T> {

    private NumericColumnFormatter outputFormatter = new NumericColumnFormatter();

    public NumberColumn(String name, ColumnType columnType, ColumnParser<T> columnParser) {
        super(name, columnType, columnParser);
    }

    public NumberColumn(String name, ColumnType dataType, T[] data, ColumnParser<T> columnParser) {
        super(name, dataType, data, columnParser);
    }

    public void setOutputFormatter(NumberFormat format) {
        setOutputFormatter(new NumericColumnFormatter(format));
    }

    public void setOutputFormatter(NumericColumnFormatter outputFormatter){
        this.outputFormatter = outputFormatter;
    }

    @Override
    public NumericColumnFormatter getOutputFormatter() {
        return outputFormatter;
    }

    public String getString(Number value){
        return outputFormatter.formatValue(value);
    }
}
