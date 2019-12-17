package worldofblocks.gui;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryStack;
import worldofblocks.gui.handlers.CursorHandler;
import worldofblocks.gui.handlers.InputHandler;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.GL_TRUE;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
  private InputHandler windowInputHandler;
  private CursorHandler cursorHandler;

  private long id;

  private int width;
  private int height;

  private boolean fullscreen = false;
  private final String title = "World of Blocks";
  GLFWErrorCallback errorCallback;

  public Window(int width, int height, boolean fullscreen) {
    this.width = width;
    this.height = height;
    this.fullscreen = fullscreen;
    createWindow();
  }

  private void createWindow() {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
//    GLFWErrorCallback.createPrint(System.err).set();
    glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }

    // Configure GLFW
    glfwDefaultWindowHints(); // optional, the current window hints are already the default
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

    // Create the window
    this.id = glfwCreateWindow(
            width,
            height,
            title,
            fullscreen ? glfwGetPrimaryMonitor() : 0L,
            NULL
    );
    if (id == NULL) {
      throw new RuntimeException("Failed to create the GLFW window");
    }

    this.windowInputHandler = new InputHandler(id);
    this.cursorHandler = new CursorHandler(currentWidth(), currentHeight());

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
      if (!fullscreen) {
        glfwSetWindowPos(
                id,
                (videoMode.width() - pWidth.get(0)) / 2,
                (videoMode.height() - pHeight.get(0)) / 2
        );
      }
    } // the stack frame is popped automatically

    // Make the OpenGL context current
    glfwMakeContextCurrent(id);

    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    // Make the OpenGL context current
    GL.createCapabilities();

    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(id);
  }

  public void toggleFullscreen() {
    this.fullscreen = !fullscreen;
    this.destroy();
    createWindow();
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

  public Vector2i getResolution() {
    IntBuffer w = BufferUtils.createIntBuffer(1);
    IntBuffer h = BufferUtils.createIntBuffer(1);
    glfwGetWindowSize(id, w, h);
    int width = w.get();
    int height = h.get();
    return new Vector2i(width, height);
  }

  public int currentWidth() {
    return getResolution().x;
  }

  public int currentHeight() {
    return getResolution().y;
  }
}
