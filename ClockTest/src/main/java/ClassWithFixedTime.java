import java.util.Date;

/**
 * Created by CodeCouple.pl
 */
class ClassWithFixedTime {

    private final Date date;

    ClassWithFixedTime(Date date) {
        this.date = date;
    }

    Date getDate() {
        return date;
    }

}
