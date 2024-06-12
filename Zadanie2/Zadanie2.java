package Zadanie2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Zadanie2 {

    public static void main(String[] args) {
        // Example paths (replace with actual directory paths)
        String[] directories = {
                "Zadanie2/Szukanie",
                "Zadanie2/Szukanie2",
                // "/path/to/second/directory"
        };

        // Create a fixed thread pool
        ExecutorService executor = Executors.newFixedThreadPool(directories.length);

        // Submit a task for each directory
        for (String dir : directories) {
            executor.submit(new DirectoryTask(dir));
        }

        // Shutdown the executor
        executor.shutdown();
    }
}

class DirectoryTask implements Runnable {
    private final String directoryPath;

    public DirectoryTask(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void run() {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            List<File> fileList = new ArrayList<>();
            long totalSize = listFilesAndCalculateSize(directory, fileList);

            System.out.println("Directory: " + directoryPath);
            for (File file : fileList) {
                System.out.println(file.getAbsolutePath());
            }
            System.out.println("Total size: " + totalSize + " bytes");
        } else {
            System.out.println("Directory does not exist or is not a directory: " + directoryPath);
        }
    }

    private long listFilesAndCalculateSize(File directory, List<File> fileList) {
        long totalSize = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                fileList.add(file);
                if (file.isFile()) {
                    totalSize += file.length();
                } else if (file.isDirectory()) {
                    totalSize += listFilesAndCalculateSize(file, fileList);
                }
            }
        }
        return totalSize;
    }
}
