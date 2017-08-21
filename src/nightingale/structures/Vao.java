package nightingale.structures;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Vao {

    private static List<Integer> vaos = new ArrayList<>();
    private static List<Integer> vbos = new ArrayList<>();

    public final int id;
    public final int vertexCount;

    public Vao(float[] vertices, float[] textureCoordinates) {
        id = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(id);
        vaos.add(id);

        storeDataInAttributeList(0, 2, vertices);
        storeDataInAttributeList(1, 2, textureCoordinates);
        unbind();

        vertexCount = vertices.length / 2;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        vbos.add(vboId);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private void unbind() {
        GL30.glBindVertexArray(0);
    }

    public static void cleanUp() {
        for (int vao: vaos) {
            GL30.glDeleteVertexArrays(vao);
        }

        for (int vbo: vbos) {
            GL15.glDeleteBuffers(vbo);
        }
    }

}
