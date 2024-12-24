// com.editor.ui/EditorUI.java
package com.editor.ui;

import com.editor.core.*;
import com.editor.data.AnimationStorage;
import com.editor.data.ModelLoader;
import com.editor.data.RenderStorage;
import com.editor.data.SimpleAnimationStorage;
import com.editor.data.SimpleModelLoader;
import com.editor.data.SimpleRenderStorage;
import com.editor.render.Renderer;
import com.editor.render.SimpleRenderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EditorUI {
    private final ModelLoader modelLoader;
    private final Renderer renderer;
    private final RenderStorage renderStorage;
    private final AnimationStorage animationStorage;
    private final Animator animator;


    public EditorUI() {
        this.modelLoader = new SimpleModelLoader();
        this.renderer = new SimpleRenderer();
        this.renderStorage = new SimpleRenderStorage();
        this.animationStorage = new SimpleAnimationStorage();
        this.animator = new SimpleAnimator();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n3D Editor Menu:");
            System.out.println("1. Load 3D model");
            System.out.println("2. Convert to 2D projection");
            System.out.println("3. View 2D projection");
            System.out.println("4. Create 3D model (simple)");
            System.out.println("5. Save 3D model");
            System.out.println("6. Save 2D projection");
            System.out.println("7. Load 2D projection");
            System.out.println("8. Start Animation");
            System.out.println("9. Save Animation");
            System.out.println("10. Load Animation");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        loadModel(scanner);
                        break;
                    case "2":
                        convertModel(scanner);
                        break;
                    case "3":
                        viewProjection(scanner);
                        break;
                    case "4":
                        createModel(scanner);
                        break;
                    case "5":
                        saveModel(scanner);
                        break;
                    case "6":
                        saveProjection(scanner);
                        break;
                    case "7":
                        loadProjection(scanner);
                        break;
                    case "8":
                        startAnimation(scanner);
                        break;
                    case "9":
                        saveAnimation(scanner);
                        break;
                    case "10":
                        loadAnimation(scanner);
                        break;
                    case "0":
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void startAnimation(Scanner scanner) throws IOException {
        System.out.print("Enter the model path for adding to animation: ");
        String filePath = scanner.nextLine();
        Model model = modelLoader.loadModel(filePath);
        animator.addFrame(model);
        animator.play();
    }

    private void loadAnimation(Scanner scanner) throws IOException {
        System.out.print("Enter the animation path: ");
        String filePath = scanner.nextLine();
        List<Model> animation = animationStorage.loadAnimation(filePath);

        for(Model frame: animation) {
            System.out.println("Frame:");
            viewFrame(frame);
        }
    }

    private void viewFrame(Model model) {
        System.out.println("Vertices:");
        for (var vertex: model.getVertices()){
            System.out.println(String.format("(%f, %f, %f)", vertex.x, vertex.y, vertex.z));
        }
        System.out.println("Triangles:");
        for(var triangle: model.getTriangles()){
            System.out.println(String.format("(%d, %d, %d)", triangle.v1, triangle.v2, triangle.v3));
        }
    }

    private void saveAnimation(Scanner scanner) throws IOException {
        System.out.print("Enter animation save path: ");
        String filePath = scanner.nextLine();
        animationStorage.saveAnimation(filePath, ((SimpleAnimator)animator).frames);
    }

    private void loadProjection(Scanner scanner) throws IOException {
        System.out.print("Enter the path to projection: ");
        String filePath = scanner.nextLine();
        Projection projection = renderStorage.loadProjection(filePath);
        viewProjection(projection);
    }

    private void saveProjection(Scanner scanner) throws IOException {
        System.out.print("Enter the path to save: ");
        String filePath = scanner.nextLine();
        Projection projection = lastProjection;
        if(projection != null) {
            renderStorage.saveProjection(filePath, projection);
            System.out.println("Projection saved.");
        } else {
            System.out.println("No projection to save.");
        }
    }

    private void saveModel(Scanner scanner) throws IOException {
        System.out.print("Enter the path to save: ");
        String filePath = scanner.nextLine();
        if(lastModel != null) {
            modelLoader.saveModel(filePath, lastModel);
            System.out.println("Model saved.");
        } else {
            System.out.println("No model to save.");
        }
    }

    private void createModel(Scanner scanner) {
        System.out.println("Creating a simple model...");
        List<com.editor.core.Point3D> vertices = new ArrayList<>();
        vertices.add(new com.editor.core.Point3D(0, 0, 0));
        vertices.add(new com.editor.core.Point3D(1, 0, 0));
        vertices.add(new com.editor.core.Point3D(0, 1, 0));
        List<com.editor.core.Triangle> triangles = new ArrayList<>();
        triangles.add(new com.editor.core.Triangle(0, 1, 2));
        lastModel = new SimpleModel(vertices, triangles);
        System.out.println("Model created.");
    }
    private Model lastModel;

    private void loadModel(Scanner scanner) throws IOException {
        System.out.print("Enter the path to the model: ");
        String filePath = scanner.nextLine();
        lastModel = modelLoader.loadModel(filePath);
        System.out.println("Model loaded.");
    }
    private Projection lastProjection;
    private void convertModel(Scanner scanner) {
        if(lastModel != null) {
            lastProjection = renderer.project(lastModel);
            System.out.println("Model converted to 2D projection.");
        } else {
            System.out.println("No model to convert.");
        }

    }

    private void viewProjection(Scanner scanner) {
        if (lastProjection != null) {
            viewProjection(lastProjection);
        } else {
            System.out.println("No projection to view.");
        }
    }
    private void viewProjection(Projection projection) {
        System.out.println("2D Projection:");
        for (Point2D point : projection.getPoints()) {
            System.out.println("(" + point.x + ", " + point.y + ")");
        }
    }

}