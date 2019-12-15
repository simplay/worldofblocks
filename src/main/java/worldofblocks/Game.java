package worldofblocks;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import worldofblocks.drawables.Block;
import worldofblocks.drawables.Plane;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Game implements Runnable, Subscriber {
  private final float EPS = 0.1f;

  private int windowWidth;
  private int windowHeight;
  private Window window;

  private Thread thread;
  private boolean running = false;

  private Block block;
  private Plane plane;

  private FpsCounter fpsCounter;

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
    this.thread = new Thread(this, "Game");

    thread.start();
  }

  public Game(int windowWidth, int windowHeight) {
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;

    this.worldTimer = new WorldTimer();
  }

  private void init() {
    this.window = new Window(windowWidth, windowHeight);
    this.fpsCounter = new FpsCounter();
    this.camera = new Camera(window.getCursorHandler(), eye, lookAtPoint, up);
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

    this.player = new Player(window.getInputHandler());
    camera.attachPlayer(player);

    this.plane = new Plane(10);
    this.block = new Block();
    this.shader = new Shader("shader");
  }

  public void run() {
    init();
    while (running) {
      update();
      render();

      // Run the rendering loop until the user has attempted to close
      // the window or has pressed the ESCAPE key.
      if (glfwWindowShouldClose(window.getId())) {
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

    glfwSwapBuffers(window.getId()); // swap the color buffers

    fpsCounter.update();
  }

  private void cleanup() {
    window.destroy();

    // Terminate GLFW and free the error callback
    glfwTerminate();
    glfwSetErrorCallback(null).free();

    worldTimer.stop();
  }

  @Override
  public void handleUpdate() {
    glfwSetWindowTitle(window.getId(), "Wolrd of Blocks - FPS: " + fpsCounter.getFps());
  }
}
