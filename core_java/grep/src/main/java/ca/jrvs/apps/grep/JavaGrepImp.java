package ca.jrvs.apps.grep;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
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
    List<String> matchingLines = new ArrayList<>();
    listFiles(getRootPath()).forEach(file-> {
      try {
        readLines(file).forEach(matchingLines::add);
      //typically files non UTF-8 that can't be read
      } catch (UncheckedIOException e) {
        logger.error(String.valueOf(e));
      }
    });
    writeToFile(matchingLines);
  }

  @Override
  public Stream<File> listFiles(String rootDir) {
    try {
      return Files.walk(Paths.get(rootDir), Integer.MAX_VALUE, FileVisitOption.FOLLOW_LINKS)
        .filter(Files::isRegularFile)
        .map(Path::toFile);
    } catch (IOException e) {
      logger.error(e.toString());
      return Stream.empty();
    }
  }

  // return stream
  @Override
  public Stream<String> readLines(File inputFile) {
    try {
      return Files.lines(inputFile.toPath()).filter(this::containsPattern);
    } catch (IOException e) {
      logger.error(String.valueOf(e));
      return Stream.empty(); // Return an empty stream on exception
    }
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
    try(FileWriter fileWriter = new FileWriter(getOutFile())) {
      for (String line : lines) {
        fileWriter.write(line +'\n');
      }
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
    } catch (IOException ex) {
      javaGrepImp.logger.error("Error: Unable to process", ex);
    }
  }
}
