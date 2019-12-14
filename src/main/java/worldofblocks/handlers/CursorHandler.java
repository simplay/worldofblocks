package worldofblocks.handlers;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorHandler extends GLFWCursorPosCallback {
  private int windowWidth;
  private int windowHeight;

  private float mousePosX;
  private float mousePosY;

  private float prevMousePosX;
  private float prevMousePosY;

  public CursorHandler(int windowWidth, int windowHeight) {
    this.windowWidth = windowWidth;
    this.windowHeight = windowHeight;
  }

  @Override
  public void invoke(long window, double x, double y) {
    this.mousePosX = (float) (x / windowWidth) - 0.5f;
    this.mousePosY = (float) (y / windowHeight) - 0.5f;
  }

  public float getMousePosX() {
    return mousePosX;
  }

  public float getMousePosY() {
    return mousePosY;
  }

  public float getDx() {
    float dx = mousePosX - prevMousePosX;
    this.prevMousePosX = mousePosX;

    return dx;
  }

  public float getDy() {
    float dy = mousePosY - prevMousePosY;
    this.prevMousePosY = mousePosY;

    return dy;
  }
}
