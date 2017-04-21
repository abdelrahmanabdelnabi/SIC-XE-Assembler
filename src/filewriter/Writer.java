package src.filewriter;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Created by ahmed on 4/19/17.
 */
public class Writer {
    // default
    private String path = System.getProperty("user.dir");
    private String fileName;

    public Writer(String path) {
        this.path = path;
    }

    public void writeToFile(String data) {
        try {
            FileWriter fileWriter = new FileWriter(path + fileName);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(data);
            writer.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
