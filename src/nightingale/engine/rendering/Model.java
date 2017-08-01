package nightingale.engine.rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.BufferUtils;

public class Model {

    private int drawQuantity;
    private int verticesId;
    private int texturesId;
    private int indicesId;

    // TODO: Implement getOrCreate
    public static Model create(float width, float height) {
        float centerX = width / 2f;
        float centerY = height / 2f;

        float[] vertices = new float[] {
                -centerX, +centerY, 0,
                -centerX, -centerY, 0,
                +centerX, -centerY, 0,
                +centerX, +centerY, 0,
        };

        float[] texture = new float[] {
                0, 0,
                0, 1,
                1, 1,
                1, 0,
        };

        int[] indices = new int[] {
                0, 1, 3,
                3, 1, 2
        };

        return new Model(vertices, texture, indices);
    }

    private static FloatBuffer createBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private static IntBuffer createBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public Model(float[] vertices, float[] textureCoordinates, int[] indices) {
        drawQuantity = indices.length;

        verticesId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesId);
        GL15.glBufferData(
                GL15.GL_ARRAY_BUFFER,
                createBuffer(vertices),
                GL15.GL_STATIC_DRAW
        );

        texturesId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texturesId);
        GL15.glBufferData(
                GL15.GL_ARRAY_BUFFER,
                createBuffer(textureCoordinates),
                GL15.GL_STATIC_DRAW
        );

        indicesId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesId);
        GL15.glBufferData(
                GL15.GL_ELEMENT_ARRAY_BUFFER,
                createBuffer(indices),
                GL15.GL_STATIC_DRAW
        );

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    protected void finalize() throws Throwable {
        GL15.glDeleteBuffers(verticesId);
        GL15.glDeleteBuffers(texturesId);
        GL15.glDeleteBuffers(indicesId);
        super.finalize();
    }

    public void render() {
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesId);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texturesId);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesId);
        GL11.glDrawElements(GL11.GL_TRIANGLES, drawQuantity, GL11.GL_UNSIGNED_INT, 0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
    }

}
