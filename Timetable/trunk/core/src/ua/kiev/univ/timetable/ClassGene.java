package ua.kiev.univ.timetable;

import java.io.Serializable;

import java.util.StringTokenizer;

import org.jgap.*;
import org.jgap.impl.IntegerGene;

public class ClassGene extends IntegerGene implements Gene, Serializable {
    private static final String TOKEN_SEPARATOR = ":";
    
    private Integer idClass;
    private Integer classNumber;
    private Integer classSize;
    
    private static Integer[] idClasses = new Integer[Start.MAX_NUMBER_OF_CLASSES];
    private static Integer max_ClassNumber = Start.MAX_NUMBER_OF_CLASSES;
    private static Integer[] allClassSize = new Integer[100];
    
    public ClassGene(Configuration a_conf,
                     Integer a_classNumber) throws InvalidConfigurationException {
        super(a_conf);
        classNumber = a_classNumber;
        idClass = idClasses[a_classNumber];
        classSize = allClassSize[classNumber];
    }


    @Override
    public Gene newGeneInternal() {
        try {
            return new ClassGene(getConfiguration(), max_ClassNumber-1);
        } catch (InvalidConfigurationException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    public void setAllele(Object a_classNumber) {
        classNumber = (Integer)a_classNumber;
        idClass = idClasses[classNumber];
        classSize = allClassSize[classNumber];
    }


    public Object getAllele() {
        return classNumber;
    }

    @Override
    public void setToRandomValue(RandomGenerator a_randomGenerator) {
        classNumber = new Integer(a_randomGenerator.nextInt(max_ClassNumber));
        idClass = idClasses[classNumber];
        classSize = allClassSize[classNumber];
    }


    @Override
    public String getPersistentRepresentation()
        /* throws UnsupportedRepresentationException */ {
        return new Integer(max_ClassNumber).toString() + TOKEN_SEPARATOR +
            classNumber.toString();
    }


    @Override
    public void setValueFromPersistentRepresentation(String a_representation) throws UnsupportedRepresentationException,
                                                                                     UnsupportedOperationException {
        StringTokenizer tokenizer =
            new StringTokenizer(a_representation, TOKEN_SEPARATOR);

        if (tokenizer.countTokens() != 2)
            throw new UnsupportedRepresentationException("Unknown representation format: Two tokens expected!");
        try {
            max_ClassNumber = Integer.parseInt(tokenizer.nextToken());
            classNumber = new Integer(tokenizer.nextToken());
        } catch (NumberFormatException ex) {
            throw new UnsupportedRepresentationException("Unknown representation format: Expecting integer values!");
        }
    }


    @Override
    public int compareTo(Object a_otherClassGene) {
        if (a_otherClassGene == null)
            return 1;

        if (classNumber == null) {

            if (((ClassGene)a_otherClassGene).classNumber == null)
                return 0;
            else
                return -1;
        }

        return classNumber.compareTo(((ClassGene)a_otherClassGene).classNumber);
    }

    @Override
    public boolean equals(Object a_otherGroupGene) {
        return a_otherGroupGene instanceof ClassGene &&
            compareTo(a_otherGroupGene) == 0;
    }

    @Override
    public int hashCode() {
        return classNumber;
    }


    @Override
    public Object getInternalValue() {
        return classNumber;
    }

    @Override
    public void applyMutation(int index, double a_percentage) {
        setAllele(getConfiguration().getRandomGenerator().nextInt(max_ClassNumber));
    }

    public Integer getClassSize() {
        return classSize;
    }


    public static void setAllClassSize(Integer a_inputClassSize,
                                         int a_index) {
        ClassGene.allClassSize[a_index] = a_inputClassSize;
    }

    public static void setIdClasses(Integer a_idClass, int a_index) {
        idClasses[a_index] = a_idClass;
    }

    public Integer getIdClass() {
        return idClass;
    }
}
