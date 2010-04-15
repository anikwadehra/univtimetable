package ua.kiev.univ.timetable;

import java.io.Serializable;

import org.jgap.*;
import org.jgap.impl.*;

import java.util.StringTokenizer;

public class TeacherGene extends IntegerGene implements Gene, Serializable {

  private static final String TOKEN_SEPARATOR = ":";
  private Integer idTeacher;
  // Array of lessons that the teacher can teach
  private Integer[] avaliableLessons = new Integer[20];
  // Array of avaliable timeslots for the teacher
  private Integer[] avaliableTimeSlots = new Integer[40];
  //TODO: remove all this magic numbers into CONSTANTS

  private static Integer max_idTeacher;
  // avaliableLessons[idTeacher][all_avaliableLessons]
  private static Integer[][] all_avaliableLessons = new Integer[30][20];
  // avaliableTimeSlots[idTeacher][all_avaliableTimeSlots]
  private static Integer[][] all_avaliableTimeSlots = new Integer[30][40];

  public TeacherGene(Configuration a_conf,
                     Integer a_idTeacher) throws InvalidConfigurationException {
    super(a_conf);
    idTeacher = a_idTeacher;
    avaliableLessons = all_avaliableLessons[idTeacher];
    avaliableTimeSlots = all_avaliableTimeSlots[idTeacher];
  }

  @Override
  public Gene newGeneInternal() {
    try {
      return new TeacherGene(getConfiguration(), max_idTeacher);
    } catch (InvalidConfigurationException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  public void setAllele(Object a_idTeacher) {
    idTeacher = (Integer)a_idTeacher;
    avaliableLessons = all_avaliableLessons[(Integer)a_idTeacher];
    avaliableTimeSlots = all_avaliableTimeSlots[(Integer)a_idTeacher];
  }

  public Object getAllele() {
    return idTeacher;
  }

  public String getPersistentRepresentation() throws UnsupportedOperationException {
    return max_idTeacher.toString() + TOKEN_SEPARATOR + idTeacher.toString();
  }

  public void setValueFromPersistentRepresentation(String a_representation) throws UnsupportedOperationException,
                                                                                   UnsupportedRepresentationException {
    StringTokenizer tokenizer =
      new StringTokenizer(a_representation, TOKEN_SEPARATOR);

    if (tokenizer.countTokens() != 2)
      throw new UnsupportedRepresentationException("Unknown representation format: Two tokens expected!");
    try {
      max_idTeacher = Integer.parseInt(tokenizer.nextToken());
      idTeacher = new Integer(tokenizer.nextToken());
    } catch (NumberFormatException ex) {
      throw new UnsupportedRepresentationException("Unknown representation format: Expecting integer values!");
    }
  }

  @Override
  public void setToRandomValue(RandomGenerator a_randomGenerator) {
    idTeacher = new Integer(a_randomGenerator.nextInt(max_idTeacher));
    avaliableLessons = all_avaliableLessons[idTeacher];
    avaliableTimeSlots = all_avaliableTimeSlots[idTeacher];
  }


  @Override
  public void applyMutation(int a_index, double a_percentage) {
    setAllele(getConfiguration().getRandomGenerator().nextInt(max_idTeacher));
  }

  @Override
  public int compareTo(Object a_otherTeacherGene) {
    if (a_otherTeacherGene == null)
      return 1;

    if (idTeacher == null) {

      if (((TeacherGene)a_otherTeacherGene).idTeacher == null)
        return 0;
      else
        return -1;
    }
    return idTeacher.compareTo(((TeacherGene)a_otherTeacherGene).idTeacher);
  }

  @Override
  public boolean equals(Object a_otherTeacherGene) {
    return a_otherTeacherGene instanceof TeacherGene &&
      compareTo(a_otherTeacherGene) == 0; 
  }

  @Override
  public int hashCode() {
    return idTeacher;
  }


  @Override
  public Object getInternalValue() {
    return idTeacher;
  }


  public static void setMax_idTeacher(Integer a_max_idTeacher) {
    TeacherGene.max_idTeacher = a_max_idTeacher;
  }

  public Integer[] getAvaliableLessons() {
    return avaliableLessons;
  }

  public static void setAll_avaliableLessons(Integer[] a_avaliableLessons,
                                             Integer a_index) {
    TeacherGene.all_avaliableLessons[a_index] = a_avaliableLessons;
  }

  public static void setAll_avaliableTimeSlots(Integer[] a_avaliableTimeSlots,
                                               Integer a_index) {
    TeacherGene.all_avaliableTimeSlots[a_index] = a_avaliableTimeSlots;
  }
}
