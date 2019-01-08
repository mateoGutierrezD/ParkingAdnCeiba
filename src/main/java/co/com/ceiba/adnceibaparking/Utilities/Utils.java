package co.com.ceiba.adnceibaparking.Utilities;

public class Utils {

    public static double ConvertNegativeNumberToPositive(double number) {
        if (number < 0) {
            number = number * -1;
        }
        return number;
    }
}
