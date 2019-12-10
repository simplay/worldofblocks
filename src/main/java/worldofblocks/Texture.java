package worldofblocks;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Texture {
  private int id;
  private int width;
  private int height;

  public Texture(String filename) {
    BufferedImage imageBuffer;

    try {
      imageBuffer = ImageIO.read(new File(filename));
      width = imageBuffer.getWidth();
      height = imageBuffer.getHeight();

      // extract rgb data from image buffer. When reading a png image, each pixel consists of 4 bytes
      int[] rawPixels = imageBuffer.getRGB(0, 0, width, height, null, 0, width);

      // initialize an RGBA pixel buffer (i.e. 4 bytes per pixel)
      ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
      for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
          int pixel = rawPixels[i * width + j];

          // process pixels of an png image
          pixels.put((byte)((pixel >> 16) & 0xFF)); // R
          pixels.put((byte)((pixel >> 8) & 0xFF)); // G
          pixels.put((byte)((pixel) & 0xFF)); // B
          pixels.put((byte)((pixel >> 24) & 0xFF)); // alpha
        }
      }

      pixels.flip();

      id = glGenTextures();
      glBindTexture(GL_TEXTURE_2D, id);

      // how to interpolate pixel values: use nearest filter
      glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
      glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

      glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void bind() {
    // https://www.khronos.org/registry/OpenGL-Refpages/es2.0/xhtml/glBindTexture.xml
    glBindTexture(GL_TEXTURE_2D, id);
  }
}
