/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package differentiation;

/**
 *
 * @author Mahesh
 * @since July 27, 2009
 * @version 1.0
 */
public class Integration{
    Expression exp = null;
    String expression = null;
    double integration = 0;
    boolean status = false;
    int npoint=10;

    public Integration(String expression){
        this.expression = expression;
        exp = new Expression(this.expression);
    }

    public String compile(){
        String msg = exp.compile();

        status = msg.equals(Expression.NO_ERROR_MSG);
        return msg;
    }

    public double integrate(double lower, double upper){
        if(status){
            double h = (upper-lower)/npoint;
            double[] f;
            f = new double[npoint+1];

            for(int i=0 ; i<f.length ; ++i){
                f[i] = exp.getValue(expression, lower+i*h);
            }

           // integration = (upper-lower)*(7*values[0] + 32*values[1] + 12*values[2] + 32*values[3] + 7*values[4]) / 90;
           integration = (5*h/299376) * ( 16067*(f[0]+f[10]) + 106300*(f[1]+f[9]) - 48525*(f[2]+f[8]) + 272400*(f[3]+f[7]) -260550*(f[4]+f[6]) + 427368*(f[5]));

          //  integration = (3*h/8) * (f[0] + 3*f[1] + 3*f[2] + f[3]);

            return integration;
        }
        else{
           return -1.0; //............. NULL VALUE.
        }
    }
}