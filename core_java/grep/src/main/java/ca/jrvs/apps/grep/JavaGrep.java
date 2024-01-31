package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public interface JavaGrep {

  /**
   * Top level search workflow
   * @throws IOException if rootPath does not exist
   */
  void process() throws IOException;

  /**
   * Traverse a given directory and return all files
   * @param rootDir input directory
   * @return stream of files under the rootDir
   */
  Stream<File> listFiles(String rootDir);

  /**
   * Read a file and return all the lines
   *
   * @param inputFile file to be read
   * @return stream of lines from inputFile
   */
  Stream<String> readLines(File inputFile);

  /**
   * check if a lines contains the regex pattern (passed by user)
   * @param line input string
   * @return true if there is a match
   */
  boolean containsPattern(String line);

  /**
   * Write lines to a file
   *
   * @param lines list of lines to write to outfile
   * @throws IOException if write failed
   */
  void writeToFile(List<String> lines) throws IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegex();

  void setRegex(String regex);

  String getOutFile();

  void setOutFile(String outFile);
}
