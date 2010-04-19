package ua.kiev.univ.timetable;

import org.jgap.*;


public class TimetableFitnessFunction extends FitnessFunction {

    private static final double NO_FITNESS_VALUE = 9999999;
    private static final int CENTRAL_VALUE = 0;
    private Integer[] estimate = new Integer[Start.CHROMOSOME_SIZE];
    private double penalty;
    private GroupGene gg;
    private ClassGene cg;
    private TeacherGene tg;
    private LessonGene lg;
    private TimeGene tig;
    // actualTimes[groupNumber][lessonNumber] shows how many times lesson has been tought for every group
    private Integer[][] actualTimes =
        new Integer[Start.MAX_NUMBER_OF_GROUPS][Start.MAX_NUMBER_OF_LESSONS];

    @Override
    protected double evaluate(IChromosome a_subject) {
        penalty = 0;

        for (int i = 0; i < Start.MAX_NUMBER_OF_GROUPS; i++) {
            for (int j = 0; j < Start.MAX_NUMBER_OF_LESSONS; j++) {
                actualTimes[i][j] = 0;
            }

        }


        // Extract supergenes from chromosome
        GroupClassTeacherLessonTimeSG[] s =
            new GroupClassTeacherLessonTimeSG[Start.CHROMOSOME_SIZE];
        for (int i = 0; i < Start.CHROMOSOME_SIZE; i++) {
            s[i] = (GroupClassTeacherLessonTimeSG)a_subject.getGene(i);
        }

        //------------Checking hard constraints----------------------------


        for (int i = 0; i < Start.CHROMOSOME_SIZE; i++) {

            //------Class size must be greater or equal than group size
            cg = (ClassGene)s[i].geneAt(Start.CLASS);
            gg = (GroupGene)s[i].geneAt(Start.GROUP);
            if (cg.getClassSize() < gg.getGroupSize())
                penalty += 10; //return 0;

            //-----Teacher can teach only avaliable lessons
            tg = (TeacherGene)s[i].geneAt(Start.TEACHER);
            lg = (LessonGene)s[i].geneAt(Start.LESSON);
            Integer[] avaliableLessons = tg.getAvaliableLessons();
            Integer idCurrentLesson = lg.getIdLesson();
            boolean flag =
                false; // If flag = false, than teacher can't teach current lesson; if true - he can do it.
            for (Integer lesson : avaliableLessons) {
                if (lesson != null && lesson == idCurrentLesson)
                    flag = true;
            }
            if (flag == false)
                penalty += 10; //return 0;

            //-----Teacher can teach only at avaliable time slots
            Integer[] avaliableTimeSlots = tg.getAvaliableTimeSlots();
            Integer idCurrentTimeSlot =
                (Integer)s[i].geneAt(Start.TIME).getAllele();
            flag =false; // If flag = false, than teacher can't teach at current time slot; if true - he can do it.
            for (Integer timeSlot : avaliableTimeSlots) {
                //System.out.println(timeSlot);
                if (timeSlot != null && timeSlot == idCurrentTimeSlot)
                    flag = true;
            }
            if (flag == false)
                penalty += 10; //return 0;

            //Checking study plan
            actualTimes[(Integer)gg.getAllele()][(Integer)lg.getAllele()]++;
            if( i == Start.CHROMOSOME_SIZE-1 ){
                Integer[] times = gg.getTimes();
                for (int j = 0; j < Start.MAX_NUMBER_OF_GROUPS; j++) {
                    for (int k = 0; k < Start.MAX_NUMBER_OF_LESSONS; k++) {
                        System.out.println(k+" "+actualTimes[j][k]+" "+times[k]);
                    }
                }

            }

            for (int j = 0; j < Start.CHROMOSOME_SIZE; j++) {

                //-----------Avoid one group be in the two classes at the same time
                if (i != j &&
                    s[i].geneAt(Start.GROUP).equals(s[j].geneAt(Start.GROUP)) &&
                    //&& s[i].geneAt(CLASS).equals(s[j].geneAt(CLASS))
                    s[i].geneAt(Start.TIME).equals(s[j].geneAt(Start.TIME))) {
                    penalty += 10; // return 0;
                }
                //-----------Avoid one class being occuped more than one group, one teacher, one lesson at the same time
                else if (i != j &&
                    //&& s[i].geneAt(GROUP).equals(s[j].geneAt(GROUP))
                    s[i].geneAt(Start.CLASS).equals(s[j].geneAt(Start.CLASS)) &&
                    s[i].geneAt(Start.TIME).equals(s[j].geneAt(Start.TIME))) {
                    penalty += 10; //return 0;
                }
                //-----------A group has more than one lesson
                //                else if (i != j &&
                //                         s[i].geneAt(Start.GROUP).equals(s[j].geneAt(Start.GROUP)) &&
                //                         s[i].geneAt(Start.LESSON).equals(s[j].geneAt(Start.LESSON))) {
                //                    return 0;
                //                }
                //-----------Avoid one teacher being in the two classes at the same time
                else if (i != j &&
                         s[i].geneAt(Start.TEACHER).equals(s[j].geneAt(Start.TEACHER)) &&
                         s[i].geneAt(Start.TIME).equals(s[j].geneAt(Start.TIME))) {
                    penalty += 10; //return 0;
                }

            }

        }
//        System.out.println("------------------------------------------");
//        for (int i = 0; i < Start.MAX_NUMBER_OF_GROUPS; i++) {
//            System.out.println("Group:"+i);
//            for (Integer j : actualTimes[i]) {
//                System.out.println( j );
//            }
//        }


        return 1 / (1 + penalty);
        //------------Checking soft constraints----------------------------

        //        for (int i = 0; i < Start.CHROMOSOME_SIZE; i++) {
        //            s[i] = (GroupClassTeacherLessonTimeSG)a_subject.getGene(i);
        //            estimate[i] = (Integer)s[i].geneAt(Start.GROUP).getAllele();
        //            estimate[i] += (Integer)s[i].geneAt(Start.CLASS).getAllele();
        //            estimate[i] += (Integer)s[i].geneAt(Start.TIME).getAllele();
        //            fitness += estimate[i];
        //        }
        //        System.out.println("Fitness is: " + fitness);
        //        return fitness;
        //        return 1 / (1 + Math.abs(CENTRAL_VALUE - fitness));

    }
}
