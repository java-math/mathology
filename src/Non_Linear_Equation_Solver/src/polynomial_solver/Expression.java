package polynomial_solver;


/**
 *
 * @author Mahesh
 * @since July 05, 2009
 * @version 1.0
 */

import java.util.Stack;

public class Expression {

/**
 * infix is String representation contains infix representation of the expression.
 */
    private String infix = null;

   /**
    * infix_converted is the infix representation of the expression where all the
    * Expression constatns are represented by their corresponding symbols.
    */
    private String infix_converted = null;

    /**
     * postfix is the postfix representation of the expression.
     * All the keywords are represented in terms of their corresponding symbols..
     * This is used to evaluate the expression
     */
    private String postfix = null;

    /**
     * PI is a constant & represents PI symbol of maths.
     *
     */
    final static double PI = Math.PI;   //   constants used in an expression

    /**
     * E is a constant and used to get Natural Log of any expression..
     */
    final static double E = Math.E;

    /**
     * ERROR is the allowed value error that can occur in any decimal calculation.
     */
    final static double ERROR = 0.00001;

    /**
     * The value through which double variables are initialized..
     */
    final static double NULL_INITIALIZATION = -1*ERROR;

    /**
     * String representation of Zero.
     */
    final static String ZERO = "0";

    /**
     * String representation of One.
     */
    final static String ONE = "1";

    /**
     * String representation of Infinity.
     */
    final static String INFINITY = "INFINITY";

    /**
     * String representation of Negative Infinity.
     */
    final static String NEGINFINITY = "-INFINITY";

    /**
     * An array of all the allowed operators in the expressions.
     */
    final static String OPERATORS[] = {"+", "-", "*", "/", "^", "(", ")", "[", "]"};

    /**
     * A constants array where each element in the array is the allowed expression in the array..
     */
    final static String ExpressConst[] = {"LOG", "EXP", "ASIN", "ACOSEC", "ACOS", "ATAN", "ASEC", "ACOT", "SIN", "COSEC", "COS",  "TAN", "SEC", "COT"};

    /**
     * An array where each element represents the symbol in the array is the symbol that can replace in the array.
     */
    final static String[] SYMBOLS = {"B", "D", "F", "H", "J", "K", "M", "Q", "R", "U", "V", "W", "Y", "Z"};

    /**
     * The String error representation of the comma..
     */
    final static String COMMA = ",";

    /**
     * The String message which is returned when no error is found on compilation.
     */
    final static String NO_ERROR_MSG = "No Error Found";
/* ***************************************************************************************************** */
    /**
     * Constructor
     * @param s is the expression in the String form..
     */
    public Expression(String s){
        s = s.replaceAll(" ",""); //......... remove all internal and external spaces...
        infix = s;
    }

