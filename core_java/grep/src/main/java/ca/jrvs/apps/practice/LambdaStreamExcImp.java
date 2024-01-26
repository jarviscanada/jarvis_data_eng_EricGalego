package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc {

  public LambdaStreamExcImp() {

  }

  public static void main(String[] args) {
    LambdaStreamExcImp lExc = new LambdaStreamExcImp();
    // Testing some functionality
    Stream<String> serStrings = lExc.toUpperCase("Bwruhwdd", "hjdawodwo", "wdhawIIII");
    Stream<String> serStrings2 = lExc.createStrStream("Brwgfkvdouh", "hjdawodwo", "wdhawIIII", "wofjes");
    serStrings.forEach(System.out::println);
    IntStream l = lExc.createIntStream(new int[] {1,2,3,4});
    IntStream lst = lExc.getOdd(l);
    lst.forEach(System.out::println);
    Consumer<String> printer = lExc.getLambdaPrinter("start>", "<end");
    printer.accept("Message body");
    String[] messages = {"a","b", "c"};
    lExc.printMessages(messages, lExc.getLambdaPrinter("msg:", "!") );
    lExc.printOdd(lExc.createIntStream(0, 5), lExc.getLambdaPrinter("odd number:", "!"));
    Stream<Integer> it = lExc.flatNestedInt(Stream.of(
        List.of(1, 2, 3),
        List.of(4, 5, 6),
        List.of(7, 8, 9)));
    it.forEach(System.out::println);
    Stream<String> strStream = lExc.filter(serStrings2, ".*a.*");
    strStream.forEach(System.out::println);
  }
  @Override
  public Stream<String> createStrStream(String... strings) {
    return Arrays.stream(strings);
  }

  @Override
  public Stream<String> toUpperCase(String... strings) {
    return createStrStream(strings).map(String::toUpperCase);
  }

  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    Pattern p = Pattern.compile(pattern);
    return stringStream.filter(s-> !p.matcher(s).matches());
  }

  @Override
  public IntStream createIntStream(int[] arr) {
    return IntStream.of(arr);
  }

  @Override
  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  @Override
  public List<Integer> toList(IntStream intStream) {
    return intStream.boxed().collect(Collectors.toList());
  }

  @Override
  public IntStream createIntStream(int start, int end) {
    return IntStream.range(start, end);
  }

  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {
    return intStream.asDoubleStream().map(e -> Math.sqrt(e));
  }

  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(x -> x % 2 != 0);
  }

  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    return x -> System.out.println(prefix+x+suffix);
  }

  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    createStrStream(messages).forEach(printer);
  }

  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    getOdd(intStream).forEach(x->printer.accept(String.valueOf(x)));
  }

  @Override
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
    return ints.flatMap(List::stream).map(num->num*num);
  }
}
