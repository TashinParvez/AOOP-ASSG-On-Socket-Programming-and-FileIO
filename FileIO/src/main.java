import java.io.*;

public class main {
    private String inputFilePath;
    private String outputFilePath;

    //----------------------------------------- Constructor to initialize file paths  -----------------------------------------
    public main(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    //----------------------------------------- Method to read and write video file  -----------------------------------------
    public void copyVideoFile() throws IOException {
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);

        if (!inputFile.exists()) {
            throw new FileNotFoundException("Input video file not found: " + inputFilePath);
        }

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            //----------------------------------------- Read and write in chunks -----------------------------------------
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("Video file copied successfully from " + inputFilePath + " to " + outputFilePath);
        } catch (IOException e) {
            throw new IOException("Error during file copy operation: " + e.getMessage());
        }
    }

    public void displayFileInfo() throws FileNotFoundException {
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            throw new FileNotFoundException("Input video file not found: " + inputFilePath);
        }

        System.out.println("File Name: " + inputFile.getName());
        System.out.println("File Size: " + inputFile.length() + " bytes");
        System.out.println("File Path: " + inputFile.getAbsolutePath());
    }

    public static void main(String[] args) {
        try {

            //----------------------------------------- INPUT AND OUTPUT Path -----------------------------------------
            String inputPath = "E:\\Downlodes\\RIPAA\\FileIO\\cpy\\sample.mp4";
            String outputPath = "E:\\Downlodes\\RIPAA\\FileIO\\paste\\copy_sample.mp4";

            main videoHandler = new main(inputPath, outputPath);

            videoHandler.displayFileInfo();

            videoHandler.copyVideoFile();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}