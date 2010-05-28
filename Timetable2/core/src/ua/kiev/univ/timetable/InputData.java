package ua.kiev.univ.timetable;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class InputData {

    public InputData() {
        super();
    }

    public static void readFromFile(String a_lessonsFileName, String a_dictionaryFilename) throws SAXException,
                                                              IOException,
                                                              ParserConfigurationException {
        //------parse file with data about lessons--- 
        Reader lessonsDocumentReader = new FileReader(a_lessonsFileName);
        InputSource lessonsDocumentSource = new InputSource(lessonsDocumentReader);
        DocumentBuilder lessonsBuilder =
            DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document lessonsDocument = lessonsBuilder.parse(lessonsDocumentSource);
        
        //-----parse file with dictionary data---
        Reader dictionaryDocumentReader = new FileReader(a_dictionaryFilename);
        InputSource dictionaryDocumentSource = new InputSource(dictionaryDocumentReader);
        DocumentBuilder dictionaryBuilder =
            DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dictionaryDocument = lessonsBuilder.parse(dictionaryDocumentSource);        

       
        //Get lesson data
        NodeList all_lessons = lessonsDocument.getElementsByTagName("lesson");
        //Start.MAX_LESSONS = all_lessons.getLength();
        //--------count number of auditories needed for all lessons
        Start.CHROMOSOME_SIZE = 0;
        for (int i = 0; i < all_lessons.getLength(); i++) {
          Element lesson = (Element)all_lessons.item(i);
          Start.CHROMOSOME_SIZE += Integer.parseInt(lesson.getAttribute("auditories"));
        }

        Start.MAX_LESSONS = all_lessons.getLength();
        for (int i = 0; i < all_lessons.getLength(); i++) {
            Element lesson = (Element)all_lessons.item(i);
            Lesson.setAll_idLessons(      Integer.parseInt(lesson.getAttribute("idLesson"))    , i);
            Lesson.setAll_nameLesson(                      lesson.getAttribute("name")         , i);
            Lesson.setAll_periodicity(    Integer.parseInt(lesson.getAttribute("periodicity"))  , i);
            Lesson.setAll_auditoriesNeed( Integer.parseInt(lesson.getAttribute("auditories"))  , i);
            if(lesson.getAttribute("linkedWithIdLesson") != "")
              Lesson.setAll_linkedWithIdLesson( Integer.parseInt(lesson.getAttribute("linkedWithIdLesson"))  , i);

            NodeList lessonTeachers = lesson.getElementsByTagName("teacher");
            for (int j = 0; j < lessonTeachers.getLength(); j++) {
              Element teacher = (Element)lessonTeachers.item(j);
              Lesson.setAll_idTeachers( Integer.parseInt(teacher.getAttribute("idTeacher")), i, j);
              //Lesson.setAll_nameTeacher(teacher.getTextContent(), i, j);
                              
            }
            NodeList lessonGroups = lesson.getElementsByTagName("group");
            for (int j = 0; j < lessonGroups.getLength(); j++) {
                Element group = (Element)lessonGroups.item(j);
                Lesson.setAll_idGroups(Integer.parseInt(group.getTextContent()), i, j);
                //Lesson.setAll_groupSize(Integer.parseInt(group.getAttribute("groupSize")), i, j);
                //Lesson.setAll_nameGroup(group.getTextContent(), i, j);
            }

        }
        //Get auditory data
        NodeList all_auditories = dictionaryDocument.getElementsByTagName("auditory");
        Start.setMAX_AUDITORIES(all_auditories.getLength());
        for (int i = 0; i < all_auditories.getLength(); i++) {
            Element auditory = (Element)all_auditories.item(i);
            Auditory.setAll_idAuditory( Integer.parseInt( auditory.getAttribute("idAuditory")), i);
            Auditory.setAll_auditorySize( Integer.parseInt( auditory.getAttribute("auditorySize")), i);
            Auditory.setAll_nameAuditory( auditory.getTextContent(), i);
//            System.out.println(auditory.getAttribute("idAuditory")
//                               +auditory.getAttribute("auditorySize")
//                               +auditory.getTextContent()
//                               );
        }
        //Get timeslots data
        NodeList all_timeslots = dictionaryDocument.getElementsByTagName("timeslot");
        Start.setMAX_TIME(all_timeslots.getLength());
        for (int i = 0; i < all_timeslots.getLength(); i++) {
            Element timeslot = (Element)all_timeslots.item(i);
            Time.setAll_idTimeslots( Integer.parseInt(timeslot.getAttribute("idTimeslot")), i);
            Time.setAll_timeslotType( Integer.parseInt(timeslot.getAttribute("timeslotType")), i);
            Time.setAll_nameTimeslot( timeslot.getTextContent(), i);
//            System.out.println(timeslot.getAttribute("idTimeslot")
//                             +timeslot.getTextContent()
//                             );
        }
        
      //Get group data
      NodeList all_groups = dictionaryDocument.getElementsByTagName("group");
      Start.setMAX_GROUPS(all_groups.getLength());
      for (int i = 0; i < all_groups.getLength(); i++) {
          Element group = (Element)all_groups.item(i);
          Group.setIdGroup(Integer.parseInt(group.getAttribute("idGroup")),i);
          Group.setGroupSize(Integer.parseInt(group.getAttribute("groupSize")),i);
          Group.setNameGroup( group.getTextContent(), i);
//          System.out.println("idGroup:" + group.getAttribute("idGroup") 
//                             + " groupSize:" + group.getAttribute("groupSize") 
//                             + " groupName:" + group.getTextContent());
      }
      

    }

}
