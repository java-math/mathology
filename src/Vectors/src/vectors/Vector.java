/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vectors;

/**
 *
 * @author Rahul
 * @since July 20, 2009
 * @version 1.0
 */
public class Vector {

  private String name;
  private double x_comp;
  private double y_comp;
  private double z_comp;
  private double magnitude;
  private boolean isLocalizable;

  final static double ERROR = 0.00001;

  private Vector(){
      x_comp = 0;
      y_comp = 0;
      z_comp = 0;
      this.isLocalizable = true;
      this.name = "Default";
  }
  public Vector(String name)
  {
      x_comp=0;
      y_comp=0;
      z_comp=0;
      magnitude = 0;
      this.isLocalizable = true;
      this.name = name;
  }

  public Vector(double x,double y,double z,String name, boolean localize)
  {
      x_comp=x;
      y_comp=y;
      z_comp=z;
      magnitude = this.getMagnitude();
      this.name = name;
      this.isLocalizable = localize;
  }

  public Vector(Vector temp,String name){
      this.x_comp = temp.getX();
      this.y_comp = temp.getY();
      this.z_comp = temp.getZ();
      this.name = name;
      this.isLocalizable = temp.getLocalizable();
  }

  public double getX()
  {
      return x_comp;
  }

  public void setX(double x)
  {
      x_comp=x;
  }

    public double getY()
  {
      return y_comp;
  }

  public void setY(double y)
  {
      y_comp=y;
  }

  public double getZ()
  {
      return z_comp;
  }

  public void setZ(double z)
  {
      z_comp=z;
  }

  public double getMagnitude(){
      return Math.sqrt(Math.pow(x_comp,2)+Math.pow(y_comp,2)+Math.pow(z_comp,2));
  }

  public void setName(String name){
      this.name = name;
  }

  public String getName(){
      return this.name;
  }
  public void setLocalizable(boolean result){
      this.isLocalizable = result;
  }
  public boolean getLocalizable(){
      return this.isLocalizable;
  }
  public static Vector getZeroVector(){
      return new Vector("Zero");
  }

  public Vector getNormVector (){
      double mag = this.getMagnitude();
      Vector ret = new Vector("Unit");

      ret.setX(this.getX()/mag);
      ret.setY(this.getY()/mag);
      ret.setZ(this.getZ()/mag);

      return ret;
  }

  public double vectorAngle(Vector temp){
      double dotproduct = this.Dot_Product(temp);
      double mag = this.getMagnitude()* temp.getMagnitude();

      return Math.acos(dotproduct/mag);
  }
  
  public Vector add(Vector temp)
  {
      Vector ret=new Vector("add");
      ret.setX(this.getX()+temp.getX());
      ret.setY(this.getY()+temp.getY());
      ret.setZ(this.getZ()+temp.getZ());
      return ret;
  }

  public Vector neg()
  {
     return this.multiply(-1);
  }

  public Vector subtract(Vector temp)
  {
      temp=temp.neg();
      return this.add(temp);
  }

  public Vector multiply(double temp)
  {
      Vector ret=new Vector();

      ret.setX(this.getX()*temp);
      ret.setY(this.getY()*temp);
      ret.setZ(this.getZ()*temp);

      return ret;

  }

  public double Dot_Product(Vector temp)
  {
      double dot = 0;

      dot += this.getX()*temp.getX();
      dot += this.getY()*temp.getY();
      dot += this.getZ()*temp.getZ();

      return dot;
  }

public Vector Cross_Product(Vector temp){
    Vector ret = new Vector();

    ret.setX(this.getY()*temp.getZ() - this.getZ()*temp.getY());
    ret.setY(this.getZ()*temp.getX() - this.getX()*temp.getZ());
    ret.setZ(this.getX()*temp.getY() - this.getY()*temp.getX());

    return ret;
}

public double tripleProduct(Vector temp1, Vector temp2){
   Vector ret;

   ret = temp1.Cross_Product(temp2);

   return this.Dot_Product(ret);
}

public Vector tripleCrossProduct(Vector temp1, Vector temp2){
    temp1 = this.Cross_Product(temp1);

    return temp1.Cross_Product(temp2);
}

private boolean match(double val1,double val2){
   return Math.abs(val1-val2) <= ERROR;
}

public boolean isUnit(){
  return this.match(this.getMagnitude(), 1);
 }

public boolean isZero(){
    return this.match(this.getMagnitude(), 0);
}

public boolean isNormal(Vector temp){
    return this.match(this.Dot_Product(temp), 0);
}

public boolean isParallel(Vector temp){
    boolean result = this.Cross_Product(temp).isZero();
    if(result == false){
        return false;
    }
    return (this.Dot_Product(temp) > 0);
}

public boolean equals(Vector v){

    boolean result = true;
    result = (this.match(this.getX(), v.getX()) && this.match(this.getY(), v.getY()) && this.match(this.getZ(), v.getZ()));
    
    return result;
}

@Override public String toString(){
    return "("+this.getX()+"i +"+this.getY()+"j +"+this.getZ()+"k)";
}
}