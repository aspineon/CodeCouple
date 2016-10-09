/**
 * Created by Krzysztof Chruściel.
 */
public enum Person {
    JAN("", false), JOHN("JOHN", true);

    private String name;
    private boolean ready;


    Person(String name, boolean ready) {
        this.name = name;
        this.ready = ready;
    }

    public String getName() {
        return name;
    }

    public boolean isReady() {
        return ready;
    }
}
