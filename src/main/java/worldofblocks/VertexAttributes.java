package worldofblocks;

public enum VertexAttributes {
  VERTICES(0),
  TEXTURES(1),
  COLORS(2);

  private final int index;

  VertexAttributes(int index) {
    this.index = index;
  }

  public int getIndex() {
    return this.index;
  }
}
