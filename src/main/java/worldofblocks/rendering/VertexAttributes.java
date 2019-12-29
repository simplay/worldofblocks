package worldofblocks.rendering;

class VertexAttribute {
  private final int index;
  private final int elementCount;

  VertexAttribute(int index, int elementCount) {
    this.index = index;
    this.elementCount = elementCount;
  }

  public int getIndex() {
    return index;
  }

  public int getElementCount() {
    return elementCount;
  }
};

public enum VertexAttributes {

  POSITION(new VertexAttribute(0, 3)),
  TEXTURE(new VertexAttribute(1, 2)),
  COLOR(new VertexAttribute(2, 4)),
  NORMAL(new VertexAttribute(3, 3)),
  FACE(new VertexAttribute(4, 3));

  private final VertexAttribute attribute;

  VertexAttributes(VertexAttribute attribute) {
    this.attribute = attribute;
  }

  public int getIndex() {
    return attribute.getIndex();
  }

  public int getElementCount() {
    return attribute.getElementCount();
  }
}
