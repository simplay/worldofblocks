package worldofblocks.game;

import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector4f;
import worldofblocks.GraphicDetails;
import worldofblocks.entities.lights.DirectionalLight;
import worldofblocks.entities.lights.PointLight;
import worldofblocks.rendering.Shader;
import worldofblocks.rendering.Texture;
import worldofblocks.rendering.drawables.Block;
import worldofblocks.rendering.drawables.Plane;
import worldofblocks.entities.cameras.Camera;
import worldofblocks.entities.cameras.Frustum;
import worldofblocks.entities.gameobjects.Player;
import worldofblocks.gui.Window;
import worldofblocks.rendering.drawables.RenderItem;

import java.util.LinkedList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Game implements Subscriber {
  private final float EPS = 0.1f;

  private int windowWidth;
  private int windowHeight;
  private boolean fullscreen;

  private Window window;

  private boolean running;

  private Block block;
  private Plane plane;

  private FpsCounter fpsCounter;
  private WorldTimer worldTimer;

  private Camera camera;
  private Frustum frustum;
  private Vector3f eye = new Vector3f(0, 0, 4f);
  private Vector3f lookAtPoint = new Vector3f(0.0f, 0.2f, 0.0f);
  private Vector3f up = new Vector3f(0, 0, 1);

  private final LinkedList<RenderItem> renderItems = new LinkedList<>();
  private Shader shader;
  private Player player;

  public Game(int windowWidth, int windowHeight, boolean fullscreen) {
    this.running = true;
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;
    this.fullscreen = fullscreen;
    this.worldTimer = new WorldTimer();
  }

  private void init() {
    this.window = new Window(windowWidth, windowHeight, fullscreen);

    glEnable(GL_TEXTURE_2D);
    glEnable(GL_DEPTH_TEST);
    glDepthFunc(GL_LESS);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE);

    this.fpsCounter = new FpsCounter();
    this.camera = new Camera(window.getCursorHandler(), eye, lookAtPoint, up);

    initFrustum();
    initShapes();

    worldTimer.addSubscriber(this);
    worldTimer.start();
  }

  private void initFrustum() {
    Vector2i res = window.getResolution();
    float aspectRatio = res.x / res.y;
    this.frustum = new Frustum(aspectRatio, EPS, 5000, 60.0f);
  }

  // TODO: fixme - crashes the app
  private void toggleFullscreen() {
//    window.toggleFullscreen();
//    initFrustum();
  }

  private final LinkedList<PointLight> pointLights = new LinkedList<>();

  private void initShapes() {
    String shaderFilePath = GraphicDetails.usedShader() + "/shader";
    this.shader = new Shader(shaderFilePath);

    this.plane = new Plane(10);
    this.block = new Block();

    renderItems.add(new RenderItem(plane, shader));
    renderItems.add(new RenderItem(block, shader, new Texture("./assets/textures/trollface.png")));

    Block playerShape = new Block();
    Matrix4f scale = new Matrix4f().identity().translation(0, 0, 4).scale(0.01f);
    playerShape.transform(scale);

    this.player = new Player(window.getInputHandler(), new RenderItem(playerShape, shader));
    camera.attachPlayer(player);

    pointLights.add(new PointLight(new Vector3f(0f, 1.0f, 1.0f), new Vector4f(1, 0, 0, 0)));
  }

  public void start() {
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

    if (window.getInputHandler().isKeyDown(GLFW_KEY_F1)) {
      toggleFullscreen();
    }

    player.update();
    camera.update();
  }

  private void render() {
    // clear the framebuffer
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    player.render();
    for (RenderItem renderItem : renderItems) {
      renderItem.getShader().bind();
      renderItem.getShader().setUniform("sampler", 0);
      renderItem.getShader().setUniform("modelview", camera.getTransformation());
      renderItem.getShader().setUniform("projection", frustum.getTransformation());
      renderItem.getShader().setUniform(pointLights);
      renderItem.render();
      renderItem.getShader().unbind();
    }

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
    glfwSetWindowTitle(window.getId(), "World of Blocks - FPS: " + fpsCounter.getFps());
  }
}
