package worldofblocks.rendering.drawables;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Instance {
  private final Matrix4f transformation = new Matrix4f().identity();
  private final Shape shape;

  public Instance(Shape shape) {
    this.shape = shape;
  }

  public void transform(Matrix4f t) {
    transformation.mul(t);
  }

  public Matrix4f getTransformation() {
    return transformation;
  }

  public void translate(Vector3f shift) {
    transformation.translation(shift);
  }

  public Shape getShape() {
    return shape;
  }
}
