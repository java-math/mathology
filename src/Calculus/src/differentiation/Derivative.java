/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package differentiation;

/**
 *
 * @author Mahesh
 * @version 1.0
 */
public class Derivative{

  /**
   * The Expression object which stores and is used to interpret the expression stored in the object
   */
  private Expression expression = null;

  /**
   * The String form of expression.
   */
  private String  exp = null;

  /**
   * The Derivative of the expression in the string format
   */
  private String derivative = null;

  /**
   * The result of recent compilation is stored.
   */
  private String compile_msg = null;

  /**
   * The boolean field determining whether expression is differentiable or not
   */
  private boolean status = false;

  /**
   * The constructore initializing the object with expression
   * @param exp The expression which is to be differentiated.
   */
  public Derivative(String exp){
     exp.replaceAll(" ",""); //.................. REMOVE ALL INBETWEEN SPACES...
     this.expression = new Expression(exp);
     this.exp = exp;
  }

  /**
   * The compilation method used to compile the expression
   * @return The result of compilation of the expression
   */
  public String compile(){
     compile_msg = expression.compile();

     status = compile_msg.equals(Expression.NO_ERROR_MSG);//.......... STATUS SHOWS WHETHER IT IS DIFF. OR NOT...
     return compile_msg;
  }

  /**
   * The method used to get Derivative of the expression
   * @return Derivative of the expression
   */
  public String getderivative(){
    if(status){
       derivative = differentiate_Term(expression.getConvertedExpression());//... DIFF. THE ENCODED FORM OF EXPRESSION..
       derivative = expression.get_Expr(derivative); //............. DECODE THE DIFF. EXPRESSION...
       return derivative;
    }
    else{
      return "Can not Differentiate without compilation";
    }
  }

/**
 * THIS METHOD BREAKS THE EXPRESSION value INTO SUBPARTS...
 * AND THEN RECURSIVELY DIFFERENTIATES EACH TERM...
 * COMBINES THEM AND RETURNS THE VALUE...
 * @term the term which is supposed to be differentiated.
 */
public String differentiate_Term(String term){
     try{                                     //..... DIFFERENTIATION OF CONSTANT TERM... IS ZERO
            if(term.equals(Double.toString(Expression.PI)) ||term.equals(Double.toString(Expression.E))){
               return Expression.ZERO;
            }
            double x = Double.parseDouble(term);
            return Expression.ZERO;
        }
        catch(Exception e){
          //........  DO NOTHING...
        }

    if(term.equalsIgnoreCase("x")){             //............. DERIVATIVE OF x IS 1
       return Expression.ONE;
    }
/* ****************************Below part recursively divides the term to differentiate it*************/

    String left_term = null;        //.... ASSUMING EXP. IS OF FORM OF...
    String right_term = null;       //.... <LEFT_TERM> <OP> <RIGHT_TERM>
    String oper = null;             //.... OPERATOR...

    int open_brace_position = -1;             //.......... KEEPS INDEX OF FIRST FOUND OPEN BRACE...
    int close_brace_position = -1;             //......... KEEPS INEDX OF CORRES. CLOSE BRACE...
    int operator_position = -1;          //......... KEEPS TRACK OF FIRST OP. FOUND....
    boolean brace_found = false;       //....... CHECKS WHETHER BRACE IS FOUND OR NOT...
    int count=0;                      //...... in case of nested braces...
                                     //.... count of innerness of an expression
    int arg_separator=-1;  //............. IF THERE ARE TWO ARGUMENTS THEN INDEX OF SEPARATOR COMMA
                           //............. SEPARATOR OF TWO..

    /*
     * The loop below scans the expression from left to right to find open brace or + or - operator and
     * acts accordingly to recursively divide it...
     */
    for(int i=0 ; i<term.length() ; ++i){
        String ch = Character.toString(term.charAt(i));

        if(expression.is_Open_Brace(ch)){
             if(!brace_found){
                open_brace_position = i;
                brace_found = true;
             }
             ++count;
        }
        else if( expression.is_Close_Brace(ch)){
            --count;
            if(count == 0 && close_brace_position == -1){
                close_brace_position = i;
            }
        }
        else if(operator_position == -1 && expression.is_Operator(ch) && count == 0){
            operator_position = i;
        }  //.......1.  ONCE OPER. IS FOUND ITS DONE...
           //...... 2. IT MUST BE AND OPERATOR
           //...... 3. OPER. MUST BE OUTSIDE THE BRACES...
        else if(expression.is_Comma(ch) && count == 1){
            arg_separator = i;
        }
    }//............ END OF FOR LOOP

    if(operator_position != -1){ //............. OPERATOR IS PRESENT
        oper = Character.toString(term.charAt(operator_position));
    }

    if(open_brace_position == 0 && close_brace_position == term.length()-1){
         return this.differentiate_Term(term.substring(open_brace_position+1, close_brace_position));
    }                                      //.......... The Term is enclosed inside one pair of braces....

    if(brace_found){
       if(open_brace_position == 0 || !expression.is_Keywords(Character.toString(term.charAt(open_brace_position-1)))){
           //................ ITS JUST A BRACE NO KEYWORD ASSOCIATED WITH IT...
           if(operator_position != -1){
              left_term = term.substring(open_brace_position+1, close_brace_position);
              right_term = term.substring(operator_position+1, term.length());
              return this.operator_differentiation(left_term, oper, right_term);
           }
       }
       else if(expression.is_Keywords(Character.toString(term.charAt(open_brace_position-1)))){
           //.......... Keyword is Found Just before the brace Thus Brace is of keyword
           //.......... Act accordingly and divide keep in mind the Keyword.

           
           if(open_brace_position == 1 && close_brace_position == term.length()-1){
               //...... The Expression is atomic in terms of Keyword....
                String keyword = expression.getExpression(Character.toString(term.charAt(open_brace_position-1)));
                int no_of_args = expression.no_of_args(keyword);

                if(no_of_args == 2){//.......... KEYWORD REQURIRES TWO ARGUMENT
                    left_term = term.substring(open_brace_position+1,arg_separator);
                    right_term = term.substring(arg_separator+1, close_brace_position);
                    return this.differentiate_Atomic(Character.toString(term.charAt(0)), left_term, right_term);
                }

                else if(no_of_args == 1){//..... KEYWORD REQUIRES ONE ARGUMENT
                    left_term = term.substring(open_brace_position+1, close_brace_position);
                    return this.differentiate_Atomic(Character.toString(term.charAt(0)), left_term);
                }
            }

           else{//..... BRACE ENDS INBETWEEN THE TERM DIVIDE THE TERM INTO LEFT-OP-RIGHT PART AND USE RECURSION
              left_term = term.substring(0, operator_position);
              right_term = term.substring(operator_position+1,term.length());
              return operator_differentiation(left_term, oper, right_term);
           }//.... END OF ATOMIC KEYWORD ELSE....
       }//.... END OF kEYWORD FOUND ELSE....
    }//..... END OF BRACE FOUND ELSE....

    //............ What to do if Arithmatic Operator is found....
    else{//......... OPERATOR IS FOUND DIVIDE EXP. ON TEH BASIS OF OPER.
       left_term = term.substring(0,operator_position);
       right_term = term.substring(operator_position+1, term.length());

       return operator_differentiation(left_term, oper, right_term);
    }
    return "Dony";
}

/**
 * THE METHOD DIFFERENTIATES TWO EXPRESSION AND COMBINES THEM ON OPERATOR...
 */
private String operator_differentiation(String left_term, String operator, String right_term){
    String diff_left = differentiate_Term(left_term);
    String diff_right = differentiate_Term(right_term);

    if(operator.trim().equals("")){
        return "";
    }
    if(operator.equals("+")){
        return expression.add(diff_left, diff_right);
    }

    if(operator.equals("-")){
        return expression.subtract(diff_left, diff_right);
    }

    else if(operator.equals("*")){
        String temp1 = expression.multiply(left_term, diff_right);
        String temp2 = expression.multiply(diff_left, right_term);
        return expression.add(temp1, temp2);
    }

    else if(operator.equals("/")){
        String temp1 = expression.multiply(diff_left, right_term);
        String temp2 = expression.multiply(left_term, diff_right);
        String temp3 = expression.pow(right_term, 2+"");
        temp1 = expression.subtract(temp1, temp2);

        return expression.divide(temp1, temp3);
    }

    else if(operator.equals("^")){
        return differentiate_Atomic(expression.getSymbol("EXP"),left_term,right_term);
    } //...  exp(left_term,right_term)

    //.... DUMMY CODE...
    return "Rahul";
}

