package worldofblocks.game;

import worldofblocks.entities.gameobjects.Gameobject;
import worldofblocks.gui.Window;
import worldofblocks.rendering.drawables.RenderItem;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Game implements Subscriber {

  private int windowWidth;
  private int windowHeight;
  private boolean fullscreen;

  private Window window;

  private boolean running;

  private FpsCounter fpsCounter;
  private WorldTimer worldTimer;

  private Scene scene;

  public Game(int windowWidth, int windowHeight, boolean fullscreen) {
    this.running = true;
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;
    this.fullscreen = fullscreen;
    this.worldTimer = new WorldTimer();
  }

  private void init() {
    this.window = new Window(windowWidth, windowHeight, fullscreen);

    // hide cursor but enable mouse grab.
    glfwSetInputMode(window.getId(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

    glEnable(GL_TEXTURE_2D);
    glEnable(GL_DEPTH_TEST);
    glDepthFunc(GL_LESS);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE);

    this.fpsCounter = new FpsCounter();
    this.scene = new Scene(window);

    worldTimer.addSubscriber(this);
    worldTimer.start();
  }

  // TODO: fixme - crashes the app
  private void toggleFullscreen() {
//    window.toggleFullscreen();
//    scene.initFrustum();
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

    for (Gameobject gameobject : this.scene.getGameobjects()) {
      gameobject.update();
    }
    scene.getCamera().update();
  }

  private void render() {
    // clear the framebuffer
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // TODO: unify iteration over game objects and render-items
    for (Gameobject gameobject : this.scene.getGameobjects()) {
      if (gameobject.getRenderItem() == null) {
        gameobject.render();
        continue;
      }

      gameobject.getRenderItem().getShader().bind();
      gameobject.getRenderItem().getShader().setUniform("sampler", 0);
      gameobject.getRenderItem().getShader().setUniform("modelview", scene.getCamera().getTransformation());
      gameobject.getRenderItem().getShader().setUniform("projection", scene.getFrustum().getTransformation());
      gameobject.getRenderItem().getShader().setUniform(scene.getPointLights());
      gameobject.getRenderItem().getShader().setUniform("sunDirection", scene.getSun().getLight().getDirection());
      gameobject.getRenderItem().getShader().setUniform("sunRadiance", scene.getSun().getLight().getRadiance());
      gameobject.getRenderItem().render();
      gameobject.getRenderItem().getShader().unbind();
    }

    for (RenderItem renderItem : scene.getRenderItems()) {
      renderItem.getShader().bind();
      renderItem.getShader().setUniform("sampler", 0);
      renderItem.getShader().setUniform("modelview", scene.getCamera().getTransformation());
      renderItem.getShader().setUniform("projection", scene.getFrustum().getTransformation());
      renderItem.getShader().setUniform(scene.getPointLights());
      renderItem.getShader().setUniform("sunDirection", scene.getSun().getLight().getDirection());
      renderItem.getShader().setUniform("sunRadiance", scene.getSun().getLight().getRadiance());
      renderItem.render();
      renderItem.getShader().unbind();
    }

    // swap the color buffers
    glfwSwapBuffers(window.getId());

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
