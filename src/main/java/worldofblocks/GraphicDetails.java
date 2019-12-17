package worldofblocks;

import org.lwjgl.opengl.GL30;

public class GraphicDetails {
  private static GraphicDetails instance = null;

  private final int glslVersion;
  private final int openglVersion;
  private final boolean esEnabled;

  private GraphicDetails() {
    // In the following we are relying on glGetString to extract graphic card details:
    // https://www.khronos.org/registry/OpenGL-Refpageso/es2.0/xhtml/glGetString.xml

    // release number of the form
    // OpenGL<space>ES<space><version number><space><vendor-specific information>.
    String glVersion = GL30.glGetString(GL30.GL_VERSION);

    // release number for the shading language of the form
    // OpenGL<space>ES<space>GLSL<space>ES<space><version number><space><vendor-specific information
    String glShadingLanguageVersion = GL30.glGetString(GL30.GL_SHADING_LANGUAGE_VERSION);

    System.out.println("OpenGL Version: " + glVersion);
    System.out.println("GLSL Version: " + glShadingLanguageVersion);

    // Dependence between OpenGL Versions and GLSL Versions:
    // https://github.com/mattdesl/lwjgl-basics/wiki/GLSL-Versions
    this.openglVersion = Integer.parseInt(extractAttribute(glVersion, 0));
    this.glslVersion = Integer.parseInt(extractAttribute(glShadingLanguageVersion, 0));

    this.esEnabled = glslVersion < 200;
  }

  public static GraphicDetails getInstance() {
    if (instance == null) {
      instance = new GraphicDetails();
    }
    return instance;
  }

  private String extractAttribute(String versionString, int index) {
    String[] splits = versionString.split(" ");
    String attribute = splits[index].replace(".", "");
    return attribute;
  }

  public static boolean esEnabled() {
    return getInstance().esEnabled;
  }

  public static int getGLSLVersion() {
    return getInstance().glslVersion;
  }

  public static int getOpenGLVersion() {
    return getInstance().openglVersion;
  }

  public static String usedShader() {
    if (getGLSLVersion() >= 150)  {
      return "glsl_150";
    }

    return "glsl_es_320";
  }
}
