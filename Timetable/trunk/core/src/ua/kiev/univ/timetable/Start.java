package ua.kiev.univ.timetable;


import com.sun.org.apache.xml.internal.serialize.*;
import com.sun.org.apache.xml.internal.serialize.DOMSerializer;
import com.sun.org.apache.xml.internal.serialize.Serializer;
import com.sun.org.apache.xml.internal.serialize.SerializerFactory;
import com.sun.org.apache.xml.internal.serializer.Method;

import java.io.*;

import javax.xml.parsers.ParserConfigurationException;

import org.jgap.*;
import org.jgap.event.*;
import org.jgap.impl.*;
import org.jgap.xml.*;

import org.w3c.dom.*;

import org.xml.sax.SAXException;


public class Start {
    protected static final int GROUP = 0;
    protected static final int CLASS = 1;
    protected static final int TIME  = 2;
    protected static int MAX_EVOLUTIONS;
    private static final String GENOTYPE_FILENAME = "E:\\population.xml";
    private static final String BEST_CHROMOSOME_FILENAME = "E:\\best_chromosome.xml";
    private static final String XML_TEST_FILENAME = "E:\\inputTimetable.xml";
    protected static int POPULATION_SIZE;
    protected static double THRESHOLD;
    protected  static Integer CHROMOSOME_SIZE;


    public static void main(String[] args) throws InvalidConfigurationException {
        
        // Reading data from xml
        try {
            new InputData().readFromFile(XML_TEST_FILENAME);
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }
        
        //Configuration conf = new DefaultConfiguration();
        Configuration conf = new Configuration("myconf");
        TimetableFitnessFunction fitnessFunction =
            new TimetableFitnessFunction();
        InitialConstraintChecker timetableConstraintChecker =
            new InitialConstraintChecker();
        
        //Creating genes
        Gene[] testGenes = new Gene[CHROMOSOME_SIZE];
        for (int i = 0; i < CHROMOSOME_SIZE; i++) {
            testGenes[i] =
                    new GroupClassTeacherLessonTimeSG(conf, new Gene[] { new GroupGene(conf,1),
                                                                   new ClassGene(conf,1),
                                                                   new TimeGene(conf,1) });
        }
        System.out.println("==================================");
        //Creating chromosome
        Chromosome testChromosome;
        testChromosome = new Chromosome(conf, testGenes );
        testChromosome.setConstraintChecker(timetableConstraintChecker);
        //Setup configuration
        conf.setSampleChromosome(testChromosome);
        conf.setPopulationSize(POPULATION_SIZE);
        conf.setFitnessFunction(fitnessFunction); // add fitness function

        BestChromosomesSelector myBestChromosomesSelector =
            new BestChromosomesSelector(conf);
        conf.addNaturalSelector(myBestChromosomesSelector, false);

        conf.setRandomGenerator(new StockRandomGenerator());
        conf.setEventManager(new EventManager());
        conf.setFitnessEvaluator(new DefaultFitnessEvaluator());

        CrossoverOperator myCrossoverOperator = 
            new CrossoverOperator(conf);
        conf.addGeneticOperator(myCrossoverOperator);
        
        TimetableMutationOperator myMutationOperator =
            new TimetableMutationOperator(conf);
        conf.addGeneticOperator(myMutationOperator);
        
        conf.setKeepPopulationSizeConstant(false);
        
        //Creating genotype
//        Population pop = new Population(conf, testChromosome);
//        Genotype population = new Genotype(conf, pop);
        Genotype population = Genotype.randomInitialGenotype(conf);

        System.out.println("Our Chromosome: \n " +
                           testChromosome.getConfiguration().toString());
        
        System.out.println("------------evolution-----------------------------");
       
       
        // Begin evolution
        for (int i = 0; i < MAX_EVOLUTIONS; i++) {
            System.out.println("generation#: "+i+" population size:"+
                               (Integer)population.getPopulation().size());
            if (population.getFittestChromosome().getFitnessValue() >=
                THRESHOLD)
                break;
            population.evolve();
        }

        
        
        System.out.println("--------------end of evolution--------------------");
        Chromosome fittestChromosome =
            (Chromosome)population.getFittestChromosome();
        System.out.println("-------------The best chromosome---fitness=" +
                           fittestChromosome.getFitnessValue() + "---");
        System.out.println("                Group Class Time");
        for (int i = 0; i < CHROMOSOME_SIZE; i++) {
            GroupClassTeacherLessonTimeSG s =
                (GroupClassTeacherLessonTimeSG)fittestChromosome.getGene(i);
            System.out.println("Gene " + i + " contains: " +
                               (Integer)s.geneAt(0).getAllele() + " " +
                               (Integer)s.geneAt(1).getAllele() + " " +
                               (Integer)s.geneAt(2).getAllele());
        //GroupGene gg = (GroupGene)s.geneAt(GROUP);
        //System.out.println("gg's idGroup"+gg.getAllele()+" gg.getGroupSize()"+ gg.getGroupSize() );
        }
        
        
        //Display the best solution

        OutputData od = new OutputData();
        od.printToConsole(fittestChromosome);
        
        //Write population to the disk
        try {
          od.printToFile(population, GENOTYPE_FILENAME, BEST_CHROMOSOME_FILENAME);
        } catch (IOException e) {
            System.out.println("IOException raised! " + e.getMessage());
        }

    }

}

