package worldofblocks.drawables;

import org.lwjgl.BufferUtils;
import worldofblocks.Texture;
import worldofblocks.VertexAttributes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public abstract class Shape {
  private float[] vertices;
  private int[] indices;
  private float[] colors;
  private float[] normals;
  private float[] textureCoordinates;
  private Texture texture;

  private int faces;
  private int vId;
  private int tId;
  private int fId;
  private int cId;
  // TODO implement nId

  private boolean hasTextures = false;

  protected abstract float[] getVertices();
  protected abstract int[] getIndices();
  protected abstract float[] getColors();
  protected abstract float[] getNormals();
  protected abstract float[] getTextureCoordinates();

  public Shape(Texture texture) {
    this.vertices = getVertices();
    this.indices = getIndices();
    this.faces = indices.length;
    this.colors = getColors();
    this.normals = getNormals();
    this.textureCoordinates = getTextureCoordinates();
    this.texture = texture;
    this.hasTextures = true;
    initialize();
  }

  public Shape() {
    this.vertices = getVertices();
    this.indices = getIndices();
    faces = indices.length;
    this.colors = getColors();
    this.normals = getNormals();
    initialize();
  }

  void updateVertices(float[] vertices) {
    this.vertices = vertices;
    initialize();
  }

  private void initialize() {
    vId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vId);
    glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);

    cId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, cId);
    glBufferData(GL_ARRAY_BUFFER, createBuffer(colors), GL_STATIC_DRAW);

    if (hasTextures) {
      tId = glGenBuffers();
      glBindBuffer(GL_ARRAY_BUFFER, tId);
      glBufferData(GL_ARRAY_BUFFER, createBuffer(textureCoordinates), GL_STATIC_DRAW);
    }

    fId = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, fId);

    IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
    buffer.put(indices);
    buffer.flip();
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

    glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  public void render() {
    glEnableVertexAttribArray(VertexAttributes.VERTICES.getIndex());
    if (hasTextures) {
      glEnableVertexAttribArray(VertexAttributes.TEXTURES.getIndex());
    }
    glEnableVertexAttribArray(VertexAttributes.COLORS.getIndex());

    if (hasTextures) {
      texture.bind(0);
    }

    glBindBuffer(GL_ARRAY_BUFFER, vId);
    glVertexAttribPointer(
            VertexAttributes.VERTICES.getIndex(),
            3,
            GL_FLOAT,
            false,
            0,
            0
    );

    if (hasTextures) {
      glBindBuffer(GL_ARRAY_BUFFER, tId);
      glVertexAttribPointer(
              VertexAttributes.TEXTURES.getIndex(),
              2,
              GL_FLOAT,
              false,
              0,
              0
      );
    }

    glBindBuffer(GL_ARRAY_BUFFER, cId);
    glVertexAttribPointer(
            VertexAttributes.COLORS.getIndex(),
            4,
            GL_FLOAT,
            false,
            0,
            0
    );

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, fId);
    glDrawElements(GL_TRIANGLES, faces, GL_UNSIGNED_INT, 0);

    glDrawArrays(GL_TRIANGLES, 0, faces);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);

    if (hasTextures) {
      texture.unbind();
    }

    glDisableVertexAttribArray(VertexAttributes.VERTICES.getIndex());
    if (hasTextures) {
      glDisableVertexAttribArray(VertexAttributes.TEXTURES.getIndex());
    }
    glDisableVertexAttribArray(VertexAttributes.COLORS.getIndex());
  }

  private FloatBuffer createBuffer(float[] data) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }
}
