package ua.kiev.univ.timetable;

import com.sun.org.apache.xml.internal.serialize.DOMSerializer;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.Serializer;
import com.sun.org.apache.xml.internal.serialize.SerializerFactory;
import com.sun.org.apache.xml.internal.serializer.Method;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.jgap.Chromosome;
import org.jgap.xml.XMLManager;

import org.w3c.dom.Document;

public class OutputData {
    
    protected static void writeToFile(Chromosome a_chromosome, String a_fileName){

      //Convert bestChromosome to a DOM object
//      Document documentBestChromosome =
//          XMLManager.representChromosomeAsDocument(a_chromosome);
      
      Writer documentWriter;
        try {
            documentWriter = new FileWriter( a_fileName );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
      
//      Document doc = XMLManager.writeFile(arg0, arg1);
//      documentBestChromosome.
//      Serializer chromosomeGenericSerializer = 
//        factory.makeSerializer(chromosomeWriter, formatting);
//      DOMSerializer chromosomeDocumentSerializer = chromosomeGenericSerializer.asDOMSerializer();
//      chromosomeDocumentSerializer.serialize(bestChromosomeXMLRepresentation);
//      chromosomeWriter.close();         
    }
}
