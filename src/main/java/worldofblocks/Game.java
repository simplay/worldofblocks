package worldofblocks;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

// TODO: try to implement a more MVC like style or at least separate view-controller code from models
public class Game implements Runnable {
  // address to window object
  private long window;

  private int windowWidth;
  private int windowHeight;

  private Thread thread;
  private boolean running = false;

  private InputHandler windowInputHandler;

  public void start() {
    this.running = true;
    thread = new Thread(this, "Game");
    thread.start();
  }

  Game() {
    this(300, 300);
  }

  Game(int windowWidth, int windowHeight) {
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;
  }

  private void init() {
    initRenderer();
    initShapes();
  }

  private Shader shader;
  private void initShapes() {
    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities();

    glEnable(GL_TEXTURE_2D);

    float[] textureCoordinates = new float[] {
        0,0, 1,0,
        1,1, 0,1
    };

    int[] indices = new int[] {
        0,1,2,
        2,3,0
    };

    shape = new Shape(
        getVertices(0, 0),
        textureCoordinates,
        indices,
        new Texture("./textures/trollface.png")
    );

    shader = new Shader("shader");
  }

  private void initRenderer() {
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
    window = glfwCreateWindow(windowWidth, windowHeight, "World of Blocks", NULL, NULL);
    if (window == NULL) {
      throw new RuntimeException("Failed to create the GLFW window");
    }

    windowInputHandler = new InputHandler(window);

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
      if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {
        glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
      }
    });

    // Get the thread stack and push a new frame
    try ( MemoryStack stack = stackPush() ) {
      IntBuffer pWidth = stack.mallocInt(1); // int*
      IntBuffer pHeight = stack.mallocInt(1); // int*

      // Get the window size passed to glfwCreateWindow
      glfwGetWindowSize(window, pWidth, pHeight);

      // Get the resolution of the primary monitor
      GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

      // Center the window
      glfwSetWindowPos(
          window,
          (videoMode.width() - pWidth.get(0)) / 2,
          (videoMode.height() - pHeight.get(0)) / 2
      );
    } // the stack frame is popped automatically

    // Make the OpenGL context current
    glfwMakeContextCurrent(window);

    // Enable v-sync
    glfwSwapInterval(1);

    // Make the window visible
    glfwShowWindow(window);
  }

  public void run() {
    init();
    while(running) {
      update();
      render();

      // Run the rendering loop until the user has attempted to close
      // the window or has pressed the ESCAPE key.
      if (glfwWindowShouldClose(window)) {
        this.running = false;
      }
    }
    cleanup();
  }

  private void update() {
    // Poll for window events. The key callback above will only be
    // invoked during this call.
    glfwPollEvents();
    handleUserInput();
  }

  private float dx = 0;
  private float dy = 0;
  private void handleUserInput() {
    if (windowInputHandler.isKeyDown(GLFW_KEY_A)) {
      dx -= 0.01f;
    }

    if (windowInputHandler.isKeyDown(GLFW_KEY_D)) {
      dx += 0.01f;
    }

    if (windowInputHandler.isKeyDown(GLFW_KEY_W)) {
      dy += 0.01f;
    }

    if (windowInputHandler.isKeyDown(GLFW_KEY_S)) {
      dy -= 0.01f;
    }

    shape.updateVertices(getVertices(dx, dy));
  }

  float[] getVertices(float dx, float dy) {
    float[] vertices = new float[] {
        -0.5f + dx, 0.5f + dy, 0, // 0
        0.5f + dx, 0.5f + dy, 0, // 1
        0.5f + dx, -0.5f + dy, 0, // 2
        -0.5f + dx, -0.5f + dy, 0, // 3
    };

    return vertices;
  }

  private Shape shape;
  private void render() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    shader.bind();
    shader.setUniform("sampler", 0);
    shape.render();
    glfwSwapBuffers(window); // swap the color buffers
  }

  private void cleanup() {
    // Free the window callbacks and destroy the window
    glfwFreeCallbacks(window);
    glfwDestroyWindow(window);

    // Terminate GLFW and free the error callback
    glfwTerminate();
    glfwSetErrorCallback(null).free();
  }
}
