package ua.kiev.univ.timetable;

import java.io.Serializable;

import org.jgap.*;
import org.jgap.impl.*;

import java.util.StringTokenizer;

public class LessonGene extends IntegerGene implements Gene, Serializable{
  public LessonGene(Configuration configuration, int i, int i1) throws InvalidConfigurationException {
    super(configuration, i, i1);
  }

  public LessonGene(Configuration configuration) throws InvalidConfigurationException {
    super(configuration);
  }

  public LessonGene() throws InvalidConfigurationException {
    super();
  }
}
