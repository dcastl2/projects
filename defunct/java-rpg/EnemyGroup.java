public class EnemyGroup implements Cloneable {

  int gold;
  int exp;
  Monster[] ms;
  char[][]  image;

  final static int XMAX =   16;
  final static int YMAX = 3*30;

  public Object clone() {
    try {
      return super.clone();  
    } catch (CloneNotSupportedException e) {
    }
    return new EnemyGroup();
  }

  public void printinfo() {
    for (Monster m : ms)
      m.printinfo();
  }

  public void loadGroup() {
    image = new char[XMAX][YMAX];
    for (int k=0; k<ms.length; k++)
      for (int i=0; i<XMAX; i++)
        for (int j=0; j<YMAX/3; j++)
	  image[i][YMAX/3*k+j] = ms[k].image[i][j];
  }


  public void printGroup() {
    for (int i=0; i<XMAX; i++) {
      for (int j=0; j<YMAX; j++)
        System.out.print(image[i][j]);
      System.out.println();
    }
  }


}
