package ua.kiev.univ.timetable;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.security.acl.Group;

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

    public static void readFromFile(String a_fileName) throws SAXException,
                                                              IOException,
                                                              ParserConfigurationException {
        Reader documentReader = new FileReader(a_fileName);
        InputSource documentSource = new InputSource(documentReader);
        DocumentBuilder builder =
            DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(documentSource);

        //Get lesson data
        NodeList all_lessons = document.getElementsByTagName("lesson");
        Start.MAX_LESSONS = all_lessons.getLength();
        for (int i = 0; i < all_lessons.getLength(); i++) {
            Element lesson = (Element)all_lessons.item(i);
            Lesson.setAll_idLessons(      Integer.parseInt(lesson.getAttribute("idLesson"))    , i);
            Lesson.setAll_nameLesson(                      lesson.getAttribute("name")         , i);
            Lesson.setAll_periodicity(                     lesson.getAttribute("periodicity")  , i);
            Lesson.setAll_auditoriesNeed( Integer.parseInt(lesson.getAttribute("auditories"))  , i);
//            System.out.println(lesson.getAttribute("idLesson") +
//                               lesson.getAttribute("name") +
//                               lesson.getAttribute("periodicity") +
//                               lesson.getAttribute("auditories")
//                               );
            NodeList lessonTeachers = lesson.getElementsByTagName("teacher");
            for (int j = 0; j < lessonTeachers.getLength(); j++) {
              Element teacher = (Element)lessonTeachers.item(j);
              Lesson.setAll_idTeachers( Integer.parseInt(teacher.getAttribute("idTeacher")), i, j);
              Lesson.setAll_nameTeacher(teacher.getTextContent(), i, j);
//              System.out.println(teacher.getAttribute("idTeacher") 
//                                 + teacher.getTextContent()
//                                 );
            }
            NodeList lessonGroups = lesson.getElementsByTagName("group");
            for (int j = 0; j < lessonGroups.getLength(); j++) {
                Element group = (Element)lessonGroups.item(j);
                Lesson.setAll_idGroups(Integer.parseInt(group.getAttribute("idGroup")), i, j);
                Lesson.setAll_groupSize(Integer.parseInt(group.getAttribute("groupSize")), i, j);
                Lesson.setAll_nameGroup(group.getTextContent(), i, j);
//                System.out.println(group.getAttribute("idGroup")
//                                   + group.getAttribute("groupSize")
//                                   + group.getTextContent()
//                                   );
            }

        }
        //Get auditory data
        NodeList all_auditories = document.getElementsByTagName("auditory");
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
        NodeList all_timeslots = document.getElementsByTagName("timeslot");
        Start.setMAX_TIME(all_timeslots.getLength());
        for (int i = 0; i < all_timeslots.getLength(); i++) {
            Element timeslot = (Element)all_timeslots.item(i);
            Time.setAll_idTimeslots( Integer.parseInt(timeslot.getAttribute("idTimeslot")), i);
            Time.setAll_nameTimeslot( timeslot.getTextContent(), i);
//            System.out.println(timeslot.getAttribute("idTimeslot")
//                             +timeslot.getTextContent()
//                             );
        }

    }

}
