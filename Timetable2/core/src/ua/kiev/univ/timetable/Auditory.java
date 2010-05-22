package ua.kiev.univ.timetable;

import java.io.Serializable;

import org.jgap.Configuration;
import org.jgap.IGeneConstraintChecker;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.impl.IntegerGene;

public class Auditory extends IntegerGene implements Serializable, org.jgap.Gene {
    private Integer idAuditory;
    private static Integer[] all_idAuditory = new Integer[Start.MAX_AUDITORIES];
    
    private Integer auditorySize;
    private static Integer[] all_auditorySize = new Integer[Start.MAX_AUDITORIES];
    
    private String  nameAuditory;
    private static String[] all_nameAuditory = new String[Start.MAX_AUDITORIES];
    
    
    public Auditory(Configuration a_conf, Integer a_index) throws InvalidConfigurationException {
        super(a_conf);
        idAuditory = all_idAuditory[a_index];
        auditorySize = all_auditorySize[a_index];
        nameAuditory = all_nameAuditory[a_index];
        
        System.out.println("Instance of Auditory created!");
    }    
    


    public org.jgap.Gene newGene() {
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
    
    //--------New methods----

    protected static void setAll_idAuditory(Integer a_idAuditory, Integer a_index) {
        Auditory.all_idAuditory[a_index] = a_idAuditory;
    }

    protected static void setAll_auditorySize(Integer a_auditorySize, Integer a_index) {
        Auditory.all_auditorySize[a_index] = a_auditorySize;
    }

    protected static void setAll_nameAuditory(String a_nameAuditory, Integer a_index) {
        Auditory.all_nameAuditory[a_index] = a_nameAuditory;
    }
    
    protected void show(){
            System.out.println("idAuditory:" + idAuditory
                               +" auditorySize:" + auditorySize
                               +" nameAuditory: " + nameAuditory
                               );
    }
}
