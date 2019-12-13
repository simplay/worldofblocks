package worldofblocks.drawables;

public class Plane extends Shape {

  @Override
  protected float[] getVertices() {
    float[] vertices = {
            0.0f, -0.2f, 0.0f,
            0.0f, -0.2f, 1.0f,
            1.0f, -0.2f, 0.0f,
            1.0f, -0.2f, 1.0f
    };
    return vertices;
  }

  @Override
  protected int[] getIndices() {
    int[] indices = {
            0, 1, 3,
            3, 1, 2
    };
    return indices;
  }

  @Override
  protected float[] getColors() {
    float[] colors = {
            // front colors
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f
    };
    return colors;
  }

  @Override
  protected float[] getNormals() {
    float[] normals = {
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f
    };

    return normals;
  }

  @Override
  protected float[] getTextureCoordinates() {
    float[] textureCoordinates = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
    };
    return textureCoordinates;
  }

  public Plane() {
    super();
  }
}
