package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);
  private String regex;
  private String rootPath;
  private String outFile;

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

  @Override
  public void process() throws IOException {
    // Error checking if rootPath is an actual path
    if(!Files.exists(Paths.get(getRootPath()))) {
      throw new IOException(String.format("Error: %s No such file or directory", getRootPath()));
    }
    List<File> files = listFiles(getRootPath(), new ArrayList<>());
    List<String> matchingLines = new ArrayList<>();
    for (File file : files) {
      matchingLines.addAll(readLines(file));
    }
    writeToFile(matchingLines);
  }

  @Override
  public List<File> listFiles(String rootDir, List<File> files) {
    File dir = new File(rootDir);
    for (File file : Objects.requireNonNull(dir.listFiles())) {
      if (file.isDirectory()) {
        listFiles(String.valueOf(file), files);
      } else {
        files.add(file);
      }
    }
    return files;
  }

  @Override
  public List<String> readLines(File inputFile) {
    BufferedReader reader;
    List<String> matchingLines = new ArrayList<>();
    try {
      reader = new BufferedReader(new FileReader(inputFile));
      String line = reader.readLine();
      while (line != null) {
        line = reader.readLine();
        if (containsPattern(line)) {
          matchingLines.add(line);
        }
      }
      reader.close();
    } catch (IOException e) {
      logger.error(String.valueOf(e));
    }
    return matchingLines;
  }

  @Override
  public boolean containsPattern(String line) {
    Pattern p = Pattern.compile(getRegex());
    if(line != null) {
      Matcher m = p.matcher(line);
      return m.matches();
    }
    return false;
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    try {
      FileWriter fileWriter = new FileWriter(getOutFile());
      for (String line : lines) {
        fileWriter.write(line +'\n');
      }
      fileWriter.close();
    } catch (IOException e) {
      logger.error(String.valueOf(e));
      throw new IOException(e);
    }
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    // Assume regex is proper regex
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      javaGrepImp.logger.error("Error: Unable to process", ex);
    }
  }
}
