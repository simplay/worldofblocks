package worldofblocks;

public class FpsCounter {
  private double time;
  private double fps = 0;

  public FpsCounter() {
    this.time = System.nanoTime();
  }

  public void update() {
    double now = System.nanoTime();
    double delta = now - time;

    this.fps = 1000000000L / delta;
    this.time = now;
  }

  public double getFps() {
    return fps;
  }
}
