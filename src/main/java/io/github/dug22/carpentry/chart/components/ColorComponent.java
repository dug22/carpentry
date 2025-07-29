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

package io.github.dug22.carpentry.chart.components;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ColorComponent {
    RED("#FF0000"),
    GREEN("#00FF00"),
    BLUE("#0000FF"),
    YELLOW("#FFFF00"),
    PURPLE("#800080"),
    ORANGE("#FFA500"),
    BLACK("#000000"),
    WHITE("#FFFFFF"),
    CYAN("#00FFFF"),
    MAGENTA("#FF00FF"),
    PINK("#FFC0CB"),
    TEAL("#008080"),
    LAVENDER("#E6E6FA"),
    BROWN("#A52A2A"),
    BEIGE("#F5F5DC"),
    MAROON("#800000"),
    MINT("#98FF98"),
    OLIVE("#808000"),
    CORAL("#FF7F50"),
    NAVY("#000080"),
    GREY("#808080"),
    VIOLET("#EE82EE"),
    TURQUOISE("#40E0D0"),
    GOLD("#FFD700"),
    SILVER("#C0C0C0"),
    INDIGO("#4B0082"),
    CRIMSON("#DC143C"),
    AQUA("#00FFFF"),
    CHARTREUSE("#7FFF00"),
    ROSE("#FF007F"),
    AZURE("#F0FFFF"),
    EMERALD("#50C878"),
    AMBER("#FFBF00"),
    JADE("#00A86B"),
    RUBY("#E0115F"),
    SAPPHIRE("#0F52BA"),
    TOPAZ("#FFC107"),
    AMETHYST("#9966CC"),
    ONYX("#353839"),
    PEARL("#EAE0C8"),
    GARNET("#733635"),
    PERIDOT("#E6E200"),
    OPAL("#A8C3BC"),
    CITRINE("#E4D96F"),
    PLUM("#DDA0DD"),
    SLATE("#708090"),
    IVORY("#FFFFF0"),
    LILAC("#C8A2C8"),
    COBALT("#0047AB"),
    CUSTOM(null) {
        private String customHexValue;

        @Override
        public ColorComponent setCustomHex(String hexValue) {
            if (hexValue != null && hexValue.matches("^#[0-9A-Fa-f]{6}$")) {
                this.customHexValue = hexValue;
            } else {
                throw new IllegalArgumentException("Invalid hex color format. Use #RRGGBB (e.g., #FF0000)");
            }
            return this;
        }

        @Override
        public String getHexValue() {
            if (customHexValue == null) {
                throw new IllegalStateException("Custom hex value not set. Call setCustomHex() first.");
            }
            return customHexValue;
        }
    };

    private final String hexValue;

    ColorComponent(String hexValue) {
        this.hexValue = hexValue;
    }

    public String getHexValue() {
        return hexValue;
    }

    /**
     * Sets the hex value for Color.CUSTOM
     * @param hexValue the color hex value
     */
    public ColorComponent setCustomHex(String hexValue) {
        throw new UnsupportedOperationException("Only CUSTOM color can set a custom hex value.");
    }

    private static final Map<Integer, ColorComponent> customColorCache = new HashMap<>();


    /**
     * Retrieves an initialized ColorComponent object based on a provided index
     * @param index the index to determine which color to select/
     * @return an initialized ColorComponent object based on a provided index
     */
    public static ColorComponent getCustomColor(int index) {
        List<ColorComponent> predefined = Arrays.stream(ColorComponent.values())
                .filter(c -> c != ColorComponent.CUSTOM)
                .toList();

        if (index < predefined.size()) {
            return predefined.get(index);
        }

        return customColorCache.computeIfAbsent(index, idx -> {
            String hex = String.format("#%06X", (0xFFFFFF & (idx * 2654435761L)));
            return ColorComponent.CUSTOM.setCustomHex(hex);
        });
    }
}