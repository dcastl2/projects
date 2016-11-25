import java.io.FileInputStream;

public abstract class Monster extends Character {

  Item   item;
  char   image[][];
  final static int XMAX = 16;
  final static int YMAX = 32;

  public abstract void attack (EnemyGroup eg, Party p, Map m);

  public void loadImage(String file) {
    int  c;
    int  i=0, j=0;
    image = new char[XMAX][YMAX];
    try {
      FileInputStream inputStream = new FileInputStream(file);
      while ((c = inputStream.read()) != -1)
        if (c != '\n' && j<YMAX && i<XMAX) {
          image[i][j] = (char)c;
	  j++;
	}
        else
          if (i<XMAX-1) {
             i++; 
             j=0;
          }
          else break;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void printImage() {
    int i, j;
    System.out.print(color);
    for (i=0; i<XMAX; i++) {
      for (j=0; j<YMAX; j++) 
        System.out.print(image[i][j]);
      System.out.println();
    }
  }

}
