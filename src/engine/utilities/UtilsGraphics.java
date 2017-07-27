package engine.utilities;

import engine.Application;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class UtilsGraphics {

    public static void drawLinePrepare() {
        GL20.glUseProgram(0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    public static void drawLine(Vector3f a, Vector3f b, boolean isOnWorld) {
        if (isOnWorld) {
            a = Application.getCamera().calculateViewPosition(a);
            b = Application.getCamera().calculateViewPosition(b);
        }

        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(a.x(), a.y());
        GL11.glVertex2f(b.x(), b.y());
        GL11.glEnd();
    }

    public static void drawLineFinish() {
        Application.getShader().bind();
    }

}
