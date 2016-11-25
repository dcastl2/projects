public class Tile {

  char    t;
  char    T[][];
  boolean can;
  String color;

  public String toString() {
    return color+t;
  }

  public String boldwhite() {
    return Color.boldwhite+t;
  }

  public String white() {
    return Color.white+t;
  }

  public String gray() {
    return Color.gray+t;
  }

  public String black() {
    return Color.black+t;
  }

}
