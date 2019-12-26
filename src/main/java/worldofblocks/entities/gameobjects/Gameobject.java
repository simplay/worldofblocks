package worldofblocks.entities.gameobjects;

import worldofblocks.rendering.drawables.RenderItem;

public interface Gameobject {
  void update();
  void render();
  RenderItem getRenderItem();
}
