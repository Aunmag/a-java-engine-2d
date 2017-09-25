package aunmag.nightingale.utilities;

import aunmag.nightingale.Application;
import aunmag.nightingale.basics.BaseQuad;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.awt.Color;

public class UtilsGraphics {

    public static void drawPrepare() {
        GL20.glUseProgram(0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    public static void drawLine(
            float x1,
            float y1,
            float x2,
            float y2,
            boolean isOnWorld
    ) {
        Vector2f a;
        Vector2f b;

        if (isOnWorld) {
            a = Application.getCamera().calculateViewPosition(x1, y1);
            b = Application.getCamera().calculateViewPosition(x2, y2);
        } else {
            a = Application.getWindow().calculateViewPosition(x1, y1);
            b = Application.getWindow().calculateViewPosition(x2, y2);
        }

        drawLine(a.x(), a.y(), b.x(), b.y());
    }

    private static void drawLine(float x1, float y1, float x2, float y2) {
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
    }

    public static void drawQuad(BaseQuad quad, boolean isFilled, boolean isOnWorld) {
        drawQuad(
                quad.getX(),
                quad.getY(),
                quad.getWidth(),
                quad.getHeight(),
                isFilled,
                isOnWorld
        );
    }

    public static void drawQuad(
            float x,
            float y,
            float width,
            float height,
            boolean isFilled,
            boolean isOnWorld
    ) {
        float x1;
        float y1;
        float x2;
        float y2;

        {
            Vector2f a;
            Vector2f b;

            if (isOnWorld) {
                a = Application.getCamera().calculateViewPosition(x, y);
                b = Application.getCamera().calculateViewPosition(x + width, y + height);
            } else {
                a = Application.getWindow().calculateViewPosition(x, y);
                b = Application.getWindow().calculateViewPosition(x + width, y + height);
            }

            x1 = a.x();
            y1 = a.y();
            x2 = b.x();
            y2 = b.y();
        }

        if (isFilled) {
            GL11.glBegin(GL11.GL_TRIANGLES);
            GL11.glVertex2f(x1, y1); // 1. a - top left
            GL11.glVertex2f(x1, y2); // 1. d - down left
            GL11.glVertex2f(x2, y1); // 1. b - top right
            GL11.glVertex2f(x2, y1); // 2. b - top right
            GL11.glVertex2f(x1, y2); // 2. d - down left
            GL11.glVertex2f(x2, y2); // 2. c - down right
            GL11.glEnd();
        } else {
            drawLine(x1, y1, x2, y1);
            drawLine(x2, y1, x2, y2);
            drawLine(x2, y2, x1, y2);
            drawLine(x1, y2, x1, y1);
        }
    }

    public static void drawCircle(
            float x,
            float y,
            float radius,
            boolean isFilled,
            boolean isOnWorld
    ) {
        // TODO: Learn difference between GL_TRIANGLE_FAN and GL_POLYGON
        GL11.glBegin(isFilled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINE_LOOP);

        final float accuracy = 0.4f; // TODO: Use in settings
        for (float radians = 0; radians <= UtilsMath.PIx2; radians += accuracy) {
            float fragmentX = (float) (x + radius * Math.cos(radians));
            float fragmentY = (float) (y + radius * Math.sin(radians));

            if (isOnWorld) {
                Vector2f viewPosition = Application.getCamera().calculateViewPosition(
                        fragmentX,
                        fragmentY
                );
                fragmentX = viewPosition.x();
                fragmentY = viewPosition.y();
            }

            GL11.glVertex2f(fragmentX, fragmentY);
        }
        GL11.glEnd();
    }

    public static void drawFinish() {
        Application.getShader().bind();
    }

    public static void setDrawColor(Color color) {
        // TODO: Optimize:
        GL11.glColor4f(
                color.getRed() / 255f,
                color.getGreen() / 255f,
                color.getBlue() / 255f,
                color.getAlpha() / 255f
        );
    }

}