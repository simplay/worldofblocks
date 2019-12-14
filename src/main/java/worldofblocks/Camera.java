package worldofblocks;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * the world-to-camera transform .
 */

// TODO: implement camera rotation
public class Camera {
  private Matrix4f cameraMatrix;
  private Matrix4f transformation = new Matrix4f().identity();
  private Matrix4f invCameraMatrix;
  private Vector3f projectionCenterPoint;
  private Vector3f lookAtPoint;
  private Vector3f upVector;

  /**
   * @param projectionCenterPoint
   * @param lookAtPoint
   * @param upVector
   */
  public Camera(Vector3f projectionCenterPoint, Vector3f lookAtPoint, Vector3f upVector) {
    this.projectionCenterPoint = projectionCenterPoint;
    this.lookAtPoint = lookAtPoint;
    this.upVector = upVector;

    updateCameraMatrix();
  }

  // notice that the camera is attached to a player (i.e. its transformation)
  public Matrix4f getTransformation(float pitch, float yaw) {
    float angle  = (pitch % 360);
    float angle2 = (yaw % 360);
    Matrix4f vertRot = new Matrix4f().rotate(angle, new Vector3f(1, 0, 0));
    Matrix4f horiRot = new Matrix4f().rotate(angle2, new Vector3f(0, 1, 0));

    Matrix4f tmp = new Matrix4f();

    vertRot.mul(horiRot, tmp);
    tmp.mul(cameraMatrix, tmp);
    tmp.mul(transformation, tmp);

    return tmp;
  }

  public void updateTransformation(Matrix4f t) {
    this.transformation = t;

  }

  /**
   * e := ProjectionCenterPoint
   * d := LookAtPointPoint
   * up := UpVector
   * <p>
   * zc := (e-d) / ||(e-d)||
   * xc := (up x zc) / ||(up x zc)||
   * yc := (zc x xc)
   * <p>
   * camera to world transformation-matrix
   * C :=
   * [xc yc	zc e]
   * [0	 0	0  1]
   */
  protected void updateCameraMatrix() {
    Vector3f e = new Vector3f(projectionCenterPoint);
    Vector3f d = new Vector3f(lookAtPoint);
    Vector3f up = new Vector3f(upVector);
    Matrix4f tmpCamera = new Matrix4f();

    // z-axis:
    Vector3f tmpZC = new Vector3f(0.0f, 0.0f, 0.0f);
    e.sub(d, tmpZC);
    tmpZC.normalize();
    Vector4f ZC = new Vector4f(tmpZC, 0);

    // x-axis:
    Vector3f tmpXC = new Vector3f(0.0f, 0.0f, 0.0f);
    up.cross(tmpZC, tmpXC);
    tmpXC.normalize();
    Vector4f XC = new Vector4f(tmpXC, 0);

    // y-axis:
    Vector3f tmpYC = new Vector3f(0.0f, 0.0f, 0.0f);
    tmpZC.cross(tmpXC, tmpYC);
    tmpYC.normalize();
    Vector4f YC = new Vector4f(tmpYC, 0);

    tmpCamera.setColumn(0, XC);
    tmpCamera.setColumn(1, YC);
    tmpCamera.setColumn(2, ZC);
    tmpCamera.setColumn(3, new Vector4f(e.x, e.y, e.z, 1));

    this.invCameraMatrix = tmpCamera;

    Matrix4f invC = new Matrix4f(tmpCamera);
    invC.invert();

    this.cameraMatrix = invC;
  }
}
