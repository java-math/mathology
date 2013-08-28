/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vectors;

//import java.util.ArrayList;
/**
 *
 * @author Dinesh
 */
public class Vector_Storage {
    private static Vector vectorA = new Vector("A");
    private static Vector vectorB = new Vector("B");
    private static Vector vectorC = new Vector("C");

    public static void setVector(Vector v, String name){
        if(name.equalsIgnoreCase("A")){
            vectorA = new Vector(v, name);
        }
        else if(name.equalsIgnoreCase("B")){
            vectorB = new Vector(v, name);
        }
        else if(name.equalsIgnoreCase("C")){
            vectorC = new Vector(v, name);
        }
    }

    public static void removeVector(String name){
        Vector temp = new Vector(name);
        setVector(temp, name);
    }

    public static String display(String name){
        if(name.equalsIgnoreCase("A")){
            return vectorA.toString();
        }
        else if(name.equalsIgnoreCase("B")){
            return vectorB.toString();
        }
        else if(name.equalsIgnoreCase("C")){
            return vectorC.toString();
        }
        //..... DUMMY CODE...
        return "Billa";
    }

    public static Vector getVector(String name){
        if(name.equalsIgnoreCase("A")){
            return vectorA;
        }
        else if(name.equalsIgnoreCase("B")){
            return vectorB;
        }
        else if(name.equalsIgnoreCase("C")){
            return vectorC;
        }
        //..... DUMMY CODE...
        return new Vector("A");
    }
}