    private String differentiate_Atomic(String operator, String value){
        String value_diff = this.differentiate_Term(value);

        if(value_diff.equals(Expression.ZERO)){
            return Expression.ZERO;
        }

        try{
            double x = Double.parseDouble(value); //............. DIFFERENTIATION OF CONSTANT TERM...
            return Expression.ZERO;
        }
        catch(Exception e){
          //........  DO NOTHING...
        }

        if(operator.equals(expression.getSymbol("Sin"))){ //........ SIN TERM...
            String temp1 = "COS("+value+") ";
            return expression.multiply(temp1, value_diff);
        }

        else if(operator.equals(expression.getSymbol("Cos"))){ //........ COS TERM...
            String temp1 = "SIN("+value+")";
            temp1 = expression.multiply(-1+"", temp1);
            return expression.multiply(temp1, value_diff);
        }

        else if(operator.equals(expression.getSymbol("Tan"))){ //........ TAN TERM...
            String temp1 = "SEC("+value+")";
            temp1 = expression.pow(temp1, 2+"");
            return expression.multiply(temp1, value_diff);
        }

        else if(operator.equals(expression.getSymbol("Cosec"))){    //........ COSEC TERM...
            String temp1 = "COSEC("+value+")";
            temp1 = expression.multiply(temp1, -1+"");

            String temp2 = "COT("+value+")";
            temp2 = expression.multiply(temp2, value_diff);

            return expression.multiply(temp1, temp2);
        }

        else if(operator.equals(expression.getSymbol("Sec"))){   //......... SEC TERM...
            String temp1 = "SEC("+value+")";

            String temp2 = "TAN("+value+")";
            temp2 = expression.multiply(temp2, value_diff);

            return expression.multiply(temp1, temp2);
        }

        else if(operator.equals(expression.getSymbol("Cot"))){  //.......... COT TERM...
            String temp1 = "COSEC("+value+")";
            temp1 = expression.pow(temp1, 2+"");
            temp1 = expression.multiply(temp1, -1+"");

            return expression.multiply(temp1, value_diff);
        }

        else if(operator.equals(expression.getSymbol("ASin"))){ //........ ASIN TERM...
            String temp1 = expression.pow(value, 2+"");
            temp1 = expression.subtract(1+"", temp1);
            temp1 = expression.pow(temp1, 0.5+"");

            return expression.divide(value_diff, temp1);
        }

        else if(operator.equals(expression.getSymbol("ACos"))){ //........ ACOS TERM...
            String temp1 = expression.pow(value, 2+"");
            temp1 = expression.subtract(1+"", temp1);
            temp1 = expression.pow(temp1, 0.5+"");
            temp1 = expression.divide(value_diff, temp1);

            return expression.multiply(-1+"", temp1);
        }

        else if(operator.equals(expression.getSymbol("ATan"))){ //........ ATAN TERM...
            String temp1 = expression.pow(value, 2+"");
            temp1 = expression.add(1+"", temp1);

            return expression.divide(value_diff, temp1);
        }

        else if(operator.equals(expression.getSymbol("ACosec"))){ //..... ACOSEC TERM...
            String temp1 = expression.multiply("(0-1)"+"", value_diff);

            String temp2 = expression.pow(value, 2+"");
            temp2 = expression.subtract(temp2, 1+"");
            temp2 = expression.pow(temp2, 0.5+"");
            temp2 = expression.multiply(value, temp2);

            return expression.divide(temp1, temp2);
        }

        else if(operator.equals(expression.getSymbol("ASec"))){ //..... ASEC TERM...
            String temp1 = value_diff;

            String temp2 = expression.pow(value, 2+"");
            temp2 = expression.subtract(temp2, 1+"");
            temp2 = expression.pow(temp2, 0.5+"");
            temp2 = expression.multiply(value, temp2);

            return expression.divide(temp1, temp2);
        }

        else if(operator.equals(expression.getSymbol("ACot"))){ //........ ACOT TERM...
            String temp1 = expression.pow(value, 2+"");
            temp1 = expression.add(1+"", temp1);
            temp1 =  expression.divide(value_diff, temp1);

            return expression.multiply(temp1, -1+"");
        }
        // DUMMY CODE.... NEVER HAPPENS...
        return "Priyanka";
    }

