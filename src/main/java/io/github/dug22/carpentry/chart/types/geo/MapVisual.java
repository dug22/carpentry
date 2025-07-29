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

package io.github.dug22.carpentry.chart.types.geo;

import io.github.dug22.carpentry.chart.AbstractChart;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

public class MapVisual extends AbstractChart<MapVisualProperties> {

    private final String title = properties.getTitle();
    private final WorldMapTemplate worldMapTemplate = properties.getWorldMapTemplate();
    private final List<GeoPoint> geoPoints = properties.getGeoPoints();

    public MapVisual(MapVisualProperties properties) {
        super(properties);
    }

    /**
     * Loads the given map template and converts the image via base64.
     * @return the image in base64
     */
    private String getBase64MapImage() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(worldMapTemplate.getMapImageFilePath())) {
            if (inputStream == null) {
                throw new IOException("standard-world-map.png not found in src/resources");
            }
            byte[] imageBytes = inputStream.readAllBytes();
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load world map template: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getFigure() {
        StringBuilder figureBuilder = new StringBuilder();
        String mapImageData = getBase64MapImage();
        double figureWidth = worldMapTemplate.getMapImageWidth();
        double figureHeight = worldMapTemplate.getMapImageHeight();

        figureBuilder
                .append("<div class=\"figure-content\">\n")
                .append("<h1>" + title + "</h1>")
                .append("<svg width=\"").append(figureWidth).append("px\" height=\"").append(figureHeight).append("px\" viewBox=\"0 0 ").append(figureWidth).append(" ").append(figureHeight).append("\" class=\"map\" aria-labelledby=\"title\" role=\"img\">\n")
                .append("<title id=\"title\">Interactive Map</title>\n")
                .append("<image href=\"").append(mapImageData).append("\" x=\"0\" y=\"0\" width=\"").append(figureWidth).append("\" height=\"").append(figureHeight).append("\" preserveAspectRatio=\"xMidYMid meet\"/>\n");

        for (int i = 0; i < geoPoints.size(); i++) {
            GeoPoint point = geoPoints.get(i);
            String[] coords = point.latAndLong().split(",");
            if (coords.length != 2) {
                throw new IllegalArgumentException("Invalid geopoint format at index " + i + ": " + point.latAndLong());
            }
            try {
                double latitude = Double.parseDouble(coords[0].trim());
                double longitude = Double.parseDouble(coords[1].trim());
                if (latitude < -90 || latitude > 90) {
                    throw new IllegalArgumentException("Invalid latitude value at index " + i + ": " + latitude);
                }
                if (longitude < -180 || longitude > 180) {
                    throw new IllegalArgumentException("Invalid longitude value at index " + i + ": " + longitude);
                }
                double x = ((longitude + 180) / 360) * figureWidth;
                double y = ((90 - latitude) / 180) * figureHeight;

                figureBuilder.append(String.format(
                        "<g class=\"map-point-%d\" transform=\"translate(%.2f, %.2f)\">\n" +
                        "  <title>%s: (%s)</title>\n" +
                        "  <g transform=\"scale(0.15)\">\n" +
                        "    <path d=\"M 0 -20 C 10 -20 20 -10 20 0 C 20 15 10 20 0 30 C -10 20 -20 15 -20 0 C -20 -10 -10 -20 0 -20 Z\" fill=\"#FF0000\" />\n" +
                        "    <path d=\"M 5 -10 C 15 -10 25 0 25 10 L 5 20 Z\" fill=\"#00000033\" opacity=\"0.5\" />\n" +
                        "    <circle cx=\"0\" cy=\"-5\" r=\"5\" fill=\"#FFFFFF\" />\n" +
                        "</g>\n" +
                        "</g>\n",
                        i, x, y, point.prefix(), point.latAndLong()
                ));


            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number format in geopoint at index " + i + ": " + point.latAndLong());
            }
        }
        figureBuilder.append("</svg>\n")
                .append("</div>\n")
                .append("<script>\n")
                .append("    const svg = document.querySelector('.map');\n")
                .append("    let viewBox = { x: 0, y: 0, width: ").append(figureWidth).append(", height: ").append(figureHeight).append(" };\n")
                .append("    let isDragging = false;\n")
                .append("    let startX, startY;\n")
                .append("    let zoomLevel = 1;\n")
                .append("    const maxZoom = 5;\n")
                .append("    const minZoom = 0.5;\n")
                .append("    const zoomSpeed = 0.1;\n")
                .append("\n")
                .append("    function updateViewBox() {\n")
                .append("        svg.setAttribute('viewBox', `${viewBox.x} ${viewBox.y} ${viewBox.width} ${viewBox.height}`);\n")
                .append("    }\n")
                .append("\n")
                .append("    svg.addEventListener('wheel', (e) => {\n")
                .append("        e.preventDefault();\n")
                .append("        const rect = svg.getBoundingClientRect();\n")
                .append("        const mouseX = e.clientX - rect.left;\n")
                .append("        const mouseY = e.clientY - rect.top;\n")
                .append("        const delta = e.deltaY < 0 ? 1 + zoomSpeed : 1 - zoomSpeed;\n")
                .append("        const newZoom = zoomLevel * delta;\n")
                .append("        if (newZoom >= minZoom && newZoom <= maxZoom) {\n")
                .append("            zoomLevel = newZoom;\n")
                .append("            const newWidth = ").append(figureWidth).append(" / zoomLevel;\n")
                .append("            const newHeight = ").append(figureHeight).append(" / zoomLevel;\n")
                .append("            const mouseViewX = viewBox.x + (mouseX / rect.width) * viewBox.width;\n")
                .append("            const mouseViewY = viewBox.y + (mouseY / rect.height) * viewBox.height;\n")
                .append("            viewBox.x = mouseViewX - (mouseX / rect.width) * newWidth;\n")
                .append("            viewBox.y = mouseViewY - (mouseY / rect.height) * newHeight;\n")
                .append("            viewBox.width = newWidth;\n")
                .append("            viewBox.height = newHeight;\n")
                .append("            updateViewBox();\n")
                .append("        }\n")
                .append("    });\n")
                .append("\n")
                .append("    svg.addEventListener('mousedown', (e) => {\n")
                .append("        isDragging = true;\n")
                .append("        startX = e.clientX;\n")
                .append("        startY = e.clientY;\n")
                .append("    });\n")
                .append("\n")
                .append("    svg.addEventListener('mousemove', (e) => {\n")
                .append("        if (isDragging) {\n")
                .append("            const dx = (e.clientX - startX) * (viewBox.width / svg.getBoundingClientRect().width);\n")
                .append("            const dy = (e.clientY - startY) * (viewBox.height / svg.getBoundingClientRect().height);\n")
                .append("            viewBox.x -= dx;\n")
                .append("            viewBox.y -= dy;\n")
                .append("            startX = e.clientX;\n")
                .append("            startY = e.clientY;\n")
                .append("            updateViewBox();\n")
                .append("        }\n")
                .append("    });\n")
                .append("\n")
                .append("    svg.addEventListener('mouseup', () => {\n")
                .append("        isDragging = false;\n")
                .append("    });\n")
                .append("\n")
                .append("    svg.addEventListener('mouseleave', () => {\n")
                .append("        isDragging = false;\n")
                .append("    });\n")
                .append("</script>\n")
                .append("</body>\n")
                .append("</html>\n");

        return figureBuilder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getKeyLegend() {
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getStyles() {
        return "html, body {\n" +
               "  height: 100%;\n" +
               "  margin: 0;\n" +
               "  font: 16px/1.4em Arial;\n" +
               "  color: #000000;\n" +
               "  display: flex;\n" +
               "  align-items: center;\n" +
               "  justify-content: center;\n" +
               "}\n" +
               "* {\n" +
               "  box-sizing: border-box;\n" +
               "}\n" +
               ".figure-content {\n" +
               "  padding: 0 15px;\n" +
               "  align-self: center;\n" +
               "}\n" +
               ".figure-content svg {\n" +
               "  height: auto;\n" +
               "  max-width: none;\n" +
               "}\n" +
               ".figure-content h1 {\n" +
               "  text-align: center;\n" +
               "}\n" +
               ".map-point {\n" +
               "  cursor: pointer;\n" +
               "}\n";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTemplateFile() {
        return "/map_template.html";
    }
}