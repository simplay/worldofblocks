package worldofblocks.rendering.drawables;

import org.joml.Matrix4f;
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
  protected final Instance instance;
  protected Texture texture;
  protected Shader shader;

  private int vId;
  private int tId;
  private int fId;
  private int cId;
  private int nId;

  private boolean hasTextures;

  public RenderItem(Instance instance, Shader shader, Texture texture) {
    this.instance = instance;
    this.shader = shader;
    this.texture = texture;
    this.hasTextures = true;

    initialize();
  }

  public Shader getShader() {
    return shader;
  }

  public Matrix4f getTransformation() {
    return instance.getTransformation();
  }

  public RenderItem(Instance instance, Shader shader) {
    this(instance, shader, null);
    this.hasTextures = false;
  }

  public void moveShape(Vector3f position) {
    instance.translate(position);
    initialize();
  }

  private void initialize() {
    vId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vId);
    glBufferData(GL_ARRAY_BUFFER, createBuffer(instance.getShape().verticesAsFloatArray()), GL_STATIC_DRAW);

    cId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, cId);
    glBufferData(GL_ARRAY_BUFFER, createBuffer(instance.getShape().colorsAsFloatArray()), GL_STATIC_DRAW);

    nId = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, nId);
    glBufferData(GL_ARRAY_BUFFER, createBuffer(instance.getShape().normalsAsFloatArray()), GL_STATIC_DRAW);

    if (hasTextures) {
      tId = glGenBuffers();
      glBindBuffer(GL_ARRAY_BUFFER, tId);
      glBufferData(GL_ARRAY_BUFFER, createBuffer(instance.getShape().textureCoordinatesAsFloatArray()), GL_STATIC_DRAW);
    }

    fId = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, fId);

    IntBuffer buffer = BufferUtils.createIntBuffer(instance.getShape().faceCount());
    buffer.put(instance.getShape().indicesAsIntArray());
    buffer.flip();
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  public void bind() {
    glEnableVertexAttribArray(VertexAttributes.POSITION.getIndex());
    if (hasTextures) {
      glEnableVertexAttribArray(VertexAttributes.TEXTURE.getIndex());
    }
    glEnableVertexAttribArray(VertexAttributes.COLOR.getIndex());
    glEnableVertexAttribArray(VertexAttributes.NORMAL.getIndex());

    if (hasTextures) {
      // TODO: Don't hardcode the sampler
      texture.bind(0);
    }
  }

  public void render() {
    bind();

    glBindBuffer(GL_ARRAY_BUFFER, vId);
    glVertexAttribPointer(
      VertexAttributes.POSITION.getIndex(),
      3,
      GL_FLOAT,
      false,
      0,
      0
    );

    if (hasTextures) {
      glBindBuffer(GL_ARRAY_BUFFER, tId);
      glVertexAttribPointer(
        VertexAttributes.TEXTURE.getIndex(),
        2,
        GL_FLOAT,
        false,
        0,
        0
      );
    }

    glBindBuffer(GL_ARRAY_BUFFER, cId);
    glVertexAttribPointer(
      VertexAttributes.COLOR.getIndex(),
      4,
      GL_FLOAT,
      false,
      0,
      0
    );

    glBindBuffer(GL_ARRAY_BUFFER, nId);
    glVertexAttribPointer(
      VertexAttributes.NORMAL.getIndex(),
      3,
      GL_FLOAT,
      false,
      0,
      0
    );

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, fId);
    glDrawElements(GL_TRIANGLES, instance.getShape().faceCount(), GL_UNSIGNED_INT, 0);

    unbind();
  }

  public void unbind() {
    if (hasTextures) {
      texture.unbind();
    }

    glDisableVertexAttribArray(VertexAttributes.POSITION.getIndex());
    if (hasTextures) {
      glDisableVertexAttribArray(VertexAttributes.TEXTURE.getIndex());
    }
    glDisableVertexAttribArray(VertexAttributes.COLOR.getIndex());
    glDisableVertexAttribArray(VertexAttributes.NORMAL.getIndex());


    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  private FloatBuffer createBuffer(float[] data) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip();
    return buffer;
  }
}
