package hu.fazekas.util;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {

    public void writeToCsv(String logData){
        File csvOutput = new File("importLog");

        try(FileWriter fileWriter = new FileWriter(csvOutput, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            fileWriter.write(logData);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
