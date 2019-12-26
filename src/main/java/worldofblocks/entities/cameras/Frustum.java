package worldofblocks.entities.cameras;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Frustum {
  private Matrix4f projectionMatrix;
  private final float aspectRatio;
  private final float near;
  private final float far;
  private final float verticalFieldView;
  private final Vector3f[] planeNormals = new Vector3f[6];

  /**
   * @return the 4x4 projection matrix
   */
  public Matrix4f getTransformation() {
    return projectionMatrix;
  }

  /**
   * set the near and far plane, aspect ratio and the vertical field of view
   * and then constructs by those parameters a new projection matrix for our frustum.
   *
   * @param aspectRatio
   * @param near
   * @param far
   * @param verticalFieldView
   */
  public Frustum(float aspectRatio, float near, float far, float verticalFieldView) {
    float ratio = (verticalFieldView / 180.0f);
    float fov = (float) (Math.PI * ratio);
    this.aspectRatio = aspectRatio;
    this.near = near;
    this.far = far;
    this.verticalFieldView = fov;
    updateProjectionMatrix();
  }

  public Vector3f[] calculateFrustumPoints(Vector3f up, Vector3f look, Vector3f cop) {
    float a, b, c;

    // near plane normal

    a = projectionMatrix.m03() + projectionMatrix.m02();
    b = projectionMatrix.m13() + projectionMatrix.m12();
    c = projectionMatrix.m23() + projectionMatrix.m22();

    //Vector4f v4 = new Vector4f(a,b,c,d);
    Vector3f v3 = new Vector3f(a, b, c);
    v3.normalize();
    planeNormals[0] = v3;

    // far plane normal
    a = projectionMatrix.m03() - projectionMatrix.m02();
    b = projectionMatrix.m13() - projectionMatrix.m12();
    c = projectionMatrix.m23() - projectionMatrix.m22();

    v3 = new Vector3f(a, b, c);
    v3.normalize();
    planeNormals[1] = v3;

    // left plane normal
    a = projectionMatrix.m03() + projectionMatrix.m00();
    b = projectionMatrix.m13() + projectionMatrix.m10();
    c = projectionMatrix.m23() + projectionMatrix.m20();

    v3 = new Vector3f(a, b, c);
    v3.normalize();
    planeNormals[2] = v3;

    // right plane normal
    a = projectionMatrix.m03() - projectionMatrix.m00();
    b = projectionMatrix.m13() - projectionMatrix.m10();
    c = projectionMatrix.m23() - projectionMatrix.m20();

    v3 = new Vector3f(a, b, c);
    v3.normalize();
    planeNormals[3] = v3;

    // top plane normal
    a = projectionMatrix.m03() - projectionMatrix.m01();
    b = projectionMatrix.m13() - projectionMatrix.m11();
    c = projectionMatrix.m23() - projectionMatrix.m21();

    v3 = new Vector3f(a, b, c);
    v3.normalize();
    planeNormals[4] = v3;

    // bottom plane normal
    a = projectionMatrix.m03() + projectionMatrix.m01();
    b = projectionMatrix.m13() + projectionMatrix.m11();
    c = projectionMatrix.m23() + projectionMatrix.m21();

    v3 = new Vector3f(a, b, c);
    v3.normalize();
    planeNormals[5] = v3;

    return planeNormals;
  }

  private void updateProjectionMatrix() {
    float tanFOV = (float) Math.tan(this.verticalFieldView / 2);
    float p11 = (1.0f / tanFOV);
    float p00 = (1.0f / (tanFOV * this.aspectRatio));
    float deltaNF = (this.near - this.far);
    float p22 = (this.near + this.far) / deltaNF;
    float p23 = (2.0f * this.near * this.far) / deltaNF;

    Vector4f column0 = new Vector4f(p00, 0, 0, 0);
    Vector4f column1 = new Vector4f(0, p11, 0, 0);
    Vector4f column2 = new Vector4f(0, 0, p22, -1.0f);
    Vector4f column3 = new Vector4f(0, 0, p23, 0);

    Matrix4f newProjectionMatrix = new Matrix4f();
    newProjectionMatrix.setColumn(0, column0);
    newProjectionMatrix.setColumn(1, column1);
    newProjectionMatrix.setColumn(2, column2);
    newProjectionMatrix.setColumn(3, column3);

    this.projectionMatrix = newProjectionMatrix;
  }
}

