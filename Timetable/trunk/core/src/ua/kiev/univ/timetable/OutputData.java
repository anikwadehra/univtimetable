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

  private Integer max_idGroup;
  private Integer max_idTime;

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

  void printToConsole(Chromosome a_bestChromosome) {

    // Extracting GroupClassTimeSupergene from a_bestChromosome
    GroupClassTimeSupergene[] s =
      new GroupClassTimeSupergene[Start.CHROMOSOME_SIZE];
    s[0] = (GroupClassTimeSupergene)a_bestChromosome.getGene(0);

    // Extracting max_idGroup from GroupGene
    GroupGene gg = (GroupGene)s[0].geneAt(Start.GROUP);
    max_idGroup = gg.getMax_idGroup();

    // Extracting max_idTime from TimeGene
    TimeGene tg = (TimeGene)s[0].geneAt(Start.TIME);
    max_idTime = tg.getMax_idTimeSlot();


    // first - Group, second - Time
    String[][] str = new String[max_idGroup][max_idTime];
    for (int i = 0; i < Start.CHROMOSOME_SIZE; i++) {
      s[i] = (GroupClassTimeSupergene)a_bestChromosome.getGene(i);

      // Here we are going through all of the id_groups and the id_times
      // and filling str[][] array
      for (int j = 0; j < max_idGroup; j++) {
        for (int k = 0; k < max_idTime; k++) {
          if ((Integer)s[i].geneAt(Start.GROUP).getAllele() == j &&
              (Integer)s[i].geneAt(Start.TIME).getAllele() == k)
            str[j][k] = s[i].geneAt(Start.CLASS).getAllele().toString();
        }
      }
    }

    // Printing str[][] array
    System.out.println("------------------------------");
    System.out.print("   ");
    for (int i = 0; i < max_idGroup; i++) {
      System.out.print(i + " ");
    }

    System.out.println("");

    for (int i = 0; i < max_idTime; i++) {
      System.out.print(i + "  ");
      for (int j = 0; j < max_idGroup; j++) {
        System.out.print( str[j][i]  + " ");
      }
      System.out.println("");
    }
  }

}
