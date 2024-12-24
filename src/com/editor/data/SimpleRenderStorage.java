// com.editor.data/SimpleRenderStorage.java
package com.editor.data;

import com.editor.core.Point2D;
import com.editor.core.Projection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleRenderStorage implements RenderStorage {
    @Override
    public void saveProjection(String filePath, Projection projection) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Point2D point : projection.getPoints()) {
                bw.write(String.format("%f %f\n", point.x, point.y));
            }
        }
    }

    @Override
    public Projection loadProjection(String filePath) throws IOException {
        List<Point2D> points = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if(parts.length == 2) {
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    points.add(new Point2D(x, y));
                }
            }
        }

        return () -> points;
    }
}
