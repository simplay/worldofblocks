package worldofblocks;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import worldofblocks.drawables.Block;
import worldofblocks.drawables.Moveable;
import worldofblocks.handlers.InputHandler;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;

public class Player implements Moveable {
  private final InputHandler inputHandler;
  private final Block shape;
  private final Vector3f position;

  public Player(InputHandler inputHandler) {
    this.inputHandler = inputHandler;
    this.shape = new Block();
    this.position = new Vector3f();
  }

  @Override
  public void updatePosition(Vector3f shift) {
    shape.updatePosition(shift);
    position.add(shift);
  }

  public Matrix4f getTransform() {
    return new Matrix4f().translate(position);
  }

  public void render() {
    // TODO: fix translation of player shape
    // shape.render();
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
