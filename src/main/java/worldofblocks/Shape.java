package worldofblocks;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Shape {
  private float[] vertices;
  private float[] textureCoordinates;
  int[] indices;

  private int drawCount;
  private int vId;
  private int tId;
  private int fId;

  public Shape(float[] vertices, float[] textureCoordinates, int[] indices) {
    drawCount = indices.length; // we are drawing 3d points
    this.vertices = vertices;
    this.textureCoordinates = textureCoordinates;
    this.indices = indices;
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

    tId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, tId);
    glBufferData(GL_ARRAY_BUFFER, createBuffer(textureCoordinates), GL_STATIC_DRAW);

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
    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_TEXTURE_COORD_ARRAY);

    glBindBuffer(GL_ARRAY_BUFFER, vId);
    glVertexPointer(3, GL_FLOAT, 0, 0);

    glBindBuffer(GL_ARRAY_BUFFER, tId);
    glTexCoordPointer(2, GL_FLOAT, 0, 0);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, fId);
    glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);

    glDrawArrays(GL_TRIANGLES, 0, drawCount);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glDisableClientState(GL_VERTEX_ARRAY);
    glDisableClientState(GL_TEXTURE_COORD_ARRAY);
  }

  private FloatBuffer createBuffer(float[] data) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }
}
