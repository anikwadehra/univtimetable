package ua.kiev.univ.timetable;

import java.io.Serializable;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IGeneConstraintChecker;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.impl.IntegerGene;

public class Lesson extends IntegerGene implements Serializable, Gene {
    
    private static final Integer MAX_TEACHERS = 2;//maximun number of teachers needed for every single square
    private static final Integer MAX_GROUPS = 6;//maximum number of groups that could be placed in a single square
    
    private Integer idLesson;
    private static Integer[] all_idLessons = new Integer[Start.MAX_LESSONS];
    
    private String  nameLesson;
    private static String[] all_nameLesson = new String[Start.MAX_LESSONS];
    
    private String periodicity;
    private static String[] all_periodicity = new String[Start.MAX_LESSONS];
    
    private Integer auditoriesNeed; //number of auditories needed for the lesson
    private static Integer[] all_auditoriesNeed = new Integer[Start.MAX_LESSONS];
    
    private Integer[] idTeachers = new Integer[MAX_TEACHERS];
    private static Integer[][] all_idTeachers = new Integer[Start.MAX_LESSONS][MAX_TEACHERS];
    
    private String[] nameTeacher = new String[MAX_TEACHERS];
    private static String[][] all_nameTeacher = new String[Start.MAX_LESSONS][MAX_TEACHERS];
    
    private Integer[] idGroups = new Integer[MAX_GROUPS];
    private static Integer[][] all_idGroups = new Integer[Start.MAX_LESSONS][MAX_GROUPS];
    
    private String[] nameGroup = new String[MAX_GROUPS];
    private static String[][] all_nameGroup = new String[Start.MAX_LESSONS][MAX_GROUPS];
    
    private Integer[] groupSize = new Integer[MAX_GROUPS];
    private static Integer[][] all_groupSize = new Integer[Start.MAX_LESSONS][MAX_GROUPS];

    public Lesson(Configuration a_configuration, Integer a_index) throws InvalidConfigurationException {
        super(a_configuration);
        idLesson = all_idLessons[a_index];
        nameLesson = all_nameLesson[a_index];
        periodicity = all_periodicity[a_index];
        auditoriesNeed = all_auditoriesNeed[a_index];
        idTeachers = all_idTeachers[a_index];
        nameTeacher = all_nameTeacher[a_index];
        idGroups = all_idGroups[a_index];
        groupSize = all_groupSize[a_index];
        nameGroup = all_nameGroup[a_index];

        System.out.println("Instance of Lesson created!");
    }
    

    public Gene newGene() {
        return null;
    }

    public void setAllele(Object object) {
    }

    public Object getAllele() {
        return null;
    }

    public String getPersistentRepresentation() throws UnsupportedOperationException {
        return null;
    }

    public void setValueFromPersistentRepresentation(String string) throws UnsupportedOperationException,
                                                                           UnsupportedRepresentationException {
    }

    public void setToRandomValue(RandomGenerator randomGenerator) {
    }

    public void cleanup() {
    }

    public int size() {
        return 0;
    }

    public void applyMutation(int i, double d) {
    }

    public void setApplicationData(Object object) {
    }

    public Object getApplicationData() {
        return null;
    }

    public void setCompareApplicationData(boolean b) {
    }

    public boolean isCompareApplicationData() {
        return false;
    }

    public double getEnergy() {
        return 0.0;
    }

    public void setEnergy(double d) {
    }

    public void setConstraintChecker(IGeneConstraintChecker iGeneConstraintChecker) {
    }

    public Configuration getConfiguration() {
        return null;
    }

    public int compareTo(Object o) {
        return 0;
    }
    //-------new methods---------

    protected static void setAll_idLessons(Integer a_idLessons, Integer a_index) {
        Lesson.all_idLessons[a_index] = a_idLessons;
    }

    protected static void setAll_nameLesson(String a_nameLesson, Integer a_index) {
        Lesson.all_nameLesson[a_index] = a_nameLesson;
    }

    protected static void setAll_periodicity(String a_periodicity, Integer a_index) {
        Lesson.all_periodicity[a_index] = a_periodicity;
    }

    protected static void setAll_auditoriesNeed(Integer a_auditoriesNeed, Integer a_index) {
        Lesson.all_auditoriesNeed[a_index] = a_auditoriesNeed;
    }

    protected static void setAll_idTeachers(Integer a_idTeachers, Integer a_indexLesson, Integer a_indexTeacherInLesson) {
        Lesson.all_idTeachers[a_indexLesson][a_indexTeacherInLesson] = a_idTeachers;
    }

    protected static void setAll_idGroups(Integer a_idGroups, Integer a_indexLesson, Integer a_indexGroupInLesson) {
        Lesson.all_idGroups[a_indexLesson][a_indexGroupInLesson] = a_idGroups;
    }

    protected static void setAll_nameTeacher(String a_nameTeacher, Integer a_indexLesson, Integer a_indexGroupInLesson) {
        Lesson.all_nameTeacher[a_indexLesson][a_indexGroupInLesson] = a_nameTeacher;
    }

    protected static void setAll_nameGroup(String a_nameGroup, Integer a_indexLesson, Integer a_indexGroupInLesson) {
        Lesson.all_nameGroup[a_indexLesson][a_indexGroupInLesson] = a_nameGroup;
    }

    protected static void setAll_groupSize(Integer a_groupSize, Integer a_indexLesson, Integer a_indexGroupInLesson) {
        Lesson.all_groupSize[a_indexLesson][a_indexGroupInLesson] = a_groupSize;
    }

    protected void show(){
      System.out.println("idLesson:" + idLesson + " name:" + nameLesson 
                         + " periodicity:"+periodicity + " auditories:" + auditoriesNeed);
      
      for (Integer i : idTeachers) {
            if(i != null) 
                System.out.println("idTeacher:" + i);
        }
      for (String s : nameTeacher) {
            if(s != null) 
                System.out.println("nameTeacher: " + s);
        }
      for (String s : nameGroup) {
            if(s != null) 
                System.out.println("groupName: " + s);
        }

    }
}
