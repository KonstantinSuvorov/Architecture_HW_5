// com.editor.data/AnimationStorage.java
package com.editor.data;

import com.editor.core.Model;
import java.io.IOException;
import java.util.List;

public interface AnimationStorage {
    void saveAnimation(String filePath, List<Model> animation) throws IOException;
    List<Model> loadAnimation(String filePath) throws IOException;
}
