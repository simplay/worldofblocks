package worldofblocks.rendering.drawables;

import org.joml.*;

public class Cube extends Shape {
  public Cube() {
    this.textureCoordinates = new Vector2f[]{
      new Vector2f(0, 0),
      new Vector2f(1, 0),
      new Vector2f(1, 1),
      new Vector2f(0, 1),

      new Vector2f(0, 0),
      new Vector2f(1, 0),
      new Vector2f(1, 1),
      new Vector2f(0, 1),

      new Vector2f(0, 0),
      new Vector2f(1, 0),
      new Vector2f(1, 1),
      new Vector2f(0, 1),

      new Vector2f(0, 0),
      new Vector2f(1, 0),
      new Vector2f(1, 1),
      new Vector2f(0, 1),

      new Vector2f(0, 0),
      new Vector2f(1, 0),
      new Vector2f(1, 1),
      new Vector2f(0, 1),

      new Vector2f(0, 0),
      new Vector2f(1, 0),
      new Vector2f(1, 1),
      new Vector2f(0, 1)
    };

    this.indices = new Vector3i[]{
      // front face
      new Vector3i(0, 2, 3),
      new Vector3i(0, 1, 2),

      // left face
      new Vector3i(4, 6, 7),
      new Vector3i(4, 5, 6),

      // back face
      new Vector3i(8, 10, 11),
      new Vector3i(8, 9, 10),

      // right face
      new Vector3i(12, 14, 15),
      new Vector3i(12, 13, 14),

      // top face
      new Vector3i(16, 18, 19),
      new Vector3i(16, 17, 18),

      // bottom face
      new Vector3i(20, 22, 23),
      new Vector3i(20, 21, 22)
    };

    this.normals = new Vector3f[]{
      new Vector3f(0.0f, 0.0f, 1.0f),
      new Vector3f(.0f, 0.0f, 1.0f),
      new Vector3f(.0f, 0.0f, 1.0f),
      new Vector3f(.0f, 0.0f, 1.0f),
      new Vector3f(1.0f, 0.0f, 0.0f),
      new Vector3f(1.0f, 0.0f, 0.0f),
      new Vector3f(1.0f, 0.0f, 0.0f),
      new Vector3f(1.0f, 0.0f, 0.0f),
      new Vector3f(.0f, 0.0f, -1.0f),
      new Vector3f(.0f, 0.0f, -1.0f),
      new Vector3f(.0f, 0.0f, -1.0f),
      new Vector3f(.0f, 0.0f, -1.0f),
      new Vector3f(.0f, 0.0f, 0.0f),
      new Vector3f(.0f, 0.0f, 0.0f),
      new Vector3f(.0f, 0.0f, 0.0f),
      new Vector3f(.0f, 0.0f, 0.0f),
      new Vector3f(.0f, 1.0f, 0.0f),
      new Vector3f(.0f, 1.0f, 0.0f),
      new Vector3f(.0f, 1.0f, 0.0f),
      new Vector3f(.0f, 1.0f, 0.0f),
      new Vector3f(.0f, -1.0f, 0.0f),
      new Vector3f(.0f, -1.0f, 0.0f),
      new Vector3f(.0f, -1.0f, 0.0f),
      new Vector3f(.0f, -1.0f, 0.0f)
    };


    this.colors = new Vector4f[]{
      new Vector4f(1.0f, 0.0f, 0.0f, 0.0f),
      new Vector4f(1.0f, 0.0f, 0.0f, 0.0f),
      new Vector4f(1.0f, 0.0f, 0.0f, 0.0f),
      new Vector4f(1.0f, 0.0f, 0.0f, 0.0f),
      new Vector4f(0.0f, 1.0f, 0.0f, 0.0f),
      new Vector4f(0.0f, 1.0f, 0.0f, 0.0f),
      new Vector4f(0.0f, 1.0f, 0.0f, 0.0f),
      new Vector4f(0.0f, 1.0f, 0.0f, 0.0f),
      new Vector4f(1.0f, 1.0f, 0.0f, 0.0f),
      new Vector4f(1.0f, 1.0f, 0.0f, 0.0f),
      new Vector4f(1.0f, 1.0f, 0.0f, 0.0f),
      new Vector4f(1.0f, 1.0f, 0.0f, 0.0f),  //back

      new Vector4f(0.0f, 1.0f, 1.0f, 0.0f),
      new Vector4f(0.0f, 1.0f, 1.0f, 0.0f),
      new Vector4f(0.0f, 1.0f, 1.0f, 0.0f),
      new Vector4f(0.0f, 1.0f, 1.0f, 0.0f),
      new Vector4f(0.0f, 0.0f, 1.0f, 0.0f),
      new Vector4f(0.0f, 0.0f, 1.0f, 0.0f),
      new Vector4f(0.0f, 0.0f, 1.0f, 0.0f),
      new Vector4f(0.0f, 0.0f, 1.0f, 0.0f),
      new Vector4f(1.0f, 0.0f, 1.0f, 0.0f),
      new Vector4f(1.0f, 0.0f, 1.0f, 0.0f),
      new Vector4f(1.0f, 0.0f, 1.0f, 0.0f),
      new Vector4f(1.0f, 0.0f, 1.0f, 0.0f)
    };


    this.vertices = new Vector4f[]{
      // front face
      new Vector4f(-1.0f, -1.0f, 1.0f, 1.0f),
      new Vector4f(1.0f, -1.0f, 1.0f, 1.0f),
      new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
      new Vector4f(-1.0f, 1.0f, 1.0f, 1.0f),

      // left face
      new Vector4f(-1.0f, -1.0f, -1.0f, 1.0f),
      new Vector4f(-1.0f, -1.0f, 1.0f, 1.0f),
      new Vector4f(-1.0f, 1.0f, 1.0f, 1.0f),
      new Vector4f(-1.0f, 1.0f, -1.0f, 1.0f),

      // back face
      new Vector4f(1.0f, -1.0f, -1.0f, 1.0f),
      new Vector4f(-1.0f, -1.0f, -1.0f, 1.0f),
      new Vector4f(-1.0f, 1.0f, -1.0f, 1.0f),
      new Vector4f(1.0f, 1.0f, -1.0f, 1.0f),

      // right face
      new Vector4f(1.0f, -1.0f, 1.0f, 1.0f),
      new Vector4f(1.0f, -1.0f, -1.0f, 1.0f),
      new Vector4f(1.0f, 1.0f, -1.0f, 1.0f),
      new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),

      // top face
      new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
      new Vector4f(1.0f, 1.0f, -1.0f, 1.0f),
      new Vector4f(-1.0f, 1.0f, -1.0f, 1.0f),
      new Vector4f(-1.0f, 1.0f, 1.0f, 1.0f),

      // bottom face
      new Vector4f(-1.0f, -1.0f, 1.0f, 1.0f),
      new Vector4f(-1.0f, -1.0f, -1.0f, 1.0f),
      new Vector4f(1.0f, -1.0f, -1.0f, 1.0f),
      new Vector4f(1.0f, -1.0f, 1.0f, 1.0f)
    };
  }
}
