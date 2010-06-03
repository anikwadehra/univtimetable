package ua.kiev.univ.timetable;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jgap.Chromosome;


public class OutputData {
    
    protected static void writeToFile(Chromosome a_chromosome, String a_fileName){
        LessonAuditoryTimeSG superGene;
        Lesson l1;
        Lesson l2;
        Auditory a1;
        Auditory a2;
        Time t1;
        Time t2;
      
        Integer[] idTeachers;
        String[] nameTeachers;
        
        Integer[] idGroups;
        String[] nameGroups;
        
        BufferedWriter outputStream = null;
        try {
            outputStream = new BufferedWriter(new FileWriter(a_fileName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        String nextRow = "\r\n";
        String tab = "\t";
        String tab2 = "\t\t";
        String tab3 = "\t\t\t";
        String tab4 = "\t\t\t\t";
        try {
        String xmlString = "<timetable>"+nextRow;
        xmlString += tab + "<lessons>"+nextRow;
        outputStream.write(xmlString);
            
        for (int i = 0; i < Start.CHROMOSOME_SIZE; i++) {
            superGene = (LessonAuditoryTimeSG)a_chromosome.getGene(i);
            l1 = (Lesson)superGene.geneAt(Start.LESSON);
            a1 = (Auditory)superGene.geneAt(Start.AUDITORY);
            t1 = (Time)superGene.geneAt(Start.TIME);
            
            xmlString = tab2 + "<lesson idLesson=\"" + l1.getIdLesson() 
                               + "\" name=\"" + l1.getNameLesson() 
                        + "\">" + nextRow;
            //--------Teachers----------
            idTeachers = l1.getIdTeachers();
            nameTeachers = Teacher.getNameTeachers(idTeachers);
            xmlString += tab3 + "<teachers>" + nextRow;
            
            for (int j = 0; j < idTeachers.length; j++) {
                if( idTeachers[j] != null)
                  xmlString += tab4 + "<teacher idTeacher=\"" + idTeachers[j] 
                            + "\">" + nameTeachers[j] + "</teacher>" + nextRow;
                }
            xmlString += tab3 + "</teachers>" + nextRow;
            
            //-------Groups------------
            idGroups = l1.getIdGroups();
            nameGroups = Group.getNameGroups(idGroups);
            xmlString += tab3 + "<groups>" + nextRow;
            for (int j = 0; j < idGroups.length; j++) {
                    if( idGroups[j] != null)
                        xmlString += tab4 + "<group idGroup=\"" 
                                     + idGroups[j] + "\">" + nameGroups[j] 
                                     + "</group>" + nextRow;
                }
            xmlString += tab3 + "</groups>" + nextRow;
            
            //-----Timeslots----------
            xmlString += tab3 + "<timeslots>" + nextRow;
//            for (int j = 0; j < Start.CHROMOSOME_SIZE; j++) {
//              superGene = (LessonAuditoryTimeSG)a_chromosome.getGene(j);
//              l2 = (Lesson)superGene.geneAt(Start.LESSON);
//              t2 = (Time)superGene.geneAt(Start.TIME);
//              if( l1.getIdLesson() == l2.getIdLesson() && t1.equals(t2)){
                xmlString += tab4 + "<timeslot idTimeslot=\"" + t1.getIdTimeslot()
                          + "\" timeslotType=\"" + t1.getTimeslotType()
                          + "\">" + t1.getNameTimeslot() + "</timeslot>" + nextRow;
              
            
            xmlString += tab3 + "</timeslots>" + nextRow;
            
            //-----Auditories---------
            xmlString += tab3 + "<auditories>" + nextRow;
            for (int j = 0; j < Start.CHROMOSOME_SIZE; j++) {
                superGene = (LessonAuditoryTimeSG)a_chromosome.getGene(j);
                l2 = (Lesson)superGene.geneAt(Start.LESSON);
                a2 = (Auditory)superGene.geneAt(Start.AUDITORY);
                if( l1.getIdLesson() == l2.getIdLesson() ){
                  xmlString += tab4 + "<auditory idAuditory=\"" + a2.getIdAuditory() 
                               + "\">" + a2.getNameAuditory() + "</auditory>" + nextRow;
                }
            }
            xmlString += tab3 + "</auditories>" + nextRow;
            xmlString += tab2 + "</lesson>" + nextRow;
            outputStream.write(xmlString);            
        }
       xmlString = tab + "</lessons>"+nextRow;
       xmlString += "</timetable>"; 

        
            outputStream.write(xmlString);
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }      
    }
}
