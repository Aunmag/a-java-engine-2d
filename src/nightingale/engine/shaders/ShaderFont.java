package nightingale.engine.shaders;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

public class ShaderFont extends ShaderSprite {

    private final int uniformLocationColour;

    public ShaderFont() {
        super("shaderFont");

        uniformLocationColour = getUniformLocation("colour");
    }

    /* Setters */

    public void setUniformColour(Vector4f colour) {
        GL20.glUniform4f(uniformLocationColour, colour.x, colour.y, colour.z, colour.w);
    }

}
