package ua.kiev.univ.timetable;

import java.io.Serializable;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IGeneConstraintChecker;
import org.jgap.InvalidConfigurationException;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.impl.IntegerGene;

public class Auditory extends IntegerGene implements Serializable, org.jgap.Gene {
    private Integer indexAuditory;
    
    private Integer idAuditory;
    private static Integer[] all_idAuditory = new Integer[Start.MAX_AUDITORIES];
    
    private Integer auditorySize;
    private static Integer[] all_auditorySize = new Integer[Start.MAX_AUDITORIES];
    
    private String  nameAuditory;
    private static String[] all_nameAuditory = new String[Start.MAX_AUDITORIES];
    
    private Integer auditoryType;
    private static Integer[] all_auditoryType = new Integer[Start.MAX_AUDITORIES];    
    
    public Auditory(Configuration a_conf, Integer a_index) throws InvalidConfigurationException {
        super(a_conf);
        indexAuditory = a_index;
        idAuditory = all_idAuditory[a_index];
        auditorySize = all_auditorySize[a_index];
        nameAuditory = all_nameAuditory[a_index];
        auditoryType = all_auditoryType[a_index];
    }    

  public Gene newGeneInternal() {
      try {
          return new Auditory(getConfiguration(), Start.MAX_AUDITORIES - 1);
      } catch (InvalidConfigurationException ex) {
          throw new IllegalStateException(ex.getMessage());
      }
  }

    public void setAllele(Object a_index) {
      indexAuditory = (Integer)a_index;
      idAuditory = all_idAuditory[indexAuditory];
      auditorySize = all_auditorySize[indexAuditory];
      nameAuditory = all_nameAuditory[indexAuditory];
      auditoryType = all_auditoryType[indexAuditory];
    }

    public Object getAllele() {
        return indexAuditory;
    }

    public String getPersistentRepresentation() throws UnsupportedOperationException {
        return null;
    }

    public void setValueFromPersistentRepresentation(String string) throws UnsupportedOperationException,
                                                                           UnsupportedRepresentationException {
    }

    public void setToRandomValue(RandomGenerator a_randomGenerator) {
        indexAuditory = new Integer(a_randomGenerator.nextInt(Start.MAX_AUDITORIES));
        setAllele(indexAuditory);
    }

    public void applyMutation(int a_index, double a_precentage) {
        Integer index;
        //---auditoryType of the new Auditory after mutation must be equal 
        //---to the prior's Auditory auditoryType
        do {
            index = getConfiguration().getRandomGenerator().nextInt(Start.MAX_AUDITORIES);
        } while ( all_auditoryType[index] != auditoryType);
        
        setAllele(index);
    }
    
    public int hashCode(){
      return indexAuditory;
    }
  
    public Object getInternalValue(){
      return indexAuditory;
    }

    public int compareTo(Object a_otherAuditory) {
      if (a_otherAuditory == null)
          return 1;

      if (indexAuditory == null) {

          if (((Auditory)a_otherAuditory).indexAuditory == null)
              return 0;
          else
              return -1;
      }
      return indexAuditory.compareTo(((Auditory)a_otherAuditory).indexAuditory);
    }
    
    public boolean equals(Object a_otherAuditory) {
      return a_otherAuditory instanceof Auditory &&
          compareTo(a_otherAuditory) == 0;
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
  
    protected static void setAll_auditoryType(Integer a_auditoryType, Integer a_index) {
        Auditory.all_auditoryType[a_index] = a_auditoryType;
    }
    
    protected void show(){
            System.out.println("idAuditory:" + idAuditory
                               +" auditorySize:" + auditorySize
                               +" nameAuditory: " + nameAuditory
                               +" auditoryType: " + auditoryType
                               );
    }

    protected Integer getIdAuditory() {
        return idAuditory;
    }

    protected Integer getAuditorySize() {
        return auditorySize;
    }

    protected String getNameAuditory() {
        return nameAuditory;
    }

    protected Integer getAuditoryType() {
        return auditoryType;
    }

    protected static Integer getAll_auditoryType(Integer a_index) {
        return all_auditoryType[a_index];
    }
}
