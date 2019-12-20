package worldofblocks.rendering.drawables;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import worldofblocks.rendering.Texture;

public class Cube extends Shape {
  @Override
  protected float[] getTextureCoordinates() {
    float[] textureCoordinates = {
            0, 0, 1, 0, 1, 1, 0, 1,
            0, 0, 1, 0, 1, 1, 0, 1,
            0, 0, 1, 0, 1, 1, 0, 1,
            0, 0, 1, 0, 1, 1, 0, 1,
            0, 0, 1, 0, 1, 1, 0, 1,
            0, 0, 1, 0, 1, 1, 0, 1
    };
    return textureCoordinates;
  }

  @Override
  protected int[] getIndices() {
    int[] indices = {
            0, 2, 3, 0, 1, 2,      // front face
            4, 6, 7, 4, 5, 6,      // left face
            8, 10, 11, 8, 9, 10,    // back face
            12, 14, 15, 12, 13, 14,    // right face
            16, 18, 19, 16, 17, 18,    // top face
            20, 22, 23, 20, 21, 22 // bottom face
    };
    return indices;
  }

  @Override
  protected float[] getNormals() {
    float normals[] = {
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f
    };
    return normals;
  }

  @Override
  protected float[] getColors() {
    float[] colors = {
            1.0f, 0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f, 0.0f,  //back

            0.0f, 1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 1.0f, 0.0f,
    };

    return colors;
  }

  @Override
  protected Vector4f[] getVertices() {
    Vector4f[] origVertices = new Vector4f[]{
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

    for (Vector4f v : origVertices) {
      v.mul(transformation);
    }

    return origVertices;
  }

  public Cube() {
    super();
  }
}
