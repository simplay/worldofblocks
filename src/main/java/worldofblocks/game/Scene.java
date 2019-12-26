package worldofblocks.game;

import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector4f;
import worldofblocks.entities.cameras.Camera;
import worldofblocks.entities.cameras.Frustum;
import worldofblocks.entities.gameobjects.Block;
import worldofblocks.entities.gameobjects.Gameobject;
import worldofblocks.entities.gameobjects.Player;
import worldofblocks.entities.gameobjects.Sun;
import worldofblocks.entities.lights.PointLight;
import worldofblocks.gui.Window;
import worldofblocks.rendering.GraphicDetails;
import worldofblocks.rendering.Shader;
import worldofblocks.rendering.Texture;
import worldofblocks.rendering.drawables.Cube;
import worldofblocks.rendering.drawables.Plane;
import worldofblocks.rendering.drawables.RenderItem;
import worldofblocks.rendering.drawables.Sphere;

import java.util.LinkedList;

public class Scene {
  private final float EPS = 0.1f;

  private final Window window;
  private Camera camera;
  private Frustum frustum;

  private final LinkedList<RenderItem> renderItems = new LinkedList<>();
  private final LinkedList<PointLight> pointLights = new LinkedList<>();
  private final LinkedList<Gameobject> gameobjects = new LinkedList<>();

  private Player player;
  private Sun sun;

  public Scene(Window window) {
    this.window = window;

    initCamera();
    initFrustum();
    initEntities();
    initLights();
  }

  private void initEntities() {
    String shaderFilePath = GraphicDetails.usedShader() + "/shader";
    Shader shader = new Shader(shaderFilePath);

    renderItems.add(new RenderItem(new Plane(10), shader));
    renderItems.add(new RenderItem(new Cube(), shader, new Texture("assets/textures/trollface.png")));

    Sphere sunShape = new Sphere(10);
    sunShape.transform(new Matrix4f().identity().translation(0, 1, 0));
    this.sun = new Sun(window.getInputHandler(), new RenderItem(sunShape, shader));

    Cube playerShape = new Cube();
    playerShape.transform(new Matrix4f().identity().translation(0, 0, 4).scale(0.01f));
    this.player = new Player(window.getInputHandler(), new RenderItem(playerShape, shader));
    camera.attachPlayer(player);

    gameobjects.add(player);
    gameobjects.add(sun);

    int items = 10;
    float step = 0.5f;
    float center = -items * step * 0.5f;
    float gap = 0.02f;
    for (int k = 0; k < items; k++) {
      for (int l = 0; l < items; l++) {
        gameobjects.add(new Block(new Vector3f(center + k * (step + gap), center + l * (step + gap), 3f)));
      }
    }
  }

  private void initLights() {
    pointLights.add(new PointLight(new Vector3f(0f, 1.0f, 1.0f), new Vector4f(1, 0, 0, 0)));
  }

  public void initFrustum() {
    Vector2i res = window.getResolution();
    float aspectRatio = res.x / res.y;
    this.frustum = new Frustum(aspectRatio, EPS, 500, 60.0f);
  }

  private void initCamera() {
    Vector3f eye = new Vector3f(0, 0, 4f);
    Vector3f lookAtPoint = new Vector3f(0.0f, 0.2f, 0.0f);
    Vector3f up = new Vector3f(0, 0, 1);
    this.camera = new Camera(window.getCursorHandler(), eye, lookAtPoint, up);
  }

  public LinkedList<RenderItem> getRenderItems() {
    return renderItems;
  }

  public LinkedList<Gameobject> getGameobjects() {
    return gameobjects;
  }

  public LinkedList<PointLight> getPointLights() {
    return pointLights;
  }

  public Sun getSun() {
    return sun;
  }

  public Camera getCamera() {
    return camera;
  }

  public Frustum getFrustum() {
    return frustum;
  }
}
