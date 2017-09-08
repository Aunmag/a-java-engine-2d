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

    private static List<Vao> all = new ArrayList<>();

    private final int id;
    private final int idVertices;
    private final int idTextureCoordinates;
    public final int vertexCount;

    public Vao(float[] vertices, float[] textureCoordinates) {
        id = GL30.glGenVertexArrays();
        idVertices = GL15.glGenBuffers();
        idTextureCoordinates = GL15.glGenBuffers();

        bind();
        storeDataInAttributeList(idVertices, 0, vertices);
        storeDataInAttributeList(idTextureCoordinates, 1, textureCoordinates);
        unbind();

        vertexCount = vertices.length / 2;
    }

    private void storeDataInAttributeList(int id, int attributeNumber, float[] data) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, 2, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void bind() {
        GL30.glBindVertexArray(id);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public void delete() {
        GL30.glDeleteVertexArrays(id);
        GL15.glDeleteBuffers(idVertices);
        GL15.glDeleteBuffers(idTextureCoordinates);
        all.remove(this);
    }

    public static void cleanUp() {
        for (Vao vao: all) {
            vao.delete();
        }
    }

}
