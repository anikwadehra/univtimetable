package ua.kiev.univ.timetable;


import com.sun.org.apache.xml.internal.serialize.*;
import com.sun.org.apache.xml.internal.serialize.DOMSerializer;
import com.sun.org.apache.xml.internal.serialize.Serializer;
import com.sun.org.apache.xml.internal.serialize.SerializerFactory;
import com.sun.org.apache.xml.internal.serializer.Method;

import java.io.*;

import org.jgap.*;
import org.jgap.xml.*;

import org.w3c.dom.*;


class OutputData {

  private int chromosomeSize;

  OutputData() {

  }

  void printToFile(Genotype a_currentPopulation,
                   String a_saveToFilename) throws IOException {
    //Convert Genotype to a DOM object
    Document xmlRepresentation =
      XMLManager.representGenotypeAsDocument(a_currentPopulation);

    Writer documentWriter = new FileWriter(a_saveToFilename);

    OutputFormat formatting =
      new OutputFormat(xmlRepresentation, "UTF-8", true);
    SerializerFactory factory =
      SerializerFactory.getSerializerFactory(Method.XML);
    Serializer genericSerializer =
      factory.makeSerializer(documentWriter, formatting);
    DOMSerializer documentSerializer = genericSerializer.asDOMSerializer();

    documentSerializer.serialize(xmlRepresentation);
    documentWriter.close();
  }

  void printToConsole(Chromosome a_bestChromosome, int a_max_idGroup,
                      int a_max_idTime) {
    chromosomeSize = a_max_idGroup * a_max_idTime;

    GroupClassTimeSupergene[] s = new GroupClassTimeSupergene[chromosomeSize];
    // first - Group, second - Time
    String[][] str = new String[Start.GROUP][Start.TIME];
    for (int i = 0; i < chromosomeSize; i++) {
      s[i] = (GroupClassTimeSupergene)a_bestChromosome.getGene(i);

      // Here we are going through all of the id_groups and the id_times
      // and filling str[][] array
      for (int j = 0; j < a_max_idGroup; j++) {
        for (int k = 0; k < a_max_idTime; k++) {
          if ((Integer)s[i].geneAt(Start.GROUP).getAllele() == j &&
              (Integer)s[i].geneAt(Start.TIME).getAllele() == k)
            str[j][k] = (Integer)s[i].geneAt(Start.CLASS). .toString();
        }
      }


      // fill square 11
      if ((Integer)s[i].geneAt(Start.GROUP).getAllele() == 1 &&
          (Integer)s[i].geneAt(Start.TIME).getAllele() == 1) {
        if ((Integer)s[i].geneAt(Start.CLASS).getAllele() == 0)
          str[1][1] = "a";
        else
          str[1][1] = "b";
      }
    }
    // print str[][]
    System.out.println("------------------------------");
    System.out.println("     I    II");
    System.out.println("1    " + str[0][0] + "    " + str[1][0]);
    System.out.println("2    " + str[0][1] + "    " + str[1][1]);
  }

}
