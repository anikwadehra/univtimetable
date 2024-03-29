package ua.kiev.univ.timetable;

import java.io.IOException;

import java.util.Calendar;

import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.ICompositeGene;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
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
    static Integer MAX_TEACHERS;
    
    protected static final int LESSON   = 0;
    protected static final int AUDITORY = 1;
    protected static final int TIME     = 2;
    
    protected static final int EVEN     = 0;
    protected static final int ODD      = 1;
    protected static final int EVEN_ODD = 10;
    
    private static final String INPUT_LESSONS_FILENAME =
        "E:\\inputTimetable_new real design 3 days.xml";
        //"E:\\inputTimetable_new real design.xml";
    protected static final String INPUT_DICTIONARY_FILENAME = 
        "E:\\dictionary3 days.xml";
        //"E:\\dictionary.xml";
    private static final String OUTPUT_XML_FILE = "E:\\output.xml";
    
    
    private static Integer POPULATION_SIZE = 50;
    private static double THRESHOLD        = 1;
    private static Integer MAX_EVOLUTION   = 5000;
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
        TimetableFitnessFunction myFitnessFunction = 
            new TimetableFitnessFunction();
        
        LessonAuditoryTimeSG[] myGenes = new LessonAuditoryTimeSG[CHROMOSOME_SIZE];
        Random randomGenerator = new Random();
        Integer indexLesson = 0;
        Integer indexTime = 0 ;
        Integer indexAuditory = 0;
        Integer[] idLessons = Lesson.getAll_idLessons();

        for (int i = 0; i < MAX_LESSONS; i++) {
            //---In every gene Lesson.periodicity must be equal to Time.timeslotType
            do {
                indexTime = randomGenerator.nextInt(MAX_TIME);
                if(Lesson.getAll_periodicity(i) == Time.getAll_timeslotType(indexTime) 
                   &&
                    (Lesson.getAll_fixedDay(i) == null 
                     || Lesson.getAll_fixedDay(i) == Time.getAll_idTimeslots(indexTime) / 4 ) 
                   &&
                    (Lesson.getAll_fixedPair(i) == null 
                     || Lesson.getAll_fixedPair(i) == Time.getAll_idTimeslots(indexTime) % 4 )
                    )
                    break;
            } while (true);

            //---In every gene Lesson.auditoryType must be equal to Auditory.auditoryType
            do {
                indexAuditory = randomGenerator.nextInt(MAX_AUDITORIES);
            } while ( ! Auditory.getAll_auditoryType(indexAuditory).equals(Lesson.getAll_auditoryType(i)) );

            //---Every lesson from the input list must be initialized Lesson.auditories times
            for (int j = 0; j < Lesson.getAll_auditoriesNeed(i); j++) {
                myGenes[indexLesson] = new LessonAuditoryTimeSG(conf, new Gene[]{new Lesson(conf,i),
                                                                   new Auditory(conf,indexAuditory),
                                                                   new Time(conf,indexTime)
                                                                  }
                                                  );
                indexLesson++;
            }
        
        }
        //----Creating chromosome---------------------
        //-------------chromosome size = MAX_LESSONS
        Chromosome  myChromosome = new Chromosome(conf, myGenes);
        //----Setup configuration--begin--------------
        //myChromosome.setConstraintChecker(timetableConstraintChecker);
        conf.setSampleChromosome(myChromosome);
        conf.setPopulationSize(POPULATION_SIZE);
        conf.setFitnessFunction(myFitnessFunction);
        
        ThresholdSelector myBestChromosomesSelector =
           new ThresholdSelector(conf,0.4);
        conf.addNaturalSelector(myBestChromosomesSelector, false);

        conf.setRandomGenerator(new StockRandomGenerator());
        conf.setEventManager(new EventManager());
        conf.setFitnessEvaluator(new DefaultFitnessEvaluator());

        MyCrossoverOperator myCrossoverOperator = 
            new MyCrossoverOperator(conf);
        conf.addGeneticOperator(myCrossoverOperator);
      
        MyMutationOperator myMutationOperator =
            new MyMutationOperator(conf,80);
        conf.addGeneticOperator(myMutationOperator);
      
        conf.setKeepPopulationSizeConstant(false);
        
   
        //Genotype myGenotype = Genotype.randomInitialGenotype(conf);
        Population myPopulation = new Population(conf, myChromosome);
        Genotype myGenotype = new Genotype(conf, myPopulation);
        //------Setup configuration end-------------
        
        //------Evolution---------------------------
        Calendar cal = Calendar.getInstance();
        long start_t = cal.getTimeInMillis();
        Integer gen = 0;
        for (int i = 0; i < MAX_EVOLUTION; i++) {
            myGenotype.evolve();
            //System.out.println("generation " + i + " fitness=" + (Double)myGenotype.getFittestChromosome().getFitnessValue());
            System.out.println(i + "\t" + (Double)myGenotype.getFittestChromosome().getFitnessValue());
            gen++;
            if(myGenotype.getFittestChromosome().getFitnessValue() >= THRESHOLD)
                break;
        }
      cal = Calendar.getInstance();
      long finish_t = cal.getTimeInMillis();
              
        //---Printing the best chromosome - the most appreciate timetable
        System.out.println("CHROMOSOME_SIZE:" + CHROMOSOME_SIZE);
        Chromosome bestChromosome = (Chromosome)myGenotype.getFittestChromosome();
        for (int i = 0; i < CHROMOSOME_SIZE; i++) {
            LessonAuditoryTimeSG bestGene = (LessonAuditoryTimeSG)bestChromosome.getGene(i);
            
            Lesson   l = (Lesson)bestGene.geneAt(LESSON);
            Auditory a = (Auditory)bestGene.geneAt(AUDITORY);
            Time     t = (Time)bestGene.geneAt(TIME);
            
            System.out.println(" lesson:" + l.getNameLesson() //+ " total group size:" + l.getTotalGroupSize() 
                               +" at time:" + t.getNameTimeslot() 
                               + " in auditory:" + a.getNameAuditory()// + " auditory size:" + a.getAuditorySize()
                               );
            for (String s : l.getNameTeacher()) {
                if(s != null)
                    System.out.println("  teacher: " + s);
            }
            for (String s : l.getNameGroup()) {
                if(s != null)
                    System.out.println("  group: " + s);
            }
        }
      System.out.println( "Elapsed time:"+ 
                        (double)(finish_t - start_t)/1000 +"s");
      System.out.println("Total generations: " + gen);

     //----------------------------------------------   
        //---write solution to the file
        OutputData.writeToFile(bestChromosome, OUTPUT_XML_FILE);
        
//        Lesson[] l = new Lesson[MAX_LESSONS];
//        for (int i = 0; i < MAX_LESSONS; i++) {
//            l[i] = new Lesson(conf, i);
//            l[i].show();
//        }
//        
//        Auditory[] a = new Auditory[MAX_AUDITORIES];
//        for (int i = 0; i < MAX_AUDITORIES; i++) {
//            a[i] = new Auditory(conf, i);
//            a[i].show();
//        }
//        
//        Time[] t = new Time[MAX_TIME];
//        for (int i = 0; i < MAX_TIME; i++) {
//            t[i] = new Time(conf,i);
//            t[i].show();
//        }

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

    protected static void setMAX_TEACHERS(Integer MAX_TEACHERS) {
        Start.MAX_TEACHERS = MAX_TEACHERS;
    }
}
