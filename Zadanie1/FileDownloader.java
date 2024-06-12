import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileDownloader {
    public static void downloadFile(String fileUrl, String saveFilePath) throws IOException, URISyntaxException {
        URL url = new URI(fileUrl).toURL();

        try (ReadableByteChannel inChannel = Channels.newChannel(url.openStream());
                WritableByteChannel outChannel = java.nio.file.Files.newByteChannel(Path.of(saveFilePath),
                        StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }
        }
    }

    public static void main(String[] args) {
        String[] fileUrls = {
                "https://github.com/itsfoss/text-files/blob/23b13222e0f5bd7da8d1cf9f40d179387f0e29e3/agatha.txt",
                "https://github.com/itsfoss/text-files/blob/23b13222e0f5bd7da8d1cf9f40d179387f0e29e3/agatha_complete.txt",
                "https://github.com/itsfoss/text-files/blob/23b13222e0f5bd7da8d1cf9f40d179387f0e29e3/sample_log_file.txt"
        };
        String[] saveFilePaths = {
                "file1.txt",
                "file2.txt",
                "file3.txt"
        };

        ExecutorService executorService = Executors.newFixedThreadPool(fileUrls.length);

        for (int i = 0; i < fileUrls.length; i++) {
            final String fileUrl = fileUrls[i];
            final String saveFilePath = saveFilePaths[i];
            executorService.submit(() -> {
                try {
                    downloadFile(fileUrl, saveFilePath);
                    System.out.println("Plik zapisany: " + saveFilePath);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
    }
}