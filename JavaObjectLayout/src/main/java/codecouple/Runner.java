package codecouple;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

/**
 * Created by CodeCouple.pl
 */
public class Runner {

    public static void main(String[] args) {
        System.out.println(VM.current().details());

        final SerialNumber serialNumber = new SerialNumber(1);
        System.out.println(ClassLayout.parseClass(SerialNumber.class).toPrintable(serialNumber));


        final SerialNumberWithAlignment serialNumberWithAlignment = new SerialNumberWithAlignment(1, 1);
        System.out.println(ClassLayout.parseClass(SerialNumberWithAlignment.class).toPrintable(serialNumberWithAlignment));

        final SerialNumberWithReference serialNumberWithReference = new SerialNumberWithReference(
                1,
                1,
                new IntNumber(1));
        System.out.println(GraphLayout.parseInstance(serialNumberWithReference).toFootprint());
    }

}


class SerialNumber {

    private final int value;

    SerialNumber(final int value) {
        this.value = value;
    }

}

class SerialNumberWithAlignment {

    private final int value;
    private final int secondValue;

    SerialNumberWithAlignment(final int value, int secondValue) {
        this.value = value;
        this.secondValue = secondValue;
    }


}


class SerialNumberWithReference {

    private final int value;
    private final int secondValue;
    private final IntNumber intNumber;

    SerialNumberWithReference(final int value, int secondValue, IntNumber intNumber) {
        this.value = value;
        this.secondValue = secondValue;
        this.intNumber = intNumber;
    }


}

class IntNumber {

    private final int value;

    IntNumber(int value) {
        this.value = value;
    }
}

