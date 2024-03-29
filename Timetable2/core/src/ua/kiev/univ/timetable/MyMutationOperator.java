package ua.kiev.univ.timetable;

import java.util.List;

import java.util.Vector;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.ICompositeGene;
import org.jgap.IGeneticOperatorConstraint;
import org.jgap.IUniversalRateCalculator;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.RandomGenerator;
import org.jgap.impl.MutationOperator;
import org.jgap.data.config.*;

public class MyMutationOperator extends MutationOperator {


    public MyMutationOperator(Configuration configuration,
                              int i) throws InvalidConfigurationException {
        super(configuration, i);
    }

    public MyMutationOperator(Configuration configuration,
                              IUniversalRateCalculator iUniversalRateCalculator) throws InvalidConfigurationException {
        super(configuration, iUniversalRateCalculator);
    }

    public MyMutationOperator(Configuration configuration) throws InvalidConfigurationException {
        super(configuration);
    }

    public MyMutationOperator() throws InvalidConfigurationException {
        super();
    }


    @Override
    public void operate(Population a_population, List a_candidateChromosomes) {

        Integer m_mutationRate = super.getMutationRate();
        IUniversalRateCalculator m_mutationRateCalc =
            super.getMutationRateCalc();

        if (a_population == null || a_candidateChromosomes == null) {
            // Population or candidate chromosomes list empty:
            // nothing to do.
            // -----------------------------------------------
            return;
        }
        if (m_mutationRate == 0 && m_mutationRateCalc == null) {
            // If the mutation rate is set to zero and dynamic mutation rate is
            // disabled, then we don't perform any mutation.
            // ----------------------------------------------------------------
            return;
        }
        // Determine the mutation rate. If dynamic rate is enabled, then
        // calculate it using the IUniversalRateCalculator instance.
        // Otherwise, go with the mutation rate set upon construction.
        // -------------------------------------------------------------
        boolean mutate = false;
        RandomGenerator generator = getConfiguration().getRandomGenerator();
        // It would be inefficient to create copies of each Chromosome just
        // to decide whether to mutate them. Instead, we only make a copy
        // once we've positively decided to perform a mutation.
        // ----------------------------------------------------------------
        int size =
            Math.min(getConfiguration().getPopulationSize(), a_population.size());
        IGeneticOperatorConstraint constraint =
            getConfiguration().getJGAPFactory().getGeneticOperatorConstraint();
        for (int i = 0; i < size; i++) {
            IChromosome chrom = a_population.getChromosome(i);
            Gene[] genes = chrom.getGenes();
            IChromosome copyOfChromosome = null;
            // For each Chromosome in the population...
            // ----------------------------------------
            for (int j = 0; j < genes.length; j++) {
                if (m_mutationRateCalc != null) {
                    // If it's a dynamic mutation rate then let the calculator decide
                    // whether the current gene should be mutated.
                    // --------------------------------------------------------------
                    mutate = m_mutationRateCalc.toBePermutated(chrom, j);
                } else {
                    // Non-dynamic, so just mutate based on the the current rate.
                    // In fact we use a rate of 1/m_mutationRate.
                    // ----------------------------------------------------------
                    mutate = (generator.nextInt(m_mutationRate) == 0);
                }
                if (mutate) {
                    // Verify that crossover allowed.
                    // ------------------------------
                    /**@todo move to base class, refactor*/
                    if (constraint != null) {
                        List v = new Vector();
                        v.add(chrom);
                        if (!constraint.isValid(a_population, v, this)) {
                            continue;
                        }
                    }
                    // Now that we want to actually modify the Chromosome,
                    // let's make a copy of it (if we haven't already) and
                    // add it to the candidate chromosomes so that it will
                    // be considered for natural selection during the next
                    // phase of evolution. Then we'll set the gene's value
                    // to a random value as the implementation of our
                    // "mutation" of the gene.
                    // ---------------------------------------------------
                    if (copyOfChromosome == null) {
                        // ...take a copy of it...
                        // -----------------------
                        copyOfChromosome = (IChromosome)chrom.clone();
                        // ...add it to the candidate pool...
                        // ----------------------------------
                        a_candidateChromosomes.add(copyOfChromosome);
                        // ...then mutate all its genes...
                        // -------------------------------
                        genes = copyOfChromosome.getGenes();
                    }
                    // Process all atomic elements in the gene. For a StringGene this
                    // would be as many elements as the string is long , for an
                    // IntegerGene, it is always one element.
                    // --------------------------------------------------------------
                    if (genes[j] instanceof ICompositeGene) {
                        ICompositeGene compositeGene = (ICompositeGene)genes[j];
                        Lesson l1 = (Lesson)compositeGene.geneAt(Start.LESSON);
                        for (int k = 0; k < compositeGene.size(); k++) {
                            if (k == Start.TIME) {
                                do {

                                    mutateGene(compositeGene.geneAt(k), generator);
                                    if((      l1.getFixedPair() == null  
                                           || l1.getFixedPair() == ((Time)compositeGene.geneAt(Start.TIME)).getIdTimeslot() % 4
                                          )
                                       &&
                                          (   l1.getFixedDay() == null  
                                           || l1.getFixedDay() == ((Time)compositeGene.geneAt(Start.TIME)).getIdTimeslot() / 4
                                          ) 
                                        )
                                        break;
                                } while ( true );
                                //System.out.println("Time mutation!");
                            } else {
                                mutateGene(compositeGene.geneAt(k), generator);
                            }
                        }
                    }
                    /**@todo isValid for super genes*/
                    //          else if (genes[j] instanceof org.jgap.supergenes.Supergene) {
                    //            org.jgap.supergenes.Supergene superGene = (org.jgap.supergenes.Supergene) genes[j];
                    //            for (int k = 0; k < superGene.size(); k++) {
                    //              mutateSupergene(superGene.geneAt(k), generator);
                    //            }
                    //          }
                    else {
                        mutateGene(genes[j], generator);
                    }
                }
            }
        }
    }

    private void mutateGene(final Gene a_gene,
                            final RandomGenerator a_generator) {
        for (int k = 0; k < a_gene.size(); k++) {
            // Retrieve value between 0 and 1 (not included) from generator.
            // Then map this value to range -1 and 1 (-1 included, 1 not).
            // -------------------------------------------------------------
            double percentage = -1 + a_generator.nextDouble() * 2;
            // Mutate atomic element by calculated percentage.
            // -----------------------------------------------
            a_gene.applyMutation(k, percentage);
        }
    }

}
