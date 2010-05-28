package ua.kiev.univ.timetable;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.StockRandomGenerator;
import org.jgap.impl.ThresholdSelector;

import org.xml.sax.SAXException;


public class Start {

    static Integer MAX_LESSONS;//maximum number of squares in timetable
    static Integer MAX_AUDITORIES;
    static Integer MAX_TIME;
    static Integer MAX_GROUPS;
    
    protected static final int LESSON   = 0;
    protected static final int AUDITORY = 1;
    protected static final int TIME     = 2;
    
    private static final String INPUT_LESSONS_FILENAME =
        "E:\\inputTimetable_new design.xml";
        //"E:\\inputTimetable_new real design.xml";
    protected static final String INPUT_DICTIONARY_FILENAME = "E:\\dictionary.xml";
        //"E:\\dictionary.xml";
    private static final String OUTPUT_XML_FILE = "E:\\output.xml";
    
    
    private static Integer POPULATION_SIZE = 50;
    private static double THRESHOLD        = 1;
    private static Integer MAX_EVOLUTION   = 100;
    static Integer CHROMOSOME_SIZE;// = MAX_LESSONS; //this is number of cells in the table where you can put lessons

    public static void main(String[] args) throws InvalidConfigurationException {
        
        //-----reading input data from XML file
        try {
            InputData.readFromFile(INPUT_LESSONS_FILENAME, INPUT_DICTIONARY_FILENAME);
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }
        //----------------------------------------------
        
        Configuration conf = new Configuration("myconf");
//        TimetableFitnessFunction myFitnessFunction = 
//            new TimetableFitnessFunction();
//        LessonAuditoryTimeSG[] myGenes = new LessonAuditoryTimeSG[CHROMOSOME_SIZE];
//        //Auditory[] auditories
//        for (int i = 0; i < CHROMOSOME_SIZE; i++) {
//            myGenes[i] = new LessonAuditoryTimeSG(conf, new Gene[]{new Lesson(conf,1),
//                                                                   new Auditory(conf,1),
//                                                                   new Time(conf,1)
//                                                                  }
//                                                  );
//        }
//        //----Creating chromosome---------------------
//        //-------------chromosome size = MAX_LESSONS
//        Chromosome  myChromosome = new Chromosome(conf, myGenes);
//        //----Setup configuration--begin--------------
//        conf.setSampleChromosome(myChromosome);
//        conf.setPopulationSize(POPULATION_SIZE);
//        conf.setFitnessFunction(myFitnessFunction);
//        
//        ThresholdSelector myBestChromosomesSelector =
//           new ThresholdSelector(conf,0.3);
//        conf.addNaturalSelector(myBestChromosomesSelector, false);
//
//        conf.setRandomGenerator(new StockRandomGenerator());
//        conf.setEventManager(new EventManager());
//        conf.setFitnessEvaluator(new DefaultFitnessEvaluator());
//
//        CrossoverOperator myCrossoverOperator = 
//            new CrossoverOperator(conf);
//        conf.addGeneticOperator(myCrossoverOperator);
//      
//        MutationOperator myMutationOperator =
//            new MutationOperator(conf);
//        conf.addGeneticOperator(myMutationOperator);
//      
//        conf.setKeepPopulationSizeConstant(false);
//        
//        Genotype myGenotype = Genotype.randomInitialGenotype(conf);
//        //------Setup configuration end-------------
//        
//        //------Evolution---------------------------
//        for (int i = 0; i < MAX_EVOLUTION; i++) {
//            myGenotype.evolve();
//            System.out.println("generation " + i + " fitness=" + (Double)myGenotype.getFittestChromosome().getFitnessValue());
//            if(myGenotype.getFittestChromosome().getFitnessValue() >= THRESHOLD)
//                break;
//        }
//        
////        ---Printing the best chromosome - the most appreciate timetable
//        System.out.println("CHROMOSOME_SIZE:" + CHROMOSOME_SIZE);
//        Chromosome bestChromosome = (Chromosome)myGenotype.getFittestChromosome();
//        for (int i = 0; i < CHROMOSOME_SIZE; i++) {
//            LessonAuditoryTimeSG bestGene = (LessonAuditoryTimeSG)bestChromosome.getGene(i);
//            
//            Lesson   l = (Lesson)bestGene.geneAt(LESSON);
//            Auditory a = (Auditory)bestGene.geneAt(AUDITORY);
//            Time     t = (Time)bestGene.geneAt(TIME);
//            
//            System.out.println(" lesson id:" + l.getIdLesson() //+ " total group size:" + l.getTotalGroupSize() 
//                               +" at time id:" + t.getNameTimeslot() 
//                               + " in auditory id:" + a.getIdAuditory()// + " auditory size:" + a.getAuditorySize()
//                               );
//        }
     
     
     //----------------------------------------------   
        //---write solution to the file
        //OutputData.writeToFile(bestChromosome, OUTPUT_XML_FILE);
        
        Lesson[] l = new Lesson[MAX_LESSONS];
        for (int i = 0; i < MAX_LESSONS; i++) {
            l[i] = new Lesson(conf, i);
            l[i].show();
        }
        
        Auditory[] a = new Auditory[MAX_AUDITORIES];
        for (int i = 0; i < MAX_AUDITORIES; i++) {
            a[i] = new Auditory(conf, i);
            a[i].show();
        }
        
        Time[] t = new Time[MAX_TIME];
        for (int i = 0; i < MAX_TIME; i++) {
            t[i] = new Time(conf,i);
            t[i].show();
        }

    }


    protected static void setMAX_LESSONS(Integer MAX_LESSONS) {
        Start.MAX_LESSONS = MAX_LESSONS;
    }

    protected static void setMAX_AUDITORIES(Integer MAX_AUDITORIES) {
        Start.MAX_AUDITORIES = MAX_AUDITORIES;
    }

    protected static void setMAX_TIME(Integer MAX_TIME) {
        Start.MAX_TIME = MAX_TIME;
    }

    protected static void setMAX_GROUPS(Integer MAX_GROUPS) {
        Start.MAX_GROUPS = MAX_GROUPS;
    }
}
