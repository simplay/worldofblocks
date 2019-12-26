package worldofblocks.rendering.drawables;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;

// See https://en.wikipedia.org/wiki/Spherical_coordinate_system
public class Sphere extends Shape {
  public Sphere(int resolution) {
    int v_resolution = resolution;
    int u_resolution = 2 * resolution;
    int n_vertices = (v_resolution) * u_resolution;
    int n_triangles = 2 * (v_resolution - 1) * (u_resolution - 1);


    Vector4f[] vertices = new Vector4f[n_vertices];
    Vector3i[] indices = new Vector3i[n_triangles];
    Vector3f[] normals = new Vector3f[n_vertices];
    Vector2f[] textureCoordinates = new Vector2f[n_vertices];
    Vector4f[] colors = new Vector4f[n_vertices];

    int vIdx = 0;
    int cIdx = 0;
    int nIdx = 0;
    int tIdx = 0;
    for (int iv = 0; iv < v_resolution; iv++) {
      for (int iu = 0; iu < u_resolution; iu++) {
        float u = (float) iu / (float) (u_resolution - 1);
        float v = (float) iv / (float) (v_resolution - 1);

        double theta = v * Math.PI;
        double phi = u * 2.0f * Math.PI;

        // Notice that the openGL coordinate system, y points upwards:
        // See https://i.stack.imgur.com/vg1P7.png
        float x = (float) (Math.cos(phi) * Math.sin(theta));
        float y = (float) Math.cos(theta);
        float z = (float) (Math.sin(phi) * Math.sin(theta));

        vertices[vIdx++] = new Vector4f(x, y, z, 1.0f);
        colors[cIdx++] = new Vector4f(x, y, z, 0.0f);
        normals[nIdx++] = new Vector3f(x, y, z);

        textureCoordinates[tIdx++] = new Vector2f(1.0f - u, 1.0f - v);
      }
    }

    int idx = 0;
    for (int v = 0; v < v_resolution - 1; v++) {
      for (int u = 0; u < u_resolution - 1; u++) {
        int i0 = u + v * u_resolution;
        int i1 = (u + 1) + v * u_resolution;
        int i2 = (u + 1) + (v + 1) * u_resolution;
        int i3 = u + (v + 1) * u_resolution;

        indices[idx++] = new Vector3i(i0, i1, i2);
        indices[idx++] = new Vector3i(i0, i2, i3);
      }
    }

    this.vertices = vertices;
    this.indices = indices;
    this.normals = normals;
    this.textureCoordinates = textureCoordinates;
    this.colors = colors;
  }
}
