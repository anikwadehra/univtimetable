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
    
    private Integer indexLesson;
    
    private Integer idLesson;
    private static Integer[] all_idLessons = new Integer[Start.MAX_LESSONS];
    
    private String  nameLesson;
    private static String[] all_nameLesson = new String[Start.MAX_LESSONS];
    
    private Integer periodicity;
    private static Integer[] all_periodicity = new Integer[Start.MAX_LESSONS];
    
    private Integer auditoriesNeed; //number of auditories needed for the lesson
    private static Integer[] all_auditoriesNeed = new Integer[Start.MAX_LESSONS];
    
    private Integer linkedWithIdLesson;
    private static Integer[] all_linkedWithIdLesson = new Integer[Start.MAX_LESSONS];
    
    private Integer auditoryType;
    private static Integer[] all_auditoryType = new Integer[Start.MAX_LESSONS];
    
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
        indexLesson = a_index;
        idLesson = all_idLessons[a_index];
        nameLesson = all_nameLesson[a_index];
        periodicity = all_periodicity[a_index];
        auditoriesNeed = all_auditoriesNeed[a_index];
        idTeachers = all_idTeachers[a_index];
        nameTeacher = Teacher.getNameTeachers(idTeachers);//all_nameTeacher[a_index];
        idGroups =  all_idGroups[a_index];
        groupSize = Group.getGroupsSize(idGroups);//all_groupSize[a_index];
        nameGroup = Group.getNameGroups(idGroups);//all_nameGroup[a_index];
        linkedWithIdLesson = all_linkedWithIdLesson[a_index];
        auditoryType = all_auditoryType[a_index];
    }
    

    public Gene newGeneInternal() {
        try {
            return new Lesson(getConfiguration(), Start.MAX_LESSONS - 1);
        } catch (InvalidConfigurationException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    public void setAllele(Object a_index) {
      indexLesson = (Integer)a_index;
      idLesson = all_idLessons[indexLesson];
      nameLesson = all_nameLesson[indexLesson];
      periodicity = all_periodicity[indexLesson];
      auditoriesNeed = all_auditoriesNeed[indexLesson];
      idTeachers = all_idTeachers[indexLesson];
      nameTeacher = Teacher.getNameTeachers(idTeachers);//all_nameTeacher[indexLesson];
      idGroups = all_idGroups[indexLesson];
      groupSize = Group.getGroupsSize(idGroups);//all_groupSize[indexLesson];
      nameGroup = Group.getNameGroups(idGroups);//all_nameGroup[indexLesson];
      linkedWithIdLesson = all_linkedWithIdLesson[indexLesson];
      auditoryType = all_auditoryType[indexLesson];
    }

    public Object getAllele() {
        return indexLesson;
    }

    public String getPersistentRepresentation() throws UnsupportedOperationException {
        return null;
    }

    public void setValueFromPersistentRepresentation(String string) throws UnsupportedOperationException,
                                                                           UnsupportedRepresentationException {
    }

    public void setToRandomValue(RandomGenerator a_randomGenerator) {
      indexLesson = new Integer(a_randomGenerator.nextInt(Start.MAX_LESSONS));
      setAllele(indexLesson);
//      idLesson = all_idLessons[indexLesson];
//      nameLesson = all_nameLesson[indexLesson];
//      periodicity = all_periodicity[indexLesson];
//      auditoriesNeed = all_auditoriesNeed[indexLesson];
//      idTeachers = all_idTeachers[indexLesson];
//      nameTeacher = all_nameTeacher[indexLesson];
//      idGroups = all_idGroups[indexLesson];
//      groupSize = all_groupSize[indexLesson];
//      nameGroup = all_nameGroup[indexLesson];        
    }

    public void applyMutation(int a_index, double a_precentage) {
        setAllele(getConfiguration().getRandomGenerator().nextInt(Start.MAX_LESSONS));
    }
    
    public int hashCode(){
      return indexLesson;
    }
    
    public Object getInternalValue(){
      return indexLesson;
    }

    public int compareTo(Object a_otherLesson) {
      if (a_otherLesson == null)
          return 1;

      if (indexLesson == null) {

          if (((Lesson)a_otherLesson).indexLesson== null)
              return 0;
          else
              return -1;
      }
      return indexLesson.compareTo(((Lesson)a_otherLesson).indexLesson);
    }
    
    public boolean equals(Object a_otherLesson) {
      return a_otherLesson instanceof Lesson &&
          compareTo(a_otherLesson) == 0;
  }
    //-------new methods---------

    protected static void setAll_idLessons(Integer a_idLessons, Integer a_index) {
        Lesson.all_idLessons[a_index] = a_idLessons;
    }

    protected static void setAll_nameLesson(String a_nameLesson, Integer a_index) {
        Lesson.all_nameLesson[a_index] = a_nameLesson;
    }

    protected static void setAll_periodicity(Integer a_periodicity, Integer a_index) {
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

    protected static void setAll_linkedWithIdLesson(Integer a_linkedWithIdLesson, Integer a_index) {
      Lesson.all_linkedWithIdLesson[a_index] = a_linkedWithIdLesson;
    }
    
    protected static void setAll_auditoryType(Integer a_auditoryType, Integer a_index) {
       Lesson.all_auditoryType[a_index] = a_auditoryType;
    }

    protected void show(){
      System.out.println("idLesson:" + idLesson + " name:" + nameLesson 
                         + " periodicity:"+periodicity + " auditories:" + auditoriesNeed);
      
      for (String s : nameTeacher) {
            if(s != null) 
                System.out.println("idTeacher:" + s);
        }

      for (String s : nameGroup) {
            if(s != null) 
                System.out.println("groupName: " + s );
        }
      for (Integer i : groupSize) {
            if(i != null)
                System.out.println(" size:" + i);
        }

    }

    protected Integer getIdLesson() {
        return idLesson;
    }

    protected Integer[] getIdTeachers() {
        return idTeachers;
    }

    protected Integer[] getIdGroups() {
        return idGroups;
    }

    protected Integer getTotalGroupSize() {
        Integer totalGroupSize = 0;
        for (int i = 0; i < MAX_GROUPS; i++) {
            if(groupSize[i] != null)
                totalGroupSize += groupSize[i];
        }
        return totalGroupSize;
    }

    protected Integer getAuditoriesNeed() {
        return auditoriesNeed;
    }

    protected Integer getPeriodicity() {
        return periodicity;
    }

    protected Integer getLinkedWithIdLesson() {
        return linkedWithIdLesson;
    }

    protected String getNameLesson() {
        return nameLesson;
    }

    protected String[] getNameTeacher() {
        return nameTeacher;
    }

    protected String[] getNameGroup() {
        return nameGroup;
    }

    protected Integer getAuditoryType() {
        return auditoryType;
    }

}
