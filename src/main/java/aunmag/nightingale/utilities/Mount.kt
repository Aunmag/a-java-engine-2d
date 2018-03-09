package aunmag.nightingale.utilities

import org.joml.Vector2f

class Mount(val item: Vector2f, var holder: Vector2f?) {

    var length = 0.0f
    var radians = 0.0f

    fun apply() {
        holder?.let {
            val cos = Math.cos(radians.toDouble()).toFloat()
            val sin = Math.sin(radians.toDouble()).toFloat()
            item.set(it.x + length * cos, it.y + length * sin)
        }
    }

}
