package ua.kiev.univ.timetable;

import java.io.Serializable;

import org.jgap.*;
import org.jgap.impl.*;

import java.util.StringTokenizer;

public class GroupGene extends IntegerGene implements Gene, Serializable {
    private static final String TOKEN_SEPARATOR = ":";
    //private Integer m_groupId;
    private int max_idGroup  = 2;
    private int max_groupSize = 30;
    private Integer idGroup;
    private Integer groupSize;
    
    public GroupGene(Configuration a_conf,
                     int a_idGroup, int a_groupSize) throws InvalidConfigurationException {
        super(a_conf);
        if (a_idGroup < 0 ) {
            throw new IllegalArgumentException("Group id must be non-negative!");
        }
        if (a_groupSize < 0 ) {
            throw new IllegalArgumentException("Group size must be non-negative!");
        }
        idGroup = a_idGroup;
        groupSize = a_groupSize;
    }


    @Override
    public Gene newGeneInternal() {
        try {
            return new GroupGene(getConfiguration(), max_idGroup, max_groupSize);
        } catch (InvalidConfigurationException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    public void setAllele(Object a_idGroup) {
        idGroup = (Integer)a_idGroup;
    }


    public Object getAllele() {
        return idGroup;
    }

    @Override
    public void setToRandomValue(RandomGenerator a_randomGenerator) {
        idGroup = new Integer(a_randomGenerator.nextInt(max_idGroup));
    }


    @Override
    public String getPersistentRepresentation() 
                    /* throws UnsupportedRepresentationException */ {
        return new Integer(max_idGroup).toString() + TOKEN_SEPARATOR +
            idGroup.toString();
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
            max_idGroup = Integer.parseInt(tokenizer.nextToken() );
            idGroup   = new Integer( tokenizer.nextToken() );
        }
        catch( NumberFormatException ex){
            throw new UnsupportedRepresentationException
                      ("Unknown representation format: Expecting integer values!");
        }
    }


    @Override
    public int compareTo(Object a_otherGroupGene) {
        if(a_otherGroupGene == null)
            return 1;
        
        if(idGroup == null){
            
            if( ((GroupGene) a_otherGroupGene).idGroup == null )
                return 0;
            else
                return -1;
        }
        
        return idGroup.compareTo(((GroupGene) a_otherGroupGene).idGroup);
    }

    @Override
    public boolean equals(Object a_otherGroupGene) {
        return a_otherGroupGene instanceof GroupGene 
            && compareTo(a_otherGroupGene) == 0;
    }

    @Override
    public int hashCode() {
        return idGroup;
    }


    @Override
    public Object getInternalValue() {
        return idGroup;
    }

    @Override
    public void applyMutation(int index, double a_percentage) {
//        System.out.println("GroupGene applyMutation invoked");
        setAllele(getConfiguration().getRandomGenerator().nextInt(max_idGroup));
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    public Integer getGroupSize() {
        return groupSize;
    }
}
