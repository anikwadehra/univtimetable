package ua.kiev.univ.timetable;

import java.io.Serializable;

import org.jgap.*;
import org.jgap.impl.*;

import java.util.StringTokenizer;

public class LessonGene extends IntegerGene implements Gene, Serializable {

  private static final String TOKEN_SEPARATOR = ":";
  private Integer idLesson;
  private static Integer max_idLesson;

  public LessonGene(Configuration a_conf,
                    Integer a_idLesson) throws InvalidConfigurationException {
    super(a_conf);
    idLesson = a_idLesson;
  }


  @Override
  public Gene newGene() {
    try {
      return new LessonGene(getConfiguration(), max_idLesson);
    } catch (InvalidConfigurationException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  public void setAllele(Object a_idLesson) {
    idLesson = (Integer)a_idLesson;
  }

  public Object getAllele() {
    return idLesson;
  }

  @Override
  public String getPersistentRepresentation() throws UnsupportedOperationException {
    return max_idLesson.toString() + TOKEN_SEPARATOR + idLesson.toString();
  }

  @Override
  public void setValueFromPersistentRepresentation(String a_representation) throws UnsupportedOperationException,
                                                                                   UnsupportedRepresentationException {
    StringTokenizer tokenizer =
      new StringTokenizer(a_representation, TOKEN_SEPARATOR);

    if (tokenizer.countTokens() != 2)
      throw new UnsupportedRepresentationException("Unknown representation format: Two tokens expected!");
    try {
      max_idLesson = Integer.parseInt(tokenizer.nextToken());
      idLesson = new Integer(tokenizer.nextToken());
    } catch (NumberFormatException ex) {
      throw new UnsupportedRepresentationException("Unknown representation format: Expecting integer values!");
    }
  }

  @Override
  public void setToRandomValue(RandomGenerator a_randomGenerator) {
    idLesson = new Integer(a_randomGenerator.nextInt(max_idLesson));
  }

  @Override
  public void applyMutation(int a_index, double a_percentage) {
    setAllele( getConfiguration().getRandomGenerator().nextInt(max_idLesson) );
  }
  
  public int compareTo(Object a_otherLessonGene) {
    if (a_otherLessonGene == null)
      return 1;

    if (idLesson == null) {

      if (((LessonGene)a_otherLessonGene).idLesson == null)
        return 0;
      else
        return -1;
    }
    return idLesson.compareTo(((LessonGene)a_otherLessonGene).idLesson);
  }

  @Override
  public boolean equals(Object a_otherLessonGene) {
    return a_otherLessonGene instanceof TeacherGene &&
      compareTo(a_otherLessonGene) == 0;
  }
  
  @Override
  public int hashCode() {
      return idLesson;
  }


  @Override
  public Object getInternalValue() {
      return idLesson;
  }


  public static void setMax_idLesson(Integer a_max_idLesson) {
    LessonGene.max_idLesson = a_max_idLesson;
  }

  public static Integer getMax_idLesson() {
    return max_idLesson;
  }
}


