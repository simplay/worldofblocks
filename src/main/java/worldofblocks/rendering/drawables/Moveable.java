package worldofblocks.rendering.drawables;

import org.joml.Vector3f;

public interface Moveable {
  void updatePosition(Vector3f shift);
}
