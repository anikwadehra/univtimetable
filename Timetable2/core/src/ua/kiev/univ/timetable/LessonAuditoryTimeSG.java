package ua.kiev.univ.timetable;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.supergenes.AbstractSupergene;
import org.jgap.supergenes.Supergene;

public class LessonAuditoryTimeSG extends AbstractSupergene {
    public LessonAuditoryTimeSG() throws InvalidConfigurationException {
        super();
    }

    public LessonAuditoryTimeSG(final Configuration a_conf,
                                Gene[] a_genes) throws InvalidConfigurationException {
        super(a_conf, a_genes);
    }

    public boolean isValid(Gene[] a_genes, Supergene a_supergene) {
        boolean valid = true;

//        Lesson l = (Lesson)a_genes[Start.LESSON];
//        Auditory a = (Auditory)a_genes[Start.AUDITORY];
//        Time t = (Time)a_genes[Start.TIME];
//
//        if (l.getAuditoriesNeed() != 0) {
//            if (l.getTotalGroupSize() / l.getAuditoriesNeed() >
//                a.getAuditorySize())
//                valid = false;
//        }
//
//        //-----lesson's periodicity (even or odd or even_odd) must be equal with timeslotType
//        if (l.getPeriodicity() != t.getTimeslotType()) {
//            valid = false;
//        }
//
//        //-----lesson must be assigened to the appropriate auditoryType
//        if (l.getAuditoryType() != a.getAuditoryType()) {
//            valid = false;
//        }
        
        return valid;
    }

}
