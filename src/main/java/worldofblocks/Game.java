package worldofblocks;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import worldofblocks.drawables.Block;
import worldofblocks.drawables.Plane;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Game implements Runnable, Subscriber {
  // address to window object
  private long window;
  private final float EPS = 0.1f;

  private int windowWidth;
  private int windowHeight;

  private Thread thread;
  private boolean running = false;

  private InputHandler windowInputHandler;
  private CursorHandler cursorHandler;

  private Block block;
  private Plane plane;

  private FpsCounter fpsCoutner;

  private WorldTimer worldTimer;

  private Camera camera;
  private Frustum frustum;
  private Vector3f eye = new Vector3f(0, 0, 4f);
  private Vector3f lookAtPoint = new Vector3f(0.0f, 0.2f, 0.0f);
  private Vector3f up = new Vector3f(0, 0, 1);

  private Shader shader;
  private Player player;

  public void start() {
    this.running = true;
    thread = new Thread(this, "Game");
    thread.start();
  }

  public Game(int windowWidth, int windowHeight) {
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;

    this.worldTimer = new WorldTimer();
  }

  private void init() {
    initRenderer();

    this.fpsCoutner = new FpsCounter();
    this.camera = new Camera(cursorHandler, eye, lookAtPoint, up);
    this.frustum = new Frustum(windowWidth / windowHeight, EPS, 5000, 60.0f);

    initShapes();

    worldTimer.addSubscriber(this);
    worldTimer.start();
  }


  private void initShapes() {
    // This line is critical for LWJGL's interoperation with GLFW's
    // OpenGL context, or any context that is managed externally.
    // LWJGL detects the context that is current in the current thread,
    // creates the GLCapabilities instance and makes the OpenGL
    // bindings available for use.
    GL.createCapabilities();

    glEnable(GL_TEXTURE_2D);
    glEnable(GL_DEPTH_TEST);
    glDepthFunc(GL_LESS);

    this.player = new Player(windowInputHandler);
    camera.attachPlayer(player);

    this.plane = new Plane();
    this.block = new Block();
    this.shader = new Shader("shader");
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
    this.window = glfwCreateWindow(windowWidth, windowHeight, "World of Blocks", NULL, NULL);
    if (window == NULL) {
      throw new RuntimeException("Failed to create the GLFW window");
    }

    this.windowInputHandler = new InputHandler(window);
    this.cursorHandler = new CursorHandler(windowWidth, windowHeight);

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
      if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
        glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
      }
    });

    glfwSetCursorPosCallback(window, cursorHandler);

    // Get the thread stack and push a new frame
    try (MemoryStack stack = stackPush()) {
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
    while (running) {
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

    player.update();
    camera.update();
  }

  private void render() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

    shader.bind();
    shader.setUniform("sampler", 0);
    shader.setUniform("modelview", camera.getTransformation());
    shader.setUniform("projection", frustum.getTransformation());

    player.render();
    plane.render();
    block.render();

    glfwSwapBuffers(window); // swap the color buffers

    fpsCoutner.update();
  }

  private void cleanup() {
    // Free the window callbacks and destroy the window
    glfwFreeCallbacks(window);
    glfwDestroyWindow(window);

    // Terminate GLFW and free the error callback
    glfwTerminate();
    glfwSetErrorCallback(null).free();

    worldTimer.stop();
  }

  @Override
  public void handleUpdate() {
    glfwSetWindowTitle(window, "Wolrd of Blocks - FPS: " + fpsCoutner.getFps());
  }
}
