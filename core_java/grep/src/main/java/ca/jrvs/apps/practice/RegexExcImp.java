package ca.jrvs.apps.practice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExcImp implements RegexExec {

  @Override
  public boolean matchJpeg(String filename) {
    Pattern p = Pattern.compile(".*\\.jpe?g");
    Matcher m = p.matcher(filename);
    return m.matches();
  }

  @Override
  public boolean matchIp(String ip) {
    Pattern p = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
    Matcher m = p.matcher(ip);
    return m.matches();
  }

  @Override
  public boolean isEmptyLine(String line) {
    Pattern p = Pattern.compile("^\\s*");
    Matcher m = p.matcher(line);
    return m.matches();
  }

  public static void main(String[] args){
    RegexExcImp reg = new RegexExcImp();
//    Empty line
    System.out.println(reg.isEmptyLine("9.9.9")); //false
    System.out.println(reg.isEmptyLine("               ")); //true
    System.out.println(reg.isEmptyLine("\n")); //true
    System.out.println(reg.isEmptyLine("\t            \n         \t")); //true
    System.out.println(reg.isEmptyLine("\t            \n      w   \t")); //false
//  IP
    System.out.println(reg.matchIp("9.9.9")); //false
    System.out.println(reg.matchIp("990.999.999.999")); //true
    System.out.println(reg.matchIp("990.999.999.9994")); //false
    System.out.println(reg.matchIp("0.9.299.94")); //true
    System.out.println(reg.matchIp("0.0.0.0")); //true
    System.out.println(reg.matchIp("0.0..0")); //false
    System.out.println(reg.matchIp("0.0.1111.0")); //false
//  Filename
    System.out.println(reg.matchJpeg("dbweaudw.jpeg")); //true
    System.out.println(reg.matchJpeg("dbweaudw.jp.eg")); //false
    System.out.println(reg.matchJpeg("dbweaudw.jpg")); //true
    System.out.println(reg.matchJpeg("dbweaudwjpeg")); //false
    System.out.println(reg.matchJpeg("dbweaudwjpg")); //false
    System.out.println(reg.matchJpeg("dbweaudwpdwad.jpeg.j")); //false
  }
}
