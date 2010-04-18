package ua.kiev.univ.timetable;

import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.IGeneConstraintChecker;

public class InitialConstraintChecker implements IGeneConstraintChecker {
    private static int i = 0;
    public InitialConstraintChecker() {
        super();
    }

    public boolean verify(Gene a_gene, Object a_alleleValue,
                          IChromosome a_chromosome, int a_geneIndex) {
//        System.out.println("ConstraintChecker invoked "+i);
        return true;
    }
}
