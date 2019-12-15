package worldofblocks.entities.gameobjects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import worldofblocks.gui.handlers.InputHandler;
import worldofblocks.rendering.drawables.RenderItem;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;

public class Player {
  private final InputHandler inputHandler;
  private final RenderItem renderItem;
  private final Vector3f position;

  public Player(InputHandler inputHandler, RenderItem renderItem) {
    this.inputHandler = inputHandler;
    this.position = new Vector3f();
    this.renderItem = renderItem;
  }

  public void updatePosition(Vector3f shift) {
    renderItem.moveShape(new Vector3f(-shift.x, -shift.y, -shift.z));
    position.add(shift);
  }

  public Matrix4f getTransform() {
    return new Matrix4f().translate(position);
  }

  public void render() {
    renderItem.render();
  }

  public void update() {
    if (inputHandler.isKeyDown(GLFW_KEY_A)) {
      updatePosition(new Vector3f(0.01f, 0, 0));
    }

    if (inputHandler.isKeyDown(GLFW_KEY_D)) {
      updatePosition(new Vector3f(-0.01f, 0, 0));
    }

    if (inputHandler.isKeyDown(GLFW_KEY_W)) {
      updatePosition(new Vector3f(0, 0, 0.01f));
    }

    if (inputHandler.isKeyDown(GLFW_KEY_S)) {
      updatePosition(new Vector3f(0, 0.0f, -0.01f));
    }

    if (inputHandler.isKeyDown(GLFW_KEY_SPACE)) {
      updatePosition(new Vector3f(0, -0.01f, 0));
    }

    if (inputHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
      updatePosition(new Vector3f(0, 0.01f, 0));
    }
  }
}
