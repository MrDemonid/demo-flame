package mr.demonid.graphics.screenshoot;

import mr.demonid.graphics.ScreenBuffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScreenshotSaver {
    private final File directory;
    private final String format;
    private final String prefix;
    private final int digits;

    private static final Pattern FILENAME_PATTERN = Pattern.compile("screen(\\d+).(png|jpg|bmp)");

    /**
     * Создаёт ScreenshotSaver с заданной папкой и форматом.
     *
     * @param directoryPath Путь к папке (например, "screenshots")
     * @param format        Формат изображения: png, jpg и т.д.
     * @param prefix        Префикс имени (обычно "screen")
     * @param digits        Кол-во цифр в номере (например, 4 → screen0001.png)
     */
    public ScreenshotSaver(String directoryPath, String format, String prefix, int digits) {
        this.directory = new File(directoryPath);
        this.format = format;
        this.prefix = prefix;
        this.digits = digits;

        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Сохраняет изображение.
     */
    public void save(BufferedImage image) {
        int nextNumber = findNextIndex();
        String filename = String.format("%s%0" + digits + "d.%s", prefix, nextNumber, format);
        File file = new File(directory, filename);
        try {
            ImageIO.write(image, format, file);
        } catch (IOException e) {
            System.out.println("Формат изображения не поддерживается: " + format);
        }
        System.out.println("Скриншот сохранён: " + file.getAbsolutePath());
    }

    /**
     * Сохранение из ScreenBuffer.
     */
    public void save(ScreenBuffer buffer) {
        save(buffer.getImage());
    }

    /**
     * Определяет следующий свободный номер файла (screenXXXX.png)
     */
    private int findNextIndex() {
        File[] files = directory.listFiles((dir, name) -> name.startsWith(prefix) && name.endsWith("." + format));
        if (files == null || files.length == 0) {
            return 1;
        }
        // составляем список номеров файлов и берем первый свободный номер
        return Arrays.stream(files)
                .map(file -> {
                    Matcher matcher = FILENAME_PATTERN.matcher(file.getName());
                    if (matcher.matches()) {
                        try {
                            return Integer.parseInt(matcher.group(1));
                        } catch (NumberFormatException ignored) {}
                    }
                    return -1;
                })
                .max(Comparator.naturalOrder())
                .orElse(0) + 1;
    }
}