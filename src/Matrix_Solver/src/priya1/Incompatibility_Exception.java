/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package priya1;

/**
 *
 * @author Mahesh
 * @since June 29, 2009
 */
public class Incompatibility_Exception extends java.lang.Exception {
   public Incompatibility_Exception(){
   }

   public void display(){
       System.err.println("The operation to be performed is incompatible");
   }
}