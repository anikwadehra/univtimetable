package ua.kiev.univ.timetable;

import java.io.Serializable;

import org.jgap.*;
import org.jgap.impl.*;

import java.util.StringTokenizer;

public class LessonGene extends IntegerGene implements Gene, Serializable {

  private static final String TOKEN_SEPARATOR = ":";
  
  private Integer idLesson; // id of one particular LesonGene
  private static Integer[] idLessons = new Integer[Start.MAX_NUMBER_OF_LESSONS]; // array of lessons id, setup from the inputTimetable.xml
  
  private Integer lessonNumber;
  private static Integer max_numberOfLessons = Start.MAX_NUMBER_OF_LESSONS; // max number of lessons

  private String name; // Name of a lesson
  private static String[] all_names = new String[Start.MAX_NUMBER_OF_LESSONS]; //Store all names of lessons
  
  public LessonGene(Configuration a_conf,
                    Integer a_LessonNumber) throws InvalidConfigurationException {
    super(a_conf);
    lessonNumber = a_LessonNumber;
    idLesson = idLessons[lessonNumber];
    name = all_names[a_LessonNumber];
  }


  @Override
  public Gene newGeneInternal() {
    try {
      return new LessonGene(getConfiguration(), max_numberOfLessons-1);
    } catch (InvalidConfigurationException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  public void setAllele(Object a_LessonNumber) {
    lessonNumber = (Integer)a_LessonNumber;
    idLesson = idLessons[lessonNumber];
    name = all_names[ (Integer)a_LessonNumber ];
  }

  public Object getAllele() {
    return lessonNumber;
  }

  @Override
  public String getPersistentRepresentation() throws UnsupportedOperationException {
    return max_numberOfLessons.toString() + TOKEN_SEPARATOR + idLesson.toString();
  }

  @Override
  public void setValueFromPersistentRepresentation(String a_representation) throws UnsupportedOperationException,
                                                                                   UnsupportedRepresentationException {
    StringTokenizer tokenizer =
      new StringTokenizer(a_representation, TOKEN_SEPARATOR);

    if (tokenizer.countTokens() != 2)
      throw new UnsupportedRepresentationException("Unknown representation format: Two tokens expected!");
    try {
      max_numberOfLessons = Integer.parseInt(tokenizer.nextToken());
      idLesson = new Integer(tokenizer.nextToken());
    } catch (NumberFormatException ex) {
      throw new UnsupportedRepresentationException("Unknown representation format: Expecting integer values!");
    }
  }

  @Override
  public void setToRandomValue(RandomGenerator a_randomGenerator) {
    lessonNumber = new Integer(a_randomGenerator.nextInt(max_numberOfLessons));
    idLesson = idLessons[ lessonNumber ];
    name = all_names[lessonNumber];
  }

  @Override
  public void applyMutation(int a_index, double a_percentage) {
    setAllele( getConfiguration().getRandomGenerator().nextInt(max_numberOfLessons) );
  }
  
  public int compareTo(Object a_otherLessonGene) {
    if (a_otherLessonGene == null)
      return 1;

    if (lessonNumber == null) {

      if (((LessonGene)a_otherLessonGene).lessonNumber == null)
        return 0;
      else
        return -1;
    }
    return lessonNumber.compareTo(((LessonGene)a_otherLessonGene).lessonNumber);
  }

  @Override
  public boolean equals(Object a_otherLessonGene) {
    return a_otherLessonGene instanceof LessonGene &&
      compareTo(a_otherLessonGene) == 0;
  }
  
  @Override
  public int hashCode() {
      return lessonNumber;
  }


  @Override
  public Object getInternalValue() {
      return lessonNumber;
  }


//  public static void setMax_numberOfLessons(Integer a_maxLesson) {
//    LessonGene.max_numberOfLessons = a_maxLesson;
//  }

//  public static Integer getMax_numberOfLessons() {
//    return max_numberOfLessons;
//  }

    public static void setIdLessons(Integer a_idLessons, int a_index) {
        LessonGene.idLessons[a_index] = a_idLessons;
    }

    public Integer getIdLesson() {
        return idLesson;
    }

    public static void setAll_names(String a_all_names, int a_index) {
        LessonGene.all_names[a_index] = a_all_names;
    }

    public String getName() {
        return name;
    }
}


