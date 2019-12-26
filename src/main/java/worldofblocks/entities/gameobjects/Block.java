package worldofblocks.entities.gameobjects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import worldofblocks.rendering.GraphicDetails;
import worldofblocks.rendering.Shader;
import worldofblocks.rendering.drawables.Cube;
import worldofblocks.rendering.drawables.RenderItem;
import worldofblocks.rendering.drawables.Shape;

public class Block implements Gameobject {
  private final Matrix4f transform;
  private final RenderItem renderItem;

  public Block(Vector3f center) {
    this.transform = new Matrix4f().identity().translate(center).scale(0.25f);
    Shape shape = new Cube();
    shape.transform(transform);

    String shaderFilePath = GraphicDetails.usedShader() + "/shader";
    Shader shader = new Shader(shaderFilePath);
    this.renderItem = new RenderItem(shape, shader);
  }

  @Override
  public void update() {
  }

  @Override
  public void render() {
    renderItem.render();
  }

  @Override
  public RenderItem getRenderItem() {
    return renderItem;
  }
}
