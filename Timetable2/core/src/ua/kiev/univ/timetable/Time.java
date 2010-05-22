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
    
    private Integer idTimeslot;
    private static Integer[] all_idTimeslots = new Integer[Start.MAX_TIME];
    
    private String nameTimeslot;
    private static String[] all_nameTimeslot = new String[Start.MAX_TIME];
    
    public Time(Configuration a_conf, Integer a_index) throws InvalidConfigurationException {
        super(a_conf);
        idTimeslot = all_idTimeslots[a_index];
        nameTimeslot = all_nameTimeslot[a_index];
        System.out.println("Instance of Time created!");
    }

    public Gene newGene() {
        return null;
    }

    public void setAllele(Object object) {
    }

    public Object getAllele() {
        return null;
    }

    public String getPersistentRepresentation() throws UnsupportedOperationException {
        return null;
    }

    public void setValueFromPersistentRepresentation(String string) throws UnsupportedOperationException,
                                                                           UnsupportedRepresentationException {
    }

    public void setToRandomValue(RandomGenerator randomGenerator) {
    }

    public void cleanup() {
    }

    public int size() {
        return 0;
    }

    public void applyMutation(int i, double d) {
    }

    public void setApplicationData(Object object) {
    }

    public Object getApplicationData() {
        return null;
    }

    public void setCompareApplicationData(boolean b) {
    }

    public boolean isCompareApplicationData() {
        return false;
    }

    public double getEnergy() {
        return 0.0;
    }

    public void setEnergy(double d) {
    }

    public void setConstraintChecker(IGeneConstraintChecker iGeneConstraintChecker) {
    }

    public Configuration getConfiguration() {
        return null;
    }

    public int compareTo(Object o) {
        return 0;
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
}
