// com.editor.data/ModelLoader.java
package com.editor.data;

import com.editor.core.Model;
import java.io.IOException;

public interface ModelLoader {
    Model loadModel(String filePath) throws IOException;
    void saveModel(String filePath, Model model) throws IOException;
}
