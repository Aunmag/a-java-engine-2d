package nightingale.shaders;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

public class ShaderSprite {

    private static List<ShaderSprite> all = new ArrayList<>();

    private final int programId;
    private final int shaderVertexId;
    private final int shaderFragmentId;
    private final int uniformLocationSampler;
    private final int uniformLocationProjection;
    private final int uniformLocationColour;

    private static String readFile(String filename) {
        StringBuilder string = new StringBuilder();

        try {
            InputStream inputStream = ShaderSprite.class.getResourceAsStream(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while((line = bufferedReader.readLine()) != null) {
                string.append(line);
                string.append("\n");
            }

            bufferedReader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return string.toString();
    }

    public ShaderSprite(String filename) {
        programId = GL20.glCreateProgram();

        shaderVertexId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(shaderVertexId, readFile(filename + ".vert"));
        GL20.glCompileShader(shaderVertexId);
        if (GL20.glGetShaderi(shaderVertexId, GL20.GL_COMPILE_STATUS) != 1) {
            System.err.println(GL20.glGetShaderInfoLog(shaderVertexId));
            System.exit(1);
        }

        shaderFragmentId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(shaderFragmentId, readFile(filename + ".frag"));
        GL20.glCompileShader(shaderFragmentId);
        if (GL20.glGetShaderi(shaderFragmentId, GL20.GL_COMPILE_STATUS) != 1) {
            System.err.println(GL20.glGetShaderInfoLog(shaderFragmentId));
            System.exit(1);
        }

        GL20.glAttachShader(programId, shaderVertexId);
        GL20.glAttachShader(programId, shaderFragmentId);

        GL20.glBindAttribLocation(programId, 0, "vertices");
        GL20.glBindAttribLocation(programId, 1, "textures");

        GL20.glLinkProgram(programId);
        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) != 1) {
            System.err.println(GL20.glGetProgramInfoLog(programId));
            System.exit(1);
        }

        GL20.glValidateProgram(programId);
        if (GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) != 1) {
            System.err.println(GL20.glGetProgramInfoLog(programId));
            System.exit(1);
        }

        uniformLocationSampler = getUniformLocation("sampler");
        uniformLocationProjection = getUniformLocation("projection");
        uniformLocationColour = getUniformLocation("colour");

        all.add(this);
    }

    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programId, uniformName);
    }

    public void bind() {
        GL20.glUseProgram(programId);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public static void cleanUp() {
        for (ShaderSprite shader: all) {
            shader.unbind();
            GL20.glDetachShader(shader.programId, shader.shaderVertexId);
            GL20.glDetachShader(shader.programId, shader.shaderFragmentId);
            GL20.glDeleteShader(shader.shaderVertexId);
            GL20.glDeleteShader(shader.shaderFragmentId);
            GL20.glDeleteProgram(shader.programId);
        }
    }

    /* Setters */

    public void setUniformSampler(int sampler) {
        GL20.glUniform1i(uniformLocationSampler, sampler);
    }

    public void setUniformProjection(Matrix4f projection) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        projection.get(buffer);
        GL20.glUniformMatrix4fv(uniformLocationProjection, false, buffer);
    }

    public void setUniformColour(Vector4f colour) {
        setUniformColour(colour.x, colour.y, colour.z, colour.w);
    }

    public void setUniformColourDefault() {
        setUniformColour(1, 1, 1, 1);
    }

    public void setUniformColour(float red, float green, float blue, float alpha) {
        GL20.glUniform4f(uniformLocationColour, red, green, blue, alpha);
    }

}
