package worldofblocks.handlers;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

public class InputHandler {
  private final long window;

  public InputHandler(long window) {
    this.window = window;
  }

  public boolean isKeyDown(int key) {
    return glfwGetKey(window, key) == GL_TRUE;
  }

  public boolean isMouseButtonDown(int key) {
    return glfwGetMouseButton(window, key) == GL_TRUE;
  }
}
