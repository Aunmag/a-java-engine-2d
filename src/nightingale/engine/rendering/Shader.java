package nightingale.engine.rendering;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

public class Shader {

    private int program;
    private int shaderVertex;
    private int shaderFragment;

    private static String readFile(String filename) {
        StringBuilder string = new StringBuilder();

        try {
            InputStream inputStream = Shader.class.getResourceAsStream(filename);
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

    public Shader(String filename) {
        program = GL20.glCreateProgram();

        shaderVertex = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(shaderVertex, readFile(filename + ".vert"));
        GL20.glCompileShader(shaderVertex);
        if (GL20.glGetShaderi(shaderVertex, GL20.GL_COMPILE_STATUS) != 1) {
            System.err.println(GL20.glGetShaderInfoLog(shaderVertex));
            System.exit(1);
        }

        shaderFragment = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(shaderFragment, readFile(filename + ".frag"));
        GL20.glCompileShader(shaderFragment);
        if (GL20.glGetShaderi(shaderFragment, GL20.GL_COMPILE_STATUS) != 1) {
            System.err.println(GL20.glGetShaderInfoLog(shaderFragment));
            System.exit(1);
        }

        GL20.glAttachShader(program, shaderVertex);
        GL20.glAttachShader(program, shaderFragment);

        GL20.glBindAttribLocation(program, 0, "vertices");
        GL20.glBindAttribLocation(program, 1, "textures");

        GL20.glLinkProgram(program);
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) != 1) {
            System.err.println(GL20.glGetProgramInfoLog(program));
            System.exit(1);
        }

        GL20.glValidateProgram(program);
        if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) != 1) {
            System.err.println(GL20.glGetProgramInfoLog(program));
            System.exit(1);
        }
    }

    public void bind() {
        GL20.glUseProgram(program);
    }

    protected void finalize() throws Throwable {
        GL20.glDetachShader(program, shaderVertex);
        GL20.glDetachShader(program, shaderFragment);
        GL20.glDeleteShader(shaderVertex);
        GL20.glDeleteShader(shaderFragment);
        GL20.glDeleteProgram(program);
        super.finalize();
    }

    /* Setters */

    public void setUniform(String name, int value) {
        int location = GL20.glGetUniformLocation(program, name);
        if (location != -1) {
            GL20.glUniform1i(location, value);
        }
    }

    public void setUniform(String name, Matrix4f value) {
        int location = GL20.glGetUniformLocation(program, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        value.get(buffer);
        if (location != -1) {
            GL20.glUniformMatrix4fv(location, false, buffer);
        }
    }

}
