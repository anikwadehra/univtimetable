package ua.kiev.univ.timetable;

import java.io.Serializable;

import org.jgap.*;
import org.jgap.impl.*;

import java.util.StringTokenizer;

public class TeacherGene extends IntegerGene implements Gene, Serializable {

    private static final String TOKEN_SEPARATOR = ":";
    
    private Integer teacherNumber;
    private Integer idTeacher;
    private String name;
    // Array of lessons that the teacher can teach
    private Integer[] avaliableLessons = new Integer[Start.MAX_NUMBER_OF_LESSONS];
    // Array of avaliable timeslots for the teacher
    private Integer[] avaliableTimeSlots = new Integer[40];
    //TODO: remove all this magic numbers into CONSTANTS

    private static Integer[] idTeachers = new Integer[Start.MAX_NUMBER_OF_TEACHERS];
    private static String[] all_names = new String[Start.MAX_NUMBER_OF_TEACHERS];
    private static Integer max_teacherNumber = Start.MAX_NUMBER_OF_TEACHERS;
    
    // avaliableLessons[idTeacher][all_avaliableLessons]
    private static Integer[][] all_avaliableLessons = new Integer[Start.MAX_NUMBER_OF_TEACHERS][Start.MAX_NUMBER_OF_LESSONS];
    // avaliableTimeSlots[idTeacher][all_avaliableTimeSlots]
    private static Integer[][] all_avaliableTimeSlots = new Integer[Start.MAX_NUMBER_OF_TEACHERS][40];

    public TeacherGene(Configuration a_conf,
                       Integer a_TeacherNumber) throws InvalidConfigurationException {
        super(a_conf);
        teacherNumber = a_TeacherNumber;
        idTeacher = idTeachers[teacherNumber];
        name = all_names[teacherNumber];
        avaliableLessons = all_avaliableLessons[teacherNumber];
        avaliableTimeSlots = all_avaliableTimeSlots[teacherNumber];
    }

    @Override
    public Gene newGeneInternal() {
        try {
            return new TeacherGene(getConfiguration(), max_teacherNumber-1);
        } catch (InvalidConfigurationException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void setAllele(Object a_TeacherNumber) {
        teacherNumber = (Integer)a_TeacherNumber;
        idTeacher = idTeachers[ (Integer)a_TeacherNumber ];
        name = all_names[ (Integer)a_TeacherNumber ];
        avaliableLessons = all_avaliableLessons[(Integer)a_TeacherNumber];
        avaliableTimeSlots = all_avaliableTimeSlots[(Integer)a_TeacherNumber];
    }

    public Object getAllele() {
        return teacherNumber;
    }

    public String getPersistentRepresentation() throws UnsupportedOperationException {
        return max_teacherNumber.toString() + TOKEN_SEPARATOR +
            teacherNumber.toString();
    }

    public void setValueFromPersistentRepresentation(String a_representation) throws UnsupportedOperationException,
                                                                                     UnsupportedRepresentationException {
        StringTokenizer tokenizer =
            new StringTokenizer(a_representation, TOKEN_SEPARATOR);

        if (tokenizer.countTokens() != 2)
            throw new UnsupportedRepresentationException("Unknown representation format: Two tokens expected!");
        try {
            max_teacherNumber = Integer.parseInt(tokenizer.nextToken());
            teacherNumber = new Integer(tokenizer.nextToken());
        } catch (NumberFormatException ex) {
            throw new UnsupportedRepresentationException("Unknown representation format: Expecting integer values!");
        }
    }

    @Override
    public void setToRandomValue(RandomGenerator a_randomGenerator) {
        teacherNumber = new Integer(a_randomGenerator.nextInt(max_teacherNumber));
        idTeacher = idTeachers[teacherNumber];
        name = all_names[teacherNumber];
        avaliableLessons = all_avaliableLessons[teacherNumber];
        avaliableTimeSlots = all_avaliableTimeSlots[teacherNumber];
    }


    @Override
    public void applyMutation(int a_index, double a_percentage) {
        setAllele(getConfiguration().getRandomGenerator().nextInt(max_teacherNumber));
    }

    @Override
    public int compareTo(Object a_otherTeacherGene) {
        if (a_otherTeacherGene == null)
            return 1;

        if (teacherNumber == null) {

            if (((TeacherGene)a_otherTeacherGene).teacherNumber == null)
                return 0;
            else
                return -1;
        }
        return teacherNumber.compareTo(((TeacherGene)a_otherTeacherGene).teacherNumber);
    }

    @Override
    public boolean equals(Object a_otherTeacherGene) {
        return a_otherTeacherGene instanceof TeacherGene &&
            compareTo(a_otherTeacherGene) == 0;
    }

    @Override
    public int hashCode() {
        return teacherNumber;
    }


    @Override
    public Object getInternalValue() {
        return teacherNumber;
    }


    public Integer[] getAvaliableLessons() {
        return avaliableLessons;
    }

    public Integer[] getAvaliableTimeSlots() {
        return avaliableTimeSlots;
    }

    public static void setAll_avaliableLessons(Integer[] a_avaliableLessons,
                                               Integer a_index) {
        TeacherGene.all_avaliableLessons[a_index] = a_avaliableLessons;
    }

    public static void setAll_avaliableTimeSlots(Integer[] a_avaliableTimeSlots,
                                                 Integer a_index) {
        TeacherGene.all_avaliableTimeSlots[a_index] = a_avaliableTimeSlots;
    }

    public static void setIdTeachers(Integer a_idTeacher, int a_index) {
        idTeachers[a_index] = a_idTeacher;
    }

    public static void setAll_names(String a_name, int a_index) {
        all_names[a_index] = a_name;
    }

    public Integer getIdTeacher() {
        return idTeacher;
    }

    public String getName() {
        return name;
    }
}
