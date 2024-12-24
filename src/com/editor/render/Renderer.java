package com.editor.render;

import com.editor.core.Model;
import com.editor.core.Projection;

public interface Renderer {
    Projection project(Model lastModel);
}
