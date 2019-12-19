package worldofblocks.rendering.drawables;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import worldofblocks.rendering.Shader;
import worldofblocks.rendering.Texture;
import worldofblocks.rendering.VertexAttributes;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class RenderItem {
  protected final Shape shape;
  protected Texture texture;
  protected Shader shader;

  private int vId;
  private int tId;
  private int fId;
  private int cId;
  private int nId;

  private boolean hasTextures;

  public RenderItem(Shape shape, Shader shader, Texture texture) {
    this.shape = shape;
    this.shader = shader;
    this.texture = texture;
    this.hasTextures = true;

    initialize();
  }

  public Shader getShader() {
    return shader;
  }

  public RenderItem(Shape shape, Shader shader) {
    this(shape, shader, null);
    this.hasTextures = false;
  }

  public void moveShape(Vector3f shift) {
    shape.translate(new Vector3f(-shift.x, -shift.y, -shift.z));
    initialize();
  }

  private void initialize() {
    vId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vId);
    glBufferData(GL_ARRAY_BUFFER, createBuffer(shape.verticesAsFloatArray()), GL_STATIC_DRAW);

    cId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, cId);
    glBufferData(GL_ARRAY_BUFFER, createBuffer(shape.colors), GL_STATIC_DRAW);

    nId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, nId);
    glBufferData(GL_ARRAY_BUFFER, createBuffer(shape.normals), GL_STATIC_DRAW);

    if (hasTextures) {
      tId = glGenBuffers();
      glBindBuffer(GL_ARRAY_BUFFER, tId);
      glBufferData(GL_ARRAY_BUFFER, createBuffer(shape.textureCoordinates), GL_STATIC_DRAW);
    }

    fId = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, fId);

    IntBuffer buffer = BufferUtils.createIntBuffer(shape.indices.length);
    buffer.put(shape.indices);
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
    glEnableVertexAttribArray(VertexAttributes.NORMALS.getIndex());

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

    glBindBuffer(GL_ARRAY_BUFFER, nId);
    glVertexAttribPointer(
            VertexAttributes.NORMALS.getIndex(),
            3,
            GL_FLOAT,
            false,
            0,
            0
    );

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, fId);
    glDrawElements(GL_TRIANGLES, shape.indices.length, GL_UNSIGNED_INT, 0);

    glDrawArrays(GL_TRIANGLES, 0, shape.indices.length);

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
    glDisableVertexAttribArray(VertexAttributes.NORMALS.getIndex());
  }

  private FloatBuffer createBuffer(float[] data) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }
}
