package worldofblocks.entities.lights;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class PointLight {
  private final Vector3f radiance;
  private final Vector4f position;

  public PointLight(Vector3f radiance, Vector4f position) {
    this.radiance = radiance;
    this.position = position;
  }

  public Vector4f getPosition() {
    return position;
  }

  public Vector3f getRadiance() {
    return radiance;
  }
}
