package worldofblocks.drawables;

import worldofblocks.drawables.Shape;

public class Plane {
  private Shape shape;

  public Plane() {
    float[] colors = {
            // front colors
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f
    };

    int[] indices = {
            0, 1, 3,
            3, 1, 2
    };

    float[] vertices = {
            0.0f, -0.2f, 0.0f,
            0.0f, -0.2f, 1.0f,
            1.0f, -0.2f, 0.0f,
            1.0f, -0.2f, 1.0f
    };

    float[] normals = {
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f
    };

    shape = new Shape(
            vertices,
            colors,
            indices
    );
  }

  public void render() {
    shape.render();
  }
}
