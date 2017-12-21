package aunmag.nightingale.structures;

import aunmag.nightingale.basics.BaseQuad;

public class ShaderStatic extends Shader {

    private final static Model model = Model.createFromQuad(new BaseQuad(2, 2));

    public ShaderStatic(Class resourceClass) {
        super(resourceClass);
    }

    public final void render() {
        bind();
        model.render();
    }

}
