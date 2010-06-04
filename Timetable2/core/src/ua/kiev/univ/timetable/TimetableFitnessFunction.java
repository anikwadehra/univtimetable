package ua.kiev.univ.timetable;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class TimetableFitnessFunction extends FitnessFunction{
    double penalty;
    
    protected double evaluate(IChromosome a_chromosome) {
        penalty = 0;
        LessonAuditoryTimeSG[] superGene = new LessonAuditoryTimeSG[Start.CHROMOSOME_SIZE];
        
        Lesson l1;
        Lesson l2;
        
        Auditory a1;
        Auditory a2;
        
        Time t1;
        Time t2;
        
        Integer[] idTeachers1;
        Integer[] idTeachers2;
        
        Integer[] idGroups1;
        Integer[] idGroups2;
        
        Integer[] lessonAssigened = new Integer[Start.CHROMOSOME_SIZE];
       
        for (int i = 0; i < Start.CHROMOSOME_SIZE; i++) {
            superGene[i] = (LessonAuditoryTimeSG)a_chromosome.getGene(i);
            l1 = (Lesson)superGene[i].geneAt(Start.LESSON);
            a1 = (Auditory)superGene[i].geneAt(Start.AUDITORY);
            t1 = (Time)superGene[i].geneAt(Start.TIME);
            idTeachers1 = l1.getIdTeachers();
            idGroups1 = l1.getIdGroups();
            lessonAssigened[i] = 1;
            
            //---sport pair(idLesson=100x) must be in sport rooms(id=100) only
//            if( (l1.getIdLesson()/1000 == 1 && a1.getIdAuditory() != 100) ||
//                (l1.getIdLesson()/1000 != 1 && a1.getIdAuditory() == 100)){
//                  penalty += 10;
//                  //return 1/penalty;
//            }
            
            //---first pair is better, 4th pair is worse; min penalty=0, max penalty=3
           // penalty += t1.getIdTimeslot() % 4;
            
            //-----auditory size must be greater or equal than total groupSize for one lesson
            if(l1.getAuditoriesNeed() != 0){
              if( l1.getTotalGroupSize()/l1.getAuditoriesNeed() > a1.getAuditorySize())
                 //penalty += 10;
                 penalty += 0;
            }
            
            //-----lesson's periodicity (even or odd or even_odd) must be equal with timeslotType
            if(l1.getPeriodicity() !=  t1.getTimeslotType()){
              //penalty += 30;
              penalty += 0;
            }
            
            //-----lesson must be assigened to the appropriate auditoryType
            if(l1.getAuditoryType() != a1.getAuditoryType()){
              //penalty += 30;
              penalty += 0;
            }
               
            
            for (int j = 0; j < Start.CHROMOSOME_SIZE; j++) {
                superGene[j] = (LessonAuditoryTimeSG)a_chromosome.getGene(j);
                l2 = (Lesson)superGene[j].geneAt(Start.LESSON);
                a2 = (Auditory)superGene[j].geneAt(Start.AUDITORY);
                t2 = (Time)superGene[j].geneAt(Start.TIME);
                idTeachers2 = l2.getIdTeachers();
                idGroups2 = l2.getIdGroups();
                
                //----every lesson is original except case when auditoriesNeed > 1---------
                if( i != j && l1.getIdLesson() == l2.getIdLesson() ){
                    lessonAssigened[i] ++;
                    //if( l1.getAuditoriesNeed() == 1 || !(t1.equals(t2)) || a1.equals(a2) )
                    if( !(t1.equals(t2)) || a1.equals(a2) )
                        penalty += 0;
                        //penalty += 233;
                }
                
                
                if(i != j && t1.equals(t2) 
                          && !(l1.equals(l2))){
                  //----ome teacher can't be assigned to more than one lesson at the same time
                  for (int m = 0; m < idTeachers1.length; m++) {
                        for (int n = 0; n < idTeachers2.length; n++) {
                            if(          idTeachers1[m] != null 
                                      && idTeachers2[n] != null 
                                      && idTeachers1[m] == idTeachers2[n]){
                              penalty += 0;
                              //penalty +=8;
                            }
                        }

                 }

                 //-----one group can't be assigned to more than one lesson at the same time
                 for (int m = 0; m < idGroups1.length; m++) {
                        for (int n = 0; n < idGroups2.length; n++) {
                            if(          idGroups1[m] != null
                                      && idGroups2[n] != null
                                      && idGroups1[m] == idGroups2[n]){
                              penalty += 0;
                              //penalty += 46;
                            }
                        }
                 }
                 //--- one auditory can be assigened to only one lesson at the same time
                 if( a1.getIdAuditory() == a2.getIdAuditory() && a1.getIdAuditory() != 100)
                    penalty += 0;
                    //penalty += 1;
               }
                //-----if one lesson is linked with another, then they must be assigened in the same idTimeslot
                //-----and different timeslot types (even or odd)
                if(l1.getLinkedWithIdLesson() != null &&
                    l1.getLinkedWithIdLesson() == l2.getIdLesson()&&
                    (t1.getIdTimeslot() != t2.getIdTimeslot() || t1.getTimeslotType() == t2.getTimeslotType())
                  )
                    penalty += 0;
                    //penalty += 3;
          }
          
          if( lessonAssigened[i] != l1.getAuditoriesNeed() )
              penalty += 0;
              System.out.println("lessonAssigened[i]:" + lessonAssigened[i] + " l1.getAuditoriesNeed():" + l1.getAuditoriesNeed() + "i:" + i);
              penalty += 811;
        }
        return 1/(1+penalty);
    }

}
