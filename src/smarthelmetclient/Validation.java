/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthelmetclient;

/**
 *
 * @author Sasi Praveen
 */
public class Validation {
    //Returns true if string contains only character words and has one space betwwen each word;
    public static boolean isStringWithSpace(String input)
    {
        return input.matches("(([a-zA-Z0-9])+(\\s)?)+");
    }
    
    public static boolean isLatitude(String input){
        return input.matches("([0-9]{4})[.]([0-9]{6})([N]|[S])");
    }
    
    public static boolean isLongitude(String input){
        return input.matches("([0-9]{4})[.]([0-9]{6})([E]|[W])");
    }
}

