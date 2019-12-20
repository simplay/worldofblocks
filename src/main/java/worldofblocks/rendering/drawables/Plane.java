package worldofblocks.rendering.drawables;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;

public class Plane extends Shape {
  public Plane(float shift) {
    this.vertices = new Vector4f[]{
            new Vector4f(0.0f - shift, -0.1f, 0.0f - shift, 1.0f),
            new Vector4f(0.0f - shift, -0.1f, 1.0f + shift, 1.0f),
            new Vector4f(1.0f + shift, -0.1f, 0.0f - shift, 1.0f),
            new Vector4f(1.0f + shift, -0.1f, 1.0f + shift, 1.0f)
    };

    this.indices = new Vector3i[]{
            new Vector3i(0, 1, 3),
            new Vector3i(3, 1, 2)
    };

    this.colors = new Vector4f[]{
            // front colors
            new Vector4f(0.0f, 1.0f, 0.0f, 0.0f),
            new Vector4f(0.0f, 1.0f, 0.0f, 0.0f),
            new Vector4f(0.0f, 1.0f, 0.0f, 0.0f),
            new Vector4f(0.0f, 1.0f, 0.0f, 0.0f)
    };

    this.normals = new Vector3f[] {
            new Vector3f(0.0f, 0.0f, 1.0f),
            new Vector3f(.0f, 0.0f, 1.0f),
            new Vector3f(.0f, 0.0f, 1.0f),
            new Vector3f(.0f, 0.0f, 1.0f)
    };

    this.textureCoordinates = new Vector2f[] {
            new Vector2f(0.0f, 0.0f),
            new Vector2f(0.0f, 1.0f),
            new Vector2f(1.0f, 0.0f),
            new Vector2f(1.0f, 1.0f)
    };
  }
}
