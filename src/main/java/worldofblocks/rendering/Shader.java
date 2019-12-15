package worldofblocks.rendering;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;


import static org.lwjgl.opengl.GL20.*;

public class Shader {
  private int programId;
  private int vertexShaderId;
  private int fragmentShaderId;

  public Shader(String filename) {
    this.programId = glCreateProgram();

    this.vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
    glShaderSource(vertexShaderId, readFile(filename + ".vs"));
    glCompileShader(vertexShaderId);

    if (glGetShaderi(vertexShaderId, GL_COMPILE_STATUS) != GL_TRUE)  {
      System.err.println(glGetShaderInfoLog(vertexShaderId));
      System.exit(1);
    }

    this.fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(fragmentShaderId, readFile(filename + ".fs"));
    glCompileShader(fragmentShaderId);

    if (glGetShaderi(fragmentShaderId, GL_COMPILE_STATUS) != GL_TRUE)  {
      System.err.println(glGetShaderInfoLog(fragmentShaderId));
      System.exit(1);
    }

    glAttachShader(programId, vertexShaderId);
    glAttachShader(programId, fragmentShaderId);

    GL20.glBindAttribLocation(programId, VertexAttributes.VERTICES.getIndex(), "vertices");
    glBindAttribLocation(programId, VertexAttributes.TEXTURES.getIndex(), "textures");
    glBindAttribLocation(programId, VertexAttributes.COLORS.getIndex(), "colors");

    glLinkProgram(programId);
    if (glGetProgrami(programId, GL_LINK_STATUS) != GL_TRUE) {
      System.err.println(glGetProgramInfoLog(programId));
      System.exit(1);
    }

    glValidateProgram(programId);
    glLinkProgram(programId);
    if (glGetProgrami(programId, GL_VALIDATE_STATUS) != GL_TRUE) {
      System.err.println(glGetProgramInfoLog(programId));
      System.exit(1);
    }
  }

  public void bind() {
    glUseProgram(programId);
  }

  public void unbind() {
    glUseProgram(0);
  }

  public void setUniform(String name, int value) {
    int location = glGetUniformLocation(programId, name);

    if (location != -1) {
      glUniform1f(location, value);
    }
  }

  public void setUniform(String name, Matrix4f value) {
    int location = glGetUniformLocation(programId, name);
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    value.get(buffer);

    if (location != -1) {
      glUniformMatrix4fv(location, false, buffer);
    }
  }

  private String readFile(String filename) {
    StringBuilder program = new StringBuilder();

    BufferedReader buffer;

    try {
      buffer = new BufferedReader(new FileReader(new File("./shaders/" + filename)));
      String line;
      while((line = buffer.readLine())!= null) {
        program.append(line);
        program.append("\n");
      }
      buffer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return program.toString();
  }
}