     private String differentiate_Atomic(String type, String value_a, String value_b){
         try{
             double x_a = Double.parseDouble(value_a);
             double x_b = Double.parseDouble(value_b);
             return Expression.ZERO;
         }
         catch(Exception e){
             //..... DO NOTHING...
         }

         String diff_a = differentiate_Term(value_a);
         String diff_b = differentiate_Term(value_b);

         if(type.equals(expression.getSymbol("Exp"))){ //......... DO IT FOR EXP[a, b] TYPE
             if(expression.is_Number(value_b) && value_a.equalsIgnoreCase("x")){
                 return expression.multiply(value_b, expression.pow(value_a,expression.subtract(value_b,"1")));             } //.... Exp(x,n) Derivation...

             String temp1 = expression.pow(value_a, value_b);
             String temp2 = expression.multiply(value_b, diff_a);
             temp2 = expression.divide(temp2,value_a);
             String temp3 = expression.multiply(diff_b, expression.Log(Expression.E+"", value_a));
             temp2 = expression.add(temp2, temp3);

             return expression.multiply(temp1, temp2);
         }
         else if(type.equals(expression.getSymbol("Log"))){//...... DO IT FOR LOG[a, b] TYPE
             String temp1 = expression.multiply(expression.Log(Expression.E+"", value_a), diff_b);
             temp1 = expression.divide(temp1, value_b);

             String temp2 = expression.multiply(expression.Log(Expression.E+"", value_b), diff_a);
             temp2 = expression.divide(temp2, value_a);

             temp1 = expression.subtract(temp1, temp2);

             temp2 = expression.pow(expression.Log(Expression.E+"", value_a), 2+"");

             return expression.divide(temp1, temp2);
         }

         // DUMMY CODE... NEVER HAPPENS...
         return "Prachi";
     }
}