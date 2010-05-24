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

    public LessonAuditoryTimeSG(final Configuration a_conf) throws InvalidConfigurationException {
        super(a_conf);
    }

    public LessonAuditoryTimeSG(final Configuration a_conf,
                                         Gene[] a_genes) throws InvalidConfigurationException {
        super(a_conf, a_genes);
    }

    public boolean isValid(Gene[] a_genes, Supergene a_supergene) {
        return true;
    }

}
