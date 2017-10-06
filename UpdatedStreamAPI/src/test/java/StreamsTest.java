import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by CodeCouple.pl
 */
class StreamsTest {

    @Test
    void iterate() {
        Stream.iterate("", s -> s + "s")
                .filter(s->s.length() < 3)
                .forEach(this::log);
    }

    @Test
    void takeWhile(){
        Stream.iterate("", s -> s + "s")
                .takeWhile(s->s.length() < 3)
                .forEach(this::log);
    }

    @Test
    void dropWhile(){
        Stream.iterate("", s -> s + "s")
                .dropWhile(s->s.length() < 3)
                .forEach(this::log);
    }

    @Test
    void iterateDate(){
        Stream.iterate(0, i -> i + 1)
                .limit(10)
                .forEach(this::log);

        LocalDate startDate = LocalDate.of(2017, 1, 1);

        Stream.iterate(startDate, date -> date.plusDays(1))
                .forEach(this::log); // To wypisze nam wszystkie daty w nieskończoność

        Stream.iterate(startDate, date -> date.plusDays(1))
                .filter(date->date.isBefore(LocalDate.now()))
                .forEach(this::log); // To wypisze nam wszystkie daty to dziś ale strumień będzie się dalej "kręcił"

        Stream.iterate(startDate, date -> date.plusDays(1))
                .peek(this::log)
                .allMatch(date->date.isBefore(LocalDate.now())); // Działający przykład, mniej intuicyjny

        Stream.iterate(startDate,date -> date.isBefore(LocalDate.now()),  date -> date.plusDays(1))
                .forEach(this::log);
    }

    @Test
    void ofNullable(){

        Map<String, Integer> map = new HashMap<>();
        map.put("String", 1);
        map.put("StringSecond", null);


        List<Integer> collect = Stream.of("String", "StringSecond")
                .flatMap(element -> {
                    Integer temp = map.get(element);
                    return temp != null ? Stream.of(temp) : Stream.empty();
                })
                .collect(toList());

        List<Integer> collection = Stream.of("String", "StringSecond")
                .flatMap(element -> Stream.ofNullable(map.get(element)))
                .collect(toList());
    }

    @Test
    void streamFromOptional(){
        Stream.of("string", "second")
                .map(this::getSomething)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(this::log);

        Stream.of("string", "second")
                .map(this::getSomething)
                .flatMap(Optional::stream)
                .forEach(this::log);
    }

    Optional<String> getSomething(String text){
        return text.equals("second") ? Optional.of(text)  : Optional.empty();
    }

    private void log(Object objectToPrint){
        System.out.println(objectToPrint);
    }
}
