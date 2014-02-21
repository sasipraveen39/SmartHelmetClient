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
    
    public static boolean isNumber(String input){
        return input.matches("[0-9]+[.]?[0-9]*");
    }
    
    public static boolean isWithinLimit(String value, double min, double max){
        if(!isNumber(value)){
            return false;
        }
        double x = Double.parseDouble(value);
        if( x >= min && x <= max){
            return true;
        }else{
            return false;
        }
    }
}

