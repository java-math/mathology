/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simultaneous_solver;

/**
 *
 * @author Mahesh
 * @since June 29, 2009
 */
public class Matrix {
    /**
     * A 2D array storing elements of the matrix.
     * Each element can be double or real. Hence represented by double.
     */
    private double[][] matrix;

    /**
     * The variable containing no. of rows that matrix have.
     */
    private int no_of_row;

    /**
     * The variable containg no. of columns that matrix can have.
     */
    private int no_of_column;
/**
 * Constructor 1.
 * The parameterized constructor.
 * Initializing the matrix with no. of rows and no. of columns.
 * Preferred way of initilization by the user.
 * @param row the no. of rows in the 2D matrix.
 * @param column the no. of columns in the 2D matrix.
 */
    public Matrix(int row, int column){
        this.no_of_row = row;
        this.no_of_column = column;
        matrix = new double[no_of_row][no_of_column];
        reinitialize();
    }

    /**
     * Constructor 2.
     * The Default constructor creating a 3 X 3 square matrix.
     * Generally not recommended for the user.
     */

    public Matrix(){
        this.no_of_row = 3;
        this.no_of_column = 3;
        matrix = new double[no_of_row][no_of_column];
        reinitialize();
    }
/**
 * UNTESTED
 * Constructor 3.
 * This constructor type 3 creates a new Matrix having all elements similar
 * to the parameter matrix.
 * Even though the param and new matrix have same value but still they do not
 * have same copy.
 * Thus changes made in param won't affect the value of new matrix.
 * @param param The matrix whose value is used to initialize the new matrix.
 */
    public Matrix(Matrix param){
        this.no_of_row = param.get_Row();
        this.no_of_column = param.get_Column();
        this.matrix = new double[no_of_row][no_of_column];

        for(int i=0 ; i<no_of_row ; ++i){
            for(int j=0 ; j<no_of_column ; ++j){
                this.matrix[i][j] = param.getElement(i, j);
            }
        }
    }
/**
 * Constructor 4. :: TESTED
 * This constructor copies the parameter matrix with the specified order.
 * i.e. the limit to what extent the matrix is to be copied is specified.
 * @param param The matrix to be copied.
 * @param left The left boundary of param.
 * @param right The right boundary of param.
 * @param top The top boundary of param.
 * @param bottom The bottom boundary of param.
 */
public Matrix(Matrix param, int top, int bottom, int left, int right){
        this.no_of_column = right-left;
        this.no_of_row = bottom-top;
        this.matrix = new double[no_of_row][no_of_column];

        for(int i=0 ; i<no_of_row ; ++i){
            for(int j=0 ; j<no_of_column ; ++j){
                this.matrix[i][j] = param.getElement(top+i, left+j);
            }
        }
    }
/**
 * Construcotr 5. ::  TESTED
 * This constructor copies the all four matrix to the one single value such that
 *        [A0  A1]
 * this = |      |
 *        [A2  A3]
 *It includes compatibility check where
 * 1. No. of row(A0) = No. of row(A1)
 * 2. No. of row(A2) = No. of row(A3)
 * 3. No. of col(A0) = No. of col.(A2)
 * 4. No. of col(A1) = No. of col.(A3)
 * @param A0 The upper left matrix to be copied.
 * @param A1 The upper right matrix to be copied.
 * @param A2 The bottom left matrix to be copied.
 * @param A3 The bottom right matrix to be copied.
 */
    public Matrix(Matrix A0, Matrix A1, Matrix A2, Matrix A3){
        try{
            if((A0.get_Row() != A1.get_Row()) ||
                (A2.get_Row() != A3.get_Row())||
                (A0.get_Column() != A2.get_Column())||
                (A1.get_Column() != A3.get_Column())
               )
                throw new Incompatibility_Exception();
        }
        catch(Incompatibility_Exception e){
            e.display();
            return;
        }

        this.no_of_row = A0.get_Row() + A2.get_Row();
        this.no_of_column = A0.get_Column() + A1.get_Column();
        this.matrix = new double[no_of_row][no_of_column];

        int part_row = A0.get_Row();
        int part_col = A0.get_Column();

        for(int i=0 ; i<this.no_of_row ; ++i){
            for(int j=0 ; j<this.no_of_column ; ++j){
                double temp = 0.0;
                if(i < part_row && j < part_col){
                    temp = A0.getElement(i, j);
                }
                else if(i < part_row && j >= part_col){
                    temp = A1.getElement(i, j-part_col);
                }
                else if(i >= part_row && j < part_col){
                    temp = A2.getElement(i-part_row, j);
                }
                else if(i >= part_row && j >= part_col){
                    temp = A3.getElement(i-part_row, j-part_col);
                }
                this.setElement(temp, i, j);
            }
        }
    }

/**
     * TESTED
     * The initialization method of entire matirx.
     * No. of rows and columns can not be changed.
     * Only elements can be reset.
     * Each element is set to 0 + 0*i value.
     */
    public void reinitialize(){

        for(int i=0 ; i<no_of_row ; ++i){
            for(int j=0 ; j<no_of_column ; ++j){
                matrix[i][j] = 0.0;
            }
        }
    }

/**
 *  TESTED
 * A static method used to get Identity matrix of any order.
 * @param order of which square matrix is required.
 * @return The Identity matrix of size (order X order).
 */
    public static Matrix getIdentity(int order){
        Matrix identity = new Matrix(order, order);

        double id = 1;
        for(int i=0 ; i<order ; ++i){
            identity.setElement(id, i, i);
        }

        return identity;
    }
    /**
     * TESTED
     * The method through which specified element of the matrix can be modified.
     * @param ele The new value of the matrix element.
     * @param row The row in the matrix at which element has to be placed.
     * @param col The column in the matrix at which element has to be placed.
     */
    public void setElement(double ele, int row, int col ){
        this.matrix[row][col] = ele;
    }

