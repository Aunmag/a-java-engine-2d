package engine.rendering;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture {

    private static HashMap<String, Texture> all = new HashMap<>();
    private int id;
    private Model model;

    public static Texture getOrCreate(String name) {
        if (all.containsKey(name)) {
            return all.get(name);
        } else {
            Texture texture = new Texture(name + ".png");
            all.put(name, texture);
            return texture;
        }
    }

    public Texture(String filename) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File("./res/images/" + filename));
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        model = Model.create(width, height);

        int[] pixelsRaw = bufferedImage.getRGB(0, 0, width, height, null, 0, width);
        ByteBuffer pixelsBuffer = BufferUtils.createByteBuffer(width * height * 4);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = pixelsRaw[i * width + j];
                pixelsBuffer.put((byte) ((pixel >> 16) & 0xFF)); // Red
                pixelsBuffer.put((byte) ((pixel >> 8) & 0xFF)); // Green
                pixelsBuffer.put((byte) (pixel & 0xFF)); // Blue
                pixelsBuffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha
            }
        }

        pixelsBuffer.flip();

        id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glTexParameterf(
                GL11.GL_TEXTURE_2D,
                GL11.GL_TEXTURE_MIN_FILTER,
                GL11.GL_NEAREST
        );
        GL11.glTexParameterf(
                GL11.GL_TEXTURE_2D,
                GL11.GL_TEXTURE_MAG_FILTER,
                GL11.GL_NEAREST
        );
        GL11.glTexImage2D(
                GL11.GL_TEXTURE_2D,
                0,
                GL11.GL_RGBA,
                width,
                height,
                0,
                GL11.GL_RGBA,
                GL11.GL_UNSIGNED_BYTE,
                pixelsBuffer
        );
    }

    public void bind(int sampler) {
        if (0 <= sampler && sampler <= 31) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0 + sampler);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        }
    }

    public void render() {
        model.render();
    }

    protected void finalize() throws Throwable {
        GL11.glDeleteTextures(id);
        super.finalize();
    }

}
