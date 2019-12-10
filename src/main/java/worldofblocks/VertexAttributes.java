package worldofblocks;

public enum VertexAttributes {
  VERTICES(0),
  TEXTURES(1);

  private final int index;

  private VertexAttributes(int index) {
    this.index = index;
  }

  public int getIndex() {
    return this.index;
  }
}
