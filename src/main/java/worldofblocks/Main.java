package worldofblocks;

import org.lwjgl.Version;

class Main {
  public static void main(String[] args) {
    System.out.println("Using LWJGL " + Version.getVersion());
    new Game(640, 480).run();
  }
}