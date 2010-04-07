package ua.kiev.univ.timetable;


import com.sun.org.apache.xml.internal.serialize.*;
import com.sun.org.apache.xml.internal.serialize.DOMSerializer;
import com.sun.org.apache.xml.internal.serialize.Serializer;
import com.sun.org.apache.xml.internal.serialize.SerializerFactory;
import com.sun.org.apache.xml.internal.serializer.Method;

import java.io.*;

import org.jgap.*;
import org.jgap.event.*;
import org.jgap.impl.*;
import org.jgap.xml.*;

import org.w3c.dom.*;


public class Start {
    protected static final int GROUP = 0;
    protected static final int CLASS = 1;
    protected static final int TIME  = 2;
    private static final int MAX_EVOLUTIONS = 1000;
    private static final String fileName = "E:\\population.xml";
    private static final int POPULATION_SIZE = 10;
    private static final double THRESHOLD = 20;
    protected static final int CHROMOSOME_SIZE = 4;


    public static void main(String[] args) throws InvalidConfigurationException {

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
                    new GroupClassTimeSupergene(conf, new Gene[] { new GroupGene(conf,1,30),
                                                                   new ClassGene(conf,1,15),
                                                                   new TimeGene(conf,1) });
        }


        //Creating chromosome
        Chromosome testChromosome;
        testChromosome = new Chromosome(conf, testGenes);
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
        
        //for (int i = 0; i < 3; i++) {
        //System.out.println("testSuperGenes[" + i + "].getAllele(): " +
        //                 testSuperGenes[i].getAllele());
        //System.out.println("testClassGenes[" + i + "].getAllele(): " +
        //                   testClassGenes[i].getAllele());
        //}

        //Creating genotype
        Genotype population = Genotype.randomInitialGenotype(conf);

        System.out.println("Our Chromosome: \n " +
                           testChromosome.getConfiguration().toString());

        System.out.println("------------evolution-----------------------------");
        // Begin evolution
        int percent_evolution = MAX_EVOLUTIONS / 100;
        for (int i = 0; i < MAX_EVOLUTIONS; i++) {
            if( i % percent_evolution == 0 ) System.out.println(i/percent_evolution+"% done");
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
            GroupClassTimeSupergene s =
                (GroupClassTimeSupergene)fittestChromosome.getGene(i);
            System.out.println("Gene " + i + " contains: " +
                               (Integer)s.geneAt(0).getAllele() + " " +
                               (Integer)s.geneAt(1).getAllele() + " " +
                               (Integer)s.geneAt(2).getAllele());
        }
        //Display the best solution
        //DisplayChromosome(fittestChromosome);

        //Write population to the disk
        try {
            savePopulation(population, fileName);
        } catch (IOException e) {
            System.out.println("IOException raised! " + e.getMessage());
        }

    }

    private static void savePopulation(Genotype a_currentPopulation,
                                       String a_saveToFilename) throws IOException {
        //Convert Genotype to a DOM object
        Document xmlRepresentation =
            XMLManager.representGenotypeAsDocument(a_currentPopulation);

        Writer documentWriter = new FileWriter(a_saveToFilename);

        OutputFormat formatting =
            new OutputFormat(xmlRepresentation, "UTF-8", true);
        SerializerFactory factory =
            SerializerFactory.getSerializerFactory(Method.XML);
        Serializer genericSerializer =
            factory.makeSerializer(documentWriter, formatting);
        DOMSerializer documentSerializer = genericSerializer.asDOMSerializer();

        documentSerializer.serialize(xmlRepresentation);
        documentWriter.close();
    }

    private static void DisplayChromosome(Chromosome a_bestChromosome) {
        GroupClassTimeSupergene[] s =
            new GroupClassTimeSupergene[CHROMOSOME_SIZE];
        String[][][] str = new String[CHROMOSOME_SIZE][2][2]; // second - Time, third - Group
        for (int i = 0; i < CHROMOSOME_SIZE; i++) {
            s[i] = (GroupClassTimeSupergene)a_bestChromosome.getGene(i);
            
            if ( (Integer)s[i].geneAt(TIME).getAllele() == 0 ){
                
                           
                if ( (Integer)s[i].geneAt(GROUP).getAllele() == 0 ) {
                    System.out.println("00"+(Integer)s[i].geneAt(CLASS).getAllele());
                    //str[i][0][0] = (String)s[i].geneAt(CLASS).getAllele();
                }
                else if( (Integer)s[i].geneAt(CLASS).getAllele()!= null ){
                    System.out.println("01"+(Integer)s[i].geneAt(CLASS).getAllele());
                    //str[i][0][1] = (String)s[i].geneAt(CLASS).getAllele();
                }
            }
            else if( (Integer)s[i].geneAt(TIME).getAllele() != null ){
                
                if ( (Integer)s[i].geneAt(GROUP).getAllele() == 0 ) {
                    System.out.println("10"+(Integer)s[i].geneAt(CLASS).getAllele());
                    //str[i][1][0] = (String)s[i].geneAt(CLASS).getAllele();
                }
                else{
                    System.out.println("11"+(Integer)s[i].geneAt(CLASS).getAllele());
                }
                
            }
        }

    }

}

