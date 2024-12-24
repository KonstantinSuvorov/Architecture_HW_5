// com.editor.core/SimpleModel.java
package com.editor.core;

import java.util.List;

public class SimpleModel implements Model {
    private List<Point3D> vertices;
    private List<Triangle> triangles;

    public SimpleModel(List<Point3D> vertices, List<Triangle> triangles){
        this.vertices = vertices;
        this.triangles = triangles;
    }
    @Override
    public List<Point3D> getVertices() {
        return vertices;
    }

    @Override
    public List<Triangle> getTriangles() {
        return triangles;
    }
}