 /* ************************************************************************************************************** */
    /**
     * The method used to retrieve the converted form of infix expression.
     * @return infix expression where each Expression constant is replaced by its corresponding symbolic constant.
     */
    public String getConvertedExpression(){
        return infix_converted;
    }
/* **************************************************************************************************************** */
    /**
     * The method used to convert the expression into symbolic form.
     * @param str is the expression to be converted.
     * @return the converted form of expression
     */
    private String converter(String str){
       String temp = str.toUpperCase();

       for(int i=0 ; i<ExpressConst.length ; ++i){
            temp = temp.replaceAll(ExpressConst[i],SYMBOLS[i]);   //... convert all the expr const into
        }                                                     //... corresponding symbols

        temp = temp.replaceAll("PI",Double.toString(Math.PI));
        temp = temp.replaceAll("E",Double.toString(Math.E));

        return temp;
    }
    /**
     * The inverse of converter. Used to convert expression into its corresponding Expression value
     * @param param The String to be converted.
     * @return The corresponding expression value.
     */
    public String get_Expr(String param){
        String value = param;

      value = value.replaceAll("X","x");  //........ exprssion should contain small x

      for(int i=0 ; i<ExpressConst.length ; ++i){
         value = value.replaceAll(SYMBOLS[i], ExpressConst[i]); //.... convert all the symbols into
      }                                           //...... corresponding symbols...

      value = value.replaceAll(PI+"", "PI");
      value = value.replaceAll(E+"", "E");

      return value;
    }
 /* ************************************************************************************************************* */
   /**
    * The compile method compiles the given infix expression.
    * compilation has 4 stages..
    * Stage 1: This stage confirms whether <b>all the braces</b> present in the expression are <b>in a proper order</b> or not..
    * Stage 2: This stage confirms whether <b> all the keywords </b> present in the expression are correct or not.
    *          OR if any illegal or undefined keyword is present..
    * Stage 3: This stage confirms whether<b> entire expression OR the grammar of the expression </b>is valid or not..
    * Stage 4: Final stage of compilation. This confirms whether <b>arguments given to each keyword </b>is proper or not..
    * @return Error message if the error is present
    *         otherwise returns NO_ERROR_MSG.
    */
    public String compile(){
        infix_converted = this.converter(infix);
        String msg = null;

        msg = this.check_Brace_Order(); //........ stage 1
        if(!msg.equals(NO_ERROR_MSG)){
            return msg;
        }

        msg = this.check_Legal_Keywords();//........... stage 2
        if(!msg.equals(NO_ERROR_MSG)){
            return msg;
        }

        infix_converted = converter(infix);

        msg = this.check_Legal_Expression();//............. stage 3
        if(!msg.equals(NO_ERROR_MSG)){
            return msg;
        }

        return this.check_No_Of_Arguments();//............. stage 4
    }
    /* ************************* COMPILATION METHODS ************************************************************** */
//CHECKED...
    /**
     * First stage of compilation checks the order of braces present in the expression.
     * @return The result of compilation. if error is present then returns error message.
     *         Otherwise returns NO_ERROR_MSG.
     */
     private String check_Brace_Order(){
         Stack<String> stc = new Stack<String>();

         for(int i=0 ; i<infix_converted.length() ; ++i){
             if( infix_converted.charAt(i) == '(' || infix_converted.charAt(i) == '[' || infix_converted.charAt(i) == '{'){
                 stc.push(Character.toString(infix_converted.charAt(i)));
             }

            else if(infix_converted.charAt(i) == ')'){
                String str = stc.peek();
                if(str.equals("(")){
                    stc.pop();
                }
                else{
                    return ("Error 1.0... Reconsider the order of braces");
                }
            }

            else if(infix_converted.charAt(i) == ']'){
                String str = stc.peek();
                if(str.equals("[")){
                    stc.pop();
                }
                else{
                    return ("Error 1.1... Reconsider the order of braces");
                }
            }
            else if(infix_converted.charAt(i) == '}'){
                String str = stc.peek();
                if(str.equals("{")){
                    stc.pop();
                }
                else{
                    return ("Error 1.2... Reconsider the order of braces");
                }
            }
        }

        if(stc.isEmpty()){
            return NO_ERROR_MSG;
        }
        else{
            return "Error 1.3... Reconsider the order of braces";
        }
    }
//CHECKED...
     /**
      * This method is used to check whether all the identifiers used in the expression are legal or not
      * @return The result of compilation. if error is present then returns error message.
      *         Otherwise returns NO_ERROR_MSG.
      */
    private String check_Legal_Keywords(){
        int i = 0;
        String str;

        while(i<infix.length()){
            while(i<infix.length()){
                String temp = Character.toString(infix.charAt(i));

                if(temp.equals(" ") || temp.equals(COMMA) || this.is_Open_Brace(temp)
                       || this.is_Close_Brace(temp) || this.is_Operator(temp)){
                    ++i;
                }
                else{
                    break;
                }//......... Now we got the keyword stop it...
            }                        //........ AVOID ALL NON LEGAL KEYWORDS...

            str ="";

            while(i<infix.length()){
                String temp = Character.toString(infix.charAt(i));
                if(temp.equals(" ") || temp.equals(COMMA) || this.is_Open_Brace(temp)
                       || this.is_Close_Brace(temp) || this.is_Operator(temp)){
                    break;
                }
                else{
                    ++i;
                    str += temp;
                }//............. This is used to collect all the keywords character one by one..
            }                    //.......... GET ALL THE KEYWORDS CHAR. APPENDED ON SAME STRING...
            if(!this.is_Legal(str)){
               return ("Error 2.0... The Keyword at the index position "+i+" is incorrect.");
            }
        }
        return NO_ERROR_MSG;
    }

