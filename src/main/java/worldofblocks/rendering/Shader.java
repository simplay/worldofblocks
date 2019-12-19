package worldofblocks.rendering;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import worldofblocks.entities.lights.PointLight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.List;


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

    if (glGetShaderi(vertexShaderId, GL_COMPILE_STATUS) != GL_TRUE) {
      System.err.println(glGetShaderInfoLog(vertexShaderId));
      System.exit(1);
    }

    this.fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);

    glShaderSource(fragmentShaderId, readFile(filename + ".fs"));
    glCompileShader(fragmentShaderId);

    if (glGetShaderi(fragmentShaderId, GL_COMPILE_STATUS) != GL_TRUE) {
      System.err.println(glGetShaderInfoLog(fragmentShaderId));
      System.exit(1);
    }

    glAttachShader(programId, vertexShaderId);
    glAttachShader(programId, fragmentShaderId);

    GL20.glBindAttribLocation(programId, VertexAttributes.POSITION.getIndex(), "position");
    glBindAttribLocation(programId, VertexAttributes.TEXTURE.getIndex(), "texture");
    glBindAttribLocation(programId, VertexAttributes.COLOR.getIndex(), "color");
    glBindAttribLocation(programId, VertexAttributes.NORMAL.getIndex(), "normal");

    glLinkProgram(programId);
    if (glGetProgrami(programId, GL_LINK_STATUS) != GL_TRUE) {
      System.err.println(glGetProgramInfoLog(programId));
      System.exit(1);
    }

    // when the graphics card does only support GLSL < v150
    if (!GraphicDetails.runsReducedMode()) {
      glValidateProgram(programId);
      if (glGetProgrami(programId, GL_VALIDATE_STATUS) != GL_TRUE) {
        System.err.println(glGetProgramInfoLog(programId));
        System.exit(1);
      }
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
      glUniform1i(location, value);
    }
  }

  public void setUniform(String name, float value) {
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

  public void setUniform(String name, Vector4f value) {
    int location = glGetUniformLocation(programId, name);
    if (location != -1) {
      glUniform4f(location, value.x, value.y, value.z, value.w);
    }
  }

  public void setUniform(String name, Vector3f value) {
    int location = glGetUniformLocation(programId, name);
    if (location != -1) {
      glUniform3f(location, value.x, value.y, value.z);
    }
  }

  public void setUniform(List<PointLight> value) {
    float[] lightDirections = new float[4 * value.size()];
    float[] radiances = new float[3 * value.size()];

    int k = 0;
    for (PointLight light : value) {
      Vector3f radiance = light.getRadiance();
      Vector4f lightPosition = light.getPosition();

      radiances[k * 3] = radiance.x;
      radiances[k * 3 + 1] = radiance.y;
      radiances[k * 3 + 2] = radiance.z;

      lightDirections[k * 4] = lightPosition.x;
      lightDirections[k * 4 + 1] = lightPosition.y;
      lightDirections[k * 4 + 2] = lightPosition.z;
      lightDirections[k * 4 + 3] = lightPosition.w;

      k++;
    }

    int location = glGetUniformLocation(programId, "pointLightRadiances");
    if (location != -1) {
      glUniform3fv(location, radiances);
    }

    location = glGetUniformLocation(programId, "pointLightPositions");
    if (location != -1) {
      glUniform4fv(location, lightDirections);
    }

    setUniform("pointLightCount", value.size());
  }

  private String readFile(String filename) {
    StringBuilder program = new StringBuilder();

    BufferedReader buffer;

    try {
      buffer = new BufferedReader(new FileReader(new File("./assets/shaders/" + filename)));
      String line;
      while ((line = buffer.readLine()) != null) {
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
