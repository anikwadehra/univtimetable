package ua.kiev.univ.timetable;

import java.io.Serializable;

import java.util.StringTokenizer;

import org.jgap.*;
import org.jgap.impl.IntegerGene;

public class ClassGene extends IntegerGene implements Gene, Serializable {
    private static final String TOKEN_SEPARATOR = ":";
    //private Integer m_groupId;
    //private int max_idClass = 3;
    private int max_classSize = 30;
    private static Integer max_idClass; // Setup in InputData.readFromFile
    private Integer idClass;
    private Integer classSize;

    private static Integer[] inputClassSize = new Integer[100];

    public ClassGene(Configuration a_conf,
                     Integer a_idClass) throws InvalidConfigurationException {
        super(a_conf);
        idClass = a_idClass;
        classSize = inputClassSize[idClass];
    }


    @Override
    public Gene newGeneInternal() {
        try {
            return new ClassGene(getConfiguration(), max_idClass);
        } catch (InvalidConfigurationException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    public void setAllele(Object a_idClass) {
        idClass = (Integer)a_idClass;
        classSize = inputClassSize[idClass];
    }


    public Object getAllele() {
        return idClass;
    }

    @Override
    public void setToRandomValue(RandomGenerator a_randomGenerator) {
        idClass = new Integer(a_randomGenerator.nextInt(max_idClass));
        classSize = inputClassSize[idClass];
    }


    @Override
    public String getPersistentRepresentation()
        /* throws UnsupportedRepresentationException */ {
        return new Integer(max_idClass).toString() + TOKEN_SEPARATOR +
            idClass.toString() + "//" + classSize.toString();
        //TODO: undo to Integer(max_idClass).toString() + TOKEN_SEPARATOR + idClass.toString()
    }


    @Override
    public void setValueFromPersistentRepresentation(String a_representation) throws UnsupportedRepresentationException,
                                                                                     UnsupportedOperationException {
        StringTokenizer tokenizer =
            new StringTokenizer(a_representation, TOKEN_SEPARATOR);

        if (tokenizer.countTokens() != 2)
            throw new UnsupportedRepresentationException("Unknown representation format: Two tokens expected!");
        try {
            max_idClass = Integer.parseInt(tokenizer.nextToken());
            idClass = new Integer(tokenizer.nextToken());
        } catch (NumberFormatException ex) {
            throw new UnsupportedRepresentationException("Unknown representation format: Expecting integer values!");
        }
    }


    @Override
    public int compareTo(Object a_otherClassGene) {
        if (a_otherClassGene == null)
            return 1;

        if (idClass == null) {

            if (((ClassGene)a_otherClassGene).idClass == null)
                return 0;
            else
                return -1;
        }

        return idClass.compareTo(((ClassGene)a_otherClassGene).idClass);
    }

    @Override
    public boolean equals(Object a_otherGroupGene) {
        return a_otherGroupGene instanceof ClassGene &&
            compareTo(a_otherGroupGene) == 0;
    }

    @Override
    public int hashCode() {
        return idClass;
    }


    @Override
    public Object getInternalValue() {
        return idClass;
    }

    @Override
    public void applyMutation(int index, double a_percentage) {
        setAllele(getConfiguration().getRandomGenerator().nextInt(max_idClass));
    }

    public Integer getClassSize() {
        return classSize;
    }

    public static void setMax_idClass(Integer max_idClass) {
        ClassGene.max_idClass = max_idClass;
    }

    public static void setInputClassSize(Integer a_inputClassSize,
                                         int a_index) {
        ClassGene.inputClassSize[a_index] = a_inputClassSize;
    }
}
