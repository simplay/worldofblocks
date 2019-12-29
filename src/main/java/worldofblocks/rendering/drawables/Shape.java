package worldofblocks.rendering.drawables;

import org.joml.*;
import worldofblocks.rendering.VertexAttributes;

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
    return indices.length * VertexAttributes.FACE.getElementCount();
  }

  protected float[] verticesAsFloatArray() {
    if (cachedVertices != null) {
      return cachedVertices;
    }

    int elementCount = VertexAttributes.POSITION.getElementCount();
    this.cachedVertices = new float[vertices.length * elementCount];

    int k = 0;
    for (Vector4f v : vertices) {
      cachedVertices[elementCount * k] = v.x;
      cachedVertices[elementCount * k + 1] = v.y;
      cachedVertices[elementCount * k + 2] = v.z;
      k++;
    }

    return cachedVertices;
  }

  protected float[] colorsAsFloatArray() {
    if (cachedColors != null) {
      return cachedColors;
    }

    int elementCount = VertexAttributes.COLOR.getElementCount();
    this.cachedColors = new float[colors.length * elementCount];

    int k = 0;
    for (Vector4f c : colors) {
      cachedColors[elementCount * k] = c.x;
      cachedColors[elementCount * k + 1] = c.y;
      cachedColors[elementCount * k + 2] = c.z;
      cachedColors[elementCount * k + 3] = c.w;
      k++;
    }

    return cachedColors;
  }

  protected float[] normalsAsFloatArray() {
    if (cachedNormals != null) {
      return cachedNormals;
    }

    int elementCount = VertexAttributes.NORMAL.getElementCount();
    this.cachedNormals = new float[normals.length * elementCount];

    int k = 0;
    for (Vector3f n : normals) {
      cachedNormals[elementCount * k] = n.x;
      cachedNormals[elementCount * k + 1] = n.y;
      cachedNormals[elementCount * k + 2] = n.z;
      k++;
    }

    return cachedNormals;
  }

  protected float[] textureCoordinatesAsFloatArray() {
    if (cachedTextureCoordinates != null) {
      return cachedTextureCoordinates;
    }

    int elementCount = VertexAttributes.TEXTURE.getElementCount();
    this.cachedTextureCoordinates = new float[textureCoordinates.length * elementCount];

    int k = 0;
    for (Vector2f t : textureCoordinates) {
      cachedTextureCoordinates[elementCount * k] = t.x;
      cachedTextureCoordinates[elementCount * k + 1] = t.y;
      k++;
    }

    return cachedTextureCoordinates;
  }

  protected int[] indicesAsIntArray() {
    if (cachedIndices != null) {
      return cachedIndices;
    }

    int elementCount = VertexAttributes.FACE.getElementCount();
    this.cachedIndices = new int[indices.length * elementCount];

    int k = 0;
    for (Vector3i i : indices) {
      cachedIndices[elementCount * k] = i.x;
      cachedIndices[elementCount * k + 1] = i.y;
      cachedIndices[elementCount * k + 2] = i.z;
      k++;
    }

    return cachedIndices;
  }
}
