// com.editor.render/SimpleRenderer.java
package com.editor.render;

import com.editor.core.Model;
import com.editor.core.Point2D;
import com.editor.core.Point3D;
import com.editor.core.Projection;

import java.util.ArrayList;
import java.util.List;

public class SimpleRenderer implements Renderer, SimpleRender {

    @Override
    public Projection project(Model model) {
        List<Point2D> points2D = new ArrayList<>();
        for (Point3D point3D : model.getVertices()) {
            // Simple orthogonal projection
            double x = point3D.x;
            double y = point3D.y;
            points2D.add(new Point2D(x, y));
        }
        return () -> points2D;
    }
}
