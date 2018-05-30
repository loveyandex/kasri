package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * is created by aMIN on 3/3/2018 at 08:07 PM
 */
public class PA {
    public static void main(String[] args) {
//
//        Pattern compile = Pattern.compile(".* .* .* at .*");
//        Matcher matcher1 = compile.matcher(" in the north at kiing jk kl");
//
//
//        while (matcher1.find())
//            System.out.println(matcher1.group());



        String input = "king$ in the 11:11:11 god is great clock is 03:3:237 ";



        String regexe = "([0-9]{1}[0-9]?):([0-9][0-9]?):([0-9][0-9]?)";        // pattern to be matched
        String replacement = "";  // replacement pattern

        // Step 1: Allocate a Pattern object to compile a regexe
        Pattern pattern = Pattern.compile(regexe, Pattern.CASE_INSENSITIVE);

        // Step 2: Allocate a Matcher object from the pattern, and provide the input
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
        // Step 3: Perform the matching and process the matching result
        String output = matcher.replaceAll(replacement);     // all matches
        //String output = matcher.replaceFirst(replacement); // first match only
        System.out.println(output);
    }
}
