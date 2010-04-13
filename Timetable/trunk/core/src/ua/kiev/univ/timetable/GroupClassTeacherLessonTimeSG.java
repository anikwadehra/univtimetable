package ua.kiev.univ.timetable;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.IntegerGene;
import org.jgap.supergenes.AbstractSupergene;
import org.jgap.supergenes.Supergene;

public class GroupClassTeacherLessonTimeSG extends AbstractSupergene {
    private static final String TOKEN_SEPARATOR = ":";
    private static final String GENE_DELIMITER = "+";

    public GroupClassTeacherLessonTimeSG() throws InvalidConfigurationException {
        super();
    }

    public GroupClassTeacherLessonTimeSG(final Configuration a_conf) throws InvalidConfigurationException {
        super(a_conf);
    }

    public GroupClassTeacherLessonTimeSG(final Configuration a_conf,
                               Gene[] a_genes) throws InvalidConfigurationException {
        super(a_conf, a_genes);
    }

    public boolean isValid(Gene[] a_genes, Supergene a_supergene) {
        return true;
    }

    // Order: GroupGene, ClassGene, TimeGene
    @Override
    public String getPersistentRepresentation() throws UnsupportedOperationException {
        return this.geneAt(Start.GROUP).getPersistentRepresentation() + GENE_DELIMITER +
               this.geneAt(Start.CLASS).getPersistentRepresentation() + GENE_DELIMITER +
               this.geneAt(Start.TIME).getPersistentRepresentation();
    }
}
