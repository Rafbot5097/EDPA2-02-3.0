/*********************************************************************
*
* Class Name: postfix
* Author/s name: PC, RCR, LD - Group 02
* Release/Creation date: 11/10/25
* Class version: 2.0
* Class description: Evaluation of postfix expressions.
*
**********************************************************************
*/

import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;  //Split could also be used

public class postfix {

    public static boolean itsThere(String[] array, String el){
        /*********************************************************************
        *
        * Method name: itsThere
        *
        * Name of the original author: PC
        *
        * Description of the Method: Searches for a certain element on an array 
        *
        * Calling arguments: Array where the element will be searched for, and that element
        *
        * Return value: True if the element is in array
        *
        *********************************************************************/

        boolean itsThere = false;
        for(int i = 0; i < array.length; i++){
            if (array[i].equals(el)){
                itsThere = true;
            }
        }
        return itsThere;
    }

    
    public static void main(String[] args) {
        String expr, cad, cad1, cad2;
        Scanner read = new Scanner(System.in);
        Stack <String> stck = new Stack<>();
        final String[] OPERATORS = {"+", "-", "@", "*"};

        System.out.println("Please, introduce the operations: ");
        expr = read.nextLine();
        StringTokenizer st = new StringTokenizer(expr, " ");



        while(st.hasMoreTokens()){
            cad = st.nextToken();
            if (!itsThere(OPERATORS, cad)){
                //Only strings are saved on the stack, not operators
                stck.push(cad);
            } 

            else {
                switch(cad){
                    case "+":
                        cad1 = stck.pop();
                        cad2 = stck.pop(); 

                        System.out.println(cad1 + " " + cad2 + " " + cad);

                        stck.push(plusOp(cad1, cad2));
                        System.out.println("Result of operation: " + stck.peek());

                        break;
                    case "-":
                        cad1 = stck.pop();
                        cad2 = stck.pop(); 
                        System.out.println(cad1 + " " + cad2 + " " + cad);

                        stck.push(minusOp(cad1, cad2));
                        System.out.println("Result of operation: " + stck.peek());
                        break;
                    case "@":
                        cad1 = stck.pop();
                        System.out.println(cad1 + " " + cad);

                        stck.push(inverseOp(cad1));
                        System.out.println("Result of operation: " + stck.peek());
                        break;
                    case "*":
                        cad1 = stck.pop();
                        cad2 = stck.pop(); 
                        System.out.println(cad1 + " " + cad2 + " " + cad);

                        stck.push(intersectOp(cad1, cad2));
                        System.out.println("Result of operation: " + stck.peek());

                        break;
                }
            }

        }
        System.out.println("Final result: " + stck.pop()); 

        read.close();
    }
    
    public static String plusOp(String s2, String s1){
        /*********************************************************************
        *
        * Method name: plusOp
        *
        * Name of the original author: RCR
        *
        * Description of the Method: Adds strings. 
        *
        * Calling arguments: Two strings, cad2 is before cad1 because of the LIFO way of working
        *
        * Return value: Concatenation of the calling arguments
        *
        *********************************************************************/
        return s1 + s2;
    }

    public static String minusOp(String s2, String s1) {
        /*********************************************************************
        *
        * Method name: minusOp
        *
        * Name of the original author: RCR
        *
        * Description of the Method: Eliminates characters in s1 that are present in s2 
        *
        * Calling arguments: Two strings, cad2 is before cad1 because of the LIFO way of working
        *
        * Return value: s1 characters not in s2
        *
        *********************************************************************/
        String result = "";
        char c;
        boolean found;

        for (int i = 0; i < s1.length(); i++) {
            c = s1.charAt(i);
            found = false;
            for (int j = 0; j < s2.length() && !found; j++) {
                if (c == s2.charAt(j)) {
                    found = true;
                }
            }
            if (!found) {
                result += c;
            }
        }
        
        return result;
    }
    

    
    public static String intersectOp(String s1, String s2){
        /*********************************************************************
        *
        * Method name: intersectOp
        *
        * Name of the original author: PC
        *
        * Description of the Method: Keeps characters in both s1 and s2 
        *
        * Calling arguments: Two strings, cad2 is before cad1 because of the LIFO way of working
        *
        * Return value: characters in both s1 and s2 (intersection of s1 and s2)
        *
        *********************************************************************/
        String result = "";

        for(int i = 0; i < s1.length(); i++){
            if(s2.indexOf(s1.charAt(i)) != -1){
                //If the character is at s2
                if(result.indexOf(s1.charAt(i)) == -1){
                    result += s1.charAt(i);
                }
            }
        }
        return result;
    }
    
    public static String inverseOp(String s){
        /*********************************************************************
        *
        * Method name: inverseOp
        *
        * Name of the original author: LD
        *
        * Description of the Method: Inverts the string given as argument 
        *
        * Calling arguments: A string that will be inverted
        *
        * Return value: inverted string
        *
        *********************************************************************/
        Stack<Character> aux = new Stack<>();
        String inverted = "";

        char[] c = s.toCharArray();

        for (int i = 0; i < s.length(); i++){
            aux.push(c[i]);
        }

        while (!aux.isEmpty()) {
            inverted += (aux.pop());
        }

        return inverted;
    }

}
