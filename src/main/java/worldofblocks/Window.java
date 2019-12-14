package worldofblocks;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
  private InputHandler windowInputHandler;
  private CursorHandler cursorHandler;

  private final long id;

  public Window(int width, int height) {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set();

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }

    // Configure GLFW
    glfwDefaultWindowHints(); // optional, the current window hints are already the default
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

    // Create the window
    this.id = glfwCreateWindow(width, height, "World of Blocks", NULL, NULL);
    if (id == NULL) {
      throw new RuntimeException("Failed to create the GLFW window");
    }

    this.windowInputHandler = new InputHandler(id);
    this.cursorHandler = new CursorHandler(width, height);

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    glfwSetKeyCallback(id, (window, key, scancode, action, mods) -> {
      if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
        glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
      }
    });

    glfwSetCursorPosCallback(id, cursorHandler);

    // Get the thread stack and push a new frame
    try (MemoryStack stack = stackPush()) {
      IntBuffer pWidth = stack.mallocInt(1); // int*
      IntBuffer pHeight = stack.mallocInt(1); // int*

      // Get the window size passed to glfwCreateWindow
      glfwGetWindowSize(id, pWidth, pHeight);

      // Get the resolution of the primary monitor
      GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      // Center the window
      glfwSetWindowPos(
              id,
              (videoMode.width() - pWidth.get(0)) / 2,
              (videoMode.height() - pHeight.get(0)) / 2
      );
    } // the stack frame is popped automatically

    // Make the OpenGL context current
    glfwMakeContextCurrent(id);

    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(id);
  }

  public CursorHandler getCursorHandler() {
    return cursorHandler;
  }

  public InputHandler getInputHandler() {
    return windowInputHandler;
  }

  public long getId() {
    return id;
  }

  public void destroy() {
    // Free the window callbacks and destroy the window
    glfwFreeCallbacks(id);
    glfwDestroyWindow(id);
  }
}
