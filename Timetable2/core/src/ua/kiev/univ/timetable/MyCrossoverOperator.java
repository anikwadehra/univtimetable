package ua.kiev.univ.timetable;

import java.util.*;

import org.jgap.*;
import org.jgap.impl.CrossoverOperator;

public class MyCrossoverOperator extends CrossoverOperator {
    public MyCrossoverOperator(Configuration configuration, double d,
                               boolean b, boolean b1) throws InvalidConfigurationException {
        super(configuration, d, b, b1);
    }

    public MyCrossoverOperator(Configuration configuration, double d,
                               boolean b) throws InvalidConfigurationException {
        super(configuration, d, b);
    }

    public MyCrossoverOperator(Configuration configuration, double d) throws InvalidConfigurationException {
        super(configuration, d);
    }

    public MyCrossoverOperator(Configuration configuration, int i, boolean b,
                               boolean b1) throws InvalidConfigurationException {
        super(configuration, i, b, b1);
    }

    public MyCrossoverOperator(Configuration configuration, int i, boolean b) throws InvalidConfigurationException {
        super(configuration, i, b);
    }

    public MyCrossoverOperator(Configuration configuration, int i) throws InvalidConfigurationException {
        super(configuration, i);
    }

    public MyCrossoverOperator(Configuration configuration,
                               IUniversalRateCalculator iUniversalRateCalculator,
                               boolean b) throws InvalidConfigurationException {
        super(configuration, iUniversalRateCalculator, b);
    }

    public MyCrossoverOperator(Configuration configuration,
                               IUniversalRateCalculator iUniversalRateCalculator) throws InvalidConfigurationException {
        super(configuration, iUniversalRateCalculator);
    }

    public MyCrossoverOperator(Configuration configuration) throws InvalidConfigurationException {
        super(configuration);
    }

    public MyCrossoverOperator() throws InvalidConfigurationException {
        super();
    }

    @Override
    protected void doCrossover(IChromosome firstMate,
                               IChromosome secondMate, List a_candidateChromosomes,
                               RandomGenerator generator) {
      Gene[] firstGenes = firstMate.getGenes();
          Gene[] secondGenes = secondMate.getGenes();
          int locus = generator.nextInt(firstGenes.length);
          // Swap the genes.
          // ---------------
          Gene gene1;
          Gene gene2;
          Object firstAllele;
          Integer timeslotType;
          for (int j = locus; j < firstGenes.length; j++) {
            // Make a distinction for ICompositeGene for the first gene.
            // ---------------------------------------------------------
            int index = 0;
            if (firstGenes[j] instanceof ICompositeGene) {
              // Randomly determine gene to be considered.
              // Lesson gene can't be taken. Auditory or Time genes only.
              // -----------------------------------------
              index = generator.nextInt(1) + 1;
              gene1 = ( (ICompositeGene) firstGenes[j]).geneAt(index);
            }
            else {
              gene1 = firstGenes[j];
            }
            // Make a distinction for the second gene if CompositeGene.
            // --------------------------------------------------------
            if (secondGenes[j] instanceof ICompositeGene) {
              gene2 = ( (ICompositeGene) secondGenes[j]).geneAt(index);
            }
            else {
              gene2 = secondGenes[j];
            }
              //---Make crossover if and only if two Time genes have equal timeslotTypes
              if( index == Start.TIME ){
                  if( ((Time)gene1).getAll_timeslotType((Integer)gene1.getAllele()) == 
                      ((Time)gene2).getAll_timeslotType((Integer)gene2.getAllele()) ){
                      
                   firstAllele = gene1.getAllele();
                   gene1.setAllele(gene2.getAllele());
                   gene2.setAllele(firstAllele); 
                  
                  }
              }
              //---Make crossover in the case of equal auditoryTypes only
              else if( index == Start.AUDITORY){
                  if( ((Auditory)gene1).getAll_auditoryType((Integer)gene1.getAllele()) ==
                      ((Auditory)gene2).getAll_auditoryType((Integer)gene2.getAllele())){
                   
                   firstAllele = gene1.getAllele();
                   gene1.setAllele(gene2.getAllele());
                   gene2.setAllele(firstAllele);  
                   
                   }
                   
              }
            
          }
          // Add the modified chromosomes to the candidate pool so that
          // they'll be considered for natural selection during the next
          // phase of evolution.
          // -----------------------------------------------------------
          a_candidateChromosomes.add(firstMate);
          a_candidateChromosomes.add(secondMate);

        
    }
}
