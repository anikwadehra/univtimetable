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

  public void readFromFile(String a_fileName) throws SAXException, IOException,
                                                     ParserConfigurationException {
    Reader documentReader = new FileReader(a_fileName);
    InputSource documentSource = new InputSource(documentReader);
    DocumentBuilder builder =
      DocumentBuilderFactory.newInstance().newDocumentBuilder();

    Document document = builder.parse(documentSource);

    // Get chromosome_size population_size max_evolution threshold
    NodeList firstElements = document.getElementsByTagName("timetable");
    Element firstElement = (Element)firstElements.item(0);

    Start.CHROMOSOME_SIZE =
        Integer.parseInt(firstElement.getAttribute("chromosome_size"));
    Start.POPULATION_SIZE =
        Integer.parseInt(firstElement.getAttribute("population_size"));
    Start.MAX_EVOLUTIONS =
        Integer.parseInt(firstElement.getAttribute("max_evolution"));
    Start.THRESHOLD = Integer.parseInt(firstElement.getAttribute("threshold"));
    System.out.println("chromosome_size:" +
                       firstElement.getAttribute("chromosome_size") +
                       " population_size:" +
                       firstElement.getAttribute("population_size") +
                       " max_evolution:" +
                       firstElement.getAttribute("max_evolution") +
                       " threshold:" + firstElement.getAttribute("threshold"));
    //---------------------------------------------------------------------
    // Get classGenes data
    NodeList classGenes = document.getElementsByTagName("classGene");
    //Set maxIdClass
    ClassGene.setMax_idClass(classGenes.getLength());

    for (int i = 0; i < classGenes.getLength(); i++) {
      Element classGene = (Element)classGenes.item(i);
      ClassGene.setInputClassSize(Integer.parseInt(classGene.getAttribute("classSize")),
                                  i);
      System.out.println(classGene.getTagName() + ": idClass=" +
                         classGene.getAttribute("idClass") + " classSize=" +
                         classGene.getAttribute("classSize"));
    }
    //---------------------------------------------------------------------
    // Get groupGenes data
    NodeList groupGenes = document.getElementsByTagName("groupGene");
    //Set maxIdGroup
    GroupGene.setMax_idGroup(groupGenes.getLength());

    for (int i = 0; i < groupGenes.getLength(); i++) {
      Element groupGene = (Element)groupGenes.item(i);
      //Set field groupSize in i-th GroupGene
      GroupGene.setInputGroupSize(Integer.parseInt(groupGene.getAttribute("groupSize")),
                                  i);

      System.out.println(groupGene.getTagName() + ": idGroup=" +
                         groupGene.getAttribute("idGroup") + " groupSize=" +
                         groupGene.getAttribute("groupSize"));
    }
    //----------------------------------------------------------------------
    // Get timeGenes data
    NodeList timeGenes = document.getElementsByTagName("timeGene");
    //Set max_idTimeSlot
    TimeGene.setMax_idTimeSlot(timeGenes.getLength());

    for (int i = 0; i < timeGenes.getLength(); i++) {
      Element timeGene = (Element)timeGenes.item(i);
      System.out.println(timeGene.getTagName() + ": idTimeSlot=" +
                         timeGene.getAttribute("idTimeSlot"));

    }
    //---------------------------------------------------------------------
    // Get teacherGenes data
    NodeList teacherGenes = document.getElementsByTagName("teacherGene");
    //Set max_idTeacher
    TeacherGene.setMax_idTeacher(teacherGenes.getLength());
    for (int i = 0; i < teacherGenes.getLength(); i++) {
      Element teacherGene = (Element)teacherGenes.item(i);
      
      TeacherGene.setAll_avaliableLessons(
                    parseLine(teacherGene.getAttribute("avaliableLessons")), i);
      TeacherGene.setAll_avaliableTimeSlots(
                  parseLine(teacherGene.getAttribute("avaliableTimeSlots")), i);
      String buffer1 = "";
      String buffer2 = "";
      
      for (Integer s : parseLine(teacherGene.getAttribute("avaliableLessons"))) {
        if( s != null ) buffer1 += s.toString();
      }
      
      for (Integer s : parseLine(teacherGene.getAttribute("avaliableTimeSlots"))) {
        if( s != null )buffer2 += s.toString();
      }
      
      System.out.println(teacherGene.getTagName() + ": idTeacherGene "+
                         teacherGene.getAttribute("idTeacher") + 
                         " avaliableLessons=" + buffer1+
                         " avaliableTimeSlots=" + buffer2
                         );
    }
    
    //---------------------------------------------------------------------
    // Get lessonGene data
    NodeList lessonGenes = document.getElementsByTagName("lessonGene");
    //Set max_idLesson
    LessonGene.setMax_idLesson(lessonGenes.getLength());
    for (int i = 0; i < lessonGenes.getLength(); i++) {
      Element lessonGene = (Element)lessonGenes.item(i);
      System.out.println(lessonGene.getTagName()+ ": idLessonGene=" + 
                         lessonGene.getAttribute("idLesson"));
    }

  }

  private Integer[] parseLine(String a_line) {
    Integer[] i = new Integer[a_line.length()];
    int i_index = 0;
    String buffer = "";
    Character ch;

    for (int j = 0; j < a_line.length(); j++) {

      buffer = "";
      if (a_line.charAt(j) != ',' && a_line.charAt(j) != ' ') {
        while (a_line.charAt(j) != ',' && a_line.charAt(j) != ' ' &&
               j < a_line.length()) {
          ch = a_line.charAt(j);
          // If input character isn't digit, than go out --->
          if (!ch.isDigit(ch)) {
            System.out.println("Only digits allowed in the inputTimetable.xml ");
            return null;
          }

          // If input character is digit, than store it
          buffer += a_line.charAt(j);
          j++;
          if (j >= a_line.length())
            break;
        }
        i[i_index] = Integer.parseInt(buffer);
        i_index++;
      }

    }
    return i;
  }

}
