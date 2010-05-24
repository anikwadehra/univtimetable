package ua.kiev.univ.timetable;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jgap.Chromosome;


public class OutputData {
    
    protected static void writeToFile(Chromosome a_chromosome, String a_fileName){
        BufferedWriter outputStream = null;
        try {
            outputStream = new BufferedWriter(new FileWriter(a_fileName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        String xmlString = "<timetable>";
        xmlString += "\r\n";
        xmlString += "</timetable>";        
        try {
            outputStream.write(xmlString);
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }      
    }
}
