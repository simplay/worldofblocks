package worldofblocks;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import worldofblocks.drawables.Block;
import worldofblocks.drawables.Moveable;

public class Player implements Moveable {
  private final Block shape;
  private final Vector3f position;

  public Player() {
    this.shape = new Block();
    this.position = new Vector3f();
  }

  @Override
  public void updatePosition(Vector3f shift) {
    shape.updatePosition(shift);






    position.add(shift);
    System.out.println(position);
  }

  public Matrix4f getTransform() {
    return new Matrix4f().translate(position);
  }

  public void render() {
    shape.render();
  }
}
