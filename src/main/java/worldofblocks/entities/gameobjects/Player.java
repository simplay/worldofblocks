package worldofblocks.entities.gameobjects;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import worldofblocks.gui.handlers.InputHandler;
import worldofblocks.rendering.drawables.RenderItem;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;

public class Player {
    private final InputHandler inputHandler;
    private final RenderItem renderItem;
    private final Vector3f position;
    private Vector3f viewingDireciton;

    public Player(InputHandler inputHandler, RenderItem renderItem) {
        this.inputHandler = inputHandler;
        this.position = new Vector3f();
        this.renderItem = renderItem;
        this.viewingDireciton = new Vector3f(0.0f, 0.0f, 1.0f);
    }

    public void updatePosition(Vector3f shift) {
        renderItem.moveShape(new Vector3f(-shift.x, -shift.y, -shift.z));
        position.add(shift);
    }

    // TODO: don't call this from the camera: rework this when refactoring the camera
    public void updateViewingDirection(float yaw) {
        double angle = 0.5 * yaw;
        double a = viewingDireciton.x;
        double b = viewingDireciton.z;

        this.viewingDireciton = new Vector3f(
                (float)(Math.cos(angle) * a - Math.sin(angle) * b),
                0.0f,
                (float) (Math.sin(angle) * a + Math.cos(angle) * b)
        );
    }

    public Matrix4f getTransform() {
        return new Matrix4f().translate(position);
    }

    public void render() {
        renderItem.render();
    }

    float scale = 0.01f;

    public void update() {
        // TODO: can get inverted: fix me
        if (inputHandler.isKeyDown(GLFW_KEY_A)) {
            updatePosition(new Vector3f(0.01f, 0, 0));
        }

        // TODO: can get inverted: fix me
        if (inputHandler.isKeyDown(GLFW_KEY_D)) {
            updatePosition(new Vector3f(-0.01f, 0, 0));
        }

        if (inputHandler.isKeyDown(GLFW_KEY_W)) {
            updatePosition(new Vector3f(viewingDireciton.x, 0, viewingDireciton.z).mul(scale));
        }

        if (inputHandler.isKeyDown(GLFW_KEY_S)) {
            updatePosition(new Vector3f(-viewingDireciton.x, 0, -viewingDireciton.z).mul(scale));
        }

        if (inputHandler.isKeyDown(GLFW_KEY_SPACE)) {
            updatePosition(new Vector3f(0, -0.01f, 0));
        }

        if (inputHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
            updatePosition(new Vector3f(0, 0.01f, 0));
        }
    }
}
