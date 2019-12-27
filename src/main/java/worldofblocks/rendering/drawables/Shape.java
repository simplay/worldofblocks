package worldofblocks.rendering.drawables;

import org.joml.*;

public abstract class Shape {
  private float[] cachedVertices;
  private int[] cachedIndices;
  private float[] cachedNormals;
  private float[] cachedTextureCoordinates;
  private float[] cachedColors;

  protected Vector4f[] vertices;
  protected Vector3i[] indices;
  protected Vector3f[] normals;
  protected Vector2f[] textureCoordinates;
  protected Vector4f[] colors;

  public int faceCount() {
    return indices.length * 3;
  }

  protected float[] verticesAsFloatArray() {
    if (cachedVertices != null) {
      return cachedVertices;
    }

    this.cachedVertices = new float[vertices.length * 3];

    int k = 0;
    for (Vector4f v : vertices) {
      cachedVertices[3 * k] = v.x;
      cachedVertices[3 * k + 1] = v.y;
      cachedVertices[3 * k + 2] = v.z;
      k++;
    }

    return cachedVertices;
  }

  protected float[] colorsAsFloatArray() {
    if (cachedColors != null) {
      return cachedColors;
    }

    this.cachedColors = new float[colors.length * 4];

    int k = 0;
    for (Vector4f c : colors) {
      cachedColors[4 * k] = c.x;
      cachedColors[4 * k + 1] = c.y;
      cachedColors[4 * k + 2] = c.z;
      cachedColors[4 * k + 3] = c.w;
      k++;
    }

    return cachedColors;
  }

  protected float[] normalsAsFloatArray() {
    if (cachedNormals != null) {
      return cachedNormals;
    }

    this.cachedNormals = new float[normals.length * 3];

    int k = 0;
    for (Vector3f n : normals) {
      cachedNormals[3 * k] = n.x;
      cachedNormals[3 * k + 1] = n.y;
      cachedNormals[3 * k + 2] = n.z;
      k++;
    }

    return cachedNormals;
  }

  protected float[] textureCoordinatesAsFloatArray() {
    if (cachedTextureCoordinates != null) {
      return cachedTextureCoordinates;
    }

    this.cachedTextureCoordinates = new float[textureCoordinates.length * 2];

    int k = 0;
    for (Vector2f t : textureCoordinates) {
      cachedTextureCoordinates[2 * k] = t.x;
      cachedTextureCoordinates[2 * k + 1] = t.y;
      k++;
    }

    return cachedTextureCoordinates;
  }

  protected int[] indicesAsIntArray() {
    if (cachedIndices != null) {
      return cachedIndices;
    }

    this.cachedIndices = new int[indices.length * 3];

    int k = 0;
    for (Vector3i i : indices) {
      cachedIndices[3 * k] = i.x;
      cachedIndices[3 * k + 1] = i.y;
      cachedIndices[3 * k + 2] = i.z;
      k++;
    }

    return cachedIndices;
  }
}
