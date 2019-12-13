package worldofblocks;

public class Block {
  private Shape shape;

  // TODO: refactor this class, define a common shape interface with update methods etc.
  public Block() {
    float[] textureCoordinates = new float[]{
            1, 0,
            0, 0,
            1, 1,
            0, 1,
            0, 0,
            1, 0,
            1, 1,
            0, 1,
            1, 1,
            0, 1,
    };

    int[] indices = new int[]{
            0, 6, 4,
            0, 2, 6,
            0, 3, 2,
            0, 1, 3,
            2, 7, 6,
            2, 3, 7,
            4, 6, 7,
            4, 7, 5,
            0, 4, 5,
            0, 5, 1,
            1, 5, 7,
            1, 7, 3
    };

    float[] colors = new float[]{
            // front colors
            1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            // back colors
            1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 1.0f
    };

    shape = new Shape(
            this.getVertices(0, 0),
            colors,
            textureCoordinates,
            indices,
            new Texture("./textures/trollface.png")
    );
  }

  void render() {
    shape.render();
  }

  float[] getVertices(float dx, float dy) {
    float[] vertices = new float[]{
            // front
            0.0f + dx, 0.0f + dy, 0.0f,
            0.0f + dx, 0.0f + dy, 1.0f,
            0.0f + dx, 1.0f + dy, 0.0f,
            0.0f + dx, 1.0f + dy, 1.0f,
            1.0f + dx, 0.0f + dy, 0.0f,
            1.0f + dx, 0.0f + dy, 1.0f,
            1.0f + dx, 1.0f + dy, 0.0f,
            1.0f + dx, 1.0f + dy, 1.0f
    };
    for (int k = 0; k < vertices.length; k++) {
      vertices[k] -= 0.5f;
    }
    return vertices;
  }

  public void updateVertices(float dx, float dy) {
    shape.updateVertices(getVertices(dx, dy));
  }
}
