import java.util.function.*;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Function<Integer, String> showCodeCouple = x -> x + " CodeCouple";
        System.out.println(showCodeCouple.apply(10));
        //"10 CodeCouple"

        //*******************************

        Function<String, Integer> createIntegerAndAddTen = x -> Integer.parseInt(x) + 10;
        Stream.of("10", "20", "30")
                .map(createIntegerAndAddTen)
                .forEach(System.out::println);
        //20, 30, 40

        //*******************************

        Function<Integer, Integer> multiplyByTen = x -> x * 10;
        Function<String, String> addZero = x -> x + "0";

        Stream.of("10", "20", "30")
                .map(createIntegerAndAddTen.andThen(multiplyByTen))
                .forEach(System.out::println);
        //200, 300, 400

        //*******************************

        Stream.of("10", "20", "30")
                .map(createIntegerAndAddTen.compose(addZero))
                .forEach(System.out::println);
        //110, 210, 310

        //*******************************

        Stream.of("10", "20", "30")
                .map(Function.identity())
                .forEach(System.out::println);
        //10, 20, 30

        //*******************************

        Function<Object, Object> doNothing = x -> x;

        Stream.of("10", "20", "30")
                .map(doNothing)
                .forEach(System.out::println);
        //10, 20, 30

        //*******************************

        Consumer<String> printCodeCouple = text -> System.out.println(text);
        printCodeCouple.accept("CodeCouple");
        //CodeCouple

        //*******************************

        Stream.of("show", "Code", "Couple")
                .forEach(printCodeCouple);
        //show
        //Code
        //Couple

        //*******************************

        Supplier<String> returnCodeCouple = () -> "CodeCouple";
        System.out.println(returnCodeCouple.get());
        //CodeCouple

        //*******************************

        Predicate<String> checkCodeCoupleIsTheBest = text -> text.contains("best");
        System.out.println("Is Code Couple the best? " + checkCodeCoupleIsTheBest.test("CodeCouple is the best"));
        //Is Code Couple the best? true

        Predicate<String> containsCode = textToCheck -> textToCheck.contains("Code");

        Stream.of("showCode", "Code", "Couple")
                .filter(containsCode)
                .forEach(System.out::println);
        //showCode
        //Code

        Stream.of("showCode", "Code", "Couple")
                .filter(containsCode.negate())
                .forEach(System.out::println);
        //Couple

        //*******************************

        Predicate<String> containsCouple = textToCheck -> textToCheck.contains("Couple");

        Stream.of("showCode", "Code", "CodeCouple")
                .filter(containsCode.and(containsCouple))
                .forEach(System.out::println);
        //CodeCouple

        Stream.of("showCode", "Code", "CodeCouple")
                .filter(Predicate.isEqual("Code"))
                .forEach(System.out::println);
        //Code

        //*******************************

        UnaryOperator<String> getCodeCouple = text -> text + " is the best";
        System.out.println(getCodeCouple.apply("CodeCouple"));
        //CodeCouple is the best

        //*******************************

        PersonChecker personChecker = (p) -> p.getName().contains("Jan");
        System.out.println("Check: " + personChecker.checkNameContainsJan(Person.JAN));
        //Check: true

    }
}
