
package worldofblocks.entities.gameobjects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import worldofblocks.entities.lights.DirectionalLight;
import worldofblocks.gui.handlers.InputHandler;
import worldofblocks.rendering.drawables.RenderItem;

import static org.lwjgl.glfw.GLFW.*;

public class Sun {
  private final InputHandler inputHandler;
  private final RenderItem renderItem;
  private final Vector3f position;
  private final DirectionalLight light;

  public Sun(InputHandler inputHandler, RenderItem renderItem) {
    this.inputHandler = inputHandler;
    this.position = new Vector3f();
    this.renderItem = renderItem;
    this.light = new DirectionalLight(new Vector3f(1), new Vector4f(position, 0));
  }

  public DirectionalLight getLight() {
    return light;
  }

  public void updatePosition(Vector3f shift) {
    renderItem.moveShape(new Vector3f(-shift.x, -shift.y, -shift.z));
    position.add(shift);
    light.updateDirection(position);
  }

  public Matrix4f getTransform() {
    return new Matrix4f().translate(position);
  }

  public void render() {
    renderItem.render();
  }

  public void update() {
    if (inputHandler.isKeyDown(GLFW_KEY_LEFT)) {
      updatePosition(new Vector3f(-0.01f, 0, 0));
    }

    if (inputHandler.isKeyDown(GLFW_KEY_RIGHT)) {
      updatePosition(new Vector3f(0.01f, 0, 0));
    }

    if (inputHandler.isKeyDown(GLFW_KEY_UP)) {
      updatePosition(new Vector3f(0, 0.01f, 0));
    }

    if (inputHandler.isKeyDown(GLFW_KEY_DOWN)) {
      updatePosition(new Vector3f(0, -0.01f, 0));
    }
  }
}
