package com.lc.nlp4han.ml.naivebayes;

import java.io.File;
import java.io.IOException;

import com.lc.nlp4han.ml.model.AbstractModel;
import com.lc.nlp4han.ml.model.AbstractModelReader;
import com.lc.nlp4han.ml.model.Context;
import com.lc.nlp4han.ml.model.DataReader;
/**
 * Abstract parent class for readers of NaiveBayes.
 */
public class NaiveBayesModelReader extends AbstractModelReader {

  public NaiveBayesModelReader(File file) throws IOException {
    super(file);
  }

  public NaiveBayesModelReader(DataReader dataReader) {
    super(dataReader);
  }

  /**
   * Retrieve a model from disk. It assumes that models are saved in the
   * following sequence:
   *
   * <br>NaiveBayes (model type identifier)
   * <br>1. # of parameters (int)
   * <br>2. # of outcomes (int)
   * <br>  * list of outcome names (String)
   * <br>3. # of different types of outcome patterns (int)
   * <br>   * list of (int int[])
   * <br>   [# of predicates for which outcome pattern is true] [outcome pattern]
   * <br>4. # of predicates (int)
   * <br>   * list of predicate names (String)
   *
   * <p>If you are creating a reader for a format which won't work with this
   * (perhaps a database or xml file), override this method and ignore the
   * other methods provided in this abstract class.
   *
   * @return The NaiveBayesModel stored in the format and location specified to
   * this NaiveBayesModelReader (usually via its the constructor).
   */
  public AbstractModel constructModel() throws IOException {
    String[] outcomeLabels = getOutcomes();
    int[][] outcomePatterns = getOutcomePatterns();
    String[] predLabels = getPredicates();
    Context[] params = getParameters(outcomePatterns);

    return new NaiveBayesModel(params,
        predLabels,
        outcomeLabels);
  }

  public void checkModelType() throws java.io.IOException {
    String modelType = readUTF();
    if (!modelType.equals("NaiveBayes"))
      System.out.println("Error: attempting to load a " + modelType +
          " model as a NaiveBayes model." +
          " You should expect problems.");
  }
}
