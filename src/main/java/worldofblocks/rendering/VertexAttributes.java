package worldofblocks.rendering;

public enum VertexAttributes {
  VERTICES(0),
  TEXTURES(1),
  COLORS(2),
  NORMALS(4);

  private final int index;

  VertexAttributes(int index) {
    this.index = index;
  }

  public int getIndex() {
    return this.index;
  }
}
