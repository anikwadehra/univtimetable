package ua.kiev.univ.timetable;

import java.io.Serializable;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IGeneConstraintChecker;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.impl.IntegerGene;


public class Time extends IntegerGene implements Gene, Serializable{
    
    private Integer indexTime;
    
    private Integer idTimeslot;
    private static Integer[] all_idTimeslots = new Integer[Start.MAX_TIME];
    
    private String nameTimeslot;
    private static String[] all_nameTimeslot = new String[Start.MAX_TIME];
    
    public Time(Configuration a_conf, Integer a_index) throws InvalidConfigurationException {
        super(a_conf);
        indexTime = a_index;
        idTimeslot = all_idTimeslots[a_index];
        nameTimeslot = all_nameTimeslot[a_index];
    }

  public Gene newGeneInternal() {
      try {
          return new Time(getConfiguration(), Start.MAX_TIME - 1);
      } catch (InvalidConfigurationException ex) {
          throw new IllegalStateException(ex.getMessage());
      }
  }

    public void setAllele(Object a_index) {
        indexTime = (Integer)a_index;
        idTimeslot = all_idTimeslots[indexTime];
        nameTimeslot = all_nameTimeslot[indexTime];        
    }

    public Object getAllele() {
        return indexTime;
    }

    public String getPersistentRepresentation() throws UnsupportedOperationException {
        return null;
    }

    public void setValueFromPersistentRepresentation(String string) throws UnsupportedOperationException,
                                                                           UnsupportedRepresentationException {
    }

    public void setToRandomValue(RandomGenerator a_randomGenerator) {
      indexTime = new Integer(a_randomGenerator.nextInt(Start.MAX_TIME));
      setAllele(indexTime);
    }

    public void applyMutation(int i, double d) {
        setAllele(getConfiguration().getRandomGenerator().nextInt(Start.MAX_TIME));
    }
    
    public int hashCode(){
      return indexTime;
    }
  
    public Object getInternalValue(){
      return indexTime;
    }

    public int compareTo(Object a_otherTime) {
      if (a_otherTime == null)
          return 1;

      if (indexTime == null) {

          if (((Time)a_otherTime).indexTime == null)
              return 0;
          else
              return -1;
      }
      return indexTime.compareTo(((Time)a_otherTime).indexTime);
    }
    
    public boolean equals(Object a_otherTime) {
      return a_otherTime instanceof Time &&
          compareTo(a_otherTime) == 0;
    }
    //----New methods-------

    protected static void setAll_idTimeslots(Integer a_idTimeslots, Integer a_index) {
        Time.all_idTimeslots[a_index] = a_idTimeslots;
    }

    protected static void setAll_nameTimeslot(String a_nameTimeslot, Integer a_index) {
        Time.all_nameTimeslot[a_index] = a_nameTimeslot;
    }
    
    protected void show(){
      System.out.println("idTimeslot:" + idTimeslot
                         +" nameTimeslot: " + nameTimeslot
                         );
    }

    protected Integer getIdTimeslot() {
        return idTimeslot;
    }
}
