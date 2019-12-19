package worldofblocks.rendering;

public enum VertexAttributes {
  POSITION(0),
  TEXTURE(1),
  COLOR(2),
  NORMAL(4);

  private final int index;

  VertexAttributes(int index) {
    this.index = index;
  }

  public int getIndex() {
    return this.index;
  }
}
