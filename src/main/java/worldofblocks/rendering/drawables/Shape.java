package worldofblocks.rendering.drawables;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public abstract class Shape {
  protected Matrix4f transformation = new Matrix4f().identity();
  protected Vector4f[] vertices;
  protected int[] indices;
  protected float[] colors;
  protected float[] normals;
  protected float[] textureCoordinates;

  public Shape() {
    this.vertices = getVertices();
    this.indices = getIndices();
    this.colors = getColors();
    this.normals = getNormals();
    this.textureCoordinates = getTextureCoordinates();
  }

  protected float[] verticesAsFloatArray() {
    float[] vertices = new float[this.vertices.length * 4];

    int k = 0;
    for (Vector4f v : this.vertices) {
      vertices[3 * k] = v.x;
      vertices[3 * k + 1] = v.y;
      vertices[3 * k + 2] = v.z;
      k++;
    }

    return vertices;
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

  protected abstract Vector4f[] getVertices();

  protected abstract int[] getIndices();

  protected abstract float[] getColors();

  protected abstract float[] getNormals();

  protected abstract float[] getTextureCoordinates();
}
