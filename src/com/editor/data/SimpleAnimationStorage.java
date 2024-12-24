// com.editor.data/SimpleAnimationStorage.java
package com.editor.data;

import com.editor.core.Model;
import com.editor.core.Point3D;
import com.editor.core.SimpleModel;
import com.editor.core.Triangle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleAnimationStorage implements AnimationStorage {

    private ModelLoader modelLoader = new SimpleModelLoader();
    @Override
    public void saveAnimation(String filePath, List<Model> animation) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for(Model frame: animation) {
                if(!(frame instanceof SimpleModel)) {
                    throw new IOException("Only SimpleModel are supported");
                }
                bw.write("FRAME START\n");
                for(Point3D vertex: frame.getVertices()){
                    bw.write(String.format("v %f %f %f\n", vertex.x, vertex.y, vertex.z));
                }
                for(Triangle triangle: frame.getTriangles()){
                    bw.write(String.format("f %d %d %d\n", triangle.v1+1, triangle.v2+1, triangle.v3+1));
                }
                bw.write("FRAME END\n");
            }
        }
    }

    @Override
    public List<Model> loadAnimation(String filePath) throws IOException {
        List<Model> animation = new ArrayList<>();
        List<Point3D> vertices = new ArrayList<>();
        List<Triangle> triangles = new ArrayList<>();
        boolean isFrameStarted = false;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().equals("FRAME START")) {
                    isFrameStarted = true;
                    vertices = new ArrayList<>();
                    triangles = new ArrayList<>();
                    continue;
                } else if (line.trim().equals("FRAME END")) {
                    animation.add(new SimpleModel(vertices, triangles));
                    isFrameStarted = false;
                    continue;
                }
                if(isFrameStarted) {
                    String[] parts = line.split("\\s+");
                    if(parts.length > 0) {
                        if (parts[0].equals("v")) {
                            double x = Double.parseDouble(parts[1]);
                            double y = Double.parseDouble(parts[2]);
                            double z = Double.parseDouble(parts[3]);
                            vertices.add(new Point3D(x, y, z));
                        } else if (parts[0].equals("f")) {
                            int v1 = Integer.parseInt(parts[1]) - 1;
                            int v2 = Integer.parseInt(parts[2]) - 1;
                            int v3 = Integer.parseInt(parts[3]) - 1;
                            triangles.add(new Triangle(v1, v2, v3));
                        }
                    }
                }
            }
        }
        return animation;
    }
}
