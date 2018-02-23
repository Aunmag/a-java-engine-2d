package aunmag.nightingale.utilities

import aunmag.nightingale.basics.BasePoint

class Mount(val item: BasePoint, var holder: BasePoint?) {

    var length = 0.0f
    var radians = 0.0f

    fun apply() {
        holder?.let {
            val cos = Math.cos(radians.toDouble()).toFloat()
            val sin = Math.sin(radians.toDouble()).toFloat()
            item.setPosition(it.x + length * cos, it.y + length * sin)
        }
    }

}
