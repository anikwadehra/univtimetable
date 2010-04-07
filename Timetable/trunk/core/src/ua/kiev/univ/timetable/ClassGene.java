package ua.kiev.univ.timetable;

import java.io.Serializable;

import java.util.StringTokenizer;

import org.jgap.*;
import org.jgap.impl.IntegerGene;

public class ClassGene extends IntegerGene implements Gene, Serializable {
    private static final String TOKEN_SEPARATOR = ":";
    //private Integer m_groupId;
    private int max_idClass = 2;
    private int max_classSize = 30;
    private Integer idClass;
    private Integer classSize;

    public ClassGene(Configuration a_conf,
                     int a_idClass, int a_classSize) throws InvalidConfigurationException {
        super(a_conf);
        if (a_idClass < 0) {
            throw new IllegalArgumentException("Class id must be non-negative!");
        }
        if (a_classSize < 0) {
            throw new IllegalArgumentException("Class size must be non-negative!");
        }
        classSize = a_idClass;
        idClass = a_idClass;
    }


    @Override
    public Gene newGeneInternal() {
        try {
            return new ClassGene(getConfiguration(), max_idClass, max_classSize);
        } catch (InvalidConfigurationException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    public void setAllele(Object a_idClass) {
        idClass = (Integer)a_idClass;
    }


    public Object getAllele() {
        return idClass;
    }

    @Override
    public void setToRandomValue(RandomGenerator a_randomGenerator) {
        idClass = new Integer(a_randomGenerator.nextInt(max_idClass));
    }


    @Override
    public String getPersistentRepresentation() 
                    /* throws UnsupportedRepresentationException */ {
        return new Integer(max_idClass).toString() + TOKEN_SEPARATOR +
            idClass.toString();
    }


    @Override
    public void setValueFromPersistentRepresentation(String a_representation) 
                    throws UnsupportedRepresentationException, 
                           UnsupportedOperationException {
        StringTokenizer tokenizer = new StringTokenizer(a_representation, TOKEN_SEPARATOR);
        
        if(tokenizer.countTokens() != 2)
            throw new UnsupportedRepresentationException
                    ("Unknown representation format: Two tokens expected!");
        try{
            max_idClass = Integer.parseInt(tokenizer.nextToken() );
            idClass   = new Integer( tokenizer.nextToken() );
        }
        catch( NumberFormatException ex){
            throw new UnsupportedRepresentationException
                      ("Unknown representation format: Expecting integer values!");
        }
    }


    @Override
    public int compareTo(Object a_otherClassGene) {
        if(a_otherClassGene == null)
            return 1;
        
        if(idClass == null){
            
            if( ((ClassGene) a_otherClassGene).idClass == null )
                return 0;
            else
                return -1;
        }
        
        return idClass.compareTo(((ClassGene) a_otherClassGene).idClass);
    }

    @Override
    public boolean equals(Object a_otherGroupGene) {
        return a_otherGroupGene instanceof ClassGene 
            && compareTo(a_otherGroupGene) == 0;
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

    public void setClassSize(Integer classSize) {
        this.classSize = classSize;
    }

    public Integer getClassSize() {
        return classSize;
    }
}
