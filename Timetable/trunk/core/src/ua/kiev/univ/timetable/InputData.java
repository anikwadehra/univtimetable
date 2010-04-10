package ua.kiev.univ.timetable;


import java.io.FileReader;
import java.io.IOException;

import java.io.Reader;

import org.w3c.dom.*;

import org.xml.sax.*;

import javax.xml.parsers.*;

public class InputData {

    public InputData() {
        super();
    }

    public void readFromFile(String a_fileName) throws SAXException,
                                                       IOException,
                                                       ParserConfigurationException {
        Reader documentReader = new FileReader(a_fileName);
        InputSource documentSource = new InputSource(documentReader);
        DocumentBuilder builder =
            DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document document = builder.parse(documentSource);

        // Get chromosome_size population_size max_evolution threshold
        NodeList firstElements = document.getElementsByTagName("timetable");
        Element firstElement = (Element)firstElements.item(0);
        System.out.println("chromosome_size:" +
                           firstElement.getAttribute("chromosome_size") +
                           " population_size:" +
                           firstElement.getAttribute("population_size") +
                           " max_evolution:" +
                           firstElement.getAttribute("max_evolution") +
                           " threshold:" +
                           firstElement.getAttribute("threshold"));

        // Get classGenes data
        NodeList classGenes = document.getElementsByTagName("classGene");
        for (int i = 0; i < classGenes.getLength(); i++) {
            Element classGene = (Element)classGenes.item(i);
            System.out.println(classGene.getTagName() + ": idClass=" +
                               classGene.getAttribute("idClass") +
                               " classSize=" +
                               classGene.getAttribute("classSize"));
        }

        // Get groupGenes data
        NodeList groupGenes = document.getElementsByTagName("groupGene");
        for (int i = 0; i < groupGenes.getLength(); i++) {
            Element groupGene = (Element)groupGenes.item(i);
            System.out.println(groupGene.getTagName() + ": idGroup=" +
                               groupGene.getAttribute("idGroup") +
                               " groupSize=" +
                               groupGene.getAttribute("groupSize"));
        }

        // Get timeGenes data
        NodeList timeGenes = document.getElementsByTagName("timeGene");
        for (int i = 0; i < timeGenes.getLength(); i++) {
            Element timeGene = (Element)timeGenes.item(i);
            System.out.println(timeGene.getTagName() + ": idTimeSlot=" +
                               timeGene.getAttribute("idTimeSlot"));

        }


        //         Element piece = (Element) classGene.getParentNode();
        //         Element pieces = (Element) piece.getParentNode();
        //         System.out.println(
        //           (pieces.getTagName().equals("WHITEPIECES")
        //            ? "White " : "Black ")
        //           + piece.getTagName().toLowerCase() + ": "
        //           + classGene.getAttribute("COLUMN")
        //           + classGene.getAttribute("ROW"));


    }

}
