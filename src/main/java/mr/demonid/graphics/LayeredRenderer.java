package mr.demonid.graphics;

import java.util.*;


/**
 * Рендер по слоям.
 */
public class LayeredRenderer {
    private final Map<Integer, List<Sprite>> layers = new TreeMap<>();

    public void addSprite(int layer, Sprite sprite) {
        layers.computeIfAbsent(layer, k -> new ArrayList<>()).add(sprite);
    }

    public void renderAll(ScreenBuffer buffer, int frame) {
        for (var entry : layers.entrySet()) {
            for (Sprite sprite : entry.getValue()) {
                sprite.render(buffer, frame);
            }
        }
    }

    public void clear() {
        layers.clear();
    }
}
