package engine.utilities;

import engine.Application;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class UtilsGraphics {

    public static void drawPrepare() {
        GL20.glUseProgram(0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    public static void drawLine(Vector2f a, Vector2f b, boolean isOnWorld) {
        if (isOnWorld) {
            a = Application.getCamera().calculateViewPosition(a);
            b = Application.getCamera().calculateViewPosition(b);
        }

        drawLine(a, b);
    }

    public static void drawLine(Vector2f a, Vector2f b) {
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(a.x(), a.y());
        GL11.glVertex2f(b.x(), b.y());
        GL11.glEnd();
    }

    public static void drawQuad(
            Vector2f a,
            Vector2f b,
            Vector2f c,
            Vector2f d,
            boolean isFilling,
            boolean isOnWorld
    ) {
        if (isOnWorld) {
            a = Application.getCamera().calculateViewPosition(a);
            b = Application.getCamera().calculateViewPosition(b);
            c = Application.getCamera().calculateViewPosition(c);
            d = Application.getCamera().calculateViewPosition(d);
        }

        if (isFilling) {
            GL11.glBegin(GL11.GL_TRIANGLES);
            GL11.glVertex2f(a.x(), a.y()); // 1. a - top left
            GL11.glVertex2f(d.x(), d.y()); // 1. d - down left
            GL11.glVertex2f(b.x(), b.y()); // 1. b - top right
            GL11.glVertex2f(b.x(), b.y()); // 2. b - top right
            GL11.glVertex2f(d.x(), d.y()); // 2. d - down left
            GL11.glVertex2f(c.x(), c.y()); // 2. c - down right
            GL11.glEnd();
        } else {
            drawLine(a, b);
            drawLine(b, c);
            drawLine(c, d);
            drawLine(d, a);
        }
    }

    public static void drawFinish() {
        Application.getShader().bind();
    }

}
