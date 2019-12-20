package worldofblocks.rendering.drawables;

import org.joml.*;

public abstract class Shape {
  protected Matrix4f transformation = new Matrix4f().identity();
  protected Vector4f[] vertices;
  protected Vector3i[] indices;
  protected Vector3f[] normals;
  protected Vector2f[] textureCoordinates;
  protected Vector4f[] colors;

  protected float[] verticesAsFloatArray() {
    float[] vertices = new float[this.vertices.length * 3];

    int k = 0;
    for (Vector4f v : this.vertices) {
      vertices[3 * k] = v.x;
      vertices[3 * k + 1] = v.y;
      vertices[3 * k + 2] = v.z;
      k++;
    }

    return vertices;
  }

  protected float[] colorsAsFloatArray() {
    float[] colors = new float[this.colors.length * 4];

    int k = 0;
    for (Vector4f c : this.colors) {
      colors[4 * k] = c.x;
      colors[4 * k + 1] = c.y;
      colors[4 * k + 2] = c.z;
      colors[4 * k + 3] = c.w;
      k++;
    }

    return colors;
  }

  protected float[] normalsAsFloatArray() {
    float[] normals = new float[this.normals.length * 3];

    int k = 0;
    for (Vector3f n : this.normals) {
      normals[3 * k] = n.x;
      normals[3 * k + 1] = n.y;
      normals[3 * k + 2] = n.z;
      k++;
    }

    return normals;
  }

  protected float[] textureCoordinatesAsFloatArray() {
    float[] textureCoordinates = new float[this.textureCoordinates.length * 2];

    int k = 0;
    for (Vector2f t : this.textureCoordinates) {
      textureCoordinates[2 * k] = t.x;
      textureCoordinates[2 * k + 1] = t.y;
      k++;
    }

    return textureCoordinates;
  }

  protected int[] indicesAsIntArray() {
    int[] indices = new int[this.indices.length * 3];

    int k = 0;
    for (Vector3i i : this.indices) {
      indices[3 * k] = i.x;
      indices[3 * k + 1] = i.y;
      indices[3 * k + 2] = i.z;
      k++;
    }

    return indices;
  }

  public void transform(Matrix4f t) {
    transformation.mul(t);
    applyTransformationOnVertices();
  }

  public void translate(Vector3f shift) {
    transform(new Matrix4f().identity().translation(shift));
  }

  public void applyTransformationOnVertices() {
    Matrix4f t = new Matrix4f(transformation);
    for (Vector4f v : vertices) {
      v.mul(t);
    }
  }
}
