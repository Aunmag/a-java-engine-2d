package nightingale.structures;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;
import javax.imageio.ImageIO;

import nightingale.Application;
import nightingale.basics.BaseSize;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

public class Texture extends BaseSize {

    private static HashMap<String, Texture> all = new HashMap<>();
    private int id;
    private Model model;
    private boolean isScaled;

    public static Texture getOrCreate(String name) {
        if (all.containsKey(name)) {
            return all.get(name);
        } else {
            Texture texture = new Texture(loadImage(name));
            all.put(name, texture);
            return texture;
        }
    }

    private static BufferedImage loadImage(String name) {
        String path = "/" + name + ".png";
        BufferedImage bufferedImage;

        try {
            bufferedImage = ImageIO.read(Texture.class.getResourceAsStream(path));
        } catch (Exception e) {
            bufferedImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
            String message = String.format("Can't load image at \"%s\"!", path);
            System.err.println(message);
            e.printStackTrace();
        }

        return bufferedImage;
    }

    private Texture(BufferedImage bufferedImage) {
        super(bufferedImage.getWidth(), bufferedImage.getHeight());

        isScaled = false;

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        int[] pixelsRaw = bufferedImage.getRGB(0, 0, width, height, null, 0, width);
        ByteBuffer pixelsBuffer = BufferUtils.createByteBuffer(width * height * 4);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
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

        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D,
                GL11.GL_TEXTURE_MIN_FILTER,
                GL11.GL_NEAREST_MIPMAP_LINEAR
        );
    }

    public void scaleAsWallpaper() {
        float setWidth;
        float setHeight;

        if (getAspectRatio() < Application.getWindow().getAspectRatio()) {
            setWidth = Application.getWindow().getWidth();
            setHeight = setWidth / getAspectRatio();
        } else {
            setHeight = Application.getWindow().getHeight();
            setWidth = setHeight * getAspectRatio();
        }

        setSize(setWidth, setHeight);
    }

    public void bind() {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public void render() {
        model.render();
    }

    public static void cleanUp() {
        for (Texture texture: all.values()) {
            GL11.glDeleteTextures(texture.id);
        }
    }

    /* Setters */

    protected void setSize(float width, float height) {
        super.setSize(width, height);
        isScaled = true;
        model = Model.createFromSize(this);
    }

    /* Getters */

    public boolean isScaled() {
        return isScaled;
    }

}
