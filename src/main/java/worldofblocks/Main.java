package worldofblocks;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import worldofblocks.game.Game;

class Main {
  public static void main(String[] args) {
    System.out.println("System Information:");
    System.out.println("OS: " + System.getProperty("os.name"));
    System.out.println("JAVA " + System.getProperty("java.version"));
    System.out.println("LWJGL " + Version.getVersion());
    System.out.println("GLFW " + GLFW.glfwGetVersionString());

    new Game(1024, 768, false).start();
  }
}