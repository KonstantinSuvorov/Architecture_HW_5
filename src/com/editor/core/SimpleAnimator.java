// com.editor.core/SimpleAnimator.java
package com.editor.core;

import java.util.ArrayList;
import java.util.List;

public class SimpleAnimator implements Animator {
    public List<Model> frames = new ArrayList<>();
    boolean isPlaying = false;
    @Override
    public void play() {
        if(!isPlaying) {
            isPlaying = true;
            new Thread(() -> {
                for(Model model: frames){
                    if(!isPlaying) return;
                    try {
                        Thread.sleep(1000); //1 sec
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Frame:");
                    System.out.println("Vertices:");
                    for (var vertex: model.getVertices()){
                        System.out.println(String.format("(%f, %f, %f)", vertex.x, vertex.y, vertex.z));
                    }
                    System.out.println("Triangles:");
                    for(var triangle: model.getTriangles()){
                        System.out.println(String.format("(%d, %d, %d)", triangle.v1, triangle.v2, triangle.v3));
                    }
                }
                isPlaying = false;
            }).start();
        }

    }

    @Override
    public void stop() {
        isPlaying = false;
    }

    @Override
    public void addFrame(Model model) {
        frames.add(model);
    }
}