    /**
     * This method is used to check whether the String passed represents<b> A legal identifier</b> or not..
     * @param str is the string to be tested
     * @return true if str is a legal identifier
     *         otherwise returns false
     */
    private boolean is_Legal(String str){
        if(str.equals("")){
            return true;
        }

        for(int i=0 ; i<ExpressConst.length ; ++i){ //.........  A KEYWORD
            if(str.equalsIgnoreCase(ExpressConst[i]))
                return true;
        }

        if(str.equals("PI") || str.equals("E")){//..... A MATHEMATICAL SYMBOL
            return true;
        }

         if(str.equalsIgnoreCase("x")){//.......... A VARIABLE
             return true;
         }

         try{                         //..........  A NUMBER
             double d = Double.parseDouble(str);
             return true;
         }
         catch(Exception e){//.............. ANYTHING ELSE
            return false;
         }
    }
   /**
    * This method actually checks the grammar or the grammar of the expression..
    * An expression is divided into certains types of identifiers.
    * This method checks whether next identifier is valid w.r.t. previous identifer or not
    * @return True if grammar is correct..
    *         Otherwise returns false..
    */
    private String check_Legal_Expression(){
        if(infix_converted.equals(null) || infix_converted.equals("")){  //.. EXPRESSION SHOULD NOT BE NULL OR EMPTY..
            return "Error 3.0... Expression should contain something";
        }

        else if(!this.is_Open_Brace(infix_converted.charAt(0)+"")){   //...... EXPRESSION IS ALWAYS SUPPOSED TO START
            return "Error 3.1... Expression should start with open Brace";       //...... WITH AN OPEN BRACE...
        }

        else if(!this.is_Close_Brace(infix_converted.charAt(infix_converted.length()-1)+"")){
            return "Error 3.2... Expression should end with close Brace";
        }                                         //.... EXPRESSION IS ALWAYS SUPPOSED TO END WITH A CLOSE BRACE...

        /*
         * STATUS CAN HAVE FOLLOWING VALUE...
         * STATUS = 0  OPEN BRACE
         * STATUS = 1  CLOSE BRACE
         * STATUS = 2  VARIABLE
         * STATUS = 3  KEYWORD
         * STATUS = 4  OPERATOR
         * STATUS = 5  COMMA
         */

         int status = 0;//........... STATUS IS ALWAYS 1 @ THE BEGINNING BCOZ OF STARTING OPEN BRACE IN EXPR..
         int i=1; //.... START WITH SECOND VARIABLE.
         String current = null;

         while(i<infix_converted.length()){
             current = Character.toString(infix_converted.charAt(i));

             if(current.equals(" ")){
                 continue;
             }

             if(this.is_Number(current)){ //........... if its a number then extract entire number
                String temp = current;        //.......... not just one digit.
                while(this.is_Number(temp)){
                   ++i;
                   current = Character.toString(infix_converted.charAt(i));
                   temp += current;
                }
                --i; //............ GO BACK UPTO THAT POINT IS NUMBER...
                current = Character.toString(infix_converted.charAt(i));
             }//.......... NUMBER RETRIEVAL LOOP.......

             switch(status){
                 case 0:                            //......... FOR OPEN BRACES
                     if(this.is_Open_Brace(current)){
                         status = 0;
                         break;
                     }
                     else if(this.is_Variable(current)){
                         status = 2;
                         break;
                     }// END OF ELSE IF...
                     else if(this.is_Keywords(current)){
                         status = 3;
                         break;
                     }
                     return ("Error Type 3.4... Error at the position "+i);
                                                           //... something else.. means illegal.

                case 1:                          //.......... FOR CLOSE BRACES
                    if(this.is_Close_Brace(current)){
                        status = 1;
                        break;
                    }
                    else if(this.is_Operator(current)){
                        status = 4;
                        break;
                    }
                    else if(this.is_Comma(current)){
                        status = 5;
                        break;
                    }
                    return ("Error Type 3.5... Error at the position "+i);

                case 2://................................... FOR VARIABLES..
                     if(this.is_Close_Brace(current)){
                        status = 1;
                        break;
                    }
                     else if(this.is_Operator(current)){
                         status = 4;
                         break;
                     }
                     else if(this.is_Comma(current)){
                         status = 5;
                         break;
                     }
                     return ("Error Type 3.6... Error at the position "+i);

                case 3://.......................... FOR KEYWORDS...
                     if(this.is_Open_Brace(current)){
                         status = 0;
                         break;
                     }
                     return ("Error Type 3.7... Error at the position "+i);

                case 4://................................ FOR OPERATORS.
                     if(this.is_Open_Brace(current)){
                         status = 0;
                         break;
                     }
                     else if(this.is_Variable(current)){
                         status = 2;
                         break;
                     }
                     else if(this.is_Keywords(current)){
                         status = 3;
                         break;
                     }
                     return ("Error Type 3.8... Error at the position "+i);

                case 5://................................ FOR COMMA
                    if(this.is_Variable(current)){
                        status = 2;
                        break;
                    }
                    else if(this.is_Open_Brace(current)){
                        status = 0;
                        break;
                    }
                    else if(this.is_Keywords(current)){
                        status = 3;
                        break;
                    }
                    return ("Error Type 3.9... Error at position "+i);
             }

             ++i;
         }//...... END OF WHILE LOOP....

        return NO_ERROR_MSG;
    }

