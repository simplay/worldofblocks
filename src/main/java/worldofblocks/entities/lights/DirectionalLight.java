package worldofblocks.entities.lights;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class DirectionalLight {
  private final Vector3f radiance;
  private Vector4f direction;

  public DirectionalLight(Vector3f radiance, Vector4f direction) {
    this.radiance = radiance;
    this.direction = direction;
  }

  public Vector4f getDirection() {
    return direction;
  }

  public Vector3f getRadiance() {
    return radiance;
  }

  public void updateDirection(Vector3f position) {
    this.direction = new Vector4f(position, 0);
  }
}
