// com.editor.data/RenderStorage.java
package com.editor.data;

import com.editor.core.Projection;
import java.io.IOException;

public interface RenderStorage {
    void saveProjection(String filePath, Projection projection) throws IOException;
    Projection loadProjection(String filePath) throws IOException;
}
