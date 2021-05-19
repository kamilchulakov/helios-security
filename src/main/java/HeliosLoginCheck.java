import java.util.regex.Pattern;

public class HeliosLoginCheck {
    public static boolean checkIfContainsHeliosLogin(String word) {
        return Pattern.matches("\"s(\\d){6}\"", word) || word.contains("It should not be here.");
    }
}
