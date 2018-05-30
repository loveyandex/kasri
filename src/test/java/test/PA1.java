package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * is created by aMIN on 3/4/2018 at 01:26 AM
 */
public class PA1 {
    public static void main(String[] args) {
        String regexe = ".class$";   // ending with ".class"
        String replacement = ".out"; // replace with ".out"

        // Step 1: Allocate a Pattern object to compile a regexe
        Pattern pattern = Pattern.compile(regexe, Pattern.CASE_INSENSITIVE);

        // Step 2: Allocate a Matcher object from the pattern, and provide the input
        CharSequence input="king.class";
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }


        String source = "There L-are thirty-three big-apple";
        String[] tokens = source.split("(\\s+)|(-)");  // whitespace(s) or -
        for (String token : tokens) {
            System.out.println(token);
        }

    }
}
