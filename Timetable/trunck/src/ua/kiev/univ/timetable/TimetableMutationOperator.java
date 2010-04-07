package ua.kiev.univ.timetable;

import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.IUniversalRateCalculator;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.MutationOperator;

public class TimetableMutationOperator extends MutationOperator {
    public TimetableMutationOperator(Configuration configuration,
                                     int i) throws InvalidConfigurationException {
        super(configuration, i);
    }

    public TimetableMutationOperator(Configuration configuration,
                                     IUniversalRateCalculator iUniversalRateCalculator) throws InvalidConfigurationException {
        super(configuration, iUniversalRateCalculator);
    }

    public TimetableMutationOperator(Configuration configuration) throws InvalidConfigurationException {
        super(configuration);
    }

    public TimetableMutationOperator() throws InvalidConfigurationException {
        super();
    }

    @Override
    public void operate(Population a_population, List a_candidateChromosomes) {
        super.operate(a_population, a_candidateChromosomes);
        //System.out.println("TimetableMutationOperator invoked!");
    }
}
