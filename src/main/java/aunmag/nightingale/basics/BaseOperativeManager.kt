package aunmag.nightingale.basics

import java.util.ArrayList

open class BaseOperativeManager<T: BaseOperative> {

    val all: MutableList<T> = ArrayList()

    open fun update() {
        for (index in all.size - 1 downTo 0) {
            val instance = all[index]

            instance.update()

            if (instance.isRemoved) {
                all.removeAt(index)
            }
        }
    }

    open fun render() {
        for (instance in all) {
            instance.render()
        }
    }

    open fun remove() {
        for (instance in all) {
            instance.remove()
        }

        all.clear()
    }

}
