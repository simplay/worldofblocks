package worldofblocks;

import org.lwjgl.opengl.GL30;

public class GraphicDetails {
  private static GraphicDetails instance = null;

  private final int shaderVersion;
  private final int openglVersion;
  private final boolean esEnabled;

  // https://github.com/mattdesl/lwjgl-basics/wiki/GLSL-Versions
  // https://www.khronos.org/registry/OpenGL-Refpages/es2.0/xhtml/glGetString.xml
  private GraphicDetails() {
    String glVersion = GL30.glGetString(GL30.GL_VERSION);
    String glShadingLanguageVersion = GL30.glGetString(GL30.GL_SHADING_LANGUAGE_VERSION);

    this.esEnabled = glVersion.contains("ES");

    String normalizedOpenglVersion = glVersion.split(" ")[0].replace(".", "");
    this.openglVersion = Integer.parseInt(normalizedOpenglVersion);

    String normalizedShaderVersion = glShadingLanguageVersion.split(" ")[0].split(" ")[0].replace(".", "");
    this.shaderVersion = Integer.parseInt(normalizedShaderVersion);
  }

  public static GraphicDetails getInstance() {
    if (instance == null) {
      instance = new GraphicDetails();
    }
    return instance;
  }

  public static boolean esEnabled() {
    return getInstance().esEnabled;
  }

  public static int getShaderVersion() {
    return getInstance().shaderVersion;
  }

  public static int getOpenGLVersion() {
    return getInstance().openglVersion;
  }
}
