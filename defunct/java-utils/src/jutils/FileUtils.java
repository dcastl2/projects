package jutils;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileUtils {


    /**
     * Reads file into string.
     * @param pathname  path of the file to be read
     */
    public static String readFile(String pathname) throws IOException {
        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");
        try { 
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() 
                             + lineSeparator);
            } 
            return fileContents.toString(); 
        } finally { 
            scanner.close(); 
        } 
    }


    public static void writeFile(String pathname, String fileContents) throws IOException {
        FileWriter w = new FileWriter(pathname);
        try { 
            w.write(fileContents);
        } finally {
            w.close();
        }
    }


    public static void catInto(String path1, String path2, String path3) throws IOException {
        String file1 = readFile(path1);
        String file2 = readFile(path2);
        writeFile(path3, file1+file2);
    }

}
