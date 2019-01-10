package co.com.ceiba.adnceibaparking.utilities;

public final class Utils {

    private Utils() {}

    public static double convertNegativeNumberToPositive(double number) {
        if (number < 0) {
            number = number * -1;
        }
        return number;
    }
}
