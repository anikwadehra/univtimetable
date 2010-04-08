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
    private static final int MAX_EVOLUTIONS = 10000;
    private static final String fileName = "D:\\population.xml";
    private static final int POPULATION_SIZE = 100;
    private static final double THRESHOLD = 1;
    protected  static final int CHROMOSOME_SIZE = 4;


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
        
        //Creating genotype
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
            GroupClassTimeSupergene s =
                (GroupClassTimeSupergene)fittestChromosome.getGene(i);
            System.out.println("Gene " + i + " contains: " +
                               (Integer)s.geneAt(0).getAllele() + " " +
                               (Integer)s.geneAt(1).getAllele() + " " +
                               (Integer)s.geneAt(2).getAllele());
        }
        //Display the best solution
        //Start.displayChromosome(fittestChromosome);
        OutputData od = new OutputData();
        od.printToConsole(fittestChromosome);
        
        //Write population to the disk
        try {
            //savePopulation(population, fileName);
          od.printToFile(population, fileName);
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

    private static void displayChromosome(Chromosome a_bestChromosome) {
        GroupClassTimeSupergene[] s =
            new GroupClassTimeSupergene[CHROMOSOME_SIZE];
      // first - Group, second - Time
        String[][] str = new String[2][2];
        for (int i = 0; i < CHROMOSOME_SIZE; i++) {
          s[i] = (GroupClassTimeSupergene)a_bestChromosome.getGene(i);
          // fill square 00
          if( (Integer)s[i].geneAt(GROUP).getAllele() == 0 &&
              (Integer)s[i].geneAt(TIME).getAllele() == 0
          ){
            if( (Integer)s[i].geneAt(CLASS).getAllele() == 0 ) 
              str[0][0] = "a";
            else
              str[0][0] = "b";
           }
         // fill square 01
         if( (Integer)s[i].geneAt(GROUP).getAllele() == 0 &&
             (Integer)s[i].geneAt(TIME).getAllele() == 1
         ){
           if( (Integer)s[i].geneAt(CLASS).getAllele() == 0 ) 
             str[0][1] = "a";
           else
             str[0][1] = "b";
          }
      // fill square 10
      if( (Integer)s[i].geneAt(GROUP).getAllele() == 1 &&
          (Integer)s[i].geneAt(TIME).getAllele() == 0
      ){
        if( (Integer)s[i].geneAt(CLASS).getAllele() == 0 ) 
          str[1][0] = "a";
        else
          str[1][0] = "b";
       }
          
      // fill square 11
      if( (Integer)s[i].geneAt(GROUP).getAllele() == 1 &&
          (Integer)s[i].geneAt(TIME).getAllele() == 1
      ){
        if( (Integer)s[i].geneAt(CLASS).getAllele() == 0 ) 
          str[1][1] = "a";
        else
          str[1][1] = "b";
       } 
    }
    // print str[][]
        System.out.println("------------------------------");
        System.out.println("     I    II");
        System.out.println("1    "+str[0][0]+"    "+str[1][0]);
        System.out.println("2    "+str[0][1]+"    "+str[1][1]);
  }

}

