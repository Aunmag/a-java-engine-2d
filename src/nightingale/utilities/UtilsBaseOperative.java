package nightingale.utilities;

import nightingale.basics.BaseOperative;

import java.util.ArrayList;
import java.util.List;

public class UtilsBaseOperative {

    public static void updateAll(List<? extends BaseOperative> all) {
        List<BaseOperative> toDelete = new ArrayList<>();

        for (BaseOperative instance: all) {
            instance.update();

            if (instance.isRemoved()) {
                toDelete.add(instance);
            }
        }

        for (BaseOperative instance: toDelete) {
            all.remove(instance);
        }
    }

    public static void renderAll(List<? extends BaseOperative> all) {
        for (BaseOperative instance: all) {
            instance.render();
        }
    }

    public static void removeAll(List<? extends BaseOperative> all) {
        for (BaseOperative instance: all) {
            instance.remove();
        }

        all.clear();
    }

}