    /**
     * This method is used to check whether all the operators and expression constants have sufficient number
     * of arguments or not...
     * @return Error value if error is present
     *         otherwise returns NO_ERROR_MSG
     */
    private String check_No_Of_Arguments(){
        int i=0 ;
         do{
            String ch = Character.toString(infix_converted.charAt(i));
            if(this.is_Keywords(ch)){
            String oper = this.getExpression(ch);
               //........... ONLY LOG AND EXP TAKE TWO ARGUMENTS REST ALL TAKE ONE ARGUMENT
               int no_args = this.no_of_args(oper);
               int count = 0;
               int temp = i;
               if(no_args == 1){
                   do{
                       ++temp;
                       if(count == 1 && ch.equals(COMMA)){
                           return "Error 4.0... Operator "+oper+" has two arguments. Required only one.";
                       }
                       ch = Character.toString(infix_converted.charAt(temp));
                       if(this.is_Open_Brace(ch)){
                           ++count;
                       }
                       else if(this.is_Close_Brace(ch)){
                           --count;
                       }
                   }while(count != 0);
               }
               else if(no_args == 2){
                   boolean result = false;
                   do{
                       ++temp;
                       if(count == 1 && ch.equals(COMMA)){
                           result = true;
                       }
                       ch = Character.toString(infix_converted.charAt(temp));
                       if(this.is_Open_Brace(ch)){
                           ++count;
                       }
                       else if(this.is_Close_Brace(ch)){
                           --count;
                       }
                   }while(count != 0);

                  if(result == false){
                      return "Error 4.1... Operator "+oper+" has one arguments required two";
                  }
               }
            }
            ++i;
         }while(i<infix_converted.length());

         return NO_ERROR_MSG;
    }
/* **************************** Method Used to calculate value of post String at a particular value *************** */
/**
 * The method used to calculate value of the post expression for x = value
 * @param post is the expression to be evaluated in postfix form.
 * @param value is the value at which the expression is to be evaluated
 * @return the double value of evaluated expression.
 */
public double getValue(String post, double value)
{
   post = this.converter(post);
   post = infixToPostfix(post);
   post = post.replaceAll("X",value+"");
   post = post.replaceAll(" ","");
   Stack<String> stc = new Stack<String>();
   String str = null;

   int i=0;
   while(i<post.length() ){
       str = "";
       while(post.charAt(i) != ','){
          str += Character.toString(post.charAt(i));
          ++i;
       }
       if(this.is_Number(str)){
          stc.push(str);
       }
       else{
           int no_of_args = this.no_of_args(str);

          if(no_of_args == 1){
              double temp1 = Double.parseDouble(stc.pop());
              double val = evaluate(str, temp1);

              stc.push(val+"");
          }
          else if(no_of_args == 2){
              double temp2 = Double.parseDouble(stc.pop());//......... LASTLY ADDED IS THE FIRST TO BE REMOVED
              double temp1 = Double.parseDouble(stc.pop());
              double val = evaluate(str, temp1, temp2);

              stc.push(val+"");
          }
       }
       ++i;
   }
   // DUMMY CODE...
   return Double.parseDouble(stc.pop());
}

/* ************************************************************************************************************ */
/**
 * The function used to evaluate binary operators value
 * @param op is the operator applied
 * @param left is the left arg. to the operator
 * @param right is the right argument to the operator
 * @return is the value returned after applying the operator.
 */
private double evaluate(String op, double left, double right){
     if(op.equals("+")){
         return left+right;
     }
     else if(op.equals("-")){
         return left-right;
     }
     else if(op.equals("*")){
         return left*right;
     }
     else if(op.equals("/")){
         return left/right;
     }
     else if(op.equals("^")){
         return Math.pow(left, right);
     }
     op = this.getExpression(op);
     if(op.equalsIgnoreCase("EXP")){
         return Math.pow(left, right);
     }
     else if(op.equalsIgnoreCase("LOG")){
        return (double)(Math.log(right)/Math.log(left));
     }

     // DUMMY CODE...
     return 0;
}

/**
 *Overridden evaluate function.
 * it is used to evalutate unary operators
 * @param op is the operator used.
 * @param value is the value on which operator is to be applied
 * @return is the value returned when operator is applied.
 */
private double evaluate(String op, double value){
     op = this.getExpression(op);

     if(op.equalsIgnoreCase("Sin")){
         return Math.sin(value);
     }
     else if(op.equalsIgnoreCase("Cos")){
        return Math.cos(value);
     }
     else if(op.equalsIgnoreCase("Tan")){
        return Math.tan(value);
     }

     else if(op.equalsIgnoreCase("Cosec")){
        return (double)(1/Math.sin(value));
     }
     else if(op.equalsIgnoreCase("Sec")){
        return (double)(1/Math.cos(value));
     }
     else if(op.equalsIgnoreCase("Cot")){
        return (double)(1/Math.tan(value));
     }

     else if(op.equalsIgnoreCase("ASIN")){
        return Math.asin(value);
     }
     else if(op.equalsIgnoreCase("ACos")){
        return Math.acos(value);
     }
     else if(op.equalsIgnoreCase("ATan")){
        return Math.atan(value);
     }

     else if(op.equalsIgnoreCase("ACosec")){
        return Math.asin((double)(1/value));
     }
     else if(op.equalsIgnoreCase("ASec")){
        return Math.acos((double)(1/value));
     }
     else if(op.equalsIgnoreCase("ACot")){
        return Math.atan((double)(1/value));
     }

     //DUMMY CODE...
     return 0;
}

/**
 * This method is used to get no. of argument that an operatorrequires.
 * @param op is value whose no. of arguments are to be determined.
 * @return no. of arguments required by the operator.
 */
public int no_of_args(String op){
   for(int i=0 ; i<OPERATORS.length ; ++i){
      if(op.equalsIgnoreCase(OPERATORS[i])){
          return 2;
      }
   }
   String temp = this.getExpression(op);
    if(temp.equalsIgnoreCase("LOG") || temp.equalsIgnoreCase("EXP")){
           return 2;
       }

   for(int i=0 ; i<ExpressConst.length ; ++i){
       if(op.equalsIgnoreCase(ExpressConst[i])){
          return 1;
       }
   }

   for(int i=0 ; i<SYMBOLS.length ; ++i){
       if(op.equalsIgnoreCase(SYMBOLS[i])){
          return 1;
       }
   }

   return -1;
}

/**
 * The method used to convert infix expression into corresponding postfix expression.
 * @param s is the string in infix form which is to be converted into its corresponding postfix value
 * @return the postfix form of the expression
 */
    private String infixToPostfix(String s){

        String infix_rev = this.converter(s);
        infix_rev = infix_rev.replaceAll(" ","");
        Stack<String> stc = new Stack<String>();
        String post = "";

        int i=0;

        while(i<infix_rev.length()){     //.............  DON'T FORGET THE INCREMENT VARIABLE
            String ch = Character.toString(infix_rev.charAt(i));

            if(ch.equals(" ")||ch.equals(",")){//......... change made here...
                ++i;
                continue;
            }
            if(this.is_Number(ch)){         //.................... NUMBER CAN BE MULTIDIGIT....
               while(i<infix_rev.length() && this.is_Number(ch)){
                  post += ch;
                  ++i;
                  ch = Character.toString(infix_rev.charAt(i));
               }
               post += COMMA;
               continue;
            }
            else if(this.is_Variable(ch)){
                post += ch;
                post += COMMA;
            }
            else if(this.is_Close_Brace(ch)){
                while(!this.is_Open_Brace(stc.peek())){
                    post += stc.pop();
                    post += COMMA;
                }
                stc.pop();
            }
            else if(this.is_Open_Brace(ch)){
                stc.push(ch);
            }
            else{
                do{
                    if(stc.isEmpty() || this.is_Open_Brace(stc.peek())){
                        break;
                    }
                    int pr_stc = this.getPriority(stc.peek());
                    int pr_current = this.getPriority(ch);

                    if(pr_current <= pr_stc){
                        post += stc.pop();
                        post += COMMA;
                    }
                    else{
                        break;
                    }
                }while(!stc.isEmpty());
                stc.push(ch);
            }
            ++i;
        }

        while(!stc.isEmpty()){
            post += stc.pop();
            post += COMMA;
        }

        return post;
}
/* ************************************************************************************************************** */

/**
 * This method is used to get the symbol of given expression costant.
 * used to convert an expression into symbolic form for processing purpse.
 *
 * @param value is the expression constant whose corresponding symbol is to be determined
 * @return is the symbol of corresponding expression constant
 *          if no match found then return the arugment itself
 */
public String getSymbol(String value){
    for(int i=0 ; i<ExpressConst.length ; ++i){
        if(value.equalsIgnoreCase(ExpressConst[i])){
            return SYMBOLS[i];
        }
  }
  //.... DUMMY CODE... NEVER EXECUTES...
  return value;
}

/**
 *  This method is used to get the expression constant of corresponding symbol.
 *  used to decode the expression for display purpse.
 *
 * @param symbol
 * @return
 */
public String getExpression(String symbol){
    for(int i=0 ; i<SYMBOLS.length ; ++i){
       if(symbol.equalsIgnoreCase(SYMBOLS[i])){
           return ExpressConst[i];
       }
    }
    //.... DUMMY CODE...
    return symbol;
}
/* ************************************************************************************************************** */

/* *****************Basic Operatons on Strings...******************************************************** */

/**
 * This method is used to determine whether two values are equal or not.
 * this method comapares them with error value to determine whether they are approximately equal or not.
 * @param x is the first value
 * @param y is the second value to be compared.
 * @return is true if they are approximately equal
 *          Otherwise returns false
 */
private boolean similarTo(double x, double y){
    return Math.abs(x-y) < ERROR;
}

/**
 * The method acts as an interpreter and adds two Strings
 * @param val1 the string1 to be added
 * @param val2 the string2 to be added
 * @return the addition of both strings
 */
public String add(String val1, String val2){
    double no_val1 = NULL_INITIALIZATION;
    double no_val2 = NULL_INITIALIZATION;

    try{
        no_val1 = Double.parseDouble(val1);
    }
    catch(Exception e){
        //......... DO NOTHING....
    }
    try{
        no_val2 = Double.parseDouble(val2);
    }
    catch(Exception e){
        //........ DO NOTHING....
    }

    if(no_val1 != NULL_INITIALIZATION && no_val2 != NULL_INITIALIZATION){
        return no_val1+no_val2+"";
    }

    if(val1.equals(INFINITY) || val2.equals(INFINITY)){
        return INFINITY;
    }

    else if(val1.equals(NEGINFINITY) || val2.equals(NEGINFINITY)){
        return NEGINFINITY;
    }
    else if(val1.equals(multiply("-1",val2)) || val2.equals(multiply("-1",val1))){
        return ZERO;
    }
    if(val1.equals(ZERO)){
        return val2;
    }
    else if(val2.equals(ZERO)){
        return val1;
    }

    else if(this.similarTo(no_val1,0) && !this.similarTo(no_val1, NULL_INITIALIZATION)){
        return val2;
    }
    else if(this.similarTo(no_val2,0) && !this.similarTo(no_val2, NULL_INITIALIZATION)){
        return val1;
    }
    else if(this.similarTo(no_val1+no_val2,0)){
        return ZERO;
    }

    else{
        return "("+val1+"+"+val2+")";
    }
}

/**
 * The method acts as an interpreter and subtracted two Strings
 * @param val1 the string1 from which another is subtracted
 * @param val2 the string2 which is subtracted
 * @return the subtraction of both strings
 */
public String subtract(String val1, String val2){
    double no_val1 = NULL_INITIALIZATION;
    double no_val2 = NULL_INITIALIZATION;

    try{
        no_val1 = Double.parseDouble(val1);
    }
    catch(Exception e){
        //......... DO NOTHING....
    }
    try{
        no_val2 = Double.parseDouble(val2);
    }
    catch(Exception e){

    }
    if(no_val1 != NULL_INITIALIZATION && no_val2 != NULL_INITIALIZATION){
        return no_val1-no_val2+"";
    }

    if(val1.equals(INFINITY) || val2.equals(NEGINFINITY)){
        return INFINITY;
    }
    else if(val1.equals(NEGINFINITY) || val2.equals(INFINITY)){
        return NEGINFINITY;
    }
    if(val1.equals(ZERO)){
        return this.multiply("-1",val2);
    }
    else if(val2.equals(ZERO)){
        return val1;
    }
    else if(val1.equals(val2)){
        return ZERO;
    }

    else if(this.similarTo(no_val1, 0) && !this.similarTo(no_val1, NULL_INITIALIZATION)){
        return this.multiply("-1",val2);
    }
    else if(this.similarTo(no_val2, 0) && !this.similarTo(no_val2, NULL_INITIALIZATION)){
        return val1;
    }
    else if(this.similarTo(no_val1, no_val2) && !this.similarTo(no_val1, NULL_INITIALIZATION)){
        return ZERO;
    }

    else{
        return "("+val1+"-"+val2+")";
    }
}

/**
 * The method acts as an interpreter and multiplied two Strings
 * @param val1 the string1 to be multiplied
 * @param val2 the string2 to be multiplied
 * @return the multiplication of both strings
 */
public String multiply(String val1, String val2){
    double no_val1 = NULL_INITIALIZATION;
    double no_val2 = NULL_INITIALIZATION;

    try{
        no_val1 = Double.parseDouble(val1);
        if(no_val1 < 0){
            val1 = "(0"+val1+")";
        }
    }
    catch(Exception e){
        //......... DO NOTHING....
    }
    try{
        no_val2 = Double.parseDouble(val2);
        if(no_val2 < 0){
            val2 = "(0"+val2+")";
        }
    }
    catch(Exception e){
        //........ DO NOTHING...
    }

    if(no_val1 != NULL_INITIALIZATION && no_val2 != NULL_INITIALIZATION){
         return no_val1*no_val2+"";
    }

    if(val1.equals(INFINITY) || val2.equals(INFINITY)){
         return INFINITY;
    }                                        //.................. WHAT TO DO FOR INFINITY...

     else if(val1.equals(NEGINFINITY) || val2.equals(NEGINFINITY)){
         return NEGINFINITY;
     }

     else if(val1.equals(ZERO) || val2.equals(ZERO)){
         return ZERO;
     }                                        //.................... WHAT TO DO FOR ZERO....
     else if(val1.equals("1/"+val2) || val2.equals("1/"+val1)){
         return ONE;
     }
     else if(val1.equals(ONE)){
         return val2;
     }                                       //................. WHAT TO DO FOR ONE....
     else if(val2.equals(ONE)){
         return val1;
     }

     else if(this.similarTo(no_val1, 0) && !this.similarTo(no_val1, NULL_INITIALIZATION)){
         return ZERO;
     }
     else if(this.similarTo(no_val2, 0)&& !this.similarTo(no_val1, NULL_INITIALIZATION)){
         return ZERO;
     }
     else if(this.similarTo(no_val1, 1)){
         return val2;
     }
     else if(this.similarTo(no_val2, 1)){
         return val1;
     }

     else{
         return "("+val1+"*"+val2+")";
     }
}

/**
 * The method acts as an interpreter and divides two Strings
 * @param val1 the string1 is componento
 * @param val2 the string2 to be dividendo
 * @return the division of both strings
 */
public String divide(String val1, String val2){
    double no_val1 = NULL_INITIALIZATION;
    double no_val2 = NULL_INITIALIZATION;

    try{
        no_val1 = Double.parseDouble(val1);
    }
    catch(Exception e){
       //......... DO NOTHING....
    }
    try{
        no_val2 = Double.parseDouble(val2);
    }
    catch(Exception e){
        //........ DO NOTHING...
    }

    if(no_val1 != NULL_INITIALIZATION && no_val2 != NULL_INITIALIZATION){
        return no_val1/no_val2+"";
     }

    if(val1.equals(INFINITY) || val2.equals(ZERO)){
        return INFINITY;
     }                               //...............  WHAT TO DO FOR INFINITY AND ZERO....
     else if(val1.equals(ZERO) || val2.equals(INFINITY)){
         return ZERO;
     }
     else if(val1.equals(val2)){
         return ONE;
     }
     else if(val2.equals(ONE)){
         return val1;
     }                        //....................... WHAT TO DO FOR ONE....

     else if(this.similarTo(no_val1, 0) && !this.similarTo(no_val1, NULL_INITIALIZATION)){
         return ZERO;
     }
     else if(this.similarTo(no_val1, no_val2) && !this.similarTo(no_val1, NULL_INITIALIZATION)){
         return ONE;
     }
     else if(this.similarTo(no_val2, 1)){
         return val1;
     }

     else{
         return "("+val1+"/"+val2+")";
     }
}

/**
 * The method acts as an interpreter and Performs power function
 * @param val1 the string1 is the base
 * @param val2 the string2 is the exponent
 * @return the power value of both strings
 */
public String pow(String val1, String val2){
    double no_val1 = NULL_INITIALIZATION;
    double no_val2 = NULL_INITIALIZATION;

    try{
        no_val1 = Double.parseDouble(val1);
    }
    catch(Exception e){
        //......... DO NOTHING....
    }
    try{
        no_val2 = Double.parseDouble(val2);
    }
    catch(Exception e){
        //........ DO NOTHING...
    }

    if(no_val1 != NULL_INITIALIZATION && no_val2 != NULL_INITIALIZATION){
        return Math.pow(no_val1,no_val2)+"";
     }

     if(val1.equals(INFINITY) || val2.equals(INFINITY)){
         return INFINITY;
     }                               //.......................... WHAT TO DO FOR INFINITY

     else if(val1.equals(ONE) || val2.equals(ZERO)){
         return ONE;
     }                            //.............................. WHAT TO DO FOR ZERO
     else if(val1.equals(ZERO)){
         return ZERO;
     }

     else if(val2.equals(ONE)){ //.............................. WHAT TO DO FOR ONE
         return val1;
     }

     else if(this.similarTo(no_val1, 1)){
         return ONE;
     }
     else if(this.similarTo(no_val2, 0) && !this.similarTo(no_val2, NULL_INITIALIZATION)){
          return ONE;
     }

     else if(this.similarTo(no_val1, 0) && !this.similarTo(no_val1, NULL_INITIALIZATION)){
         return ZERO;
     }
     else if(this.similarTo(no_val2, 1)){
         return val1;
     }

     else{
         return "("+val1+"^"+val2+")";
     }
}

/**
 * The method acts as an interpreter and calculates Log of two Strings
 * @param val1 the string1 is the base
 * @param val2 the string2 is the value whose log is to be calculated
 * @return the Log of both strings
 */
public String Log(String val1, String val2){
    double no_val1 = NULL_INITIALIZATION;
    double no_val2 = NULL_INITIALIZATION;

    try{
        no_val1 = Double.parseDouble(val1);
    }
    catch(Exception e){
        //......... DO NOTHING....
    }
    try{
        no_val2 = Double.parseDouble(val2);
    }
    catch(Exception e){
        //........ DO NOTHING...
    }

    if(no_val1 != NULL_INITIALIZATION && no_val2 != NULL_INITIALIZATION){
        return (double)(Math.log(no_val1)/Math.log(no_val2))+"";
    }

    if(val1.equals(val2)){
         return ONE;
    }
    else if(val1.equals(INFINITY)){
        return ZERO;
    }
    else if(val2.equals(ONE)){
        return ZERO;
    }
    else if(val1.equals("E") || val1.equals(Math.E+"")){
        return "LOG("+E+","+val2+")";
    }

    else if(this.similarTo(no_val1, no_val2) && !this.similarTo(no_val2, NULL_INITIALIZATION)){
        return ONE;
    }
    else if(this.similarTo(no_val2, 1)){
        return ZERO;
    }

    else{
        return "(Log("+val1+", "+val2+"))";
    }
}

/* **************************************************************************************************************** */

/**
 * This method is the checking function used to confirm whether arg represents any type of open brace
 * @param str the string to be checked
 * @return True if argument is one of 3 types of open brace
 *         Otherwise returns false
 */
    public boolean is_Open_Brace(String str){
        if (str.equals("(") ){
            return true;
         }
         else if(str.equals("[")){
            return true;
          }
          else if(str.equals("{")){
              return true;
          }
          return false;
    }

/**
 * This method is the checking function used to confirm whether arg represents any type of close brace
 * @param str the string to be checked
 * @return True if argument is one of 3 types of close brace
 *         Otherwise returns false
 */
   public boolean is_Close_Brace(String str){
         if (str.equals(")") ){
            return true;
         }
         else if(str.equals("]")){
            return true;
         }
         else if(str.equals("}")){
              return true;
          }
         return false;
    }

/**
 * This method is the checking function used to confirm whether arg represents any type of variable
 * Variable includes:-
 * 1. x
 * 2. Maths constants (PI or E)
 * 3. A number
 * @param str the string to be checked
 * @return True if argument is a variable
 *         Otherwise returns false
 */
    public boolean is_Variable(String str){
        if(str.equalsIgnoreCase("x")){
            return true;
        }
        else if(str.equals("P") || str.equals("E")){
           return true;
        }
        else{
            try{
                double temp = Double.parseDouble(str);
                return true;
            }
            catch(Exception e){
                return false;
            }
        }
    }

/**
 * This method is the checking function used to confirm whether arg represents any type of keyword
 * This method checks from symbol list only.
 * So, converted expressions can only be examined
 * @param str the string to be checked
 * @return True if argument is a keyword
 *         Otherwise returns false
 */
public boolean is_Keywords(String str){
    for(int i=0 ; i<SYMBOLS.length ; ++i){
        if(str.equals(SYMBOLS[i])){
            return true;
        }
    }
    return false;
}

/**
 * This method is the checking function used to confirm whether arg represents any type of algebraic operator
 * @param str the string to be checked
 * @return True if argument is one of algebraic operator
 *         Otherwise returns false
 */
public boolean is_Operator(String str){
    if(str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals("^")){
        return true;
    }
    return false;
}

/**
 * This method is the checking function used to confirm whether arg represents a comma
 * @param str the string to be checked
 * @return True if argument is a comma
 *         Otherwise returns false
 */
public boolean is_Comma(String str){
    return str.equals(COMMA);
}

/**
 * This method is the checking function used to confirm whether arg represents a number
 * @param str the string to be checked
 * @return True if argument is a number
 *         Otherwise returns false
 */
public boolean is_Number(String str){

   if(str.equals("0") || str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4") || str.equals("5") || str.equals("6") || str.equals("7") || str.equals("8") || str.equals("9") || str.equals(".")){
       return true;
   }

   try{
       double temp = Double.parseDouble(str);
       return true;
   }
   catch(Exception e){
      return false;
   }
}
/* ***************************************************************************************************************** */

/**
 * Private method used to get priority of any operator, brace and keywords symbol.
 * Its a private method used to convert infix expression into corresponding postfix expression for evalution
 * purpose
 * @param str whose priority is to be returned
 * @return the priority of string if it is defined
 *         Otherwise returns -1 as null value
 */
    private int getPriority(String str){
        if(this.is_Open_Brace(str) || this.is_Close_Brace(str)){
            return 4;
        }
        else if(str.equals("/") || str.equals("*")){
            return 2;
        }
        else if(str.equals("+") || str.equals("-")){
            return 1;
        }
        else if(str.equals("^")){
            return 3;
        }
        else{
            for(int i=0 ; i<SYMBOLS.length ; ++i){
                if(str.equals(SYMBOLS[i])){
                    return 3;
                }
            }
        }
        // DUMMY CODE...
        return -1;
    }
}
