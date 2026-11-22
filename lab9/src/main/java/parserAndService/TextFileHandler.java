package parserAndService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextFileHandler {

    public static String read(Path path) throws IOException {
        return Files.readString(path);
    }

    public static void write(Path path, String content) throws IOException {
        Path parent = path.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.writeString(path, content);
    }
}