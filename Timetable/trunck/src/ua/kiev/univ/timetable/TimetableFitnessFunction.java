package ua.kiev.univ.timetable;

import java.lang.Math;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class TimetableFitnessFunction extends FitnessFunction {
    private static final int GROUP = 0;
    private static final int CLASS = 1;
    private static final int TIME  = 2;
    private static final double NO_FITNESS_VALUE = 9999999;
    private static final int CENTRAL_VALUE = 0;
    private Integer[] estimate = new Integer[Start.CHROMOSOME_SIZE];
    private double fitness;

    @Override
    protected double evaluate(IChromosome a_subject) {
        fitness = 0;

    GroupClassTimeSupergene[] s = new GroupClassTimeSupergene[Start.CHROMOSOME_SIZE];
    for (int i = 0; i < Start.CHROMOSOME_SIZE; i++) {
        s[i] = (GroupClassTimeSupergene)a_subject.getGene(i);
    }        

    //------------Checking hard constraints---------------------------- 
 
   
    for (int i = 0; i < Start.CHROMOSOME_SIZE; i++) {
        for (int j = 0; j < Start.CHROMOSOME_SIZE; j++) {
    
    //-----------Avoid one group be in the two classes at the same time        
            if( i!=j && s[i].geneAt(GROUP).equals(s[j].geneAt(GROUP))
                     //&& s[i].geneAt(CLASS).equals(s[j].geneAt(CLASS))
                     && s[i].geneAt(TIME).equals(s[j].geneAt(TIME) )
            ) {return 0;
                }
    //-----------Avoid one class being occuped more than one group at the same time
            else if( i!=j //&& s[i].geneAt(GROUP).equals(s[j].geneAt(GROUP))
                     && s[i].geneAt(CLASS).equals(s[j].geneAt(CLASS))
                     && s[i].geneAt(TIME).equals(s[j].geneAt(TIME) )
            ) {return 0;
                }
        }
    }
    
    
    

    //------------Checking soft constraints----------------------------
        
        for (int i = 0; i < Start.CHROMOSOME_SIZE; i++) {
            s[i] =
                (GroupClassTimeSupergene)a_subject.getGene(i);
            estimate[i]  = (Integer)s[i].geneAt(GROUP).getAllele();
            estimate[i] += (Integer)s[i].geneAt(CLASS).getAllele();
            estimate[i] += (Integer)s[i].geneAt(TIME).getAllele();
            fitness += estimate[i];
        }
        System.out.println("Fitness is: "+fitness);
        return fitness;
//        return 1 / (1 + Math.abs(CENTRAL_VALUE - fitness));

    }
}
