package worldofblocks;

public class Plane {
  private Shape shape;

  Plane() {
    float[] colors = new float[]{
            // front colors
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f
    };

    int[] indices = new int[]{
            0, 1, 3,
            3, 1, 2
    };

    float[] vertices = new float[]{
            0.0f, -0.2f, 0.0f,
            0.0f, -0.2f, 1.0f,
            1.0f, -0.2f, 0.0f,
            1.0f, -0.2f, 1.0f
    };

    shape = new Shape(
            vertices,
            colors,
            indices
    );
  }

  void render() {
    shape.render();
  }
}
