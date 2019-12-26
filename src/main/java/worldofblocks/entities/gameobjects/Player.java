package worldofblocks.entities.gameobjects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import worldofblocks.gui.handlers.InputHandler;
import worldofblocks.rendering.drawables.RenderItem;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;

public class Player implements Gameobject {
  private final float scale = 0.01f;

  private final InputHandler inputHandler;
  private final RenderItem renderItem;
  private final Vector3f position;
  private Vector3f viewingDirection;
  private Vector3f sideWalkingDirection;

  public Player(InputHandler inputHandler, RenderItem renderItem) {
    this.inputHandler = inputHandler;
    this.position = new Vector3f();
    this.renderItem = renderItem;
    this.viewingDirection = new Vector3f(0.0f, 0.0f, 1.0f);
    this.sideWalkingDirection = new Vector3f(1.0f, 0.0f, 0.0f);
  }

  public void updateViewingDirection(float yaw) {
    double angle = 0.5 * yaw;
    double a = viewingDirection.x;
    double b = viewingDirection.z;

    this.viewingDirection = new Vector3f(
        (float) (Math.cos(angle) * a - Math.sin(angle) * b),
        0.0f,
        (float) (Math.sin(angle) * a + Math.cos(angle) * b)
    );

    Vector3f tmp = new Vector3f(viewingDirection);
    this.sideWalkingDirection = tmp.rotateY((float) (Math.PI / 2.0));
  }

  public Matrix4f getTransform() {
    return new Matrix4f().translate(position);
  }

  @Override
  public void render() {
    renderItem.render();
  }


  @Override
  public void update() {
    if (inputHandler.isKeyDown(GLFW_KEY_A)) {
      updatePosition(new Vector3f(sideWalkingDirection.x, 0, sideWalkingDirection.z).mul(scale));
    }

    if (inputHandler.isKeyDown(GLFW_KEY_D)) {
      updatePosition(new Vector3f(-sideWalkingDirection.x, 0, -sideWalkingDirection.z).mul(scale));
    }

    if (inputHandler.isKeyDown(GLFW_KEY_W)) {
      updatePosition(new Vector3f(viewingDirection.x, 0, viewingDirection.z).mul(scale));
    }

    if (inputHandler.isKeyDown(GLFW_KEY_S)) {
      updatePosition(new Vector3f(-viewingDirection.x, 0, -viewingDirection.z).mul(scale));
    }

    if (inputHandler.isKeyDown(GLFW_KEY_SPACE)) {
      updatePosition(new Vector3f(0, -0.01f, 0));
    }

    if (inputHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
      updatePosition(new Vector3f(0, 0.01f, 0));
    }
  }

  private void updatePosition(Vector3f shift) {
    renderItem.moveShape(new Vector3f(-shift.x, -shift.y, -shift.z));
    position.add(shift);
  }
}
