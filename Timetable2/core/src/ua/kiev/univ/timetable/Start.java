package ua.kiev.univ.timetable;

import java.io.IOException;

import java.security.acl.Group;

import javax.xml.parsers.ParserConfigurationException;

import org.jgap.Configuration;
import org.jgap.InvalidConfigurationException;

import org.xml.sax.SAXException;

public class Start {

    static Integer MAX_LESSONS;//maximum number of squares in timetable
    static Integer MAX_AUDITORIES;
    static Integer MAX_TIME;
    private static final String XML_TEST_FILENAME =
        "E:\\inputTimetable_new design.xml";
    private static Integer POPULATION_SIZE;
    private static double THRESHOLD;
    private static Integer CHROMOSOME_SIZE;

    public static void main(String[] args) throws InvalidConfigurationException {
        try {
            InputData.readFromFile(XML_TEST_FILENAME);
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }
        
        Configuration conf = new Configuration("myconf");
        Lesson[] l = new Lesson[MAX_LESSONS];
        for (int i = 0; i < MAX_LESSONS; i++) {
            l[i] = new Lesson(conf, i);
            l[i].show();
        }
        
        Auditory[] a = new Auditory[MAX_AUDITORIES];
        for (int i = 0; i < MAX_AUDITORIES; i++) {
            a[i] = new Auditory(conf, i);
            a[i].show();
        }
        
        Time[] t = new Time[MAX_TIME];
        for (int i = 0; i < MAX_TIME; i++) {
            t[i] = new Time(conf,i);
            t[i].show();
        }

    }

    protected static void setMAX_LESSONS(Integer MAX_LESSONS) {
        Start.MAX_LESSONS = MAX_LESSONS;
    }

    protected static void setMAX_AUDITORIES(Integer MAX_AUDITORIES) {
        Start.MAX_AUDITORIES = MAX_AUDITORIES;
    }

    protected static void setMAX_TIME(Integer MAX_TIME) {
        Start.MAX_TIME = MAX_TIME;
    }
}