    /**
     * TESTED
     * The method which retrieves the element present at the specified row
     * and column in the matrix.
     * @param row The no. of row specified by the user.
     * @param col The no. of column specified by the user.
     * @return The element present at the specified row and column
     */
    public double getElement(int row, int col){
        return this.matrix[row][col];
    }

    /**
     * TESTED
     * No. of rows can only be viewed.
     * They can be initialized only at the contstruction time.
     * They can not further be modified.
     * @return  Returns no. of row in the matrix
     */
    public int get_Row(){
        return this.no_of_row;
    }
    /**
     * TESTED
     * No. of columns can only be viewed.
     * They can be initialized in the constructor.
     * They can not further be modified.
     * @return Returns the no. of columns in the matrix.
     */
    public int get_Column(){
        return this.no_of_column;
    }

    /**
     * TESTED
     * The Method returns negation of the matrix.
     * the original matrix is not modified.
     * the modified matrix is returned.
     * <b>Negation(A+Bi) = -A - Bi </b>
     * @return Negation of the matrix.
     */
    public Matrix getNegation(){
        Matrix temp = new Matrix(this.get_Row(),this.get_Column());
        for(int i=0 ; i<this.get_Row() ; ++i){
            for(int j=0 ; j<this.get_Column() ; ++j){
                temp.setElement(-1*this.getElement(i, j),i,j);
            }
        }
        return temp;
    }

/**
 * TESTED
 * The add method of Matrix class.
 * Adds two Matrix objects and returns the added value.
 * The method checks for compatibility of Addition.
 * If they are compatible then one to one corresponding adding is done.
 * @param param The Matrix object that has to be added to the calling object.
 * @return NULL if incompatible  OTHERWISE
 *         Addition of both matrix.
 */
    public Matrix add(Matrix param){
        try{
            if(this.get_Row() != param.get_Row() ||
                    this.get_Column() != param.get_Column()){
                throw new Incompatibility_Exception();
            }
        }
        catch(Incompatibility_Exception e){
            e.display();
            return null;
        }
        Matrix mat = new Matrix(this.get_Row(), this.get_Column());

        for(int i=0 ; i<this.get_Row() ; ++i){
            for(int j=0 ; j<this.get_Column() ; ++j){
                double temp = this.getElement(i, j) + param.getElement(i, j);
                mat.setElement(temp, i, j);
            }
        }

        return mat;
    }
/**
 * TESTED
 * The subtract Method subtracting the param from calling value.
 * @param param The value to be subtracted.
 * @return The subtraction of the calling matrix and param.
 */
    public Matrix subtract(Matrix param){
        param = param.getNegation();
        return this.add(param);
    }
/**
 * TESTED
 * The multiplication operation is implemented by this Method.
 * For multiplication the compatiblity is needed.
 * For C = A*B::
 * No. of columns(A) = No. of rows(B)
 * C = (No. of rows of(A) X No. of columns(B)
 * <b>C[i][j] = sum{A[i][k]*B[k][j]}</b>
 *
 * @param param The matrix to be multiplied.
 * @return The multipication
 */
    public Matrix multiply(Matrix param){
        try{
            if(this.get_Column() != param.get_Row()){
                throw new Incompatibility_Exception();
            }
        }
        catch(Incompatibility_Exception e){
            e.display();
            return null;
        }

        Matrix temp = new Matrix(this.get_Row(), param.get_Column());

        for(int i=0 ; i<temp.get_Row() ; ++i){
            for(int j=0 ; j<temp.get_Column() ; ++j){
                double temp1 = 0.0;
                for(int k=0 ; k<this.get_Column() ; ++k){
                    double temp2 = this.getElement(i, k)*param.getElement(k, j);
                    temp1 = temp1 + temp2;
                }
                temp.setElement(temp1, i, j);
            }
        }

        return temp;
    }
/**
 * TESTED
 * The Inverse of a square matrix can be obtained by this Method.
 * The compatibility criteria is that the matrix should be a
 * <b> SQUARE MATRIX </b>.
 * To get Inverse Standard formula is used i.e.::
 *<b>Inversion by Partitioning:</b>
 * To inverse a matrix X (size N) by partitioning, the matrix is partitioned into:

       |  A     B  |
   X = |           | with A and C squared matrix with the respective size
       |  C     D  | s0 and s3 following the rule: s0 + s3 = N

The inverse is

            |  B0    B1  |
   Inv(X) = |            |
            |  B2    B3  |

with:

  B0 = Inv(A) + Inv(A)*B*Z*C*Inv(A)
  B1 = -Inv(A)*B*Z
  B2 = - Z*C*Inv(A)
  B3 = Z
  Z  = Inv(D - C*Inv(A)*B)
 *
 * @return Inverse of the calling square matrix.
 */
        public Matrix getInverse(){
        try{
            if(this.get_Row() != this.get_Column()){
                throw new Incompatibility_Exception();
            }
        }
        catch(Incompatibility_Exception e){
            e.display();
            return null;
        }

        Matrix inverse = new Matrix(this);

        if(this.get_Row() == 1 ){
           for(int i=0 ; i<this.get_Column() ; ++i){
              double temp = inverse.getElement(0, i);
              temp = ((double)(1 / temp));
              inverse.setElement(temp, 0, i);
            }
            return inverse;
        }

        else if(this.get_Column() == 1){
            for(int i=0 ; i<this.get_Row() ; ++i){
                double temp = inverse.getElement(i, 0);
                temp = ((double)(1 / temp));
                inverse.setElement(temp, i, 0);
            }
            return inverse;
        }

        else{
            int row = this.get_Row() / 2;
            int column = this.get_Column() / 2;
            int left = 0, right = this.get_Column();
            int top = 0, bottom = this.get_Row();

            Matrix A = new Matrix(this, left, column, top, row);
            Matrix B = new Matrix(this, left, column, row, bottom);
            Matrix C = new Matrix(this, column, right, top, row);
            Matrix D = new Matrix(this, column, right, row, bottom);

            Matrix temp1 = A.getInverse().multiply(B);

            Matrix temp2 = C.multiply(A.getInverse());

            temp2 = temp2.multiply(B);
            temp2 = D.subtract(temp2);
            temp2 = temp2.getInverse();
            Matrix temp3 = temp2;

            temp1 = temp1.multiply(temp3);
            temp2 = C.multiply(A.getInverse());
            temp1 = temp1.multiply(temp2);
            temp1 = temp1.add(A.getInverse());
            Matrix B0 = temp1;

            temp1 = A.getInverse();
            temp1 = temp1.multiply(B);
            temp1 = temp1.multiply(temp3);
            Matrix B1 = temp1.getNegation();

            temp1 = C.multiply(A.getInverse());
            temp1 = temp3.multiply(temp1);
            Matrix B2 = temp1.getNegation();

            Matrix B3 = temp3;

            inverse = new Matrix(B0, B1, B2, B3);

            return inverse;
        }// END OF ELSE IF.........
    }
/**
  * TESTED
  * RETURNS THE MATRIX FORM DISPLAYABLE OF THE MATRIX.
  * EACH ELEMENT OF THE SAME ROW ARE SEPARATED BY ().
  * TWO ELEMENTS HAVING DIFFERENT ARE PRINTED ON THE DIFFERENT LINE.....
  * THE OUTPUT IS OF FORM OF::
  *  {(ele 1),(ele 2),(ele 3),
  *  (ele 4),(ele 5),(ele 6),
  *  (ele 7),(ele 8),(ele 9),}
  */
    @Override public String toString(){
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.###"); // ALLOW ONLY 3 DECIMAL PRECISION.

        String s = "{" ;
        for(int i=0 ; i<this.get_Row() ; ++i){
            for(int j=0 ; j<this.get_Column() ; ++j){
                s += " {" + df.format(this.getElement(i, j)) + " },";
            }
              s += "\n";
        }
        s += "}";
        return s;
    }
}