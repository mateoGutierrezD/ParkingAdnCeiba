package co.com.ceiba.adnceibaparking.utilities;

public class Utils {

    public static double convertNegativeNumberToPositive(double number) {
        if (number < 0) {
            number = number * -1;
        }
        return number;
    }
}
