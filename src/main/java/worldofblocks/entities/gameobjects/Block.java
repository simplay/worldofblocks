package worldofblocks.entities.gameobjects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import worldofblocks.rendering.GraphicDetails;
import worldofblocks.rendering.Shader;
import worldofblocks.rendering.drawables.Cube;
import worldofblocks.rendering.drawables.Instance;
import worldofblocks.rendering.drawables.RenderItem;
import worldofblocks.rendering.drawables.Shape;

public class Block implements Gameobject {
  private final RenderItem renderItem;

  public Block(Vector3f center, Cube cube, Shader shader) {
    Instance instance = new Instance(cube);
    instance.transform(new Matrix4f().identity().translate(center).scale(0.25f));
    this.renderItem = new RenderItem(instance, shader);
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
