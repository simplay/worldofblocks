package worldofblocks.drawables;

import worldofblocks.Texture;

public class Block extends Shape {

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
  protected float[] getVertices() {
    float[] vertices = {
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,      // front face

            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f,    // left face

            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,    // back face

            1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, 1.0f,      // right face

            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,      // top face

            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f      // bottom face
    };
    for (int k = 0; k < vertices.length; k++) {
      vertices[k] -= 0.5f;
    }
    return vertices;
  }

  public Block() {
    super(
            new Texture("./textures/trollface.png")
    );
  }

  float[] getVertices(float dx, float dy) {
    float[] vertices = {
            -1.0f + dx, -1.0f + dy, 1.0f,
            1.0f + dx, -1.0f + dy, 1.0f,
            1.0f + dx, 1.0f + dy, 1.0f,
            -1.0f + dx, 1.0f + dy, 1.0f,      // front face

            -1.0f + dx, -1.0f + dy, -1.0f,
            -1.0f + dx, -1.0f + dy, 1.0f,
            -1.0f + dx, 1.0f + dy, 1.0f,
            -1.0f + dx, 1.0f + dy, -1.0f,    // left face

            1.0f + dx, -1.0f + dy, -1.0f,
            -1.0f + dx, -1.0f + dy, -1.0f,
            -1.0f + dx, 1.0f + dy, -1.0f,
            1.0f + dx, 1.0f + dy, -1.0f,    // back face

            1.0f + dx, -1.0f + dy, 1.0f,
            1.0f + dx, -1.0f + dy, -1.0f,
            1.0f + dx, 1.0f + dy, -1.0f,
            1.0f + dx, 1.0f + dy, 1.0f,      // right face

            1.0f + dx, 1.0f + dy, 1.0f,
            1.0f + dx, 1.0f + dy, -1.0f,
            -1.0f + dx, 1.0f + dy, -1.0f,
            -1.0f + dx, 1.0f + dy, 1.0f,      // top face

            -1.0f + dx, -1.0f + dy, 1.0f,
            -1.0f + dx, -1.0f + dy, -1.0f,
            1.0f + dx, -1.0f + dy, -1.0f,
            1.0f + dx, -1.0f + dy, 1.0f      // bottom face
    };
    for (int k = 0; k < vertices.length; k++) {
      vertices[k] -= 0.5f;
    }
    return vertices;
  }

  public void updateVertices(float dx, float dy) {
    updateVertices(getVertices(dx, dy));
  }
}
