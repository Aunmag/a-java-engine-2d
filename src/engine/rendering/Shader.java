package engine.rendering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

public class Shader {

    private int program;
    private int shaderVertex;
    private int shaderFragment;

    public Shader(String fileName) {
        program = GL20.glCreateProgram();

        shaderVertex = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(shaderVertex, readFile(fileName + ".vert"));
        GL20.glCompileShader(shaderVertex);
        if (GL20.glGetShaderi(shaderVertex, GL20.GL_COMPILE_STATUS) != 1) {
            System.err.println(GL20.glGetShaderInfoLog(shaderVertex));
            System.exit(1);
        }

        shaderFragment = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(shaderFragment, readFile(fileName + ".frag"));
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

    protected void finalize() throws Throwable {
        GL20.glDetachShader(program, shaderVertex);
        GL20.glDetachShader(program, shaderFragment);
        GL20.glDeleteShader(shaderVertex);
        GL20.glDeleteShader(shaderFragment);
        GL20.glDeleteProgram(program);
        super.finalize();
    }

    public void bind() {
        GL20.glUseProgram(program);
    }

    private String readFile(String fileName) {
        StringBuilder string = new StringBuilder();

        try {
            String path = "./src/engine/rendering/" + fileName;
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(new File(path))
            );

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
