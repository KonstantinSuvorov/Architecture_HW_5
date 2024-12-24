// com.editor.core/Model.java
package com.editor.core;

import java.util.List;

public interface Model {
    List<Point3D> getVertices();
    List<Triangle> getTriangles();
}
