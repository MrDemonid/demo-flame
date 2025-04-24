package mr.demonid.graphics;


/**
 * Эмуляция старой VGA-палитры
 */
public class VGAPalette {
    private final int[] colors = new int[256];              // палитра из 256 цветов
    private static final int[] VGA_TO_RGB = new int[64];    // 6-бит в 8-бит

    static {
        for (int i = 0; i < 64; i++) {
            VGA_TO_RGB[i] = i * 255 / 63;
        }
    }

    /**
     * Создаёт палитру из массива 768 байт (256 цветов × 3 компонента)
     */
    public VGAPalette(byte[] rawPalette) {
        if (rawPalette.length != 768) {
            throw new IllegalArgumentException("Палитра должна содержать ровно 768 байт (256 x 3)");
        }

        for (int i = 0; i < 256; i++) {
            int r6 = rawPalette[i * 3] & 0x3F;
            int g6 = rawPalette[i * 3 + 1] & 0x3F;
            int b6 = rawPalette[i * 3 + 2] & 0x3F;

            int r = VGA_TO_RGB[r6];
            int g = VGA_TO_RGB[g6];
            int b = VGA_TO_RGB[b6];

            colors[i] = 0xFF000000 | (r << 16) | (g << 8) | b;
        }
    }

    /**
     * Возвращает 32-битный ARGB цвет по индексу.
     */
    public int getColor(int index) {
        return colors[index & 0xFF];
    }

    /**
     * Возвращает всю палитру из 256 цветов.
     */
    public int[] getColors() {
        return colors.clone();
    }

    /**
     * Устанавливает цвет вручную.
     */
    public void setColor(int index, int r6, int g6, int b6) {
        int r = VGA_TO_RGB[r6 & 0x3F];
        int g = VGA_TO_RGB[g6 & 0x3F];
        int b = VGA_TO_RGB[b6 & 0x3F];
        colors[index & 0xFF] = 0xFF000000 | (r << 16) | (g << 8) | b;
    }
}
