package ua.kiev.univ.timetable;

import java.io.Serializable;

import java.util.StringTokenizer;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.impl.IntegerGene;

public class TimeGene extends IntegerGene implements Gene, Serializable{
    private static final String TOKEN_SEPARATOR = ":";
    //private Integer m_groupId;
    private int max_idTimeSlot = 3;
    private Integer idTimeSlot;

    public TimeGene(Configuration a_conf,
                     int a_idTimeSlot) throws InvalidConfigurationException {
        super(a_conf);
        if (a_idTimeSlot < 0) {
            throw new IllegalArgumentException("Time slot id must be non-negative!");
        }
        idTimeSlot = a_idTimeSlot;
    }


    @Override
    public Gene newGeneInternal() {
        try {
            return new TimeGene(getConfiguration(), max_idTimeSlot);
        } catch (InvalidConfigurationException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    public void setAllele(Object a_idTimeSlot) {
        idTimeSlot = (Integer)a_idTimeSlot;
    }


    public Object getAllele() {
        return idTimeSlot;
    }

    @Override
    public void setToRandomValue(RandomGenerator a_randomGenerator) {
        idTimeSlot = new Integer(a_randomGenerator.nextInt(max_idTimeSlot));
    }


    @Override
    public String getPersistentRepresentation()
        /* throws UnsupportedRepresentationException */ {
        return new Integer(max_idTimeSlot).toString() + TOKEN_SEPARATOR +
            idTimeSlot.toString();
    }


    @Override
    public void setValueFromPersistentRepresentation(String a_representation) throws UnsupportedRepresentationException,
                                                                                     UnsupportedOperationException {
        StringTokenizer tokenizer =
            new StringTokenizer(a_representation, TOKEN_SEPARATOR);

        if (tokenizer.countTokens() != 2)
            throw new UnsupportedRepresentationException("Unknown representation format: Two tokens expected!");
        try {
            max_idTimeSlot = Integer.parseInt(tokenizer.nextToken());
            idTimeSlot = new Integer(tokenizer.nextToken());
        } catch (NumberFormatException ex) {
            throw new UnsupportedRepresentationException("Unknown representation format: Expecting integer values!");
        }
    }


    @Override
    public int compareTo(Object a_otherTimeGene) {
        if (a_otherTimeGene == null)
            return 1;

        if (idTimeSlot == null) {

            if (((TimeGene)a_otherTimeGene).idTimeSlot == null)
                return 0;
            else
                return -1;
        }

        return idTimeSlot.compareTo(((TimeGene)a_otherTimeGene).idTimeSlot);
    }

    @Override
    public boolean equals(Object a_otherTimeGene) {
        return a_otherTimeGene instanceof TimeGene &&
            compareTo(a_otherTimeGene) == 0;
    }

    @Override
    public int hashCode() {
        return idTimeSlot;
    }


    @Override
    public Object getInternalValue() {
        return idTimeSlot;
    }

    @Override
    public void applyMutation(int index, double a_percentage) {
        setAllele(getConfiguration().getRandomGenerator().nextInt(max_idTimeSlot));
    }

  public void setMax_idTimeSlot(int max_idTimeSlot) {
    this.max_idTimeSlot = max_idTimeSlot;
  }

  public int getMax_idTimeSlot() {
    return max_idTimeSlot;
  }
}
